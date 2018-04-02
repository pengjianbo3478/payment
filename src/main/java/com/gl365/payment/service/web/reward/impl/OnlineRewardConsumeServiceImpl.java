package com.gl365.payment.service.web.reward.impl;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gl365.payment.common.Finance;
import com.gl365.payment.dto.base.BaseContext;
import com.gl365.payment.dto.onlineconsume.request.OnlineRewardConsumeReqDTO;
import com.gl365.payment.dto.onlineconsume.response.OnlineConsumeRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.pay.DcType;
import com.gl365.payment.enums.pay.DealStatus;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.enums.system.OrganCode;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.remote.dto.settlement.request.ConfirmPreSettleDateReqDTO;
import com.gl365.payment.remote.dto.settlement.response.ConfirmPreSettleDateRespDTO;
import com.gl365.payment.service.dbservice.PayMainService;
import com.gl365.payment.service.dbservice.impl.OnlineConsumeDBService;
import com.gl365.payment.service.web.confirm.impl.OnlineConsumeServiceImpl;
import com.gl365.payment.service.web.reward.OnlineRewardConsumeService;
import com.gl365.payment.service.web.reward.abs.AbstractOnlineRewardConsume;
import com.gl365.payment.util.CalculationFormula;
@Service
public class OnlineRewardConsumeServiceImpl extends AbstractOnlineRewardConsume implements OnlineRewardConsumeService {
	private static final Logger LOG = LoggerFactory.getLogger(OnlineConsumeServiceImpl.class);
	@Autowired
	private OnlineConsumeDBService onlineConsumeDBService;
	@Autowired
	private PayMainService payMainService;

	@Override
	public OnlineConsumeRespDTO onlineRewardConsume(OnlineRewardConsumeReqDTO reqDTO, OnlineConsumeRespDTO respDTO) {
		return this.service(reqDTO, respDTO);
	}

	@Override
	public void setTranType(BaseContext<OnlineRewardConsumeReqDTO, OnlineConsumeRespDTO> bc) {
		bc.setTranType(TranType.ONLINE_CONSUME);
	}

	@Override
	public boolean bizCheck(BaseContext<OnlineRewardConsumeReqDTO, OnlineConsumeRespDTO> bc) throws ServiceException {
		BigDecimal totalAmount = bc.getRequest().getTotalAmount();
		boolean m = (totalAmount.compareTo(BigDecimal.ZERO) == 1);
		if (!m) {
			setReturnResponse(bc, Msg.REQ_0021);
			return false;
		}
		return true;
	}

	@Override
	public boolean firstCommit(BaseContext<OnlineRewardConsumeReqDTO, OnlineConsumeRespDTO> bc) throws ServiceException {
		Finance finance = bc.getFinance();
		BigDecimal beanAmount = bc.getRequest().getPayldmoney();
		BigDecimal cashAmount = bc.getRequest().getTotalAmount().subtract(bc.getRequest().getPayldmoney());
		finance.setBeanAmount(beanAmount);
		finance.setCashAmount(cashAmount);
		calculation(bc, finance, beanAmount);
		this.buildPayMain(bc, DealStatus.WAITING_FOR_PAYMENT);
		this.buildPayDetails(bc);
		this.onlineConsumeDBService.firstCommit(bc);
		return true;
	}

	private void calculation(BaseContext<OnlineRewardConsumeReqDTO, OnlineConsumeRespDTO> bc, Finance finance, BigDecimal beanAmount) {
		BigDecimal cashAmount = finance.getCashAmount();
		LOG.info("乐豆={},银行扣款={}", beanAmount, cashAmount);
		// 计算返佣金额、营销费、支付手续费
		int cardType = 1;
		if (DcType.C.getCode().equals(bc.getGl365UserAccount().getCardType())) {
			cardType = 2;
			finance.setFeeRate(bc.getGl365Merchant().getOnpayCreditFeeRate());
			finance.setMaxAmt(bc.getGl365Merchant().getOnpayCreditMaxAmt());
		}
		else {
			finance.setFeeRate(bc.getGl365Merchant().getOnpayDebitFeeRate());
			finance.setMaxAmt(bc.getGl365Merchant().getOnpayDebitMaxAmt());
		}
		// 支付手续费
		BigDecimal feeRate = finance.getFeeRate();
		BigDecimal maxAmt = finance.getMaxAmt();
		BigDecimal payFee = CalculationFormula.calcPayFee(cashAmount, 0, cardType, feeRate, maxAmt);
		finance.setPayFee(payFee);
		// 返佣金额
		finance.setCommAmount(payFee);
		BigDecimal commRate = CalculationFormula.calcCommRate(bc.getRequest().getTotalAmount(), BigDecimal.ZERO, finance.getGiftAmount(), finance.getCommAmount());
		bc.getGl365Merchant().setGlFeeRate(commRate);
		// 营销费
		BigDecimal marketFee = BigDecimal.ZERO;
		finance.setMarcketFee(marketFee);
		// 商户实得金额
		BigDecimal giftAmount = finance.getGiftAmount();
		BigDecimal commAmount = finance.getCommAmount();
		BigDecimal calcMerchantSettlAmount = CalculationFormula.calcMerchantSettlAmount(beanAmount, cashAmount, commAmount, giftAmount);
		finance.setMerchantSettlAmount(calcMerchantSettlAmount);
		BigDecimal marcketFee = finance.getMarcketFee();
		BigDecimal merchantSettlAmount = finance.getMerchantSettlAmount();
		LOG.info("返利={},返佣金额={},营销费={},支付手续费={},商户实得金额={}", giftAmount, commAmount, marcketFee, payFee, merchantSettlAmount);
	}

	@Override
	public boolean secondCommit(BaseContext<OnlineRewardConsumeReqDTO, OnlineConsumeRespDTO> bc) throws ServiceException {
		beanTransfer(bc);
		PayMain payMain = payAdapter.clonePayMain(bc.getPayMain(), DealStatus.ALREADY_PAID, null);
		bc.setPayMain(payMain);
		ConfirmPreSettleDateReqDTO request = new ConfirmPreSettleDateReqDTO();
		request.setOrganCode(OrganCode.FFT.getCode());
		request.setPayId(bc.getPayMain().getPayId());
		request.setTransType("1");
		request.setOrganPayTime(bc.getPayMain().getPayTime());
		ConfirmPreSettleDateRespDTO result = remoteMicroServiceAgent.getConfirmPreSettleDate(request);
		LocalDate preSettleDate = result.getData().getPreSettleDate();
		payMain.setPreSettleDate(preSettleDate);
		payMainService.updateByPayId(payMain);
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
