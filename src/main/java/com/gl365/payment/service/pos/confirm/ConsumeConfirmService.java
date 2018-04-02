package com.gl365.payment.service.pos.confirm;
import com.gl365.payment.dto.consumeconfirm.request.ConsumeConfirmReqDTO;
import com.gl365.payment.dto.consumeconfirm.response.ConsumeConfirmRespDTO;
public interface ConsumeConfirmService {
	/**
	 * 消费确认
	 * 付费通通过该交易完成乐豆、乐币等的扣款
	 * @param reqDTO
	 * @param respDTO
	 * @return
	 */
	ConsumeConfirmRespDTO consumeConfirm(ConsumeConfirmReqDTO reqDTO, ConsumeConfirmRespDTO respDTO);
}
