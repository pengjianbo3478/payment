package com.gl365.payment.service.web;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.gl365.payment.dto.onlineconsume.response.OnlineConsumeRespDTO;
import com.gl365.payment.dto.onlineconsume.response.OnlineRewardBeanConsumeRespDTO;
import com.gl365.payment.dto.rollback.response.RollbackRespDTO;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.service.base.BaseWebServiceTest;
import com.gl365.payment.util.Gl365StrUtils;
import com.gl365.payment.util.IdGenerator;
import com.gl365.payment.util.gson.GsonUtils;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@MapperScan(basePackages = { "com.gl365.payment.mapper" })
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.gl365.payment.*" })
@EnableFeignClients
@Ignore
public class WebServiceTest extends BaseWebServiceTest {
	private static final Logger LOG = LoggerFactory.getLogger(WebServiceTest.class);

	@Test
	@Ignore
	public void test1() {
		// 网上消费
		OnlineConsumeRespDTO onlineConsumeRespDTO = this.consumeConfirm();
		// 网上消费冲正
		this.webConsumeReverse(onlineConsumeRespDTO);
	}

	@Test
	@Ignore
	public void test2() {
		// 网上消费
		OnlineConsumeRespDTO onlineConsumeRespDTO = this.consumeConfirm();
		// 网上消费撤销
		RollbackRespDTO webConsumeCancel = this.webConsumeCancel(onlineConsumeRespDTO);
		// 网上消费撤销冲更好正
		this.webConsumeCancelReverse(webConsumeCancel, onlineConsumeRespDTO);
	}

	@Test
	@Ignore
	public void test3() {
		// 网上消费
		OnlineConsumeRespDTO onlineConsumeRespDTO = this.consumeConfirm();
		// 退货查询
		this.refundQuery(onlineConsumeRespDTO);
		// 退货
		this.refund(onlineConsumeRespDTO);
	}

	@Test
	@Ignore
	public void test4() {
		// 网上消费
		OnlineConsumeRespDTO onlineConsumeRespDTO = this.consumeConfirm();
		// 打赏
		OnlineConsumeRespDTO result = this.onlineRewardConsume(onlineConsumeRespDTO);
		webConsumeReverse.setRequestId(IdGenerator.getUuId32());
		this.webConsumeReverse.setTotalAmount(result.getTotalMoney());
		this.webConsumeReverse.setOrigRequestId(result.getRequestId());
		RollbackRespDTO res = this.webConsumeReverseService.execute(this.webConsumeReverse);
		LOG.info("######webConsumeReverse result={}", Gl365StrUtils.toMultiLineStr(res));
	}

	@Test
	@Ignore
	public void test5() {
		// 网上消费
		this.consumeConfirm();
		// 乐豆打赏
		OnlineRewardBeanConsumeRespDTO result = new OnlineRewardBeanConsumeRespDTO(PayStatus.COMPLETE_PAY.getCode(), PayStatus.COMPLETE_PAY.getDesc());
		this.onlineRewardBeanConsumeReqDTO.setMerchantOrderNo(this.onlineConsumeReqDTO.getMerchantOrderNo());
		this.onlineRewardBeanConsumeReqDTO.setRewardPayId(this.onlineConsumeReqDTO.getMerchantOrderNo());
		result = this.onlineRewardBeanConsumeService.onlineRewardBeanConsume(onlineRewardBeanConsumeReqDTO, result);
		LOG.info("result={}", GsonUtils.toJson(result));
	}

	@Test
	public void test6() {
		// 参与者消费
		this.consumeConfirmParticipant();
		// 发起者消费
		this.consumeConfirmInitiator();
	}

	@Test
	@Ignore
	public void test7() {
		// 参与者消费
		// this.consumeConfirmParticipant();
		// 发起者消费
		OnlineConsumeRespDTO resp = this.consumeConfirmInitiator();
		// 退货
		this.refund(resp);
	}
}
