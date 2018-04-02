package com.gl365.payment.dto.wx.request;

import java.io.Serializable;
import java.math.BigDecimal;

import com.gl365.payment.util.Gl365StrUtils;

public class WxRewardReqDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 机构代码
	 */
	private String organCode;
	/**
	 * 请求交易日期
	 */
	private String requestDate;
	/**
	 * 给乐商户号
	 */
	private String merchantNo;
	/**
	 * 商户号
	 */
	private String organMerchantNo;
	/**
	 * 会员号
	 */
	private String userId;
	/**
	 * 消费金额
	 */
	private BigDecimal totalAmount;
	/**
	 * 订单标题
	 */
	private String merchantOrderTitle;
	/**
	 * 订单描述
	 */
	private String merchantOrderDesc;
	/**
	 * 不可返利金额
	 */
	private BigDecimal noBenefitAmount;
	/**
	 * 支付乐豆
	 */
	private BigDecimal payLdMoney;
	/**
	 * 商户订单号
	 */
	private String merchantOrderNo;
	/**
	 * 被打赏员工
	 */
	private String rewardUserId;
	/**
	 * 被打赏原支付单号
	 */
	private String rewardPayId;
	
	/**
	 * 支付场景
	 */
	private String scene;

	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getOrganMerchantNo() {
		return organMerchantNo;
	}

	public void setOrganMerchantNo(String organMerchantNo) {
		this.organMerchantNo = organMerchantNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getMerchantOrderTitle() {
		return merchantOrderTitle;
	}

	public void setMerchantOrderTitle(String merchantOrderTitle) {
		this.merchantOrderTitle = merchantOrderTitle;
	}

	public String getMerchantOrderDesc() {
		return merchantOrderDesc;
	}

	public void setMerchantOrderDesc(String merchantOrderDesc) {
		this.merchantOrderDesc = merchantOrderDesc;
	}

	public BigDecimal getNoBenefitAmount() {
		return noBenefitAmount;
	}

	public void setNoBenefitAmount(BigDecimal noBenefitAmount) {
		this.noBenefitAmount = noBenefitAmount;
	}

	public BigDecimal getPayLdMoney() {
		return payLdMoney;
	}

	public void setPayLdMoney(BigDecimal payLdMoney) {
		this.payLdMoney = payLdMoney;
	}

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	public String getRewardUserId() {
		return rewardUserId;
	}

	public void setRewardUserId(String rewardUserId) {
		this.rewardUserId = rewardUserId;
	}

	public String getRewardPayId() {
		return rewardPayId;
	}

	public void setRewardPayId(String rewardPayId) {
		this.rewardPayId = rewardPayId;
	}
	
	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toMultiLineStr(this);
	}
}
