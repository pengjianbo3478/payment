package com.gl365.payment.dto.wx.request;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl365.payment.util.Gl365StrUtils;
public class WxPrePayReqDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 机构代码
	 */
	private String organCode;
	/**
	 * 请求交易流水号
	 */
	private String requestId;
	/**
	 * 请求交易日期
	 */
	@JsonFormat(pattern = "yyyyMMdd")
	@DateTimeFormat(pattern = "yyyyMMdd")
	private LocalDate requestDate;
	/**
	 * 给乐商户号
	 */
	private String merchantNo;
	/**
	 * 融脉商户号
	 */
	private String organMerchantNo;
	/**
	 * 会员号
	 */
	private String userId;
	/**
	 * 现金支付金额
	 */
	private BigDecimal totalAmount;
	/**
	 * 支付场景
	 */
	private String scene;
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
	 * 商户订单号
	 */
	private String merchantOrderNo;
	/**
	 * 订单类型
	 */
	private String orderType;
	/**
	 *  群组id(达人活动ID)
	 */
	private String groupOrderId;
	/**
	 *  发起人应支付
	 */
	private BigDecimal groupMainuserPay;
	/** 
	 * 分单标志
	 */
	private String splitFlag;
	/** 
	 * 群支付给乐商家
	 */
	private String groupMerchantNo;
	
	private String operator;
	
	private String terminal;

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

	public LocalDate getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDate requestDate) {
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

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
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

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	public String getGroupOrderId() {
		return groupOrderId;
	}

	public void setGroupOrderId(String groupOrderId) {
		this.groupOrderId = groupOrderId;
	}

	public BigDecimal getGroupMainuserPay() {
		return groupMainuserPay;
	}

	public void setGroupMainuserPay(BigDecimal groupMainuserPay) {
		this.groupMainuserPay = groupMainuserPay;
	}

	public String getSplitFlag() {
		return splitFlag;
	}

	public void setSplitFlag(String splitFlag) {
		this.splitFlag = splitFlag;
	}

	public String getGroupMerchantNo() {
		return groupMerchantNo;
	}

	public void setGroupMerchantNo(String groupMerchantNo) {
		this.groupMerchantNo = groupMerchantNo;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toMultiLineStr(this);
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
}
