package com.gl365.payment.service.wz.rm;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import com.gl365.payment.dto.wx.request.WxCancelQueryReqDTO;
import com.gl365.payment.dto.wx.request.WxCancelReqDTO;
import com.gl365.payment.dto.wx.request.WxConfirmReqDTO;
import com.gl365.payment.dto.wx.request.WxPrePayReqDTO;
import com.gl365.payment.dto.wx.response.WxPrePayRespDTO;
import com.gl365.payment.enums.pay.OrderType;
import com.gl365.payment.enums.pay.Scene;
import com.gl365.payment.enums.pay.SplitFlag;
import com.gl365.payment.enums.system.OrganCode;
import com.gl365.payment.service.wz.rm.cancel.cancel.RmWxCancelService;
import com.gl365.payment.service.wz.rm.cancel.query.RmWxCancelQueryService;
import com.gl365.payment.service.wz.rm.confirm.RmWxConfirmPayService;
import com.gl365.payment.service.wz.rm.pre.mpay.RmWxPrePayInitiatorService;
import com.gl365.payment.service.wz.rm.pre.pay.RmWxPrePayService;
import com.gl365.payment.util.IdGenerator;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@MapperScan(basePackages = { "com.gl365.payment.mapper" })
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.gl365.payment.*" })
@EnableFeignClients
public class RmWxCancelServiceTest {
	@Autowired
	private RmWxCancelService rmWxCancelService;
	@Autowired
	private RmWxPrePayInitiatorService rmWxPrePayInitiatorService;
	@Autowired
	private RmWxPrePayService rmWxPrePayService;
	@Autowired
	private RmWxConfirmPayService rmWxConfirmPayService;
	@Autowired
	private RmWxCancelQueryService rmWxCancelQueryService;
	private WxCancelQueryReqDTO cancelQueryReqDTO = new WxCancelQueryReqDTO();
	private WxPrePayReqDTO wxPrePayReqDTO = new WxPrePayReqDTO();
	private WxConfirmReqDTO wxConfirmReqDTO = new WxConfirmReqDTO();
	private WxCancelReqDTO wxCancelReqDTO = new WxCancelReqDTO();
	private String merchantOrderNo = IdGenerator.getUuId32();

	@Test
	@Ignore
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
		// 交易确认
		this.wxConfirmReqDTO.setOrganCode(OrganCode.WX.getCode());
		this.wxConfirmReqDTO.setMerchantOrderNo(this.wxPrePayReqDTO.getMerchantOrderNo());
		this.wxConfirmReqDTO.setOrganOrderNo("00001");
		this.wxConfirmReqDTO.setTransactionId("1234567890");
		this.wxConfirmReqDTO.setPayResult("0");
		this.wxConfirmReqDTO.setOrganPayTime(LocalDateTime.now());
		// 退货查询
		this.cancelQueryReqDTO.setOrganCode(OrganCode.WX.getCode());
		this.cancelQueryReqDTO.setMerchantOrderNo(this.wxPrePayReqDTO.getMerchantOrderNo());
		// 退货
		this.wxCancelReqDTO.setOrganCode(OrganCode.WX.getCode());
		this.wxCancelReqDTO.setMerchantOrderNo(IdGenerator.getUuId32());
		this.wxCancelReqDTO.setOrganOrderNo("4124312");
		this.wxCancelReqDTO.setOrigMerchantOrderNo(this.wxPrePayReqDTO.getMerchantOrderNo());
		this.wxCancelReqDTO.setOrigOrganOrderNo(this.wxConfirmReqDTO.getOrganOrderNo());
		this.wxCancelReqDTO.setOrganPayTime(LocalDateTime.now());
		this.wxCancelReqDTO.setTerminal("55555888889999944444");
		this.wxCancelReqDTO.setOperator("test111111");
		WxPrePayRespDTO resp = this.rmWxPrePayService.prePay(wxPrePayReqDTO);
		BigDecimal cashAmount = resp.getData().getCashAmount();
		this.wxConfirmReqDTO.setCashAmount(cashAmount);
		this.rmWxConfirmPayService.confirm(wxConfirmReqDTO);
		this.wxCancelReqDTO.setCashAmount(cashAmount);
		this.rmWxCancelQueryService.cancelQuery(this.cancelQueryReqDTO);
		this.rmWxCancelService.cancel(wxCancelReqDTO);
	}

	@Test
	@Ignore
	public void groupPayCancel() {
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
		// 群支付参数
		this.wxPrePayReqDTO.setOrderType(OrderType.groupPay.getCode());
		this.wxPrePayReqDTO.setGroupOrderId("879879874691170");
		this.wxPrePayReqDTO.setGroupMainuserPay(new BigDecimal(10));
		this.wxPrePayReqDTO.setSplitFlag(SplitFlag.mainOrder.getCode());
		this.wxPrePayReqDTO.setGroupMerchantNo("1000001000530");
		// 交易确认
		this.wxConfirmReqDTO.setOrganCode(OrganCode.WX.getCode());
		this.wxConfirmReqDTO.setMerchantOrderNo(this.wxPrePayReqDTO.getMerchantOrderNo());
		this.wxConfirmReqDTO.setOrganOrderNo("00001");
		this.wxConfirmReqDTO.setTransactionId("1234567890");
		this.wxConfirmReqDTO.setPayResult("0");
		this.wxConfirmReqDTO.setOrganPayTime(LocalDateTime.now());
		// 退货查询
		this.cancelQueryReqDTO.setOrganCode(OrganCode.WX.getCode());
		this.cancelQueryReqDTO.setMerchantOrderNo(this.wxPrePayReqDTO.getMerchantOrderNo());
		// 退货
		this.wxCancelReqDTO.setOrganCode(OrganCode.WX.getCode());
		this.wxCancelReqDTO.setMerchantOrderNo(IdGenerator.getUuId32());
		this.wxCancelReqDTO.setOrganOrderNo("4124312");
		this.wxCancelReqDTO.setOrigMerchantOrderNo(this.wxPrePayReqDTO.getMerchantOrderNo());
		this.wxCancelReqDTO.setOrigOrganOrderNo(this.wxConfirmReqDTO.getOrganOrderNo());
		this.wxCancelReqDTO.setOrganPayTime(LocalDateTime.now());
		WxPrePayRespDTO resp = this.rmWxPrePayInitiatorService.prePay(wxPrePayReqDTO);
		BigDecimal cashAmount = resp.getData().getCashAmount();
		this.wxConfirmReqDTO.setCashAmount(cashAmount);
		this.rmWxConfirmPayService.confirm(wxConfirmReqDTO);
		this.wxCancelReqDTO.setCashAmount(cashAmount);
		this.rmWxCancelQueryService.cancelQuery(this.cancelQueryReqDTO);
		this.rmWxCancelService.cancel(wxCancelReqDTO);
	}
}