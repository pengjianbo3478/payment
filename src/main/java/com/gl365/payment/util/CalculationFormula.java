package com.gl365.payment.util;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.gl365.payment.enums.merchant.MerchantGlFeeType;
import com.gl365.payment.remote.dto.merchant.Gl365Merchant;
public class CalculationFormula {
	public static final BigDecimal OneHundred = new BigDecimal(100);

	/**
	 * 返利金额=(消费金额-不可返利金额)*返利率/100
	 * @param totalAmount
	 * @param noBenefitAmount
	 * @param rate
	 * @return
	 */
	public static BigDecimal calcGiftAmount(BigDecimal amt, BigDecimal noAmt, BigDecimal rate) {
		BigDecimal result = (amt.subtract(noAmt)).multiply(rate).divide(OneHundred).setScale(2, BigDecimal.ROUND_HALF_UP);
		return result;
		/*BigDecimal temp = BigDecimalUtil.subtract(totalAmount, noBenefitAmount, 2);
		temp = BigDecimalUtil.mul(temp, glFeeRate, 10);
		return BigDecimalUtil.divide(temp, OneHundred, 2, BigDecimal.ROUND_HALF_UP);*/
	}

	/**
	 * 营销费 = 消费返佣 - 支付手续费  (营销费计算出来可能为负值，则直接为0)
	 * @param giftAmount
	 * @param payFee
	 * @return
	 */
	public static BigDecimal calcMarketFee(BigDecimal giftAmount, BigDecimal payFee) {
		BigDecimal temp = BigDecimalUtil.subtract(giftAmount, payFee, 2);
		if (BigDecimalUtil.GreaterThan0(temp)) {
			return temp;
		}
		else {
			return BigDecimal.ZERO;
		}
	}

	/**
	 * 支付手续费 = 银行扣款* 手续费率(区分借贷和POS，扫码支付)
		If 交易类型是POS支付 then
		{
		支付手续费 = Round(银行扣款* 手续费率/100,2);
		If 借记卡封项金额>0 then
		If支付手续费>借记卡封项金额 then
		支付手续费:= 借记卡封项金额 ;
		    }
		Else   –-扫码支付（线上）
		{
		支付手续费 = Round(银行扣款* 手续费率/100,2);
		If 借记卡封项金额>0 then
		If支付手续费>借记卡封项金额 then
		支付手续费:= 借记卡封项金额;
		 }
	 * @param totalAmount 银行扣款(实际扣除金额)
	 * @param tranType 交易类别，只用区分线上/线下
	 * @param bankCardType 卡类型(1是借记卡，2是贷记卡) 查账号系统
	 * @param feeRate 手续费率(区分借贷和POS，扫码支付	查商户系统
	 * @param maxAmt 借记卡封项金额
	 * @return
	 */
	public static BigDecimal calcPayFee(BigDecimal totalAmount, int tranType, int bankCardType, BigDecimal feeRate, BigDecimal maxAmt) {
		BigDecimal result = BigDecimal.ZERO;
		result = BigDecimalUtil.divide(BigDecimalUtil.mul(totalAmount, feeRate, 10), OneHundred, 2, BigDecimal.ROUND_HALF_UP);
		if (/*1 == bankCardType ////借记卡(查商户接口返回)
			&& */BigDecimalUtil.GreaterThan0(maxAmt) //// 卡封项金额>0
				&& BigDecimalUtil.compareTo(result, maxAmt) == 1) { // 支付手续费>卡封项金额
			result = maxAmt;
		}
		return result;
	}

	/**
	 * 应退金额=round(本次退款金额*(原支付单支付实扣金额/原支付单消费金额),2);
	 * if 应退金额>round(原支付单支付实扣金额-已退实扣金额,2) then
	 * 		应退金额:=round(原支付单支付实扣金额-已退实扣金额,2) 
	 * end if;
	 * @param currTotalAmount
	 * @param origPayTotalAmount
	 * @param origPayCashAmount
	 * @param originalSingle_cash_ratio
	 * @param returnedCashAmount
	 * @return
	 */
	// @Deprecated
	public static BigDecimal calcRefundableCashAmount(BigDecimal currTotalAmount, BigDecimal origPayTotalAmount, BigDecimal origPayCashAmount, BigDecimal returnedCashAmount, BigDecimal returnableBeanAmount, Boolean isRefundAll) {
		if (isRefundAll) { return BigDecimalUtil.subtract(origPayCashAmount, returnedCashAmount); }
		// 原支付单支付实扣金额/原支付单消费金额
		// BigDecimal ratio = BigDecimalUtil.divide(origPayCashAmount,
		// origPayTotalAmount, 10, BigDecimal.ROUND_HALF_UP);
		// BigDecimal refundableCashAmount = BigDecimalUtil.mul(currTotalAmount,
		// ratio, 2);
		BigDecimal refundableCashAmount = BigDecimalUtil.subtract(currTotalAmount, returnableBeanAmount);
		// if 应退金额>round(原支付单支付实扣金额-已退实扣金额,2) then
		if (BigDecimalUtil.compareTo(refundableCashAmount, BigDecimalUtil.subtract(origPayCashAmount, returnedCashAmount)) == 1) {
			refundableCashAmount = BigDecimalUtil.subtract(origPayCashAmount, returnedCashAmount, 2);
		}
		return refundableCashAmount;
	}

	public static BigDecimal calcRefundableCashAmount333(BigDecimal currTotalAmount, BigDecimal origPayTotalAmount, BigDecimal origPayCashAmount, BigDecimal returnedCashAmount, Boolean isRefundAll) {
		if (isRefundAll) { return BigDecimalUtil.subtract(origPayTotalAmount, returnedCashAmount); }
		// 原支付单支付实扣金额/原支付单消费金额
		BigDecimal ratio = BigDecimalUtil.divide(origPayCashAmount, origPayTotalAmount, 10, BigDecimal.ROUND_HALF_UP);
		BigDecimal refundableCashAmount = BigDecimalUtil.mul(currTotalAmount, ratio, 2);
		// if 应退金额>round(原支付单支付实扣金额-已退实扣金额,2) then
		if (BigDecimalUtil.compareTo(refundableCashAmount, BigDecimalUtil.subtract(origPayCashAmount, returnedCashAmount)) == 1) {
			refundableCashAmount = BigDecimalUtil.subtract(origPayCashAmount, returnedCashAmount, 2);
		}
		return refundableCashAmount;
	}

	/**
	 * 本次应退金额:=本次退款金额-本次应退乐豆;
		if 本次应退金额>原支付单支付实扣金额-已退实扣金额 then
		  本次应退金额:=原支付单支付实扣金额-已退实扣金额 
		end if;
	 * @param currTotalAmount
	 * @param returnAbleBeanAmount
	 * @param origPayCashAmount
	 * @param returnedCashAmount
	 * @return
	 */
	public static BigDecimal calcRefundableCashAmount3(BigDecimal currTotalAmount, BigDecimal returnAbleBeanAmount, BigDecimal origPayCashAmount, BigDecimal returnedCashAmount) {
		// 本次应退金额:=本次退款金额-本次应退乐豆;
		BigDecimal refundableCashAmount = BigDecimalUtil.subtract(currTotalAmount, returnAbleBeanAmount, 2);
		// if 应退金额>round(原支付单支付实扣金额-已退实扣金额,2) then
		if (BigDecimalUtil.compareTo(refundableCashAmount, BigDecimalUtil.subtract(origPayCashAmount, returnedCashAmount)) == 1) {
			refundableCashAmount = BigDecimalUtil.subtract(origPayCashAmount, returnedCashAmount, 2);
		}
		return refundableCashAmount;
	}

	/**
	 * 应退乐豆：=round(本次退款金额*(原支付单支付乐豆/原支付单消费金额),2);
	 * if 应退乐豆>round(原支付单支付乐豆-已退乐豆总额,2) then
	 * 		应退乐豆:=round(原支付单支付乐豆-已退乐豆总额,2)
	 * end if
	 * @param currTotalAmount
	 * @param origPayTotalAmount
	 * @param origPayBeanAmount
	 * @param returnedBeanAmount
	 * @return
	 */
	public static BigDecimal calcRefundableBeanAmount(BigDecimal currTotalAmount, BigDecimal origPayTotalAmount, BigDecimal origPayBeanAmount, BigDecimal returnedBeanAmount, Boolean isRefundAll) {
		if (isRefundAll) { return BigDecimalUtil.subtract(origPayBeanAmount, returnedBeanAmount); }
		// 原支付单支付乐豆/原支付单消费金额
		BigDecimal ratio = BigDecimalUtil.divide(origPayBeanAmount, origPayTotalAmount, 10, BigDecimal.ROUND_HALF_UP);
		BigDecimal refundableBeanAmount = BigDecimalUtil.mul(currTotalAmount, ratio, 2);
		// if 应退金额>round(原支付单支付实扣金额-已退实扣金额,2) then
		if (BigDecimalUtil.compareTo(refundableBeanAmount, BigDecimalUtil.subtract(origPayBeanAmount, returnedBeanAmount)) == 1) {
			refundableBeanAmount = BigDecimalUtil.subtract(origPayBeanAmount, returnedBeanAmount, 2);
		}
		return refundableBeanAmount;
	}

	/**
	 * 应退返利金额=原单返利*(本次退款金额/原单消费金额));
	 * if 应退返利金额>round(原支付单返利金额-已退返利金额,2) then  
	 * 		应退返利金额:=round(原支付单返利金额-已退返利金额,2)
	 * end if;
	 * @param currTotalAmount
	 * @param origPayGiftAmount
	 * @param originalSingle_Gift_ratio
	 * @param returnedGiftAmount
	 * @return
	 */
	public static BigDecimal calcRefundableGiftAmount(BigDecimal currTotalAmount, BigDecimal origPayTotalAmount, BigDecimal origPayGiftAmount, BigDecimal returnedGiftAmount, Boolean isRefundAll) {
		if (isRefundAll) { return BigDecimalUtil.subtract(origPayGiftAmount, returnedGiftAmount); }
		// 本次退款金额/原单消费金额
		BigDecimal ratio = BigDecimalUtil.divide(currTotalAmount, origPayTotalAmount, 10, BigDecimal.ROUND_HALF_UP);
		BigDecimal refundableGiftAmount = BigDecimalUtil.mul(origPayGiftAmount, ratio, 2);
		// 应退返利金额>原支付单返利金额-已退返利金额
		if (BigDecimalUtil.compareTo(refundableGiftAmount, BigDecimalUtil.subtract(origPayGiftAmount, returnedGiftAmount)) == 1) {
			refundableGiftAmount = BigDecimalUtil.subtract(origPayGiftAmount, returnedGiftAmount, 2);
		}
		return refundableGiftAmount;
	}

	/**
	 * 本次返佣金额=Round(原单返佣金额* (本次退款金额-本次应退返利金额）/(原支付单消费金额-原单返利金额),2)
		  if 本次返佣金额>原单返佣金额-已退单返佣金额 then
		    本次返佣金额=原单返佣金额-已退单返佣金额
		  end if;
	 * @param currTotalAmount
	 * @param origPayTotalAmount
	 * @param currGiftAmount
	 * @param origPayGiftAmount
	 * @param returnedCommAmount
	 * @param origPayCommAmount
	 * @param isRefundAll
	 * @return
	 */
	public static BigDecimal calcRefundableCommAmount(BigDecimal currTotalAmount, BigDecimal origPayTotalAmount, BigDecimal currGiftAmount, BigDecimal origPayGiftAmount, BigDecimal returnedCommAmount, BigDecimal origPayCommAmount, Boolean isRefundAll) {
		if (isRefundAll) { return BigDecimalUtil.subtract(origPayCommAmount, returnedCommAmount); }
		// (本次退款金额-本次应退返利金额）/(原支付单消费金额-原单返利金额)
		BigDecimal ratio = BigDecimalUtil.divide(BigDecimalUtil.subtract(currTotalAmount, currGiftAmount), BigDecimalUtil.subtract(origPayTotalAmount, origPayGiftAmount), 10, BigDecimal.ROUND_HALF_UP);
		BigDecimal refundableGiftAmount = BigDecimalUtil.mul(origPayCommAmount, ratio, 2);
		// 应退返利金额>原支付单返利金额-已退返利金额
		if (BigDecimalUtil.compareTo(refundableGiftAmount, BigDecimalUtil.subtract(origPayCommAmount, returnedCommAmount)) == 1) {
			refundableGiftAmount = BigDecimalUtil.subtract(origPayCommAmount, returnedCommAmount, 2);
		}
		return refundableGiftAmount;
	}

	/**
	 * 应退营销费:=原单营销费*(本次退款金额/原单消费金额));
	 * if 应退营销费>round(原支付单营销费-已退返利金额,2) then
	 *   应退营销费:=round(原支付单营销费-已退营销费,2)
	 * end if;
	 * @param currTotalAmount
	 * @param origPayTotalAmount
	 * @param origPayMarcketFee
	 * @param returnedMarcketFee
	 * @return
	 */
	public static BigDecimal calcRefundableMarcketFee(BigDecimal currTotalAmount, BigDecimal origPayTotalAmount, BigDecimal origPayMarcketFee, BigDecimal returnedMarcketFee, Boolean isRefundAll) {
		if (isRefundAll) { return BigDecimalUtil.subtract(origPayMarcketFee, returnedMarcketFee); }
		// 本次退款金额/原单消费金额
		BigDecimal ratio = BigDecimalUtil.divide(currTotalAmount, origPayTotalAmount, 10, BigDecimal.ROUND_HALF_UP);
		BigDecimal refundableMarcketFee = BigDecimalUtil.mul(origPayMarcketFee, ratio, 2);
		// 应退营销费>原支付单营销费-已退营销费
		if (BigDecimalUtil.compareTo(refundableMarcketFee, BigDecimalUtil.subtract(origPayMarcketFee, returnedMarcketFee)) == 1) {
			refundableMarcketFee = BigDecimalUtil.subtract(origPayMarcketFee, returnedMarcketFee, 2);
		}
		return refundableMarcketFee;
	}

	/**
	 * 应退手续费:=原单手续费:*(本次退款金额/原单消费金额));
	 * if 应退手续费:>round(原支付单手续费:-已退返利金额,2) then
	 *   应退手续费::=round(原支付单手续费:-已退手续费:,2) 
	 * end if;
	 * @param currTotalAmount
	 * @param origPayTotalAmount
	 * @param origPayPayFee
	 * @param returnedPayFee
	 * @return
	 */
	public static BigDecimal calcRefundablePayFee(BigDecimal currTotalAmount, BigDecimal origPayTotalAmount, BigDecimal origPayFee, BigDecimal returnedPayFee, Boolean isRefundAll) {
		if (isRefundAll) { return BigDecimalUtil.subtract(origPayFee, returnedPayFee); }
		// 本次退款金额/原单消费金额
		BigDecimal ratio = BigDecimalUtil.divide(currTotalAmount, origPayTotalAmount, 10, BigDecimal.ROUND_HALF_UP);
		BigDecimal refundablePayFee = BigDecimalUtil.mul(origPayFee, ratio, 2);
		// 应退营销费>原支付单营销费-已退营销费
		if (BigDecimalUtil.compareTo(refundablePayFee, BigDecimalUtil.subtract(origPayFee, returnedPayFee)) == 1) {
			refundablePayFee = BigDecimalUtil.subtract(origPayFee, returnedPayFee, 2);
		}
		return refundablePayFee;
	}

	/**
	 * 商户清算金额（即应得金额） = 支付乐豆 + 银行扣款 - 返佣费 - 返利金额
	 * @param beanAmount
	 * @param cashAmount
	 * @param commAmount
	 * @param giftAmount
	 * @return
	 */
	public static BigDecimal calcMerchantSettlAmount(BigDecimal beanAmount, BigDecimal cashAmount, BigDecimal commAmount, BigDecimal giftAmount) {
		BigDecimal temp = BigDecimalUtil.add(beanAmount, cashAmount);
		return BigDecimalUtil.subtract(temp, commAmount, giftAmount, 2);
	}

	/**
	 * 消费返佣费 = （消费金额 - 乐币 - 返利金额） * 佣金率/100
	 * @param totalAmount
	 * @param coinAmount
	 * @param giftAmount
	 * @param commRate
	 * @return
	 */
	public static BigDecimal calcCommAmount(BigDecimal totalAmount, BigDecimal coinAmount, BigDecimal giftAmount, BigDecimal commRate) {
		BigDecimal temp = BigDecimalUtil.subtract(totalAmount, coinAmount, giftAmount, 10);
		temp = BigDecimalUtil.mul(temp, commRate, 10);
		return BigDecimalUtil.divide(temp, OneHundred, 2, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 消费返佣率 = commAmount/（消费金额 - 乐币 - 返利金额） *100
	 * @param totalAmount
	 * @param coinAmount
	 * @param giftAmount
	 * @param commAmount
	 * @return
	 */
	public static BigDecimal calcCommRate(BigDecimal totalAmount, BigDecimal coinAmount, BigDecimal giftAmount, BigDecimal commAmount) {
		BigDecimal temp = BigDecimalUtil.subtract(totalAmount, coinAmount, giftAmount, 10);
		temp = BigDecimalUtil.divide(commAmount, temp, 4, BigDecimal.ROUND_HALF_UP);
		return temp.multiply(OneHundred);
	}
	
	/**
	 * glFeeType = 0  返佣率 = glFeeRate
	 * glFeeType = 1 返佣率：=round( 交易总金额-成本价-返利金额)/(交易总金额-返利金额)*100,2)
	 * @param totalAmount 交易总金额
	 * @param coinAmount 返利豆（0）
	 * @param giftAmount 返利金额
	 * @param gl365Merchant 
	 * @param costPrice 成本价  
	 * @return
	 */
	public static BigDecimal calcCommFeeRate(BigDecimal totalAmount, BigDecimal coinAmount, BigDecimal giftAmount, Gl365Merchant gl365Merchant, BigDecimal costPrice) {
		if (StringUtils.equals(gl365Merchant.getGlFeeType(), MerchantGlFeeType.FIXED.getValue())) {
			return gl365Merchant.getGlFeeRate();
		}
		BigDecimal temp = BigDecimalUtil.subtract(totalAmount, coinAmount, giftAmount, 10);
		BigDecimal rate = BigDecimalUtil.divide(temp.subtract(costPrice), temp, 4, BigDecimal.ROUND_HALF_UP);
		BigDecimal feeRate = rate.multiply(OneHundred);
		gl365Merchant.setGlFeeRate(feeRate);
		return feeRate;
	}

	/**
	 * 是否是全额退款
	 * @param totalAmount
	 * @param returnedAmount
	 * @param origPayTotalAmount
	 * @return
	 */
	public static boolean ifRefundAll(BigDecimal totalAmount, BigDecimal returnedAmount, BigDecimal origPayTotalAmount) {
		BigDecimal temp = BigDecimalUtil.add(totalAmount, returnedAmount);
		return temp.compareTo(origPayTotalAmount) == 0 ? true : false;
	}
	/*public static void main(String[] args) {
		System.out.println(calcMarcketFee(new BigDecimal(90), new BigDecimal(38), new BigDecimal(0.4)));
	}*/
}
