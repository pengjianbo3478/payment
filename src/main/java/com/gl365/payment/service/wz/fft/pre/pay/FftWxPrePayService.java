package com.gl365.payment.service.wz.fft.pre.pay;
import com.gl365.payment.dto.wx.request.WxPrePayReqDTO;
import com.gl365.payment.dto.wx.response.WxPrePayRespDTO;
public interface FftWxPrePayService {
	/**
	 * 付费通微信支付宝预交易
	 * @param wxPrePayReqDTO
	 * @return WxPrePayRespDTO
	 */
	WxPrePayRespDTO prePay(WxPrePayReqDTO wxPrePayReqDTO);
}
