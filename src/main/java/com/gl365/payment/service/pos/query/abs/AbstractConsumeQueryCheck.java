package com.gl365.payment.service.pos.query.abs;
import org.springframework.beans.factory.annotation.Autowired;
import com.gl365.payment.dto.pretranscation.request.PreTranReqDTO;
import com.gl365.payment.service.check.CheckPayRequestService;
public abstract class AbstractConsumeQueryCheck extends AbstractConsumeQueryBuild {
	@Autowired
	public CheckPayRequestService checkPayRequestService;

	public void checkRequestParams(PreTranReqDTO request) {
		this.checkPayRequestService.checkOrganCode(request.getOrganCode());
		this.checkPayRequestService.checkRequestId(request.getRequestId());
		this.checkPayRequestService.checkRequestDate(request.getRequestDate());
		this.checkPayRequestService.checkOrganMerchantNo(request.getOrganMerchantNo());
		this.checkPayRequestService.checkCardIndex(request.getCardIndex());
		this.checkPayRequestService.checkTotalAmount(request.getTotalAmount());
		this.checkReqeust(request);
	}
}
