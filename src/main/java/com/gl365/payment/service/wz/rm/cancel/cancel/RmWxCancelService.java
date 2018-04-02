package com.gl365.payment.service.wz.rm.cancel.cancel;
import com.gl365.payment.dto.wx.request.WxCancelReqDTO;
import com.gl365.payment.dto.wx.response.WxCancelRespDTO;
public interface RmWxCancelService {
	/**
	 * 融脉微信退货
	 * @param req
	 * @return WxCancelRespDTO
	 */
	WxCancelRespDTO cancel(WxCancelReqDTO req);
}
