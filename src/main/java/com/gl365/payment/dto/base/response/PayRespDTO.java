package com.gl365.payment.dto.base.response;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
public class PayRespDTO extends BaseResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 机构代码
	 */
	private String organCode;
	/**
	 * 商户号
	 */
	private String organMerchantNo;
	/**
	 * 终端号
	 */
	private String terminal;
	/**
	 * 绑卡索引号
	 */
	private String cardIndex;
	/**
	 * 交易日期
	 */
	@JsonFormat(pattern = "yyyyMMdd")
	@DateTimeFormat(pattern = "yyyyMMdd")
	private LocalDate txnDate;

	public LocalDate getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(LocalDate txnDate) {
		this.txnDate = txnDate;
	}

	public String getCardIndex() {
		return cardIndex;
	}

	public void setCardIndex(String cardIndex) {
		this.cardIndex = cardIndex;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getOrganMerchantNo() {
		return organMerchantNo;
	}

	public void setOrganMerchantNo(String organMerchantNo) {
		this.organMerchantNo = organMerchantNo;
	}

	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	public PayRespDTO(String payStatus, String payDesc) {
		super(payStatus, payDesc);
	}

	public PayRespDTO() {
		super();
	}
}
