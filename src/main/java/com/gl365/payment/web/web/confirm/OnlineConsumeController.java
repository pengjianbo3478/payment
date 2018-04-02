package com.gl365.payment.web.web.confirm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.gl365.payment.dto.onlineconsume.request.OnlineConsumeReqDTO;
import com.gl365.payment.dto.onlineconsume.response.OnlineConsumeRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.pay.OrderType;
import com.gl365.payment.enums.pay.SplitFlag;
import com.gl365.payment.service.web.confirm.OnlineConsumeService;
import com.gl365.payment.web.AbstractBaseController;
@RestController
public class OnlineConsumeController extends AbstractBaseController<OnlineConsumeReqDTO, OnlineConsumeRespDTO> {
	@Autowired
	@Qualifier("onlineConsumeService")
	private OnlineConsumeService onlineConsumeService;
	
	@Autowired
	@Qualifier("onlineConsumeParticipantService")
	private OnlineConsumeService onlineConsumeParticipantService;
	
	@Autowired
	@Qualifier("onlineConsumeInitiatorService")
	private OnlineConsumeService onlineConsumeInitiatorService;

	@PostMapping(value = "onlineConsume")
	@Override
	public OnlineConsumeRespDTO service(@RequestBody OnlineConsumeReqDTO reqDTO) {
		OnlineConsumeRespDTO respDTO = new OnlineConsumeRespDTO(Msg.UNKNOW_FAIL.getCode(), Msg.UNKNOW_FAIL.getDesc());
		return getOnlineConsumeService(reqDTO).onlineConsume(reqDTO, respDTO);
	}
	
	private OnlineConsumeService getOnlineConsumeService(OnlineConsumeReqDTO reqDTO) {
		String orderType = reqDTO.getOrderType();
		if (OrderType.groupPay.getCode().equals(orderType)) {
			String splitFlag = reqDTO.getSplitFlag();
			if (SplitFlag.mainOrder.getCode().equals(splitFlag)) 
				return onlineConsumeInitiatorService;
			else 
				return onlineConsumeParticipantService;
		}  
		
		return onlineConsumeService;
	}
}
