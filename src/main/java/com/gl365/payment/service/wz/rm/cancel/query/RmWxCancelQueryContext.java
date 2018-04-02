package com.gl365.payment.service.wz.rm.cancel.query;
import com.gl365.payment.dto.wx.request.WxCancelQueryReqDTO;
import com.gl365.payment.dto.wx.response.WxCancelQueryRespDTO;
import com.gl365.payment.remote.dto.account.response.QueryAccountBalanceInfoRespDTO;
import com.gl365.payment.service.wz.rm.RmWxPayContext;
public class RmWxCancelQueryContext extends RmWxPayContext {
	private static final long serialVersionUID = 1L;
	private WxCancelQueryReqDTO request;
	private WxCancelQueryRespDTO response = new WxCancelQueryRespDTO();
	private QueryAccountBalanceInfoRespDTO accDto;

	public WxCancelQueryReqDTO getRequest() {
		return request;
	}

	public void setRequest(WxCancelQueryReqDTO request) {
		this.request = request;
	}

	public WxCancelQueryRespDTO getResponse() {
		return response;
	}

	public void setResponse(WxCancelQueryRespDTO response) {
		this.response = response;
	}

	public QueryAccountBalanceInfoRespDTO getAccDto() {
		return accDto;
	}

	public void setAccDto(QueryAccountBalanceInfoRespDTO accDto) {
		this.accDto = accDto;
	}
}
