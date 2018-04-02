package com.gl365.payment.service.wz.fft.reward.abs;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.gl365.payment.common.Finance;
import com.gl365.payment.common.PayAdapter;
import com.gl365.payment.dto.wx.request.WxRewardReqDTO;
import com.gl365.payment.dto.wx.response.WxPrePayRespDataDTO;
import com.gl365.payment.dto.wx.response.WxRewardRespDTO;
import com.gl365.payment.enums.pay.CardType;
import com.gl365.payment.enums.pay.DcType;
import com.gl365.payment.enums.pay.DealStatus;
import com.gl365.payment.enums.pay.OrderType;
import com.gl365.payment.enums.pay.PayChannel;
import com.gl365.payment.enums.pay.Scene;
import com.gl365.payment.enums.system.Flag;
import com.gl365.payment.enums.system.GlSystem;
import com.gl365.payment.enums.system.ResultCode;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.remote.dto.merchant.Gl365Merchant;
import com.gl365.payment.service.check.CheckPayRequestService;
import com.gl365.payment.service.dbservice.PayMainService;
import com.gl365.payment.service.wz.common.AbstractPay;
import com.gl365.payment.service.wz.common.WxPrePayCalcuateService;
import com.gl365.payment.service.wz.rm.reward.RmWxRewardContext;
import com.gl365.payment.util.CalculationFormula;
import com.gl365.payment.util.FormatUtil;

public abstract class AbstractFftWxRewardBuild extends AbstractPay{
	private static final Logger LOG = LoggerFactory.getLogger(AbstractFftWxRewardBuild.class);
	@Resource
	private PayAdapter payAdapter;
	@Autowired
	public PayMainService payMainService;
	@Autowired
	private WxPrePayCalcuateService wxPrePayCalcuateService;
	@Autowired
	private CheckPayRequestService checkPayRequestService;
	
	protected void checkRequest(WxRewardReqDTO request) {
		this.checkPayRequestService.checkOrganCode(request.getOrganCode());
		this.checkPayRequestService.checkRequestDate(FormatUtil.parseYyyyMMdd(request.getRequestDate()));
		this.checkPayRequestService.checkMerchantNo(request.getMerchantNo());
		this.checkPayRequestService.checkOrganMerchantNo(request.getOrganMerchantNo());
		this.checkPayRequestService.checkUserId(request.getUserId());
		this.checkPayRequestService.checkTotalAmount(request.getTotalAmount());
		this.checkPayRequestService.checkMerchantOrderTitle(request.getMerchantOrderTitle());
		this.checkPayRequestService.checkMerchantOrderDesc(request.getMerchantOrderDesc());
		this.checkPayRequestService.checkNoBenefitAmount(request.getNoBenefitAmount());
		this.checkPayRequestService.checkPayLdMoney(request.getPayLdMoney());
		this.checkPayRequestService.checkUserId(request.getRewardUserId());
		this.checkPayRequestService.checkRewardPayId(request.getRewardPayId());
		this.checkPayRequestService.checkScene(request.getScene());
		this.checkPayRequestService.checkRewardUserId(request.getUserId(), request.getRewardUserId());
	}
	
	protected PayStream buildStream(RmWxRewardContext ctx) {
		PayStream ps = new PayStream();
		ps.setPayId(ctx.getPayId());
		ps.setTransType(ctx.getTranType().getCode());
		ps.setReturnAmount(null);
		ps.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
		ps.setCreateTime(LocalDateTime.now());
		ps.setModifyBy(GlSystem.MODIFY_BY_GL_SYSTEM.getCode());
		ps.setModifyTime(LocalDateTime.now());
		ps.setDealStatus(DealStatus.WAITING_FOR_PAYMENT.getCode());
		String requestId = ctx.getRequest().getMerchantOrderNo();
		ps.setRequestId(requestId);
		String requestDate = ctx.getRequest().getRequestDate();
		ps.setRequestDate(FormatUtil.parseYyyyMMdd(requestDate));
		ps.setOrganCode(ctx.getRequest().getOrganCode());
		ps.setOrganMerchantNo(ctx.getRequest().getOrganMerchantNo());
		ps.setTotalAmount(ctx.getRequest().getTotalAmount());
		ps.setReturnAmount(BigDecimal.ZERO);
		String mergeString = FormatUtil.mergeString(requestId);
		ps.setUniqueSerial(mergeString);
		ctx.setPayStream(ps);
		return ps;
	}
	
	protected void buildRewardPayData(RmWxRewardContext ctx) {
		Finance finance = new Finance();
		finance.setGiftAmount(BigDecimal.ZERO);
		WxRewardReqDTO request = ctx.getRequest();
		BigDecimal beanAmount = request.getPayLdMoney();
		BigDecimal cashAmount = request.getTotalAmount().subtract(request.getPayLdMoney());
		finance.setBeanAmount(beanAmount);
		finance.setCashAmount(cashAmount);
		calculation(ctx, finance);
		buildPayMain(ctx, DealStatus.WAITING_FOR_PAYMENT, finance);
		calculationDecAmount(ctx);
	}
	
	protected WxRewardRespDTO buildRewardResp(RmWxRewardContext ctx) {
		WxRewardRespDTO resp = new WxRewardRespDTO();
		resp.setResultCode(ResultCode.SUCCESS.getCode());
		resp.setResultDesc(ResultCode.SUCCESS.getDesc());
		WxPrePayRespDataDTO data = new WxPrePayRespDataDTO();
		PayMain payMain = ctx.getPayMain();
		data.setGiftAmount(payMain.getGiftAmount());
		data.setPayId(payMain.getPayId());
		data.setDecAmount(payMain.getDecAmount());
		data.setDetails(ctx.getRespDataDetails());
		data.setCreateTime(payMain.getCreateTime());
		data.setCashAmount(payMain.getCashAmount());
		resp.setData(data);
		return resp;
	}
	
	private void calculation(RmWxRewardContext ctx, Finance finance) {
		BigDecimal beanAmount = finance.getBeanAmount();
		BigDecimal cashAmount = finance.getCashAmount();
		LOG.info("乐豆={},银行扣款={}", beanAmount, cashAmount);
		// 计算返佣金额、营销费、支付手续费
		String cardType = getDefaultCardType();
		Gl365Merchant gl365Merchant = ctx.getGl365Merchant();
		// 支付手续费
		BigDecimal payFee = this.wxPrePayCalcuateService.calculatePayFee(gl365Merchant, cashAmount, cardType, PayChannel.ONLINE.getCode());
		finance.setPayFee(payFee);
		// 返佣金额
		finance.setCommAmount(payFee);
		BigDecimal commRate = CalculationFormula.calcCommRate(ctx.getRequest().getTotalAmount(), BigDecimal.ZERO, finance.getGiftAmount(), finance.getCommAmount());
		ctx.getGl365Merchant().setGlFeeRate(commRate);
		// 营销费
		BigDecimal marketFee = BigDecimal.ZERO;
		finance.setMarcketFee(marketFee);
		// 商户实得金额
		BigDecimal giftAmount = finance.getGiftAmount();
		BigDecimal commAmount = finance.getCommAmount();
		BigDecimal merchantSettleAmount = this.wxPrePayCalcuateService.calculateMerchantSettleAmount(beanAmount, cashAmount, commAmount, giftAmount);
		finance.setMerchantSettlAmount(merchantSettleAmount);
		LOG.info("返利={},返佣金额={},营销费={},支付手续费={},商户实得金额={}", giftAmount, commAmount, marketFee, payFee, merchantSettleAmount);
	}
	
	private PayMain buildPayMain(RmWxRewardContext ctx, DealStatus dealStatus, Finance finance) {
		PayMain payMain = new PayMain();
		payMain.setPayId(ctx.getPayId());
		payMain.setPayTime(LocalDateTime.now());
		payMain.setTransType(ctx.getTranType().getCode());
		payMain.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
		payMain.setCreateTime(LocalDateTime.now());
		payMain.setModifyBy(GlSystem.MODIFY_BY_GL_SYSTEM.getCode());
		payMain.setModifyTime(LocalDateTime.now());
		payMain.setPayStatus(dealStatus.getCode());
		payMain.setPayDesc(dealStatus.getDesc());
		payMain = payAdapter.setPayMain(payMain, ctx.getGl365UserAccount());
		payMain.setPayFeeType(CardType.C.getCode());
		payMain = payAdapter.setPayMain(payMain, ctx.getGl365User());
		Gl365Merchant gl365Merchant = ctx.getGl365Merchant();
		payMain = payAdapter.setPayMain(payMain, gl365Merchant, DcType.C, Scene.FAST_PAY);
		payMain = payAdapter.setPayMain(payMain, finance);
		WxRewardReqDTO request = ctx.getRequest();
		payMain.setRequestDate(FormatUtil.parseYyyyMMdd(request.getRequestDate()));
		payMain.setOrganCode(request.getOrganCode());
		payMain.setOrganMerchantNo(request.getOrganMerchantNo());
		payMain.setUserId(request.getUserId());
		payMain.setTotalAmount(request.getTotalAmount());
		payMain.setNoBenefitAmount(request.getNoBenefitAmount());
		payMain.setRequestId(request.getMerchantOrderNo());
		payMain.setRewardUserId(request.getRewardUserId());
		payMain.setRewardPayId(request.getRewardPayId());
		payMain.setOrderType(OrderType.ds.getCode());
		payMain.setScene(request.getScene());
		payMain.setMerchantOrderNo(request.getMerchantOrderNo());
		payMain.setMerchantOrderTitle(request.getMerchantOrderTitle());
		payMain.setMerchentOrderDesc(request.getMerchantOrderDesc());
		payMain.setIsNotify(Flag.N.getCode());
		ctx.setPayMain(payMain);
		return payMain;
	}
	
	private void calculationDecAmount(RmWxRewardContext ctx) {
		PayMain payMain = ctx.getPayMain();
		BigDecimal totalAmt = payMain.getTotalAmount();
		BigDecimal cashAmt = payMain.getCashAmount();
		BigDecimal decAmount = this.wxPrePayCalcuateService.calculateDecAmt(totalAmt, cashAmt);
		payMain.setDecAmount(decAmount);
	}
	
	private String getDefaultCardType() {
		return CardType.C.getCode();
	}
}	
