package com.gl365.payment.web.wz.rm;
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
import com.gl365.payment.service.wz.rm.cancel.query.RmWxCancelQueryService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
@RestController
public class RmWzCancelQueryController {
	private static final Logger LOG = LoggerFactory.getLogger(RmWzCancelQueryController.class);
	@Autowired
	private RmWxCancelQueryService rmWxcancelQueryService;

	@HystrixCommand(fallbackMethod = "fallback")
	@PostMapping(value = "wx/cancelquery")
	public WxCancelQueryRespDTO pay(@RequestBody WxCancelQueryReqDTO request) {
		WxCancelQueryRespDTO result = new WxCancelQueryRespDTO();
		try {
			result = this.rmWxcancelQueryService.cancelQuery(request);
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
