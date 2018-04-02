package com.gl365.payment.service.wz.fft.pre.mpay;
import com.gl365.payment.dto.wx.request.WxPrePayReqDTO;
import com.gl365.payment.dto.wx.response.WxPrePayRespDTO;
public interface FftWxPrePayInitiatorService {
	/**
	 * 付费通微信支付宝群买单主单支付
	 * @param wxPrePayReqDTO
	 * @return WxPrePayRespDTO
	 */
	WxPrePayRespDTO prePay(WxPrePayReqDTO wxPrePayReqDTO);
}
