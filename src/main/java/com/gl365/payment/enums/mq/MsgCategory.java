package com.gl365.payment.enums.mq;
public enum MsgCategory {
	/**
	 * 正常交易
	 */
	normal("N", "正常交易"),
	/**
	 * 推送通知
	 */
	push("P", "推送通知"),
	/**
	 * 红包通知
	 */
	redPacket("rp", "红包通知");
	private final String code;
	private final String desc;

	private MsgCategory(String code, String desc) {
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
