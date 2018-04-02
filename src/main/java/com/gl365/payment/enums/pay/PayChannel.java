package com.gl365.payment.enums.pay;
public enum PayChannel {
	/**
	 * 交易通道-1
	 */
	POS("1", "POS"),
	/**
	 * 交易通道-2
	 */
	ONLINE("2", "网上");
	private final String code;
	private final String desc;

	private PayChannel(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public String getCode() {
		return code;
	}
}
