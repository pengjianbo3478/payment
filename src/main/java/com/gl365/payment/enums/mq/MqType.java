package com.gl365.payment.enums.mq;
public enum MqType {
	/**
	 * 
	 */
	SETT("01", "SETT"),
	/**
	 * 
	 */
	MERCHANT("02", "MERCHANT"),
	/**
	 * 
	 */
	APP("03", "APP"),
	/**
	 * 
	 */
	JPSH("04", "Jpush");
	private final String code;
	private final String desc;

	private MqType(String code, String desc) {
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
