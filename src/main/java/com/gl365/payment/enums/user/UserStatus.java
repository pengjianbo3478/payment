package com.gl365.payment.enums.user;
public enum UserStatus {
	/**
	 * 未激活-0
	 */
	UNACTIVATED("0", "未激活"),
	/**
	 * 正常-1
	 */
	NORMAL("1", "正常"),
	/**
	 * 冻结-2
	 */
	FREEZE("2", "冻结"),
	/**
	 * 注销-3
	 */
	CANCELED("3", "注销");
	private final String code;
	private final String desc;

	private UserStatus(String code, String desc) {
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
