package com.gl365.payment.web.wz.fft;
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
import com.gl365.payment.service.wz.fft.pre.mpay.FftWxPrePayInitiatorService;
import com.gl365.payment.service.wz.fft.pre.mpay.FftWxPrePayParticipantService;
import com.gl365.payment.service.wz.fft.pre.pay.FftWxPrePayService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
@RestController
public class FftWzPrePayController {
	private static final Logger LOG = LoggerFactory.getLogger(FftWzPrePayController.class);
	@Autowired
	private FftWxPrePayService fftWxPrePayService;
	@Autowired
	private FftWxPrePayParticipantService fftWxPrePayParticipantService;
	@Autowired
	private FftWxPrePayInitiatorService fftWxPrePayInitiatorService;

	@HystrixCommand(fallbackMethod = "fallback")
	@PostMapping(value = "fft/wx/prepay")
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
				return this.fftWxPrePayInitiatorService.prePay(reqDTO);
			else 
				return this.fftWxPrePayParticipantService.prePay(reqDTO);
		}  
		
		return this.fftWxPrePayService.prePay(reqDTO);
	}
}
