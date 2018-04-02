package com.gl365.payment.enums.system;
public enum Flag {
	Y("Y", "Y"), N("N", "N");
	private final String code;
	private final String desc;

	private Flag(String code, String desc) {
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
