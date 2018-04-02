package com.gl365.payment.service.wz.rm.pre.mpay;
import com.gl365.payment.dto.wx.request.WxPrePayReqDTO;
import com.gl365.payment.dto.wx.response.WxPrePayRespDTO;
public interface RmWxPrePayInitiatorService {
	/**
	 * 融脉微信群买单主单预交易
	 * @param wxPrePayReqDTO
	 * @return
	 */
	WxPrePayRespDTO prePay(WxPrePayReqDTO wxPrePayReqDTO);
}
