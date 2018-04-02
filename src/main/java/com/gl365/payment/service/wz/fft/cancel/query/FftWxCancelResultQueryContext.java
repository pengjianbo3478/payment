package com.gl365.payment.service.wz.fft.cancel.query;
import com.gl365.payment.dto.wx.request.WxCancelResultQueryReqDTO;
import com.gl365.payment.dto.wx.response.WxCancelResultQueryRespDTO;
import com.gl365.payment.model.PayReturn;
import com.gl365.payment.service.wz.fft.FftWxPayContext;
public class FftWxCancelResultQueryContext extends FftWxPayContext {
	private static final long serialVersionUID = 1L;
	private WxCancelResultQueryReqDTO request;
	private WxCancelResultQueryRespDTO response = new WxCancelResultQueryRespDTO();
	private PayReturn payReturn;

	public WxCancelResultQueryReqDTO getRequest() {
		return request;
	}

	public void setRequest(WxCancelResultQueryReqDTO request) {
		this.request = request;
	}

	public WxCancelResultQueryRespDTO getResponse() {
		return response;
	}

	public void setResponse(WxCancelResultQueryRespDTO response) {
		this.response = response;
	}

	public PayReturn getPayReturn() {
		return payReturn;
	}

	public void setPayReturn(PayReturn payReturn) {
		this.payReturn = payReturn;
	}
}
