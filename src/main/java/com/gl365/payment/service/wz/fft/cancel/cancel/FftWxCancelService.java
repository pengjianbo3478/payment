package com.gl365.payment.service.wz.fft.cancel.cancel;
import com.gl365.payment.dto.wx.request.WxCancelReqDTO;
import com.gl365.payment.dto.wx.response.WxCancelRespDTO;
public interface FftWxCancelService {
	/**
	 * 付费通微信支付宝退货
	 * @param req
	 * @return WxCancelRespDTO
	 */
	WxCancelRespDTO cancel(WxCancelReqDTO req);
}
