package com.gl365.payment.dto.mgr.reqeust;
import java.io.Serializable;
public class QueryPayDetailByPayIdReqDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String payId;

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}
}
