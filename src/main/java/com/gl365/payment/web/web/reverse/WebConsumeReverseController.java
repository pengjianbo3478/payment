package com.gl365.payment.web.web.reverse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.gl365.payment.dto.rollback.request.RollbackReqDTO;
import com.gl365.payment.dto.rollback.response.RollbackRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.service.web.reverse.WebConsumeReverseService;
import com.gl365.payment.web.AbstractBaseController;
/**
 * 网上消费冲正
 */
@RestController
public class WebConsumeReverseController extends AbstractBaseController<RollbackReqDTO, RollbackRespDTO> {
	private static final Logger LOG = LoggerFactory.getLogger(WebConsumeReverseController.class);
	@Autowired
	private WebConsumeReverseService webConsumeReverseService;

	@Override
	@PostMapping(value = "webConsumeReverse")
	public RollbackRespDTO service(@RequestBody RollbackReqDTO reqDTO) {
		RollbackRespDTO result = new RollbackRespDTO();
		try {
			result = this.webConsumeReverseService.execute(reqDTO);
			result.setPayStatus(Msg.S000.getCode());
			result.setPayDesc(Msg.S000.getDesc());
		}
		catch (ServiceException e) {
			LOG.error(e.getMessage(), e);
			result.setPayStatus(e.getCode());
			result.setPayDesc(e.getDesc());
		}
		catch (Exception e) {
			LOG.error(e.getMessage(), e);
			result.setPayStatus(Msg.F000.getCode());
			result.setPayDesc(Msg.F000.getDesc());
		}
		return result;
	}
}
