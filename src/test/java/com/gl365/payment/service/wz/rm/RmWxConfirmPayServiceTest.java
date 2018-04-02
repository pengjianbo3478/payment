package com.gl365.payment.service.wz.rm;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import com.gl365.payment.dto.wx.request.WxConfirmReqDTO;
import com.gl365.payment.dto.wx.request.WxPrePayReqDTO;
import com.gl365.payment.dto.wx.response.WxPrePayRespDTO;
import com.gl365.payment.enums.pay.OrderType;
import com.gl365.payment.enums.pay.Scene;
import com.gl365.payment.enums.pay.WxPayResult;
import com.gl365.payment.enums.system.OrganCode;
import com.gl365.payment.service.wz.rm.confirm.RmWxConfirmPayService;
import com.gl365.payment.service.wz.rm.pre.pay.RmWxPrePayService;
import com.gl365.payment.util.IdGenerator;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@MapperScan(basePackages = { "com.gl365.payment.mapper" })
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.gl365.payment.*" })
@EnableFeignClients
public class RmWxConfirmPayServiceTest {
	@Autowired
	private RmWxPrePayService rmWxPrePayService;
	private WxPrePayReqDTO wxPrePayReqDTO = new WxPrePayReqDTO();
	@Autowired
	private RmWxConfirmPayService rmWxConfirmPayService;
	private WxConfirmReqDTO wxConfirmReqDTO = new WxConfirmReqDTO();

	@Before
	public void before() {
		this.wxPrePayReqDTO.setOrganCode(OrganCode.WX.getCode());
		this.wxPrePayReqDTO.setRequestDate(LocalDate.now());
		this.wxPrePayReqDTO.setMerchantNo("1707121000098");
		this.wxPrePayReqDTO.setOrganMerchantNo("100000");
		this.wxPrePayReqDTO.setUserId("52ecaa6c3bb545aea27429a6de3c6d46");
		this.wxPrePayReqDTO.setTotalAmount(new BigDecimal(10));
		this.wxPrePayReqDTO.setScene(Scene.WX_PAY_PUB.getCode());
		this.wxPrePayReqDTO.setMerchantOrderTitle("title");
		this.wxPrePayReqDTO.setMerchantOrderDesc("desc");
		this.wxPrePayReqDTO.setNoBenefitAmount(BigDecimal.ZERO);
		this.wxPrePayReqDTO.setTerminal("55555888889999944444");
		this.wxPrePayReqDTO.setOperator("meimei555");
		String merchantOrderNo = IdGenerator.getUuId32();
		this.wxPrePayReqDTO.setMerchantOrderNo(merchantOrderNo);
		this.wxPrePayReqDTO.setOrderType(OrderType.pos.getCode());
		this.wxConfirmReqDTO.setOrganCode(OrganCode.WX.getCode());
		this.wxConfirmReqDTO.setMerchantOrderNo(merchantOrderNo);
		this.wxConfirmReqDTO.setOrganOrderNo(IdGenerator.getUuId32());
		this.wxConfirmReqDTO.setTransactionId(IdGenerator.getUuId32());
		this.wxConfirmReqDTO.setPayResult(WxPayResult.SUCCESS.getCode());
		this.wxConfirmReqDTO.setPayDesc(WxPayResult.SUCCESS.getDesc());
		this.wxConfirmReqDTO.setDecAmount(new BigDecimal(10));
		this.wxConfirmReqDTO.setDecResult(WxPayResult.SUCCESS.getDesc());
		this.wxConfirmReqDTO.setBankType("ICBC");
		this.wxConfirmReqDTO.setOrganPayTime(LocalDateTime.now());
	}

	@Test
	@Ignore
	public void test() {
		WxPrePayRespDTO respDTO = this.rmWxPrePayService.prePay(wxPrePayReqDTO);
		this.wxConfirmReqDTO.setCashAmount(respDTO.getData().getCashAmount());
		this.wxConfirmReqDTO.setDecAmount(respDTO.getData().getDecAmount());
		this.rmWxConfirmPayService.confirm(wxConfirmReqDTO);
	}
}
