package com.gl365.payment.remote.service.impl;
import org.springframework.stereotype.Component;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.exception.RemoteServiceException;
import com.gl365.payment.remote.dto.merchant.request.QueryMerchantInfoReqDTO;
import com.gl365.payment.remote.dto.merchant.response.QueryMerchantInfoRespDTO;
import com.gl365.payment.remote.service.MerchantServiceRemote;
@Component
public class MerchantServiceRemoteFallback implements MerchantServiceRemote {
	public QueryMerchantInfoRespDTO queryMerchantInfo(QueryMerchantInfoReqDTO queryMerchantInfoReqDTO) {
		throw new RemoteServiceException(Msg.MERCHANT_ERROR.getCode(), Msg.MERCHANT_ERROR.getDesc());
	}
}
