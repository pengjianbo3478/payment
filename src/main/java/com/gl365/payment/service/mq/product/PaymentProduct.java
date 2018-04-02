package com.gl365.payment.service.mq.product;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.gl365.aliyun.ons.OnsProducer;
@Service
public class PaymentProduct {
	private static final Logger LOG = LoggerFactory.getLogger(PaymentProduct.class);
	@Lazy
	@Resource(name = "payment-notify-producer")
	private OnsProducer paymentProducter;

	public void send(String content) {
		try {
			LOG.debug("#####发送MQ消息内容={}", content);
			if (StringUtils.isEmpty(content)) return;
			LOG.info("#####发送MQ消息开始");
			this.paymentProducter.send(content);
			LOG.info("#####发送MQ消息完成");
		}
		catch (Exception e) {
			LOG.error("send MQ message error, causeBy: ", e);
		}
	}
}
