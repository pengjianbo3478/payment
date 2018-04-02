package com.gl365.payment.remote.dto.account.response;
import java.io.Serializable;
import com.gl365.payment.util.Gl365StrUtils;
public class BeanTransferRespDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String resultCode;
	private String resultDesc;

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

	@Override
	public String toString() {
		return Gl365StrUtils.toStr(this);
	}
}
