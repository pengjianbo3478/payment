package com.gl365.payment.enums.mgr;
public enum MgrMsg {
	/**
	 * QUERY_PAYMAIN_BY_PAYID_ERROR_01-请求参数对象为空
	 */
	ERROR_01("01", "请求参数对象为空"),
	/**
	 * QUERY_PAYMAIN_BY_PAYID_ERROR_02-请求参数PayId为空
	 */
	ERROR_02("02", "请求参数PayId为空");
	private final String code;
	private final String desc;

	private MgrMsg(String code, String desc) {
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
