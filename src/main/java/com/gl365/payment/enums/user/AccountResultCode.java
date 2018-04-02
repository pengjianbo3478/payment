package com.gl365.payment.enums.user;

public enum AccountResultCode {

	SUCCESS("000000", "成功"),
	/**
	 * 乐豆余额不足
	 */
	BALANCE_UNENOUGH("A10003","余额不足");

	private final String code;
	private final String desc;

	private AccountResultCode(String code, String desc) {
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
