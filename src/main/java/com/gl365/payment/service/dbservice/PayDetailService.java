package com.gl365.payment.service.dbservice;
import java.util.List;
import com.gl365.payment.model.PayDetail;
public interface PayDetailService {
	int save(PayDetail payDetail);
	
	int deleteByPayId(String payId);
	
	List<PayDetail> queryPayDetailByPayId( String payId);
}
