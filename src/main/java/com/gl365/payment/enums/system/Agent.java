package com.gl365.payment.enums.system;

/**
 * 账户发行渠道枚举，对应数据库：account_data.account.agent_id
 * 
 * @author Xiang xiaowen
 * @since JDK8
 * @history 2017年4月19日 新建
 */
public enum Agent {

	GL365("40001", "给乐365");

	private String key;

	private String value;

	private Agent(String key, String value) {
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
