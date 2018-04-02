package com.gl365.payment.service.wz.fft.cancel.cancel.impl;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.dto.wx.request.WxCancelReqDTO;
import com.gl365.payment.dto.wx.response.WxCancelRespDTO;
import com.gl365.payment.enums.pay.PayCategory;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.enums.user.AccountResultCode;
import com.gl365.payment.model.PayDetail;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayReturn;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.remote.dto.member.Gl365User;
import com.gl365.payment.remote.dto.merchant.Gl365Merchant;
import com.gl365.payment.service.wz.common.Gl365MerchantService;
import com.gl365.payment.service.wz.common.Gl365UserService;
import com.gl365.payment.service.wz.fft.cancel.cancel.FftWxCancelService;
import com.gl365.payment.service.wz.fft.cancel.cancel.abs.AbstractFftWxCancelBuild;
import com.gl365.payment.service.wz.rm.cancel.cancel.RmWxCancelContext;
import com.gl365.payment.util.IdGenerator;
import com.gl365.payment.util.JsonUtil;
import com.gl365.payment.util.gson.GsonUtils;
@Service
public class FftWxCancelServiceImpl extends AbstractFftWxCancelBuild implements FftWxCancelService {
	private static final Logger LOG = LoggerFactory.getLogger(FftWxCancelServiceImpl.class);
	@Autowired
	private Gl365UserService gl365UserService;
	@Autowired
	private Gl365MerchantService gl365MerchantService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public WxCancelRespDTO cancel(WxCancelReqDTO req) {
		LOG.info(this.getLog(0, this.initTranType().getDesc()) + "开始");
		LOG.info("请求参数={}", JsonUtil.toJson(req));
		RmWxCancelContext ctx = new RmWxCancelContext();
		ctx.setRequest(req);
		this.generatePayId(ctx);
		this.checkRequestParams(ctx);
		this.savePayStream(ctx);
		this.queryOrigPayMain(ctx);
		this.checkUser(ctx);
		this.checkMerchantInfo(ctx);
		this.confirmPreSettleDate(ctx);
		this.saveReturnAndDetail(ctx);
		boolean isCompletePay = PayStatus.COMPLETE_PAY.getCode().equals(ctx.getPayMain().getPayStatus());
		if (isCompletePay) this.updateAccountBalance(ctx);
		this.updateOrigPayMain(ctx);
		this.sendMQ(ctx);
		WxCancelRespDTO buildResp = this.buildResp(ctx);
		LOG.info(this.getLog(10, this.initTranType().getDesc()) + "结束");
		return buildResp;
	}

	private void generatePayId(RmWxCancelContext ctx) {
		LOG.info(this.totalStep() + "-1-生成给乐交易单号");
		ctx.setTranType(TranType.ONLINE_REFUND_ALL);
		String payId = IdGenerator.generatePayId(PayCategory.PAY_85.getCode());
		ctx.setPayId(payId);
	}

	private void checkRequestParams(RmWxCancelContext ctx) {
		LOG.info(this.totalStep() + "-2-检查请求传入参数");
		WxCancelReqDTO request = ctx.getRequest();
		this.checkRequest(request);
	}

	private void savePayStream(RmWxCancelContext ctx) {
		LOG.info(this.totalStep() + "-3-写流水表");
		this.buildStream(ctx);
		PayStream payStream = ctx.getPayStream();
		LOG.debug("写流水表传入值JOSN={}", GsonUtils.toJson(payStream));
		this.payStreamService.save(payStream);
	}

	private void queryOrigPayMain(RmWxCancelContext ctx) {
		LOG.info(this.totalStep() + "-4-查原单");
		this.queryPayMian(ctx);
	}

	private void checkUser(RmWxCancelContext ctx) {
		LOG.info(this.totalStep() + "-5-查用户检查状态");
		String userId = ctx.getPayMain().getUserId();
		Gl365User gl365User = this.gl365UserService.queryGl365UserByNewUserId(userId);
		this.gl365UserService.checkGl365User(gl365User);
		ctx.setGl365User(gl365User);
	}

	private void checkMerchantInfo(RmWxCancelContext ctx) {
		LOG.info(this.totalStep() + "-6-查询商户检查状态");
		String organMerchantNo = ctx.getPayMain().getMerchantNo();
		String organCode = ctx.getRequest().getOrganCode();
		String scene = ctx.getPayMain().getScene();
		String payChannleType = this.getPayChannleType(scene);
		Gl365Merchant gl365Merchant = this.gl365MerchantService.queryGl365Merchant(organMerchantNo, organCode, payChannleType);
		this.gl365MerchantService.checkGl365Merchant(gl365Merchant);
		ctx.setGl365Merchant(gl365Merchant);
	}

	private void confirmPreSettleDate(RmWxCancelContext ctx) {
		LOG.info(this.totalStep() + "-8-获取待清算日期");
		this.getConfirmPreSettleDate(ctx);
	}

	private void saveReturnAndDetail(RmWxCancelContext ctx) {
		LOG.info(this.totalStep() + "-8-写退款表和明细表");
		this.buildPayReturn(ctx);
		boolean isCompletePay = PayStatus.COMPLETE_PAY.getCode().equals(ctx.getPayMain().getPayStatus());
		if (isCompletePay) this.buildPayDetail(ctx);
		LOG.debug("写退款表传入值JOSN={}", GsonUtils.toJson(ctx.getPayReturn()));
		this.insertReturnAndDetail(ctx);
	}

	private void updateAccountBalance(RmWxCancelContext ctx) {
		LOG.info(this.totalStep() + "-9-更新给乐账户");
		this.updateAccount(ctx);
	}

	private void updateOrigPayMain(RmWxCancelContext ctx) {
		LOG.info(this.totalStep() + "-10-更新原单与退货单");
		this.updatePayMain(ctx);
	}

	private void sendMQ(RmWxCancelContext ctx) {
		LOG.info(this.totalStep() + "-11-发送MQ");
		try {
			this.sendSettleMQ(ctx);
		}
		catch (Exception e) {
			LOG.error(this.totalStep() + "-11-发送MQ消息失败");
			LOG.error(e.getMessage(), e);
		}
	}

	private WxCancelRespDTO buildResp(RmWxCancelContext ctx) {
		LOG.info(this.totalStep() + "-12-组装返回结");
		WxCancelRespDTO result = this.buildCancelResp(ctx);
		LOG.info("交易返回结果={}", GsonUtils.toJson(result));
		return result;
	}

	@Override
	public TranType initTranType() {
		return TranType.ONLINE_REFUND_ALL;
	}

	@Override
	public PayStatus initPayStatus() {
		return null;
	}

	@Override
	public String initPayCategoryCode() {
		LOG.debug("初始化给乐交易流水号前缀={}", PayCategory.PAY_85.getCode());
		return PayCategory.PAY_85.getCode();
	}

	@Override
	public int totalStep() {
		return 12;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insertReturnAndDetail(RmWxCancelContext ctx) {
		payReturnService.insert(ctx.getPayReturn());
		List<PayDetail> payDetails = ctx.getPayDetails();
		if (CollectionUtils.isNotEmpty(payDetails)) {
			for (PayDetail pd : payDetails) {
				payDetailService.save(pd);
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePayMain(RmWxCancelContext ctx) {
		PayMain payMain = new PayMain();
		payMain.setPayId(ctx.getPayMain().getPayId());
		PayReturn payReturn = new PayReturn();
		payReturn.setPayId(ctx.getPayReturn().getPayId());
		this.updateStatus(ctx, payMain, payReturn);
	}

	private void updateStatus(RmWxCancelContext ctx, PayMain payMain, PayReturn payReturn) {
		String payStatus = ctx.getPayMain().getPayStatus();
		LOG.info("原单状态为={}", payStatus);
		boolean b = PayStatus.COMPLETE_PAY.getCode().equals(payStatus);
		if (!b) {
			payMain.setPayStatus(PayStatus.PART_CANCEL.getCode());
			payMain.setPayDesc(PayStatus.PART_CANCEL.getDesc());
			payReturn.setPayStatus(PayStatus.PART_CANCEL.getCode());
			payReturn.setPayDesc(PayStatus.PART_CANCEL.getDesc());
		}
		else {
			String resCode = ctx.getAccountResultCode();
			LOG.info("退货扣乐豆返回代码={}", resCode);
			boolean a = AccountResultCode.SUCCESS.getCode().equals(resCode);
			if (a) {
				LOG.info("退货扣豆成功修改原单与退货单状态为={},{}", PayStatus.ALL_RETURN.getCode(), PayStatus.ALL_RETURN.getDesc());
				payMain.setPayStatus(PayStatus.ALL_RETURN.getCode());
				payMain.setPayDesc(PayStatus.ALL_RETURN.getDesc());
				payReturn.setPayStatus(PayStatus.ALL_RETURN.getCode());
				payReturn.setPayDesc(PayStatus.ALL_RETURN.getDesc());
			}
			else {
				LOG.info("退货返回扣豆失败修改原单状态={},{}", PayStatus.ALL_RETURN.getCode(), PayStatus.ALL_RETURN.getDesc());
				payMain.setPayStatus(PayStatus.ALL_RETURN.getCode());
				payMain.setPayDesc(PayStatus.ALL_RETURN.getDesc());
				payReturn.setPayStatus(PayStatus.PART_PAY.getCode());
				payReturn.setPayDesc(PayStatus.PART_PAY.getDesc());
				LOG.info("退货返回扣豆失败修改退货单状态={},{}", PayStatus.PART_PAY.getCode(), PayStatus.PART_PAY.getDesc());
			}
		}
		payMainService.updateStatusByPayId(payMain);
		payReturnService.updateStatus(payReturn);
	}
}
