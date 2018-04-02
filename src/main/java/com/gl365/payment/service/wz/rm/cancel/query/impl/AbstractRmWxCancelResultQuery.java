package com.gl365.payment.service.wz.rm.cancel.query.impl;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import com.gl365.payment.dto.wx.request.WxCancelResultQueryReqDTO;
import com.gl365.payment.dto.wx.response.WxCancelResultQueryRespDTO;
import com.gl365.payment.enums.system.ResultCode;
import com.gl365.payment.model.PayReturn;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.service.dbservice.PayReturnService;
import com.gl365.payment.service.wz.common.AbstractPay;
import com.gl365.payment.service.wz.rm.cancel.query.RmWxCancelResultQueryContext;
import com.gl365.payment.util.IdGenerator;

public abstract class AbstractRmWxCancelResultQuery extends AbstractPay {
	@Autowired
	protected PayReturnService payReturnService;
	
	public void buildPayStream(RmWxCancelResultQueryContext ctx) {
		WxCancelResultQueryReqDTO request = ctx.getRequest();
		PayStream ps = ctx.getPayStream();
		ps.setRequestId(IdGenerator.getUuId32());
		ps.setRequestDate(LocalDate.now());
		ps.setOrigRequestId(null);
		ps.setOrigPayDate(null);
		ps.setOrganCode(request.getOrganCode());
		ps.setOrganMerchantNo(null);
		ps.setTerminal(null);
		ps.setOperator(null);
		ps.setTransType(this.initTranType().getCode());
		ps.setTotalAmount(BigDecimal.ZERO);
		ps.setReturnAmount(BigDecimal.ZERO);
		ps.setUniqueSerial(UUID.randomUUID().toString());
		ctx.setPayStream(ps);
	}

	public void buildResponseResult(RmWxCancelResultQueryContext ctx) {
		WxCancelResultQueryRespDTO response = ctx.getResponse();
		PayReturn payReturn = ctx.getPayReturn();
		if (payReturn == null) {
			response.setResultCode(ResultCode.FAIL.getCode());
			response.setResultDesc(ResultCode.FAIL.getDesc());
		}else {
			response.setResultCode(ResultCode.SUCCESS.getCode());
			response.setResultDesc(ResultCode.SUCCESS.getDesc());
		}
	}

	public void checkRequest(RmWxCancelResultQueryContext ctx) {
		WxCancelResultQueryReqDTO request = ctx.getRequest();
		this.checkPayRequestService.checkOrganCode(request.getOrganCode());
		this.checkPayRequestService.checkMerchantOrderNo(request.getMerchantOrderNo());
	}
}
