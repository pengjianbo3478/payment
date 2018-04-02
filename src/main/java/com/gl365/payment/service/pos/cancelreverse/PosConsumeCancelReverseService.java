package com.gl365.payment.service.pos.cancelreverse;

import com.gl365.payment.dto.rollback.request.RollbackReqDTO;
import com.gl365.payment.dto.rollback.response.RollbackRespDTO;
import com.gl365.payment.service.transaction.PayService;

public interface PosConsumeCancelReverseService  extends PayService<RollbackReqDTO, RollbackRespDTO> {

}
