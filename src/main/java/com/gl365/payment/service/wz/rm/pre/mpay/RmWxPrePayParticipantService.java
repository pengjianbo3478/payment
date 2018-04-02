package com.gl365.payment.service.wz.rm.pre.mpay;
import com.gl365.payment.dto.wx.request.WxPrePayReqDTO;
import com.gl365.payment.dto.wx.response.WxPrePayRespDTO;
public interface RmWxPrePayParticipantService {
	/**
	 * 融脉微信群买单子单预交易
	 * @param wxPrePayReqDTO
	 * @return WxPrePayRespDTO
	 */
	WxPrePayRespDTO prePay(WxPrePayReqDTO wxPrePayReqDTO);
}
