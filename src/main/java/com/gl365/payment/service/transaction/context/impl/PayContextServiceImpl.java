package com.gl365.payment.service.transaction.context.impl;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.dto.base.PayContext;
import com.gl365.payment.model.PayDetail;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.service.dbservice.PayDetailService;
import com.gl365.payment.service.dbservice.PayMainService;
import com.gl365.payment.service.dbservice.PayStreamService;
import com.gl365.payment.service.transaction.context.PayContextService;
import com.gl365.payment.service.transaction.remote.AbstractRemoteServiceFacade;
import com.gl365.payment.util.IdGenerator;
import com.gl365.payment.util.Gl365StrUtils;
@Service("payContextService")
public class PayContextServiceImpl extends AbstractRemoteServiceFacade implements PayContextService {
	private static final Logger LOG = LoggerFactory.getLogger(PayContextServiceImpl.class);
	@Autowired
	private PayMainService payMainService;
	@Autowired
	private PayStreamService payStreamService;
	@Autowired
	private PayDetailService payDetailService;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int savePayStream(PayContext ctx) {
		PayStream payStream = ctx.getPayStream();
		LOG.debug("payStream={}", Gl365StrUtils.toStr(ctx));
		return this.payStreamService.save(payStream);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int savePayDetails(PayContext ctx) {
		List<PayDetail> payDetails = ctx.getPayDetails();
		LOG.debug("payDetails={}", Gl365StrUtils.toStr(ctx));
		for (PayDetail payDetail : payDetails) {
			this.payDetailService.save(payDetail);
		}
		return payDetails.size();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PayMain queryPayMainByPayId(String payId) {
		PayMain payMain = this.payMainService.queryByPayId(payId);
		LOG.debug("payMain={}", Gl365StrUtils.toStr(payMain));
		return payMain;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PayMain queryPayMainByRequestId(PayContext ctx) {
		LOG.debug("ctx={}", Gl365StrUtils.toStr(ctx));
		String requestId = ctx.getRequestId();
		PayMain payMain = this.payMainService.queryByRequestId(requestId);
		LOG.debug("payMain={}", Gl365StrUtils.toStr(payMain));
		ctx.setPayMain(payMain);
		return payMain;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PayStream queryPayStreamByRequestId(String requestId) {
		PayStream payStream = this.payStreamService.queryByRequestId(requestId);
		LOG.debug("payStream={}", Gl365StrUtils.toStr(payStream));
		return payStream;
	}

	@Override
	public String generatePayId(String payCategory) {
		String payId = IdGenerator.generatePayId(payCategory);
		LOG.info("生成给乐交易流水号={}", payId);
		return payId;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PayMain queryPayMainByRequestIdAndTerminal(String terminal, String requestId) {
		LOG.debug("terminal={},requestId={}", terminal,requestId);
		PayMain payMain = this.payMainService.queryByTerminalAndRequestId(terminal, requestId);
		LOG.debug("payMain={}", Gl365StrUtils.toStr(payMain));
		return payMain;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int updatePayStreamStatus(PayStream payStream) {
		String payId = payStream.getPayId();
		String dealStatus = payStream.getDealStatus();
		String dealDesc = payStream.getDealDesc();
		return this.payStreamService.updateStatus(payId, dealStatus,dealDesc);
	}

}
