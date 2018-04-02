package com.gl365.payment.dto.mgr.response;
import java.io.Serializable;
import com.github.pagehelper.PageInfo;
import com.gl365.payment.model.PayReturn;
public class QueryPagePayReturnRespDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String resultCode;
	private String resultDesc;
	private PageInfo<PayReturn> resultData;

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

	public PageInfo<PayReturn> getResultData() {
		return resultData;
	}

	public void setResultData(PageInfo<PayReturn> resultData) {
		this.resultData = resultData;
	}
}
