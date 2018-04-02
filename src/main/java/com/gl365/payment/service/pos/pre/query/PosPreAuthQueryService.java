package com.gl365.payment.service.pos.pre.query;
import com.gl365.payment.dto.pretranscation.request.PreTranReqDTO;
import com.gl365.payment.dto.pretranscation.response.PreTranRespDTO;
import com.gl365.payment.service.transaction.PayService;
public interface PosPreAuthQueryService extends PayService<PreTranReqDTO, PreTranRespDTO> {
}
