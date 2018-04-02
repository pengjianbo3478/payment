package com.gl365.payment.remote.dto.account.response;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl365.payment.util.Gl365StrUtils;
public class RespItemDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String accountId;
	private String userId;
	private String payId;
	private String merchantNo;
	private String merchantName;
	private String merchantOrderNo;
	private String operateType;
	/**
	 * 原金额
	 */
	private BigDecimal originalAmount;
	/**
	 * 借贷标志 D：借-减少 C：贷-增加
	 */
	private String dcType;
	/**
	 * 操作金额
	 */
	private BigDecimal operateAmount;
	/**
	 * 结余金额
	 */
	private BigDecimal balanceAmoung;
	private String createBy;
	private String scene;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;
	/**
	 * <p>支付机构代码 40001：给乐</p>
	 */
	private String agentId;
	/**
	 * <p>支付账户</p>
	 * <p>乐豆账户支付时：userId机构账户支付时：agentAccountNo</p>
	 */
	private String payAccount;
	/**
	 * <p>支付类型</p>
	 * <p>00：现金01：乐豆02：机构积分 03：商户积分04：红包</p>
	 */
	private String payType;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the payId
	 */
	public String getPayId() {
		return payId;
	}

	/**
	 * @param payId the payId to set
	 */
	public void setPayId(String payId) {
		this.payId = payId;
	}

	/**
	 * @return the merchantNo
	 */
	public String getMerchantNo() {
		return merchantNo;
	}

	/**
	 * @param merchantNo the merchantNo to set
	 */
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	/**
	 * @return the merchantName
	 */
	public String getMerchantName() {
		return merchantName;
	}

	/**
	 * @param merchantName the merchantName to set
	 */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	/**
	 * @return the merchantOrderNo
	 */
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	/**
	 * @param merchantOrderNo the merchantOrderNo to set
	 */
	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	/**
	 * @return the operateType
	 */
	public String getOperateType() {
		return operateType;
	}

	/**
	 * @param operateType the operateType to set
	 */
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	/**
	 * @return the originalAmount
	 */
	public BigDecimal getOriginalAmount() {
		return originalAmount;
	}

	/**
	 * @param originalAmount the originalAmount to set
	 */
	public void setOriginalAmount(BigDecimal originalAmount) {
		this.originalAmount = originalAmount;
	}

	/**
	 * @return the dcType
	 */
	public String getDcType() {
		return dcType;
	}

	/**
	 * @param dcType the dcType to set
	 */
	public void setDcType(String dcType) {
		this.dcType = dcType;
	}

	/**
	 * @return the operateAmount
	 */
	public BigDecimal getOperateAmount() {
		return operateAmount;
	}

	/**
	 * @param operateAmount the operateAmount to set
	 */
	public void setOperateAmount(BigDecimal operateAmount) {
		this.operateAmount = operateAmount;
	}

	/**
	 * @return the balanceAmoung
	 */
	public BigDecimal getBalanceAmoung() {
		return balanceAmoung;
	}

	/**
	 * @param balanceAmoung the balanceAmoung to set
	 */
	public void setBalanceAmoung(BigDecimal balanceAmoung) {
		this.balanceAmoung = balanceAmoung;
	}

	/**
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * @return the createTime
	 */
	public LocalDateTime getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toStr(this);
	}

	/**
	 * @return the scene
	 */
	public String getScene() {
		return scene;
	}

	/**
	 * @param scene the scene to set
	 */
	public void setScene(String scene) {
		this.scene = scene;
	}

	/**
	 * @return the agentId
	 */
	public String getAgentId() {
		return agentId;
	}

	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	/**
	 * @return the payType
	 */
	public String getPayType() {
		return payType;
	}

	/**
	 * @param payType the payType to set
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}

	/**
	 * @return the payAccount
	 */
	public String getPayAccount() {
		return payAccount;
	}

	/**
	 * @param payAccount the payAccount to set
	 */
	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}
}
