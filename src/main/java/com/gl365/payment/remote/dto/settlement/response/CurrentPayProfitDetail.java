package com.gl365.payment.remote.dto.settlement.response;
import java.io.Serializable;
import java.math.BigDecimal;
public class CurrentPayProfitDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 子订单号
	 * 提成明细：pay_id + “_”+“Y”+“_”+id
	 * 商户实得金额：pay_id + “_”+“M”+“_”+商户号
	 */
	private String innerOrderNo;
	/**
	 * 收款账号  提成对象或者商户号
	 */
	private String virAccNo;
	/**
	 * 子订单金额  对象提成金额
	 */
	private BigDecimal amount;
	/**
	 * 冻结天数  默认为0
	 */
	private int freezeDays;

	public String getInnerOrderNo() {
		return innerOrderNo;
	}

	public void setInnerOrderNo(String innerOrderNo) {
		this.innerOrderNo = innerOrderNo;
	}

	public String getVirAccNo() {
		return virAccNo;
	}

	public void setVirAccNo(String virAccNo) {
		this.virAccNo = virAccNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getFreezeDays() {
		return freezeDays;
	}

	public void setFreezeDays(int freezeDays) {
		this.freezeDays = freezeDays;
	}
}
