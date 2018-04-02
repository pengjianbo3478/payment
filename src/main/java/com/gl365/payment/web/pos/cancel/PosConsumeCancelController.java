package com.gl365.payment.web.pos.cancel;
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
import com.gl365.payment.service.pos.cancel.PosConsumeCancelService;
import com.gl365.payment.util.Gl365StrUtils;
import com.gl365.payment.web.AbstractBaseController;

/**
 * POS消费取消
 */
@RestController
public class PosConsumeCancelController extends AbstractBaseController<RollbackReqDTO, RollbackRespDTO> {
	private static final Logger LOG = LoggerFactory.getLogger(PosConsumeCancelController.class);
	@Autowired
	private PosConsumeCancelService posConsumeCancelService;

	@PostMapping(value = "posConsumeCancel")
	@Override
	public RollbackRespDTO service(@RequestBody RollbackReqDTO reqDTO) {
		LOG.debug("request={}", Gl365StrUtils.toStr(reqDTO));
		RollbackRespDTO result = new RollbackRespDTO();
		try {
			result = this.posConsumeCancelService.execute(reqDTO);
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
