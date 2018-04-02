package com.gl365.payment.service.pos.confirm.impl;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gl365.payment.dto.base.BaseContext;
import com.gl365.payment.dto.consumeconfirm.request.ConsumeConfirmReqDTO;
import com.gl365.payment.dto.consumeconfirm.response.ConsumeConfirmRespDTO;
import com.gl365.payment.enums.pay.DealStatus;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayPrepay;
import com.gl365.payment.service.dbservice.impl.ConsumeDBService;
import com.gl365.payment.service.pos.confirm.ConsumeConfirmService;
import com.gl365.payment.service.pos.confirm.abs.AbstractConsumeConfirmCheck;
@Service
public class ConsumeConfirmServiceImpl extends AbstractConsumeConfirmCheck implements ConsumeConfirmService {
	@Autowired
	@Qualifier("consumeDBService")
	ConsumeDBService consumeDBService;

	@Override
	public ConsumeConfirmRespDTO consumeConfirm(ConsumeConfirmReqDTO reqDTO, ConsumeConfirmRespDTO respDTO) {
		return this.service(reqDTO, respDTO);
	}

	@Override
	public void setTranType(BaseContext<ConsumeConfirmReqDTO, ConsumeConfirmRespDTO> bc) {
		bc.setTranType(TranType.CONSUME_COMMIT);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public void query(BaseContext<ConsumeConfirmReqDTO, ConsumeConfirmRespDTO> bc) throws ServiceException {
		// 通过查询号查询支付预交易表
		PayPrepay payPrepay = payPrepayMapper.queryByPayId(bc.getRequest().getOrigPayId());
		if (payPrepay != null) {
			bc.setPayPrepay(payPrepay);
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public boolean firstCommit(BaseContext<ConsumeConfirmReqDTO, ConsumeConfirmRespDTO> bc) throws ServiceException {
		// 写交易主表
		buildPayMain(bc, DealStatus.WAITING_FOR_PAYMENT);
		// payMainMapper.insert(payMain);
		// 写付款表
		buildPayDetails(bc);
		/*for (PayDetail pd : payDetails) {
			payDetailMapper.insert(pd);
		}*/
		consumeDBService.firstCommit(bc);
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public boolean secondCommit(BaseContext<ConsumeConfirmReqDTO, ConsumeConfirmRespDTO> bc) throws ServiceException {
		// 调用account系统服务
		buildUpdateAccountBalanceOffLineReqDTO(bc);
		// 更新交易主表
		PayMain payResult = payAdapter.clonePayMain(bc.getPayMain(), DealStatus.ALREADY_PAID, null);
		payResult.setPayTime(LocalDateTime.now());
		bc.setPayMain(payResult);
		// 更新支付交易查询表状态
		PayPrepay payPrepayResult = payAdapter.clonePayPrepay(bc.getPayPrepay(), DealStatus.ALREADY_PAID, null);
		bc.setPayPrepay(payPrepayResult);
		consumeDBService.secondCommit(bc);
		return true;
	}

	@Override
	public boolean isSendPaymentResultMQ() {
		return true;
	}

	@Override
	public boolean isSendRewardResultMQ() {
		return false;
	}
}
