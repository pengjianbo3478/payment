package com.gl365.payment.service.web.refund.query.abs;
import java.math.BigDecimal;
import com.gl365.payment.common.Finance;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.util.CalculationFormula;
public abstract class AbstractRefundQueryCalc extends AbstractRefundQueryBuild {
	public BigDecimal g(Finance refundable) {
		BigDecimal beanAmount = refundable.getBeanAmount();
		BigDecimal cashAmount = refundable.getCashAmount();
		BigDecimal commAmount = refundable.getCommAmount();
		BigDecimal giftAmount = refundable.getGiftAmount();
		return CalculationFormula.calcMerchantSettlAmount(beanAmount, cashAmount, commAmount, giftAmount);
	}

	public BigDecimal f(PayMain payMain, Finance returned, BigDecimal totalAmount, Finance refundable, boolean isRefundAll) {
		BigDecimal totalAmount2 = payMain.getTotalAmount();
		BigDecimal giftAmount = refundable.getGiftAmount();
		BigDecimal giftAmount2 = payMain.getGiftAmount();
		BigDecimal commAmount = returned.getCommAmount();
		BigDecimal commAmount2 = payMain.getCommAmount();
		return CalculationFormula.calcRefundableCommAmount(totalAmount, totalAmount2, giftAmount, giftAmount2, commAmount, commAmount2, isRefundAll);
	}

	public BigDecimal e(PayMain payMain, Finance returned, BigDecimal totalAmount, boolean isRefundAll) {
		BigDecimal totalAmount2 = payMain.getTotalAmount();
		BigDecimal payFee = payMain.getPayFee();
		BigDecimal payFee2 = returned.getPayFee();
		return CalculationFormula.calcRefundablePayFee(totalAmount, totalAmount2, payFee, payFee2, isRefundAll);
	}

	public BigDecimal d(PayMain payMain, Finance returned, BigDecimal totalAmount, boolean isRefundAll) {
		BigDecimal totalAmount2 = payMain.getTotalAmount();
		BigDecimal marcketFee = payMain.getMarcketFee();
		BigDecimal marcketFee2 = returned.getMarcketFee();
		return CalculationFormula.calcRefundableMarcketFee(totalAmount, totalAmount2, marcketFee, marcketFee2, isRefundAll);
	}

	public BigDecimal c(PayMain payMain, Finance returned, BigDecimal totalAmount, boolean isRefundAll) {
		BigDecimal totalAmount2 = payMain.getTotalAmount();
		BigDecimal giftAmount = payMain.getGiftAmount();
		BigDecimal giftAmount2 = returned.getGiftAmount();
		return CalculationFormula.calcRefundableGiftAmount(totalAmount, totalAmount2, giftAmount, giftAmount2, isRefundAll);
	}

	public BigDecimal b(PayMain payMain, Finance returned, BigDecimal totalAmount, Finance refundable, boolean isRefundAll) {
		BigDecimal cashAmount = payMain.getCashAmount();
		BigDecimal cashAmount2 = returned.getCashAmount();
		BigDecimal beanAmount = refundable.getBeanAmount();
		return CalculationFormula.calcRefundableCashAmount(totalAmount, cashAmount, cashAmount, cashAmount2, beanAmount, isRefundAll);
	}

	public BigDecimal a(PayMain payMain, Finance returned, BigDecimal totalAmount, boolean isRefundAll) {
		BigDecimal totalAmount2 = payMain.getTotalAmount();
		BigDecimal beanAmount = payMain.getBeanAmount();
		BigDecimal beanAmount2 = returned.getBeanAmount();
		return CalculationFormula.calcRefundableBeanAmount(totalAmount, totalAmount2, beanAmount, beanAmount2, isRefundAll);
	}

	public void put(PayMain payMain, Finance returned, BigDecimal totalAmount, Finance refundable, boolean isRefundAll) {
		refundable.setBeanAmount(a(payMain, returned, totalAmount, isRefundAll));
		refundable.setCashAmount(b(payMain, returned, totalAmount, refundable, isRefundAll));
		refundable.setGiftAmount(c(payMain, returned, totalAmount, isRefundAll));
		refundable.setMarcketFee(d(payMain, returned, totalAmount, isRefundAll));
		refundable.setPayFee(e(payMain, returned, totalAmount, isRefundAll));
		refundable.setCommAmount(f(payMain, returned, totalAmount, refundable, isRefundAll));
		refundable.setMerchantSettlAmount(g(refundable));
		refundable.setTotalAmount(totalAmount);
	}
}
