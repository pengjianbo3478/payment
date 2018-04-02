package com.gl365.payment.service.web.refund.refund.abs;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.gl365.payment.common.Finance;
import com.gl365.payment.common.constants.PaymentConstants;
import com.gl365.payment.dto.base.BaseContext;
import com.gl365.payment.dto.refund.request.RefundReqDTO;
import com.gl365.payment.dto.refund.response.RefundRespDTO;
import com.gl365.payment.enums.pay.DcType;
import com.gl365.payment.enums.pay.DealStatus;
import com.gl365.payment.enums.pay.OrderType;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.enums.pay.SplitFlag;
import com.gl365.payment.enums.system.GlSystem;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayDetail;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayReturn;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.remote.dto.account.request.CancelOperateReqDTO;
import com.gl365.payment.util.BigDecimalUtil;
import com.gl365.payment.util.FormatUtil;

public abstract class AbstractRefundBuild extends AbstractRefundCheck{

	@Override
	public PayStream buildPayStream(BaseContext<RefundReqDTO, RefundRespDTO> bc) {
		PayStream ps = payAdapter.buildPayStream(bc);
		RefundReqDTO reqDTO = bc.getRequest();
		ps.setRequestId(reqDTO.getRequestId());
		ps.setRequestDate(FormatUtil.parseYyyyMMdd(reqDTO.getRequestDate()));
		ps.setOrigRequestId(reqDTO.getRequestId());
		ps.setOrigPayDate(FormatUtil.parseYyyyMMdd(reqDTO.getOrigTxnDate()));
		ps.setOrganCode(reqDTO.getOrganCode());
		ps.setOrganMerchantNo(reqDTO.getOrganMerchantNo());
		ps.setTerminal(reqDTO.getTerminal());
		ps.setOperator(reqDTO.getOperator());
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
	public PayMain buildPayMain(BaseContext<RefundReqDTO, RefundRespDTO> bc, DealStatus dealStatus) {
		PayMain payMain = payAdapter.clonePayMain(bc.getPayMain(), dealStatus, null);
		bc.setPayMain(payMain);
		return payMain;
	}

	@Override
	public List<PayDetail> buildPayDetails(BaseContext<RefundReqDTO, RefundRespDTO> bc) {
		ArrayList<PayDetail> payDetails = new ArrayList<PayDetail>();
		PayDetail ps = new PayDetail();
		ps.setPayId(bc.getPayId());
		ps.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
		ps.setCreateTime(LocalDateTime.now());
		ps.setOrganCode(bc.getRequest().getOrganCode());
		PayReturn payReturn = bc.getPayReturn();
		boolean isGroupMainOrder = OrderType.groupPay.getCode().equals(payReturn.getOrderType()) && SplitFlag.mainOrder.getCode().equals(payReturn.getSplitFlag());
		if (isGroupMainOrder) buildGroupMainOrderPayDetail(bc, ps, payDetails);
		else {
			if (BigDecimalUtil.GreaterThan0(payReturn.getBeanAmount())) {
				ps.setPayType(PaymentConstants.PayType.PAY_BEAN.getCode());
				ps.setPayAccount(bc.getGl365User().getUserId());
				ps.setPayAmount(payReturn.getBeanAmount());
				payDetails.add(ps);
			}
			if (BigDecimalUtil.GreaterThan0(payReturn.getCashAmount())) {
				PayDetail ps2 = ps.clone();
				ps2.setPayType(PaymentConstants.PayType.PAY_CASH.getCode());
				ps2.setPayAmount(payReturn.getCashAmount());
				ps2.setPayAccount(bc.getGl365User().getUserId());
				payDetails.add(ps2);
			}
		}
		bc.setPayDetails(payDetails);
		return payDetails;
	}
	
	private void buildGroupMainOrderPayDetail(BaseContext<RefundReqDTO, RefundRespDTO> bc, PayDetail ps, ArrayList<PayDetail> payDetails) {
		PayReturn payReturn = bc.getPayReturn();
		if (BigDecimalUtil.GreaterThan0(payReturn.getGroupPtPay())) {
			ps.setPayType(PaymentConstants.PayType.PAY_BEAN.getCode());
			ps.setPayAmount(payReturn.getGroupPtPay());
			ps.setPayAccount("1000001900343");
			payDetails.add(ps);
		}
		if (BigDecimalUtil.GreaterThan0(payReturn.getGroupMainuserPayBean())) {
			PayDetail ps2 = ps.clone();
			ps2.setPayType(PaymentConstants.PayType.PAY_BEAN.getCode());
			ps2.setPayAccount(bc.getGl365User().getUserId());
			ps2.setPayAmount(payReturn.getGroupMainuserPayBean());
			payDetails.add(ps2);
		}
		if (BigDecimalUtil.GreaterThan0(payReturn.getCashAmount())) {
			PayDetail ps3 = ps.clone();
			ps3.setPayType(PaymentConstants.PayType.PAY_CASH.getCode());
			ps3.setPayAmount(payReturn.getCashAmount());
			ps3.setPayAccount(bc.getGl365User().getUserId());
			payDetails.add(ps3);
		}
	}

	public PayReturn buildPayReturn(BaseContext<RefundReqDTO, RefundRespDTO> bc) {
		RefundReqDTO request = bc.getRequest();
		PayReturn payReturn = payAdapter.buildPayReturn(bc, bc.getFinance().getRefundType());
		payAdapter.setPayReturn(payReturn, bc.getPayMain());
		payReturn.setUserId(bc.getGl365User().getUserId());
		//boolean isGroupOrder = OrderType.groupPay.getCode().equals(payReturn.getOrderType());
		boolean isGroupOrder = OrderType.groupPay.getCode().equals(bc.getPayMain().getOrderType());
		if (isGroupOrder) setGroupFinance(payReturn, bc.getPayMain());
		else payAdapter.setPayReturn(payReturn, bc.getFinance());
		payReturn.setTransType(getOperateType(bc).getCode());
		payReturn.setPayId(bc.getPayId());
		payReturn.setRequestId(request.getRequestId());
		payReturn.setRequestDate(FormatUtil.parseYyyyMMdd(request.getRequestDate()));
		payReturn.setOrigPayId(request.getOrigPayId());
		payReturn.setOrigPayDate(FormatUtil.parseYyyyMMdd(request.getOrigTxnDate()));
		payReturn.setOrganCode(request.getOrganCode());
		payReturn.setOrganMerchantNo(request.getOrganMerchantNo());
		payReturn.setTerminal(request.getTerminal());
		payReturn.setOperator(request.getOperator());
		payReturn.setTotalAmount(request.getTotalAmount());
		payReturn.setCardIndex(bc.getCardIndex());
		payReturn.setPayTime(LocalDateTime.now());
		bc.setPayReturn(payReturn);
		bc.setTranType(this.getOperateType(bc));
		return payReturn;
	}
	
	@Override
	public RefundRespDTO buildReturnResponse(BaseContext<RefundReqDTO, RefundRespDTO> bc) throws ServiceException {
		RefundReqDTO request = bc.getRequest();
		RefundRespDTO response = bc.getResponse();
		Finance finance = bc.getFinance();
		response.setOrganCode(request.getOrganCode());
		response.setOrganMerchantNo(request.getOrganMerchantNo());
		response.setTerminal(request.getTerminal());
		response.setRequestId(request.getRequestId());
		response.setCardIndex(request.getCardIndex());
		response.setPayId(bc.getPayId());
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

	public CancelOperateReqDTO buildCancelOperate(BaseContext<RefundReqDTO, RefundRespDTO> bc) {
		PayMain payMain = bc.getPayMain();
		String code = this.getOperateType(bc).getCode();
		String origPayId = payMain.getPayId();
		CancelOperateReqDTO request = payAdapter.buildUpdateAccount(payMain, code,origPayId);
		request.setUserId(bc.getGl365User().getUserId());
		setUpdateAmount(payMain, request);
		request.setPayId(bc.getPayId());
		request.setDcType(DcType.C.getCode());
		bc.setCancelOperateReqDTO(request);
		return request;
	}
	
	public void putFinance(Finance returned, BigDecimal fAmt, PayReturn pr) {
		returned.setTotalAmount(BigDecimalUtil.add(fAmt, pr.getTotalAmount()));
		returned.setBeanAmount(BigDecimalUtil.add(returned.getBeanAmount(), pr.getBeanAmount()));
		returned.setCashAmount(BigDecimalUtil.add(returned.getCashAmount(), pr.getCashAmount()));
		returned.setMarcketFee(BigDecimalUtil.add(returned.getMarcketFee(), pr.getMarcketFee()));
		returned.setCoinAmount(BigDecimalUtil.add(returned.getCoinAmount(), pr.getCoinAmount()));
		returned.setGiftAmount(BigDecimalUtil.add(returned.getGiftAmount(), pr.getGiftAmount()));
		returned.setCommAmount(BigDecimalUtil.add(returned.getCommAmount(), pr.getCommAmount()));
		returned.setPayFee(BigDecimalUtil.add(returned.getPayFee(), pr.getPayFee()));
		returned.setGiftPoint(BigDecimalUtil.add(returned.getGiftPoint(), pr.getGiftPoint()));
		returned.setMerchantSettlAmount(BigDecimalUtil.add(returned.getMerchantSettlAmount(), pr.getMerchantSettleAmount()));
	}
	
	private void setUpdateAmount(PayMain payMain, CancelOperateReqDTO request) {
		boolean isGroupOrder = OrderType.groupPay.getCode().equals(payMain.getOrderType());
		boolean isGroupMainOrder = isGroupOrder && SplitFlag.mainOrder.getCode().equals(payMain.getSplitFlag());
		boolean isGroupChildOrder = isGroupOrder && SplitFlag.childOrder.getCode().equals(payMain.getSplitFlag());
		BigDecimal operateAmount = payMain.getBeanAmount();	
		BigDecimal giftAmount = payMain.getGiftAmount();
		if (isGroupMainOrder) {
			operateAmount = payMain.getGroupMainuserPayBean();
			giftAmount = payMain.getGroupGiftAmount();
		}
		if (isGroupChildOrder) {
			giftAmount = payMain.getGroupGiftAmount();
		}
		request.setOperateAmount(operateAmount);
		request.setGiftAmount(giftAmount);
	}
	
	private void setGroupFinance(PayReturn payReturn, PayMain paymain) {
		payReturn.setTransType(paymain.getTransType());
		payReturn.setCashAmount(paymain.getCashAmount());
		payReturn.setBeanAmount(paymain.getBeanAmount());
		payReturn.setCoinAmount(paymain.getCoinAmount());
		payReturn.setPayFee(paymain.getPayFee());
		payReturn.setCommAmount(paymain.getCommAmount());
		payReturn.setMarcketFee(paymain.getMarcketFee());
		payReturn.setGiftAmount(paymain.getGiftAmount());
		payReturn.setGiftPoint(paymain.getGiftPoint());
		payReturn.setMerchantSettleAmount(paymain.getMerchantSettleAmount());
		payReturn.setPayStatus(PayStatus.ALL_RETURN.getCode());
		payReturn.setPayDesc(PayStatus.ALL_RETURN.getDesc());
	}
}
