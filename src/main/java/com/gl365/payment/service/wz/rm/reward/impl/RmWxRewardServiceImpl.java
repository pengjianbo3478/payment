package com.gl365.payment.service.wz.rm.reward.impl;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.dto.wx.request.WxRewardReqDTO;
import com.gl365.payment.dto.wx.response.WxRewardRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.pay.PayCategory;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.enums.system.Agent;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.remote.dto.account.response.QueryAccountBalanceInfoRespDTO;
import com.gl365.payment.remote.dto.member.Gl365User;
import com.gl365.payment.remote.dto.merchant.Gl365Merchant;
import com.gl365.payment.service.dbservice.PayStreamService;
import com.gl365.payment.service.wz.common.Gl365MerchantService;
import com.gl365.payment.service.wz.common.Gl365UserAccountService;
import com.gl365.payment.service.wz.common.Gl365UserService;
import com.gl365.payment.service.wz.rm.reward.RmWxRewardService;
import com.gl365.payment.service.wz.rm.reward.RmWxRewardContext;
import com.gl365.payment.service.wz.rm.reward.abs.AbstractRmWxRewardBuild;
import com.gl365.payment.util.BigDecimalUtil;
import com.gl365.payment.util.Gl365StrUtils;
import com.gl365.payment.util.IdGenerator;
import com.gl365.payment.util.gson.GsonUtils;
@Service
public class RmWxRewardServiceImpl extends AbstractRmWxRewardBuild implements RmWxRewardService {
	private static final Logger LOG = LoggerFactory.getLogger(RmWxRewardServiceImpl.class);
	@Autowired
	private PayStreamService payStreamService;
	@Autowired
	private Gl365UserService gl365UserService;
	@Autowired
	private Gl365MerchantService gl365MerchantService;
	@Autowired
	public Gl365UserAccountService gl365UserAccountService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public WxRewardRespDTO reward(WxRewardReqDTO req) {
		LOG.info(this.getLog(0, this.initTranType().getDesc()) + "开始");
		LOG.info("请求参数request={}", Gl365StrUtils.toMultiLineStr(req));
		RmWxRewardContext ctx = new RmWxRewardContext();
		ctx.setRequest(req);
		this.generatePayId(ctx);
		this.checkRequestParams(ctx);
		this.savePayStream(ctx);
		this.checkUser(ctx);
		this.checkMerchantInfo(ctx);
		this.checkAccountBalance(ctx);
		this.buildPayData(ctx);
		this.buildPaySettleData(ctx);
		this.savePayData(ctx);
		WxRewardRespDTO resp = this.buildResponseResult(ctx);
		LOG.info(this.getLog(10, this.initTranType().getDesc()) + "结束");
		return resp;
	}

	private void generatePayId(RmWxRewardContext ctx) {
		LOG.info(this.totalStep() + "-1-生成给乐交易单号");
		ctx.setTranType(this.initTranType());
		String payId = IdGenerator.generatePayId(this.initPayCategoryCode());
		ctx.setPayId(payId);
	}

	private void checkRequestParams(RmWxRewardContext ctx) {
		LOG.info(this.totalStep() + "-2-检查请求传入参数");
		WxRewardReqDTO request = ctx.getRequest();
		this.checkRequest(request);
	}

	private void savePayStream(RmWxRewardContext ctx) {
		LOG.info(this.totalStep() + "-3-写流水表");
		PayStream stream = this.buildStream(ctx);
		ctx.setPayStream(stream);
		LOG.debug("写流水表传入值JOSN={}", GsonUtils.toJson(stream));
		this.payStreamService.save(stream);
	}

	private void checkUser(RmWxRewardContext ctx) {
		LOG.info(this.totalStep() + "-4-查用户检查状态");
		String userId = ctx.getRequest().getUserId();
		Gl365User gl365User = this.gl365UserService.queryGl365User(userId);
		this.gl365UserService.checkGl365User(gl365User);
		ctx.setGl365User(gl365User);
	}

	private void checkMerchantInfo(RmWxRewardContext ctx) {
		LOG.info(this.totalStep() + "-5-查询商户检查状态");
		String merchantNo = ctx.getRequest().getMerchantNo();
		String organCode = ctx.getRequest().getOrganCode();
		String scene = ctx.getRequest().getScene();
		String payChannleType = this.getPayChannleType(scene);
		Gl365Merchant gl365Merchant = this.gl365MerchantService.queryGl365Merchant(merchantNo, organCode, payChannleType);
		this.gl365MerchantService.checkGl365Merchant(gl365Merchant);
		ctx.setGl365Merchant(gl365Merchant);
	}

	private void checkAccountBalance(RmWxRewardContext ctx) {
		LOG.info(this.totalStep() + "-6-查用户可乐豆消费余额");
		BigDecimal payLdMoney = ctx.getRequest().getPayLdMoney();
		boolean b = BigDecimalUtil.GreaterThan0(payLdMoney);
		if (!b) return;
		String userId = ctx.getRequest().getUserId();
		QueryAccountBalanceInfoRespDTO result = this.gl365UserAccountService.queryGl365AccountBalance(userId, Agent.GL365.getKey());
		BigDecimal balance = result.getBalance();
		LOG.info("用户乐豆余额={}", balance);
		boolean c = !BigDecimalUtil.GreaterOrEqual(balance, payLdMoney);
		if (c) throw new ServiceException(Msg.PAY_8024.getCode(), Msg.PAY_8024.getDesc());
	}

	private void buildPayData(RmWxRewardContext ctx) {
		LOG.info(this.totalStep() + "-7-计算返利金额-返佣金额-营销费-手续费");
		this.buildRewardPayData(ctx);
	}

	private void buildPaySettleData(RmWxRewardContext ctx) {
		LOG.info(this.totalStep() + "-8-取清分数据");
		this.calculateCurrentPayProfit(ctx);
	}

	private void savePayData(RmWxRewardContext ctx) {
		LOG.info(this.totalStep() + "-9-写交易表");
		LOG.debug("写交易主表传入值JOSN={}", GsonUtils.toJson(ctx.getPayMain()));
		payMainService.save(ctx.getPayMain());
	}

	private WxRewardRespDTO buildResponseResult(RmWxRewardContext ctx) {
		LOG.info(this.totalStep() + "-10-组装返回结果");
		WxRewardRespDTO result = this.buildRewardResp(ctx);
		LOG.info("交易返回结果={}", GsonUtils.toJson(result));
		return result;
	}

	@Override
	public TranType initTranType() {
		return TranType.ONLINE_CONSUME;
	}

	@Override
	public PayStatus initPayStatus() {
		return null;
	}

	@Override
	public String initPayCategoryCode() {
		return PayCategory.PAY_80.getCode();
	}

	@Override
	public int totalStep() {
		return 10;
	}
}
