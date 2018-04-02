package com.gl365.payment.enums.merchant;
public enum MerchantStatus {
	/**
	 * 正常
	 */
	NORMAL("0", "正常"),
	/**
	 * 禁止
	 */
	FORBIDDEN("1", "禁止"),
	/**
	 * 暂停
	 */
	PAUSE("2", "暂停"),
	/**
	 * 注销
	 */
	CANCELED("3", "注销");
	private final String code;
	private final String desc;

	private MerchantStatus(String code, String desc) {
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
