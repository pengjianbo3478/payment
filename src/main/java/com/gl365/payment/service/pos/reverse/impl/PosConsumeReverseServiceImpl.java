package com.gl365.payment.service.pos.reverse.impl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.dto.rollback.RollbackContext;
import com.gl365.payment.dto.rollback.request.RollbackReqDTO;
import com.gl365.payment.dto.rollback.response.RollbackRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.pay.PayCategory;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.service.pos.reverse.PosConsumeReverseService;
import com.gl365.payment.service.pos.reverse.abs.AbstractConsumeReverse;
@Service
public class PosConsumeReverseServiceImpl extends AbstractConsumeReverse implements PosConsumeReverseService {
	@Override
	public String initTranType() {
		return TranType.CONSUME_REVERSE.getCode();
	}

	@Override
	public String initPayCategoryCode() {
		return PayCategory.PAY_91.getCode();
	}

	@Override
	public TranType initLongTranType() {
		return TranType.CONSUME_REVERSE;
	}

	@Override
	public void checkTransType(RollbackContext ctx) {
		PayMain payMain = ctx.getPayMain();
		String transType = payMain.getTransType();
		boolean b = StringUtils.equals(transType, TranType.CONSUME_COMMIT.getCode());
		if (!b) throw new ServiceException(Msg.PAY_8020.getCode(), Msg.PAY_8020.getDesc());
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
