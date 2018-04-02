package com.gl365.payment.service.wz.rm.cancel.query;
import com.gl365.payment.dto.wx.request.WxCancelResultQueryReqDTO;
import com.gl365.payment.dto.wx.response.WxCancelResultQueryRespDTO;
public interface RmWxCancelResultQueryService {
	WxCancelResultQueryRespDTO cancelResultQuery(WxCancelResultQueryReqDTO request);
}
