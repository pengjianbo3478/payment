package com.gl365.payment.service.mq.product;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.gl365.aliyun.ons.OnsProducer;
@Service
public class PaymentResultProduct {
	private static final Logger LOG = LoggerFactory.getLogger(PaymentResultProduct.class);
	@Lazy
	@Resource(name = "payment-result-monitor-producer")
	private OnsProducer paymentResultProduct;

	public void send(String content) {
		try {
			LOG.info("#####发送1分钟延时交易结果通知内容={}", content);
			if (StringUtils.isEmpty(content)) return;
			LOG.info("#####发送1分钟延时交易结果通知开始");
			this.paymentResultProduct.send(System.currentTimeMillis() + 60000, content);
			LOG.info("#####发送1分钟延时交易结果通知结束");
		}
		catch (Exception e) {
			LOG.error("#####send payment result MQ message error={}", e);
		}
	}
}
