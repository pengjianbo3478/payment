package com.gl365.payment.service.pos.reverse.abs;
import com.gl365.payment.dto.rollback.RollbackContext;
import com.gl365.payment.dto.rollback.request.RollbackReqDTO;
import com.gl365.payment.enums.pay.DcType;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.model.PayMain;
public abstract class AbstractConsumeReverse extends AbstractConsumeReverseCheck {
	@Override
	public String initDcType() {
		return DcType.C.getCode();
	}

	@Override
	public PayStatus initPayStatus() {
		return PayStatus.REVERSE;
	}

	@Override
	public String getReverseOperatePayId(RollbackContext ctx) {
		return ctx.getPayId();
	}

	public void queryPayMian(RollbackContext ctx) {
		RollbackReqDTO request = ctx.getRollbackReqDTO();
		String requestId = request.getOrigRequestId();
		String terminal = request.getTerminal();
		ctx.setRequestId(requestId);
		ctx.setTerminal(terminal);
		PayMain payMain = this.payContextService.queryPayMainByRequestId(ctx);
		ctx.setPayMain(payMain);
	}

	public void updateAccountBalance(RollbackContext ctx) {
		this.reverseOperate(ctx);
	}

	@Override
	public String getPayStreamOrigRequestId(RollbackContext ctx) {
		return ctx.getRollbackReqDTO().getOrigRequestId();
	}

	@Override
	public boolean isSendResultMQ() {
		return false;
	}
}
