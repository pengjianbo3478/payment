package com.gl365.payment.enums.pay;

public enum SplitFlag {
	/**
	 * 0主单
	 */
	mainOrder("0"),
	/**
	 * 1子单
	 */
	childOrder("1");
	
	private final String code;

	private SplitFlag(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
