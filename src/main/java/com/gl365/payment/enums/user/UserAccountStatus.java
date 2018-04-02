package com.gl365.payment.enums.user;

public enum UserAccountStatus {
	
	/**
	 * 正常-0
	 */
	NORMAL("N", "正常");

	private final String code;
	private final String desc;

	private UserAccountStatus(String code, String desc) {
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
