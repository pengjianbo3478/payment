package com.gl365.payment.dto.onlineconsume;
import com.gl365.payment.dto.base.PayContext;
import com.gl365.payment.dto.onlineconsume.request.OnlineConsumeReqDTO;
public class OnlineConsumeContext extends PayContext {
	private static final long serialVersionUID = 1L;
	private OnlineConsumeReqDTO reqDTO;

	public OnlineConsumeReqDTO getReqDTO() {
		return reqDTO;
	}

	public void setReqDTO(OnlineConsumeReqDTO reqDTO) {
		super.setCardIndex(reqDTO.getCardIndex());
		this.reqDTO = reqDTO;
	}
}
