package com.gl365.payment.enums.pay;
public enum PayCategory {
	/**
	 * POS消费交易(确认)
	 */
	PAY_90("90", "POS消费交易(确认)"),
	/**
	 * POS消费冲正
	 */
	PAY_91("91", "POS消费冲正"),
	/**
	 * POS消费撤单
	 */
	PAY_92("92", "POS消费撤单"),
	/**
	 * POS消费撤单冲
	 */
	PAY_93("93", "POS消费撤单冲正"),
	/**
	 * POS消费退货
	 */
	PAY_94("94", "POS消费退货"),
	/**
	 * POS消费退货冲
	 */
	PAY_95("95", "POS消费退货冲正"),
	/**
	 * 预授权完成(确认)
	 */
	PAY_96("96", "预授权完成(确认)"),
	/**
	 * 预授权完成冲正
	 */
	PAY_97("97", "预授权完成冲正"),
	/**
	 * POS预交易查询
	 */
	PAY_98("98", "POS预交易查询"),
	/**
	 * POS预授权查询
	 */
	PAY_99("99", "POS预授权查询"),
	/**
	 * 网上消费
	 */
	PAY_80("80", "网上消费"),
	/**
	 * 网上消费撤单
	 */
	PAY_81("81", "网上消费撤单"),
	/**
	 * POS预授权查询
	 */
	PAY_82("82", "POS预授权查询"),
	/**
	 * 网上消费冲正
	 */
	PAY_83("83", "网上消费冲正"),
	/**
	 * 网上消费撤单冲正
	 */
	PAY_84("84", "网上消费撤单冲正"),
	/**
	 * 网上消费退货
	 */
	PAY_85("85", "网上消费退货"),
	/**
	 * 网上消费退货冲正
	 */
	PAY_86("86", "网上消费退货冲正"),
	/**
	 * 退货查询
	 */
	PAY_71("71", "退货查询");
	private final String code;
	private final String desc;

	private PayCategory(String code, String desc) {
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
