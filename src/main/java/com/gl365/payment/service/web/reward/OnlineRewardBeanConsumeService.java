package com.gl365.payment.service.web.reward;
import com.gl365.payment.dto.onlineconsume.request.OnlineRewardBeanConsumeReqDTO;
import com.gl365.payment.dto.onlineconsume.response.OnlineRewardBeanConsumeRespDTO;
public interface OnlineRewardBeanConsumeService {
	OnlineRewardBeanConsumeRespDTO onlineRewardBeanConsume(OnlineRewardBeanConsumeReqDTO reqDTO, OnlineRewardBeanConsumeRespDTO respDTO);
}
