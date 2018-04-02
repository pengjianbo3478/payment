package com.gl365.payment.remote.dto.account.request;
import java.io.Serializable;
import com.gl365.payment.util.Gl365StrUtils;
public class QueryAccountBalanceInfoReqDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 会员ID
	 */
	private String userId;
	/**
	 * 发行渠道
	 */
	private String agentId;

	public QueryAccountBalanceInfoReqDTO() {
		super();
	}

	public QueryAccountBalanceInfoReqDTO(String userId, String agentId) {
		super();
		this.userId = userId;
		this.agentId = agentId;
	}

	/**
	 * 会员ID
	 * @return
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 会员ID
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toStr(this);
	}

	/**
	 * @return the agentId
	 */
	public String getAgentId() {
		return agentId;
	}

	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
}
