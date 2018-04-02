package com.gl365.payment.dto.wx.response;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl365.payment.util.Gl365StrUtils;
public class WxPrePayRespDataDTO {
	/**
	 * 给乐流水号
	 */
	private String payId;
	/**
	 * 现金支付金额
	 */
	private BigDecimal cashAmount;
	private BigDecimal giftAmount;
	/**
	 * 抵扣金额
	 */
	private BigDecimal decAmount;
	private BigDecimal totalAmount;
	private BigDecimal beanAmount;
	private BigDecimal marcketFee;
	private BigDecimal coinAmount;
	private BigDecimal giftPoint;
	private BigDecimal merchantSettleAmount;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;
	private List<WxPrePayRespDetailDTO> details = new ArrayList<WxPrePayRespDetailDTO>();

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public BigDecimal getDecAmount() {
		return decAmount;
	}

	public void setDecAmount(BigDecimal decAmount) {
		this.decAmount = decAmount;
	}

	public List<WxPrePayRespDetailDTO> getDetails() {
		return details;
	}

	public void setDetails(List<WxPrePayRespDetailDTO> details) {
		this.details = details;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toMultiLineStr(this);
	}

	public BigDecimal getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(BigDecimal giftAmount) {
		this.giftAmount = giftAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getBeanAmount() {
		return beanAmount;
	}

	public void setBeanAmount(BigDecimal beanAmount) {
		this.beanAmount = beanAmount;
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

	public BigDecimal getMerchantSettleAmount() {
		return merchantSettleAmount;
	}

	public void setMerchantSettleAmount(BigDecimal merchantSettleAmount) {
		this.merchantSettleAmount = merchantSettleAmount;
	}
}
