package com.gl365.payment.service.web.reverse.impl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.gl365.payment.dto.rollback.RollbackContext;
import com.gl365.payment.dto.rollback.request.RollbackReqDTO;
import com.gl365.payment.dto.rollback.response.RollbackRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.pay.PayCategory;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.service.pos.reverse.abs.AbstractConsumeReverse;
import com.gl365.payment.service.web.reverse.WebConsumeReverseService;
@Service
public class WebConsumeReverseServiceImpl extends AbstractConsumeReverse implements WebConsumeReverseService {
	@Override
	public String initTranType() {
		return TranType.ONLINE_CONSUME_REVERSE.getCode();
	}

	@Override
	public String initPayCategoryCode() {
		return PayCategory.PAY_83.getCode();
	}

	@Override
	public TranType initLongTranType() {
		return TranType.ONLINE_CONSUME_REVERSE;
	}

	@Override
	public void checkTransType(RollbackContext ctx) {
		PayMain payMain = ctx.getPayMain();
		String transType = payMain.getTransType();
		boolean b = StringUtils.equals(transType, TranType.ONLINE_CONSUME.getCode());
		if (!b) throw new ServiceException(Msg.PAY_8020.getCode(), Msg.PAY_8020.getDesc());
	}

	@Override
	public RollbackRespDTO execute(RollbackReqDTO request) {
		return this.exeReverse(request);
	}

	@Override
	public void checkPayReqeust(RollbackReqDTO request) {
	}
}
