package com.gl365.payment.dto.wx.response;

import java.io.Serializable;
import com.gl365.payment.util.Gl365StrUtils;

public class WxRewardRespDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String resultCode;
	private String resultDesc;
	private WxPrePayRespDataDTO data;
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
	public WxPrePayRespDataDTO getData() {
		return data;
	}
	public void setData(WxPrePayRespDataDTO data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return Gl365StrUtils.toMultiLineStr(this);
	}
}
