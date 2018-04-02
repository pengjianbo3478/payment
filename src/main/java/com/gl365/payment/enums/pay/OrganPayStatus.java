package com.gl365.payment.enums.pay;
public enum OrganPayStatus {
	REFUND_ALL("04", "全额退货成功");
	private final String code;
	private final String desc;

	private OrganPayStatus(String code, String desc) {
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
