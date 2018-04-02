package com.gl365.payment.web.web.reward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gl365.payment.dto.onlineconsume.request.OnlineRewardBeanConsumeReqDTO;
import com.gl365.payment.dto.onlineconsume.response.OnlineRewardBeanConsumeRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.service.web.reward.OnlineRewardBeanConsumeService;
import com.gl365.payment.web.AbstractBaseController;
@RestController
public class OnlineRewardBeanConsumeController extends AbstractBaseController<OnlineRewardBeanConsumeReqDTO, OnlineRewardBeanConsumeRespDTO> {
	@Autowired
	private OnlineRewardBeanConsumeService onlineRewardBeanConsumeService;

	@PostMapping(value = "onlineRewardBeanConsume")
	@Override
	public OnlineRewardBeanConsumeRespDTO service(@RequestBody OnlineRewardBeanConsumeReqDTO reqDTO) {
		OnlineRewardBeanConsumeRespDTO respDTO = new OnlineRewardBeanConsumeRespDTO(Msg.UNKNOW_FAIL.getCode(), Msg.UNKNOW_FAIL.getDesc());
		return onlineRewardBeanConsumeService.onlineRewardBeanConsume(reqDTO, respDTO);
	}
}
