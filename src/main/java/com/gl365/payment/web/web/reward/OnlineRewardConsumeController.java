package com.gl365.payment.web.web.reward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.gl365.payment.dto.onlineconsume.request.OnlineRewardConsumeReqDTO;
import com.gl365.payment.dto.onlineconsume.response.OnlineConsumeRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.service.web.reward.OnlineRewardConsumeService;
import com.gl365.payment.web.AbstractBaseController;
@RestController
public class OnlineRewardConsumeController extends AbstractBaseController<OnlineRewardConsumeReqDTO, OnlineConsumeRespDTO> {
	@Autowired
	private OnlineRewardConsumeService onlineRewardConsumeService;

	@PostMapping(value = "onlineRewardConsume")
	@Override
	public OnlineConsumeRespDTO service(@RequestBody OnlineRewardConsumeReqDTO reqDTO) {
		OnlineConsumeRespDTO respDTO = new OnlineConsumeRespDTO(Msg.UNKNOW_FAIL.getCode(), Msg.UNKNOW_FAIL.getDesc());
		return onlineRewardConsumeService.onlineRewardConsume(reqDTO, respDTO);
	}
}
