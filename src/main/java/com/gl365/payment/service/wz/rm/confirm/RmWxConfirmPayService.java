package com.gl365.payment.service.wz.rm.confirm;
import com.gl365.payment.dto.wx.request.WxConfirmReqDTO;
import com.gl365.payment.dto.wx.response.WxConfirmRespDTO;
public interface RmWxConfirmPayService {
	/**
	 * 融脉微信交易确认
	 * @param request
	 * @return WxConfirmRespDTO
	 */
	WxConfirmRespDTO confirm(WxConfirmReqDTO request);
}
