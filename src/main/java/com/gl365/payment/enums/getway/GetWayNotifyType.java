package com.gl365.payment.enums.getway;
public enum GetWayNotifyType {
	/**
	 * 1-正常交易通知
	 * 
	 */
	normal("1", "正常交易通知"),
	/**
	 * 2-延时通知订单查询结果通知
	 */
	query("2", "延时通知订单查询结果通知");
	private String key;
	private String value;

	private GetWayNotifyType(String key, String value) {
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
