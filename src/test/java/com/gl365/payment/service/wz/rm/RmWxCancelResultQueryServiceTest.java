package com.gl365.payment.service.wz.rm;
import org.junit.Before;
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
import com.gl365.payment.dto.wx.request.WxCancelResultQueryReqDTO;
import com.gl365.payment.enums.system.OrganCode;
import com.gl365.payment.service.wz.rm.cancel.query.RmWxCancelResultQueryService;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@MapperScan(basePackages = { "com.gl365.payment.mapper" })
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.gl365.payment.*" })
@EnableFeignClients
@Ignore
public class RmWxCancelResultQueryServiceTest {
	@Autowired
	private RmWxCancelResultQueryService rmWxCancelResultQueryService;
	private WxCancelResultQueryReqDTO request = new WxCancelResultQueryReqDTO();

	@Before
	public void before() {
		this.request.setOrganCode(OrganCode.WX.getCode());
		this.request.setMerchantOrderNo("CMtOrderNo20170922151303");
	}

	@Test
	public void test() {
		this.rmWxCancelResultQueryService.cancelResultQuery(request);
	}
}
