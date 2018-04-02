package com.gl365.payment.service.mq.consumer.getway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gl365.aliyun.ons.OnsListener;
import com.gl365.payment.service.mq.consumer.service.PaymentConsumerService;
@Component("getway-notify-consumer")
public class GetwayNotifyConsumer implements OnsListener {
	private static final Logger LOG = LoggerFactory.getLogger(GetwayNotifyConsumer.class);
	@Autowired
	private PaymentConsumerService paymentConsumerService;

	@Override
	public void receive(byte[] data) {
		// 接受网关消息，解析获取PyId
		String message = new String(data);
		LOG.debug("#####接受网关推送通知消息={}", message);
		this.paymentConsumerService.exeGetWayPaymentNotify(message);
	}
}