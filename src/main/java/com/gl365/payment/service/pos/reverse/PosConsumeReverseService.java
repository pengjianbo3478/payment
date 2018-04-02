package com.gl365.payment.service.pos.reverse;
import com.gl365.payment.dto.rollback.request.RollbackReqDTO;
import com.gl365.payment.dto.rollback.response.RollbackRespDTO;
import com.gl365.payment.service.transaction.PayService;
public interface PosConsumeReverseService extends PayService<RollbackReqDTO, RollbackRespDTO> {

}
