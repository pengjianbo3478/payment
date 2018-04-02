package com.gl365.payment.service.web.reward.abs;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gl365.payment.common.constants.PaymentConstants;
import com.gl365.payment.common.constants.PaymentConstants.Column;
import com.gl365.payment.dto.base.BaseContext;
import com.gl365.payment.dto.onlineconsume.request.OnlineRewardBeanConsumeReqDTO;
import com.gl365.payment.dto.onlineconsume.response.OnlineRewardBeanConsumeRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.pay.AccountOperateType;
import com.gl365.payment.enums.pay.CardType;
import com.gl365.payment.enums.pay.DealStatus;
import com.gl365.payment.enums.pay.OrderType;
import com.gl365.payment.enums.system.Flag;
import com.gl365.payment.enums.system.OrganCode;
import com.gl365.payment.exception.InvalidRequestException;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayDetail;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.remote.dto.account.Gl365UserAccount;
import com.gl365.payment.remote.dto.account.request.BeanTransferReqDTO;
import com.gl365.payment.service.transaction.AbstractTranscation;
import com.gl365.payment.util.FormatUtil;
public abstract class AbstractOnlineRewardBeanConsume extends AbstractTranscation<OnlineRewardBeanConsumeReqDTO, OnlineRewardBeanConsumeRespDTO> {
	
	
	private static String REWARD_BEAN_MERCHANT_NO = "1000001900343";
	
	public void buildReqDTO(OnlineRewardBeanConsumeReqDTO req) {
		req.setRequestId(req.getMerchantOrderNo());
		req.setOrganOrderNo(req.getMerchantOrderNo());
		req.setTerminal("00000000");
		req.setOperator("00000000");
	}

	public boolean checkAndSaveInputParameter(BaseContext<OnlineRewardBeanConsumeReqDTO, OnlineRewardBeanConsumeRespDTO> bc) throws InvalidRequestException {
		Map<PaymentConstants.Column, String> strColumns = new HashMap<PaymentConstants.Column, String>();
		OnlineRewardBeanConsumeReqDTO request = bc.getRequest();
		strColumns.put(Column.organCode, request.getOrganCode());
		strColumns.put(Column.merchantOrderNo, request.getMerchantOrderNo());
		strColumns.put(Column.requestDate, request.getRequestDate());
		strColumns.put(Column.payUserId, request.getPayUserId());
		strColumns.put(Column.rewardUserId, request.getRewardUserId());
		strColumns.put(Column.rewardPayId, request.getRewardUserId());
		Map<PaymentConstants.Column, String> strLenColumns = new HashMap<PaymentConstants.Column, String>();
		strLenColumns.put(Column.organCode, request.getOrganCode());
		strLenColumns.put(Column.merchantOrderNo, request.getMerchantOrderNo());
		strLenColumns.put(Column.requestDate, request.getRequestDate());
		strLenColumns.put(Column.payUserId, request.getPayUserId());
		strLenColumns.put(Column.merchantOrderDesc, request.getMerchantOrderDesc());
		strLenColumns.put(Column.rewardPayId, request.getRewardPayId());
		strLenColumns.put(Column.rewardUserId, request.getRewardUserId());
		Map<PaymentConstants.Column, BigDecimal> bigDecimalColumns = new HashMap<PaymentConstants.Column, BigDecimal>();
		bigDecimalColumns.put(Column.totalAmount, request.getTotalAmount());
		boolean b = !checkStringNPT(bc, strColumns);
		boolean c = !checkStringLen(bc, strLenColumns);
		boolean c2 = !checkBigDecimalNPT(bc, bigDecimalColumns);
		boolean ot = !checkOrderType(bc, request.getOrderType());
		if (b || c || c2 || ot) return false;
		return true;
	}

	@Override
	public PayStream buildPayStream(BaseContext<OnlineRewardBeanConsumeReqDTO, OnlineRewardBeanConsumeRespDTO> bc) {
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
	public PayMain buildPayMain(BaseContext<OnlineRewardBeanConsumeReqDTO, OnlineRewardBeanConsumeRespDTO> bc, DealStatus dealStatus) {
		PayMain payMain = payAdapter.buildPayMain(bc, dealStatus);
		OnlineRewardBeanConsumeReqDTO request = bc.getRequest();
		payMain = payAdapter.setPayMain(payMain, bc.getGl365User());
		payMain.setOrganOrderNo(request.getOrganOrderNo());
		payMain.setPayFeeType(CardType.D.getCode());
		payMain.setOrganCode(request.getOrganCode());
		payMain.setUserId(request.getPayUserId());
		payMain.setMerchantAgentNo("100000");
		payMain.setCity((short) 77);
		payMain.setProvince((short) 6);
		payMain.setMerchantNo(REWARD_BEAN_MERCHANT_NO);
		payMain.setMerchantName("给乐打赏专户");
		payMain.setGiftRate(BigDecimal.ZERO);
		payMain.setCommRate(BigDecimal.ZERO);
		payMain.setSettleOrganNo(OrganCode.BeanGL.getCode());
		payMain.setPayFeeRate(BigDecimal.ZERO);
		payMain.setMaxPayFee(BigDecimal.ZERO);
		payMain.setPayFee(BigDecimal.ZERO);
		payMain.setBeanAmount(request.getTotalAmount());
		payMain.setCoinAmount(BigDecimal.ZERO);
		payMain.setMarcketFee(BigDecimal.ZERO);
		payMain.setCommAmount(BigDecimal.ZERO);
		payMain.setCashAmount(BigDecimal.ZERO);
		payMain.setGiftAmount(BigDecimal.ZERO);
		payMain.setGiftPoint(BigDecimal.ZERO);
		payMain.setMerchantSettleAmount(request.getTotalAmount());
		payMain.setRequestDate(FormatUtil.parseYyyyMMdd(request.getRequestDate()));
		payMain.setOrganCode(request.getOrganCode());
		payMain.setOrganMerchantNo(REWARD_BEAN_MERCHANT_NO);
		payMain.setTerminal(request.getTerminal());
		payMain.setOperator(request.getOperator());
		payMain.setTotalAmount(request.getTotalAmount());
		payMain.setNoBenefitAmount(BigDecimal.ZERO);
		payMain.setRequestId(request.getRequestId());
		payMain.setScene(request.getScene());
		payMain.setRewardUserId(request.getRewardUserId());
		payMain.setRewardPayId(request.getRewardPayId());
		payMain.setOrderType(request.getOrderType());
		payMain.setMerchantOrderNo(request.getMerchantOrderNo());
		payMain.setMerchantOrderTitle(getMerchentOrderDesc(request.getOrderType()));
		payMain.setMerchentOrderDesc(request.getMerchantOrderDesc());
		payMain.setCardIndex(request.getCardIndex());
		payMain.setIsNotify(Flag.N.getCode());
		payMain.setPreSettleDate(LocalDate.now());
		payMain.setSettleMerchantNo(REWARD_BEAN_MERCHANT_NO);
		payMain.setParentMerchantNo(REWARD_BEAN_MERCHANT_NO);
		bc.setPayMain(payMain);
		return payMain;
	}

	@Override
	public List<PayDetail> buildPayDetails(BaseContext<OnlineRewardBeanConsumeReqDTO, OnlineRewardBeanConsumeRespDTO> bc) {
		List<PayDetail> payDetails = payAdapter.buildPayDetailsFromPayMain(bc.getPayMain(), bc.getPayId());
		bc.setPayDetails(payDetails);
		return payDetails;
	}

	@Override
	public OnlineRewardBeanConsumeRespDTO buildReturnResponse(BaseContext<OnlineRewardBeanConsumeReqDTO, OnlineRewardBeanConsumeRespDTO> bc) throws ServiceException {
		OnlineRewardBeanConsumeReqDTO request = bc.getRequest();
		bc.getResponse().setOrganCode(request.getOrganCode());
		bc.getResponse().setTerminal(request.getTerminal());
		bc.getResponse().setMerchantOrderNo(request.getMerchantOrderNo());
		bc.getResponse().setPayUserId(request.getPayUserId());
		bc.getResponse().setPayId(bc.getPayId());
		bc.getResponse().setTotalMoney(request.getTotalAmount());
		PayMain pm = bc.getPayMain();
		bc.getResponse().setCoinAmount(pm.getCoinAmount());
		bc.getResponse().setBeanAmount(pm.getBeanAmount());
		bc.getResponse().setGiftAmount(pm.getGiftAmount());
		bc.getResponse().setTxnDate(FormatUtil.formatYyyyMMdd(LocalDateTime.now()));
		bc.getResponse().setPayStatus(DealStatus.ALREADY_PAID.getCode());
		bc.getResponse().setPayDesc(DealStatus.ALREADY_PAID.getDesc());
		return bc.getResponse();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public void query(BaseContext<OnlineRewardBeanConsumeReqDTO, OnlineRewardBeanConsumeRespDTO> bc) throws ServiceException {
		PayMain payMain = this.payMainMapper.queryByMerchantOrderNo(bc.getRequest().getRewardPayId());
		if (payMain == null) throw new ServiceException(Msg.PAY_8012.getCode(), Msg.PAY_8012.getDesc());
		// 通过用户SN查询用户表，取出用户所属发展机构、乐豆支付总开关 用户状态是否正常
		Gl365UserAccount account = new Gl365UserAccount();
		account.setUserId(bc.getRequest().getPayUserId());
		bc.setGl365UserAccount(account);
		bc.setGl365User(this.remoteMicroServiceAgent.queryUserInfo(bc));
		// 调转账接口
		OnlineRewardBeanConsumeReqDTO request = bc.getRequest();
		String operateType = this.getOperateType(request.getOrderType());
		BeanTransferReqDTO req = new BeanTransferReqDTO();
		req.setFromUser(request.getPayUserId());
		req.setToUser(request.getRewardUserId());
		req.setOperateAmount(request.getTotalAmount());
		req.setOperateType(operateType);
		req.setPayId(bc.getPayId());
		req.setOperateBy(request.getOperator());
		this.remoteMicroServiceAgent.beanTransfer(req);
	}

	private String getOperateType(String orderType) {
		if (StringUtils.equals(orderType, OrderType.beanDs.getCode())) return AccountOperateType.Ds.getCode();
		if (StringUtils.equals(orderType, OrderType.beanPay.getCode())) return AccountOperateType.Zz.getCode();
		return null;
	}
	
	private String getMerchentOrderDesc(String orderType) {
		if (StringUtils.equals(orderType, OrderType.beanDs.getCode())) return  OrderType.beanDs.getDesc();
		if (StringUtils.equals(orderType, OrderType.beanPay.getCode())) return OrderType.beanPay.getDesc();
		return null;
	}
	
}
