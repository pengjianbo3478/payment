package com.gl365.payment.enums.system;
public enum QueryOrder {
	version("1.0.0", "1.0.0"), respTypeSuccess("S", "成功");
	private final String code;
	private final String desc;

	private QueryOrder(String code, String desc) {
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
