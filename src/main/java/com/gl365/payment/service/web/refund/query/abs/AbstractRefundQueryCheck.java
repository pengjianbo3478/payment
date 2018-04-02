package com.gl365.payment.service.web.refund.query.abs;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.gl365.payment.common.constants.PaymentConstants;
import com.gl365.payment.common.constants.PaymentConstants.Column;
import com.gl365.payment.dto.base.BaseContext;
import com.gl365.payment.dto.refund.query.request.RefundQueryReqDTO;
import com.gl365.payment.dto.refund.query.response.RefundQueryRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.exception.InvalidRequestException;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.service.transaction.AbstractTranscation;
public abstract class AbstractRefundQueryCheck extends AbstractTranscation<RefundQueryReqDTO, RefundQueryRespDTO> {
	@Override
	public boolean checkAndSaveInputParameter(BaseContext<RefundQueryReqDTO, RefundQueryRespDTO> bc) throws InvalidRequestException {
		Map<PaymentConstants.Column, String> strColumns = new HashMap<PaymentConstants.Column, String>();
		RefundQueryReqDTO request = bc.getRequest();
		strColumns.put(Column.organCode, request.getOrganCode());
		strColumns.put(Column.requestId, request.getRequestId());
		strColumns.put(Column.requestDate, request.getRequestDate());
		strColumns.put(Column.organMerchantNo, request.getOrganMerchantNo());
		strColumns.put(Column.cardIndex, request.getCardIndex());
		strColumns.put(Column.origPayId, request.getOrigPayId());
		//strColumns.put(Column.payChannel, request.getPayChannel());
		Map<PaymentConstants.Column, String> strLenColumns = new HashMap<PaymentConstants.Column, String>();
		strLenColumns.put(Column.organCode, request.getOrganCode());
		strLenColumns.put(Column.requestId, request.getRequestId());
		strLenColumns.put(Column.requestDate, request.getRequestDate());
		strLenColumns.put(Column.organMerchantNo, request.getOrganMerchantNo());
		strLenColumns.put(Column.terminal, request.getTerminal());
		strLenColumns.put(Column.cardIndex, request.getCardIndex());
		strLenColumns.put(Column.origPayId, request.getOrigPayId());
		strLenColumns.put(Column.payChannel, request.getPayChannel());
		Map<PaymentConstants.Column, BigDecimal> bigDecimalColumns = new HashMap<PaymentConstants.Column, BigDecimal>();
		bigDecimalColumns.put(Column.totalAmount, request.getTotalAmount());
		boolean a = !checkStringNPT(bc, strColumns);
		boolean b = !checkStringLen(bc, strLenColumns);
		boolean c = !checkBigDecimalNPT(bc, bigDecimalColumns);
		if (a || b || c) return false;
		bc.setCardIndex(request.getCardIndex());
		bc.setMerchantNo(bc.getRequest().getOrganMerchantNo());
		return true;
	}

	@Override
	public boolean bizCheck(BaseContext<RefundQueryReqDTO, RefundQueryRespDTO> bc) throws ServiceException {
		PayMain pm = bc.getPayMain();
		if (pm == null) {
			setReturnResponse(bc, Msg.PAY_8012);
			return false;
		}
		if (!StringUtils.equals(bc.getCardIndex(), pm.getCardIndex())) {
			setReturnResponse(bc, Msg.PAY_8009);
			return false;
		}
		RefundQueryReqDTO request = bc.getRequest();
		if (!StringUtils.equals(request.getOrganMerchantNo(), pm.getOrganMerchantNo())) {
			setReturnResponse(bc, Msg.PAY_8010);
			return false;
		}
		if (transactionService.isValidStatusWithRefund(pm.getPayStatus())) {
			setReturnResponse(bc, Msg.PAY_8004);
			return false;
		}
		return true;
	}
}
