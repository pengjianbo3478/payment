package com.gl365.payment.service.pos.pre.reverse.impl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.dto.rollback.RollbackContext;
import com.gl365.payment.dto.rollback.request.RollbackReqDTO;
import com.gl365.payment.dto.rollback.response.RollbackRespDTO;
import com.gl365.payment.enums.pay.DcType;
import com.gl365.payment.enums.pay.PayCategory;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.service.pos.pre.reverse.PosPreAuthReverseService;
import com.gl365.payment.service.pos.pre.reverse.abs.AbstractPosPreAuthReverse;
@Service
public class PosPreAuthReverseServiceImpl extends AbstractPosPreAuthReverse implements PosPreAuthReverseService {
	@Override
	public String initTranType() {
		return TranType.PRE_AUTH_CONSUME_CONFIRM_REVERSE.getCode();
	}

	@Override
	public String initDcType() {
		return DcType.C.getCode();
	}

	@Override
	public PayStatus initPayStatus() {
		return PayStatus.REVERSE;
	}

	@Override
	public String initPayCategoryCode() {
		return PayCategory.PAY_97.getCode();
	}

	@Override
	public void updateAccountBalance(RollbackContext ctx) {
		this.reverseOperate(ctx);
	}

	@Override
	public TranType initLongTranType() {
		return TranType.PRE_AUTH_CONSUME_CONFIRM_REVERSE;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public RollbackRespDTO execute(RollbackReqDTO request) {
		return this.exeReverse(request);
	}

	@Override
	public void checkPayReqeust(RollbackReqDTO request) {
		this.checkPayRequestService.checkTerminal(request.getTerminal());
	}
}
