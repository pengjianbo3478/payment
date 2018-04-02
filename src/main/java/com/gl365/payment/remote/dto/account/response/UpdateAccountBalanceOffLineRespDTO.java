package com.gl365.payment.remote.dto.account.response;
import java.io.Serializable;
import java.util.List;
import com.gl365.payment.util.Gl365StrUtils;
public class UpdateAccountBalanceOffLineRespDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String resultCode;
	private String resultDesc;
	private List<RespItemDTO> resultData;

	/**
	 * @return the resultCode
	 */
	public String getResultCode() {
		return resultCode;
	}

	/**
	 * @param resultCode the resultCode to set
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * @return the resultDesc
	 */
	public String getResultDesc() {
		return resultDesc;
	}

	/**
	 * @param resultDesc the resultDesc to set
	 */
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	/**
	 * @return the resultData
	 */
	public List<RespItemDTO> getResultData() {
		return resultData;
	}

	/**
	 * @param resultData the resultData to set
	 */
	public void setResultData(List<RespItemDTO> resultData) {
		this.resultData = resultData;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toMultiLineStr(this);
	}
}
