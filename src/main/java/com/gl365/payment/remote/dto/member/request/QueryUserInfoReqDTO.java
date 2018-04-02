package com.gl365.payment.remote.dto.member.request;
import java.io.Serializable;
import com.gl365.payment.util.Gl365StrUtils;
public class QueryUserInfoReqDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toStr(this);
	}
}
