package com.gl365.payment.service.mq.consumer.payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gl365.aliyun.ons.OnsListener;
import com.gl365.payment.service.mq.consumer.service.PaymentConsumerService;
@Component("payment-result-monitor-consumer")
public class PaymentResultConsumer implements OnsListener {
	private static final Logger LOG = LoggerFactory.getLogger(PaymentResultConsumer.class);
	@Autowired
	private PaymentConsumerService paymentConsumerService;

	@Override
	public void receive(byte[] data) {
		String message = new String(data);
		LOG.debug("#####接收payment延时通知消息={}", message);
		this.paymentConsumerService.exePaymentResult(message);
	}
}
