package com.gl365.payment.common;
/**
 * MQ的内容和日志
 * @author duanxz
 *2017年5月27日
 */
public class MqCell {
		private String mqContent;
		private String logBase;
		public String getMqContent() {
			return mqContent;
		}
		public void setMqContent(String mqContent) {
			this.mqContent = mqContent;
		}
		public String getLogBase() {
			return logBase;
		}
		public void setLogBase(String logBase) {
			this.logBase = logBase;
		}
		@Override
		public String toString() {
			return "MqCell [mqContent=" + mqContent + ", logBase=" + logBase + "]";
		}
		
	}