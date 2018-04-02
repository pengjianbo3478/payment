package com.gl365.payment.service.wz.fft;
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
import com.gl365.payment.dto.wx.request.WxCancelReqDTO;
import com.gl365.payment.dto.wx.request.WxConfirmReqDTO;
import com.gl365.payment.dto.wx.request.WxPrePayReqDTO;
import com.gl365.payment.dto.wx.response.WxPrePayRespDTO;
import com.gl365.payment.enums.pay.OrderType;
import com.gl365.payment.enums.pay.Scene;
import com.gl365.payment.enums.system.OrganCode;
import com.gl365.payment.service.wz.fft.cancel.cancel.FftWxCancelService;
import com.gl365.payment.service.wz.fft.confirm.FftWxConfirmPayService;
import com.gl365.payment.service.wz.fft.pre.pay.FftWxPrePayService;
import com.gl365.payment.util.IdGenerator;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@MapperScan(basePackages = { "com.gl365.payment.mapper" })
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.gl365.payment.*" })
@EnableFeignClients
public class FftWxCancelServiceTest {
	@Autowired
	private FftWxCancelService fftWxCancelService;
	@Autowired
	private FftWxPrePayService fftWxPrePayService;
	@Autowired
	private FftWxConfirmPayService fftWxConfirmPayService;
	private WxPrePayReqDTO wxPrePayReqDTO = new WxPrePayReqDTO();
	private WxConfirmReqDTO wxConfirmReqDTO = new WxConfirmReqDTO();
	private WxCancelReqDTO wxCancelReqDTO = new WxCancelReqDTO();
	private String merchantOrderNo = IdGenerator.getUuId32();
	
	@Before
	public void normalPayCancel() {
		// 预交易
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
		this.wxPrePayReqDTO.setMerchantOrderNo(this.merchantOrderNo);
		this.wxPrePayReqDTO.setOrderType(OrderType.pos.getCode());
		WxPrePayRespDTO resp = this.fftWxPrePayService.prePay(wxPrePayReqDTO);
		// 交易确认
		this.wxConfirmReqDTO.setOrganCode(OrganCode.WX.getCode());
		this.wxConfirmReqDTO.setMerchantOrderNo(this.wxPrePayReqDTO.getMerchantOrderNo());
		this.wxConfirmReqDTO.setOrganOrderNo("00001");
		this.wxConfirmReqDTO.setTransactionId("1234567890");
		this.wxConfirmReqDTO.setPayResult("0");
		this.wxConfirmReqDTO.setOrganPayTime(LocalDateTime.now());
		BigDecimal cashAmount = resp.getData().getCashAmount();
		this.wxConfirmReqDTO.setCashAmount(cashAmount);
		this.fftWxConfirmPayService.confirm(wxConfirmReqDTO);
		// 退货
		this.wxCancelReqDTO.setOrganCode(OrganCode.WX.getCode());
		this.wxCancelReqDTO.setMerchantOrderNo(IdGenerator.getUuId32());
		this.wxCancelReqDTO.setOrganOrderNo("4124312");
		this.wxCancelReqDTO.setOrigMerchantOrderNo(this.wxPrePayReqDTO.getMerchantOrderNo());
		this.wxCancelReqDTO.setOrigOrganOrderNo(this.wxConfirmReqDTO.getOrganOrderNo());
		this.wxCancelReqDTO.setOrganPayTime(LocalDateTime.now());
		this.wxCancelReqDTO.setTerminal("55555888889999944444");
		this.wxCancelReqDTO.setOperator("test111111");
		this.wxCancelReqDTO.setCashAmount(cashAmount);
	}
	
	
	@Test
	@Ignore
	public void cancel() {
		this.fftWxCancelService.cancel(wxCancelReqDTO);
	}

	
}
