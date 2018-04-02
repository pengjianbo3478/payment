package com.gl365.payment.remote.dto.settlement.request;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
public class CurrentPayProfitReqDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 给乐流水号 */
	private String payId;
	/** 支付机构代码 */
	private String organCode;
	/** 支付机构商户号 */
	private String organMerchantNo;
	/** 给乐商户号 */
	private String merchantNo;
	/** 给乐商户名称 */
	private String merchantName;
	/** 发展机构 */
	private String merchantAgentNo;
	/**
	 * 发展商户机构上级机构
	 */
	private String parentAgentNo;
	/**
	 * 推广业务员
	 */
	private String inviteAgentNo;
	/** 发展会员机构类型(发展会员机构类型
	0 ：总公司 00
	1：省级运营商
	2：市级运营商
	3：县级运营商
	4：业务员机构
	5：联盟商家商户
	6：员工,店长,会员
	7：企业协会机构
	8：积分机构
	9：电子商城
	10：积分商城
	11：政府机构
	12：银行机构
	13：支付公司) */
	private String userAgentType;
	/** 发展会员机构 */
	private String userAgentNo;
	/** 发展会员商家店长 */
	private String userDevManager;
	/** 发展会员商家员工 */
	private String userDevStaff;
	/** 商家所在省 */
	private int province;
	/** 商家所在市 */
	private int city;
	/** 商家所在区 */
	private int district;
	/** 交易类型 */
	private String transType;
	/** 订单类型 1：正常订单（如果订单标题解析出来为空、或者是POS交易，则默认为1） 2：打赏订单 3：达人订单 4：网购订单 */
	private String orderType;
	/** 商户订单号 */
	private String merchantOrderNo;
	/** 被打赏对象 */
	private String rewardUserId;
	/** 被打赏支付订单号 */
	private String rewardPayId;
	/** 会员ID */
	private String userId;
	/** 会员姓名 */
	private String userName;
	/** 交易总金额 */
	private BigDecimal totalAmount;
	/** 支付手续费 */
	private BigDecimal payFee;
	/** 实扣金额 */
	private BigDecimal cashAmount;
	/** 乐豆金额 */
	private BigDecimal beanAmount;
	/** 乐币金额 */
	private BigDecimal coinAmount;
	/** 返佣金额 */
	private BigDecimal commAmount;
	/** 营销费=返佣-支付手续费 */
	private BigDecimal marcketFee;
	/** 返利金额 */
	private BigDecimal giftAmount;
	/** 赠送积分 */
	private BigDecimal giftPoint;
	/** 商户实得金额 */
	private BigDecimal merchantSettleAmount;
	/** 支付确认时间（支付时为支付时间，退货时为退货时间） */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime payTime;
	/** 交易状态 */
	private String payStatus;
	/**
	 * 加盟方式 1：联明商家（默认）2：合伙商家
	 */
	private String joinType;
	/**
	 * 群成员本单返乐豆
	 */
	private BigDecimal groupGiftAmount;
	/**
	 * 分单标志
	 */
	private String splitFlag;
	/**
	 * 请求日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate requestDate;
	/**
	 * 清算商户
	 */
	private String settleMerchantNo;
	/**
	 * 总店
	 */
	private String parentMerchantNo;

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

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

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantAgentNo() {
		return merchantAgentNo;
	}

	public void setMerchantAgentNo(String merchantAgentNo) {
		this.merchantAgentNo = merchantAgentNo;
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

	public String getUserAgentType() {
		return userAgentType;
	}

	public void setUserAgentType(String userAgentType) {
		this.userAgentType = userAgentType;
	}

	public String getUserAgentNo() {
		return userAgentNo;
	}

	public void setUserAgentNo(String userAgentNo) {
		this.userAgentNo = userAgentNo;
	}

	public String getUserDevManager() {
		return userDevManager;
	}

	public void setUserDevManager(String userDevManager) {
		this.userDevManager = userDevManager;
	}

	public String getUserDevStaff() {
		return userDevStaff;
	}

	public void setUserDevStaff(String userDevStaff) {
		this.userDevStaff = userDevStaff;
	}

	public int getProvince() {
		return province;
	}

	public void setProvince(int province) {
		this.province = province;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public int getDistrict() {
		return district;
	}

	public void setDistrict(int district) {
		this.district = district;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getPayFee() {
		return payFee;
	}

	public void setPayFee(BigDecimal payFee) {
		this.payFee = payFee;
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

	public void setMerchantSettleAmount(BigDecimal merchantSettleAmount) {
		this.merchantSettleAmount = merchantSettleAmount;
	}

	public LocalDateTime getPayTime() {
		return payTime;
	}

	public void setPayTime(LocalDateTime payTime) {
		this.payTime = payTime;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getJoinType() {
		return joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	public BigDecimal getGroupGiftAmount() {
		return groupGiftAmount;
	}

	public void setGroupGiftAmount(BigDecimal groupGiftAmount) {
		this.groupGiftAmount = groupGiftAmount;
	}

	public String getSplitFlag() {
		return splitFlag;
	}

	public void setSplitFlag(String splitFlag) {
		this.splitFlag = splitFlag;
	}

	public LocalDate getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
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
}
