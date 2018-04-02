package com.gl365.payment.service.wz.rm.cancel.query.impl;
import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.dto.wx.request.WxCancelQueryReqDTO;
import com.gl365.payment.dto.wx.response.WxCancelQueryRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.pay.PayCategory;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.enums.system.Agent;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.remote.dto.account.response.QueryAccountBalanceInfoRespDTO;
import com.gl365.payment.remote.dto.member.Gl365User;
import com.gl365.payment.remote.dto.merchant.Gl365Merchant;
import com.gl365.payment.service.wz.rm.cancel.query.RmWxCancelQueryService;
import com.gl365.payment.service.wz.rm.cancel.query.RmWxCancelQueryContext;
import com.gl365.payment.util.gson.GsonUtils;
@Service
public class RmWxCancelQueryServiceImpl extends AbstractRmWxCancelQuery implements RmWxCancelQueryService {
	private static final Logger LOG = LoggerFactory.getLogger(RmWxCancelQueryServiceImpl.class);

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public WxCancelQueryRespDTO cancelQuery(WxCancelQueryReqDTO request) {
		RmWxCancelQueryContext ctx = new RmWxCancelQueryContext();
		ctx.setRequest(request);
		this.generatePayId(ctx);
		this.checkRequestParams(ctx);
		this.savePayStream(ctx);
		this.queryPayMain(ctx);
		this.checkUser(ctx);
		this.checkMerchantInfo(ctx);
		this.queryAccountBalanceInfo(ctx);
		this.isCanCancel(ctx);
		this.buildResp(ctx);
		return ctx.getResponse();
	}

	private void generatePayId(RmWxCancelQueryContext ctx) {
		LOG.info(this.getLog(0, this.initTranType().getDesc()));
		LOG.info(this.totalStep() + "-1-生成给乐交易单号");
		String payCategory = this.initPayCategoryCode();
		String payId = this.payContextService.generatePayId(payCategory);
		ctx.getPayStream().setPayId(payId);
	}

	private void checkRequestParams(RmWxCancelQueryContext ctx) {
		LOG.info(this.totalStep() + "-2-生成给乐交易单号");
		this.checkRequest(ctx);
	}

	private void savePayStream(RmWxCancelQueryContext ctx) {
		LOG.info(this.totalStep() + "-3-写流水表");
		this.buildPayStream(ctx);
		PayStream payStream = ctx.getPayStream();
		this.payStreamService.save(payStream);
	}

	private void queryPayMain(RmWxCancelQueryContext ctx) {
		LOG.info(this.totalStep() + "-4-查原单并检查");
		String merchantOrderNo = ctx.getRequest().getMerchantOrderNo();
		String organCode = ctx.getRequest().getOrganCode();
		PayMain payMain = this.payMainService.queryByParam(merchantOrderNo, organCode);
		if (payMain == null) throw new ServiceException(Msg.PAY_8012.getCode(), Msg.PAY_8012.getDesc());
		String payStatus = payMain.getPayStatus();
		ctx.setPayMain(payMain);
		if (isPartPay(ctx)) return;
		boolean b = StringUtils.equals(payStatus, PayStatus.COMPLETE_PAY.getCode());
		if (!b) throw new ServiceException(Msg.PAY_8004.getCode(), Msg.PAY_8004.getDesc());
	}

	private boolean isPartPay(RmWxCancelQueryContext ctx) {
		LOG.info("检查订单状态是否为部份支付 ");
		PayMain payMain = ctx.getPayMain();
		String payStatus = payMain.getPayStatus();
		boolean c = StringUtils.equals(payStatus, PayStatus.PART_PAY.getCode());
		LOG.info("检查订单状态是否为部份支付结果={}", c);
		return c;
	}

	private void checkUser(RmWxCancelQueryContext ctx) {
		LOG.info(this.totalStep() + "-5-查用户并检查状态");
		String userId = ctx.getPayMain().getUserId();
		Gl365User gl365User = this.gl365UserService.queryGl365UserByNewUserId(userId);
		ctx.setGl365User(gl365User);
		this.gl365UserService.checkGl365User(gl365User);
	}

	private void checkMerchantInfo(RmWxCancelQueryContext ctx) {
		LOG.info(this.totalStep() + "-6-查商户并检查状态");
		PayMain payMain = ctx.getPayMain();
		String organCode = payMain.getOrganCode();
		String organMerchantNo = payMain.getMerchantNo();
		String scene = payMain.getScene();
		String payChannleType = this.getPayChannleType(scene);
		Gl365Merchant gl365Merchant = this.gl365MerchantService.queryGl365Merchant(organMerchantNo, organCode, payChannleType);
		ctx.setGl365Merchant(gl365Merchant);
		this.gl365MerchantService.checkGl365Merchant(gl365Merchant);
	}

	private void queryAccountBalanceInfo(RmWxCancelQueryContext ctx) {
		if (isPartPay(ctx)) return;
		LOG.info(this.totalStep() + "-7-查用户乐豆账户余额");
		String userId = ctx.getGl365User().getUserId();
		QueryAccountBalanceInfoRespDTO dto = this.gl365UserAccountService.queryGl365AccountBalance(userId, Agent.GL365.getKey());
		ctx.setAccDto(dto);
	}

	private void isCanCancel(RmWxCancelQueryContext ctx) {
		if (isPartPay(ctx)) return;
		LOG.info(this.totalStep() + "-8-检查是否可以退款");
		BigDecimal beanBalance = ctx.getAccDto().getBalance();
		BigDecimal giftAmount = ctx.getPayMain().getGiftAmount();
		BigDecimal beanAmount = ctx.getPayMain().getBeanAmount();
		int b = beanBalance.add(beanAmount).compareTo(giftAmount);
		if (b == -1) throw new ServiceException(Msg.PAY_8024.getCode(), Msg.PAY_8024.getDesc());
	}

	private void buildResp(RmWxCancelQueryContext ctx) {
		LOG.info(this.totalStep() + "-9-组装返回结果");
		this.buildResponseResult(ctx);
		LOG.info("交易返回结果={}", GsonUtils.toJson(ctx.getResponse()));
	}

	public PayStatus initPayStatus() {
		return null;
	}

	@Override
	public TranType initTranType() {
		return TranType.REFUND_QUERY;
	}

	@Override
	public String initPayCategoryCode() {
		return PayCategory.PAY_71.getCode();
	}

	@Override
	public int totalStep() {
		return 9;
	}
}
