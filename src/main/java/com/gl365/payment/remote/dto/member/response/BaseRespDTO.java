package com.gl365.payment.remote.dto.member.response;
import java.io.Serializable;
import com.gl365.payment.util.Gl365StrUtils;
public class BaseRespDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 返回码
	 */
	private int result;
	/**
	 * description
	 */
	private String description;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
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
