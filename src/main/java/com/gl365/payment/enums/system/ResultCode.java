package com.gl365.payment.enums.system;
public enum ResultCode {
	SUCCESS("000000", "交易成功"), FAIL("999999", "交易失败"), BALANCE_UNENOUGH("A10003", "余额不足"),CANCEL_BEAN_FAIL("000001","撤单扣豆失败");
	private final String code;
	private final String desc;

	private ResultCode(String code, String desc) {
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
