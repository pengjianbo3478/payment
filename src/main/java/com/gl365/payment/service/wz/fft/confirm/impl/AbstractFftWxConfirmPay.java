package com.gl365.payment.service.wz.fft.confirm.impl;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.gl365.payment.common.constants.PaymentConstants.PayType;
import com.gl365.payment.dto.wx.request.WxConfirmReqDTO;
import com.gl365.payment.dto.wx.response.WxConfirmRespDTO;
import com.gl365.payment.enums.mq.MsgCategory;
import com.gl365.payment.enums.pay.AccountOperateType;
import com.gl365.payment.enums.pay.DcType;
import com.gl365.payment.enums.pay.OrderType;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.enums.pay.SplitFlag;
import com.gl365.payment.enums.system.Agent;
import com.gl365.payment.enums.system.GlSystem;
import com.gl365.payment.enums.system.OrganCode;
import com.gl365.payment.enums.system.ResultCode;
import com.gl365.payment.model.PayDetail;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.remote.dto.account.request.BeanTransferReqDTO;
import com.gl365.payment.remote.dto.account.request.UpdateAccountBalanceOffLineReqDTO;
import com.gl365.payment.remote.dto.settlement.request.ConfirmPreSettleDateReqDTO;
import com.gl365.payment.remote.dto.settlement.response.ConfirmPreSettleDateRespDTO;
import com.gl365.payment.service.dbservice.PayDetailService;
import com.gl365.payment.service.mq.dto.PaymentBody;
import com.gl365.payment.service.mq.dto.PaymentMQ;
import com.gl365.payment.service.mq.product.PaymentProduct;
import com.gl365.payment.service.wz.common.AbstractPay;
import com.gl365.payment.service.wz.common.Gl365SettlementService;
import com.gl365.payment.service.wz.rm.confirm.RmWxPayConfirmContext;
import com.gl365.payment.util.BigDecimalUtil;
import com.gl365.payment.util.Gl365StrUtils;
import com.gl365.payment.util.IdGenerator;
import com.gl365.payment.util.gson.GsonUtils;
public abstract class AbstractFftWxConfirmPay extends AbstractPay {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractFftWxConfirmPay.class);
	@Autowired
	public PaymentProduct paymentProduct;
	@Autowired
	public PayDetailService payDetailService;
	@Autowired
	private Gl365SettlementService gl365SettlementService;

	public void checkRequest(RmWxPayConfirmContext ctx) {
		WxConfirmReqDTO request = ctx.getRequest();
		LOG.debug("交易请求参数={}", GsonUtils.toJson(request));
		this.checkPayRequestService.checkOrganCode(request.getOrganCode());
		this.checkPayRequestService.checkMerchantOrderNo(request.getMerchantOrderNo());
		this.checkPayRequestService.checkOrganOrderNo(request.getOrganOrderNo());
		// this.checkPayRequestService.checkTransactionId(request.getTransactionId());
		//this.checkPayRequestService.checkCashAmount(request.getCashAmount());
		this.checkPayRequestService.checkPayResult(request.getPayResult());
		this.checkPayRequestService.checkPayTime(request.getOrganPayTime());
	}

	public void buildPayStream(RmWxPayConfirmContext ctx) {
		WxConfirmReqDTO request = ctx.getRequest();
		PayStream ps = ctx.getPayStream();
		ps.setRequestId(IdGenerator.getUuId32());
		ps.setRequestDate(LocalDate.now());
		ps.setOrigRequestId(null);
		ps.setOrigPayDate(null);
		ps.setOrganCode(request.getOrganCode());
		ps.setOrganMerchantNo(null);
		ps.setTerminal(null);
		ps.setOperator(null);
		ps.setTransType(this.initTranType().getCode());
		ps.setTotalAmount(BigDecimal.ZERO);
		ps.setReturnAmount(BigDecimal.ZERO);
		ps.setUniqueSerial(UUID.randomUUID().toString());
		ctx.setPayStream(ps);
	}

	public void rewardBeanTransfer(RmWxPayConfirmContext ctx) {
		PayMain payMain = ctx.getPayMain();
		if (!BigDecimalUtil.GreaterThan0(payMain.getBeanAmount())) return;
		BeanTransferReqDTO req = new BeanTransferReqDTO();
		req.setFromUser(payMain.getUserId());
		req.setToUser(payMain.getRewardUserId());
		req.setOperateAmount(payMain.getBeanAmount());
		req.setOperateType(AccountOperateType.Ds.getCode());
		req.setPayId(payMain.getPayId());
		String operator = "00000000";
		req.setOperateBy(operator);
		if (BigDecimalUtil.GreaterThan0(req.getOperateAmount())) {
			this.gl365UserAccountService.beanTransfer(req);
		}
	}

	public void buildPayDetails(RmWxPayConfirmContext ctx) {
		List<PayDetail> payDetails = new ArrayList<PayDetail>();
		PayMain pm = ctx.getPayMain();
		String payId = pm.getPayId();
		BigDecimal beanAmt = pm.getBeanAmount();
		BigDecimal cashAmt = pm.getCashAmount();
		if (beanAmt.compareTo(BigDecimal.ZERO) == 1) {
			PayDetail pdBean = new PayDetail();
			pdBean.setPayId(payId);
			pdBean.setOrganCode(pm.getOrganCode());
			pdBean.setPayAccount(pm.getUserId());
			pdBean.setPayType(PayType.PAY_BEAN.getCode());
			pdBean.setPayAmount(pm.getBeanAmount());
			pdBean.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
			pdBean.setCreateTime(LocalDateTime.now());
			payDetails.add(pdBean);
		}
		if (cashAmt.compareTo(BigDecimal.ZERO) == 1) {
			PayDetail pdCash = new PayDetail();
			pdCash.setPayId(payId);
			pdCash.setOrganCode(pm.getOrganCode());
			pdCash.setPayAccount(pm.getUserId());
			pdCash.setPayType(PayType.PAY_CASH.getCode());
			pdCash.setPayAmount(pm.getCashAmount());
			pdCash.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
			pdCash.setCreateTime(LocalDateTime.now());
			payDetails.add(pdCash);
		}
		ctx.setPayDetails(payDetails);
	}

	public void buildGroupMainPayDetails(RmWxPayConfirmContext ctx) {
		List<PayDetail> payDetails = new ArrayList<PayDetail>();
		PayMain pm = ctx.getPayMain();
		String payId = pm.getPayId();
		BigDecimal groupPtPay = pm.getGroupPtPay();
		BigDecimal beanAmt = pm.getGroupMainuserPayBean();
		BigDecimal cashAmt = pm.getTotalAmount().subtract(beanAmt).subtract(groupPtPay);
		if (BigDecimalUtil.GreaterThan0(groupPtPay)) {
			PayDetail ptPay = new PayDetail();
			ptPay.setPayId(payId);
			ptPay.setOrganCode(pm.getOrganCode());
			ptPay.setPayAccount("1000001900343");
			ptPay.setPayType(PayType.PAY_BEAN.getCode());
			ptPay.setPayAmount(groupPtPay);
			ptPay.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
			ptPay.setCreateTime(LocalDateTime.now());
			payDetails.add(ptPay);
		}
		if (BigDecimalUtil.GreaterThan0(beanAmt)) {
			PayDetail pdBean = new PayDetail();
			pdBean.setPayId(payId);
			pdBean.setOrganCode(pm.getOrganCode());
			pdBean.setPayAccount(pm.getUserId());
			pdBean.setPayType(PayType.PAY_BEAN.getCode());
			pdBean.setPayAmount(beanAmt);
			pdBean.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
			pdBean.setCreateTime(LocalDateTime.now());
			payDetails.add(pdBean);
		}
		if (BigDecimalUtil.GreaterThan0(cashAmt)) {
			PayDetail pdCash = new PayDetail();
			pdCash.setPayId(payId);
			pdCash.setOrganCode(pm.getOrganCode());
			pdCash.setPayAccount(pm.getUserId());
			pdCash.setPayType(PayType.PAY_CASH.getCode());
			pdCash.setPayAmount(cashAmt);
			pdCash.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
			pdCash.setCreateTime(LocalDateTime.now());
			payDetails.add(pdCash);
		}
		ctx.setPayDetails(payDetails);
	}

	public void buildUpdateAccountBalanceOffLineReqDTO(RmWxPayConfirmContext ctx) {
		UpdateAccountBalanceOffLineReqDTO request = ctx.getUpdateAccBlanceRequest();
		PayMain payMain = ctx.getPayMain();
		request.setUserId(payMain.getUserId());
		request.setAgentId(Agent.GL365.getKey());
		request.setPayId(payMain.getPayId());
		request.setOrganCode(OrganCode.GL.getCode());
		request.setMerchantNo(payMain.getMerchantNo());
		request.setMerchantName(payMain.getMerchantName());
		request.setMerchantOrderNo(payMain.getMerchantOrderNo());
		request.setScene(payMain.getScene());
		request.setOperateType(payMain.getTransType());
		setAmount(request, payMain);
		request.setDcType(DcType.D.getCode());
	}

	private void setAmount(UpdateAccountBalanceOffLineReqDTO request, PayMain payMain) {
		BigDecimal operateAmount = payMain.getBeanAmount();
		if (SplitFlag.mainOrder.getCode().equals(payMain.getSplitFlag())) operateAmount = payMain.getGroupMainuserPayBean();
		request.setOperateAmount(operateAmount);
		BigDecimal giftAmount = payMain.getGiftAmount();
		if (OrderType.groupPay.getCode().equals(payMain.getOrderType())) giftAmount = payMain.getGroupGiftAmount();
		request.setGiftAmount(giftAmount);
	}

	public void sendSettleMQ(RmWxPayConfirmContext ctx) {
		PaymentBody body = new PaymentBody();
		body.setPayMain(ctx.getPayMain());
		body.setPayStream(ctx.getPayStream());
		body.setPayModifyStatus(ctx.getPayStatus().getCode());
		body.setPayModifyTime(LocalDateTime.now());
		body.setPayDetails(ctx.getPayDetails());
		PaymentMQ res = new PaymentMQ();
		res.setMsgCategory(MsgCategory.normal.getCode());
		res.setTranType(ctx.getPayStream().getTransType());
		res.setPaymentBody(body);
		String content = GsonUtils.toJson(res);
		LOG.info("发送清结算MQ消息={},JSON={}", Gl365StrUtils.toStr(res), content);
		this.paymentProduct.send(content);
	}

	public void sendPayResultMQ(RmWxPayConfirmContext ctx) {
		String pContent = buildSendPayResultMq(ctx, MsgCategory.push.getCode());
		this.paymentProduct.send(pContent);
		LOG.info("发送JPUSH消息JSON={}", pContent);
		String rpContent = buildSendPayResultMq(ctx, MsgCategory.redPacket.getCode());
		LOG.info("发送红包消息JSON={}", rpContent);
		this.paymentProduct.send(rpContent);
	}

	private String buildSendPayResultMq(RmWxPayConfirmContext ctx, String msgCategory) {
		PaymentBody body = new PaymentBody();
		body.setPayMain(ctx.getPayMain());
		body.setPayStream(ctx.getPayStream());
		body.setPayModifyTime(java.time.LocalDateTime.now());
		PaymentMQ res = new PaymentMQ();
		res.setMsgCategory(msgCategory);
		res.setMsgCategory(msgCategory);
		res.setTranType(ctx.getPayMain().getTransType());
		res.setPaymentBody(body);
		String content = GsonUtils.toJson(res);
		return content;
	}

	public void buildResp(RmWxPayConfirmContext ctx) {
		WxConfirmRespDTO resp = ctx.getResponse();
		boolean b = StringUtils.equals(ctx.getPayStatus().getCode(), PayStatus.PART_PAY.getCode());
		if (b) {
			resp.setResultCode(ResultCode.BALANCE_UNENOUGH.getCode());
			resp.setResultDesc(ResultCode.BALANCE_UNENOUGH.getDesc());
		}
		else {
			resp.setResultCode(ResultCode.SUCCESS.getCode());
			resp.setResultDesc(ResultCode.SUCCESS.getDesc());
		}
		ctx.setResponse(resp);
	}

	public void getConfirmPreSettleDate(RmWxPayConfirmContext ctx) {
		ConfirmPreSettleDateReqDTO request = new ConfirmPreSettleDateReqDTO();
		request.setOrganCode(OrganCode.WX.getCode());
		request.setPayId(ctx.getPayMain().getPayId());
		request.setTransType("1");
		request.setOrganPayTime(ctx.getRequest().getOrganPayTime());
		ConfirmPreSettleDateRespDTO result = this.gl365SettlementService.getConfirmPreSettleDate(request);
		ctx.setConfirmPreSettleDateRespDTO(result);
	}
}
