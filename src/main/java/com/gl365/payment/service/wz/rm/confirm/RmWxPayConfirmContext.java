package com.gl365.payment.service.wz.rm.confirm;
import java.util.ArrayList;
import java.util.List;
import com.gl365.payment.dto.wx.request.WxConfirmReqDTO;
import com.gl365.payment.dto.wx.response.WxConfirmRespDTO;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.model.PayDetail;
import com.gl365.payment.remote.dto.account.request.UpdateAccountBalanceOffLineReqDTO;
import com.gl365.payment.remote.dto.settlement.response.ConfirmPreSettleDateRespDTO;
import com.gl365.payment.service.wz.rm.RmWxPayContext;
public class RmWxPayConfirmContext extends RmWxPayContext {
	private static final long serialVersionUID = 1L;
	private WxConfirmReqDTO request;
	private WxConfirmRespDTO response = new WxConfirmRespDTO();
	private PayStatus payStatus;
	private List<PayDetail> payDetails = new ArrayList<PayDetail>();
	private UpdateAccountBalanceOffLineReqDTO updateAccBlanceRequest = new UpdateAccountBalanceOffLineReqDTO();
	private ConfirmPreSettleDateRespDTO confirmPreSettleDateRespDTO;

	public WxConfirmReqDTO getRequest() {
		return request;
	}

	public void setRequest(WxConfirmReqDTO request) {
		this.request = request;
	}

	public WxConfirmRespDTO getResponse() {
		return response;
	}

	public void setResponse(WxConfirmRespDTO response) {
		this.response = response;
	}

	public PayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}

	public List<PayDetail> getPayDetails() {
		return payDetails;
	}

	public void setPayDetails(List<PayDetail> payDetails) {
		this.payDetails = payDetails;
	}

	public UpdateAccountBalanceOffLineReqDTO getUpdateAccBlanceRequest() {
		return updateAccBlanceRequest;
	}

	public void setUpdateAccBlanceRequest(UpdateAccountBalanceOffLineReqDTO updateAccBlanceRequest) {
		this.updateAccBlanceRequest = updateAccBlanceRequest;
	}

	public ConfirmPreSettleDateRespDTO getConfirmPreSettleDateRespDTO() {
		return confirmPreSettleDateRespDTO;
	}

	public void setConfirmPreSettleDateRespDTO(ConfirmPreSettleDateRespDTO confirmPreSettleDateRespDTO) {
		this.confirmPreSettleDateRespDTO = confirmPreSettleDateRespDTO;
	}
}
