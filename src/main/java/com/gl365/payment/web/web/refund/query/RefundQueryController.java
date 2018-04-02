package com.gl365.payment.web.web.refund.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gl365.payment.dto.refund.query.request.RefundQueryReqDTO;
import com.gl365.payment.dto.refund.query.response.RefundQueryRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.service.web.refund.query.RefundQueryService;
import com.gl365.payment.web.AbstractBaseController;

/**
 * 退货查询
 * @author duanxz
 *2017年5月22日
 */
@RestController
public class RefundQueryController extends AbstractBaseController<RefundQueryReqDTO, RefundQueryRespDTO> {

	@Autowired
	private RefundQueryService refundQueryService;
	
	@PostMapping(value = "refundQuery")
	@Override
	public RefundQueryRespDTO service(@RequestBody RefundQueryReqDTO reqDTO) {
		RefundQueryRespDTO respDTO = new RefundQueryRespDTO(Msg.UNKNOW_FAIL.getCode(), Msg.UNKNOW_FAIL.getDesc());
		return refundQueryService.refundQuery(reqDTO, respDTO);
	}

}
