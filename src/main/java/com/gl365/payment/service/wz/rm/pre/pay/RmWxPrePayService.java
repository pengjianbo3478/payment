package com.gl365.payment.service.wz.rm.pre.pay;
import com.gl365.payment.dto.wx.request.WxPrePayReqDTO;
import com.gl365.payment.dto.wx.response.WxPrePayRespDTO;
public interface RmWxPrePayService {
	/**
	 * 融脉微信预交易
	 * @param wxPrePayReqDTO
	 * @return WxPrePayRespDTO
	 */
	WxPrePayRespDTO prePay(WxPrePayReqDTO wxPrePayReqDTO);
}
