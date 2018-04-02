package com.gl365.payment.enums.pay;
public enum WxPayDetailType {
	/**
	 * 商户实得金额-S
	 */
	merchantSettleAmount("s", "商户实得金额"),
	/**
	 * 赠送乐豆-G
	 */
	giftAmount("g", "赠送乐豆"),
	/**
	 * 营销费-M
	 */
	marcketFee("m", "营销费");
	private final String code;
	private final String desc;

	private WxPayDetailType(String code, String desc) {
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
