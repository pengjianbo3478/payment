package com.gl365.payment.dto.onlineconsume.request;
import java.math.BigDecimal;
import com.gl365.payment.util.Gl365StrUtils;
public class OnlineRewardConsumeReqDTO extends OnlineConsumeReqDTO {
	private static final long serialVersionUID = 1L;
	/**
	 * 被打赏员工	String	32	否
	 */
	private String rewardUserId; // 被打赏员工 String 32 否
	/**
	 * 被打赏原支付单号	String	32	否
	 */
	private String rewardPayId; // 被打赏原支付单号 String 32 否
	
	private BigDecimal payldmoney; // 支付乐豆

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
	
	public BigDecimal getPayldmoney() {
		return payldmoney;
	}

	public void setPayldmoney(BigDecimal payldmoney) {
		this.payldmoney = payldmoney;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toMultiLineStr(this);
	}
}
