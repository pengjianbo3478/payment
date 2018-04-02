package com.gl365.payment.service.dbservice.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.mapper.PayNotifyMapper;
import com.gl365.payment.model.PayNotify;
import com.gl365.payment.service.dbservice.PayNotifyService;
@Service
public class PayNotifyServiceImpl implements PayNotifyService {
	@Autowired
	PayNotifyMapper payNotifyMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int insert(PayNotify payNotify) {
		return this.payNotifyMapper.insert(payNotify);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public PayNotify queryByPayNotify(PayNotify payNotify) {
		return this.payNotifyMapper.queryByPayNotify(payNotify);
	}
}
