package com.gl365.payment.dto.onlineconsume.request;
import java.math.BigDecimal;
import com.gl365.payment.dto.base.request.BaseRequest;
import com.gl365.payment.util.Gl365StrUtils;
import java.io.Serializable;
public class OnlineConsumeReqDTO extends BaseRequest implements Serializable {
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
	/* 绑卡索引号 ,最大长度32 ,不能为空 */
	private String cardIndex;
	/* 消费金额 ,最大长度 ,不能为空 */
	private BigDecimal totalAmount;
	/**
	 * 场景	Ans5 00：直接支付（快捷支付）,01：B扫C支付,02：C扫B支付,03：POS
	 */
	private String scene = "00";
	/* 订单标题 ,最大长度128 ,不能为空 */
	private String merchantOrderTitle;
	/* 订单描述 ,最大长度512 ,不能为空 */
	private String merchantOrderDesc;
	/* 商户操作员编号 ,最大长度64 ,不能为空 */
	private String operator;
	/* 不可返利金额 ,最大长度 ,不能为空 */
	private BigDecimal noBenefitAmount;
	/* 给乐订单号 ,最大长度32 ,不能为空 */
	private String merchantOrderNo;
	/* 支付公司订单号 ,最大长度32 可空 */
	private String organOrderNo;
	/* 成本价*/
	private BigDecimal costPrice;
	/* 订单类型*/
	private String orderType;
	/* 群组id(达人活动ID)*/
	private String groupOrderId;
	/* 发起人应支付*/
	private BigDecimal groupMainUserPay;
	/* 分单标志*/
	private String splitFlag;
	/* 群支付给乐商家*/
	private String groupMerchantNo;

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

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

	public String getCardIndex() {
		return cardIndex;
	}

	public void setCardIndex(String cardIndex) {
		this.cardIndex = cardIndex;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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

	public String getOrganOrderNo() {
		return organOrderNo;
	}

	public void setOrganOrderNo(String organOrderNo) {
		this.organOrderNo = organOrderNo;
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

	public BigDecimal getGroupMainUserPay() {
		return groupMainUserPay;
	}

	public void setGroupMainUserPay(BigDecimal groupMainUserPay) {
		this.groupMainUserPay = groupMainUserPay;
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
}
