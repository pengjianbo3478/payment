package com.gl365.payment.service.pos.abs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.dto.rollback.RollbackContext;
import com.gl365.payment.dto.rollback.request.RollbackReqDTO;
import com.gl365.payment.dto.rollback.response.RollbackRespDTO;
public abstract class AbstractRollbackMain extends AbstractRollbackResp {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractRollbackMain.class);

	@Transactional(propagation = Propagation.REQUIRED)
	public RollbackRespDTO exeRollback(RollbackReqDTO request) {
		RollbackContext ctx = this.step0(request);
		this.step1(request);
		this.step2(ctx);
		this.step3(ctx);
		this.setp4(ctx);
		this.step5(ctx);
		this.step6(ctx);
		this.step7(ctx);
		this.step8(ctx);
		this.step9(ctx);
		RollbackRespDTO result = this.step10(ctx);
		//this.stepEnd(ctx);
		return result;
	}

	private RollbackContext step0(RollbackReqDTO request) {
		LOG.info("#####" + this.getTranName() + "开始");
		LOG.info("#####生成给乐交易单号开始");
		RollbackContext ctx = new RollbackContext(request);
		this.generatePayId(ctx);
		LOG.info("#####生成给乐交易单号结束");
		return ctx;
	}

	private void generatePayId(RollbackContext ctx) {
		String payCategory = this.initPayCategoryCode();
		String payId = this.payContextService.generatePayId(payCategory);
		ctx.setPayId(payId);
	}

	/*	private void stepEnd(RollbackContext ctx) {
			LOG.info("#####更新流水状态开始");
			String payId = ctx.getPayStream().getPayId();
			String code = DealStatus.SUCCESS.getCode();
			String desc = DealStatus.SUCCESS.getDesc();
			PayStream payStream = new PayStream(payId, code, desc);
			this.payContextService.updatePayStreamStatus(payStream);
			LOG.info("#####更新流水状态结束");
		}*/

	private RollbackRespDTO step10(RollbackContext ctx) {
		LOG.info("#####组装返回结果开始");
		RollbackRespDTO result = this.getResp(ctx);
		LOG.info("#####组装返回结果结束");
		LOG.info("#####" + this.getTranName() + "结束");
		return result;
	}

	private void step9(RollbackContext ctx) {
		try {
			LOG.info("#####组装MQ开始");
			this.sendMQ(ctx);
			LOG.info("#####组装MQ结束");
			LOG.info("#####发送MQ开始");
			if (this.isSendResultMQ()) this.sendPaymentResultMQ(ctx);
			LOG.info("#####发送MQ结束");
		}
		catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	private void step8(RollbackContext ctx) {
		LOG.info("#####更新原单状态开始");
		this.updatePayMainStatus(ctx);
		LOG.info("#####更新原单状态结束");
	}

	private void step7(RollbackContext ctx) {
		LOG.info("#####调帐户系统的接口-余额交易(线下)扣乐豆-返利处理开始");
		if (this.isUpdateAccountBalance(ctx)) this.updateAccountBalance(ctx);
		LOG.info("#####调帐户系统的接口-余额交易(线下)扣乐豆-返利处理结束");
	}

	private void step6(RollbackContext ctx) {
		LOG.info("#####写付款表开始");
		this.buildPayDetails(ctx);
		this.payContextService.savePayDetails(ctx);
		LOG.info("#####写付款表结束");
	}

	private void step5(RollbackContext ctx) {
		LOG.info("#####检查原单交易时间开始");
		this.checkRollbackService.checkPaymentDate(ctx);
		LOG.info("#####检查原单交易时间结束");
	}

	private void setp4(RollbackContext ctx) {
		LOG.info("#####检查原单是否存在开始");
		this.checkPayMian(ctx);
		LOG.info("#####检查原单是否存在结束");
	}

	private void step3(RollbackContext ctx) {
		LOG.info("#####查原单开始");
		this.queryPayMian(ctx);
		LOG.info("#####查原单结束");
	}

	private void step2(RollbackContext ctx) {
		LOG.info("#####写交易流水开始");
		this.buildPayStream(ctx);
		this.payContextService.savePayStream(ctx);
		LOG.info("#####写交易流水结束");
	}

	private void step1(RollbackReqDTO request) {
		LOG.info("#####检查请求传入参数开始");
		this.checkRequestParams(request);
		LOG.info("#####检查请求传入参数结束");
	}
}
