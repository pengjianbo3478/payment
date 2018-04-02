package com.gl365.payment.enums.pay;
public enum PayChannleType {
	/**
	 * 微信公众号-wxpub
	 */
	wxpub("wxpub", "微信公众号"),
	/**
	 * 微信H5
	 */
	H5("wxh5", "微信H5");
	private final String code;
	private final String desc;

	private PayChannleType(String code, String desc) {
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
