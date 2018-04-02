package com.gl365.payment.enums.system;
public enum WxDetailAcc {
	/**
	 * 乐豆池专户(赠送乐豆)-GL1000301
	 */
	beanAccountNo("GL1000301", "乐豆池专户(赠送乐豆)"),
	/**
	 * 营销费专户-GL1000302
	 */
	marcketFeeAccountNo("GL1000302", "营销费专户");
	private final String code;
	private final String desc;

	private WxDetailAcc(String code, String desc) {
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
