package com.gl365.payment.service.web.refund.refund;
import com.gl365.payment.dto.refund.request.RefundReqDTO;
import com.gl365.payment.dto.refund.response.RefundRespDTO;
public interface RefundService {
	RefundRespDTO refund(RefundReqDTO reqDTO, RefundRespDTO respDTO);
}
