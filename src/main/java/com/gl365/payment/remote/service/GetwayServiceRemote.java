package com.gl365.payment.remote.service;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.gl365.payment.remote.dto.getway.request.QueryOrderReqDTO;
import com.gl365.payment.remote.dto.getway.response.QueryOrderRespDTO;
import com.gl365.payment.remote.service.impl.GetwayServiceRemoteFallback;
@FeignClient(name = "pos-gateway-cloud", fallback = GetwayServiceRemoteFallback.class)
public interface GetwayServiceRemote {
	/**
	 * <p>订单查询</p>
	 * @param queryOrderReqDTO
	 * @return QueryOrderRespDTO
	 */
	@RequestMapping(value = "/tranQuery", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public QueryOrderRespDTO queryOrder(@RequestBody QueryOrderReqDTO queryOrderReqDTO);
}
