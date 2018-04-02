package com.gl365.payment.remote.service.impl;
import org.springframework.stereotype.Component;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.exception.RemoteServiceException;
import com.gl365.payment.remote.dto.getway.request.QueryOrderReqDTO;
import com.gl365.payment.remote.dto.getway.response.QueryOrderRespDTO;
import com.gl365.payment.remote.service.GetwayServiceRemote;
@Component
public class GetwayServiceRemoteFallback implements GetwayServiceRemote {
	@Override
	public QueryOrderRespDTO queryOrder(QueryOrderReqDTO queryOrderReqDTO) {
		throw new RemoteServiceException(Msg.GETWAY_SERVICE_ERROR.getCode(), Msg.GETWAY_SERVICE_ERROR.getDesc());
	}
}
