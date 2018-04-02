package com.gl365.payment.service.pos.cancel.impl;
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
import com.gl365.payment.service.pos.cancel.PosConsumeCancelService;
import com.gl365.payment.service.pos.cancel.abs.AbstractConsumeCancel;
@Service
public class PosConsumeCancelServiceImpl extends AbstractConsumeCancel implements PosConsumeCancelService {
	@Override
	public String initTranType() {
		return TranType.CANCEL.getCode();
	}

	@Override
	public String initPayCategoryCode() {
		return PayCategory.PAY_92.getCode();
	}

	@Override
	public TranType initLongTranType() {
		return TranType.CANCEL;
	}

	@Override
	public void checkTransType(RollbackContext ctx) {
		PayMain payMain = ctx.getPayMain();
		String transType = payMain.getTransType();
		boolean b = StringUtils.endsWith(transType, TranType.CONSUME_COMMIT.getCode());
		if (!b) throw new ServiceException(Msg.PAY_8020.getCode(), Msg.PAY_8020.getDesc());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public RollbackRespDTO execute(RollbackReqDTO request) {
		return this.exeConsumeCancel(request);
	}

	@Override
	public boolean isSendResultMQ() {
		return true;
	}

	@Override
	public void checkPayReqeust(RollbackReqDTO request) {
		this.checkPayRequestService.checkTerminal(request.getTerminal());
	}
}
