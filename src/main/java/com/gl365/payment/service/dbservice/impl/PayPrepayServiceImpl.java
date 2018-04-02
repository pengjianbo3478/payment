package com.gl365.payment.service.dbservice.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.mapper.PayPrepayMapper;
import com.gl365.payment.model.PayPrepay;
import com.gl365.payment.service.dbservice.PayPrepayService;
@Service
public class PayPrepayServiceImpl implements PayPrepayService {
	@Autowired
	private PayPrepayMapper payPrepayMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int save(PayPrepay payPrepay) {
		return this.payPrepayMapper.insertSelective(payPrepay);
	}
}
