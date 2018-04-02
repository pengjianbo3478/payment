package com.gl365.payment.enums.pay;
public enum DealStatus {
	/**
	 * 00-待处理
	 */
	PROCESS("00", "待处理"),
	/**
	 * 01-处理成功
	 */
	SUCCESS("88", "处理成功"),
	/**
	 * 99-失败
	 */
	FAIL("99", "失败"),
	/**
	 * 00-待支付
	 */
	WAITING_FOR_PAYMENT("00", "待支付"),
	/**
	 * 01-已支付
	 */
	ALREADY_PAID("01", "已支付"),
	/**
	 * 02-已撤销
	 */
	WITHDRAW("02", "已撤销"),
	/**
	 * 03-已部分退货
	 */
	PARTIAL_RETURN("03", "已部分退货"),
	/**
	 * 04-全额退货
	 */
	FULL_RETURN("04", "全额退货"),
	/**
	 * 05-预授权完成
	 */
	PRE_AUTHORIZATION_COMPLETED("05", "预授权完成"),
	/**
	 * 88-正常
	 */
	NORMAL("88", "正常");
	private final String code;
	private final String desc;

	private DealStatus(String code, String desc) {
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
