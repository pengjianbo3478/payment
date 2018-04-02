package com.gl365.payment.enums.pay;
public enum DcType {
	/**
	 * 借-减少
	 */
	D("D", "借-减少"),
	/**
	 * 贷-增加
	 */
	C("C", "贷-增加");
	private final String code;
	private final String desc;

	private DcType(String code, String desc) {
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
