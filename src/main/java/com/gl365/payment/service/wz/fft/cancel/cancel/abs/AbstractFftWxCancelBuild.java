package com.gl365.payment.service.wz.fft.cancel.cancel.abs;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.gl365.payment.common.Finance;
import com.gl365.payment.common.PayAdapter;
import com.gl365.payment.common.constants.PaymentConstants;
import com.gl365.payment.dto.wx.request.WxCancelReqDTO;
import com.gl365.payment.dto.wx.response.WxCancelRespDTO;
import com.gl365.payment.enums.mq.MsgCategory;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.pay.DcType;
import com.gl365.payment.enums.pay.DealStatus;
import com.gl365.payment.enums.pay.OrderType;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.enums.pay.SplitFlag;
import com.gl365.payment.enums.system.GlSystem;
import com.gl365.payment.enums.system.OrganCode;
import com.gl365.payment.enums.system.ResultCode;
import com.gl365.payment.enums.user.AccountResultCode;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayDetail;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayReturn;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.remote.dto.account.request.CancelOperateReqDTO;
import com.gl365.payment.remote.dto.settlement.request.ConfirmPreSettleDateReqDTO;
import com.gl365.payment.remote.dto.settlement.response.ConfirmPreSettleDateRespDTO;
import com.gl365.payment.service.check.CheckPayRequestService;
import com.gl365.payment.service.dbservice.PayDetailService;
import com.gl365.payment.service.dbservice.PayMainService;
import com.gl365.payment.service.dbservice.PayReturnService;
import com.gl365.payment.service.dbservice.PayStreamService;
import com.gl365.payment.service.mq.dto.PaymentBody;
import com.gl365.payment.service.mq.dto.PaymentMQ;
import com.gl365.payment.service.mq.product.PaymentProduct;
import com.gl365.payment.service.wz.common.AbstractPay;
import com.gl365.payment.service.wz.common.Gl365SettlementService;
import com.gl365.payment.service.wz.common.Gl365UserAccountService;
import com.gl365.payment.service.wz.rm.cancel.cancel.RmWxCancelContext;
import com.gl365.payment.util.BigDecimalUtil;
import com.gl365.payment.util.Gl365StrUtils;
import com.gl365.payment.util.IdGenerator;
import com.gl365.payment.util.gson.GsonUtils;
public abstract class AbstractFftWxCancelBuild extends AbstractPay {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractFftWxCancelBuild.class);
	@Autowired
	protected PayMainService payMainService;
	@Autowired
	protected PayStreamService payStreamService;
	@Autowired
	protected PayReturnService payReturnService;
	@Autowired
	protected PayDetailService payDetailService;
	@Resource
	private PayAdapter payAdapter;
	@Autowired
	private PaymentProduct paymentProduct;
	@Autowired
	private Gl365UserAccountService gl365UserAccountService;
	@Autowired
	private CheckPayRequestService checkPayRequestService;
	@Autowired
	private Gl365SettlementService gl365SettlementService;

	protected void checkRequest(WxCancelReqDTO request) {
		this.checkPayRequestService.checkOrganCode(request.getOrganCode());
		this.checkPayRequestService.checkMerchantOrderNo(request.getMerchantOrderNo());
		this.checkPayRequestService.checkOrganOrderNo(request.getOrganOrderNo());
		this.checkPayRequestService.checkMerchantOrderNo(request.getOrigMerchantOrderNo());
		this.checkPayRequestService.checkOrganOrderNo(request.getOrigOrganOrderNo());
		this.checkPayRequestService.checkTotalAmount(request.getCashAmount());
		this.checkPayRequestService.checkPayTime(request.getOrganPayTime());
		this.checkPayRequestService.checkTerminal(request.getTerminal());
		this.checkPayRequestService.checkOperator(request.getOperator());
	}

	protected void buildStream(RmWxCancelContext ctx) {
		WxCancelReqDTO request = ctx.getRequest();
		PayStream ps = new PayStream();
		ps.setPayId(ctx.getPayId());
		ps.setRequestId(request.getMerchantOrderNo());
		ps.setRequestDate(LocalDate.now());
		ps.setOrigRequestId(request.getOrigMerchantOrderNo());
		ps.setOrganCode(request.getOrganCode());
		ps.setOperator(StringUtils.EMPTY);
		ps.setTransType(ctx.getTranType().getCode());
		ps.setTotalAmount(request.getCashAmount());
		ps.setReturnAmount(BigDecimal.ZERO);
		ps.setUniqueSerial(IdGenerator.getUuId32());
		ps.setDealStatus(DealStatus.SUCCESS.getCode());
		ps.setCreateTime(LocalDateTime.now());
		ps.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
		ps.setModifyBy(GlSystem.MODIFY_BY_GL_SYSTEM.getCode());
		ps.setModifyTime(LocalDateTime.now());
		LOG.debug("PayStream={}", Gl365StrUtils.toStr(ps));
		ctx.setPayStream(ps);
	}

	protected void queryPayMian(RmWxCancelContext ctx) {
		String merchantOrderNo = ctx.getRequest().getOrigMerchantOrderNo();
		PayMain payMain = this.payMainService.queryByMerchantOrderNo(merchantOrderNo);
		if (payMain == null) throw new ServiceException(Msg.PAY_8012.getCode(), Msg.PAY_8012.getDesc());
		LOG.debug("查原单值JOSN={}", GsonUtils.toJson(payMain));
		boolean b = PayStatus.COMPLETE_PAY.getCode().equals(payMain.getPayStatus()) || PayStatus.PART_PAY.getCode().equals(payMain.getPayStatus());
		if (!b) throw new ServiceException(Msg.PAY_8025.getCode(), Msg.PAY_8025.getDesc());
		boolean c = StringUtils.equals(ctx.getRequest().getOrigOrganOrderNo(), payMain.getOrganOrderNo());
		if (!c) throw new ServiceException(Msg.PAY_8026.getCode(), Msg.PAY_8026.getDesc());
		boolean d = ctx.getRequest().getCashAmount().compareTo(payMain.getCashAmount()) == 0;
		if (!d) throw new ServiceException(Msg.PAY_8014.getCode(), Msg.PAY_8014.getDesc());
		ctx.setPayMain(payMain);
	}

	protected void buildPayReturn(RmWxCancelContext ctx) {
		PayMain pm = ctx.getPayMain();
		Finance refund = new Finance();
		refund.setRefundType(DealStatus.PROCESS);
		refund.setCashAmount(pm.getCashAmount());
		refund.setBeanAmount(pm.getBeanAmount());
		refund.setGiftAmount(pm.getGiftAmount());
		refund.setMarcketFee(pm.getMarcketFee());
		refund.setPayFee(pm.getPayFee());
		refund.setCommAmount(pm.getCommAmount());
		refund.setMerchantSettlAmount(pm.getMerchantSettleAmount());
		refund.setTotalAmount(pm.getTotalAmount());
		PayReturn payReturn = new PayReturn();
		payReturn.setPayId(ctx.getPayId());
		payReturn.setPayTime(LocalDateTime.now());
		payReturn.setTransType(ctx.getTranType().getCode());
		payReturn.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
		payReturn.setCreateTime(LocalDateTime.now());
		payReturn.setModifyBy(GlSystem.MODIFY_BY_GL_SYSTEM.getCode());
		payReturn.setModifyTime(LocalDateTime.now());
		payAdapter.setPayReturn(payReturn, ctx.getPayMain());
		payReturn.setUserId(ctx.getGl365User().getUserId());
		payAdapter.setPayReturn(payReturn, refund);
		payReturn.setPayStatus(PayStatus.PART_PAY.getCode());
		payReturn.setPayDesc(PayStatus.PART_PAY.getDesc());
		payReturn.setTransType(ctx.getTranType().getCode());
		payReturn.setPayId(ctx.getPayId());
		payReturn.setRequestId(ctx.getRequest().getMerchantOrderNo());
		payReturn.setRequestDate(LocalDate.now());
		payReturn.setOrigPayId(ctx.getPayMain().getPayId());
		payReturn.setOrigPayDate(pm.getRequestDate());
		payReturn.setOrganCode(ctx.getRequest().getOrganCode());
		// payReturn.setOrganMerchantNo();
		payReturn.setTotalAmount(pm.getTotalAmount());
		payReturn.setPayTime(LocalDateTime.now());
		WxCancelReqDTO request = ctx.getRequest();
		payReturn.setMerchantOrderNo(request.getMerchantOrderNo());
		payReturn.setOrigMerchantOrderNo(request.getOrigMerchantOrderNo());
		payReturn.setOrganOrderNo(request.getOrganOrderNo());
		payReturn.setOrigOrganOrderNo(request.getOrigOrganOrderNo());
		payReturn.setOrganPayTime(request.getOrganPayTime());
		payReturn.setSettleMerchantNo(pm.getSettleMerchantNo());
		payReturn.setParentMerchantNo(pm.getParentMerchantNo());
		ConfirmPreSettleDateRespDTO res = ctx.getConfirmPreSettleDateRespDTO();
		LocalDate preSettleDate = res.getData().getPreSettleDate();
		payReturn.setPreSettleDate(preSettleDate);
		payReturn.setTerminal(request.getTerminal());
		payReturn.setOperator(request.getOperator());
		ctx.setPayReturn(payReturn);
	}

	protected void buildPayDetail(RmWxCancelContext ctx) {
		ArrayList<PayDetail> payDetails = new ArrayList<PayDetail>();
		PayDetail ps = new PayDetail();
		ps.setPayId(ctx.getPayId());
		ps.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
		ps.setCreateTime(LocalDateTime.now());
		ps.setOrganCode(ctx.getRequest().getOrganCode());
		PayReturn payReturn = ctx.getPayReturn();
		boolean isGroupMainOrder = OrderType.groupPay.getCode().equals(payReturn.getOrderType()) && SplitFlag.mainOrder.getCode().equals(payReturn.getSplitFlag());
		if (isGroupMainOrder) buildGroupMainOrderPayDetail(ctx, ps, payDetails);
		else {
			if (BigDecimalUtil.GreaterThan0(payReturn.getBeanAmount())) {
				ps.setPayType(PaymentConstants.PayType.PAY_BEAN.getCode());
				ps.setPayAccount(ctx.getGl365User().getUserId());
				ps.setPayAmount(payReturn.getBeanAmount());
				payDetails.add(ps);
			}
			if (BigDecimalUtil.GreaterThan0(payReturn.getCashAmount())) {
				PayDetail ps2 = ps.clone();
				ps2.setPayType(PaymentConstants.PayType.PAY_CASH.getCode());
				ps2.setPayAmount(payReturn.getCashAmount());
				ps2.setPayAccount(ctx.getGl365User().getUserId());
				payDetails.add(ps2);
			}
		}
		ctx.setPayDetails(payDetails);
	}

	private void buildGroupMainOrderPayDetail(RmWxCancelContext ctx, PayDetail ps, ArrayList<PayDetail> payDetails) {
		PayReturn payReturn = ctx.getPayReturn();
		if (BigDecimalUtil.GreaterThan0(payReturn.getGroupPtPay())) {
			ps.setPayType(PaymentConstants.PayType.PAY_BEAN.getCode());
			ps.setPayAmount(payReturn.getGroupPtPay());
			ps.setPayAccount("1000001900343");
			payDetails.add(ps);
		}
		if (BigDecimalUtil.GreaterThan0(payReturn.getGroupMainuserPayBean())) {
			PayDetail ps2 = ps.clone();
			ps2.setPayType(PaymentConstants.PayType.PAY_BEAN.getCode());
			ps2.setPayAccount(ctx.getGl365User().getUserId());
			ps2.setPayAmount(payReturn.getGroupMainuserPayBean());
			payDetails.add(ps2);
		}
		if (BigDecimalUtil.GreaterThan0(payReturn.getCashAmount())) {
			PayDetail ps3 = ps.clone();
			ps3.setPayType(PaymentConstants.PayType.PAY_CASH.getCode());
			ps3.setPayAmount(payReturn.getCashAmount());
			ps3.setPayAccount(ctx.getGl365User().getUserId());
			payDetails.add(ps3);
		}
	}

	protected void updateAccount(RmWxCancelContext ctx) {
		PayMain payMain = ctx.getPayMain();
		String origPayId = payMain.getPayId();
		String code = ctx.getTranType().getCode();
		CancelOperateReqDTO request = payAdapter.buildUpdateAccount(payMain, code, origPayId);
		request.setUserId(ctx.getGl365User().getUserId());
		setUpdateAmount(payMain, request);
		request.setPayId(ctx.getPayId());
		request.setDcType(DcType.C.getCode());
		boolean isNeedCancel = BigDecimalUtil.GreaterThan0(request.getOperateAmount()) || BigDecimalUtil.GreaterThan0(request.getGiftAmount());
		if (isNeedCancel) {
			String resultCode = gl365UserAccountService.wxCancelOperate(request);
			ctx.setAccountResultCode(resultCode);
		}
		else {
			ctx.setAccountResultCode(AccountResultCode.SUCCESS.getCode());
		}
	}

	private void setUpdateAmount(PayMain payMain, CancelOperateReqDTO request) {
		boolean isGroupOrder = OrderType.groupPay.getCode().equals(payMain.getOrderType());
		boolean isGroupMainOrder = isGroupOrder && SplitFlag.mainOrder.getCode().equals(payMain.getSplitFlag());
		boolean isGroupChildOrder = isGroupOrder && SplitFlag.childOrder.getCode().equals(payMain.getSplitFlag());
		BigDecimal operateAmount = payMain.getBeanAmount();
		BigDecimal giftAmount = payMain.getGiftAmount();
		if (isGroupMainOrder) {
			operateAmount = payMain.getGroupMainuserPayBean();
			giftAmount = payMain.getGroupGiftAmount();
		}
		if (isGroupChildOrder) {
			giftAmount = payMain.getGroupGiftAmount();
		}
		request.setOperateAmount(operateAmount);
		request.setGiftAmount(giftAmount);
	}

	protected void sendSettleMQ(RmWxCancelContext ctx) {
		PaymentBody paymentBody = new PaymentBody();
		ctx.setPayMain(this.payMainService.queryByPayId(ctx.getPayMain().getPayId()));
		ctx.setPayReturn(this.payReturnService.queryByPayId(ctx.getPayReturn().getPayId()));
		paymentBody.setPayMain(ctx.getPayMain());
		paymentBody.setPayStream(ctx.getPayStream());
		paymentBody.setPayReturn(ctx.getPayReturn());
		paymentBody.setPayDetails(ctx.getPayDetails());
		paymentBody.setPayModifyStatus(ctx.getPayMain().getPayStatus());
		paymentBody.setPayModifyTime(LocalDateTime.now());
		PaymentMQ paymentMQ = new PaymentMQ();
		paymentMQ.setMsgCategory(MsgCategory.normal.getCode());
		paymentMQ.setTranType(ctx.getTranType().getCode());
		paymentMQ.setPaymentBody(paymentBody);
		String json = GsonUtils.toJson(paymentMQ);
		LOG.info("发送清结算MQ消息={}", json);
		this.paymentProduct.send(json);
	}

	protected WxCancelRespDTO buildCancelResp(RmWxCancelContext ctx) {
		WxCancelRespDTO response = new WxCancelRespDTO();
		if (PayStatus.PART_PAY.getCode().equals(ctx.getPayReturn().getPayStatus())) {
			response.setResultCode(ResultCode.CANCEL_BEAN_FAIL.getCode());
			response.setResultDesc(ResultCode.CANCEL_BEAN_FAIL.getDesc());
		}
		else {
			response.setResultCode(ResultCode.SUCCESS.getCode());
			response.setResultDesc(ResultCode.SUCCESS.getDesc());
		}
		return response;
	}

	public void getConfirmPreSettleDate(RmWxCancelContext ctx) {
		ConfirmPreSettleDateReqDTO request = new ConfirmPreSettleDateReqDTO();
		request.setOrganCode(OrganCode.WX.getCode());
		request.setPayId(ctx.getPayId());
		request.setTransType("2");
		request.setOrganPayTime(ctx.getRequest().getOrganPayTime());
		request.setOrigOrganPayTime(ctx.getPayMain().getOrganPayTime());
		request.setOrigPreSettleDate(ctx.getPayMain().getPreSettleDate());
		ConfirmPreSettleDateRespDTO result = this.gl365SettlementService.getConfirmPreSettleDate(request);
		ctx.setConfirmPreSettleDateRespDTO(result);
	}
}
