package com.gl365.payment.web.wz.fft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.gl365.payment.dto.wx.request.WxCancelQueryReqDTO;
import com.gl365.payment.dto.wx.response.WxCancelQueryRespDTO;
import com.gl365.payment.enums.system.ResultCode;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.service.wz.fft.cancel.query.FftWxCancelQueryService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
@RestController
public class FftWzCancelQueryController {
	private static final Logger LOG = LoggerFactory.getLogger(FftWzCancelQueryController.class);
	@Autowired
	private FftWxCancelQueryService fftWxCancelQueryService;

	@HystrixCommand(fallbackMethod = "fallback")
	@PostMapping(value = "fft/wx/cancelquery")
	public WxCancelQueryRespDTO pay(@RequestBody WxCancelQueryReqDTO request) {
		WxCancelQueryRespDTO result = new WxCancelQueryRespDTO();
		try {
			result = this.fftWxCancelQueryService.cancelQuery(request);
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

	public WxCancelQueryRespDTO fallback(@RequestBody WxCancelQueryReqDTO request) {
		return null;
	}
}
