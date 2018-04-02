package com.gl365.payment.service.dbservice.impl;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gl365.payment.enums.system.GlSystem;
import com.gl365.payment.mapper.PayReturnMapper;
import com.gl365.payment.model.PayReturn;
import com.gl365.payment.service.dbservice.PayReturnService;
@Service
public class PayReturnServiceImpl implements PayReturnService {
	@Autowired
	PayReturnMapper payReturnMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(PayReturn payReturn) {
		payReturnMapper.insert(payReturn);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PayReturn> queryByOrigPayId(String origPayId) {
		return payReturnMapper.queryByOrigPayId(origPayId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PageInfo<PayReturn> queryPagePayReturn(PayReturn payReturn, int pageNum, int pageSize) {
		Page<PayReturn> page = PageHelper.startPage(pageNum, pageSize);
		this.payReturnMapper.queryPagePayReturn(payReturn);
		PageInfo<PayReturn> result = new PageInfo<PayReturn>(page);
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PayReturn queryByPayId(String payId) {
		return this.payReturnMapper.queryByPayId(payId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateStatus(PayReturn payReturn) {
		payReturn.setModifyBy(GlSystem.MODIFY_BY_GL_SYSTEM.getCode());
		payReturn.setModifyTime(LocalDateTime.now());
		this.payReturnMapper.updateStatus(payReturn);
	}

	@Override
	public PayReturn queryByMerchantOrder(String organCode, String merchantOrderNo) {
		return payReturnMapper.queryByMerchantOrder(organCode, merchantOrderNo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateReturnTime(PayReturn payReturn) {
		this.payReturnMapper.updateReturnTime(payReturn);
	}
}
