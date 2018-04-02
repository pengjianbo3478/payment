package com.gl365.payment.service.wz.rm.confirm.impl;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.dto.wx.request.WxConfirmReqDTO;
import com.gl365.payment.dto.wx.response.WxConfirmRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.pay.OrderType;
import com.gl365.payment.enums.pay.PayCategory;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.enums.pay.SplitFlag;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.enums.pay.WxPayResult;
import com.gl365.payment.enums.system.GlSystem;
import com.gl365.payment.enums.system.ResultCode;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayDetail;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.remote.dto.account.request.UpdateAccountBalanceOffLineReqDTO;
import com.gl365.payment.remote.dto.account.response.UpdateAccountBalanceOffLineRespDTO;
import com.gl365.payment.remote.dto.settlement.response.ConfirmPreSettleDateRespDTO;
import com.gl365.payment.service.wz.rm.confirm.RmWxConfirmPayService;
import com.gl365.payment.service.wz.rm.confirm.RmWxPayConfirmContext;
import com.gl365.payment.util.BigDecimalUtil;
import com.gl365.payment.util.gson.GsonUtils;
@Service
public class RmWxConfirmPayServiceImpl extends AbstractRmWxConfirmPay implements RmWxConfirmPayService {
	private static final Logger LOG = LoggerFactory.getLogger(RmWxConfirmPayServiceImpl.class);

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public WxConfirmRespDTO confirm(WxConfirmReqDTO request) {
		LOG.info(this.getLog(0, this.initTranType().getDesc()) + "开始");
		RmWxPayConfirmContext ctx = new RmWxPayConfirmContext();
		ctx.setRequest(request);
		this.generatePayId(ctx);
		this.checkRequestParams(ctx);
		boolean checkIsPay = this.checkIsPay(ctx);
		if (checkIsPay) {
			WxConfirmRespDTO res = ctx.getResponse();
			res.setResultCode(ResultCode.SUCCESS.getCode());
			res.setResultDesc(ResultCode.SUCCESS.getDesc());
			return res;
		}
		this.savePayStream(ctx);
		this.queryPayMain(ctx);
		this.checkWxPayResult(ctx);
		this.savePayDetails(ctx);
		this.updateGl365UserAccountBeanBalance(ctx);
		this.confirmPreSettleDate(ctx);
		this.updatePayMainStatus(ctx);
		this.sendMq(ctx);
		this.buildResponseResult(ctx);
		LOG.info(this.getLog(this.totalStep(), this.initTranType().getDesc()) + "结束");
		return ctx.getResponse();
	}

	private boolean checkIsPay(RmWxPayConfirmContext ctx) {
		LOG.info(this.totalStep() + "-0-检查是否已支付 ");
		String merchantOrderNo = ctx.getRequest().getMerchantOrderNo();
		String organCode = ctx.getRequest().getOrganCode();
		PayMain payMain = this.payMainService.queryByParamForUpdate(merchantOrderNo, organCode);
		ctx.setPayMain(payMain);
		if (payMain == null) throw new ServiceException(Msg.PAY_8012.getCode(), Msg.PAY_8012.getDesc());
		String payStatus = payMain.getPayStatus();
		boolean cp = StringUtils.equals(payStatus, PayStatus.COMPLETE_PAY.getCode());
		if (payMain != null && cp) return true;
		else return false;
	}

	private void generatePayId(RmWxPayConfirmContext ctx) {
		LOG.info(this.totalStep() + "-1-生成给乐交易单号");
		String payCategory = this.initPayCategoryCode();
		String payId = this.payContextService.generatePayId(payCategory);
		ctx.getPayStream().setPayId(payId);
	}

	private void checkRequestParams(RmWxPayConfirmContext ctx) {
		LOG.info(this.totalStep() + "-2-检查请求参数");
		this.checkRequest(ctx);
	}

	private void savePayStream(RmWxPayConfirmContext ctx) {
		LOG.info(this.totalStep() + "-3-写交易流水表");
		this.buildPayStream(ctx);
		PayStream payStream = ctx.getPayStream();
		this.payStreamService.save(payStream);
	}

	private void queryPayMain(RmWxPayConfirmContext ctx) {
		LOG.info(this.totalStep() + "-4-查原单并检查");
		PayMain payMain = ctx.getPayMain();
		String payStatus = payMain.getPayStatus();
		boolean b = StringUtils.equals(payStatus, PayStatus.WAIT_PAY.getCode());
		if (!b) throw new ServiceException(Msg.PAY_8004.getCode(), Msg.PAY_8004.getDesc());
		BigDecimal cashAmount = payMain.getCashAmount();
		int c = cashAmount.compareTo(ctx.getRequest().getCashAmount());
		if (c != 0) throw new ServiceException(Msg.PAY_8014.getCode(), Msg.PAY_8014.getDesc());
	}

	private void updateBean(RmWxPayConfirmContext ctx) {
		this.buildUpdateAccountBalanceOffLineReqDTO(ctx);
		UpdateAccountBalanceOffLineReqDTO req = ctx.getUpdateAccBlanceRequest();
		boolean greaterThan0 = BigDecimalUtil.GreaterThan0(req.getOperateAmount()) || BigDecimalUtil.GreaterThan0(req.getGiftAmount());
		if (!greaterThan0) {
			ctx.setPayStatus(PayStatus.COMPLETE_PAY);
			return;
		}
		UpdateAccountBalanceOffLineRespDTO response = this.gl365UserAccountService.UpdateAccountBalanceOffLine(req);
		String resultCode = response.getResultCode();
		boolean b = StringUtils.equals(resultCode, ResultCode.SUCCESS.getCode());
		boolean g = StringUtils.equals(resultCode, ResultCode.BALANCE_UNENOUGH.getCode());
		if (b) ctx.setPayStatus(PayStatus.COMPLETE_PAY);
		if (g) {
			ctx.setPayStatus(PayStatus.PART_PAY);
			this.payDetailService.deleteByPayId(ctx.getPayMain().getPayId());
		}
	}

	private boolean checkWxPayResult(RmWxPayConfirmContext ctx) {
		LOG.info(this.totalStep() + "-5-检查微信支付结果");
		WxConfirmReqDTO request = ctx.getRequest();
		String wxPayResult = request.getPayResult();
		boolean b = StringUtils.equals(wxPayResult, WxPayResult.SUCCESS.getCode());
		if (!b) throw new ServiceException("微信支付结果失败");
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private void savePayDetails(RmWxPayConfirmContext ctx) {
		LOG.info(this.totalStep() + "-6-写付款明细表");
		Boolean isGroupMain = StringUtils.equals(OrderType.groupPay.getCode(), ctx.getPayMain().getOrderType()) && StringUtils.equals(SplitFlag.mainOrder.getCode(), ctx.getPayMain().getSplitFlag());
		if (isGroupMain) this.buildGroupMainPayDetails(ctx);
		else this.buildPayDetails(ctx);
		List<PayDetail> payDetails = ctx.getPayDetails();
		LOG.debug("payDetails={}", GsonUtils.toJson(payDetails));
		for (PayDetail payDetail : payDetails) {
			this.payDetailService.save(payDetail);
		}
	}

	private void updateGl365UserAccountBeanBalance(RmWxPayConfirmContext ctx) {
		LOG.info(this.totalStep() + "-7-扣乐豆");
		boolean dsEq = OrderType.ds.getCode().equals(ctx.getPayMain().getOrderType());
		if (dsEq) {
			this.rewardBeanTransfer(ctx);
			ctx.setPayStatus(PayStatus.COMPLETE_PAY);
		}
		else {
			this.updateBean(ctx);
		}
	}

	private void confirmPreSettleDate(RmWxPayConfirmContext ctx) {
		LOG.info(this.totalStep() + "-8-获取待清算日期");
		this.getConfirmPreSettleDate(ctx);
	}

	private void updatePayMainStatus(RmWxPayConfirmContext ctx) {
		LOG.info(this.totalStep() + "-9-更新原单状态与待清算日期");
		PayMain payMain = ctx.getPayMain();
		payMain.setPayId(ctx.getPayMain().getPayId());
		payMain.setPayStatus(ctx.getPayStatus().getCode());
		payMain.setPayDesc(ctx.getPayStatus().getDesc());
		payMain.setModifyBy(GlSystem.MODIFY_BY_GL_SYSTEM.getCode());
		payMain.setModifyTime(LocalDateTime.now());
		payMain.setPayTime(LocalDateTime.now());
		payMain.setTransactionId(ctx.getRequest().getTransactionId());
		payMain.setOrganPayTime(ctx.getRequest().getOrganPayTime());
		payMain.setOrganOrderNo(ctx.getRequest().getOrganOrderNo());
		ConfirmPreSettleDateRespDTO res = ctx.getConfirmPreSettleDateRespDTO();
		payMain.setPreSettleDate(res.getData().getPreSettleDate());
		this.payMainService.updateStatusByPayId(payMain);
	}

	private void sendMq(RmWxPayConfirmContext ctx) {
		LOG.info(this.totalStep() + "-10-发送MQ消息");
		try {
			this.sendSettleMQ(ctx);
			this.sendPayResultMQ(ctx);
		}
		catch (Exception e) {
			LOG.error(this.totalStep() + "-10-发送MQ消息失败");
			LOG.error(e.getMessage(), e);
		}
	}

	private void buildResponseResult(RmWxPayConfirmContext ctx) {
		LOG.info(this.totalStep() + "-11-组装返回结果");
		this.buildResp(ctx);
		LOG.info("交易返回结果={}", GsonUtils.toJson(ctx.getResponse()));
	}

	public PayStatus initPayStatus() {
		LOG.debug("初始化交易状态={}", PayStatus.COMPLETE_PAY.getDesc());
		return PayStatus.COMPLETE_PAY;
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
	public int totalStep() {
		return 11;
	}
}
