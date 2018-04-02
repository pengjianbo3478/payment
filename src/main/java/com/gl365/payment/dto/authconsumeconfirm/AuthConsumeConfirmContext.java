package com.gl365.payment.dto.authconsumeconfirm;
import java.io.Serializable;

import com.gl365.payment.dto.authconsumeconfirm.request.AuthConsumeConfirmReqDTO;
import com.gl365.payment.dto.authconsumeconfirm.response.AuthConsumeConfirmRespDTO;
import com.gl365.payment.dto.base.PayContext;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.model.PayPrepay;
import com.gl365.payment.util.Gl365StrUtils;
public class AuthConsumeConfirmContext extends PayContext implements Serializable {
	private static final long serialVersionUID = 1L;
	private PayPrepay payPrepay = new PayPrepay();
	private AuthConsumeConfirmReqDTO authConsumeConfirmReqDTO;
	private AuthConsumeConfirmRespDTO authConsumeConfirmRespDTO;

	public AuthConsumeConfirmContext(AuthConsumeConfirmReqDTO reqDTO) {
		super();
		this.authConsumeConfirmReqDTO = reqDTO;
		setTranType(TranType.PRE_AUTH_CONSUME_CONFIRM);
	}

	public AuthConsumeConfirmContext() {
		super();
	}

	public PayPrepay getPayPrepay() {
		return payPrepay;
	}

	public void setPayPrepay(PayPrepay payPrepay) {
		this.payPrepay = payPrepay;
	}



	public AuthConsumeConfirmReqDTO getAuthConsumeConfirmReqDTO() {
		return authConsumeConfirmReqDTO;
	}

	public void setAuthConsumeConfirmReqDTO(AuthConsumeConfirmReqDTO authConsumeConfirmReqDTO) {
		this.authConsumeConfirmReqDTO = authConsumeConfirmReqDTO;
	}

	public AuthConsumeConfirmRespDTO getAuthConsumeConfirmRespDTO() {
		return authConsumeConfirmRespDTO;
	}

	public void setAuthConsumeConfirmRespDTO(AuthConsumeConfirmRespDTO authConsumeConfirmRespDTO) {
		this.authConsumeConfirmRespDTO = authConsumeConfirmRespDTO;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toStr(this);
	}
}
