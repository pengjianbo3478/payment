package com.gl365.payment.remote.dto.account.request;

public class QueryChannelBindInfoReqDTO {
	
	/**
	 * 会员ID
	 */
	private String userId;
	
	/**
	 * 支付渠道-FFT001:付费通
	 */
	private String organCode;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the organCode
	 */
	public String getOrganCode() {
		return organCode;
	}

	/**
	 * @param organCode the organCode to set
	 */
	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}
	
	
	
}
