package com.gl365.payment.service.wz.common;
import java.math.BigDecimal;
import java.util.Map;
import com.gl365.payment.remote.dto.member.Gl365User;
import com.gl365.payment.remote.dto.merchant.Gl365Merchant;
public interface WxPrePayCalcuateService {
	/**
	 * 计算支付手续费
	 * @param merchant 商户
	 * @param cashAmt 银行扣款（即现金部分）
	 * @param cardType 支付卡类型
	 * @param payChannel 交易类型-POS/WEB
	 * @return BigDecimal
	 */
	BigDecimal calculatePayFee(Gl365Merchant merchant, BigDecimal cashAmt, String cardType, String payChannel);

	/**
	 * 获取商户支付手费费费率
	 * @param gl365Merchant 商户
	 * @param cardType 支付卡类型
	 * @param payChannel 交易类型-POS/WEB
	 * @return BigDecimal
	 */
	BigDecimal getPayFeeRate(Gl365Merchant gl365Merchant, String cardType, String payChannel);

	/**
	 * 计算商户结算金额 【商户结算金额=支付乐豆 +银行扣款-返佣费 -返利金额】
	 * @param beanAmt 支付乐豆
	 * @param cashAmt 银行扣款
	 * @param commAmt 返佣金额
	 * @param giftAmt  返利金额
	 * @return BigDecimal
	 */
	BigDecimal calculateMerchantSettleAmount(BigDecimal beanAmt, BigDecimal cashAmt, BigDecimal commAmt, BigDecimal giftAmt);

	/**
	 * 计算消费返返佣金额 【消费返佣 = （消费金额 - 乐币 - 返利金额） * 佣金率 】
	 * @param totalAmt
	 * @param coinAmt
	 * @param giftAmt
	 * @param commRate
	 * @param  payFee 支付手续费
	 * @return BigDecimal
	 */
	BigDecimal calculateCommAmount(BigDecimal totalAmt, BigDecimal coinAmt, BigDecimal giftAmt, BigDecimal commRate,BigDecimal payFee);

	/**
	 * 计算 营销费 =消费返佣 - 支付手续费 (营销费计算出来可能为负值，则直接为0)
	 * @param commAmount 消费返佣
	 * @param payFee 支付手续费
	 * @return
	 */
	BigDecimal calculateMarcketFee(BigDecimal commAmount, BigDecimal payFee);

	/**
	 * 
	 * @param gl365Merchant 给乐商户
	 * @param gl365User 给乐用户
	 * @param bAmt 乐豆余额 
	 * @return tAmt 消费金额
	 */
	Map<String, BigDecimal> getPayBeanAmt(Gl365Merchant gl365Merchant, Gl365User gl365User, BigDecimal tAmt, BigDecimal bAmt);

	BigDecimal getMaxPayFee(Gl365Merchant merchant, String cardType, String payChannel);

	/**
	 * 计算抵扣金额
	 * @param totalAmt 订单总金额
	 * @param cashAmt 现金支付金额
	 * @return
	 */
	BigDecimal calculateDecAmt(BigDecimal totalAmt, BigDecimal cashAmt);
	
	/**
	 * 计算消费返返佣率 【消费率 = commAmount/（消费金额 - 乐币 - 返利金额）】
	 * @param totalAmt
	 * @param coinAmt
	 * @param giftAmt
	 * @param commAmount
	 * @return BigDecimal
	 */
	BigDecimal calculateCommRate(BigDecimal totalAmt, BigDecimal coinAmt, BigDecimal giftAmt, BigDecimal commAmount);
}
