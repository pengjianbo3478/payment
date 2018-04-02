package com.gl365.payment.service.wz.common;
import java.util.List;
import com.gl365.payment.dto.wx.response.WxPrePayRespDetailDTO;
import com.gl365.payment.remote.dto.settlement.request.ConfirmPreSettleDateReqDTO;
import com.gl365.payment.remote.dto.settlement.request.CurrentPayProfitReqDTO;
import com.gl365.payment.remote.dto.settlement.response.ConfirmPreSettleDateRespDTO;
public interface Gl365SettlementService {
	List<WxPrePayRespDetailDTO> calculateCurrentPayProfit(CurrentPayProfitReqDTO currentPayProfitReqDTO);

	ConfirmPreSettleDateRespDTO getConfirmPreSettleDate(ConfirmPreSettleDateReqDTO confirmPreSettleDateReqDTO);
}
