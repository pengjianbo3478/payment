package com.gl365.payment.enums.pay;
public enum PayCurrency {
	BEAN("bean", "乐豆"), CASH("cash", "现金");
	private final String code;
	private final String desc;

	private PayCurrency(String code, String desc) {
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
