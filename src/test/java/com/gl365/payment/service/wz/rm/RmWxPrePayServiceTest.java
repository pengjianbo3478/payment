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
@Ignore
public class RmWxPrePayServiceTest {
	@Autowired
	private RmWxPrePayService rmWxPrePayService;
	private WxPrePayReqDTO wxPrePayReqDTO = new WxPrePayReqDTO();
	@Autowired
	private RmWxConfirmPayService rmWxConfirmPayService;
	private WxConfirmReqDTO request = new WxConfirmReqDTO();

	@Before
	public void before() {
		this.wxPrePayReqDTO.setOrganCode(OrganCode.WX.getCode());
		this.wxPrePayReqDTO.setRequestDate(LocalDate.now());
		this.wxPrePayReqDTO.setMerchantNo("1000001000530");
		this.wxPrePayReqDTO.setOrganMerchantNo("1000001000530");
		this.wxPrePayReqDTO.setUserId("52ecaa6c3bb545aea27429a6de3c6d46");
		this.wxPrePayReqDTO.setTotalAmount(new BigDecimal(20));
		this.wxPrePayReqDTO.setScene(Scene.WX_PAY_H5.getCode());
		this.wxPrePayReqDTO.setMerchantOrderTitle("订单描述测试测试");
		this.wxPrePayReqDTO.setMerchantOrderDesc("订单描述测试");
		this.wxPrePayReqDTO.setNoBenefitAmount(BigDecimal.ZERO);
		String merchantOrderNo = IdGenerator.getUuId32();
		this.wxPrePayReqDTO.setMerchantOrderNo(merchantOrderNo);
		this.wxPrePayReqDTO.setOrderType("1");
		this.request.setOrganCode(OrganCode.WX.getCode());
		this.request.setMerchantOrderNo(merchantOrderNo);
		this.request.setOrganOrderNo(IdGenerator.getUuId32()); 
		this.request.setTransactionId(IdGenerator.getUuId32());
		this.request.setPayResult(WxPayResult.SUCCESS.getCode());
		this.request.setPayDesc(WxPayResult.SUCCESS.getDesc());
		this.request.setDecAmount(new BigDecimal(10));
		this.request.setDecResult(WxPayResult.SUCCESS.getDesc());
		this.request.setBankType("招商银行");
		this.request.setOrganPayTime(LocalDateTime.now());
	}

	@Test
	public void test() {
		WxPrePayRespDTO respDTO = this.rmWxPrePayService.prePay(wxPrePayReqDTO);
		this.request.setCashAmount(respDTO.getData().getCashAmount());
		this.request.setDecAmount(respDTO.getData().getDecAmount());
		this.rmWxConfirmPayService.confirm(request);
	}
}