package com.gl365.payment.remote.dto.settlement.request;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
public class ConfirmPreSettleDateReqDTO {
	/** 支付机构代码 */
	private String organCode;
	/** 给乐流水号 */
	private String payId;
	/** 交易类型 */
	private String transType;
	/**
	 * 支付/退货完成时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime organPayTime;
	/**
	 * 原交易支付完成时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime origOrganPayTime;
	/**
	 * 原交易待清算日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate origPreSettleDate;

	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public LocalDateTime getOrganPayTime() {
		return organPayTime;
	}

	public void setOrganPayTime(LocalDateTime organPayTime) {
		this.organPayTime = organPayTime;
	}

	public LocalDateTime getOrigOrganPayTime() {
		return origOrganPayTime;
	}

	public void setOrigOrganPayTime(LocalDateTime origOrganPayTime) {
		this.origOrganPayTime = origOrganPayTime;
	}

	public LocalDate getOrigPreSettleDate() {
		return origPreSettleDate;
	}

	public void setOrigPreSettleDate(LocalDate origPreSettleDate) {
		this.origPreSettleDate = origPreSettleDate;
	}
}
