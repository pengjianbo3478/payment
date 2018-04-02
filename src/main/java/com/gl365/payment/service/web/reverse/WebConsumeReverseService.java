package com.gl365.payment.service.web.reverse;

import com.gl365.payment.dto.rollback.request.RollbackReqDTO;
import com.gl365.payment.dto.rollback.response.RollbackRespDTO;
import com.gl365.payment.service.transaction.PayService;

public interface WebConsumeReverseService  extends PayService<RollbackReqDTO, RollbackRespDTO> {
}
