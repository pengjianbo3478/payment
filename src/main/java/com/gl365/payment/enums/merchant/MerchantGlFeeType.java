package com.gl365.payment.enums.merchant;

public enum MerchantGlFeeType {
	FIXED("0"),
	COSTPRICE("1");

	private String value;

	private MerchantGlFeeType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
