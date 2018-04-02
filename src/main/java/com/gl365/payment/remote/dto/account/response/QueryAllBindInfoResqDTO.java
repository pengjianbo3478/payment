package com.gl365.payment.remote.dto.account.response;
import java.io.Serializable;
import java.util.List;
import com.gl365.payment.util.Gl365StrUtils;
public class QueryAllBindInfoResqDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 返回码
	 */
	private String resultCode;
	/**
	 * 返回描述
	 */
	private String resultDesc;
	/**
	 * 返回结果集
	 */
	private List<QueryAllBindInfoResqItemDTO> bindList;

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

	public List<QueryAllBindInfoResqItemDTO> getBindList() {
		return bindList;
	}

	public void setBindList(List<QueryAllBindInfoResqItemDTO> bindList) {
		this.bindList = bindList;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toStr(this);
	}
}
