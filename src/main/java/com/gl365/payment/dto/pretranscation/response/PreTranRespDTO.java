package com.gl365.payment.dto.pretranscation.response;
import java.math.BigDecimal;
import com.gl365.payment.dto.base.response.PayRespDTO;
import com.gl365.payment.util.Gl365StrUtils;
public class PreTranRespDTO extends PayRespDTO {
	private static final long serialVersionUID = 1L;
	/**
	 * 原请求交易流水号
	 */
	private String origRequestId;
	/**
	 * 消费金额
	 */
	private BigDecimal totalMoney;
	/**
	 * 营销费
	 */
	private BigDecimal marketFee;
	/**
	 * 乐币
	 */
	private BigDecimal coinAmount;
	/**
	 * 乐豆
	 */
	private BigDecimal beanAmount;
	/**
	 * 实扣金额
	 */
	private BigDecimal cashMoney;
	/**
	 * 赠送金额
	 */
	private BigDecimal giftAmount;
	private BigDecimal giftPoint;

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public BigDecimal getMarketFee() {
		return marketFee;
	}

	public void setMarketFee(BigDecimal marketFee) {
		this.marketFee = marketFee;
	}

	public BigDecimal getCoinAmount() {
		return coinAmount;
	}

	public void setCoinAmount(BigDecimal coinAmount) {
		this.coinAmount = coinAmount;
	}

	public BigDecimal getBeanAmount() {
		return beanAmount;
	}

	public void setBeanAmount(BigDecimal beanAmount) {
		this.beanAmount = beanAmount;
	}

	public BigDecimal getCashMoney() {
		return cashMoney;
	}

	public void setCashMoney(BigDecimal cashMoney) {
		this.cashMoney = cashMoney;
	}

	public BigDecimal getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(BigDecimal giftAmount) {
		this.giftAmount = giftAmount;
	}

	public BigDecimal getGiftPoint() {
		return giftPoint;
	}

	public void setGiftPoint(BigDecimal giftPoint) {
		this.giftPoint = giftPoint;
	}

	public String getOrigRequestId() {
		return origRequestId;
	}

	public void setOrigRequestId(String origRequestId) {
		this.origRequestId = origRequestId;
	}

	public PreTranRespDTO() {
		super();
	}

	public PreTranRespDTO(String payStatus, String payDesc) {
		super(payStatus, payDesc);
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toStr(this);
	}
}
