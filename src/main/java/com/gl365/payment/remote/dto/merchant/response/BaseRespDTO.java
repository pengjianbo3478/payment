package com.gl365.payment.remote.dto.merchant.response;
import java.io.Serializable;
import com.gl365.payment.util.Gl365StrUtils;
public class BaseRespDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String result;
	private String description;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toStr(this);
	}
}
