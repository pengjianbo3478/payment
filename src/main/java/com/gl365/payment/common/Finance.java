package com.gl365.payment.common;
import java.io.Serializable;
import java.math.BigDecimal;
import com.gl365.payment.enums.pay.DealStatus;
public class Finance implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 本次消费金额(=银行卡支付金额 　＋　乐豆支付　＋　乐币支付)
	 */
	private BigDecimal totalAmount = BigDecimal.ZERO;
	/**
	 * 本次消费金额(银行卡支付)
	 */
	private BigDecimal cashAmount = BigDecimal.ZERO;
	/**
	 * 本次消费乐豆
	 */
	private BigDecimal beanAmount = BigDecimal.ZERO;
	/**
	 * 返利金额
	 */
	private BigDecimal giftAmount = BigDecimal.ZERO;
	/**
	 * 营销费
	 */
	private BigDecimal marcketFee = BigDecimal.ZERO;
	/**
	 * 乐币
	 */
	private BigDecimal coinAmount = BigDecimal.ZERO;
	/**
	 * 赠送积分
	 */
	private BigDecimal giftPoint = BigDecimal.ZERO;
	/**
	 * 支付手续费
	 */
	private BigDecimal payFee = BigDecimal.ZERO;
	/**
	 * 手续费费率
	 */
	private BigDecimal feeRate = BigDecimal.ZERO;
	/**
	 * 手续费封顶值
	 */
	private BigDecimal maxAmt = BigDecimal.ZERO;
	
	/**
	 * 商户实得金额
	 */
	private BigDecimal merchantSettlAmount = BigDecimal.ZERO;
	
	/**
	 * 返佣金额
	 */
	private BigDecimal commAmount = BigDecimal.ZERO;
	
	/**
	 * 本次退货是全额还是部分
	 */
	private DealStatus refundType;
	
	/**
	 * 原单是全额还是部分
	 */
	private DealStatus origRefundType;

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public BigDecimal getBeanAmount() {
		return beanAmount;
	}

	public void setBeanAmount(BigDecimal beanAmount) {
		this.beanAmount = beanAmount;
	}

	public BigDecimal getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(BigDecimal giftAmount) {
		this.giftAmount = giftAmount;
	}

	public BigDecimal getMarcketFee() {
		return marcketFee;
	}

	public void setMarcketFee(BigDecimal marcketFee) {
		this.marcketFee = marcketFee;
	}

	public BigDecimal getCoinAmount() {
		return coinAmount;
	}

	public void setCoinAmount(BigDecimal coinAmount) {
		this.coinAmount = coinAmount;
	}

	public BigDecimal getGiftPoint() {
		return giftPoint;
	}

	public void setGiftPoint(BigDecimal giftPoint) {
		this.giftPoint = giftPoint;
	}

	public BigDecimal getPayFee() {
		return payFee;
	}

	public void setPayFee(BigDecimal payFee) {
		this.payFee = payFee;
	}

	public BigDecimal getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}

	public BigDecimal getMaxAmt() {
		return maxAmt;
	}

	public void setMaxAmt(BigDecimal maxAmt) {
		this.maxAmt = maxAmt;
	}

	public BigDecimal getMerchantSettlAmount() {
		return merchantSettlAmount;
	}

	public void setMerchantSettlAmount(BigDecimal merchantSettlAmount) {
		this.merchantSettlAmount = merchantSettlAmount;
	}

	public BigDecimal getCommAmount() {
		return commAmount;
	}

	public void setCommAmount(BigDecimal commAmount) {
		this.commAmount = commAmount;
	}

	public DealStatus getRefundType() {
		return refundType;
	}

	public void setRefundType(DealStatus refundType) {
		this.refundType = refundType;
	}

	public DealStatus getOrigRefundType() {
		return origRefundType;
	}

	public void setOrigRefundType(DealStatus origRefundType) {
		this.origRefundType = origRefundType;
	}

	@Override
	public String toString() {
		return "Finance [totalAmount=" + totalAmount + ", cashAmount=" + cashAmount + ", beanAmount=" + beanAmount
				+ ", giftAmount=" + giftAmount + ", marcketFee=" + marcketFee + ", coinAmount=" + coinAmount
				+ ", giftPoint=" + giftPoint + ", payFee=" + payFee + ", feeRate=" + feeRate + ", maxAmt=" + maxAmt
				+ ", merchantSettlAmount=" + merchantSettlAmount + ", commAmount=" + commAmount + ", refundType="
				+ refundType + ", origRefundType=" + origRefundType + "]";
	}
	
}
