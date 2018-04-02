package com.gl365.payment.service.wz.rm.cancel.query.impl;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import com.gl365.payment.dto.wx.request.WxCancelQueryReqDTO;
import com.gl365.payment.dto.wx.response.WxCancelQueryRespDTO;
import com.gl365.payment.enums.system.ResultCode;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.service.wz.common.AbstractPay;
import com.gl365.payment.service.wz.rm.cancel.query.RmWxCancelQueryContext;
import com.gl365.payment.util.IdGenerator;
public abstract class AbstractRmWxCancelQuery extends AbstractPay {
	public void buildPayStream(RmWxCancelQueryContext ctx) {
		WxCancelQueryReqDTO request = ctx.getRequest();
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

	public void buildResponseResult(RmWxCancelQueryContext ctx) {
		WxCancelQueryRespDTO response = ctx.getResponse();
		response.setResultCode(ResultCode.SUCCESS.getCode());
		response.setResultDesc(ResultCode.SUCCESS.getDesc());
	}

	public void checkRequest(RmWxCancelQueryContext ctx) {
		WxCancelQueryReqDTO request = ctx.getRequest();
		this.checkPayRequestService.checkOrganCode(request.getOrganCode());
		this.checkPayRequestService.checkMerchantOrderNo(request.getMerchantOrderNo());
	}
}
