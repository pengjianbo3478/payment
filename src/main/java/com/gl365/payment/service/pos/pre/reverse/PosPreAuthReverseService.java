package com.gl365.payment.service.pos.pre.reverse;
import com.gl365.payment.dto.rollback.request.RollbackReqDTO;
import com.gl365.payment.dto.rollback.response.RollbackRespDTO;
import com.gl365.payment.service.transaction.PayService;
public interface PosPreAuthReverseService extends PayService<RollbackReqDTO, RollbackRespDTO> {
	
}
