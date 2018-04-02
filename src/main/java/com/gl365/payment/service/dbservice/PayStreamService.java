package com.gl365.payment.service.dbservice;
import com.gl365.payment.model.PayStream;
public interface PayStreamService {
	int save(PayStream PayStream);

	PayStream queryByRequestId(String requestId);

	PayStream queryByRequestIdAndOrganMerNo(String requestId, String organMerchantNo);

	PayStream queryByPayId(String payId);

	int updateStatus(String payId, String status, String desc);
}
