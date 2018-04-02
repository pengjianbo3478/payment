package com.gl365.payment.remote.service;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.gl365.payment.remote.dto.settlement.request.ConfirmPreSettleDateReqDTO;
import com.gl365.payment.remote.dto.settlement.request.CurrentPayProfitReqDTO;
import com.gl365.payment.remote.dto.settlement.response.ConfirmPreSettleDateRespDTO;
import com.gl365.payment.remote.dto.settlement.response.CurrentPayProfitDetail;
import com.gl365.payment.remote.dto.settlement.response.CurrentPayProfitRespDTO;
@FeignClient(name = "settlement")
public interface SettlementServiceRemote {
	@RequestMapping(value = "/currentPayProfit/calculateCurrentPayProfit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	CurrentPayProfitRespDTO<CurrentPayProfitDetail> calculateCurrentPayProfit(@RequestBody CurrentPayProfitReqDTO currentPayProfitReqDTO);

	@RequestMapping(value = "/confirmPreSettleDate/confirm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ConfirmPreSettleDateRespDTO confirmPreSettleDate(@RequestBody ConfirmPreSettleDateReqDTO confirmPreSettleDateReqDTO);
}
