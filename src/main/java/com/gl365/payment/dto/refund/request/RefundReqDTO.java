package com.gl365.payment.dto.refund.request;
import java.io.Serializable;
import java.math.BigDecimal;
import com.gl365.payment.dto.base.request.BaseRequest;
import com.gl365.payment.util.Gl365StrUtils;
public class RefundReqDTO extends BaseRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 机构代码 ,最大长度15 ,不能为空 */
	private String organCode;
	/* 请求交易流水号 ,最大长度32 ,不能为空 */
	private String requestId;
	/* 请求交易日期 ,最大长度8 ,不能为空 */
	private String requestDate;
	/* 商户号 ,最大长度15 ,不能为空 */
	private String organMerchantNo;
	/* 终端号 ,最大长度8 ,不能为空 */
	private String terminal;
	/* 商户操作员编号 ,最大长度64 ,不能为空 */
	private String operator;
	/* 绑卡索引号 ,最大长度32 ,不能为空 */
	private String cardIndex;
	/* 退货金额 ,最大长度 ,不能为空 */
	private BigDecimal totalAmount;
	/* 原网上消费交易流水号 ,最大长度32 ,不能为空 */
	private String origPayId;
	/* 原交易日期 ,最大长度8 ,不能为空 */
	private String origTxnDate;
	/**交易通道 1POS ;2网上*/
	private String payChannel;

	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public String getOrganMerchantNo() {
		return organMerchantNo;
	}

	public void setOrganMerchantNo(String organMerchantNo) {
		this.organMerchantNo = organMerchantNo;
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

	public String getCardIndex() {
		return cardIndex;
	}

	public void setCardIndex(String cardIndex) {
		this.cardIndex = cardIndex;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getOrigPayId() {
		return origPayId;
	}

	public void setOrigPayId(String origPayId) {
		this.origPayId = origPayId;
	}

	public String getOrigTxnDate() {
		return origTxnDate;
	}

	public void setOrigTxnDate(String origTxnDate) {
		this.origTxnDate = origTxnDate;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toStr(this);
	}
}
