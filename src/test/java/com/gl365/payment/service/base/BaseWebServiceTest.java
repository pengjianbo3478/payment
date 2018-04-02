package com.gl365.payment.service.base;
import java.math.BigDecimal;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.gl365.payment.dto.onlineconsume.request.OnlineConsumeReqDTO;
import com.gl365.payment.dto.onlineconsume.request.OnlineRewardBeanConsumeReqDTO;
import com.gl365.payment.dto.onlineconsume.request.OnlineRewardConsumeReqDTO;
import com.gl365.payment.dto.onlineconsume.response.OnlineConsumeRespDTO;
import com.gl365.payment.dto.refund.query.request.RefundQueryReqDTO;
import com.gl365.payment.dto.refund.query.response.RefundQueryRespDTO;
import com.gl365.payment.dto.refund.request.RefundReqDTO;
import com.gl365.payment.dto.refund.response.RefundRespDTO;
import com.gl365.payment.dto.rollback.request.RollbackReqDTO;
import com.gl365.payment.dto.rollback.response.RollbackRespDTO;
import com.gl365.payment.enums.pay.OrderType;
import com.gl365.payment.enums.pay.PayChannel;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.enums.pay.SplitFlag;
import com.gl365.payment.service.web.WebServiceTest;
import com.gl365.payment.service.web.cancel.WebConsumeCancelService;
import com.gl365.payment.service.web.cancelreverse.WebConsumeCancelReverseService;
import com.gl365.payment.service.web.confirm.OnlineConsumeService;
import com.gl365.payment.service.web.refund.query.RefundQueryService;
import com.gl365.payment.service.web.refund.refund.RefundService;
import com.gl365.payment.service.web.reverse.WebConsumeReverseService;
import com.gl365.payment.service.web.reward.OnlineRewardBeanConsumeService;
import com.gl365.payment.service.web.reward.OnlineRewardConsumeService;
import com.gl365.payment.util.Gl365StrUtils;
import com.gl365.payment.util.IdGenerator;
import com.gl365.payment.util.gson.GsonUtils;
public class BaseWebServiceTest extends BaseServiceTest {
	private static final Logger LOG = LoggerFactory.getLogger(WebServiceTest.class);
	@Autowired
	public OnlineConsumeService onlineConsumeService;
	public OnlineConsumeReqDTO onlineConsumeReqDTO = new OnlineConsumeReqDTO();
	@Autowired
	public WebConsumeCancelService webConsumeCancelService;
	public RollbackReqDTO webConsumeCancelReqDTO = new RollbackReqDTO();
	@Autowired
	public WebConsumeCancelReverseService webConsumeCancelReverseService;
	public RollbackReqDTO webConsumeCancelReverseReqDTO = new RollbackReqDTO();
	@Autowired
	public WebConsumeReverseService webConsumeReverseService;
	public RollbackReqDTO webConsumeReverse = new RollbackReqDTO();
	@Autowired
	public RefundService refundService;
	public RefundReqDTO refundReqDTO = new RefundReqDTO();
	@Autowired
	public RefundQueryService refundQueryService;
	public RefundQueryReqDTO refundQueryReqDTO = new RefundQueryReqDTO();
	@Autowired
	public OnlineRewardConsumeService onlineRewardConsumeService;
	public OnlineRewardConsumeReqDTO onlineRewardConsumeReqDTO = new OnlineRewardConsumeReqDTO();
	
	@Autowired
	public OnlineRewardBeanConsumeService onlineRewardBeanConsumeService;
	public OnlineRewardBeanConsumeReqDTO onlineRewardBeanConsumeReqDTO = new OnlineRewardBeanConsumeReqDTO();
	
	@Autowired
	@Qualifier("onlineConsumeParticipantService")
	public OnlineConsumeService onlineConsumeParticipantService;
	@Autowired
	@Qualifier("onlineConsumeInitiatorService")
	public OnlineConsumeService onlineConsumeInitiatorService;
	
	private String groupOrderId = "25241452365";

	public OnlineConsumeRespDTO consumeConfirm() {
		OnlineConsumeRespDTO onlineConsumeRespDTO = new OnlineConsumeRespDTO(PayStatus.COMPLETE_PAY.getCode(), PayStatus.COMPLETE_PAY.getDesc());
		onlineConsumeRespDTO = this.onlineConsumeService.onlineConsume(onlineConsumeReqDTO, onlineConsumeRespDTO);
		LOG.debug("######consumeConfirm result={}", Gl365StrUtils.toMultiLineStr(onlineConsumeRespDTO));
		return onlineConsumeRespDTO;
	}
	
	public OnlineConsumeRespDTO consumeConfirmParticipant() {
		OnlineConsumeRespDTO onlineConsumeRespDTO = new OnlineConsumeRespDTO(PayStatus.COMPLETE_PAY.getCode(), PayStatus.COMPLETE_PAY.getDesc());
		this.onlineConsumeReqDTO.setOrderType(OrderType.groupPay.getCode());
		this.onlineConsumeReqDTO.setGroupOrderId(this.groupOrderId);
		this.onlineConsumeReqDTO.setSplitFlag(SplitFlag.childOrder.getCode());
		this.onlineConsumeReqDTO.setGroupMerchantNo("1707121000098");
		this.onlineConsumeReqDTO.setTotalAmount(new BigDecimal(10));
		onlineConsumeRespDTO = this.onlineConsumeParticipantService.onlineConsume(onlineConsumeReqDTO, onlineConsumeRespDTO);
		LOG.debug("######consumeConfirm result={}", Gl365StrUtils.toMultiLineStr(onlineConsumeRespDTO));
		return onlineConsumeRespDTO;
	}
	
	public OnlineConsumeRespDTO consumeConfirmInitiator() {
		OnlineConsumeRespDTO onlineConsumeRespDTO = new OnlineConsumeRespDTO(PayStatus.COMPLETE_PAY.getCode(), PayStatus.COMPLETE_PAY.getDesc());
		this.onlineConsumeReqDTO.setRequestId(IdGenerator.getUuId32());
		this.onlineConsumeReqDTO.setOrderType(OrderType.groupPay.getCode());
		this.onlineConsumeReqDTO.setGroupOrderId(this.groupOrderId);
		this.onlineConsumeReqDTO.setGroupMainUserPay(new BigDecimal(15));
		this.onlineConsumeReqDTO.setSplitFlag(SplitFlag.mainOrder.getCode());
		this.onlineConsumeReqDTO.setGroupMerchantNo("1707121000098");
		this.onlineConsumeReqDTO.setTotalAmount(new BigDecimal(25));
		onlineConsumeRespDTO = this.onlineConsumeInitiatorService.onlineConsume(onlineConsumeReqDTO, onlineConsumeRespDTO);
		LOG.debug("######consumeConfirm result={}", Gl365StrUtils.toMultiLineStr(onlineConsumeRespDTO));
		return onlineConsumeRespDTO;
	}

	public RollbackRespDTO webConsumeCancel(OnlineConsumeRespDTO onlineConsumeRespDTO) {
		this.webConsumeCancelReqDTO.setTotalAmount(onlineConsumeRespDTO.getTotalMoney());
		this.webConsumeCancelReqDTO.setOrigPayId(onlineConsumeRespDTO.getPayId());
		RollbackRespDTO result = this.webConsumeCancelService.execute(webConsumeCancelReqDTO);
		LOG.debug("######webConsumeCancel result={}", Gl365StrUtils.toMultiLineStr(result));
		return result;
	}

	public void webConsumeCancelReverse(RollbackRespDTO webConsumeCancel, OnlineConsumeRespDTO onlineConsumeRespDTO) {
		this.webConsumeCancelReverseReqDTO.setTotalAmount(onlineConsumeRespDTO.getTotalMoney());
		this.webConsumeCancelReverseReqDTO.setOrigRequestId(webConsumeCancel.getRequestId());
		RollbackRespDTO result = this.webConsumeCancelReverseService.execute(webConsumeCancelReverseReqDTO);
		LOG.info("######webConsumeCancelReverse result={}", Gl365StrUtils.toMultiLineStr(result));
	}

	public void webConsumeReverse(OnlineConsumeRespDTO onlineConsumeRespDTO) {
		this.webConsumeReverse.setTotalAmount(onlineConsumeRespDTO.getTotalMoney());
		this.webConsumeReverse.setOrigRequestId(onlineConsumeRespDTO.getRequestId());
		RollbackRespDTO result = this.webConsumeReverseService.execute(this.webConsumeReverse);
		LOG.info("######webConsumeReverse result={}", Gl365StrUtils.toMultiLineStr(result));
	}

	public void refund(OnlineConsumeRespDTO onlineConsumeRespDTO) {
		RefundRespDTO result = new RefundRespDTO(PayStatus.ALL_RETURN.getCode(), PayStatus.ALL_RETURN.getDesc());
		this.refundReqDTO.setTotalAmount(onlineConsumeRespDTO.getTotalMoney());
		refundReqDTO.setOrigPayId(onlineConsumeRespDTO.getPayId());
		result = this.refundService.refund(refundReqDTO, result);
		LOG.info("result={}", Gl365StrUtils.toStr(result));
	}

	public void refundQuery(OnlineConsumeRespDTO onlineConsumeRespDTO) {
		RefundQueryRespDTO result = new RefundQueryRespDTO(PayStatus.ALL_RETURN.getCode(), PayStatus.ALL_RETURN.getDesc());
		refundQueryReqDTO.setOrigPayId(onlineConsumeRespDTO.getPayId());
		refundQueryReqDTO.setTotalAmount(onlineConsumeRespDTO.getTotalMoney());
		refundQueryReqDTO.setRequestId(IdGenerator.getUuId32());
		result = this.refundQueryService.refundQuery(refundQueryReqDTO, result);
		LOG.info("result={}", GsonUtils.toJson(result));
	}
	
	public OnlineConsumeRespDTO onlineRewardConsume(OnlineConsumeRespDTO onlineConsumeRespDTO) {
		OnlineConsumeRespDTO result = new OnlineConsumeRespDTO(PayStatus.COMPLETE_PAY.getCode(), PayStatus.COMPLETE_PAY.getDesc());
		this.onlineRewardConsumeReqDTO.setRewardPayId(onlineConsumeRespDTO.getPayId());
		this.onlineRewardConsumeReqDTO.setTotalAmount(new BigDecimal(10));
		result = this.onlineRewardConsumeService.onlineRewardConsume(onlineRewardConsumeReqDTO, result);
		LOG.info("result={}", Gl365StrUtils.toStr(result));
		return result;
	}
	


	@Before
	public void before() {
		this.onlineConsumeReqDTO.setOrganCode(this.organCode);
		this.onlineConsumeReqDTO.setRequestId(this.getRequestId());
		this.onlineConsumeReqDTO.setRequestDate(LocalDate.now().toString("yyyyMMdd"));
		this.onlineConsumeReqDTO.setOrganMerchantNo(this.organMerchantNo);
		this.onlineConsumeReqDTO.setTerminal(this.terminal);
		this.onlineConsumeReqDTO.setCardIndex(this.cardIndex);
		this.onlineConsumeReqDTO.setTotalAmount(new BigDecimal(10));
		this.onlineConsumeReqDTO.setNoBenefitAmount(new BigDecimal(0));
		this.onlineConsumeReqDTO.setOrganOrderNo(this.organOrderNo);
		this.onlineConsumeReqDTO.setMerchantOrderNo(IdGenerator.getUuId32());
		this.onlineConsumeReqDTO.setMerchantOrderDesc(this.merchantOrderDesc);
		this.webConsumeCancelReqDTO.setOrganCode(this.organCode);
		this.webConsumeCancelReqDTO.setOrganMerchantNo(this.organMerchantNo);
		this.webConsumeCancelReqDTO.setRequestId(this.getRequestId());
		this.webConsumeCancelReqDTO.setRequestDate(java.time.LocalDate.now());
		this.webConsumeCancelReqDTO.setCardIndex(this.cardIndex);
		this.webConsumeCancelReqDTO.setTerminal(this.terminal);
		this.webConsumeCancelReqDTO.setOrigTxnDate(java.time.LocalDate.now());
		this.webConsumeCancelReverseReqDTO.setOrganCode(this.organCode);
		this.webConsumeCancelReverseReqDTO.setRequestId(this.getRequestId());
		this.webConsumeCancelReverseReqDTO.setRequestDate(java.time.LocalDate.now());
		this.webConsumeCancelReverseReqDTO.setOrganMerchantNo(this.organMerchantNo);
		this.webConsumeCancelReverseReqDTO.setTerminal(this.terminal);
		this.webConsumeCancelReverseReqDTO.setCardIndex(this.cardIndex);
		this.webConsumeCancelReverseReqDTO.setOrigTxnDate(java.time.LocalDate.now());
		this.webConsumeReverse.setOrganCode(this.organCode);
		this.webConsumeReverse.setRequestId(this.getRequestId());
		this.webConsumeReverse.setRequestDate(java.time.LocalDate.now());
		this.webConsumeReverse.setOrganMerchantNo(this.organMerchantNo);
		this.webConsumeReverse.setTerminal(this.terminal);
		this.webConsumeReverse.setCardIndex(this.cardIndex);
		this.webConsumeReverse.setOrigTxnDate(java.time.LocalDate.now());
		this.refundReqDTO.setOrganCode(this.organCode);
		this.refundReqDTO.setRequestId(this.requestId);
		this.refundReqDTO.setRequestDate(this.requestDate);
		this.refundReqDTO.setOrganMerchantNo(this.organMerchantNo);
		this.refundReqDTO.setTerminal(this.terminal);
		this.refundReqDTO.setCardIndex(this.cardIndex);
		this.refundReqDTO.setPayChannel(PayChannel.POS.getCode());
		this.refundReqDTO.setOrigTxnDate(LocalDate.now().toString("yyyyMMdd"));
		this.refundQueryReqDTO.setOrganCode(this.organCode);
		this.refundQueryReqDTO.setRequestId(this.requestId);
		this.refundQueryReqDTO.setRequestDate(this.requestDate);
		this.refundQueryReqDTO.setOrganMerchantNo(this.organMerchantNo);
		this.refundQueryReqDTO.setTerminal(this.terminal);
		this.refundQueryReqDTO.setCardIndex(this.cardIndex);
		this.refundQueryReqDTO.setOrigTxnDate(LocalDate.now().toString("yyyyMMdd"));
		this.refundQueryReqDTO.setPayChannel(PayChannel.POS.getCode());
		this.onlineRewardConsumeReqDTO.setOrganCode(this.organCode);
		this.onlineRewardConsumeReqDTO.setRequestId(this.requestId);
		this.onlineRewardConsumeReqDTO.setRequestDate(this.requestDate);
		this.onlineRewardConsumeReqDTO.setOrganMerchantNo(this.organMerchantNo);
		this.onlineRewardConsumeReqDTO.setCardIndex(this.cardIndex);
		this.onlineRewardConsumeReqDTO.setRewardUserId(this.rewardUserId);
		this.onlineRewardConsumeReqDTO.setNoBenefitAmount(new BigDecimal(0));
		this.onlineRewardConsumeReqDTO.setPayldmoney(this.payldmoney);
		this.onlineRewardConsumeReqDTO.setTerminal(this.terminal);
		this.onlineRewardBeanConsumeReqDTO.setOrganCode("10002");
		this.onlineRewardBeanConsumeReqDTO.setOrderType("5");
		this.onlineRewardBeanConsumeReqDTO.setMerchantOrderNo(this.merchantOrderNo);
		this.onlineRewardBeanConsumeReqDTO.setRequestDate(this.requestDate);
		this.onlineRewardBeanConsumeReqDTO.setPayUserId(this.UserId);
		this.onlineRewardBeanConsumeReqDTO.setTotalAmount(new BigDecimal(5));
		this.onlineRewardBeanConsumeReqDTO.setRewardUserId(this.rewardUserId);
		this.onlineRewardBeanConsumeReqDTO.setRewardPayId(this.prevMerchanOrderNo);
	}
}
