package com.gl365.payment.enums.pay;
public enum Scene {
	FAST_PAY("00", "快捷支付"),
	B_SAO_C("01", "B扫C支付"),
	C_SAO_B("02", "C扫B支付"),
	POS_PAY("03", "POS"),
	WX_PAY_PUB("04","微信公众号支付"),
	WX_PAY_H5("05","微信H5支付");

	private final String code;
	private final String desc;

	private Scene(String code, String desc) {
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
