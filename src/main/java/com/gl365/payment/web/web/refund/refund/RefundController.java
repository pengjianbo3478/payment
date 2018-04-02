package com.gl365.payment.web.web.refund.refund;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gl365.payment.dto.refund.request.RefundReqDTO;
import com.gl365.payment.dto.refund.response.RefundRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.service.web.refund.refund.RefundService;
import com.gl365.payment.web.AbstractBaseController;

/**
 * 退货
 * @author duanxz
 *2017年5月22日
 */
@RestController
public class RefundController extends AbstractBaseController<RefundReqDTO, RefundRespDTO> {

	@Autowired
	private RefundService refundService;
	
	@PostMapping(value = "refund")
	@Override
	public RefundRespDTO service(@RequestBody RefundReqDTO reqDTO) {
		RefundRespDTO respDTO = new RefundRespDTO(Msg.UNKNOW_FAIL.getCode(), Msg.UNKNOW_FAIL.getDesc());
		return refundService.refund(reqDTO, respDTO);
	}


}
