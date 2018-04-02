package com.gl365.payment.web.wz.fft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.gl365.payment.dto.wx.request.WxCancelResultQueryReqDTO;
import com.gl365.payment.dto.wx.response.WxCancelResultQueryRespDTO;
import com.gl365.payment.enums.system.ResultCode;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.service.wz.fft.cancel.query.FftWxCancelResultQueryService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
@RestController
public class FftWzCancelResultQueryController {
	private static final Logger LOG = LoggerFactory.getLogger(FftWzCancelResultQueryController.class);
	@Autowired
	private FftWxCancelResultQueryService fftWxCancelResultQueryService;

	@HystrixCommand(fallbackMethod = "fallback")
	@PostMapping(value = "fft/wx/cancelResultQuery")
	public WxCancelResultQueryRespDTO query(@RequestBody WxCancelResultQueryReqDTO request) {
		WxCancelResultQueryRespDTO result = new WxCancelResultQueryRespDTO();
		try {
			result = this.fftWxCancelResultQueryService.cancelResultQuery(request);
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

	public WxCancelResultQueryRespDTO fallback(@RequestBody WxCancelResultQueryReqDTO request) {
		return null;
	}
}
