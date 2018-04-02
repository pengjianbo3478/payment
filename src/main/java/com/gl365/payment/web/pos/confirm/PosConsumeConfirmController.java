package com.gl365.payment.web.pos.confirm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gl365.payment.dto.consumeconfirm.request.ConsumeConfirmReqDTO;
import com.gl365.payment.dto.consumeconfirm.response.ConsumeConfirmRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.service.pos.confirm.ConsumeConfirmService;
import com.gl365.payment.web.AbstractBaseController;

/**
 * POS消费确认
 */
@RestController
public class PosConsumeConfirmController extends AbstractBaseController<ConsumeConfirmReqDTO, ConsumeConfirmRespDTO> {

	@Autowired
	private ConsumeConfirmService consumeConfirmService;
	
	@PostMapping(value = "consumeConfirm")
	@Override
	public ConsumeConfirmRespDTO service(@RequestBody ConsumeConfirmReqDTO reqDTO) {
		ConsumeConfirmRespDTO respDTO = new ConsumeConfirmRespDTO(Msg.UNKNOW_FAIL.getCode(), Msg.UNKNOW_FAIL.getDesc());
		return this.consumeConfirmService.consumeConfirm(reqDTO, respDTO);
	}

}
