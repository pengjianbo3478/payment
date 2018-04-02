package com.gl365.payment.service.wz.rm;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.gl365.payment.dto.wx.request.WxConfirmReqDTO;
import com.gl365.payment.dto.wx.request.WxRewardReqDTO;
import com.gl365.payment.dto.wx.response.WxRewardRespDTO;
import com.gl365.payment.enums.pay.Scene;
import com.gl365.payment.enums.system.OrganCode;
import com.gl365.payment.service.wz.rm.reward.RmWxRewardService;
import com.gl365.payment.util.Gl365StrUtils;
import com.gl365.payment.util.IdGenerator;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@MapperScan(basePackages = { "com.gl365.payment.mapper" })
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.gl365.payment.*" })
@EnableFeignClients
@Ignore
public class RmWxRewardServiceTest {
	private static final Logger LOG = LoggerFactory.getLogger(RmWxRewardServiceTest.class);
	@Autowired
	private RmWxRewardService rmWxRewardService;
	private WxRewardReqDTO wxRewardReqDTO = new WxRewardReqDTO();
	private WxConfirmReqDTO wxConfirmReqDTO = new WxConfirmReqDTO();
	public String merchantOrderNo = IdGenerator.getUuId32();

	@Before
	public void before() {
		this.wxRewardReqDTO.setOrganCode(OrganCode.WX.getCode());
		this.wxRewardReqDTO.setRequestDate(LocalDate.now().toString("yyyyMMdd"));
		this.wxRewardReqDTO.setMerchantNo("1707121000098");
		this.wxRewardReqDTO.setOrganMerchantNo("100000");
		this.wxRewardReqDTO.setUserId("52ecaa6c3bb545aea27429a6de3c6d46");
		this.wxRewardReqDTO.setTotalAmount(new BigDecimal(21));
		this.wxRewardReqDTO.setMerchantOrderTitle("title");
		this.wxRewardReqDTO.setMerchantOrderDesc("desc");
		this.wxRewardReqDTO.setNoBenefitAmount(BigDecimal.ZERO);
		this.wxRewardReqDTO.setPayLdMoney(BigDecimal.ONE);
		this.wxRewardReqDTO.setMerchantOrderNo(this.merchantOrderNo);
		this.wxRewardReqDTO.setRewardUserId("3000036");
		this.wxRewardReqDTO.setRewardPayId("80170621174791MS5K");
		this.wxRewardReqDTO.setScene(Scene.WX_PAY_H5.getCode());
		
		this.wxConfirmReqDTO.setOrganCode(OrganCode.WX.getCode());
		this.wxConfirmReqDTO.setMerchantOrderNo(this.wxRewardReqDTO.getMerchantOrderNo());
		this.wxConfirmReqDTO.setOrganOrderNo("00001");
		this.wxConfirmReqDTO.setTransactionId("1234567890");
		this.wxConfirmReqDTO.setPayResult("0");
		this.wxConfirmReqDTO.setOrganPayTime(LocalDateTime.now());
	}

	@Test
	public void test() {
		WxRewardRespDTO resp = this.rmWxRewardService.reward(wxRewardReqDTO);
		LOG.info("result={}", Gl365StrUtils.toMultiLineStr(resp));
	}
}