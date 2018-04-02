package com.gl365.payment.remote.dto.account.request;
import java.io.Serializable;
import com.gl365.payment.util.Gl365StrUtils;
public class HasNormalBindInfoReqDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 会员ID
	 */
	private String userId;
	/**
	 * 支付渠道
	 */
	private String organCode;

	public HasNormalBindInfoReqDTO(String userId, String organCode) {
		super();
		this.userId = userId;
		this.organCode = organCode;
	}

	public HasNormalBindInfoReqDTO() {
		super();
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

	/**
	 * 支付渠道
	 * @return
	 */
	public String getOrganCode() {
		return organCode;
	}

	/**
	 * 支付渠道
	 * @param organCode
	 */
	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toStr(this);
	}
}
