package com.gl365.payment.web.wz.rm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.gl365.payment.dto.wx.request.WxConfirmReqDTO;
import com.gl365.payment.dto.wx.response.WxConfirmRespDTO;
import com.gl365.payment.enums.system.ResultCode;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.service.wz.rm.confirm.RmWxConfirmPayService;
import com.gl365.payment.util.gson.GsonUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
@RestController
public class RmWzConfirmPayController {
	private static final Logger LOG = LoggerFactory.getLogger(RmWzConfirmPayController.class);
	@Autowired
	private RmWxConfirmPayService rmWxconfirmPayService;

	@HystrixCommand(fallbackMethod = "fallback")
	@PostMapping(value = "wx/confirmpay")
	public WxConfirmRespDTO confirm(@RequestBody WxConfirmReqDTO wxConfirmReqDTO) {
		LOG.info("确认支付请求参数={}", GsonUtils.toJson(wxConfirmReqDTO));
		WxConfirmRespDTO result = new WxConfirmRespDTO();
		try {
			result = this.rmWxconfirmPayService.confirm(wxConfirmReqDTO);
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

	public WxConfirmRespDTO fallback(@RequestBody WxConfirmReqDTO wxConfirmReqDTO) {
		return null;
	}
}
