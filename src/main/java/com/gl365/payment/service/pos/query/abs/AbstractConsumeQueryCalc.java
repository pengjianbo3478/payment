package com.gl365.payment.service.pos.query.abs;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.gl365.payment.dto.pretranscation.PreTranContext;
import com.gl365.payment.enums.merchant.MerchantPayBean;
import com.gl365.payment.model.PayPrepay;
import com.gl365.payment.remote.dto.account.response.QueryAccountBalanceInfoRespDTO;
import com.gl365.payment.service.pos.query.PreTranCalcuateService;
public abstract class AbstractConsumeQueryCalc extends AbstractConsumeQueryCheck {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractConsumeQueryCalc.class);
	@Autowired
	private PreTranCalcuateService preTranCalcuateService;

	public void calculateBeanAmount(PreTranContext ctx) {
		if (!this.checkUserPayBean(ctx)) return;
		if (!this.checkMerchantPayBean(ctx)) return;
		if (this.lessOnePay(ctx)) return;
		this.greaterOnePay(ctx);
	}

	public void calculate(PreTranContext ctx) {
		this.preTranCalcuateService.calculatePayFeeRate(ctx);
		this.preTranCalcuateService.calculateCommAmount(ctx);
		this.preTranCalcuateService.calculateMarcketFee(ctx);
		this.preTranCalcuateService.calculateMerchantSettleAmount(ctx);
	}

	public boolean checkMerchantPayBean(PreTranContext ctx) {
		String ldSale = ctx.getGl365Merchant().getLdSale();
		PayPrepay payPrepay = ctx.getPayPrepay();
		LOG.info("商家乐豆支付开关 ={}", ldSale);
		if (StringUtils.equals(ldSale, MerchantPayBean.ON.getKey())) return true;
		payPrepay.setBeanAmount(BigDecimal.ZERO);
		BigDecimal cAmt = ctx.getPreTranReqDTO().getTotalAmount();
		payPrepay.setCashAmount(cAmt);
		LOG.debug("商家不允乐豆支付|乐豆抵扣金额={}|现金消费金额={}", BigDecimal.ZERO, cAmt);
		return false;
	}

	public boolean lessOnePay(PreTranContext ctx) {
		final BigDecimal tAmt = ctx.getPreTranReqDTO().getTotalAmount();
		int res = tAmt.compareTo(BigDecimal.ONE);
		PayPrepay payPrepay = ctx.getPayPrepay();
		// 消费金额〈=1元时不支付乐豆
		if (res == 1) return false;
		LOG.debug("消费金额〈=1元不支付乐豆|乐豆抵扣金额={}|现金消费金额={}", BigDecimal.ZERO, tAmt);
		payPrepay.setBeanAmount(BigDecimal.ZERO);
		payPrepay.setCashAmount(tAmt);
		return true;
	}

	public void greaterOnePay(PreTranContext ctx) {
		final BigDecimal cAmt = ctx.getPreTranReqDTO().getTotalAmount();
		PayPrepay pp = ctx.getPayPrepay();
		QueryAccountBalanceInfoRespDTO accDto = ctx.getQueryAccountBalanceInfoRespDTO();
		BigDecimal bAmt = accDto.getBalance();
		LOG.info("用户乐豆账户余额={}|乐豆可抵扣金额={}", bAmt, bAmt);
		int ctResult = bAmt.compareTo(cAmt.subtract(BigDecimal.ONE));
		if (ctResult == -1) {
			pp.setBeanAmount(bAmt);
			BigDecimal cash = cAmt.subtract(bAmt).setScale(2, RoundingMode.HALF_UP);
			pp.setCashAmount(cash);
			LOG.debug("可支付乐豆小于消费金额|扣全部乐豆|现金消费金额=消费金额-乐豆抵扣金额|乐豆抵扣金额={}|现金消费金额={}", bAmt, cash);
		}
		else {
			BigDecimal bamt = cAmt.subtract(BigDecimal.ONE);
			LOG.debug("可支付乐豆大于消费金额|现金消费金额=现金最少支付1元|乐豆抵扣金额={}|现金消费金额={}", bamt, BigDecimal.ONE);
			pp.setBeanAmount(bamt);
			pp.setCashAmount(BigDecimal.ONE);
		}
		LOG.info("乐豆抵扣金额={}|现金消费金额 ={} ", pp.getBeanAmount(), pp.getCashAmount());
	}

	public boolean checkUserPayBean(PreTranContext ctx) {
		boolean c = ctx.getGl365User().isEnableHappycoin();
		PayPrepay pp = ctx.getPayPrepay();
		LOG.info("用户乐豆支付开关 ={}", c);
		if (c) return true;
		// 用户乐豆支付关闭时不使用乐豆支付
		pp.setBeanAmount(BigDecimal.ZERO);
		BigDecimal cAmt = ctx.getPreTranReqDTO().getTotalAmount();
		pp.setCashAmount(cAmt);
		LOG.debug("用户乐豆支付关闭时不使用乐豆支付|乐豆抵扣金额={}|现金消费金额={}", BigDecimal.ZERO, cAmt);
		return false;
	}
}
