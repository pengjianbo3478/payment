package com.gl365.payment.dto.onlineconsume.request;
import com.gl365.payment.util.Gl365StrUtils;
public class OnlineRewardBeanConsumeReqDTO extends OnlineConsumeReqDTO {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 5纯乐豆打赏   6C对C纯乐豆支付
	 */
	private String orderType;
	/**
	 * 支付用户ID
	 */
	private String payUserId;
	/**
	 * 被打赏员工	String	32	否
	 */
	private String rewardUserId; // 被打赏员工 String 32 否
	/**
	 * 被打赏原支付单号	String	32	否
	 */
	private String rewardPayId; // 被打赏原支付单号 String 32 否

	public String getRewardUserId() {
		return rewardUserId;
	}

	public void setRewardUserId(String rewardUserId) {
		this.rewardUserId = rewardUserId;
	}

	public String getRewardPayId() {
		return rewardPayId;
	}

	public void setRewardPayId(String rewardPayId) {
		this.rewardPayId = rewardPayId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getPayUserId() {
		return payUserId;
	}

	public void setPayUserId(String payUserId) {
		this.payUserId = payUserId;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toMultiLineStr(this);
	}
}
