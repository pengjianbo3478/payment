package com.gl365.payment.web.web.cancel;
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
import com.gl365.payment.service.web.cancel.WebConsumeCancelService;
import com.gl365.payment.web.AbstractBaseController;
/**
* 网上消费撤销
*/
@RestController
public class WebConsumeCancelController extends AbstractBaseController<RollbackReqDTO, RollbackRespDTO> {
	private static final Logger LOG = LoggerFactory.getLogger(WebConsumeCancelController.class);
	@Autowired
	private WebConsumeCancelService webConsumeCancelService;

	@Override
	@PostMapping(value = "webConsumeCancel")
	public RollbackRespDTO service(@RequestBody RollbackReqDTO reqDTO) {
		RollbackRespDTO result = new RollbackRespDTO();
		try {
			result = this.webConsumeCancelService.execute(reqDTO);
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
