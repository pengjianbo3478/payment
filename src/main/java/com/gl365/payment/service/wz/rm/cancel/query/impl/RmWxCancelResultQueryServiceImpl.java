package com.gl365.payment.service.wz.rm.cancel.query.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.dto.wx.request.WxCancelResultQueryReqDTO;
import com.gl365.payment.dto.wx.response.WxCancelResultQueryRespDTO;
import com.gl365.payment.enums.pay.PayCategory;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.model.PayReturn;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.service.wz.rm.cancel.query.RmWxCancelResultQueryService;
import com.gl365.payment.service.wz.rm.cancel.query.RmWxCancelResultQueryContext;
import com.gl365.payment.util.gson.GsonUtils;
@Service
public class RmWxCancelResultQueryServiceImpl extends AbstractRmWxCancelResultQuery implements RmWxCancelResultQueryService {
	private static final Logger LOG = LoggerFactory.getLogger(RmWxCancelResultQueryServiceImpl.class);

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public WxCancelResultQueryRespDTO cancelResultQuery(WxCancelResultQueryReqDTO request) {
		RmWxCancelResultQueryContext ctx = new RmWxCancelResultQueryContext();
		ctx.setRequest(request);
		this.generatePayId(ctx);
		this.checkRequestParams(ctx);
		this.savePayStream(ctx);
		this.queryPayReturn(ctx);
		this.buildResp(ctx);
		return ctx.getResponse();
	}

	private void generatePayId(RmWxCancelResultQueryContext ctx) {
		LOG.info(this.getLog(0, this.initTranType().getDesc()));
		LOG.info(this.totalStep() + "-1-生成给乐交易单号");
		String payCategory = this.initPayCategoryCode();
		String payId = this.payContextService.generatePayId(payCategory);
		ctx.getPayStream().setPayId(payId);
	}

	private void checkRequestParams(RmWxCancelResultQueryContext ctx) {
		LOG.info(this.totalStep() + "-2-生成给乐交易单号");
		this.checkRequest(ctx);
	}

	private void savePayStream(RmWxCancelResultQueryContext ctx) {
		LOG.info(this.totalStep() + "-3-写流水表");
		this.buildPayStream(ctx);
		PayStream payStream = ctx.getPayStream();
		this.payStreamService.save(payStream);
	}

	private void queryPayReturn(RmWxCancelResultQueryContext ctx) {
		LOG.info(this.totalStep() + "-4-查退货表");
		String merchantOrderNo = ctx.getRequest().getMerchantOrderNo();
		String organCode = ctx.getRequest().getOrganCode();
		PayReturn payReturn = payReturnService.queryByMerchantOrder(organCode, merchantOrderNo);
		ctx.setPayReturn(payReturn);
	}

	private void buildResp(RmWxCancelResultQueryContext ctx) {
		LOG.info(this.totalStep() + "-5-组装返回结果");
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
		return 5;
	}
}
