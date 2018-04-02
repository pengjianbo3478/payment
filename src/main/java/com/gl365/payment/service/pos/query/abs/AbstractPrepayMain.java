package com.gl365.payment.service.pos.query.abs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.dto.pretranscation.PreTranContext;
import com.gl365.payment.dto.pretranscation.request.PreTranReqDTO;
import com.gl365.payment.dto.pretranscation.response.PreTranRespDTO;
@Service
public abstract class AbstractPrepayMain extends AbstractPrepay {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractPrepayMain.class);

	@Transactional(propagation = Propagation.REQUIRED)
	public PreTranRespDTO query(PreTranReqDTO request) {
		PreTranContext ctx = this.step0(request);
		this.step1(request);
		this.step2(ctx);
		this.step3(request, ctx);
		this.step4(ctx);
		this.step5(ctx);
		this.step6(ctx);
		this.step7(ctx);
		this.step8(ctx);
		PreTranRespDTO result = this.step9(ctx);
		// this.stepEnd(ctx);
		return result;
	}

	private PreTranContext step0(PreTranReqDTO request) {
		LOG.info("#####" + this.getTranName() + "开始");
		LOG.info("#####生成给乐交易单号开始");
		PreTranContext ctx = new PreTranContext(request);
		this.generatePayId(ctx);
		LOG.info("#####生成给乐交易单号结束");
		return ctx;
	}

	private void generatePayId(PreTranContext ctx) {
		PreTranReqDTO request = ctx.getPreTranReqDTO();
		ctx.setMerchantNo(request.getOrganMerchantNo());
		String payCategory = this.initPayCategoryCode();
		String payId = this.payContextService.generatePayId(payCategory);
		ctx.setPayId(payId);
	}

	/*	private void stepEnd(PreTranContext ctx) {
			LOG.info("#####更新流水状态开始");
			String payId = ctx.getPayStream().getPayId();
			String code = DealStatus.SUCCESS.getCode();
			String desc = DealStatus.SUCCESS.getDesc();
			PayStream payStream = new PayStream(payId, code, desc);
			this.payContextService.updatePayStreamStatus(payStream);
			LOG.info("#####更新流水状态结束");
		}*/
	private PreTranRespDTO step9(PreTranContext ctx) {
		LOG.info("#####组装返回结果开始");
		PreTranRespDTO result = this.buildResp(ctx);
		LOG.info("#####组装返回结果结束");
		LOG.info("#####" + this.getTranName() + "结束");
		return result;
	}

	private void step8(PreTranContext ctx) {
		LOG.info("#####写预交易表开始");
		this.savePrepay(ctx.getPayPrepay());
		LOG.info("#####写预交易表结束");
	}

	private void step7(PreTranContext ctx) {
		LOG.info("#####计算返利金额-返佣金额-营销费-手续费开始");
		this.buildPayPrepay(ctx);
		LOG.info("#####计算返利金额-返佣金额-营销费-手续费结束");
	}

	private void step6(PreTranContext ctx) {
		LOG.info("#####查用户可乐豆消费余额开始");
		if (this.isPayBean()) this.queryAccountBalanceInfo(ctx);
		LOG.info("#####查用户可乐豆消费余额结束");
	}

	private void step5(PreTranContext ctx) {
		LOG.info("#####查询商户检查状态开始");
		ctx.setGl365Merchant(this.payContextService.queryMerchantInfo(ctx));
		LOG.info("#####查询商户检查状态结束");
	}

	private void step4(PreTranContext ctx) {
		LOG.info("#####查用户检查状态开始");
		ctx.setGl365User(this.payContextService.queryUserInfo(ctx));
		LOG.info("#####查用户检查状态结束");
	}

	private void step3(PreTranReqDTO request, PreTranContext ctx) {
		LOG.info("#####查询绑卡表得到用户SN开始");
		ctx.setCardIndex(request.getCardIndex());
		ctx.setOrganCode(request.getOrganCode());
		ctx.setGl365UserAccount(this.payContextService.queryAccount(ctx));
		LOG.info("#####查询绑卡表得到用户SN结束");
	}

	private void step2(PreTranContext ctx) {
		LOG.info("#####写流水表开始");
		this.buildPayStream(ctx);
		this.payContextService.savePayStream(ctx);
		LOG.info("#####写流水表结束");
	}

	private void step1(PreTranReqDTO request) {
		LOG.info("#####检查请求传入参数开始");
		this.checkRequestParams(request);
		LOG.info("#####检查请求传入参数结束");
	}
}
