package com.gl365.payment.dto.consumeconfirm.request;
import java.math.BigDecimal;
import com.gl365.payment.dto.base.request.BaseRequest;
import com.gl365.payment.util.Gl365StrUtils;
import java.io.Serializable;
public class ConsumeConfirmReqDTO extends BaseRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 机构代码 ,最大长度15 ,不能为空 */
	private String organCode;
	/** 请求交易流水号 ,最大长度32 ,不能为空 */
	private String requestId;
	/** 请求交易日期 ,最大长度8 ,不能为空 */
	private String requestDate;
	/** 商户号 ,最大长度15 ,不能为空 */
	private String organMerchantNo;
	/** 终端号 ,最大长度8 ,不能为空 */
	private String terminal;
	/** 消费查询交易流水号 ,最大长度32 ,不能为空 */
	private String origPayId;
	/** 绑卡索引号 ,最大长度32 ,不能为空 */
	private String cardIndex;
	/** 消费金额 ,最大长度 ,不能为空 */
	private BigDecimal totalAmount;
	/**不可返利金额*/
	private BigDecimal noBenefitAmount;
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

	public String getOrigPayId() {
		return origPayId;
	}

	public void setOrigPayId(String origPayId) {
		this.origPayId = origPayId;
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

	public BigDecimal getNoBenefitAmount() {
		return noBenefitAmount;
	}

	public void setNoBenefitAmount(BigDecimal noBenefitAmount) {
		this.noBenefitAmount = noBenefitAmount;
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

	@Override
	public String toString() {
		return Gl365StrUtils.toMultiLineStr(this);
	}
}
