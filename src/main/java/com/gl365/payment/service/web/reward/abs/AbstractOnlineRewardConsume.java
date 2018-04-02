package com.gl365.payment.service.web.reward.abs;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.gl365.payment.common.Finance;
import com.gl365.payment.common.constants.PaymentConstants;
import com.gl365.payment.common.constants.PaymentConstants.Column;
import com.gl365.payment.dto.base.BaseContext;
import com.gl365.payment.dto.onlineconsume.request.OnlineRewardConsumeReqDTO;
import com.gl365.payment.dto.onlineconsume.response.OnlineConsumeRespDTO;
import com.gl365.payment.enums.pay.AccountOperateType;
import com.gl365.payment.enums.pay.DcType;
import com.gl365.payment.enums.pay.DealStatus;
import com.gl365.payment.enums.pay.OrderType;
import com.gl365.payment.enums.pay.Scene;
import com.gl365.payment.enums.system.Flag;
import com.gl365.payment.exception.InvalidRequestException;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayDetail;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.remote.dto.account.request.BeanTransferReqDTO;
import com.gl365.payment.remote.dto.merchant.Gl365Merchant;
import com.gl365.payment.service.transaction.AbstractTranscation;
import com.gl365.payment.util.BigDecimalUtil;
import com.gl365.payment.util.FormatUtil;
public abstract class AbstractOnlineRewardConsume extends AbstractTranscation<OnlineRewardConsumeReqDTO, OnlineConsumeRespDTO> {
	@Override
	public boolean checkAndSaveInputParameter(BaseContext<OnlineRewardConsumeReqDTO, OnlineConsumeRespDTO> bc) throws InvalidRequestException {
		Map<PaymentConstants.Column, String> strColumns = new HashMap<PaymentConstants.Column, String>();
		OnlineRewardConsumeReqDTO request = bc.getRequest();
		strColumns.put(Column.organCode, request.getOrganCode());
		strColumns.put(Column.requestId, request.getRequestId());
		strColumns.put(Column.requestDate, request.getRequestDate());
		strColumns.put(Column.organMerchantNo, request.getOrganMerchantNo());
		strColumns.put(Column.cardIndex, request.getCardIndex());
		strColumns.put(Column.rewardPayId, request.getRewardPayId());
		strColumns.put(Column.rewardUserId, request.getRewardUserId());
		Map<PaymentConstants.Column, String> strLenColumns = new HashMap<PaymentConstants.Column, String>();
		strLenColumns.put(Column.organCode, request.getOrganCode());
		strLenColumns.put(Column.requestId, request.getRequestId());
		strLenColumns.put(Column.requestDate, request.getRequestDate());
		strLenColumns.put(Column.organMerchantNo, request.getOrganMerchantNo());
		//strLenColumns.put(Column.terminal, bc.getRequest().getTerminal());
		strLenColumns.put(Column.operator, request.getOperator());
		strLenColumns.put(Column.cardIndex, request.getCardIndex());
		strLenColumns.put(Column.merchantOrderTitle, request.getMerchantOrderTitle());
		strLenColumns.put(Column.merchantOrderDesc, request.getMerchantOrderDesc());
		strLenColumns.put(Column.merchantOrderNo, request.getMerchantOrderNo());
		Map<PaymentConstants.Column, BigDecimal> bigDecimalColumns = new HashMap<PaymentConstants.Column, BigDecimal>();
		bigDecimalColumns.put(Column.totalAmount, request.getTotalAmount());
		bigDecimalColumns.put(Column.noBenefitAmount, request.getNoBenefitAmount());
		bigDecimalColumns.put(Column.payldmoney, request.getPayldmoney());
		boolean b = !checkStringNPT(bc, strColumns);
		boolean c = !checkStringLen(bc, strLenColumns);
		boolean c2 = !checkBigDecimalNPT(bc, bigDecimalColumns);
		if (b || c || c2) return false;
		bc.setCardIndex(request.getCardIndex());
		bc.setOrganCode(bc.getRequest().getOrganCode());
		bc.setMerchantNo(bc.getRequest().getOrganMerchantNo());
		return true;
	}

	@Override
	public PayStream buildPayStream(BaseContext<OnlineRewardConsumeReqDTO, OnlineConsumeRespDTO> bc) {
		PayStream ps = payAdapter.buildPayStream(bc);
		String requestId = bc.getRequest().getRequestId();
		ps.setRequestId(requestId);
		String requestDate = bc.getRequest().getRequestDate();
		ps.setRequestDate(FormatUtil.parseYyyyMMdd(requestDate));
		ps.setOrganCode(bc.getRequest().getOrganCode());
		ps.setOrganMerchantNo(bc.getRequest().getOrganMerchantNo());
		String terminal = bc.getRequest().getTerminal();
		ps.setTerminal(terminal);
		ps.setTotalAmount(bc.getRequest().getTotalAmount());
		ps.setReturnAmount(BigDecimal.ZERO);
		String mergeString = FormatUtil.mergeString(terminal, requestId);
		ps.setUniqueSerial(mergeString);
		bc.setPayStream(ps);
		return ps;
	}

	@Override
	public PayMain buildPayMain(BaseContext<OnlineRewardConsumeReqDTO, OnlineConsumeRespDTO> bc, DealStatus dealStatus) {
		PayMain payMain = payAdapter.buildPayMain(bc, dealStatus);
		payMain = payAdapter.setPayMain(payMain, bc.getGl365UserAccount());
		payMain = payAdapter.setPayMain(payMain, bc.getGl365User());
		String cardType = bc.getGl365UserAccount().getCardType();
		Gl365Merchant gl365Merchant = bc.getGl365Merchant();
		DcType dcType = getDcType(cardType);
		payMain = payAdapter.setPayMain(payMain, gl365Merchant, dcType, Scene.FAST_PAY);
		payMain = payAdapter.setPayMain(payMain, bc.getFinance());
		OnlineRewardConsumeReqDTO request = bc.getRequest();
		payMain.setRequestDate(FormatUtil.parseYyyyMMdd(request.getRequestDate()));
		payMain.setOrganCode(request.getOrganCode());
		payMain.setOrganMerchantNo(request.getOrganMerchantNo());
		payMain.setTerminal(request.getTerminal());
		payMain.setOperator(request.getOperator());
		payMain.setTotalAmount(request.getTotalAmount());
		payMain.setNoBenefitAmount(request.getNoBenefitAmount());
		payMain.setRequestId(request.getRequestId());
		payMain.setScene(request.getScene());
		payMain.setRewardUserId(request.getRewardUserId());
		payMain.setRewardPayId(request.getRewardPayId());
		payMain.setOrderType(OrderType.ds.getCode());
		payMain.setMerchantOrderNo(request.getMerchantOrderNo());
		payMain.setMerchantOrderTitle(request.getMerchantOrderTitle());
		payMain.setMerchentOrderDesc(request.getMerchantOrderDesc());
		payMain.setCardIndex(request.getCardIndex());
		payMain.setIsNotify(Flag.N.getCode());
		bc.setPayMain(payMain);
		return payMain;
	}

	@Override
	public List<PayDetail> buildPayDetails(BaseContext<OnlineRewardConsumeReqDTO, OnlineConsumeRespDTO> bc) {
		List<PayDetail> payDetails = payAdapter.buildPayDetailsFromPayMain(bc.getPayMain(), bc.getPayId());
		bc.setPayDetails(payDetails);
		return payDetails;
	}

	@Override
	public OnlineConsumeRespDTO buildReturnResponse(BaseContext<OnlineRewardConsumeReqDTO, OnlineConsumeRespDTO> bc) throws ServiceException {
		OnlineRewardConsumeReqDTO request = bc.getRequest();
		bc.getResponse().setOrganCode(request.getOrganCode());
		bc.getResponse().setOrganMerchantNo(request.getOrganMerchantNo());
		bc.getResponse().setTerminal(request.getTerminal());
		bc.getResponse().setRequestId(request.getRequestId());
		bc.getResponse().setCardIndex(request.getCardIndex());
		bc.getResponse().setPayId(bc.getPayId());
		bc.getResponse().setTotalMoney(bc.getRequest().getTotalAmount());
		Finance finance = bc.getFinance();
		bc.getResponse().setMarketFee(finance.getMarcketFee());
		bc.getResponse().setCoinAmount(finance.getCoinAmount());
		bc.getResponse().setBeanAmount(finance.getBeanAmount());
		bc.getResponse().setCashMoney(finance.getCashAmount());
		bc.getResponse().setGiftAmount(finance.getGiftAmount());
		bc.getResponse().setGiftPoint(finance.getGiftPoint());
		bc.getResponse().setTxnDate(FormatUtil.formatYyyyMMdd(LocalDateTime.now()));
		bc.getResponse().setPayStatus(DealStatus.ALREADY_PAID.getCode());
		bc.getResponse().setPayDesc(DealStatus.ALREADY_PAID.getDesc());
		return bc.getResponse();
	}

	public void beanTransfer(BaseContext<OnlineRewardConsumeReqDTO, OnlineConsumeRespDTO> bc) {
		OnlineRewardConsumeReqDTO request = bc.getRequest();
		if (!BigDecimalUtil.GreaterThan0(request.getPayldmoney())) return;
		BeanTransferReqDTO req = new BeanTransferReqDTO();
		req.setFromUser(bc.getGl365User().getUserId());
		req.setToUser(request.getRewardUserId());
		req.setOperateAmount(request.getPayldmoney());
		req.setOperateType(AccountOperateType.Ds.getCode());
		req.setPayId(bc.getPayId());
		String operator = "00000000";
		if (StringUtils.isNotBlank(request.getOperator())) operator = request.getOperator();
		req.setOperateBy(operator);
		if (BigDecimalUtil.GreaterThan0(req.getOperateAmount())) {
			this.remoteMicroServiceAgent.beanTransfer(req);
		}
	}

	@Override
	public void query(BaseContext<OnlineRewardConsumeReqDTO, OnlineConsumeRespDTO> bc) throws ServiceException {
		// 通过卡索引号查询绑卡表，得到用户SN、绑卡状态/
		bc.setGl365UserAccount(this.remoteMicroServiceAgent.queryAccount(bc));
		this.remoteMicroServiceAgent.isBindCard(bc);
		// 通过用户SN查询用户表，取出用户所属发展机构、乐豆支付总开关 用户状态是否正常
		bc.setGl365User(this.remoteMicroServiceAgent.queryUserInfo(bc));
		// 通过付费通商户号查询给乐商户号 查询商户信息 商户状态是否正常
		bc.setGl365Merchant(this.remoteMicroServiceAgent.queryMerchantInfo(bc));
		Finance finance = new Finance();
		// 计算返利
		finance.setGiftAmount(BigDecimal.ZERO);
		bc.setFinance(finance);
	}
}
