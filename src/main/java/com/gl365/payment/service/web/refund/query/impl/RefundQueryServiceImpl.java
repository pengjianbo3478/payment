package com.gl365.payment.service.web.refund.query.impl;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.common.Finance;
import com.gl365.payment.dto.base.BaseContext;
import com.gl365.payment.dto.refund.query.request.RefundQueryReqDTO;
import com.gl365.payment.dto.refund.query.response.RefundQueryRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.pay.DealStatus;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayReturn;
import com.gl365.payment.service.web.refund.query.RefundQueryService;
import com.gl365.payment.service.web.refund.query.abs.AbstractRefundQueryCalc;
import com.gl365.payment.util.BigDecimalUtil;
import com.gl365.payment.util.CalculationFormula;
@Service
public class RefundQueryServiceImpl extends AbstractRefundQueryCalc implements RefundQueryService {
	@Override
	public RefundQueryRespDTO refundQuery(RefundQueryReqDTO reqDTO, RefundQueryRespDTO respDTO) {
		return this.service(reqDTO, respDTO);
	}

	@Override
	public void setTranType(BaseContext<RefundQueryReqDTO, RefundQueryRespDTO> bc) {
		bc.setTranType(TranType.REFUND_QUERY);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public void query(BaseContext<RefundQueryReqDTO, RefundQueryRespDTO> bc) throws ServiceException {
		RefundQueryReqDTO request = bc.getRequest();
		String origPayId = request.getOrigPayId();
		// Scene scene = getScene(request.getPayChannel());
		// String code = getTranTypeByScene(scene).getCode();
		String terminal = request.getTerminal();
		PayMain pm = payMainMapper.queryByPayIdAndTranTypeAndTerminal(origPayId, null, terminal);
		if (pm != null) bc.setPayMain(pm);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	@Override
	public boolean secondCommit(BaseContext<RefundQueryReqDTO, RefundQueryRespDTO> bc) throws ServiceException {
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	@Override
	public boolean firstCommit(BaseContext<RefundQueryReqDTO, RefundQueryRespDTO> bc) throws ServiceException {
		// 已退汇总
		List<PayReturn> payReturns = payReturnMapper.queryByOrigPayId(bc.getPayMain().getPayId());
		Finance returned = new Finance();
		if (payReturns != null && payReturns.size() > 0) {
			for (PayReturn pr : payReturns) {
				returned.setTotalAmount(BigDecimalUtil.add(returned.getTotalAmount(), pr.getTotalAmount()));
				returned.setBeanAmount(BigDecimalUtil.add(returned.getBeanAmount(), pr.getBeanAmount()));
				returned.setCashAmount(BigDecimalUtil.add(returned.getCashAmount(), pr.getCashAmount()));
				returned.setMarcketFee(BigDecimalUtil.add(returned.getMarcketFee(), pr.getMarcketFee()));
				returned.setCoinAmount(BigDecimalUtil.add(returned.getCoinAmount(), pr.getCoinAmount()));
				returned.setGiftAmount(BigDecimalUtil.add(returned.getGiftAmount(), pr.getGiftAmount()));
				returned.setCommAmount(BigDecimalUtil.add(returned.getCommAmount(), pr.getCommAmount()));
				returned.setPayFee(BigDecimalUtil.add(returned.getPayFee(), pr.getPayFee()));
				returned.setGiftPoint(BigDecimalUtil.add(returned.getGiftPoint(), pr.getGiftPoint()));
				returned.setMerchantSettlAmount(BigDecimalUtil.add(returned.getMerchantSettlAmount(), pr.getMerchantSettleAmount()));
			}
		}
		// 检查已退金额+本次要退金额>消费金额
		if (BigDecimalUtil.compareTo(BigDecimalUtil.add(returned.getTotalAmount(), bc.getRequest().getTotalAmount()), bc.getPayMain().getTotalAmount()) == 1) {
			setReturnResponse(bc, Msg.PAY_8011);
			return false;
		}
		Finance refundable = new Finance();
		boolean isRefundAll = false;
		if (CalculationFormula.ifRefundAll(bc.getRequest().getTotalAmount(), returned.getTotalAmount(), bc.getPayMain().getTotalAmount())) {
			refundable.setOrigRefundType(DealStatus.FULL_RETURN);
			isRefundAll = true;
			// 之前从未退过货，则本次退货是全额退货，否则是部分退货
			if (BigDecimalUtil.GreaterThan0(returned.getTotalAmount())) {
				refundable.setRefundType(DealStatus.PARTIAL_RETURN);
			}
			else {
				refundable.setRefundType(DealStatus.FULL_RETURN);
			}
		}
		else {
			refundable.setOrigRefundType(DealStatus.PARTIAL_RETURN);
			refundable.setRefundType(DealStatus.PARTIAL_RETURN);
		}
		refundable.setBeanAmount(CalculationFormula.calcRefundableBeanAmount(bc.getRequest().getTotalAmount(), bc.getPayMain().getTotalAmount(), bc.getPayMain().getBeanAmount(), returned.getBeanAmount(), isRefundAll));
		refundable.setCashAmount(CalculationFormula.calcRefundableCashAmount(bc.getRequest().getTotalAmount(), bc.getPayMain().getCashAmount(), bc.getPayMain().getCashAmount(), returned.getCashAmount(), refundable.getBeanAmount(), isRefundAll));
		refundable.setGiftAmount(CalculationFormula.calcRefundableGiftAmount(bc.getRequest().getTotalAmount(), bc.getPayMain().getTotalAmount(), bc.getPayMain().getGiftAmount(), returned.getGiftAmount(), isRefundAll));
		refundable.setMarcketFee(CalculationFormula.calcRefundableMarcketFee(bc.getRequest().getTotalAmount(), bc.getPayMain().getTotalAmount(), bc.getPayMain().getMarcketFee(), returned.getMarcketFee(), isRefundAll));
		refundable.setPayFee(CalculationFormula.calcRefundablePayFee(bc.getRequest().getTotalAmount(), bc.getPayMain().getTotalAmount(), bc.getPayMain().getPayFee(), returned.getPayFee(), isRefundAll));
		refundable.setCommAmount(CalculationFormula.calcRefundableCommAmount(bc.getRequest().getTotalAmount(), bc.getPayMain().getTotalAmount(), refundable.getGiftAmount(), bc.getPayMain().getGiftAmount(), returned.getCommAmount(), bc.getPayMain().getCommAmount(), isRefundAll));
		refundable.setMerchantSettlAmount(CalculationFormula.calcMerchantSettlAmount(refundable.getBeanAmount(), refundable.getCashAmount(), refundable.getCommAmount(), refundable.getGiftAmount()));
		refundable.setTotalAmount(bc.getRequest().getTotalAmount());
		bc.setFinance(refundable);
		return true;
	}
	
	@Override
	public boolean isSendPaymentResultMQ() {
		return true;
	}

	@Override
	public boolean isSendRewardResultMQ() {
		return false;
	}
}
