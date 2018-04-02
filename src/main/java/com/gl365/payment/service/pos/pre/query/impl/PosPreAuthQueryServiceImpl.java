package com.gl365.payment.service.pos.pre.query.impl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.dto.pretranscation.PreTranContext;
import com.gl365.payment.dto.pretranscation.request.PreTranReqDTO;
import com.gl365.payment.dto.pretranscation.response.PreTranRespDTO;
import com.gl365.payment.enums.pay.OrderType;
import com.gl365.payment.enums.pay.PayCategory;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.service.pos.pre.query.PosPreAuthQueryService;
import com.gl365.payment.service.pos.query.abs.AbstractConsumeQuery;
@Service
public class PosPreAuthQueryServiceImpl extends AbstractConsumeQuery implements PosPreAuthQueryService {
	@Override
	public String initPayCategoryCode() {
		return PayCategory.PAY_99.getCode();
	}

	@Override
	public boolean isPayBean() {
		return false;
	}

	@Override
	public String initTranType() {
		return TranType.PRE_AUTH_CONSUME_QUERY.getCode();
	}

	@Override
	public void checkReqeust(PreTranReqDTO request) {
		this.checkPayRequestService.checkTerminal(request.getTerminal());
	}

	@Override
	public TranType initTranCate() {
		return TranType.PRE_AUTH_CONSUME_QUERY;
	}

	@Override
	public String getOrderType(PreTranContext ctx) {
		return OrderType.pos.getCode();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public PreTranRespDTO execute(PreTranReqDTO request) {
		return this.query(request);
	}
}
