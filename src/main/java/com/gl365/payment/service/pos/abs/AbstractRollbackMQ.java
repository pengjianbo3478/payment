package com.gl365.payment.service.pos.abs;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.gl365.payment.dto.rollback.RollbackContext;
import com.gl365.payment.enums.mq.MsgCategory;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.service.mq.dto.PaymentBody;
import com.gl365.payment.service.mq.dto.PaymentMQ;
import com.gl365.payment.service.mq.dto.PaymentResult;
import com.gl365.payment.service.mq.product.PaymentProduct;
import com.gl365.payment.service.mq.product.PaymentResultProduct;
import com.gl365.payment.util.Gl365StrUtils;
import com.gl365.payment.util.gson.GsonUtils;
public abstract class AbstractRollbackMQ extends AbstractRollback {
	@Autowired
	private PaymentProduct paymentProduct;
	@Autowired
	private PaymentResultProduct paymentResultProduct;
	private static final Logger LOG = LoggerFactory.getLogger(AbstractRollbackMQ.class);

	public void sendMQ(RollbackContext ctx) {
		PaymentBody body = new PaymentBody();
		body.setPayMain(ctx.getPayMain());
		body.setPayStream(ctx.getPayStream());
		body.setPayModifyStatus(ctx.getPayStatus().getCode());
		body.setPayModifyTime(LocalDateTime.now());
		body.setPayDetails(ctx.getPayDetails());
		PaymentMQ res = new PaymentMQ();
		res.setMsgCategory(MsgCategory.normal.getCode());
		res.setTranType(ctx.getPayStream().getTransType());
		res.setPaymentBody(body);
		LOG.info("#####发送MQ消息={}", Gl365StrUtils.toStr(res));
		String content = GsonUtils.toJson(res);
		LOG.info("#####发送MQ消息JSON={}", content);
		this.paymentProduct.send(content);
	}

	public void sendPaymentResultMQ(RollbackContext ctx) {
		PayMain pm = ctx.getPayMain();
		String payId = pm.getPayId();
		String requestId = ctx.getPayStream().getRequestId();
		PaymentResult paymentResult = new PaymentResult();
		paymentResult.setRequestId(requestId);
		paymentResult.setCardIndex(pm.getCardIndex());
		paymentResult.setOrganMerchantNo(pm.getOrganMerchantNo());
		paymentResult.setRequestDate(pm.getRequestDate());
		paymentResult.setTerminal(pm.getTerminal());
		paymentResult.setTotalAmount(pm.getTotalAmount());
		paymentResult.setPayId(payId);
		String content = GsonUtils.toJson(paymentResult);
		this.paymentResultProduct.send(content);
	}
}
