package com.gl365.payment.enums.getway;
public enum OrderQueryResult {
	/**
	 * 00-交易成功
	 * 
	 */
	SUCCESS("00", "交易成功");

	private String key;
	private String value;

	private OrderQueryResult(String key, String value) {
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
