package com.gl365.payment.remote.dto.account.request;
import java.io.Serializable;
public class QueryAccountTotalBalanceReqDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 会员ID
	 */
	private String userId;

	public QueryAccountTotalBalanceReqDTO(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "QueryAccountTotalBalanceReqDTO [userId=" + userId + "]";
	}
}
