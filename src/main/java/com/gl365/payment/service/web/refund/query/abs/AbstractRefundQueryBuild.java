package com.gl365.payment.service.web.refund.query.abs;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.gl365.payment.common.Finance;
import com.gl365.payment.dto.base.BaseContext;
import com.gl365.payment.dto.refund.query.request.RefundQueryReqDTO;
import com.gl365.payment.dto.refund.query.response.RefundQueryRespDTO;
import com.gl365.payment.enums.pay.DealStatus;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayDetail;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayReturn;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.util.BigDecimalUtil;
import com.gl365.payment.util.FormatUtil;
public abstract class AbstractRefundQueryBuild extends AbstractRefundQueryCheck {
	@Override
	public PayMain buildPayMain(BaseContext<RefundQueryReqDTO, RefundQueryRespDTO> bc, DealStatus dealStatus) {
		return null;
	}

	@Override
	public List<PayDetail> buildPayDetails(BaseContext<RefundQueryReqDTO, RefundQueryRespDTO> bc) {
		return null;
	}

	@Override
	public PayStream buildPayStream(BaseContext<RefundQueryReqDTO, RefundQueryRespDTO> bc) {
		PayStream ps = payAdapter.buildPayStream(bc);
		RefundQueryReqDTO reqDTO = bc.getRequest();
		ps.setRequestId(reqDTO.getRequestId());
		ps.setRequestDate(FormatUtil.parseYyyyMMdd(reqDTO.getRequestDate()));
		ps.setOrigRequestId(reqDTO.getRequestId());
		ps.setOrigPayDate(FormatUtil.parseYyyyMMdd(reqDTO.getOrigTxnDate()));
		ps.setOrganCode(reqDTO.getOrganCode());
		ps.setOrganMerchantNo(reqDTO.getOrganMerchantNo());
		ps.setTerminal(reqDTO.getTerminal());
		ps.setTotalAmount(reqDTO.getTotalAmount());
		ps.setReturnAmount(reqDTO.getTotalAmount());
		String terminal = reqDTO.getTerminal();
		String requestId = reqDTO.getRequestId();
		String mergeString = FormatUtil.mergeString(terminal, requestId);
		ps.setUniqueSerial(mergeString);
		ps.setTransType(bc.getTranType().getCode());
		bc.setPayStream(ps);
		return ps;
	}

	@Override
	public RefundQueryRespDTO buildReturnResponse(BaseContext<RefundQueryReqDTO, RefundQueryRespDTO> bc) throws ServiceException {
		RefundQueryReqDTO request = bc.getRequest();
		RefundQueryRespDTO response = bc.getResponse();
		response.setOrganCode(request.getOrganCode());
		response.setOrganMerchantNo(request.getOrganMerchantNo());
		response.setTerminal(request.getTerminal());
		response.setRequestId(request.getRequestId());
		response.setCardIndex(request.getCardIndex());
		response.setPayId(bc.getPayId());
		Finance finance = bc.getFinance();
		response.setTotalAmount(finance.getTotalAmount());
		response.setMarketFee(finance.getMarcketFee());
		response.setCoinAmount(finance.getCoinAmount());
		response.setBeanAmount(finance.getBeanAmount());
		response.setCashMoney(finance.getCashAmount());
		response.setGiftAmount(finance.getGiftAmount());
		response.setGiftPoint(finance.getGiftPoint());
		response.setTxnDate(FormatUtil.formatYyyyMMdd(LocalDateTime.now()));
		response.setPayStatus(DealStatus.ALREADY_PAID.getCode());
		response.setPayDesc(DealStatus.ALREADY_PAID.getDesc());
		return response;
	}

	public void putReturned(Finance returned, PayReturn pr) {
		returned.setTotalAmount(BigDecimalUtil.add(returned.getTotalAmount(), pr.getTotalAmount()));
		returned.setBeanAmount(BigDecimalUtil.add(returned.getBeanAmount(), pr.getBeanAmount()));
		returned.setCashAmount(BigDecimalUtil.add(returned.getCashAmount(), pr.getCashAmount()));
		returned.setMarcketFee(BigDecimalUtil.add(returned.getMarcketFee(), pr.getMarcketFee()));
		returned.setCoinAmount(BigDecimalUtil.add(returned.getCoinAmount(), pr.getCoinAmount()));
		returned.setGiftAmount(BigDecimalUtil.add(returned.getGiftAmount(), pr.getGiftAmount()));
		returned.setCommAmount(BigDecimalUtil.add(returned.getCommAmount(), pr.getCommAmount()));
		returned.setPayFee(BigDecimalUtil.add(returned.getPayFee(), pr.getPayFee()));
		returned.setGiftPoint(BigDecimalUtil.add(returned.getGiftPoint(), pr.getGiftPoint()));
		BigDecimal merchantSettlAmount = returned.getMerchantSettlAmount();
		BigDecimal settlAmt = BigDecimalUtil.add(merchantSettlAmount, pr.getMerchantSettleAmount());
		returned.setMerchantSettlAmount(settlAmt);
	}
}
