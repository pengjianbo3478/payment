package com.gl365.payment.enums.mq;
/**
 * MQ 系统级类别
 * @author duanxz
 *2017年5月13日
 */
public enum SystemType {

		PAYMENT_SETTLE("payment_settle", "payment to settle"),
		PAYMENT_MERCHANT("payment_merchant", "payment to merchant"),
		PAYMENT_APP("payment_app", "payment to app"),
		PAYMENT_JPUSH("payment_app", "payment to jpush");

		private final String code;
		private final String desc;

		private SystemType(String key, String value) {
			this.code = key;
			this.desc = value;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}
	}