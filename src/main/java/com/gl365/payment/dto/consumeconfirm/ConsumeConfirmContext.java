package com.gl365.payment.dto.consumeconfirm;
import java.io.Serializable;
import com.gl365.payment.dto.base.PayContext;
import com.gl365.payment.dto.consumeconfirm.request.ConsumeConfirmReqDTO;
import com.gl365.payment.dto.consumeconfirm.response.ConsumeConfirmRespDTO;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.model.PayPrepay;
import com.gl365.payment.util.Gl365StrUtils;
public class ConsumeConfirmContext extends PayContext implements Serializable {
	private static final long serialVersionUID = 1L;
	private PayPrepay payPrepay = new PayPrepay();
	private ConsumeConfirmReqDTO consumeConfirmReqDTO;
	private ConsumeConfirmRespDTO consumeConfirmRespDTO;

	public ConsumeConfirmContext(ConsumeConfirmReqDTO reqDTO) {
		super();
		this.consumeConfirmReqDTO = reqDTO;
		setTranType(TranType.CONSUME_COMMIT);
	}

	public ConsumeConfirmContext() {
		super();
	}

	public PayPrepay getPayPrepay() {
		return payPrepay;
	}

	public void setPayPrepay(PayPrepay payPrepay) {
		this.payPrepay = payPrepay;
	}


	public ConsumeConfirmReqDTO getConsumeConfirmReqDTO() {
		return consumeConfirmReqDTO;
	}

	public void setConsumeConfirmReqDTO(ConsumeConfirmReqDTO consumeConfirmReqDTO) {
		this.consumeConfirmReqDTO = consumeConfirmReqDTO;
	}

	public ConsumeConfirmRespDTO getConsumeConfirmRespDTO() {
		return consumeConfirmRespDTO;
	}

	public void setConsumeConfirmRespDTO(ConsumeConfirmRespDTO consumeConfirmRespDTO) {
		this.consumeConfirmRespDTO = consumeConfirmRespDTO;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toStr(this);
	}
}
