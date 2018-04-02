package com.gl365.payment.service.dbservice.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.mapper.PayMainMapper;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.service.dbservice.PayMainService;
@Service
public class PayMainServiceImpl implements PayMainService {
	@Autowired
	private PayMainMapper payMainMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int save(PayMain payMain) {
		return this.payMainMapper.insert(payMain);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PayMain queryByTerminalAndRequestId(String terminal, String requestId) {
		return this.payMainMapper.queryByTerminalAndRequestId(terminal, requestId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PayMain queryByPayId(String payId) {
		return this.payMainMapper.queryByPayId(payId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateByPayId(PayMain record) {
		return this.payMainMapper.updateByPayId(record);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateStatusByPayId(PayMain payMain) {
		return this.payMainMapper.updateStatusByPayId(payMain);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateStatus(PayMain payMain) {
		return this.payMainMapper.updateStatus(payMain);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateNotifyByPayId(String payId) {
		return this.payMainMapper.updateNotifyByPayId(payId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String queryNotifyByPayId(String payId) {
		return this.payMainMapper.queryNotifyByPayId(payId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PayMain queryByMerchantOrderNo(String merchantOrderNo) {
		return this.payMainMapper.queryByMerchantOrderNo(merchantOrderNo);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PayMain queryByRequestId(String requestId) {
		return this.payMainMapper.queryByRequestId(requestId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PayMain queryByRequestIdAndOrganMerNo(String requestId, String organMerchantNo) {
		return this.payMainMapper.queryByRequestIdAndOrganMerNo(requestId, organMerchantNo);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PageInfo<PayMain> queryPagePayMain(PayMain payMain, int pageNum, int pageSize) {
		Page<PayMain> page = PageHelper.startPage(pageNum, pageSize);
		this.payMainMapper.queryPagePayMain(payMain);
		PageInfo<PayMain> result = new PageInfo<PayMain>(page);
		return result;
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PayMain> queryByGroupOrderId(String groupOrderId) {
		return this.payMainMapper.queryByGroupOrderId(groupOrderId, PayStatus.COMPLETE_PAY.getCode());
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PayMain queryByParam(String merchantOrderNo, String organCode) {
		return this.payMainMapper.queryByParam(merchantOrderNo, organCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int updateStatusByPayIdNew(PayMain record) {
		return this.payMainMapper.updateStatusByPayId(record);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PayMain queryByParamForUpdate(String merchantOrderNo, String organCode) {
		return this.payMainMapper.queryByParamForUpdate(merchantOrderNo, organCode);
	}
}
