package com.gl365.payment.dto.base;

public class CommonDTO {

	/** 接口结果返回编码 */
	private String resultCode;

	/** 接口结果返回描述 */
	private String resultDesc;

	/** 返回结果 */
	private Object resultData;
	
	public CommonDTO(String resultCode, String resultDesc, Object resultData) {
		this.resultCode=resultCode;
		this.resultDesc=resultDesc;
		this.resultData=resultData;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public Object getResultData() {
		return resultData;
	}

	public void setResultData(Object resultData) {
		this.resultData = resultData;
	}

}
