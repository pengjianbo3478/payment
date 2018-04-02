package com.gl365.payment.remote.dto.settlement.response;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl365.payment.util.JsonUtil;
public class ConfirmPreSettleDateRespDataDTO {
	/** 支付机构代码 */
	private String organCode;
	/** 给乐流水号 */
	private String payId;
	/** 交易类型 */
	private String transType;
	/**
	 * 待清算日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate preSettleDate;

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

	public LocalDate getPreSettleDate() {
		return preSettleDate;
	}

	public void setPreSettleDate(LocalDate preSettleDate) {
		this.preSettleDate = preSettleDate;
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
