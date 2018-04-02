package com.gl365.payment.enums.mq;
public enum AppMQTitle {
	/**
	 * 支付撤单成功
	 */
	ConsumeCancel("ConsumeCancel", "支付撤单成功"),
	/**
	 * 支付冲正成功
	 */
	ConsumeReverse("ConsumeReverse", "支付冲正成功"),
	/**
	 * 支付撤单冲正成功
	 */
	CancelReverse("CancelReverse", "支付撤单冲正成功"),
	/**
	 * 预授权冲正成功
	 */
	PreReverse("PreReverse", "预授权冲正成功"),
	/**
	 * 交易成功
	 */
	SUCC("SUCC", "交易成功");
	private String key;
	private String value;

	private AppMQTitle(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
}
