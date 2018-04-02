package com.gl365.payment.service.web.confirm.impl;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gl365.payment.common.Finance;
import com.gl365.payment.dto.base.BaseContext;
import com.gl365.payment.dto.onlineconsume.request.OnlineConsumeReqDTO;
import com.gl365.payment.dto.onlineconsume.response.OnlineConsumeRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.pay.DcType;
import com.gl365.payment.enums.pay.DealStatus;
import com.gl365.payment.enums.pay.OnLinePay;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.remote.dto.merchant.Gl365Merchant;
import com.gl365.payment.service.dbservice.impl.OnlineConsumeDBService;
import com.gl365.payment.service.web.confirm.OnlineConsumeService;
import com.gl365.payment.service.web.confirm.abs.AbstractOnlineConsumeParticipant;
import com.gl365.payment.util.BigDecimalUtil;
import com.gl365.payment.util.CalculationFormula;
@Service("onlineConsumeParticipantService")
public class OnlineConsumeParticipantServiceImpl extends AbstractOnlineConsumeParticipant implements OnlineConsumeService {
	private static final Logger LOG = LoggerFactory.getLogger(OnlineConsumeParticipantServiceImpl.class);
	@Autowired
	private OnlineConsumeDBService onlineConsumeDBService;

	@Override
	public OnlineConsumeRespDTO onlineConsume(OnlineConsumeReqDTO reqDTO, OnlineConsumeRespDTO respDTO) {
		return this.service(reqDTO, respDTO);
	}

	@Override
	public void setTranType(BaseContext<OnlineConsumeReqDTO, OnlineConsumeRespDTO> bc) {
		bc.setTranType(TranType.ONLINE_CONSUME);
	}

	@Override
	public boolean bizCheck(BaseContext<OnlineConsumeReqDTO, OnlineConsumeRespDTO> bc) throws ServiceException {
		Gl365Merchant gl365Merchant = bc.getGl365Merchant();
		String onLinePayment = gl365Merchant.getOnLinePayment();
		boolean b = StringUtils.equals(onLinePayment, OnLinePay.POS.getCode());
		if (b) {
			setReturnResponse(bc, Msg.PAY_8021);
			return false;
		}
		BigDecimal totalAmount = bc.getRequest().getTotalAmount();
		boolean m = (totalAmount.compareTo(BigDecimal.ZERO) == 1);
		if (!m) {
			setReturnResponse(bc, Msg.REQ_0021);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean firstCommit(BaseContext<OnlineConsumeReqDTO, OnlineConsumeRespDTO> bc) throws ServiceException {
		String enableLdSale = bc.getGroupMerchant().getLdSale(); // 商户允许乐豆支付:0:不允许,1:允许
		boolean enableHappyCoin = bc.getGl365User().isEnableHappycoin(); // 检查用户是允许乐豆支付？
		boolean useBeanAmount = false;
		// 消费金额〈=1元 /*检查用户是允许乐豆支付？*/
		boolean greaterOrEqual = BigDecimalUtil.GreaterOrEqual(bc.getRequest().getTotalAmount(), ONE);
		if ("1".equals(enableLdSale) && greaterOrEqual && enableHappyCoin) useBeanAmount = true;
		LOG.info("使用乐豆支付 ={},商户乐豆支付开关 ={},用户乐豆支付开关 ={}", useBeanAmount, enableLdSale, enableHappyCoin);
		Finance finance = bc.getFinance();
		OnlineConsumeReqDTO request = bc.getRequest();
		BigDecimal cashAmt = request.getTotalAmount();
		if (useBeanAmount) this.beanPay(bc);
		else finance.setCashAmount(cashAmt);
		BigDecimal cashAmount = finance.getCashAmount();
		BigDecimal beanAmount = finance.getBeanAmount();
		LOG.debug("乐豆={},银行扣款={}", beanAmount, cashAmount);
		boolean cal = calculation(bc);
		if (!cal) {
			return false;
		}
		// 写交易主表
		buildPayMain(bc, DealStatus.WAITING_FOR_PAYMENT);
		// 写付款表
		buildPayDetails(bc);
		onlineConsumeDBService.firstCommit(bc);
		return true;
	}
	
	private boolean calculation(BaseContext<OnlineConsumeReqDTO, OnlineConsumeRespDTO> bc) {
		// 计算返佣金额、营销费、支付手续费
		Finance finance = bc.getFinance();
		int cardType = 1;
		if (DcType.C.getCode().equals(bc.getGl365UserAccount().getCardType())) {
			cardType = 2;
			bc.getFinance().setFeeRate(bc.getGroupMerchant().getOnpayCreditFeeRate());
			bc.getFinance().setMaxAmt(bc.getGroupMerchant().getOnpayCreditMaxAmt());
		}
		else {
			bc.getFinance().setFeeRate(bc.getGroupMerchant().getOnpayDebitFeeRate());
			bc.getFinance().setMaxAmt(bc.getGroupMerchant().getOnpayDebitMaxAmt());
		}
		// 支付手续费
		BigDecimal cashAmount = finance.getCashAmount();
		BigDecimal feeRate = finance.getFeeRate();
		BigDecimal maxAmt = finance.getMaxAmt();
		BigDecimal payFee = CalculationFormula.calcPayFee(cashAmount, 0, cardType, feeRate, maxAmt);
		finance.setPayFee(payFee);
		// 返佣金额
		finance.setCommAmount(payFee);
		BigDecimal commRate = CalculationFormula.calcCommRate(bc.getRequest().getTotalAmount(), BigDecimal.ZERO, finance.getGiftAmount(), finance.getCommAmount());
		bc.getGroupMerchant().setGlFeeRate(commRate);
		// 营销费
		finance.setMarcketFee(BigDecimal.ZERO);
		// 商户实得金额
		BigDecimal beanAmount = finance.getBeanAmount();
		BigDecimal msAmt = CalculationFormula.calcMerchantSettlAmount(beanAmount, cashAmount, finance.getCommAmount(), finance.getGiftAmount());
		finance.setMerchantSettlAmount(msAmt);
		BigDecimal marcketFee = finance.getMarcketFee();
		BigDecimal payFee2 = finance.getPayFee();
		BigDecimal setAmt = finance.getMerchantSettlAmount();
		LOG.debug("返利={},返佣金额={},营销费={},支付手续费={},商户实得金额={}", finance.getGiftAmount(), payFee, marcketFee, payFee2, setAmt);
		return true;
	}

	@Override
	public boolean secondCommit(BaseContext<OnlineConsumeReqDTO, OnlineConsumeRespDTO> bc) throws ServiceException {
		// 调用account系统服务
		buildUpdateAccountBalanceOffLineReqDTO(bc);
		// 更新交易主表
		PayMain payResult = payAdapter.clonePayMain(bc.getPayMain(), DealStatus.ALREADY_PAID, null);
		payResult.setPayTime(LocalDateTime.now());
		bc.setPayMain(payResult);
		onlineConsumeDBService.secondCommit(bc);
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
