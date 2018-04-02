package com.gl365.payment.dto.base;

import java.util.HashMap;
import java.util.List;
import com.gl365.payment.common.Finance;
import com.gl365.payment.common.MqCell;
import com.gl365.payment.enums.mq.SystemType;
import com.gl365.payment.model.PayDetail;
import com.gl365.payment.model.PayPrepay;
import com.gl365.payment.model.PayReturn;
import com.gl365.payment.remote.dto.account.request.CancelOperateReqDTO;
import com.gl365.payment.remote.dto.account.request.UpdateAccountBalanceOffLineReqDTO;

public class SuperContext extends PayContext {
	
	private static final long serialVersionUID = 1L;

	private PayPrepay payPrepay;

	private PayReturn payReturn;
	
	private Finance finance;
	
	private List<PayDetail> payDetails;
	
	private CancelOperateReqDTO cancelOperateReqDTO;
	
	private UpdateAccountBalanceOffLineReqDTO updateAccountBalanceOffLineReqDTO;
	
	private HashMap<SystemType, List<MqCell>> mqContents = null;

	public PayPrepay getPayPrepay() {
		return payPrepay;
	}

	public void setPayPrepay(PayPrepay payPrepay) {
		this.payPrepay = payPrepay;
	}

	public PayReturn getPayReturn() {
		return payReturn;
	}

	public void setPayReturn(PayReturn payReturn) {
		this.payReturn = payReturn;
	}

	public Finance getFinance() {
		return finance;
	}

	public void setFinance(Finance finance) {
		this.finance = finance;
	}

	public List<PayDetail> getPayDetails() {
		return payDetails;
	}

	public void setPayDetails(List<PayDetail> payDetails) {
		this.payDetails = payDetails;
	}

	public CancelOperateReqDTO getCancelOperateReqDTO() {
		return cancelOperateReqDTO;
	}

	public void setCancelOperateReqDTO(CancelOperateReqDTO cancelOperateReqDTO) {
		this.cancelOperateReqDTO = cancelOperateReqDTO;
	}

	public UpdateAccountBalanceOffLineReqDTO getUpdateAccountBalanceOffLineReqDTO() {
		return updateAccountBalanceOffLineReqDTO;
	}

	public void setUpdateAccountBalanceOffLineReqDTO(UpdateAccountBalanceOffLineReqDTO updateAccountBalanceOffLineReqDTO) {
		this.updateAccountBalanceOffLineReqDTO = updateAccountBalanceOffLineReqDTO;
	}

	public HashMap<SystemType, List<MqCell>> getMqContents() {
		return mqContents;
	}

	public void setMqContents(HashMap<SystemType, List<MqCell>> mqContents) {
		this.mqContents = mqContents;
	}
	
	
}
