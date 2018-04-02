package com.gl365.payment.service.pos.cancel.abs;
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
import com.gl365.payment.enums.pay.DcType;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.enums.system.Agent;
import com.gl365.payment.enums.system.OrganCode;
import com.gl365.payment.exception.InvalidRequestException;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.remote.dto.account.request.CancelOperateReqDTO;
import com.gl365.payment.service.pos.abs.AbstractRollbackMain;
import com.gl365.payment.util.Gl365StrUtils;
public abstract class AbstractConsumeCancel extends AbstractRollbackMain {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractConsumeCancel.class);

	@Transactional(propagation = Propagation.REQUIRED)
	public RollbackRespDTO exeConsumeCancel(RollbackReqDTO reqeust) {
		return this.exeRollback(reqeust);
	}

	@Override
	public String getPayStreamOrigRequestId(RollbackContext ctx) {
		return ctx.getRollbackReqDTO().getOrigPayId();
	}

	public void updateAccountBalance(RollbackContext ctx) {
		PayMain payMain = ctx.getPayMain();
		CancelOperateReqDTO request = new CancelOperateReqDTO();
		request.setUserId(payMain.getUserId());
		request.setAgentId(Agent.GL365.getKey());
		request.setOrganCode(OrganCode.GL.getCode());
		request.setPayId(ctx.getPayId());
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
		request.setOrigPayId(payMain.getPayId());
		LOG.debug("#####request={}", Gl365StrUtils.toMultiLineStr(request));
		this.payContextService.cancelOperate(request);
	}

	public abstract void checkPayReqeust(RollbackReqDTO request);

	@Override
	public void checkReqeust(RollbackReqDTO request) {
		String origPayId = request.getOrigPayId();
		boolean ep = StringUtils.isEmpty(origPayId);
		if (ep) throw new InvalidRequestException(Msg.REQ_0022.getCode(), Msg.REQ_0022.getDesc());
		if (origPayId.length() > 32) throw new InvalidRequestException(Msg.REQ_0022.getCode(), Msg.REQ_0022.getDesc());
		LocalDate origTxnDate = request.getOrigTxnDate();
		if (origTxnDate == null) throw new InvalidRequestException(Msg.REQ_0023.getCode(), Msg.REQ_0023.getDesc());
		this.checkPayReqeust(request);
	}

	@Override
	public void queryPayMian(RollbackContext ctx) {
		String payId = ctx.getRollbackReqDTO().getOrigPayId();
		PayMain payMain = this.payContextService.queryPayMainByPayId(payId);
		if (payMain == null) throw new ServiceException(Msg.PAY_8012.getCode(), Msg.PAY_8012.getDesc());
		ctx.setPayMain(payMain);
	}

	@Override
	public String initDcType() {
		return DcType.C.getCode();
	}

	@Override
	public PayStatus initPayStatus() {
		return PayStatus.CANCEL;
	}

	@Override
	public void checkPayMianStatus(RollbackContext ctx) {
		this.checkCompletePay(ctx);
	}
}
