package com.gl365.payment.model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl365.payment.util.Gl365StrUtils;
public class PayReturn implements Serializable {
	private static final long serialVersionUID = 1L;
	private String payId;
	private String requestId;
	/** 请求交易日期 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate requestDate;
	private String origPayId;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate origPayDate;
	private String organCode;
	private String organMerchantNo;
	private String merchantNo;
	private String merchantName;
	private String terminal;
	private String operator;
	private String merchantAgentNo;
	private String userAgentType;
	private String userAgentNo;
	/**
	 * 发展会员商家店长
	 */
	private String userDevManager;
	/**
	 * 发展会员商家员工
	 */
	private String userDevStaff;
	/**
	 * 清算支付公司
	 */
	private String settleOrganNo;
	/**
	 * 发展商户机构上级机构
	 */
	private String parentAgentNo;
	/**
	 * 推广业务员
	 */
	private String inviteAgentNo;
	/**
	 * 会员手机
	 */
	private String userMobile;
	/**
	 * 支付卡号
	 */
	private String cardNo;
	private Short province;
	private Short city;
	private Short district;
	private String transType;
	private String userId;
	private String userName;
	private String cardIndex;
	private BigDecimal totalAmount;
	private BigDecimal noBenefitAmount;
	private BigDecimal cashAmount;
	private BigDecimal beanAmount;
	private BigDecimal coinAmount;
	/**
	 * 支付手续费类型（借贷）
	 */
	private String payFeeType;
	/**
	 * 支付手续费率
	 */
	private BigDecimal payFeeRate;
	/**
	 * 支付手续费上限值
	 */
	private BigDecimal maxPayFee;
	private BigDecimal payFee;
	private String commType;
	private BigDecimal commRate;
	private BigDecimal giftRate;
	private BigDecimal commAmount;
	private BigDecimal marcketFee;
	private BigDecimal giftAmount;
	private BigDecimal giftPoint;
	private BigDecimal merchantSettleAmount;
	/**
	 * 支付确认时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime payTime;
	private String payStatus;
	private String payDesc;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;
	private String createBy;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifyTime;
	private String modifyBy;
	private String merchantOrderNo;
	private String origMerchantOrderNo;
	private String organOrderNo;
	private String origOrganOrderNo;
	private String transactionId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime organPayTime;
	/**
	 * 加盟方式 1：联明商家（默认）2：合伙商家
	 */
	private String joinType;
	/**
	 * 群组单号或达人活动单号
	 */
	private String scene;
	private String orderType;
	private String merchantOrderTitle;
	private String merchentOrderDesc;
	private String rewardUserId;
	private String rewardPayId;
	private String groupOrderId;
	private String splitFlag;
	private BigDecimal groupMainuserPay;
	private BigDecimal groupPtPay;
	private BigDecimal groupMainuserPayBean;
	private BigDecimal groupGiftAmount;
	private String groupMerchantNo;
	private BigDecimal decAmount;
	/**
	 * 清算商户
	 */
	private String settleMerchantNo;
	/**
	 * 总店
	 */
	private String parentMerchantNo;
	private String organPayStatus;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime organReturnTime;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate preSettleDate;

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId == null ? null : payId.trim();
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId == null ? null : requestId.trim();
	}

	public String getOrigPayId() {
		return origPayId;
	}

	public void setOrigPayId(String origPayId) {
		this.origPayId = origPayId == null ? null : origPayId.trim();
	}

	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode == null ? null : organCode.trim();
	}

	public String getOrganMerchantNo() {
		return organMerchantNo;
	}

	public void setOrganMerchantNo(String organMerchantNo) {
		this.organMerchantNo = organMerchantNo == null ? null : organMerchantNo.trim();
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo == null ? null : merchantNo.trim();
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName == null ? null : merchantName.trim();
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal == null ? null : terminal.trim();
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator == null ? null : operator.trim();
	}

	public String getMerchantAgentNo() {
		return merchantAgentNo;
	}

	public void setMerchantAgentNo(String merchantAgentNo) {
		this.merchantAgentNo = merchantAgentNo == null ? null : merchantAgentNo.trim();
	}

	public String getUserAgentType() {
		return userAgentType;
	}

	public void setUserAgentType(String userAgentType) {
		this.userAgentType = userAgentType == null ? null : userAgentType.trim();
	}

	public String getUserAgentNo() {
		return userAgentNo;
	}

	public void setUserAgentNo(String userAgentNo) {
		this.userAgentNo = userAgentNo == null ? null : userAgentNo.trim();
	}

	public Short getProvince() {
		return province;
	}

	public void setProvince(Short province) {
		this.province = province;
	}

	public Short getCity() {
		return city;
	}

	public void setCity(Short city) {
		this.city = city;
	}

	public Short getDistrict() {
		return district;
	}

	public void setDistrict(Short district) {
		this.district = district;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType == null ? null : transType.trim();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getCardIndex() {
		return cardIndex;
	}

	public void setCardIndex(String cardIndex) {
		this.cardIndex = cardIndex == null ? null : cardIndex.trim();
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

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public BigDecimal getBeanAmount() {
		return beanAmount;
	}

	public void setBeanAmount(BigDecimal beanAmount) {
		this.beanAmount = beanAmount;
	}

	public BigDecimal getCoinAmount() {
		return coinAmount;
	}

	public void setCoinAmount(BigDecimal coinAmount) {
		this.coinAmount = coinAmount;
	}

	public String getPayFeeType() {
		return payFeeType;
	}

	public void setPayFeeType(String payFeeType) {
		this.payFeeType = payFeeType;
	}

	public BigDecimal getMaxPayFee() {
		return maxPayFee;
	}

	public void setMaxPayFee(BigDecimal maxPayFee) {
		this.maxPayFee = maxPayFee;
	}

	public BigDecimal getPayFeeRate() {
		return payFeeRate;
	}

	public void setPayFeeRate(BigDecimal payFeeRate) {
		this.payFeeRate = payFeeRate;
	}

	public BigDecimal getPayFee() {
		return payFee;
	}

	public void setPayFee(BigDecimal payFee) {
		this.payFee = payFee;
	}

	public String getCommType() {
		return commType;
	}

	public void setCommType(String commType) {
		this.commType = commType == null ? null : commType.trim();
	}

	public BigDecimal getCommRate() {
		return commRate;
	}

	public void setCommRate(BigDecimal commRate) {
		this.commRate = commRate;
	}

	public BigDecimal getGiftRate() {
		return giftRate;
	}

	public void setGiftRate(BigDecimal giftRate) {
		this.giftRate = giftRate;
	}

	public BigDecimal getCommAmount() {
		return commAmount;
	}

	public void setCommAmount(BigDecimal commAmount) {
		this.commAmount = commAmount;
	}

	public BigDecimal getMarcketFee() {
		return marcketFee;
	}

	public void setMarcketFee(BigDecimal marcketFee) {
		this.marcketFee = marcketFee;
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

	public BigDecimal getMerchantSettleAmount() {
		return merchantSettleAmount;
	}

	public void setMerchantSettleAmount(BigDecimal merchantSettelAmount) {
		this.merchantSettleAmount = merchantSettelAmount;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus == null ? null : payStatus.trim();
	}

	public String getPayDesc() {
		return payDesc;
	}

	public void setPayDesc(String payDesc) {
		this.payDesc = payDesc == null ? null : payDesc.trim();
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy == null ? null : createBy.trim();
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy == null ? null : modifyBy.trim();
	}

	public LocalDate getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}

	public LocalDate getOrigPayDate() {
		return origPayDate;
	}

	public void setOrigPayDate(LocalDate origPayDate) {
		this.origPayDate = origPayDate;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(LocalDateTime modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getUserDevManager() {
		return userDevManager;
	}

	public void setUserDevManager(String userDevManager) {
		this.userDevManager = userDevManager == null ? null : userDevManager.trim();
	}

	public String getUserDevStaff() {
		return userDevStaff;
	}

	public void setUserDevStaff(String userDevStaff) {
		this.userDevStaff = userDevStaff == null ? null : userDevStaff.trim();
	}

	public String getSettleOrganNo() {
		return settleOrganNo;
	}

	public void setSettleOrganNo(String settleOrganNo) {
		this.settleOrganNo = settleOrganNo;
	}

	public String getParentAgentNo() {
		return parentAgentNo;
	}

	public void setParentAgentNo(String parentAgentNo) {
		this.parentAgentNo = parentAgentNo;
	}

	public String getInviteAgentNo() {
		return inviteAgentNo;
	}

	public void setInviteAgentNo(String inviteAgentNo) {
		this.inviteAgentNo = inviteAgentNo;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public LocalDateTime getPayTime() {
		return payTime;
	}

	public void setPayTime(LocalDateTime payTime) {
		this.payTime = payTime;
	}

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	public String getOrigMerchantOrderNo() {
		return origMerchantOrderNo;
	}

	public void setOrigMerchantOrderNo(String origMerchantOrderNo) {
		this.origMerchantOrderNo = origMerchantOrderNo;
	}

	public String getOrganOrderNo() {
		return organOrderNo;
	}

	public void setOrganOrderNo(String organOrderNo) {
		this.organOrderNo = organOrderNo;
	}

	public String getOrigOrganOrderNo() {
		return origOrganOrderNo;
	}

	public void setOrigOrganOrderNo(String origOrganOrderNo) {
		this.origOrganOrderNo = origOrganOrderNo;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public LocalDateTime getOrganPayTime() {
		return organPayTime;
	}

	public void setOrganPayTime(LocalDateTime organPayTime) {
		this.organPayTime = organPayTime;
	}

	public String getJoinType() {
		return joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getMerchantOrderTitle() {
		return merchantOrderTitle;
	}

	public void setMerchantOrderTitle(String merchantOrderTitle) {
		this.merchantOrderTitle = merchantOrderTitle;
	}

	public String getMerchentOrderDesc() {
		return merchentOrderDesc;
	}

	public void setMerchentOrderDesc(String merchentOrderDesc) {
		this.merchentOrderDesc = merchentOrderDesc;
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

	public String getGroupOrderId() {
		return groupOrderId;
	}

	public void setGroupOrderId(String groupOrderId) {
		this.groupOrderId = groupOrderId;
	}

	public String getSplitFlag() {
		return splitFlag;
	}

	public void setSplitFlag(String splitFlag) {
		this.splitFlag = splitFlag;
	}

	public BigDecimal getGroupMainuserPay() {
		return groupMainuserPay;
	}

	public void setGroupMainuserPay(BigDecimal groupMainuserPay) {
		this.groupMainuserPay = groupMainuserPay;
	}

	public BigDecimal getGroupPtPay() {
		return groupPtPay;
	}

	public void setGroupPtPay(BigDecimal groupPtPay) {
		this.groupPtPay = groupPtPay;
	}

	public BigDecimal getGroupMainuserPayBean() {
		return groupMainuserPayBean;
	}

	public void setGroupMainuserPayBean(BigDecimal groupMainuserPayBean) {
		this.groupMainuserPayBean = groupMainuserPayBean;
	}

	public BigDecimal getGroupGiftAmount() {
		return groupGiftAmount;
	}

	public void setGroupGiftAmount(BigDecimal groupGiftAmount) {
		this.groupGiftAmount = groupGiftAmount;
	}

	public String getGroupMerchantNo() {
		return groupMerchantNo;
	}

	public void setGroupMerchantNo(String groupMerchantNo) {
		this.groupMerchantNo = groupMerchantNo;
	}

	public BigDecimal getDecAmount() {
		return decAmount;
	}

	public void setDecAmount(BigDecimal decAmount) {
		this.decAmount = decAmount;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toMultiLineStr(this);
	}

	public String getSettleMerchantNo() {
		return settleMerchantNo;
	}

	public void setSettleMerchantNo(String settleMerchantNo) {
		this.settleMerchantNo = settleMerchantNo;
	}

	public String getParentMerchantNo() {
		return parentMerchantNo;
	}

	public void setParentMerchantNo(String parentMerchantNo) {
		this.parentMerchantNo = parentMerchantNo;
	}

	public String getOrganPayStatus() {
		return organPayStatus;
	}

	public void setOrganPayStatus(String organPayStatus) {
		this.organPayStatus = organPayStatus;
	}

	public LocalDateTime getOrganReturnTime() {
		return organReturnTime;
	}

	public void setOrganReturnTime(LocalDateTime organReturnTime) {
		this.organReturnTime = organReturnTime;
	}

	public LocalDate getPreSettleDate() {
		return preSettleDate;
	}

	public void setPreSettleDate(LocalDate preSettleDate) {
		this.preSettleDate = preSettleDate;
	}
}