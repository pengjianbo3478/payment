package com.gl365.payment.service.pos;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.gl365.payment.dto.authconsumeconfirm.response.AuthConsumeConfirmRespDTO;
import com.gl365.payment.dto.consumeconfirm.response.ConsumeConfirmRespDTO;
import com.gl365.payment.dto.pretranscation.response.PreTranRespDTO;
import com.gl365.payment.dto.rollback.response.RollbackRespDTO;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.service.base.BasePosServiceTest;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@MapperScan(basePackages = { "com.gl365.payment.mapper" })
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.gl365.payment.*" })
@EnableFeignClients
public class PosServiceTest extends BasePosServiceTest {
	
	@Test
	@Ignore
	public void test() throws ServiceException {
		// POS-交易查询
		PreTranRespDTO preTranRespDTO = this.consumeQuery();
		// POS-消费确认
		ConsumeConfirmRespDTO consumeConfirmRespDTO = this.consumeConfirm(preTranRespDTO);
		// POS-消费撤销
		RollbackRespDTO consumeCancel = this.consumeCancel(consumeConfirmRespDTO);
		// POS-消费撤销充正
		this.consumeCancelReverse(consumeCancel);
	}

	@Test
	@Ignore
	public void test2() throws ServiceException {
		// POS-交易查询
		PreTranRespDTO preTranRespDTO = this.consumeQuery();
		// POS-消费确认
		ConsumeConfirmRespDTO consumeConfirmRespDTO = this.consumeConfirm(preTranRespDTO);
		// POS-消费冲正
		this.consumeReverse(consumeConfirmRespDTO);
	}

	@Test
	@Ignore
	public void test3() {
		// POS-预交易查询
		PreTranRespDTO preTranRespDTO = this.posPreAuthQuery();
		// POS-预交易确认
		AuthConsumeConfirmRespDTO posPreAuthConsumeConfirm = this.posPreAuthConsumeConfirm(preTranRespDTO);
		// POS-预交易冲正
		this.posPreAuthReverse(posPreAuthConsumeConfirm);
	}
}
