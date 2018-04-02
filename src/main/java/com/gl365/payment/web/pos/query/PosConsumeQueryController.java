package com.gl365.payment.web.pos.query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.gl365.payment.dto.pretranscation.request.PreTranReqDTO;
import com.gl365.payment.dto.pretranscation.response.PreTranRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.service.pos.query.PosConsumeQueryService;
import com.gl365.payment.util.Gl365StrUtils;
import com.gl365.payment.web.AbstractBaseController;
/**
 * POS消费查询
 */
@RestController
public class PosConsumeQueryController extends AbstractBaseController<PreTranReqDTO, PreTranRespDTO> {
	private static final Logger LOG = LoggerFactory.getLogger(PosConsumeQueryController.class);
	@Autowired
	private PosConsumeQueryService posConsumeQueryService;

	@Override
	@PostMapping(value = "posConsumeQuery")
	public PreTranRespDTO service(@RequestBody PreTranReqDTO reqDTO) {
		LOG.debug("request={}", Gl365StrUtils.toStr(reqDTO));
		PreTranRespDTO result = new PreTranRespDTO();
		try {
			result = this.posConsumeQueryService.execute(reqDTO);
			result.setPayStatus(Msg.S000.getCode());
			result.setPayDesc(Msg.S000.getDesc());
		}
		catch (ServiceException e) {
			LOG.error(e.getMessage(), e);
			BeanUtils.copyProperties(reqDTO, result);
			result.setPayStatus(e.getCode());
			result.setPayDesc(e.getDesc());
		}
		catch (Exception e) {
			LOG.error(e.getMessage(), e);
			result.setPayStatus(Msg.F000.getCode());
			result.setPayDesc(Msg.F000.getDesc());
		}
		LOG.debug("response={}", Gl365StrUtils.toStr(result));
		return result;
	}
}
