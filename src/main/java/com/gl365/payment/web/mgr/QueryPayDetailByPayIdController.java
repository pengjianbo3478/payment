package com.gl365.payment.web.mgr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.gl365.payment.dto.mgr.reqeust.QueryPayDetailByPayIdReqDTO;
import com.gl365.payment.dto.mgr.response.QueryPayDetailByPayIdRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.service.mgr.MgrService;
import com.gl365.payment.util.Gl365StrUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
@RestController
public class QueryPayDetailByPayIdController {
	private static final Logger LOG = LoggerFactory.getLogger(QueryPayDetailByPayIdController.class);
	@Autowired
	private MgrService mgrService;

	@PostMapping(value = "queryPayDetailByPayId")
	@HystrixCommand(fallbackMethod = "fallback")
	public QueryPayDetailByPayIdRespDTO queryPayDetailByPayId(@RequestBody QueryPayDetailByPayIdReqDTO request) {
		LOG.debug("#####QueryPayDetailByPayIdReqDTO={}", request);
		QueryPayDetailByPayIdRespDTO result = new QueryPayDetailByPayIdRespDTO();
		try {
			result = this.mgrService.queryPayDetailByPayId(request);
		}
		catch (ServiceException e) {
			LOG.error(e.getMessage(), e);
			result.setResultCode(e.getCode());
			result.setResultDesc(e.getDesc());
		}
		catch (Exception e) {
			result.setResultCode(Msg.F000.getCode());
			result.setResultDesc(Msg.F000.getDesc());
		}
		LOG.debug("result={}", Gl365StrUtils.toStr(result));
		return result;
	}
	
	
	public QueryPayDetailByPayIdRespDTO fallback(@RequestBody QueryPayDetailByPayIdReqDTO request) {
		QueryPayDetailByPayIdRespDTO result = new QueryPayDetailByPayIdRespDTO();
		result.setResultCode(Msg.F000.getCode());
		result.setResultDesc(Msg.F000.getDesc());
		return result;
	}
}
