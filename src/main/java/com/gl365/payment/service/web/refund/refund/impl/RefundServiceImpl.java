package com.gl365.payment.service.web.refund.refund.impl;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.common.Finance;
import com.gl365.payment.dto.base.BaseContext;
import com.gl365.payment.dto.refund.request.RefundReqDTO;
import com.gl365.payment.dto.refund.response.RefundRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.pay.DealStatus;
import com.gl365.payment.enums.pay.OrderType;
import com.gl365.payment.enums.pay.SplitFlag;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayReturn;
import com.gl365.payment.remote.dto.account.Gl365UserAccount;
import com.gl365.payment.service.dbservice.impl.RefundDBService;
import com.gl365.payment.service.web.refund.refund.RefundService;
import com.gl365.payment.service.web.refund.refund.abs.AbstractRefundCalc;
import com.gl365.payment.util.BigDecimalUtil;
import com.gl365.payment.util.CalculationFormula;
@Service
public class RefundServiceImpl extends AbstractRefundCalc implements RefundService {
	private static final Logger LOG = LoggerFactory.getLogger(RefundServiceImpl.class);
	@Autowired
	private RefundDBService refundDBService;

	@Override
	public RefundRespDTO refund(RefundReqDTO reqDTO, RefundRespDTO respDTO) {
		return this.service(reqDTO, respDTO);
	}

	@Override
	public void setTranType(BaseContext<RefundReqDTO, RefundRespDTO> bc) {
		bc.setTranType(TranType.REFUND_PART);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public void query(BaseContext<RefundReqDTO, RefundRespDTO> bc) throws ServiceException {
		RefundReqDTO request = bc.getRequest();
		String origPayId = request.getOrigPayId();
		// Scene scene = getScene(request.getPayChannel());
		// String code = getTranTypeByScene(scene).getCode();
		String terminal = request.getTerminal();
		PayMain pm = payMainMapper.queryByPayIdAndTranTypeAndTerminal(origPayId, null, terminal);
		if (pm != null) {
			bc.setPayMain(pm);
			Gl365UserAccount gl365UserAccount = new Gl365UserAccount();
			gl365UserAccount.setUserId(pm.getUserId());
			bc.setGl365UserAccount(gl365UserAccount);
			bc.setGl365User(this.remoteMicroServiceAgent.queryUserInfoByNewUserId(bc));
		}
	}

	@Override
	public boolean secondCommit(BaseContext<RefundReqDTO, RefundRespDTO> bc) throws ServiceException {
		// 写退货表
		buildPayReturn(bc);
		// 写付款明细
		buildPayDetails(bc);
		// 调用account系统服务
		buildCancelOperate(bc);
		// 更新主付款表
		buildPayMain(bc, bc.getFinance().getOrigRefundType());
		refundDBService.secondCommit(bc);
		return true;
	}

	public TranType getOperateType(BaseContext<RefundReqDTO, RefundRespDTO> bc) {
		boolean result = DealStatus.FULL_RETURN.getCode().equals(bc.getFinance().getRefundType().getCode());
		if (TranType.ONLINE_CONSUME.getCode().equals(bc.getPayMain().getTransType())) {// 网上
			if (result) {
				return TranType.ONLINE_REFUND_ALL;
			}
			else {
				return TranType.ONLINE_REFUND_PART;
			}
		}
		else {
			if (result) {
				return TranType.REFUND_ALL;
			}
			else {
				return TranType.REFUND_PART;
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public boolean firstCommit(BaseContext<RefundReqDTO, RefundRespDTO> bc) throws ServiceException {
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
		LOG.info("#####已退金额={}", returned.getTotalAmount());
		LOG.info("#####本次要退金额={}", bc.getRequest().getTotalAmount());
		BigDecimal totalAmount = bc.getPayMain().getTotalAmount();
		boolean isGroupOrder = OrderType.groupPay.getCode().equals(bc.getPayMain().getOrderType());
		if (isGroupOrder) {
			boolean isMainOrder = StringUtils.equals(bc.getPayMain().getSplitFlag(), SplitFlag.mainOrder.getCode());
			// 如果是群支付用为子单取消费金额
			if (isMainOrder) totalAmount = bc.getPayMain().getGroupMainuserPay();
		}
		LOG.info("#####消费金额={}", totalAmount);
		// 检查已退金额+本次要退金额>消费金额
		if (BigDecimalUtil.compareTo(BigDecimalUtil.add(returned.getTotalAmount(), bc.getRequest().getTotalAmount()), totalAmount) == 1) {
			setReturnResponse(bc, Msg.PAY_8011);
			return false;
		}
		Finance refundable = new Finance();
		boolean isRefundAll = false;
		if (CalculationFormula.ifRefundAll(bc.getRequest().getTotalAmount(), returned.getTotalAmount(), totalAmount)) {
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
		refundable.setBeanAmount(CalculationFormula.calcRefundableBeanAmount(bc.getRequest().getTotalAmount(), totalAmount, bc.getPayMain().getBeanAmount(), returned.getBeanAmount(), isRefundAll));
		refundable.setCashAmount(CalculationFormula.calcRefundableCashAmount(bc.getRequest().getTotalAmount(), bc.getPayMain().getCashAmount(), bc.getPayMain().getCashAmount(), returned.getCashAmount(), refundable.getBeanAmount(), isRefundAll));
		refundable.setGiftAmount(CalculationFormula.calcRefundableGiftAmount(bc.getRequest().getTotalAmount(), totalAmount, bc.getPayMain().getGiftAmount(), returned.getGiftAmount(), isRefundAll));
		refundable.setMarcketFee(CalculationFormula.calcRefundableMarcketFee(bc.getRequest().getTotalAmount(), totalAmount, bc.getPayMain().getMarcketFee(), returned.getMarcketFee(), isRefundAll));
		refundable.setPayFee(CalculationFormula.calcRefundablePayFee(bc.getRequest().getTotalAmount(), totalAmount, bc.getPayMain().getPayFee(), returned.getPayFee(), isRefundAll));
		refundable.setCommAmount(CalculationFormula.calcRefundableCommAmount(bc.getRequest().getTotalAmount(), totalAmount, refundable.getGiftAmount(), bc.getPayMain().getGiftAmount(), returned.getCommAmount(), bc.getPayMain().getCommAmount(), isRefundAll));
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
