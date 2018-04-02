package com.gl365.payment.service.pos.pre.reverse.abs;
import com.gl365.payment.dto.rollback.RollbackContext;
public abstract class AbstractPosPreAuthReverse extends AbstractPosPreAuthReverseCheck {
	@Override
	public String getReverseOperatePayId(RollbackContext ctx) {
		return ctx.getPayId();
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
