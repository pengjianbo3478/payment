package com.gl365.payment.remote.dto.merchant.response;
import java.math.BigDecimal;
import com.gl365.payment.util.JsonUtil;
public class QueryMerchantInfoRespDTOBody {
	/**
	 * 商家状态:0正常1禁止2暂停3注销
	 */
	private String status;
	/**
	 * 给乐商户号
	 */
	private String merchantNo;
	/**
	 * 商家简称
	 */
	private String merchantShortname;
	/**
	 * 返利率
	 */
	private BigDecimal saleRate;
	/**
	 * 返佣率
	 */
	private BigDecimal glFeeRate;
	/**
	 * 返佣方式:0按固定返佣,1按商品成本折算
	 */
	private String glFeeType;
	/**
	 * POS借记卡手续费费率
	 */
	private BigDecimal posDebitFeeRate;
	/**
	 * POS借记卡手续费封顶值
	 */
	private BigDecimal posDebitMaxAmt;
	/**
	 * POS贷记卡手续费费率
	 */
	private BigDecimal posCreditFeeRate;
	/**
	 * POS贷记卡手续费封顶值
	 */
	private BigDecimal posCreditMaxAmt;
	/**
	 * 在线支付借记卡手续费率
	 */
	private BigDecimal onpayDebitFeeRate;
	/**
	 * 在线支付借记卡手续费封顶值
	 */
	private BigDecimal onpayDebitMaxAmt;
	/**
	 * 在线支付贷记卡手续费率
	 */
	private BigDecimal onpayCreditFeeRate;
	/**
	 * 在线支付贷记卡手续费封顶值
	 */
	private BigDecimal onpayCreditMaxAmt;
	/**
	 * 允许乐豆支付:0:不允许,1:允许
	 */
	private String ldSale;
	/**
	 * 发展机构
	 */
	private String agentNo;
	/**
	 * 商家所在省
	 */
	private Short province;
	/**
	 * 商家所在市
	 */
	private Short city;
	/**
	 * 商家所在区域
	 */
	private Short district;
	/**
	 * 商家所在区域代理商
	 */
	private String distAgent;
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
	 * 在线支付 0线下支付线上支付，1线下支付，2线上支付
	 */
	private String onLinePayment;
	/**
	 * 上级商户号
	 */
	private String parentMerchantNo;
	/**
	 * 结算商户号
	 */
	private String settleMerchant;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 给乐商户号
	 * @return
	 */
	public String getMerchantNo() {
		return merchantNo;
	}

	/**
	 * 给乐商户号
	 * @param merchantNo
	 */
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	/**
	 * 商家简称
	 * @return
	 */
	public String getMerchantShortname() {
		return merchantShortname;
	}

	/**
	 * 商家简称
	 * @param merchantShortname
	 */
	public void setMerchantShortname(String merchantShortname) {
		this.merchantShortname = merchantShortname;
	}

	/**
	 * 返利率
	 * @return
	 */
	public BigDecimal getSaleRate() {
		return saleRate;
	}

	/**
	 * 返利率
	 * @param saleRate
	 */
	public void setSaleRate(BigDecimal saleRate) {
		this.saleRate = saleRate;
	}

	/**
	 * 返佣率
	 * @return
	 */
	public BigDecimal getGlFeeRate() {
		return glFeeRate;
	}

	/**
	 * 返佣率
	 * @param glFeeRate
	 */
	public void setGlFeeRate(BigDecimal glFeeRate) {
		this.glFeeRate = glFeeRate;
	}

	/**
	 * 返佣方式:0按固定返佣,1按商品成本折算
	 * @return
	 */
	public String getGlFeeType() {
		return glFeeType;
	}

	/**
	 * 返佣方式:0按固定返佣,1按商品成本折算
	 * @param glFeeType
	 */
	public void setGlFeeType(String glFeeType) {
		this.glFeeType = glFeeType;
	}

	/**
	 * POS借记卡手续费率
	 * @return
	 */
	public BigDecimal getPosDebitFeeRate() {
		return posDebitFeeRate;
	}

	/**
	 * POS借记卡手续费率
	 * @param posDebitFeeRate
	 */
	public void setPosDebitFeeRate(BigDecimal posDebitFeeRate) {
		this.posDebitFeeRate = posDebitFeeRate;
	}

	/**
	 * POS借记卡手续费封顶值
	 * @return
	 */
	public BigDecimal getPosDebitMaxAmt() {
		return posDebitMaxAmt;
	}

	/**
	 * POS借记卡手续费封顶值
	 * @param posDebitMaxAmt
	 */
	public void setPosDebitMaxAmt(BigDecimal posDebitMaxAmt) {
		this.posDebitMaxAmt = posDebitMaxAmt;
	}

	/**
	 * POS贷记卡手续费封顶值
	 * @return
	 */
	public BigDecimal getPosCreditMaxAmt() {
		return posCreditMaxAmt;
	}

	/**
	 * POS贷记卡手续费封顶值
	 * @param posCreditMaxAmt
	 */
	public void setPosCreditMaxAmt(BigDecimal posCreditMaxAmt) {
		this.posCreditMaxAmt = posCreditMaxAmt;
	}

	/**
	 * 在线支付借记卡手续费率
	 * @return
	 */
	public BigDecimal getOnpayDebitFeeRate() {
		return onpayDebitFeeRate;
	}

	/**
	 * 在线支付借记卡手续费率
	 * @param onpayDebitFeeRate
	 */
	public void setOnpayDebitFeeRate(BigDecimal onpayDebitFeeRate) {
		this.onpayDebitFeeRate = onpayDebitFeeRate;
	}

	/**
	 * 在线支付贷记卡手续费率
	 * @return
	 */
	public BigDecimal getOnpayCreditFeeRate() {
		return onpayCreditFeeRate;
	}

	/**
	 * 在线支付贷记卡手续费率
	 * @param onpayCreditFeeRate
	 */
	public void setOnpayCreditFeeRate(BigDecimal onpayCreditFeeRate) {
		this.onpayCreditFeeRate = onpayCreditFeeRate;
	}

	/**
	 * 在线支付贷记卡手续费封顶值
	 * @return
	 */
	public BigDecimal getOnpayCreditMaxAmt() {
		return onpayCreditMaxAmt;
	}

	/**
	 * 在线支付贷记卡手续费封顶值
	 * @param onpayCreditMaxAmt
	 */
	public void setOnpayCreditMaxAmt(BigDecimal onpayCreditMaxAmt) {
		this.onpayCreditMaxAmt = onpayCreditMaxAmt;
	}

	/**
	 * 允许乐豆支付:0:不允许,1:允许
	 * @return
	 */
	public String getLdSale() {
		return ldSale;
	}

	/**
	 * 允许乐豆支付:0:不允许,1:允许
	 * @param ldSale
	 */
	public void setLdSale(String ldSale) {
		this.ldSale = ldSale;
	}

	/**
	 * 发展机构
	 * @return
	 */
	public String getAgentNo() {
		return agentNo;
	}

	/**
	 * 发展机构
	 * @param agentNo
	 */
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

	/**
	 * 商家所在省
	 * @return
	 */
	public Short getProvince() {
		return province;
	}

	/**
	 * 商家所在省
	 * @param province
	 */
	public void setProvince(Short province) {
		this.province = province;
	}

	/**
	 * 商家所在市
	 * @return
	 */
	public Short getCity() {
		return city;
	}

	/**
	 * 商家所在市
	 * @param city
	 */
	public void setCity(Short city) {
		this.city = city;
	}

	/**
	 * 商家所在区域
	 * @return
	 */
	public Short getDistrict() {
		return district;
	}

	/**
	 * 商家所在区域
	 * @param district
	 */
	public void setDistrict(Short district) {
		this.district = district;
	}

	/**
	 * 商家所在区域代理商
	 * @return
	 */
	public String getDistAgent() {
		return distAgent;
	}

	/**
	 * 商家所在区域代理商
	 * @param distAgent
	 */
	public void setDistAgent(String distAgent) {
		this.distAgent = distAgent;
	}

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}

	/**
	 * <p>POS贷记卡手续费费率</p>
	 * @return the posCreditFeeRate
	 */
	public BigDecimal getPosCreditFeeRate() {
		return posCreditFeeRate;
	}

	/**
	 * <p>POS贷记卡手续费费率</p>
	 * @param posCreditFeeRate the posCreditFeeRate to set
	 */
	public void setPosCreditFeeRate(BigDecimal posCreditFeeRate) {
		this.posCreditFeeRate = posCreditFeeRate;
	}

	/**
	 * <p>在线支付借记卡手续费封顶值</p>
	 * @return the onpayDebitMaxAmt
	 */
	public BigDecimal getOnpayDebitMaxAmt() {
		return onpayDebitMaxAmt;
	}

	/**
	 * <p>在线支付借记卡手续费封顶值</p>
	 * @param onpayDebitMaxAmt the onpayDebitMaxAmt to set
	 */
	public void setOnpayDebitMaxAmt(BigDecimal onpayDebitMaxAmt) {
		this.onpayDebitMaxAmt = onpayDebitMaxAmt;
	}

	/**
	 * 清算支付公司
	 * @return the settleOrganNo
	 */
	public String getSettleOrganNo() {
		return settleOrganNo;
	}

	/**
	 * 清算支付公司
	 * @param settleOrganNo the settleOrganNo to set
	 */
	public void setSettleOrganNo(String settleOrganNo) {
		this.settleOrganNo = settleOrganNo;
	}

	/**
	 * 发展商户机构上级机构
	 * @return the parentAgentNo
	 */
	public String getParentAgentNo() {
		return parentAgentNo;
	}

	/**
	 * 发展商户机构上级机构
	 * @param parentAgentNo the parentAgentNo to set
	 */
	public void setParentAgentNo(String parentAgentNo) {
		this.parentAgentNo = parentAgentNo;
	}

	/**
	 * 推广业务员
	 * @return the inviteAgentNo
	 */
	public String getInviteAgentNo() {
		return inviteAgentNo;
	}

	/**
	 * 推广业务员
	 * @param inviteAgentNo the inviteAgentNo to set
	 */
	public void setInviteAgentNo(String inviteAgentNo) {
		this.inviteAgentNo = inviteAgentNo;
	}

	/**
	 * 在线支付 0线下支付线上支付，1线下支付，2线上支付
	 * @return the onLinePayment
	 */
	public String getOnLinePayment() {
		return onLinePayment;
	}

	/**
	 * 在线支付 0线下支付线上支付，1线下支付，2线上支付
	 * @param onLinePayment the onLinePayment to set
	 */
	public void setOnLinePayment(String onLinePayment) {
		this.onLinePayment = onLinePayment;
	}

	public String getParentMerchantNo() {
		return parentMerchantNo;
	}

	public void setParentMerchantNo(String parentMerchantNo) {
		this.parentMerchantNo = parentMerchantNo;
	}

	public String getSettleMerchant() {
		return settleMerchant;
	}

	public void setSettleMerchant(String settleMerchant) {
		this.settleMerchant = settleMerchant;
	}
}
