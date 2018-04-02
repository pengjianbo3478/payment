package com.gl365.payment.service.transaction;
import com.gl365.payment.dto.base.request.PayReqDTO;
import com.gl365.payment.dto.base.response.PayRespDTO;
import com.gl365.payment.exception.ServiceException;
public interface PayService<Request extends PayReqDTO, Response extends PayRespDTO> {
	Response execute(Request request) throws ServiceException;
}