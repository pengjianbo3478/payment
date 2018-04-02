package com.gl365.payment.web.wz.rm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.gl365.payment.dto.wx.request.WxPrePayReqDTO;
import com.gl365.payment.dto.wx.response.WxPrePayRespDTO;
import com.gl365.payment.enums.pay.OrderType;
import com.gl365.payment.enums.pay.SplitFlag;
import com.gl365.payment.enums.system.ResultCode;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.service.wz.rm.pre.mpay.RmWxPrePayInitiatorService;
import com.gl365.payment.service.wz.rm.pre.mpay.RmWxPrePayParticipantService;
import com.gl365.payment.service.wz.rm.pre.pay.RmWxPrePayService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
@RestController
public class RmWzPrePayController {
	private static final Logger LOG = LoggerFactory.getLogger(RmWzPrePayController.class);
	@Autowired
	private RmWxPrePayService rmWxprePayService;
	@Autowired
	private RmWxPrePayParticipantService rmWxprePayParticipantService;
	@Autowired
	private RmWxPrePayInitiatorService rmWxprePayInitiatorService;

	@HystrixCommand(fallbackMethod = "fallback")
	@PostMapping(value = "wx/prepay")
	public WxPrePayRespDTO pay(@RequestBody WxPrePayReqDTO wxPrePayReqDTO) {
		WxPrePayRespDTO result = new WxPrePayRespDTO();
		try {
			result = this.prePay(wxPrePayReqDTO);
			result.setResultCode(ResultCode.SUCCESS.getCode());
			result.setResultDesc(ResultCode.SUCCESS.getDesc());
		}
		catch (ServiceException e) {
			LOG.error(e.getMessage(), e);
			result.setResultCode(e.getCode());
			result.setResultDesc(e.getDesc());
		}
		catch (Exception e) {
			LOG.error(e.getMessage(), e);
			result.setResultCode(ResultCode.FAIL.getCode());
			result.setResultDesc(ResultCode.FAIL.getDesc());
		}
		return result;
	}

	public WxPrePayRespDTO fallback(@RequestBody WxPrePayReqDTO wxPrePayReqDTO) {
		return null;
	}
	
	private WxPrePayRespDTO prePay(WxPrePayReqDTO reqDTO) {
		String orderType = reqDTO.getOrderType();
		if (OrderType.groupPay.getCode().equals(orderType)) {
			String splitFlag = reqDTO.getSplitFlag();
			if (SplitFlag.mainOrder.getCode().equals(splitFlag)) 
				return rmWxprePayInitiatorService.prePay(reqDTO);
			else 
				return rmWxprePayParticipantService.prePay(reqDTO);
		}  
		
		return this.rmWxprePayService.prePay(reqDTO);
	}
}
