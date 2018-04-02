package com.gl365.payment.service.wz.fft.reward;

import java.math.BigDecimal;
import java.util.List;
import com.gl365.payment.dto.wx.request.WxRewardReqDTO;
import com.gl365.payment.dto.wx.response.WxPrePayRespDetailDTO;
import com.gl365.payment.dto.wx.response.WxRewardRespDTO;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.model.PayDetail;
import com.gl365.payment.service.wz.fft.FftWxPayContext;

public class FftWxRewardContext extends FftWxPayContext {
	private static final long serialVersionUID = 1L;
	private WxRewardReqDTO request;
	private WxRewardRespDTO response;
	private TranType tranType;
	private String payId;
	private List<PayDetail> payDetails;
	private List<WxPrePayRespDetailDTO> respDataDetails;
	private BigDecimal decAmount;

	public WxRewardReqDTO getRequest() {
		return request;
	}

	public void setRequest(WxRewardReqDTO request) {
		this.request = request;
	}

	public WxRewardRespDTO getResponse() {
		return response;
	}

	public void setResponse(WxRewardRespDTO response) {
		this.response = response;
	}

	public TranType getTranType() {
		return tranType;
	}

	public void setTranType(TranType tranType) {
		this.tranType = tranType;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public List<PayDetail> getPayDetails() {
		return payDetails;
	}

	public void setPayDetails(List<PayDetail> payDetails) {
		this.payDetails = payDetails;
	}

	public List<WxPrePayRespDetailDTO> getRespDataDetails() {
		return respDataDetails;
	}

	public void setRespDataDetails(List<WxPrePayRespDetailDTO> respDataDetails) {
		this.respDataDetails = respDataDetails;
	}

	public BigDecimal getDecAmount() {
		return decAmount;
	}

	public void setDecAmount(BigDecimal decAmount) {
		this.decAmount = decAmount;
	}
}
