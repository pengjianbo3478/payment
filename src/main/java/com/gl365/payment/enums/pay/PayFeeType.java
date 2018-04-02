package com.gl365.payment.enums.pay;
public enum PayFeeType {
	D("D", "借记卡"), C("C", "贷记卡");
	private final String code;
	private final String desc;

	private PayFeeType(String code, String desc) {
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
