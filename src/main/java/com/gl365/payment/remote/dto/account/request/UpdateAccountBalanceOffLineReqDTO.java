package com.gl365.payment.remote.dto.account.request;
import java.io.Serializable;
import java.math.BigDecimal;
import com.gl365.payment.util.Gl365StrUtils;
public class UpdateAccountBalanceOffLineReqDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 会员ID
	 */
	private String userId;
	/**
	 * <p>FFT001:付费通</p>
	 * 支付渠道
	 */
	private String organCode;
	/**
	 * 支付流水号
	 */
	private String payId;
	/**
	 * 商户ID
	 */
	private String merchantNo;
	/**
	 * 商户名称
	 */
	private String merchantName;
	/**
	 * 商户订单号
	 */
	private String merchantOrderNo;
	/**
	 * 操作类型
	 */
	private String operateType;
	/**
	 * 操作金额
	 */
	private BigDecimal operateAmount;
	/**
	 * 返利金额
	 */
	private BigDecimal giftAmount;
	/**
	 * <p>D：借-减少C：贷-增加</p>
	 * 增减标记 
	 */
	private String dcType;
	private String scene;
	private String agentId;

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
	 * @return the organCode
	 */
	public String getOrganCode() {
		return organCode;
	}

	/**
	 * @param organCode the organCode to set
	 */
	public void setOrganCode(String organCode) {
		this.organCode = organCode;
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
	 * <p>原单消费乐豆金额</p>
	 * @return the operateAount
	 */
	public BigDecimal getOperateAmount() {
		return operateAmount;
	}

	/**
	 * <p>原单消费乐豆金额</p>
	 * @param operateAount the operateAount to set
	 */
	public void setOperateAmount(BigDecimal operateAmount) {
		this.operateAmount = operateAmount;
	}

	/**
	 * <p>返利金额</p>
	 * @return the giftAmount
	 */
	public BigDecimal getGiftAmount() {
		return giftAmount;
	}

	/**
	 * <p>返利金额</p>
	 * @param giftAmount the giftAmount to set
	 */
	public void setGiftAmount(BigDecimal giftAmount) {
		this.giftAmount = giftAmount;
	}

	/**
	 * <p>D：借-减少C：贷-增加</p>
	 * @return the dcType
	 */
	public String getDcType() {
		return dcType;
	}

	/**
	 * <p>D：借-减少C：贷-增加</p>
	 * @param dcType the dcType to set
	 */
	public void setDcType(String dcType) {
		this.dcType = dcType;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toStr(this);
	}

	/**
	 * 支付场景
	 * @return the scene
	 */
	public String getScene() {
		return scene;
	}

	/**
	 * 支付场景
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
}
