package com.gl365.payment.service.web.cancelreverse;

import com.gl365.payment.dto.rollback.request.RollbackReqDTO;
import com.gl365.payment.dto.rollback.response.RollbackRespDTO;
import com.gl365.payment.service.transaction.PayService;

public interface WebConsumeCancelReverseService extends PayService<RollbackReqDTO, RollbackRespDTO>  {

}
