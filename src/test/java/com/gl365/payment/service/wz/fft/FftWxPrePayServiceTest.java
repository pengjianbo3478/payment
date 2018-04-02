package com.gl365.payment.service.wz.fft;
import java.math.BigDecimal;
import java.time.LocalDate;
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
import com.gl365.payment.dto.wx.request.WxPrePayReqDTO;
import com.gl365.payment.enums.pay.Scene;
import com.gl365.payment.enums.system.OrganCode;
import com.gl365.payment.service.wz.fft.pre.pay.FftWxPrePayService;
import com.gl365.payment.util.IdGenerator;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@MapperScan(basePackages = { "com.gl365.payment.mapper" })
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.gl365.payment.*" })
@EnableFeignClients

public class FftWxPrePayServiceTest {
	@Autowired
	private FftWxPrePayService fftWxPrePayService;
	private WxPrePayReqDTO wxPrePayReqDTO = new WxPrePayReqDTO();

	@Before
	public void before() {
		this.wxPrePayReqDTO.setOrganCode(OrganCode.FFT.getCode());
		this.wxPrePayReqDTO.setRequestDate(LocalDate.now());
		this.wxPrePayReqDTO.setMerchantNo("1708121000112");
		this.wxPrePayReqDTO.setOrganMerchantNo("1708121000112");
		this.wxPrePayReqDTO.setUserId("52ecaa6c3bb545aea27429a6de3c6d46");
		this.wxPrePayReqDTO.setTotalAmount(new BigDecimal(20));
		this.wxPrePayReqDTO.setScene(Scene.WX_PAY_H5.getCode());
		this.wxPrePayReqDTO.setMerchantOrderTitle("title");
		this.wxPrePayReqDTO.setMerchantOrderDesc("desc");
		this.wxPrePayReqDTO.setNoBenefitAmount(BigDecimal.ZERO);
		String merchantOrderNo = IdGenerator.getUuId32();
		this.wxPrePayReqDTO.setMerchantOrderNo(merchantOrderNo);
		this.wxPrePayReqDTO.setOrderType("1");
	}

	@Test
	public void test() {
		this.fftWxPrePayService.prePay(wxPrePayReqDTO);
	}
}
