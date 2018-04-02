package com.gl365.payment.service.pos.cancelreverse.abs;
import org.apache.commons.lang3.StringUtils;
import com.gl365.payment.dto.rollback.RollbackContext;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.pay.DcType;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.enums.system.Agent;
import com.gl365.payment.enums.system.OrganCode;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.remote.dto.account.request.ReverseOperateReqDTO;
import com.gl365.payment.service.pos.reverse.abs.AbstractReverseTran;
public abstract class AbstractCancelReverse extends AbstractReverseTran {
	@Override
	public String initDcType() {
		return DcType.D.getCode();
	}

	@Override
	public PayStatus initPayStatus() {
		return PayStatus.COMPLETE_PAY;
	}

	@Override
	public void updateAccountBalance(RollbackContext ctx) {
		this.reverseOperate(ctx);
	}

	@Override
	public String getReverseOperatePayId(RollbackContext ctx) {
		return ctx.getPayId();
	}

	public String getReverseOperateOrigPayId(RollbackContext ctx) {
		return ctx.getCancelPayStream().getPayId();
	}

	public void queryPayMian(RollbackContext ctx) {
		String requestId = ctx.getRollbackReqDTO().getOrigRequestId();
		// 查询消费撤销交易流水记录
		PayStream oldPayStream = this.payContextService.queryPayStreamByRequestId(requestId);
		if (oldPayStream == null) throw new ServiceException(Msg.PAY_8022.getCode(), Msg.PAY_8022.getDesc());
		ctx.setCancelPayStream(oldPayStream);
		// 获取原单payId
		String pmPayId = oldPayStream.getOrigRequestId();
		// 根据payId查询原单
		PayMain pm = this.payContextService.queryPayMainByPayId(pmPayId);
		ctx.setPayMain(pm);
		if (pm == null) throw new ServiceException(Msg.PAY_8012.getCode(), Msg.PAY_8012.getDesc());
		ctx.setPayMain(pm);
	}

	public void checkPayMianStatus(RollbackContext ctx) {
		PayMain payMain = ctx.getPayMain();
		String payStatus = payMain.getPayStatus();
		boolean cp = StringUtils.equals(payStatus, PayStatus.CANCEL.getCode());
		if (!cp) throw new ServiceException(Msg.PAY_8004.getCode(), Msg.PAY_8004.getDesc());
	}

	@Override
	public String getPayStreamOrigRequestId(RollbackContext ctx) {
		return ctx.getRollbackReqDTO().getOrigRequestId();
	}

	public void reverseOperate(RollbackContext ctx) {
		PayMain payMain = ctx.getPayMain();
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
		boolean isGroupPayMainOrder = this.isGroupPayMainOrder(payMain);
		if (isGroupPayMainOrder) {
			request.setOperateAmount(payMain.getGroupMainuserPayBean());
			request.setGiftAmount(payMain.getGroupGiftAmount());
		}
		request.setDcType(this.initDcType());
		request.setScene(payMain.getScene());
		request.setPayId(this.getReverseOperatePayId(ctx));
		request.setOrigPayId(this.getReverseOperateOrigPayId(ctx));
		this.payContextService.reverseOperate(request);
	}
}
