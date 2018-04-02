package com.gl365.payment.web.wz.rm;
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
import com.gl365.payment.service.wz.rm.cancel.cancel.RmWxCancelService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
@RestController
public class RmWzCancelController {
	private static final Logger LOG = LoggerFactory.getLogger(RmWzCancelController.class);
	@Autowired
	private RmWxCancelService rmWxcancelService;

	@HystrixCommand(fallbackMethod = "fallback")
	@PostMapping(value = "wx/cancel")
	public WxCancelRespDTO pay(@RequestBody WxCancelReqDTO request) {
		WxCancelRespDTO result = new WxCancelRespDTO();
		try {
			result = this.rmWxcancelService.cancel(request);
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
