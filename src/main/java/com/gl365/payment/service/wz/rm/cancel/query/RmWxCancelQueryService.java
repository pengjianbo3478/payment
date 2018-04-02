package com.gl365.payment.service.wz.rm.cancel.query;
import com.gl365.payment.dto.wx.request.WxCancelQueryReqDTO;
import com.gl365.payment.dto.wx.response.WxCancelQueryRespDTO;
public interface RmWxCancelQueryService {
	WxCancelQueryRespDTO cancelQuery(WxCancelQueryReqDTO request);
}
