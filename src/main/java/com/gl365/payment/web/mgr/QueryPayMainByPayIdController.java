package com.gl365.payment.web.mgr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.gl365.payment.dto.mgr.reqeust.QueryPayMainByPayIdReqDTO;
import com.gl365.payment.dto.mgr.response.QueryPayMainByPayIdRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.service.mgr.MgrService;
import com.gl365.payment.util.Gl365StrUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
@RestController
public class QueryPayMainByPayIdController {
	private static final Logger LOG = LoggerFactory.getLogger(QueryPayMainByPayIdController.class);
	@Autowired
	private MgrService mgrService;

	@PostMapping(value = "queryPayMainByPayId")
	@HystrixCommand(fallbackMethod = "fallback")
	public QueryPayMainByPayIdRespDTO queryPayMainByPayId(@RequestBody QueryPayMainByPayIdReqDTO request) {
		LOG.debug("#####QueryPayMainByPayIdReqDTO={}", request);
		QueryPayMainByPayIdRespDTO response = new QueryPayMainByPayIdRespDTO();
		try {
			response = this.mgrService.queryPayMainByPayId(request);
			response.setResultCode(Msg.S000.getCode());
			response.setResultDesc(Msg.S000.getDesc());
		}
		catch (ServiceException e) {
			LOG.error(e.getMessage(), e);
			response.setResultCode(e.getCode());
			response.setResultDesc(e.getDesc());
		}
		catch (Exception e) {
			response.setResultCode(Msg.F000.getCode());
			response.setResultDesc(Msg.F000.getDesc());
		}
		LOG.debug("response={}", Gl365StrUtils.toStr(response));
		return response;
	}

	public QueryPayMainByPayIdRespDTO fallback(QueryPayMainByPayIdReqDTO request) {
		QueryPayMainByPayIdRespDTO result = new QueryPayMainByPayIdRespDTO();
		result.setResultCode(Msg.F000.getCode());
		result.setResultDesc(Msg.F000.getDesc());
		return result;
	}
}
