package com.gl365.payment.service.web.refund.refund.abs;
import java.math.BigDecimal;
import com.gl365.payment.common.Finance;
import com.gl365.payment.dto.base.BaseContext;
import com.gl365.payment.dto.refund.request.RefundReqDTO;
import com.gl365.payment.dto.refund.response.RefundRespDTO;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.util.CalculationFormula;
public abstract class AbstractRefundCalc extends AbstractRefundBuild {
	
	public void put(BaseContext<RefundReqDTO, RefundRespDTO> bc, Finance returned, Finance refundable, boolean isRefundAll) {
		refundable.setBeanAmount(a(bc, returned, isRefundAll));
		refundable.setCashAmount(b(bc, returned, refundable, isRefundAll));
		refundable.setGiftAmount(c(bc, returned, isRefundAll));
		refundable.setMarcketFee(d(bc, returned, isRefundAll));
		refundable.setPayFee(e(bc, returned, isRefundAll));
		refundable.setCommAmount(f(bc, returned, refundable, isRefundAll));
		refundable.setMerchantSettlAmount(g(refundable));
		refundable.setTotalAmount(bc.getRequest().getTotalAmount());
	}

	public BigDecimal g(Finance refundable) {
		BigDecimal beanAmount = refundable.getBeanAmount();
		BigDecimal cashAmount = refundable.getCashAmount();
		BigDecimal commAmount = refundable.getCommAmount();
		BigDecimal giftAmount = refundable.getGiftAmount();
		return CalculationFormula.calcMerchantSettlAmount(beanAmount, cashAmount, commAmount, giftAmount);
	}

	public BigDecimal f(BaseContext<RefundReqDTO, RefundRespDTO> bc, Finance returned, Finance refundable, boolean isRefundAll) {
		RefundReqDTO request = bc.getRequest();
		BigDecimal totalAmount = request.getTotalAmount();
		PayMain payMain = bc.getPayMain();
		BigDecimal totalAmount2 = payMain.getTotalAmount();
		BigDecimal giftAmount = refundable.getGiftAmount();
		BigDecimal giftAmount2 = payMain.getGiftAmount();
		BigDecimal commAmount = returned.getCommAmount();
		BigDecimal commAmount2 = payMain.getCommAmount();
		return CalculationFormula.calcRefundableCommAmount(totalAmount, totalAmount2, giftAmount, giftAmount2, commAmount, commAmount2, isRefundAll);
	}

	public BigDecimal e(BaseContext<RefundReqDTO, RefundRespDTO> bc, Finance returned, boolean isRefundAll) {
		RefundReqDTO request = bc.getRequest();
		BigDecimal totalAmount = request.getTotalAmount();
		PayMain payMain = bc.getPayMain();
		BigDecimal totalAmount2 = payMain.getTotalAmount();
		BigDecimal payFee = payMain.getPayFee();
		BigDecimal payFee2 = returned.getPayFee();
		return CalculationFormula.calcRefundablePayFee(totalAmount, totalAmount2, payFee, payFee2, isRefundAll);
	}

	public BigDecimal d(BaseContext<RefundReqDTO, RefundRespDTO> bc, Finance returned, boolean isRefundAll) {
		RefundReqDTO request = bc.getRequest();
		BigDecimal totalAmount = request.getTotalAmount();
		PayMain payMain = bc.getPayMain();
		BigDecimal totalAmount2 = payMain.getTotalAmount();
		BigDecimal marcketFee = payMain.getMarcketFee();
		BigDecimal marcketFee2 = returned.getMarcketFee();
		return CalculationFormula.calcRefundableMarcketFee(totalAmount, totalAmount2, marcketFee, marcketFee2, isRefundAll);
	}

	public BigDecimal c(BaseContext<RefundReqDTO, RefundRespDTO> bc, Finance returned, boolean isRefundAll) {
		RefundReqDTO request = bc.getRequest();
		BigDecimal totalAmount = request.getTotalAmount();
		PayMain payMain = bc.getPayMain();
		BigDecimal totalAmount2 = payMain.getTotalAmount();
		BigDecimal giftAmount = payMain.getGiftAmount();
		BigDecimal giftAmount2 = returned.getGiftAmount();
		return CalculationFormula.calcRefundableGiftAmount(totalAmount, totalAmount2, giftAmount, giftAmount2, isRefundAll);
	}

	public BigDecimal b(BaseContext<RefundReqDTO, RefundRespDTO> bc, Finance returned, Finance refundable, boolean isRefundAll) {
		RefundReqDTO request = bc.getRequest();
		BigDecimal totalAmount = request.getTotalAmount();
		BigDecimal cashAmount = bc.getPayMain().getCashAmount();
		BigDecimal cashAmount2 = returned.getCashAmount();
		BigDecimal beanAmount = refundable.getBeanAmount();
		return CalculationFormula.calcRefundableCashAmount(totalAmount, cashAmount, cashAmount, cashAmount2, beanAmount, isRefundAll);
	}

	public BigDecimal a(BaseContext<RefundReqDTO, RefundRespDTO> bc, Finance returned, boolean isRefundAll) {
		RefundReqDTO request = bc.getRequest();
		BigDecimal totalAmount = request.getTotalAmount();
		PayMain payMain = bc.getPayMain();
		BigDecimal totalAmount2 = payMain.getTotalAmount();
		BigDecimal beanAmount = payMain.getBeanAmount();
		BigDecimal beanAmount2 = returned.getBeanAmount();
		return CalculationFormula.calcRefundableBeanAmount(totalAmount, totalAmount2, beanAmount, beanAmount2, isRefundAll);
	}
}
