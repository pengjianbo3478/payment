package com.gl365.payment.mq;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.gl365.payment.enums.getway.GetWayNotifyType;
import com.gl365.payment.remote.dto.getway.response.DealNotifyRespDTO;
import com.gl365.payment.service.mq.consumer.service.PaymentConsumerService;
import com.gl365.payment.service.mq.dto.PaymentResult;
import com.gl365.payment.util.gson.GsonUtils;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@MapperScan(basePackages = { "com.gl365.payment.mapper" })
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.gl365.payment.*" })
@EnableFeignClients
@Ignore
public class PaymentConsumerServiceTest {
	@Autowired
	private PaymentConsumerService paymentConsumerService;

	@Test
	@Ignore
	public void testExePaymentResult() {
		String payId = "901707131804852QZN";
		PaymentResult paymentResult = new PaymentResult();
		paymentResult.setPayId(payId);
		String content = GsonUtils.toJson(paymentResult);
		this.paymentConsumerService.exePaymentResult(content);
	}

	@Test
	public void testExeGetWayPaymentNotify() {
		DealNotifyRespDTO dto = new DealNotifyRespDTO();
		dto.setOrigPayId("238e4ce1cdff418cbcea70a351c424b6");
		dto.setMerchantCode("805024000000203");
		dto.setInterfaceType(GetWayNotifyType.normal.getKey());
		String content = GsonUtils.toJson(dto);
		this.paymentConsumerService.exeGetWayPaymentNotify(content);
	}
}
