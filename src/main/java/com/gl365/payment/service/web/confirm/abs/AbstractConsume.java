package com.gl365.payment.service.web.confirm.abs;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.gl365.payment.common.Finance;
import com.gl365.payment.dto.base.BaseContext;
import com.gl365.payment.dto.onlineconsume.request.OnlineConsumeReqDTO;
import com.gl365.payment.dto.onlineconsume.response.OnlineConsumeRespDTO;
import com.gl365.payment.enums.pay.DcType;
import com.gl365.payment.enums.pay.DealStatus;
import com.gl365.payment.enums.system.Agent;
import com.gl365.payment.enums.system.OrganCode;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayDetail;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.remote.dto.account.request.QueryAccountTotalBalanceReqDTO;
import com.gl365.payment.remote.dto.account.request.UpdateAccountBalanceOffLineReqDTO;
import com.gl365.payment.remote.dto.account.response.QueryAccountTotalBalanceRespDTO;
import com.gl365.payment.service.transaction.AbstractTranscation;
import com.gl365.payment.util.BigDecimalUtil;
import com.gl365.payment.util.FormatUtil;
import com.gl365.payment.util.StringParseUtil;
public abstract class AbstractConsume extends AbstractTranscation<OnlineConsumeReqDTO, OnlineConsumeRespDTO> {
	@Override
	public PayStream buildPayStream(BaseContext<OnlineConsumeReqDTO, OnlineConsumeRespDTO> bc) {
		PayStream ps = payAdapter.buildPayStream(bc);
		ps.setRequestId(bc.getRequest().getRequestId());
		ps.setRequestDate(FormatUtil.parseYyyyMMdd(bc.getRequest().getRequestDate()));
		ps.setOrganCode(bc.getRequest().getOrganCode());
		ps.setOrganMerchantNo(bc.getRequest().getOrganMerchantNo());
		ps.setTerminal(bc.getRequest().getTerminal());
		ps.setTotalAmount(bc.getRequest().getTotalAmount());
		ps.setReturnAmount(BigDecimal.ZERO);
		ps.setUniqueSerial(FormatUtil.mergeString(bc.getRequest().getTerminal(), bc.getRequest().getRequestId()));
		bc.setPayStream(ps);
		return ps;
	}
	
	public void beanPay(BaseContext<OnlineConsumeReqDTO, OnlineConsumeRespDTO> bc) {
		// 调帐户系统余额交易(线上)
		QueryAccountTotalBalanceReqDTO reqDTO = new QueryAccountTotalBalanceReqDTO(bc.getGl365UserAccount().getUserId());
		QueryAccountTotalBalanceRespDTO response = this.remoteMicroServiceAgent.queryAccountTotalBalance(reqDTO.getUserId());
		BigDecimal accountTotalBalance = response.getResultData();
		// 计算支付明细,规则：优先使用可用的乐豆 // if 乐豆余额>=(消费金额-1) then
		OnlineConsumeReqDTO request = bc.getRequest();
		BigDecimal c = BigDecimalUtil.subtract(request.getTotalAmount(), ONE);
		boolean greaterOrEqual2 = BigDecimalUtil.GreaterOrEqual(accountTotalBalance, c);
		Finance finance = bc.getFinance();
		if (greaterOrEqual2) {
			BigDecimal totalAmount = request.getTotalAmount();
			BigDecimal beanAmt = BigDecimalUtil.subtract(totalAmount, ONE);
			finance.setBeanAmount(beanAmt);
		}
		else {
			finance.setBeanAmount(accountTotalBalance);
		}
		BigDecimal totalAmount = request.getTotalAmount();
		BigDecimal beanAmount = finance.getBeanAmount();
		BigDecimal cashAmt = BigDecimalUtil.subtract(totalAmount, beanAmount);
		finance.setCashAmount(cashAmt);
	}
	
	@Override
	public OnlineConsumeRespDTO buildReturnResponse(BaseContext<OnlineConsumeReqDTO, OnlineConsumeRespDTO> bc) throws ServiceException {
		bc.getResponse().setOrganCode(bc.getRequest().getOrganCode());
		bc.getResponse().setOrganMerchantNo(bc.getRequest().getOrganMerchantNo());
		bc.getResponse().setTerminal(bc.getRequest().getTerminal());
		bc.getResponse().setRequestId(bc.getRequest().getRequestId());
		bc.getResponse().setCardIndex(bc.getRequest().getCardIndex());
		bc.getResponse().setPayId(bc.getPayId());
		bc.getResponse().setTotalMoney(bc.getRequest().getTotalAmount());
		bc.getResponse().setMarketFee(bc.getFinance().getMarcketFee());
		bc.getResponse().setCoinAmount(bc.getFinance().getCoinAmount());
		bc.getResponse().setBeanAmount(bc.getFinance().getBeanAmount());
		bc.getResponse().setCashMoney(bc.getFinance().getCashAmount());
		bc.getResponse().setGiftAmount(bc.getFinance().getGiftAmount());
		bc.getResponse().setGiftPoint(bc.getFinance().getGiftPoint());
		bc.getResponse().setTxnDate(FormatUtil.formatYyyyMMdd(LocalDateTime.now()));
		bc.getResponse().setPayStatus(DealStatus.ALREADY_PAID.getCode());
		bc.getResponse().setPayDesc(DealStatus.ALREADY_PAID.getDesc());
		return bc.getResponse();
	}
	
	public UpdateAccountBalanceOffLineReqDTO buildUpdateAccountBalanceOffLineReqDTO(BaseContext<OnlineConsumeReqDTO, OnlineConsumeRespDTO> bc) {
		UpdateAccountBalanceOffLineReqDTO request = new UpdateAccountBalanceOffLineReqDTO();
		request.setUserId(bc.getGl365User().getUserId());
		request.setOrganCode(OrganCode.GL.getCode());
		request.setPayId(bc.getPayId());
		request.setMerchantNo(bc.getGl365Merchant().getMerchantNo());
		request.setMerchantName(bc.getGl365Merchant().getMerchantShortname());
		request.setMerchantOrderNo(bc.getRequest().getMerchantOrderNo());
		request.setOperateType(bc.getTranType().getCode());
		// 支付乐豆
		request.setOperateAmount(bc.getFinance().getBeanAmount());
		// 返利乐豆
		request.setGiftAmount(bc.getFinance().getGiftAmount());
		request.setDcType(DcType.D.getCode());
		request.setScene(bc.getPayMain().getScene());
		request.setAgentId(Agent.GL365.getKey());
		bc.setUpdateAccountBalanceOffLineReqDTO(request);
		return request;
	}

	@Override
	public List<PayDetail> buildPayDetails(BaseContext<OnlineConsumeReqDTO, OnlineConsumeRespDTO> bc) {
		List<PayDetail> payDetails = payAdapter.buildPayDetailsFromPayMain(bc.getPayMain(), bc.getPayId());
		bc.setPayDetails(payDetails);
		return payDetails;
	}
	
	public void splitOrderDesc(OnlineConsumeReqDTO request) {
		String reqDesc = request.getMerchantOrderDesc();
		Map<String, String> map = StringParseUtil.getOrderDescAndCostPrice(reqDesc);
		request.setMerchantOrderDesc(map.get("orderDesc"));
		String costPrice = map.get("costPrice");
		if (StringUtils.isNotBlank(costPrice)) {
			request.setCostPrice(new BigDecimal(costPrice));
		}
	}
}
