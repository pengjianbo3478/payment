package com.gl365.payment.service.wz.fft.reward;

import com.gl365.payment.dto.wx.request.WxRewardReqDTO;
import com.gl365.payment.dto.wx.response.WxRewardRespDTO;

public interface FftWxRewardService {
	
	/**
	 * 付费通微信支付宝打赏支付
	 * @param req
	 * @return WxRewardRespDTO
	 */
	WxRewardRespDTO reward(WxRewardReqDTO req);
}
