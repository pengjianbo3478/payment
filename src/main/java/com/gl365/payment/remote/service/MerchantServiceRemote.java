package com.gl365.payment.remote.service;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.gl365.payment.remote.dto.merchant.request.QueryMerchantInfoReqDTO;
import com.gl365.payment.remote.dto.merchant.response.QueryMerchantInfoRespDTO;
import com.gl365.payment.remote.service.impl.MerchantServiceRemoteFallback;
@FeignClient(name = "merchant", fallback = MerchantServiceRemoteFallback.class)
public interface MerchantServiceRemote {
	@RequestMapping(value = "/merchant/query/pay/info", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	QueryMerchantInfoRespDTO queryMerchantInfo(@RequestBody QueryMerchantInfoReqDTO queryMerchantInfoReqDTO);
}
