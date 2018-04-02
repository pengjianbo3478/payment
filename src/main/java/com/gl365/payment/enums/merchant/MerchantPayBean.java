package com.gl365.payment.enums.merchant;


public enum MerchantPayBean {

	ON("1", "开"),
	OFF("0", "关");

	private String key;

	private String value;

	private MerchantPayBean(String key, String value) {
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
