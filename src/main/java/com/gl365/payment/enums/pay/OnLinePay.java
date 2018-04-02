package com.gl365.payment.enums.pay;
public enum OnLinePay {
	/**
	 * 00-线上线下
	 */
	ONLINE_POS("0", "线上线下"),
	/**
	 * 1-线下
	 */
	POS("1", "线下"),
	/**
	 * 2-线上
	 */
	ON_LINE("2", "线上");
	private final String code;
	private final String desc;

	private OnLinePay(String code, String desc) {
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
