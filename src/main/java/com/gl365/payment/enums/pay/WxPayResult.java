package com.gl365.payment.enums.pay;
public enum WxPayResult {
	SUCCESS("0", "交易成功");
	private final String code;
	private final String desc;

	private WxPayResult(String code, String desc) {
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
