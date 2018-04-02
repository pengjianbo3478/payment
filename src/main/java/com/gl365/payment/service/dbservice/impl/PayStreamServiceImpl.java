package com.gl365.payment.service.dbservice.impl;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.enums.pay.DealStatus;
import com.gl365.payment.enums.system.GlSystem;
import com.gl365.payment.mapper.PayStreamMapper;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.service.dbservice.PayStreamService;
import com.gl365.payment.util.gson.GsonUtils;
@Service
public class PayStreamServiceImpl implements PayStreamService {
	private static final Logger LOG = LoggerFactory.getLogger(PayStreamServiceImpl.class);
	@Autowired
	private PayStreamMapper payStreamMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int save(PayStream ps) {
		ps.setDealStatus(DealStatus.SUCCESS.getCode());
		ps.setDealDesc(DealStatus.SUCCESS.getDesc());
		LocalDateTime now = LocalDateTime.now();
		ps.setCreateTime(now);
		ps.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
		ps.setModifyTime(now);
		ps.setModifyBy(GlSystem.MODIFY_BY_GL_SYSTEM.getCode());
		LOG.debug("PayStream={}", GsonUtils.toJson(ps));
		return this.payStreamMapper.insert(ps);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PayStream queryByRequestId(String requestId) {
		return this.payStreamMapper.queryByRequestId(requestId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PayStream queryByPayId(String payId) {
		return this.payStreamMapper.queryByPayId(payId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateStatus(String payId, String dealStatus, String dealDesc) {
		return this.payStreamMapper.updateStatus(payId, dealStatus, dealDesc);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PayStream queryByRequestIdAndOrganMerNo(String requestId, String organMerchantNo) {
		return payStreamMapper.queryByRequestIdAndOrganMerNo(requestId, organMerchantNo);
	}
}
