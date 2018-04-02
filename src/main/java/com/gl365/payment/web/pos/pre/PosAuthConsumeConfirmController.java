package com.gl365.payment.web.pos.pre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.gl365.payment.dto.authconsumeconfirm.request.AuthConsumeConfirmReqDTO;
import com.gl365.payment.dto.authconsumeconfirm.response.AuthConsumeConfirmRespDTO;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.service.pos.pre.confirm.PosPreAuthConsumeConfirm;
import com.gl365.payment.web.AbstractBaseController;
@RestController
public class PosAuthConsumeConfirmController extends AbstractBaseController<AuthConsumeConfirmReqDTO, AuthConsumeConfirmRespDTO> {
	@Autowired
	private PosPreAuthConsumeConfirm posPreauthConsumeConfirm;

	@PostMapping(value = "preAuthConsumeConfirm")
	@Override
	public AuthConsumeConfirmRespDTO service(@RequestBody AuthConsumeConfirmReqDTO reqDTO) {
		AuthConsumeConfirmRespDTO respDTO = new AuthConsumeConfirmRespDTO(PayStatus.COMPLETE_PAY.getCode(), PayStatus.COMPLETE_PAY.getDesc());
		return posPreauthConsumeConfirm.authConsumeConfirm(reqDTO, respDTO);
	}
}
