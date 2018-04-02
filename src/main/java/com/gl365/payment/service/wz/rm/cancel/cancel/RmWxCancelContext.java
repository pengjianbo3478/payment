package com.gl365.payment.service.wz.rm.cancel.cancel;

import java.util.List;
import com.gl365.payment.dto.wx.request.WxCancelReqDTO;
import com.gl365.payment.dto.wx.response.WxCancelRespDTO;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.model.PayDetail;
import com.gl365.payment.model.PayReturn;
import com.gl365.payment.remote.dto.settlement.response.ConfirmPreSettleDateRespDTO;
import com.gl365.payment.service.wz.rm.RmWxPayContext;

public class RmWxCancelContext extends RmWxPayContext {
	private static final long serialVersionUID = 1L;
	private WxCancelReqDTO request;
	private WxCancelRespDTO response;
	private PayReturn payReturn;
	private TranType tranType;
	private String payId;
	private List<PayDetail> payDetails;
	private String AccountResultCode;
	private ConfirmPreSettleDateRespDTO confirmPreSettleDateRespDTO;
	
	public WxCancelReqDTO getRequest() {
		return request;
	}

	public void setRequest(WxCancelReqDTO request) {
		this.request = request;
	}

	public WxCancelRespDTO getResponse() {
		return response;
	}

	public void setResponse(WxCancelRespDTO response) {
		this.response = response;
	}

	public PayReturn getPayReturn() {
		return payReturn;
	}

	public void setPayReturn(PayReturn payReturn) {
		this.payReturn = payReturn;
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

	public String getAccountResultCode() {
		return AccountResultCode;
	}

	public void setAccountResultCode(String accountResultCode) {
		AccountResultCode = accountResultCode;
	}

	public ConfirmPreSettleDateRespDTO getConfirmPreSettleDateRespDTO() {
		return confirmPreSettleDateRespDTO;
	}

	public void setConfirmPreSettleDateRespDTO(ConfirmPreSettleDateRespDTO confirmPreSettleDateRespDTO) {
		this.confirmPreSettleDateRespDTO = confirmPreSettleDateRespDTO;
	}
}
