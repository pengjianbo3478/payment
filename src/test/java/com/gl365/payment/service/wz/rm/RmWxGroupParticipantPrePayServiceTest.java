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
import com.gl365.payment.enums.pay.SplitFlag;
import com.gl365.payment.enums.system.OrganCode;
import com.gl365.payment.service.wz.rm.confirm.RmWxConfirmPayService;
import com.gl365.payment.service.wz.rm.pre.mpay.RmWxPrePayParticipantService;
import com.gl365.payment.util.IdGenerator;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@MapperScan(basePackages = { "com.gl365.payment.mapper" })
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.gl365.payment.*" })
@EnableFeignClients
@Ignore
public class RmWxGroupParticipantPrePayServiceTest {
	@Autowired
	private RmWxPrePayParticipantService rmWxPrePayParticipantService;
	private WxPrePayReqDTO wxPrePayReqDTO = new WxPrePayReqDTO();
	@Autowired
	private RmWxConfirmPayService rmWxConfirmPayService;
	private WxConfirmReqDTO wxConfirmReqDTO = new WxConfirmReqDTO();
	public String MerchantOrderNo = IdGenerator.getUuId32();

	@Before
	public void before() {
		this.wxPrePayReqDTO.setOrganCode(OrganCode.WX.getCode());
		this.wxPrePayReqDTO.setRequestDate(LocalDate.now());
		this.wxPrePayReqDTO.setMerchantNo("1707121000098");
		this.wxPrePayReqDTO.setOrganMerchantNo("100000");
		this.wxPrePayReqDTO.setUserId("52ecaa6c3bb545aea27429a6de3c6d46");
		this.wxPrePayReqDTO.setTotalAmount(new BigDecimal(100));
		this.wxPrePayReqDTO.setScene(Scene.WX_PAY_PUB.getCode());
		this.wxPrePayReqDTO.setMerchantOrderTitle("title");
		this.wxPrePayReqDTO.setMerchantOrderDesc("desc");
		this.wxPrePayReqDTO.setNoBenefitAmount(BigDecimal.ZERO);
		this.wxPrePayReqDTO.setMerchantOrderNo(this.MerchantOrderNo);
		this.wxPrePayReqDTO.setOrderType(OrderType.groupPay.getCode());
		this.wxPrePayReqDTO.setGroupOrderId("879879874691171");
		this.wxPrePayReqDTO.setSplitFlag(SplitFlag.childOrder.getCode());
		this.wxPrePayReqDTO.setGroupMerchantNo("1707121000098");
		
		this.wxConfirmReqDTO.setOrganCode(OrganCode.WX.getCode());
		this.wxConfirmReqDTO.setMerchantOrderNo(this.wxPrePayReqDTO.getMerchantOrderNo());
		this.wxConfirmReqDTO.setOrganOrderNo("00001");
		this.wxConfirmReqDTO.setTransactionId("1234567890");
		this.wxConfirmReqDTO.setPayResult("0");
		this.wxConfirmReqDTO.setOrganPayTime(LocalDateTime.now());
	}

	@Test
	public void test() {
		WxPrePayRespDTO resp = this.rmWxPrePayParticipantService.prePay(wxPrePayReqDTO);
		
		BigDecimal cashAmount = resp.getData().getCashAmount();
		wxConfirmReqDTO.setCashAmount(cashAmount);
		this.rmWxConfirmPayService.confirm(wxConfirmReqDTO);
	}
}