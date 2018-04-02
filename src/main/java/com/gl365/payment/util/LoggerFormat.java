package com.gl365.payment.util;

import java.time.LocalDateTime;
import com.gl365.payment.enums.pay.TranType;

/**
 * 
 * @author duanxz
 *
 */
public class LoggerFormat {
	
	public enum ExecuteState {
		NEW("NEW", "新建"),
		BEG("BEG", "开始"),
		RUN("RUN", "运行"),
		SMQ("SMQ", "发送MQ"),
		END("END", "结束"),
		FAI("FAI", "失败"),
		SUC("SUC", "失败");
		
		private String code;
		private String desc;
		private ExecuteState(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
	}

	/**
	 * 
	 * @param tranType
	 * @param info
	 * @param step
	 * @param resultCode
	 * @param resultDesc
	 * @return
	 */
	@Deprecated
	public static String logTran(TranType tranType, String info, int step, ExecuteState state, String resultDesc) {
		return LocalDateTime.now() + "|" 
				+ tranType.getCode() + "|"
				+ info + "|"
				+ step + "|"
				+ state.getCode() + "|"
				+ resultDesc;
	}
	
	public static String logTranBase(TranType tranType, String payId, int step, ExecuteState state) {
		return (tranType != null ? tranType.getCode() : "----") + "|"
				+ payId + "|"
				+ step + "|"
				+ state.getCode();
	}
	
	public static String logTran(String logBase, String resultCode, String resultDesc, String info) {
		return LocalDateTime.now() + "|" 
				+ logBase + "|"
				+ resultCode + "|"
				+ resultDesc + "|"
				+ info;
	}
	
	public static String logTran(TranType tranType, String payId, int step, ExecuteState state, String resultCode, String resultDesc, String info) {
		return LocalDateTime.now() + "|" 
				+ logTranBase(tranType, payId, step, state) + "|"
				+ resultCode + "|"
				+ resultDesc + "|"
				+ info;
	}
}
