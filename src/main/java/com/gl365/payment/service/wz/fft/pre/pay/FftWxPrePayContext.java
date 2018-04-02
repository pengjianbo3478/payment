package com.gl365.payment.service.wz.fft.pre.pay;
import com.gl365.payment.dto.wx.request.WxPrePayReqDTO;
import com.gl365.payment.dto.wx.response.WxPrePayRespDTO;
import com.gl365.payment.remote.dto.merchant.Gl365Merchant;
import com.gl365.payment.service.wz.fft.FftWxPayContext;
public class FftWxPrePayContext extends FftWxPayContext {
	private static final long serialVersionUID = 1L;
	private WxPrePayReqDTO request;
	private WxPrePayRespDTO response = new WxPrePayRespDTO();
	private Gl365Merchant groupMerchant;

	public WxPrePayReqDTO getRequest() {
		return request;
	}

	public void setRequest(WxPrePayReqDTO request) {
		this.request = request;
	}

	public WxPrePayRespDTO getResponse() {
		return response;
	}

	public void setResponse(WxPrePayRespDTO response) {
		this.response = response;
	}

	public Gl365Merchant getGroupMerchant() {
		return groupMerchant;
	}

	public void setGroupMerchant(Gl365Merchant groupMerchant) {
		this.groupMerchant = groupMerchant;
	}
}
