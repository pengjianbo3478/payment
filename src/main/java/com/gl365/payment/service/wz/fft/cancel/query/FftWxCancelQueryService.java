package com.gl365.payment.service.wz.fft.cancel.query;
import com.gl365.payment.dto.wx.request.WxCancelQueryReqDTO;
import com.gl365.payment.dto.wx.response.WxCancelQueryRespDTO;
public interface FftWxCancelQueryService {
	WxCancelQueryRespDTO cancelQuery(WxCancelQueryReqDTO request);
}
