package com.gl365.payment.dto.rollback;
import com.gl365.payment.dto.base.PayContext;
import com.gl365.payment.dto.rollback.request.RollbackReqDTO;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.util.Gl365StrUtils;
public class RollbackContext extends PayContext {
	private static final long serialVersionUID = 1L;
	private RollbackReqDTO rollbackReqDTO;
	private PayStatus payStatus;
	private PayStream cancelPayStream;

	public RollbackReqDTO getRollbackReqDTO() {
		return rollbackReqDTO;
	}

	public void setRollbackReqDTO(RollbackReqDTO rollbackReqDTO) {
		this.rollbackReqDTO = rollbackReqDTO;
	}

	public RollbackContext(RollbackReqDTO rollbackReqDTO) {
		super();
		this.rollbackReqDTO = rollbackReqDTO;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toStr(this);
	}

	public PayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}

	public PayStream getCancelPayStream() {
		return cancelPayStream;
	}

	public void setCancelPayStream(PayStream cancelPayStream) {
		this.cancelPayStream = cancelPayStream;
	}
}
