package com.gl365.payment.remote.dto.settlement.response;
import com.gl365.payment.util.JsonUtil;
public class ConfirmPreSettleDateRespDTO {
	/** 接口结果返回编码 */
	private String resultCode;
	/** 接口结果返回描述 */
	private String resultDesc;
	private ConfirmPreSettleDateRespDataDTO data;

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

	public ConfirmPreSettleDateRespDataDTO getData() {
		return data;
	}

	public void setData(ConfirmPreSettleDateRespDataDTO data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
