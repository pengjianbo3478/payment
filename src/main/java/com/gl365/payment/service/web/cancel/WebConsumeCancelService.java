package com.gl365.payment.service.web.cancel;

import com.gl365.payment.dto.rollback.request.RollbackReqDTO;
import com.gl365.payment.dto.rollback.response.RollbackRespDTO;
import com.gl365.payment.service.transaction.PayService;

public interface WebConsumeCancelService extends PayService<RollbackReqDTO, RollbackRespDTO>  {
}
