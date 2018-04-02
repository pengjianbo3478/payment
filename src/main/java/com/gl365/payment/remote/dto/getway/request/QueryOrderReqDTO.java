package com.gl365.payment.remote.dto.getway.request;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl365.payment.util.Gl365StrUtils;
public class QueryOrderReqDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String organMerchantNo;
	private String terminal;
	private BigDecimal totalAmount;
	private String cardIndex;
	private String requestId;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate requestDate;

	/**
	 * @return the organMerchantNo
	 */
	public String getOrganMerchantNo() {
		return organMerchantNo;
	}

	/**
	 * @param organMerchantNo the organMerchantNo to set
	 */
	public void setOrganMerchantNo(String organMerchantNo) {
		this.organMerchantNo = organMerchantNo;
	}

	/**
	 * @return the terminal
	 */
	public String getTerminal() {
		return terminal;
	}

	/**
	 * @param terminal the terminal to set
	 */
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	/**
	 * @return the totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the cardIndex
	 */
	public String getCardIndex() {
		return cardIndex;
	}

	/**
	 * @param cardIndex the cardIndex to set
	 */
	public void setCardIndex(String cardIndex) {
		this.cardIndex = cardIndex;
	}

	/**
	 * @return the requestId
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * @return the requestDate
	 */
	public LocalDate getRequestDate() {
		return requestDate;
	}

	/**
	 * @param requestDate the requestDate to set
	 */
	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}
	
	@Override
	public String toString() {
		return Gl365StrUtils.toMultiLineStr(this);
	}
}
