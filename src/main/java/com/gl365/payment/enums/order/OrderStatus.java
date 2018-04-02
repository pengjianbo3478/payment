package com.gl365.payment.enums.order;
public enum OrderStatus {
	refund(5, "退货");
	private final Integer code;
	private final String desc;

	private OrderStatus(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
