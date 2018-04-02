package com.gl365.payment.service.web.reward;
import com.gl365.payment.dto.onlineconsume.request.OnlineRewardConsumeReqDTO;
import com.gl365.payment.dto.onlineconsume.response.OnlineConsumeRespDTO;
public interface OnlineRewardConsumeService {
	OnlineConsumeRespDTO onlineRewardConsume(OnlineRewardConsumeReqDTO reqDTO, OnlineConsumeRespDTO respDTO);
}
