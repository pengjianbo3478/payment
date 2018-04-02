package com.gl365.payment.service.pos.pre.confirm.impl;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.dto.authconsumeconfirm.request.AuthConsumeConfirmReqDTO;
import com.gl365.payment.dto.authconsumeconfirm.response.AuthConsumeConfirmRespDTO;
import com.gl365.payment.dto.base.BaseContext;
import com.gl365.payment.enums.pay.DealStatus;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayPrepay;
import com.gl365.payment.service.dbservice.impl.ConsumeDBService;
import com.gl365.payment.service.pos.pre.confirm.PosPreAuthConsumeConfirm;
import com.gl365.payment.service.pos.pre.confirm.abs.AbstractPosPreAuthConsumeConfirm;
@Service
public class PosPreAuthConsumeConfirmImpl extends AbstractPosPreAuthConsumeConfirm implements PosPreAuthConsumeConfirm {
	@Autowired
	@Qualifier("consumeDBService")
	private ConsumeDBService consumeDBService;

	@Override
	public AuthConsumeConfirmRespDTO authConsumeConfirm(AuthConsumeConfirmReqDTO reqDTO, AuthConsumeConfirmRespDTO respDTO) {
		return this.service(reqDTO, respDTO);
	}

	@Override
	public void setTranType(BaseContext<AuthConsumeConfirmReqDTO, AuthConsumeConfirmRespDTO> bc) {
		bc.setTranType(TranType.PRE_AUTH_CONSUME_CONFIRM);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public boolean firstCommit(BaseContext<AuthConsumeConfirmReqDTO, AuthConsumeConfirmRespDTO> bc) throws ServiceException {
		// 写交易主表
		buildPayMain(bc, DealStatus.WAITING_FOR_PAYMENT);
		// 写付款表
		buildPayDetails(bc);
		consumeDBService.firstCommit(bc);
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public boolean secondCommit(BaseContext<AuthConsumeConfirmReqDTO, AuthConsumeConfirmRespDTO> bc) throws ServiceException {
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
