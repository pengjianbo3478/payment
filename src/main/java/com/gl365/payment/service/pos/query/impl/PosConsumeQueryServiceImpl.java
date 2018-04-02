package com.gl365.payment.service.pos.query.impl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.dto.pretranscation.PreTranContext;
import com.gl365.payment.dto.pretranscation.request.PreTranReqDTO;
import com.gl365.payment.dto.pretranscation.response.PreTranRespDTO;
import com.gl365.payment.enums.pay.OrderType;
import com.gl365.payment.enums.pay.PayCategory;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.service.pos.query.PosConsumeQueryService;
import com.gl365.payment.service.pos.query.abs.AbstractConsumeQuery;
@Service
public class PosConsumeQueryServiceImpl extends AbstractConsumeQuery implements PosConsumeQueryService {
	@Override
	public String initPayCategoryCode() {
		return PayCategory.PAY_98.getCode();
	}

	@Override
	public String initTranType() {
		return TranType.CONSUME_QUERY.getCode();
	}

	@Override
	public boolean isPayBean() {
		return true;
	}

	@Override
	public TranType initTranCate() {
		return TranType.CONSUME_QUERY;
	}

	@Override
	public String getOrderType(PreTranContext ctx) {
		return OrderType.pos.getCode();
	}

	@Override
	public void checkReqeust(PreTranReqDTO request) {
		this.checkPayRequestService.checkScene(request.getScene());
		this.checkPayRequestService.checkMerchantOrderTitle(request.getMerchantOrderTitle());
		this.checkPayRequestService.checkMerchantOrderDesc(request.getMerchantOrderDesc());
		this.checkPayRequestService.checkOperator(request.getOperator());
		this.checkPayRequestService.checkNoBenefitAmount(request.getNoBenefitAmount());
		this.checkPayRequestService.checkTerminal(request.getTerminal());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public PreTranRespDTO execute(PreTranReqDTO request) {
		return this.query(request);
	}
}
