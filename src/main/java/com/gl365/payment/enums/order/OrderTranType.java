package com.gl365.payment.enums.order;
public enum OrderTranType {
	refundIng("refundIng", "退款中"), refund("refund", "退款完成通知"), confirm("confirm", "交易确认通知");
	private final String code;
	private final String desc;

	private OrderTranType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
