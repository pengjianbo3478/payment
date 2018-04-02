package com.gl365.payment.service.pos.query.impl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.gl365.payment.dto.pretranscation.PreTranContext;
import com.gl365.payment.dto.pretranscation.request.PreTranReqDTO;
import com.gl365.payment.enums.pay.CardType;
import com.gl365.payment.enums.pay.PayFeeType;
import com.gl365.payment.model.PayPrepay;
import com.gl365.payment.remote.dto.merchant.Gl365Merchant;
import com.gl365.payment.service.pos.query.PreTranCalcuateService;
@Service
public class PreTranCalcuateServiceImpl implements PreTranCalcuateService {
	private static final Logger LOG = LoggerFactory.getLogger(PreTranCalcuateServiceImpl.class);

	public void calculatePayFeeRate(PreTranContext ctx) {
		PayPrepay pp = ctx.getPayPrepay();
		Gl365Merchant merchant = ctx.getGl365Merchant();
		BigDecimal payFeeRate = pp.getPayFeeRate().divide(new BigDecimal(100));
		// 支付手续费=银行扣款（即现金部分） * 手续费率(区分借贷)
		BigDecimal payFee = pp.getCashAmount().multiply(payFeeRate).setScale(2, RoundingMode.HALF_UP);
		LOG.debug("支付手续费=银行扣款（即现金部分） * 手续费率(区分借贷)={}", payFee);
		// BigDecimal maxComm = payPrepay.getMaxComm();//
		BigDecimal posDebitMaxAmt = merchant.getPosDebitMaxAmt();// POS借记卡封顶值
		LOG.debug("POS借记卡封顶值={}", posDebitMaxAmt);
		BigDecimal posCreditMaxAmt = merchant.getPosCreditMaxAmt();// POS贷记卡封顶值
		LOG.debug("POS贷记卡封顶值={}", posCreditMaxAmt);
		pp.setPayFee(payFee);
		String payFeeType = pp.getPayFeeType();// 支付手续费率类型(区分借贷)
		LOG.debug("支付手续费率类型(区分借贷)={}", payFeeType);
		boolean d = StringUtils.equals(payFeeType, PayFeeType.D.getCode());
		// 卡为借记卡 支付手续费大于POS借记卡封顶值取封顶值
		boolean cpPosDebitMaxAmt = payFee.compareTo(posDebitMaxAmt) == 1;
		if (d && cpPosDebitMaxAmt) {
			LOG.debug("卡为借记卡 支付手续费大于POS借记卡封顶值取封顶值={}", posDebitMaxAmt);
			pp.setPayFee(posDebitMaxAmt);
		}
		// 卡为信用卡 支付手续费大于POS信用卡封顶值取封顶值
		boolean c = StringUtils.equals(payFeeType, CardType.C.getCode());
		boolean cpPosCreditMaxAmt = payFee.compareTo(posCreditMaxAmt) == 1;
		if (c && cpPosCreditMaxAmt) {
			LOG.debug("卡为信用卡 支付手续费大于POS信用卡封顶值取封顶值={}", posCreditMaxAmt);
			pp.setPayFee(posCreditMaxAmt);
		}
	}

	public void calculateCommAmount(PreTranContext ctx) {
		// 消费返佣 = （消费金额 - 乐币 - 返利金额） * 佣金率 //
		PreTranReqDTO request = ctx.getPreTranReqDTO();
		PayPrepay payPrepay = ctx.getPayPrepay();
		BigDecimal ca = request.getTotalAmount();// 消费金额
		LOG.debug("消费金额={}", ca);
		BigDecimal ba = payPrepay.getCoinAmount();// 乐币
		LOG.debug("乐币={}", ba);
		BigDecimal ga = payPrepay.getGiftAmount();// 返利金额
		LOG.debug("返利金额={}", ga);
		BigDecimal cr = payPrepay.getCommRate().divide(new BigDecimal(100));// 佣金率
		LOG.debug("佣金率={}", cr);
		BigDecimal result = (ca.subtract(ba).subtract(ga)).multiply(cr).setScale(2, RoundingMode.HALF_UP);
		LOG.debug("消费返佣 = （消费金额 - 乐币 - 返利金额） * 佣金率={}", result);
		BigDecimal payFee = ctx.getPayPrepay().getPayFee();
		if (result.compareTo(payFee) == -1) {
			LOG.debug("返佣金额小于支付手续费取支付手续费值");
			payPrepay.setCommAmount(payFee);
		}
		else {
			payPrepay.setCommAmount(result);
		}
	}

	public void calculateMarcketFee(PreTranContext ctx) {
		// 营销费 =消费返佣 - 支付手续费 (营销费计算出来可能为负值，则直接为0)
		PayPrepay payPrepay = ctx.getPayPrepay();
		BigDecimal commAmount = payPrepay.getCommAmount();// 消费返佣
		LOG.debug("消费返佣={}", commAmount);
		BigDecimal payFee = payPrepay.getPayFee();// 支付手续费
		LOG.debug("支付手续费={}", payFee);
		BigDecimal marcketFee = commAmount.subtract(payFee);
		LOG.debug("营销费 =消费返佣 - 支付手续费 (营销费计算出来可能为负值,则直接为0)={}", marcketFee);
		payPrepay.setMarcketFee(marcketFee);
		boolean b = marcketFee.compareTo(BigDecimal.ZERO) == -1; // 营销费小于0
		if (b) {
			LOG.debug("营销费计算出来为负值，设置值为0)");
			payPrepay.setMarcketFee(BigDecimal.ZERO);
		}
	}

	public void calculateMerchantSettleAmount(PreTranContext ctx) {
		// 支付乐豆 + 银行扣款 - 返佣费 - 返利金额
		PayPrepay payPrepay = ctx.getPayPrepay();
		BigDecimal ba = payPrepay.getBeanAmount();// 支付乐豆
		LOG.debug("支付乐豆={}", ba);
		BigDecimal ca = payPrepay.getCashAmount();// 银行扣款
		LOG.debug("银行扣款={}", ca);
		BigDecimal coa = payPrepay.getCommAmount();// 返佣费
		LOG.debug("返佣费={}", coa);
		BigDecimal ga = payPrepay.getGiftAmount();// 返利金额
		LOG.debug("返利金额={}", ga);
		BigDecimal result = ba.add(ca).subtract(coa).subtract(ga);
		LOG.debug("结算金额=支付乐豆 + 银行扣款 - 返佣费 - 返利金额={}", result);
		payPrepay.setMerchantSettleAmount(result);
	}
}
