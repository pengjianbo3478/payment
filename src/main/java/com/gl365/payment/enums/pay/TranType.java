package com.gl365.payment.enums.pay;
public enum TranType {
	/** 消费查询 */
	CONSUME_QUERY("0000", "POS消费查询"),
	/** 消费 */
	CONSUME_COMMIT("1000","POS消费确认"), 
	/** 消费冲正 */
	CONSUME_REVERSE("1001","POS消费冲正"),
	/** 网上消费 */
	ONLINE_CONSUME("1100","网上消费"), 
	/** 网上消费冲正 */
	ONLINE_CONSUME_REVERSE("1101","网上消费冲正"),
	/** 撤销 */
	CANCEL("2000","POS撤销"),
	/** 撤销冲正 */
	CANCEL_REVERSE("2001","POS撤销冲正"),
	/** 网上消费撤销 */
	ONLINE_CANCEL("2100","网上消费撤销"),
	/** 网上消费撤销冲正 */
	ONLINE_CANCEL_REVERSE("2101","网上消费撤销冲正"),
	/** 全额退货 */
	REFUND_ALL("3000","POS全额退货"),
	/** 部分退货 */
	REFUND_PART("3100", "POS部分退货"),
	/** 网上全额退货 */
	ONLINE_REFUND_ALL("3200","网上全额退货"),
	/** 网上部分退货 */
	ONLINE_REFUND_PART("3300", "网上部分退货"),
	/** 退货冲正 */
	REFUND_REVERSE("3001","POS退货冲正"),
	/** 预授权完成查询 */
	PRE_AUTH_CONSUME_QUERY("4000","POS预授权完成查询"),
	/** 预授权完成确认 */
	PRE_AUTH_CONSUME_CONFIRM("4100","POS预授权完成确认"),
	/** 预授权完成确认冲正 */
	PRE_AUTH_CONSUME_CONFIRM_REVERSE("4101","POS预授权完成确认冲正"),
	/** 退货查询*/
	REFUND_QUERY("3003","退货查询"),
	/** 返利 */
	GIFT("9000","返利"),
	/** 取消返利 */
	CANCEL_GIFT("9001","取消返利");
	
	private final String code;
	private final String desc;

	private TranType(String code, String desc) {
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
