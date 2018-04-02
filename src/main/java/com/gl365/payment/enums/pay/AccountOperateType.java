package com.gl365.payment.enums.pay;
/**
 * 账号系统操作类型
 * @author yanguoqing
 *
 */
public enum AccountOperateType {
	/**
	 * 打赏
	 */
	Ds("6010", "打赏"),
	/**
	 * 打赏冲正
	 */
	DS_REVERSE("6012", "打赏冲正"),
	/**
	 * 转账
	 */
	Zz("6020", "转账");
	private final String code;
	private final String desc;

	private AccountOperateType(String code, String desc) {
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
