package com.gl365.payment.service.wz.fft.pre.mpay.abs;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import com.gl365.payment.dto.wx.request.WxPrePayReqDTO;
import com.gl365.payment.dto.wx.response.WxPrePayRespDTO;
import com.gl365.payment.dto.wx.response.WxPrePayRespDataDTO;
import com.gl365.payment.enums.pay.CardType;
import com.gl365.payment.enums.pay.PayChannel;
import com.gl365.payment.enums.pay.PayCurrency;
import com.gl365.payment.enums.system.GlSystem;
import com.gl365.payment.enums.system.OrganCode;
import com.gl365.payment.enums.system.ResultCode;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.remote.dto.member.Gl365User;
import com.gl365.payment.remote.dto.merchant.Gl365Merchant;
import com.gl365.payment.service.wz.common.AbstractPay;
import com.gl365.payment.service.wz.common.WxPrePayCalcuateService;
import com.gl365.payment.service.wz.rm.pre.pay.RmWxPrePayContext;
public abstract class AbstractFftWxPrePayParticipant extends AbstractPay {
	@Autowired
	private WxPrePayCalcuateService wxPrePayCalcuateService;

	public abstract String getOrderType(RmWxPrePayContext ctx);

	public void checkRequest(RmWxPrePayContext ctx) {
		WxPrePayReqDTO request = ctx.getRequest();
		this.checkPayRequestService.checkOrganCode(request.getOrganCode());
		this.checkPayRequestService.checkOrganMerchantNo(request.getOrganMerchantNo());
		this.checkPayRequestService.checkMerchantNo(request.getMerchantNo());
		this.checkPayRequestService.checkUserId(request.getUserId());
		this.checkPayRequestService.checkTotalAmount(request.getTotalAmount());
		this.checkPayRequestService.checkScene(request.getScene());
		this.checkPayRequestService.checkMerchantOrderTitle(request.getMerchantOrderTitle());
		this.checkPayRequestService.checkMerchantOrderDesc(request.getMerchantOrderDesc());
		this.checkPayRequestService.checkNoBenefitAmount(request.getNoBenefitAmount());
		this.checkPayRequestService.checkOrderType(request.getOrderType());
		this.checkPayRequestService.checkMerchantOrderNo(request.getMerchantOrderNo());
		this.checkPayRequestService.checkGroupOrderId(request.getGroupOrderId());
		this.checkPayRequestService.checkSplitFlag(request.getSplitFlag());
		this.checkPayRequestService.checkGroupMerchantNo(request.getGroupMerchantNo());
		this.checkPayRequestService.checkScene(request.getScene());
	}
	
	public void initPayDefault(RmWxPrePayContext ctx) {
		final BigDecimal cashAmount = ctx.getRequest().getTotalAmount();
		PayMain pp = ctx.getPayMain();
		pp.setBeanAmount(BigDecimal.ZERO);
		pp.setCashAmount(cashAmount);
		pp.setGiftPoint(BigDecimal.ZERO);
		if (pp.getNoBenefitAmount() == null) pp.setNoBenefitAmount(BigDecimal.ZERO);
		pp.setTransType(this.initTranType().getCode());
		pp.setPayStatus(this.initPayStatus().getCode());
		pp.setPayDesc(this.initPayStatus().getDesc());
		LocalDateTime now = LocalDateTime.now();
		pp.setCreateTime(now);
		pp.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
		pp.setModifyTime(now);
		pp.setModifyBy(GlSystem.MODIFY_BY_GL_SYSTEM.getCode());
		pp.setCoinAmount(BigDecimal.ZERO);
		pp.setSettleOrganNo(OrganCode.WX.getCode());
	}
	
	public void putRequest(RmWxPrePayContext ctx) {
		PayMain pp = ctx.getPayMain();
		WxPrePayReqDTO request = ctx.getRequest();
		pp.setRequestId(request.getMerchantOrderNo());
		pp.setRequestDate(request.getRequestDate());
		pp.setOrganCode(request.getOrganCode());
		pp.setOrganMerchantNo(request.getOrganMerchantNo());
		pp.setTerminal(null);
		pp.setOperator(request.getOperator());
		pp.setScene(request.getScene());
		pp.setMerchantOrderTitle(request.getMerchantOrderTitle());
		pp.setMerchentOrderDesc(request.getMerchantOrderDesc());
		pp.setMerchantOrderNo(request.getMerchantOrderNo());
		pp.setCardIndex(null);
		pp.setTotalAmount(request.getTotalAmount());
		pp.setNoBenefitAmount(request.getNoBenefitAmount());
		pp.setOrderType(this.getOrderType(ctx));
		pp.setGroupOrderId(request.getGroupOrderId());
		pp.setSplitFlag(request.getSplitFlag());
		pp.setGroupMerchantNo(request.getGroupMerchantNo());
	}
	
	public void putGl365User(RmWxPrePayContext ctx) {
		Gl365User gl365User = ctx.getGl365User();
		PayMain pp = ctx.getPayMain();
		String userAgentType = String.valueOf(gl365User.getAgentType());
		pp.setUserAgentType(userAgentType);
		pp.setUserAgentNo(gl365User.getAgentNo());
		pp.setUserId(gl365User.getUserId());
		pp.setUserName(gl365User.getUserName());
		pp.setUserDevManager(gl365User.getUserDevManager());
		pp.setUserDevStaff(gl365User.getUserDevStaff());
		pp.setUserMobile(gl365User.getUserMobile());
		pp.setJoinType(gl365User.getJoinType());
	}
	
	public void buildPayMain(RmWxPrePayContext ctx) {
		this.initPayDefault(ctx);
		this.putRequest(ctx);
		this.putGl365User(ctx);
		this.putGl365Merchant(ctx);
		this.calculateBeanAmount(ctx);
		this.calculate(ctx);
	}
	
	public void buildPayStream(RmWxPrePayContext ctx) {
		WxPrePayReqDTO request = ctx.getRequest();
		PayStream ps = ctx.getPayStream();
		ps.setRequestId(request.getMerchantOrderNo());
		ps.setRequestDate(request.getRequestDate());
		ps.setOrigRequestId(null);
		ps.setOrigPayDate(null);
		ps.setOrganCode(request.getOrganCode());
		ps.setOrganMerchantNo(request.getOrganMerchantNo());
		ps.setTerminal(null);
		ps.setOperator(request.getOperator());
		ps.setTransType(this.initTranType().getCode());
		ps.setTotalAmount(request.getTotalAmount());
		ps.setReturnAmount(BigDecimal.ZERO);
		ps.setUniqueSerial(UUID.randomUUID().toString());
		ctx.setPayStream(ps);
	}

	public void putGl365Merchant(RmWxPrePayContext ctx) {
		Gl365Merchant mt = ctx.getGl365Merchant();
		Gl365Merchant groupMerchant = ctx.getGroupMerchant();
		PayMain pp = ctx.getPayMain();
		pp.setMerchantNo(mt.getMerchantNo());
		pp.setMerchantName(mt.getMerchantShortname());
		pp.setSettleOrganNo(mt.getSettleOrganNo());
		pp.setParentAgentNo(mt.getParentAgentNo());
		pp.setInviteAgentNo(mt.getInviteAgentNo());
		pp.setMerchantAgentNo(mt.getAgentNo());
		pp.setProvince(mt.getProvince());
		pp.setCity(mt.getCity());
		pp.setDistrict(mt.getDistrict());
		pp.setSettleMerchantNo(mt.getSettleMerchant());
		pp.setParentMerchantNo(mt.getParentMerchantNo());
		BigDecimal giftRate = groupMerchant.getSaleRate();
		pp.setGiftRate(giftRate);// 返利率
		WxPrePayReqDTO request = ctx.getRequest();
		BigDecimal gr = giftRate.divide(new BigDecimal(100));
		BigDecimal tAmt = request.getTotalAmount();
		BigDecimal noBfAmt = pp.getNoBenefitAmount();
		BigDecimal giftAmt = (tAmt.subtract(noBfAmt)).multiply(gr).setScale(2, RoundingMode.HALF_UP);
		pp.setGroupGiftAmount(giftAmt);// 返利金额
		pp.setGiftAmount(BigDecimal.ZERO);
		pp.setGiftPoint(BigDecimal.ZERO);// 赠送积分
		pp.setPayFeeType(this.getDefaultCardType());
		BigDecimal payFeeRate = this.wxPrePayCalcuateService.getPayFeeRate(groupMerchant, this.getDefaultCardType(), PayChannel.ONLINE.getCode());
		pp.setPayFeeRate(payFeeRate);// 支付手续费率(区分借贷)
		BigDecimal maxPayFee = this.wxPrePayCalcuateService.getMaxPayFee(groupMerchant, this.getDefaultCardType(), PayChannel.ONLINE.getCode());
		pp.setMaxPayFee(maxPayFee);// 支付手续费上限(区分借贷)
	}

	public void calculateBeanAmount(RmWxPrePayContext ctx) {
		Gl365Merchant groupMerchant = ctx.getGroupMerchant();
		Gl365User gl365User = ctx.getGl365User();
		BigDecimal bAmt = ctx.getGl365UserAccBalance().getBalance();
		PayMain pm = ctx.getPayMain();
		BigDecimal tAmt = pm.getTotalAmount();
		Map<String, BigDecimal> map = this.wxPrePayCalcuateService.getPayBeanAmt(groupMerchant, gl365User, tAmt, bAmt);
		BigDecimal beanAmt = map.get(PayCurrency.BEAN.getCode());
		BigDecimal cashAmt = map.get(PayCurrency.CASH.getCode());
		pm.setBeanAmount(beanAmt);
		pm.setCashAmount(cashAmt);
	}

	public void calculate(RmWxPrePayContext ctx) {
		Gl365Merchant gl365Merchant = ctx.getGroupMerchant();
		PayMain pm = ctx.getPayMain();
		BigDecimal cashAmt = pm.getCashAmount();
		BigDecimal beanAmt = pm.getBeanAmount();
		BigDecimal giftAmt = pm.getGroupGiftAmount();
		BigDecimal totalAmt = pm.getTotalAmount();
		BigDecimal payFee = this.wxPrePayCalcuateService.calculatePayFee(gl365Merchant, cashAmt, this.getDefaultCardType(), PayChannel.ONLINE.getCode());
		pm.setPayFee(payFee);
		pm.setCommAmount(payFee);
		BigDecimal commRate = wxPrePayCalcuateService.calculateCommRate(totalAmt, BigDecimal.ZERO, giftAmt, payFee);
		pm.setCommRate(commRate);
		pm.setMarcketFee(BigDecimal.ZERO);
		BigDecimal merchantSettleAmount = this.wxPrePayCalcuateService.calculateMerchantSettleAmount(beanAmt, cashAmt, pm.getCommAmount(), giftAmt);
		pm.setMerchantSettleAmount(merchantSettleAmount);
		pm.setDecAmount(this.wxPrePayCalcuateService.calculateDecAmt(totalAmt, cashAmt));
		pm.setGroupMainuserPay(BigDecimal.ZERO);
		pm.setGroupPtPay(BigDecimal.ZERO);
		pm.setGroupMainuserPayBean(BigDecimal.ZERO);
	}
	
	public void buildResp(RmWxPrePayContext ctx) {
		WxPrePayRespDTO resp = ctx.getResponse();
		resp.setResultCode(ResultCode.SUCCESS.getCode());
		resp.setResultDesc(ResultCode.SUCCESS.getDesc());
		WxPrePayRespDataDTO data = resp.getData();
		PayMain payMain = ctx.getPayMain();
		data.setGiftAmount(payMain.getGroupGiftAmount());
		data.setPayId(payMain.getPayId());
		data.setCashAmount(payMain.getCashAmount());
		data.setDecAmount(payMain.getDecAmount());
		data.setCreateTime(payMain.getCreateTime());
		data.setTotalAmount(payMain.getTotalAmount());
		data.setCoinAmount(payMain.getCoinAmount());
		data.setGiftPoint(payMain.getGiftAmount());
		data.setMarcketFee(payMain.getMarcketFee());
		data.setBeanAmount(payMain.getBeanAmount());
		data.setMerchantSettleAmount(payMain.getMerchantSettleAmount());
		ctx.setResponse(resp);
	}

	private String getDefaultCardType() {
		return CardType.C.getCode();
	}
}
