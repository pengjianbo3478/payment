package com.gl365.payment.service.dbservice.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.mapper.PayDetailMapper;
import com.gl365.payment.model.PayDetail;
import com.gl365.payment.service.dbservice.PayDetailService;
@Service
public class PayDetailServiceImpl implements PayDetailService {
	@Autowired
	private PayDetailMapper payDetailMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int save(PayDetail payDetail) {
		return this.payDetailMapper.insert(payDetail);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PayDetail> queryPayDetailByPayId(String payId) {
		return this.payDetailMapper.queryPayDetailByPayId(payId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int deleteByPayId(String payId) {
		return this.payDetailMapper.deleteByPayId(payId);
	}
}
