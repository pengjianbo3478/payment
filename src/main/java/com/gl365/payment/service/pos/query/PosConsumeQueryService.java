package com.gl365.payment.service.pos.query;

import com.gl365.payment.dto.pretranscation.request.PreTranReqDTO;
import com.gl365.payment.dto.pretranscation.response.PreTranRespDTO;
import com.gl365.payment.service.transaction.PayService;

public interface PosConsumeQueryService extends PayService<PreTranReqDTO, PreTranRespDTO>{

}
