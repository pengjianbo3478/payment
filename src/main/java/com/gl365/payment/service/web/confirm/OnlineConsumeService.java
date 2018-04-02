package com.gl365.payment.service.web.confirm;
import com.gl365.payment.dto.onlineconsume.request.OnlineConsumeReqDTO;
import com.gl365.payment.dto.onlineconsume.response.OnlineConsumeRespDTO;
public interface OnlineConsumeService {
	OnlineConsumeRespDTO onlineConsume(OnlineConsumeReqDTO reqDTO, OnlineConsumeRespDTO respDTO);
}
