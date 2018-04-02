package com.gl365.payment.web.mgr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.gl365.payment.dto.mgr.reqeust.QueryPayReturnByPayIdReqDTO;
import com.gl365.payment.dto.mgr.response.QueryPayReturnByPayIdRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.service.mgr.MgrService;
import com.gl365.payment.util.Gl365StrUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
@RestController
public class QueryPayReturnByPayIdController {
	private static final Logger LOG = LoggerFactory.getLogger(QueryPayReturnByPayIdController.class);
	@Autowired
	private MgrService mgrService;

	@PostMapping(value = "queryPayReturnByPayId")
	@HystrixCommand(fallbackMethod = "fallback")
	public QueryPayReturnByPayIdRespDTO queryPayReturnByPayId(@RequestBody QueryPayReturnByPayIdReqDTO request) {
		LOG.debug("#####QueryPayReturnByPayIdReqDTO={}", request);
		QueryPayReturnByPayIdRespDTO response = new QueryPayReturnByPayIdRespDTO();
		try {
			response = this.mgrService.queryPayReturnByPayId(request);
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

	public QueryPayReturnByPayIdRespDTO fallback(QueryPayReturnByPayIdReqDTO request) {
		QueryPayReturnByPayIdRespDTO result = new QueryPayReturnByPayIdRespDTO();
		result.setResultCode(Msg.F000.getCode());
		result.setResultDesc(Msg.F000.getDesc());
		return result;
	}
}
