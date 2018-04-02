package com.gl365.payment.model;
import java.io.Serializable;
public class PayNotify implements Serializable {
	private static final long serialVersionUID = 1L;
	private String requestId;
	private String payId;
	private String status;

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId == null ? null : payId.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public PayNotify(String payId, String status) {
		super();
		this.payId = payId;
		this.status = status;
	}

	public PayNotify() {
		super();
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}