package com.gl365.payment.service.wz.rm.pre.pay.impl;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.dto.wx.request.WxPrePayReqDTO;
import com.gl365.payment.dto.wx.response.WxPrePayRespDTO;
import com.gl365.payment.enums.pay.OrderType;
import com.gl365.payment.enums.pay.PayCategory;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.enums.system.Agent;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.remote.dto.account.response.QueryAccountBalanceInfoRespDTO;
import com.gl365.payment.remote.dto.member.Gl365User;
import com.gl365.payment.remote.dto.merchant.Gl365Merchant;
import com.gl365.payment.service.wz.rm.pre.pay.RmWxPrePayService;
import com.gl365.payment.service.wz.rm.pre.pay.RmWxPrePayContext;
import com.gl365.payment.util.gson.GsonUtils;
@Service
public class RmWxPrePayServiceImpl extends AbstractRmWxPrePay implements RmWxPrePayService {
	private static final Logger LOG = LoggerFactory.getLogger(RmWxPrePayServiceImpl.class);

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public WxPrePayRespDTO prePay(WxPrePayReqDTO request) {
		LOG.info(this.getLog(0, this.initTranType().getDesc()) + "开始");
		RmWxPrePayContext ctx = new RmWxPrePayContext();
		ctx.setRequest(request);
		this.generatePayId(ctx);
		this.checkRequestParams(ctx);
		this.savePayStream(ctx);
		this.checkUser(ctx);
		this.checkMerchantInfo(ctx);
		this.queryAccountBalanceInfo(ctx);
		this.buildPayData(ctx);
		this.buildPaySettleData(ctx);
		this.savePayData(ctx);
		this.buildResponseResult(ctx);
		LOG.info(this.getLog(10, this.initTranType().getDesc()) + "结束");
		return ctx.getResponse();
	}

	private void generatePayId(RmWxPrePayContext ctx) {
		LOG.info(this.totalStep() + "-1-生成给乐交易单号");
		String payCategory = this.initPayCategoryCode();
		String payId = this.payContextService.generatePayId(payCategory);
		ctx.getPayMain().setPayId(payId);
		ctx.getPayStream().setPayId(payId);
	}

	private void checkRequestParams(RmWxPrePayContext ctx) {
		LOG.info(this.totalStep() + "-2-检查请求传入参数");
		this.checkRequest(ctx);
	}

	private void savePayStream(RmWxPrePayContext ctx) {
		LOG.info(this.totalStep() + "-3-写流水表");
		this.buildPayStream(ctx);
		PayStream payStream = ctx.getPayStream();
		LOG.debug("写流水表传入值JOSN={}", GsonUtils.toJson(payStream));
		this.payStreamService.save(payStream);
	}

	private void checkUser(RmWxPrePayContext ctx) {
		LOG.info(this.totalStep() + "-4-查用户检查状态");
		String userId = ctx.getRequest().getUserId();
		Gl365User gl365User = this.gl365UserService.queryGl365User(userId);
		ctx.setGl365User(gl365User);
		this.gl365UserService.checkGl365User(gl365User);
	}

	private void checkMerchantInfo(RmWxPrePayContext ctx) {
		LOG.info(this.totalStep() + "-5-查询商户检查状态");
		WxPrePayReqDTO wxPrePayReqDTO = ctx.getRequest();
		String organCode = wxPrePayReqDTO.getOrganCode();
		String organMerchantNo = wxPrePayReqDTO.getMerchantNo();
		String scene = ctx.getRequest().getScene();
		String payChannleType = this.getPayChannleType(scene);
		Gl365Merchant gl365Merchant = this.gl365MerchantService.queryGl365Merchant(organMerchantNo, organCode, payChannleType);
		ctx.setGl365Merchant(gl365Merchant);
		this.gl365MerchantService.checkGl365Merchant(gl365Merchant);
	}

	private void queryAccountBalanceInfo(RmWxPrePayContext ctx) {
		LOG.info(this.totalStep() + "-6-查用户可乐豆消费余额");
		String userId = ctx.getRequest().getUserId();
		QueryAccountBalanceInfoRespDTO result = this.gl365UserAccountService.queryGl365AccountBalance(userId, Agent.GL365.getKey());
		BigDecimal balance = result.getBalance();
		LOG.info("用户乐豆余额={}", balance);
		ctx.setGl365UserAccBalance(result);
	}

	private void buildPayData(RmWxPrePayContext ctx) {
		LOG.info(this.totalStep() + "-7-计算返利-返佣-营销费-手续费");
		this.buildPayMain(ctx);
	}

	private void buildPaySettleData(RmWxPrePayContext ctx) {
		LOG.info(this.totalStep() + "-8-获取提成明细数据");
		this.calculateCurrentPayProfit(ctx);
	}

	private void savePayData(RmWxPrePayContext ctx) {
		LOG.info(this.totalStep() + "-9-写交易主表");
		PayMain payMain = ctx.getPayMain();
		LOG.debug("写交易主表传入值JOSN={}", GsonUtils.toJson(payMain));
		this.payMainService.save(payMain);
	}

	private void buildResponseResult(RmWxPrePayContext ctx) {
		LOG.info(this.totalStep() + "-10-组装返回结果");
		this.buildResp(ctx);
		LOG.info("交易返回结果={}", GsonUtils.toJson(ctx.getResponse()));
	}

	public PayStatus initPayStatus() {
		LOG.debug("初始化交易状态={}", PayStatus.WAIT_PAY.getDesc());
		return PayStatus.WAIT_PAY;
	}

	@Override
	public TranType initTranType() {
		return TranType.ONLINE_CONSUME;
	}

	@Override
	public String initPayCategoryCode() {
		LOG.debug("初始化给乐交易流水号前缀={}", PayCategory.PAY_80.getCode());
		return PayCategory.PAY_80.getCode();
	}

	@Override
	public String getOrderType(RmWxPrePayContext ctx) {
		LOG.debug("设置交易订单类型={}", OrderType.pos.getDesc());
		return OrderType.pos.getCode();
	}

	@Override
	public int totalStep() {
		return 10;
	}
}
