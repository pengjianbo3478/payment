package com.gl365.payment.enums.mq;


public enum AppMQPayType {

	/**
	 * POS刷卡消费
	 */
	POS("POS", "POS刷卡消费"),
	
	/**
	 * APP支付
	 */
	APP("APP", "APP支付");

	private String key;

	private String value;

	private AppMQPayType(String key, String value) {
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
