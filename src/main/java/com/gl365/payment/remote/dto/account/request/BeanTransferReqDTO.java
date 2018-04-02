package com.gl365.payment.remote.dto.account.request;
import java.io.Serializable;
import java.math.BigDecimal;
import com.gl365.payment.util.Gl365StrUtils;
public class BeanTransferReqDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	// 转出会员ID
	private String fromUser;
	// 转入会员ID
	private String toUser;
	// 支付流水号
	private String payId;
	// 操作类型 打赏：6010 转账：6020
	private String operateType;
	// 转账金额
	private BigDecimal operateAmount;
	// 操作人
	private String operateBy;

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public BigDecimal getOperateAmount() {
		return operateAmount;
	}

	public void setOperateAmount(BigDecimal operateAmount) {
		this.operateAmount = operateAmount;
	}

	public String getOperateBy() {
		return operateBy;
	}

	public void setOperateBy(String operateBy) {
		this.operateBy = operateBy;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toStr(this);
	}
}
