package com.gl365.payment.common.constants;
public interface PaymentConstants {
	/**
	 * 支付类型
	 * 00：现金
	 * 01：乐豆
	 * 02：机构积分
	 * 03：商户积分
	 * 04：红包
	 * 
	 * date: 2017年5月2日 上午11:40:50 <br/>
	 * @author lenovo
	 */
	public enum PayType {
		PAY_CASH("00", "现金"), PAY_BEAN("01", "乐豆"), PAY_OGRAN_POINTS("03", "机构积分"), PAY_MERCHANT_POINTS("03", "商户积分"), PAY_BRIBERY_MONEY("04", "红包");
		private final String code;
		private final String desc;

		private PayType(String code, String desc) {
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

	public enum Column {
		/**
		 * 
		 */
		organCode("organCode", "机构代码"),
		/**
		 * 
		 */
		requestId("requestId", "请求交易流水号"),
		/**
		 * 
		 */
		requestDate("requestDate", "请求交易日期"),
		/**
		 * 
		 */
		organMerchantNo("organMerchantNo", "商户号"),
		/**
		 * 
		 */
		terminal("terminal", "终端号"),
		/**
		 * 
		 */
		operator("operator", "操作员"),
		/**
		 * 
		 */
		cardIndex("cardIndex", "绑卡索引号"),
		/**
		 * 
		 */
		totalAmount("totalAmount", "消费金额/退货金额"),
		/**
		 * 
		 */
		origPayId("origPayId", "原网上消费交易流水号"),
		/**
		 * 
		 */
		origTxnDate("origTxnDate", "原交易日期"),
		/**
		 * 
		 */
		giftPoint("giftPoint", "赠送积分"),
		/**
		 * 
		 */
		marketFee("marketFee", "营销费"),
		/**
		 * 
		 */
		coinAmount("coinAmount", "乐币"),
		/**
		 * 
		 */
		beanAmount("beanAmount", "乐豆"),
		/**
		 * 
		 */
		cashMoney("cashMoney", "实扣金额"),
		/**
		 * 
		 */
		giftAmount("giftAmount", "赠送金额"),
		/**
		 * 
		 */
		merchantOrderTitle("merchantOrderTitle", "订单标题"),
		/**
		 * 
		 */
		merchantOrderDesc("merchantOrderDesc", "订单描述"),
		/**
		 * 
		 */
		noBenefitAmount("noBenefitAmount", "不可返利金额"),
		/**
		 * 
		 */
		merchantOrderNo("merchantOrderNo", "给乐订单号"),
		/**
		 * 
		 */
		payChannel("payChannel", "交易通道1POS ;2网上"),
		/**
		 * 
		 */
		rewardPayId("rewardPayId", "被打赏交易单号"),
		/**
		 * 
		 */
		rewardUserId("rewardUserId", "被打赏人"),
		/**
		 * 
		 */
		organOrderNo("organOrderNo", "支付公司订单号"),
		/**
		 * 支付用户
		 */
		payUserId("payUserId", "支付用户"),
		/**
		 * 支付用户
		 */
		payldmoney("payldmoney", "支付乐豆"),
		/**
		 * 群组Id
		 */
		groupOrderId("payldmoney", "群组Id"),
		/**
		 * 发起人应支付总额
		 */
		groupMainUserPay("payldmoney", "发起人应支付总额"),
		/**
		 * 分单标志
		 */
		splitFlag("payldmoney", "分单标志"),
		/**
		 * 群支付给乐商家
		 */
		groupMerchantNo("payldmoney", "群支付给乐商家"),
		;
		private final String code;
		private final String desc;

		private Column(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}
	}
}
