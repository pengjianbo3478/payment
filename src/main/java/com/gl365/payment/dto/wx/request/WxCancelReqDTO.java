package com.gl365.payment.dto.wx.request;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl365.payment.util.Gl365StrUtils;
public class WxCancelReqDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 机构代码
	 */
	private String organCode;
	/**
	 * 给乐退款订单号
	 */
	private String merchantOrderNo;
	/**
	 * 融脉退款订单号
	 */
	private String organOrderNo;
	/**
	 * 给乐原订单号
	 */
	private String origMerchantOrderNo;
	/**
	 * 融脉原订单号
	 */
	private String origOrganOrderNo;
	/**
	 * 交易金额
	 */
	private BigDecimal cashAmount;
	/**
	 * 融脉退货时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime organPayTime;
	/**
	 * 终端号
	 */
	private String terminal;
	/**
	 * 操作员
	 */
	private String operator;

	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	public String getOrganOrderNo() {
		return organOrderNo;
	}

	public void setOrganOrderNo(String organOrderNo) {
		this.organOrderNo = organOrderNo;
	}

	public String getOrigMerchantOrderNo() {
		return origMerchantOrderNo;
	}

	public void setOrigMerchantOrderNo(String origMerchantOrderNo) {
		this.origMerchantOrderNo = origMerchantOrderNo;
	}

	public String getOrigOrganOrderNo() {
		return origOrganOrderNo;
	}

	public void setOrigOrganOrderNo(String origOrganOrderNo) {
		this.origOrganOrderNo = origOrganOrderNo;
	}

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public LocalDateTime getOrganPayTime() {
		return organPayTime;
	}

	public void setOrganPayTime(LocalDateTime organPayTime) {
		this.organPayTime = organPayTime;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toMultiLineStr(this);
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
}
