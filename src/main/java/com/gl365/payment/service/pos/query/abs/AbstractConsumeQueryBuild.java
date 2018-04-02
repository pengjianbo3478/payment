package com.gl365.payment.service.pos.query.abs;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import com.gl365.payment.dto.pretranscation.PreTranContext;
import com.gl365.payment.dto.pretranscation.request.PreTranReqDTO;
import com.gl365.payment.enums.pay.CardType;
import com.gl365.payment.enums.pay.Scene;
import com.gl365.payment.model.PayPrepay;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.remote.dto.account.Gl365UserAccount;
import com.gl365.payment.remote.dto.member.Gl365User;
import com.gl365.payment.remote.dto.merchant.Gl365Merchant;
public abstract class AbstractConsumeQueryBuild extends AbstractConsumeQueryResp {
	public PayPrepay buildPayPrepay(PreTranContext ctx) {
		this.initPayDefault(ctx);
		this.putRequest(ctx);
		this.putGl365UserAccount(ctx);
		this.putGl365User(ctx);
		this.putGl365Merchant(ctx);
		if (isPayBean()) this.calculateBeanAmount(ctx);
		this.calculate(ctx);
		return ctx.getPayPrepay();
	}

	public void putGl365UserAccount(PreTranContext ctx) {
		Gl365UserAccount gl365UserAccount = ctx.getGl365UserAccount();
		PayPrepay pp = ctx.getPayPrepay();
		pp.setCardNo(gl365UserAccount.getCardNo());
	}

	public void buildPayStream(PreTranContext ctx) {
		PreTranReqDTO request = ctx.getPreTranReqDTO();
		PayStream ps = new PayStream();
		ps.setPayId(ctx.getPayId());
		ps.setRequestId(request.getRequestId());
		ps.setRequestDate(request.getRequestDate());
		ps.setOrigRequestId(null);
		ps.setOrigPayDate(null);
		ps.setOrganCode(request.getOrganCode());
		ps.setOrganMerchantNo(request.getOrganMerchantNo());
		ps.setTerminal(request.getTerminal());
		ps.setOperator(ctx.getPreTranReqDTO().getOperator());
		ps.setTransType(this.initTranType());
		ps.setTotalAmount(ctx.getPreTranReqDTO().getTotalAmount());
		ps.setReturnAmount(BigDecimal.ZERO);
		ps.setUniqueSerial(UUID.randomUUID().toString());
		ctx.setPayStream(ps);
	}

	public void putRequest(PreTranContext ctx) {
		PayPrepay pp = ctx.getPayPrepay();
		PreTranReqDTO request = ctx.getPreTranReqDTO();
		pp.setRequestId(request.getRequestId());
		pp.setRequestDate(request.getRequestDate());
		pp.setOrganCode(request.getOrganCode());
		pp.setOrganMerchantNo(request.getOrganMerchantNo());
		pp.setTerminal(request.getTerminal());
		pp.setOperator(request.getOperator());
		pp.setScene(Scene.POS_PAY.getCode());// 支付场景
		pp.setMerchantOrderTitle(request.getMerchantOrderTitle());// 订单标题
		pp.setMerchentOrderDesc(request.getMerchantOrderDesc());// 订单描述
		pp.setMerchantOrderNo(request.getMerchantOrderNo());// 商户订单号
		pp.setCardIndex(request.getCardIndex());
		pp.setTotalAmount(request.getTotalAmount());// 设置交易总金额
		pp.setNoBenefitAmount(request.getNoBenefitAmount());// 不可返利金额
		pp.setOrderType(this.getOrderType(ctx));
	}

	public void putGl365User(PreTranContext ctx) {
		Gl365User gl365User = ctx.getGl365User();
		PayPrepay pp = ctx.getPayPrepay();
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

	public void putGl365Merchant(PreTranContext ctx) {
		Gl365Merchant mt = ctx.getGl365Merchant();
		PayPrepay pp = ctx.getPayPrepay();
		pp.setMerchantNo(mt.getMerchantNo());
		pp.setMerchantName(mt.getMerchantShortname());
		pp.setSettleOrganNo(mt.getSettleOrganNo());
		pp.setParentAgentNo(mt.getParentAgentNo());
		pp.setInviteAgentNo(mt.getInviteAgentNo());
		pp.setMerchantAgentNo(mt.getAgentNo());
		pp.setProvince(mt.getProvince());
		pp.setCity(mt.getCity());
		pp.setDistrict(mt.getDistrict());
		pp.setParentMerchantNo(mt.getParentMerchantNo());
		pp.setSettleMerchantNo(mt.getSettleMerchant());
		BigDecimal giftRate = mt.getSaleRate();
		pp.setGiftRate(giftRate);// 返利率
		pp.setCommRate(mt.getGlFeeRate());// 返佣率
		PreTranReqDTO request = ctx.getPreTranReqDTO();
		BigDecimal gr = giftRate.divide(new BigDecimal(100));
		BigDecimal tAmt = request.getTotalAmount();
		BigDecimal noBfAmt = pp.getNoBenefitAmount();
		BigDecimal giftAmt = (tAmt.subtract(noBfAmt)).multiply(gr).setScale(2, RoundingMode.HALF_UP);
		pp.setGiftAmount(giftAmt);// 返利金额
		pp.setGiftPoint(giftAmt);// 赠送积分
		Gl365UserAccount account = ctx.getGl365UserAccount();
		String cardType = account.getCardType();// 卡内型
		pp.setPayFeeType(cardType);
		if (StringUtils.equals(cardType, CardType.D.getCode())) {
			pp.setPayFeeRate(mt.getPosDebitFeeRate());// 支付手续费率(区分借贷)
			pp.setMaxPayFee(mt.getPosDebitMaxAmt());
		}
		if (StringUtils.equals(cardType, CardType.C.getCode())) {
			pp.setPayFeeRate(mt.getPosCreditFeeRate());// 支付手续费率(区分借贷)
			pp.setMaxPayFee(mt.getPosCreditMaxAmt());
		}
	}
}
