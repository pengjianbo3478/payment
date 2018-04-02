package com.gl365.payment.enums.system;
public enum GlSystem {
	CREATE_BY_GL_SYSTEM("10000","给乐平台"),
	MODIFY_BY_GL_SYSTEM("10000","给乐平台"),
	E9999("", "");
	private final String code;
	private final String desc;

	private GlSystem(String code, String desc) {
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
