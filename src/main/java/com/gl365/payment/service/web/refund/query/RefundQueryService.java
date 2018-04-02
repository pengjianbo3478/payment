package com.gl365.payment.service.web.refund.query;
import com.gl365.payment.dto.refund.query.request.RefundQueryReqDTO;
import com.gl365.payment.dto.refund.query.response.RefundQueryRespDTO;
public interface RefundQueryService {
	RefundQueryRespDTO refundQuery(RefundQueryReqDTO reqDTO, RefundQueryRespDTO respDTO);
}
