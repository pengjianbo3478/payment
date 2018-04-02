package com.gl365.payment.dto.consumeconfirm.response;
import java.io.Serializable;
import java.math.BigDecimal;
import com.gl365.payment.dto.base.response.BaseResponse;
import com.gl365.payment.util.Gl365StrUtils;
public class ConsumeConfirmRespDTO extends BaseResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	public ConsumeConfirmRespDTO(String payStatus, String payDesc) {
		super(payStatus, payDesc);
	}
	/** 机构代码 ,最大长度15 ,不能为空 */
	private String organCode;
	/** 商户号 ,最大长度15 ,不能为空 */
	private String organMerchantNo;
	/** 终端号 ,最大长度8 ,不能为空 */
	private String terminal;
	/** 原请求交易流水号 ,最大长度32 ,不能为空 */
	private String requestId;
	/** 绑卡索引号 ,最大长度32 ,不能为空 */
	private String cardIndex;
	/** 消费金额 ,最大长度 ,不能为空 */
	private BigDecimal totalMoney;
	/** 营销费 ,最大长度 ,不能为空 */
	private BigDecimal marketFee;
	/** 乐币 ,最大长度 ,不能为空 */
	private BigDecimal coinAmount;
	/** 乐豆 ,最大长度 ,不能为空 */
	private BigDecimal beanAmount;
	/** 实扣金额 ,最大长度 ,不能为空 */
	private BigDecimal cashMoney;
	/** 赠送金额 ,最大长度 ,不能为空 */
	private BigDecimal giftAmount;
	/** 赠送积分 ,最大长度 ,不能为空 */
	private BigDecimal giftPoint;
	/** 交易日期 ,最大长度8 ,不能为空 */
	private String txnDate;

	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
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

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getCardIndex() {
		return cardIndex;
	}

	public void setCardIndex(String cardIndex) {
		this.cardIndex = cardIndex;
	}

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

	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toMultiLineStr(this);
	}
}
