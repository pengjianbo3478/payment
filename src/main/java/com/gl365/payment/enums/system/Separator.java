package com.gl365.payment.enums.system;
public enum Separator {
	wxPayDetail("_", "_");
	private final String code;
	private final String desc;

	private Separator(String code, String desc) {
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
