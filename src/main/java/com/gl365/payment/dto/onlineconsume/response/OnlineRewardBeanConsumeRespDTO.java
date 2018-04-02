package com.gl365.payment.dto.onlineconsume.response;
import java.math.BigDecimal;
import com.gl365.payment.dto.base.response.BaseResponse;
import com.gl365.payment.util.Gl365StrUtils;
import java.io.Serializable;
public class OnlineRewardBeanConsumeRespDTO extends BaseResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 机构代码 ,最大长度15 ,不能为空 */
	private String organCode;
	/* 终端号 ,最大长度8 ,不能为空 */
	private String terminal;
	/* 商户号 ,最大长度15 ,不能为空 */
	private String merchantOrderNo;
	/* 支付用户ID*/
	private String payUserId;
	/* 消费金额 ,最大长度 ,不能为空 */
	private BigDecimal totalMoney;
	/* 乐币 ,最大长度 ,不能为空 */
	private BigDecimal coinAmount;
	/* 乐豆 ,最大长度 ,不能为空 */
	private BigDecimal beanAmount;
	/* 赠送金额 ,最大长度 ,不能为空 */
	private BigDecimal giftAmount;
	/* 交易日期 ,最大长度8 ,不能为空 */
	private String txnDate;

	public OnlineRewardBeanConsumeRespDTO(String payStatus, String payDesc) {
		super(payStatus, payDesc);
	}

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

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
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


	public BigDecimal getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(BigDecimal giftAmount) {
		this.giftAmount = giftAmount;
	}

	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public String getPayUserId() {
		return payUserId;
	}

	public void setPayUserId(String payUserId) {
		this.payUserId = payUserId;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toMultiLineStr(this);
	}
}