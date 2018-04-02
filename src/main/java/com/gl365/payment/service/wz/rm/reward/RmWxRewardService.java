package com.gl365.payment.service.wz.rm.reward;
import com.gl365.payment.dto.wx.request.WxRewardReqDTO;
import com.gl365.payment.dto.wx.response.WxRewardRespDTO;
public interface RmWxRewardService {
	/**
	 * 融脉微信打赏支付
	 * @param req
	 * @return WxRewardRespDTO
	 */
	WxRewardRespDTO reward(WxRewardReqDTO req);
}
