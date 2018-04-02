package com.gl365.payment.service.pos.reverse.abs;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.dto.rollback.RollbackContext;
import com.gl365.payment.dto.rollback.request.RollbackReqDTO;
import com.gl365.payment.dto.rollback.response.RollbackRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.pay.AccountOperateType;
import com.gl365.payment.enums.pay.OrderType;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.enums.pay.SplitFlag;
import com.gl365.payment.enums.system.Agent;
import com.gl365.payment.enums.system.GlSystem;
import com.gl365.payment.enums.system.OrganCode;
import com.gl365.payment.exception.InvalidRequestException;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.remote.dto.account.request.BeanTransferReverseReqDTO;
import com.gl365.payment.remote.dto.account.request.ReverseOperateReqDTO;
import com.gl365.payment.service.pos.abs.AbstractRollbackMain;
public abstract class AbstractReverseTran extends AbstractRollbackMain {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractReverseTran.class);

	/**
	 * 设置冲正交易-支付流水号
	 * @param ctx
	 * @return String
	 */
	public abstract String getReverseOperatePayId(RollbackContext ctx);

	@Transactional(propagation = Propagation.REQUIRED)
	public RollbackRespDTO exeReverse(RollbackReqDTO reqeust) {
		return this.exeRollback(reqeust);
	}

	public void reverseOperate(RollbackContext ctx) {
		PayMain payMain = ctx.getPayMain();
		String c = payMain.getOrderType();
		boolean k = StringUtils.equals(c, OrderType.ds.getCode());
		if (k) this.dsRevereOperate(ctx, payMain);
		else this.normalReverseOperate(ctx, payMain);
	}

	private void dsRevereOperate(RollbackContext ctx, PayMain payMain) {
		BeanTransferReverseReqDTO request = new BeanTransferReverseReqDTO();
		request.setOrigPayId(payMain.getPayId());
		request.setPayId(this.getReverseOperatePayId(ctx));
		request.setFromUser(payMain.getUserId());
		request.setToUser(payMain.getRewardUserId());
		request.setOperateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
		request.setOperateType(AccountOperateType.DS_REVERSE.getCode());
		request.setOperateAmount(payMain.getBeanAmount());
		this.payContextService.beanTransfeReverseOperate(request);
	}

	private void normalReverseOperate(RollbackContext ctx, PayMain payMain) {
		ReverseOperateReqDTO request = new ReverseOperateReqDTO();
		request.setAgentId(Agent.GL365.getKey());
		request.setUserId(payMain.getUserId());
		request.setOrganCode(OrganCode.GL.getCode());
		request.setMerchantNo(payMain.getMerchantNo());
		request.setMerchantName(payMain.getMerchantName());
		request.setMerchantOrderNo(payMain.getMerchantOrderNo());
		request.setOperateType(this.initTranType());
		request.setOperateAmount(payMain.getBeanAmount());
		request.setGiftAmount(payMain.getGiftAmount());
		// 订单是否为群支付时设置返利金额
		this.setGiftAmount(payMain, request);
		request.setDcType(this.initDcType());
		request.setScene(payMain.getScene());
		request.setPayId(this.getReverseOperatePayId(ctx));
		request.setOrigPayId(payMain.getPayId());
		this.payContextService.reverseOperate(request);
	}

	private void setGiftAmount(PayMain payMain, ReverseOperateReqDTO request) {
		String orderType = payMain.getOrderType();
		boolean k = StringUtils.equals(orderType, OrderType.groupPay.getCode());
		if (!k) return;
		LOG.info("订单为群支付");
		String sf = payMain.getSplitFlag();
		boolean m = StringUtils.equals(SplitFlag.mainOrder.getCode(), sf);
		if (m) {
			LOG.info("订单为群支付且为主单操作金额={}", payMain.getGroupMainuserPayBean());
			request.setOperateAmount(payMain.getGroupMainuserPayBean());
		}
		LOG.info("订单为群支付返利金额={}", payMain.getGroupGiftAmount());
		request.setGiftAmount(payMain.getGroupGiftAmount());
	}

	@Override
	public void queryPayMian(RollbackContext ctx) {
		RollbackReqDTO reqeust = ctx.getRollbackReqDTO();
		String requestId = reqeust.getOrigRequestId();
		String terminal = reqeust.getTerminal();
		ctx.setRequestId(requestId);
		ctx.setTerminal(terminal);
		PayMain payMain = this.payContextService.queryPayMainByRequestId(ctx);
		ctx.setPayMain(payMain);
	}

	public abstract void checkPayReqeust(RollbackReqDTO request);

	@Override
	public void checkReqeust(RollbackReqDTO request) {
		String origRequestId = request.getOrigRequestId();
		boolean e = StringUtils.isEmpty(origRequestId);
		if (e) throw new InvalidRequestException(Msg.REQ_0022.getCode(), Msg.REQ_0022.getDesc());
		boolean l = origRequestId.length() > 32;
		if (l) throw new InvalidRequestException(Msg.REQ_0022.getCode(), Msg.REQ_0022.getDesc());
		LocalDate origTxnDate = request.getOrigTxnDate();
		if (origTxnDate == null) throw new InvalidRequestException(Msg.REQ_0023.getCode(), Msg.REQ_0023.getDesc());
		this.checkPayReqeust(request);
	}

	@Override
	public void checkPayMianStatus(RollbackContext ctx) {
		PayMain payMain = ctx.getPayMain();
		String payStatus = payMain.getPayStatus();
		boolean cp = StringUtils.equals(payStatus, PayStatus.COMPLETE_PAY.getCode());
		if (!cp) throw new ServiceException(Msg.PAY_8004.getCode(), Msg.PAY_8004.getDesc());
	}
}
