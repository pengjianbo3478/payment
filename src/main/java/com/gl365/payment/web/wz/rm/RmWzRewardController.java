package com.gl365.payment.web.wz.rm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.gl365.payment.dto.wx.request.WxRewardReqDTO;
import com.gl365.payment.dto.wx.response.WxRewardRespDTO;
import com.gl365.payment.enums.system.ResultCode;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.service.wz.rm.reward.RmWxRewardService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
@RestController
public class RmWzRewardController {
	private static final Logger LOG = LoggerFactory.getLogger(RmWzRewardController.class);
	@Autowired
	private RmWxRewardService rmWxrewardService;

	@HystrixCommand(fallbackMethod = "fallback")
	@PostMapping(value = "wx/reward")
	public WxRewardRespDTO pay(@RequestBody WxRewardReqDTO request) {
		WxRewardRespDTO result = new WxRewardRespDTO();
		try {
			result = this.rmWxrewardService.reward(request);
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

	public WxRewardRespDTO fallback(@RequestBody WxRewardReqDTO request) {
		return null;
	}
}
