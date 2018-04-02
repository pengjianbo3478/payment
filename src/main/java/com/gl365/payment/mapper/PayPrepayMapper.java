package com.gl365.payment.mapper;
import org.springframework.stereotype.Repository;
import com.gl365.payment.model.PayPrepay;
@Repository
public interface PayPrepayMapper {

	int insertSelective(PayPrepay payPrepay);
	
	int updateStatusByPayId(PayPrepay payPrepay);
	
	PayPrepay queryByPayId(String payId);
}