package com.gl365.payment.service.wz.common.impl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.gl365.payment.enums.merchant.MerchantPayBean;
import com.gl365.payment.enums.pay.CardType;
import com.gl365.payment.enums.pay.PayChannel;
import com.gl365.payment.enums.pay.PayCurrency;
import com.gl365.payment.enums.pay.PayFeeType;
import com.gl365.payment.remote.dto.member.Gl365User;
import com.gl365.payment.remote.dto.merchant.Gl365Merchant;
import com.gl365.payment.service.wz.common.WxPrePayCalcuateService;
import com.gl365.payment.util.BigDecimalUtil;
@Service
public class WxPrePayCalcuateServiceImpl implements WxPrePayCalcuateService {
	private static final Logger LOG = LoggerFactory.getLogger(WxPrePayCalcuateServiceImpl.class);

	public BigDecimal calculatePayFee(Gl365Merchant gl365Merchant, BigDecimal cashAmt, String cardType, String payChannel) {
		BigDecimal res = this.getPayFeeRate(gl365Merchant, cardType, payChannel);
		BigDecimal payFeeRate = res.divide(new BigDecimal(100));
		// 支付手续费=银行扣款（即现金部分） * 手续费率(区分借贷)
		BigDecimal payFee = cashAmt.multiply(payFeeRate).setScale(2, RoundingMode.HALF_UP);
		LOG.debug("支付手续费=现金:{}*手续费率:{}={}", cashAmt, payFeeRate, payFee);
		return this.getPayFeeMaxAmt(gl365Merchant, payFee, cardType, payChannel);
	}

	public BigDecimal getPayFeeRate(Gl365Merchant gl365Merchant, String cardType, String payChannel) {
		boolean c = StringUtils.equals(cardType, CardType.C.getCode());
		boolean d = StringUtils.equals(cardType, CardType.D.getCode());
		boolean pos = StringUtils.equals(payChannel, PayChannel.POS.getCode());
		boolean web = StringUtils.equals(payChannel, PayChannel.ONLINE.getCode());
		BigDecimal payFeeRate = BigDecimal.ZERO;
		if (c) {
			if (pos) payFeeRate = gl365Merchant.getPosCreditFeeRate();
			if (web) payFeeRate = gl365Merchant.getOnpayCreditFeeRate();
		}
		if (d) {
			if (pos) payFeeRate = gl365Merchant.getPosDebitFeeRate();
			if (web) payFeeRate = gl365Merchant.getOnpayDebitFeeRate();
		}
		return payFeeRate;
	}

	private BigDecimal getPayFeeMaxAmt(Gl365Merchant gl365Merchant, BigDecimal payFee, String cardType, String payChannel) {
		BigDecimal posDebitMaxAmt = gl365Merchant.getPosDebitMaxAmt();
		BigDecimal posCreditMaxAmt = gl365Merchant.getPosCreditMaxAmt();
		BigDecimal onpayCreditMaxAmt = gl365Merchant.getOnpayCreditMaxAmt();
		BigDecimal onpayDebitMaxAmt = gl365Merchant.getOnpayDebitMaxAmt();
		LOG.debug("支付卡类型(区分借贷)={}", cardType);
		LOG.debug("借记卡POS封顶值={},借记卡网上封顶值={}", posDebitMaxAmt, onpayDebitMaxAmt);
		LOG.debug("贷记卡POS封顶值={},贷记卡网上封顶值={}", posCreditMaxAmt, onpayCreditMaxAmt);
		boolean d = StringUtils.equals(cardType, PayFeeType.D.getCode());
		boolean pos = StringUtils.equals(payChannel, PayChannel.POS.getCode());
		boolean web = StringUtils.equals(payChannel, PayChannel.ONLINE.getCode());
		if (d) {
			if (pos) {
				boolean t = payFee.compareTo(posDebitMaxAmt) == 1;
				LOG.debug("卡为借记卡 支付手续费是否大于POS借记卡封顶值取封顶值={}", t);
				if (t) return posDebitMaxAmt;
			}
			if (web) {
				boolean t = payFee.compareTo(onpayDebitMaxAmt) == 1;
				LOG.debug("卡为借记卡 支付手续费是否大于网上借记卡封顶值取封顶值={}", t);
				if (t) return onpayDebitMaxAmt;
			}
		}
		boolean c = StringUtils.equals(cardType, CardType.C.getCode());
		if (c) {
			if (pos) {
				boolean t = payFee.compareTo(posCreditMaxAmt) == 1;
				LOG.debug("卡为信用卡 支付手续费是否大于POS信用卡封顶值取封顶值={}", t);
				if (t) return posCreditMaxAmt;
			}
			if (web) {
				boolean t = payFee.compareTo(onpayCreditMaxAmt) == 1;
				LOG.debug("卡为信用卡 支付手续费是否大于网上借记卡封顶值取封顶值={}", t);
				if (t) return onpayCreditMaxAmt;
			}
		}
		LOG.debug("支付手续费={}", payFee);
		return payFee;
	}

	@Override
	public BigDecimal calculateMerchantSettleAmount(BigDecimal beanAmt, BigDecimal cashAmt, BigDecimal commAmt, BigDecimal giftAmt) {
		BigDecimal result = beanAmt.add(cashAmt).subtract(commAmt).subtract(giftAmt);
		LOG.debug("结算金额=[乐豆:{}+现金:{}-返佣费:{}-返利金额:{}]={}", beanAmt, cashAmt, commAmt, giftAmt, result);
		return result;
	}

	public BigDecimal calculateCommAmount(BigDecimal totalAmt, BigDecimal coinAmt, BigDecimal giftAmt, BigDecimal commRate, BigDecimal payFee) {
		LOG.debug("消费金额={},乐币={},返利金额={},支付手续费={}", totalAmt, coinAmt, giftAmt, payFee);
		BigDecimal cr = commRate.divide(new BigDecimal(100));
		LOG.debug("佣金率={}", cr);
		BigDecimal result = (totalAmt.subtract(coinAmt).subtract(giftAmt)).multiply(cr).setScale(2, RoundingMode.HALF_UP);
		LOG.debug("消费返佣 =[消费金额:{}-乐币:{}-返利金额:{}*佣金率:{}]={}", totalAmt, coinAmt, giftAmt, cr, result);
		if (result.compareTo(payFee) == -1) {
			LOG.debug("消费返佣小于支付手续费取支付手续费值");
			return payFee;
		}
		return result;
	}

	public BigDecimal calculateMarcketFee(BigDecimal commAmount, BigDecimal payFee) {
		LOG.debug("消费返佣={},支付手续费={}", commAmount, payFee);
		BigDecimal marcketFee = commAmount.subtract(payFee);
		LOG.debug("营销费 =消费返佣:{}-支付手续费 :{}={}", commAmount, payFee, marcketFee);
		boolean b = marcketFee.compareTo(BigDecimal.ZERO) == -1; // 营销费小于0
		if (b) {
			LOG.debug("营销费计算出来为负值返回0");
			return BigDecimal.ZERO;
		}
		else {
			return marcketFee;
		}
	}

	@Override
	public Map<String, BigDecimal> getPayBeanAmt(Gl365Merchant gl365Merchant, Gl365User gl365User, BigDecimal tAmt, BigDecimal bAmt) {
		boolean c = gl365User.isEnableHappycoin();
		LOG.debug("用户乐豆支付开关 ={}", c);
		boolean m = StringUtils.equals(gl365Merchant.getLdSale(), MerchantPayBean.ON.getKey());
		LOG.debug("商家乐豆支付开关 ={}", m);
		Map<String, BigDecimal> result = new HashMap<String, BigDecimal>();
		if (!c || !m) {
			result.put(PayCurrency.BEAN.getCode(), BigDecimal.ZERO);
			result.put(PayCurrency.CASH.getCode(), tAmt);
			return result;
		}
		int res = tAmt.compareTo(BigDecimal.ONE);
		if (res == -1) {
			LOG.debug("消费金额小于1元不支付乐豆-乐豆支付={},现金支付={}", BigDecimal.ZERO, tAmt);
			result.put(PayCurrency.BEAN.getCode(), BigDecimal.ZERO);
			result.put(PayCurrency.CASH.getCode(), tAmt);
			return result;
		}
		else if (res == 0) {
			LOG.debug("消费金额等于1元不支付-乐豆乐豆支付={},现金支付={}", BigDecimal.ZERO, tAmt);
			result.put(PayCurrency.BEAN.getCode(), BigDecimal.ZERO);
			result.put(PayCurrency.CASH.getCode(), tAmt);
		}
		else if (res == 1) {
			int ctResult = bAmt.compareTo(tAmt.subtract(BigDecimal.ONE));
			if (ctResult == -1) {
				result.put(PayCurrency.BEAN.getCode(), bAmt);
				result.put(PayCurrency.CASH.getCode(), tAmt.subtract(bAmt).setScale(2, RoundingMode.HALF_UP));
			}
			else {
				result.put(PayCurrency.BEAN.getCode(), tAmt.subtract(BigDecimal.ONE));
				result.put(PayCurrency.CASH.getCode(), BigDecimal.ONE);
			}
		}
		LOG.debug("乐豆支付={},现金支付={}", result.get(PayCurrency.BEAN.getCode()), result.get(PayCurrency.CASH.getCode()));
		return result;
	}

	@Override
	public BigDecimal getMaxPayFee(Gl365Merchant merchant, String cardType, String payChannel) {
		boolean d = StringUtils.equals(cardType, PayFeeType.D.getCode());
		boolean c = StringUtils.equals(cardType, CardType.C.getCode());
		boolean pos = StringUtils.equals(payChannel, PayChannel.POS.getCode());
		boolean web = StringUtils.equals(payChannel, PayChannel.ONLINE.getCode());
		if (d) {
			if (pos) return merchant.getPosDebitMaxAmt();
			if (web) return merchant.getOnpayDebitMaxAmt();
		}
		if (c) {
			if (pos) return merchant.getPosCreditMaxAmt();
			if (web) return merchant.getOnpayCreditMaxAmt();
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal calculateDecAmt(BigDecimal totalAmt, BigDecimal cashAmt) {
		BigDecimal res = totalAmt.subtract(cashAmt);
		LOG.debug("计算抵扣金额 [抵扣金额=订单总金额:{}-现金金额:{}]={}", totalAmt, cashAmt, res);
		return res;
	}

	@Override
	public BigDecimal calculateCommRate(BigDecimal totalAmt, BigDecimal coinAmt, BigDecimal giftAmt, BigDecimal commAmount) {
		LOG.debug("消费金额={},乐币={},返利金额={}, 返佣费={}", totalAmt, coinAmt, giftAmt, commAmount);
		BigDecimal temp = totalAmt.subtract(coinAmt).subtract(giftAmt);
		BigDecimal rate = BigDecimalUtil.divide(commAmount, temp, 4, BigDecimal.ROUND_HALF_UP);
		BigDecimal result = rate.multiply(new BigDecimal(100));
		LOG.debug("消费率 =[返佣费:{}/(消费金额:{}-乐币:{}-返利金额:{})*100]={}", totalAmt, coinAmt, giftAmt, result);
		return result;
	}
}
