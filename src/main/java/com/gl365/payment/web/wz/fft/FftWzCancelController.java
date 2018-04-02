package com.gl365.payment.web.wz.fft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.gl365.payment.dto.wx.request.WxCancelReqDTO;
import com.gl365.payment.dto.wx.response.WxCancelRespDTO;
import com.gl365.payment.enums.system.ResultCode;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.service.wz.fft.cancel.cancel.FftWxCancelService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
@RestController
public class FftWzCancelController {
	private static final Logger LOG = LoggerFactory.getLogger(FftWzCancelController.class);
	@Autowired
	private FftWxCancelService fftWxCancelService;

	@HystrixCommand(fallbackMethod = "fallback")
	@PostMapping(value = "fft/wx/cancel")
	public WxCancelRespDTO pay(@RequestBody WxCancelReqDTO request) {
		WxCancelRespDTO result = new WxCancelRespDTO();
		try {
			result = this.fftWxCancelService.cancel(request);
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

	public WxCancelRespDTO fallback(@RequestBody WxCancelReqDTO request) {
		return null;
	}
}
