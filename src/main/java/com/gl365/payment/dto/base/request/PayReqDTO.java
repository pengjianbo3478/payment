package com.gl365.payment.dto.base.request;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
public class PayReqDTO extends BaseRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String organCode;
	private String requestId;
	@JsonFormat(pattern = "yyyyMMdd")
	@DateTimeFormat(pattern = "yyyyMMdd")
	private LocalDate requestDate;
	private String terminal;
	private String cardIndex;
	private BigDecimal totalAmount;
	private String operator;
	private String organMerchantNo;

	/**
	 * <p>商户号	String	15	否</p>
	 * @return the organMerchantNo
	 */
	public String getOrganMerchantNo() {
		return organMerchantNo;
	}

	/**
	 * <p>商户号	String	15	否</p>
	 * @param organMerchantNo the organMerchantNo to set
	 */
	public void setOrganMerchantNo(String organMerchantNo) {
		this.organMerchantNo = organMerchantNo;
	}

	/**
	 * <p>商户操作员编号	String	64	是</p>
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * <p>商户操作员编号	String	64	是</p>
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * <p>消费金额	BigDecimal		否</p>
	 * @return the totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * <p></p>
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * <p>绑卡索引号	String	32	否</p>
	 * @return the cardIndex
	 */
	public String getCardIndex() {
		return cardIndex;
	}

	/**
	 * <p>绑卡索引号	String	32	否</p>
	 * @param cardIndex the cardIndex to set
	 */
	public void setCardIndex(String cardIndex) {
		this.cardIndex = cardIndex;
	}

	/**
	 * <p>终端号	String	8	是</p>
	 * @return the terminal
	 */
	public String getTerminal() {
		return terminal;
	}

	/**
	 * <p>终端号	String	8	是</p>
	 * @param terminal the terminal to set
	 */
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	/**
	 * <p>机构代码	String	15	否</p>
	 * @return the organCode
	 */
	public String getOrganCode() {
		return organCode;
	}

	/**
	 * <p>机构代码	String	15	否</p>
	 * @param organCode the organCode to set
	 */
	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	/**
	 * <p>请求交易流水号	String	32	否</p>
	 * @return the requestId
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * <p>请求交易流水号	String	32	否</p>
	 * @param requestId the requestId to set
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * <p>请求交易日期	String	8	否</p>
	 * @return the requestDate
	 */
	public LocalDate getRequestDate() {
		return requestDate;
	}

	/**
	 * <p>请求交易日期	String	8	否</p>
	 * @param requestDate the requestDate to set
	 */
	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}
}
