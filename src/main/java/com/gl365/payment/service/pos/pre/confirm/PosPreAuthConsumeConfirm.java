package com.gl365.payment.service.pos.pre.confirm;
import com.gl365.payment.dto.authconsumeconfirm.request.AuthConsumeConfirmReqDTO;
import com.gl365.payment.dto.authconsumeconfirm.response.AuthConsumeConfirmRespDTO;
public interface PosPreAuthConsumeConfirm {
	/**
	 * 预授权完成确认
	 * @param reqDTO
	 * @param respDTO
	 * @return
	 */
	AuthConsumeConfirmRespDTO authConsumeConfirm(AuthConsumeConfirmReqDTO reqDTO, AuthConsumeConfirmRespDTO respDTO);
}
