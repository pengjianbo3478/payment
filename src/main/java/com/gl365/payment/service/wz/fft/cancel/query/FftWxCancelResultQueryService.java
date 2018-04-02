package com.gl365.payment.service.wz.fft.cancel.query;
import com.gl365.payment.dto.wx.request.WxCancelResultQueryReqDTO;
import com.gl365.payment.dto.wx.response.WxCancelResultQueryRespDTO;
public interface FftWxCancelResultQueryService {
	/**
	 * 付费通微信支付宝退货结果查询
	 * @param request
	 * @return WxCancelResultQueryRespDTO
	 */
	WxCancelResultQueryRespDTO cancelResultQuery(WxCancelResultQueryReqDTO request);
}
