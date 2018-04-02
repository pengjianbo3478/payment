package com.gl365.payment.service.wz.fft.confirm;
import com.gl365.payment.dto.wx.request.WxConfirmReqDTO;
import com.gl365.payment.dto.wx.response.WxConfirmRespDTO;
public interface FftWxConfirmPayService {
	
	/**
	 * 付费通微信支付宝交易确认
	 * @param request
	 * @return WxConfirmRespDTO
	 */
	WxConfirmRespDTO confirm(WxConfirmReqDTO request);
}
