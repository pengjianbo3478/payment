package com.gl365.payment.enums.pay;

public enum OrderType {
	/**
	 * 正常订单（如果订单标题解析出来为空、或者是POS交易，则默认为1）
	 */
	pos("1", "正常订单（如果订单标题解析出来为空、或者是POS交易，则默认为1）"),
	/**
	 * 打赏订单
	 */
	ds("2", "打赏订单"),
	/**
	 * 达人订单
	 */
	dr("3", "达人订单"),
	/**
	 * 网购订单
	 */
	wg("4", "网购订单"),
	/**
	 * 纯乐豆打赏
	 */
	beanDs("5", "纯乐豆打赏"),
	/**
	 * C对C纯乐豆支付
	 */
	beanPay("6", "C对C纯乐豆支付"),
	/**
	 * 群支付
	 */
	groupPay("7", "群支付");
	
	private final String code;
	private final String desc;

	private OrderType(String code, String desc) {
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
