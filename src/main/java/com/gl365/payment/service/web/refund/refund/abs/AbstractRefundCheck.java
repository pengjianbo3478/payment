package com.gl365.payment.service.web.refund.refund.abs;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.gl365.payment.common.constants.PaymentConstants;
import com.gl365.payment.common.constants.PaymentConstants.Column;
import com.gl365.payment.dto.base.BaseContext;
import com.gl365.payment.dto.refund.request.RefundReqDTO;
import com.gl365.payment.dto.refund.response.RefundRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.exception.InvalidRequestException;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.service.transaction.AbstractTranscation;
public abstract class AbstractRefundCheck extends AbstractTranscation<RefundReqDTO, RefundRespDTO> {
	@Override
	public boolean checkAndSaveInputParameter(BaseContext<RefundReqDTO, RefundRespDTO> bc) throws InvalidRequestException {
		Map<PaymentConstants.Column, String> strColumns = new HashMap<PaymentConstants.Column, String>();
		RefundReqDTO request = bc.getRequest();
		strColumns.put(Column.organCode, request.getOrganCode());
		strColumns.put(Column.requestId, request.getRequestId());
		strColumns.put(Column.requestDate, request.getRequestDate());
		strColumns.put(Column.organMerchantNo, request.getOrganMerchantNo());
		strColumns.put(Column.cardIndex, request.getCardIndex());
		strColumns.put(Column.origPayId, request.getOrigPayId());
		strColumns.put(Column.origTxnDate, request.getOrigTxnDate());
		// strColumns.put(Column.payChannel, request.getPayChannel());
		Map<PaymentConstants.Column, String> strLenColumns = new HashMap<PaymentConstants.Column, String>();
		strLenColumns.put(Column.organCode, request.getOrganCode());
		strLenColumns.put(Column.requestId, request.getRequestId());
		strLenColumns.put(Column.requestDate, request.getRequestDate());
		strLenColumns.put(Column.organMerchantNo, request.getOrganMerchantNo());
		strLenColumns.put(Column.terminal, request.getTerminal());
		strLenColumns.put(Column.operator, request.getOperator());
		strLenColumns.put(Column.cardIndex, request.getCardIndex());
		strLenColumns.put(Column.origPayId, request.getOrigPayId());
		strLenColumns.put(Column.origTxnDate, request.getOrigTxnDate());
		strLenColumns.put(Column.payChannel, request.getPayChannel());
		Map<PaymentConstants.Column, BigDecimal> map = new HashMap<PaymentConstants.Column, BigDecimal>();
		map.put(Column.totalAmount, request.getTotalAmount());
		boolean b = !checkStringNPT(bc, strColumns);
		boolean c = !checkStringLen(bc, strLenColumns);
		boolean a = !checkBigDecimalNPT(bc, map);
		if (b || c || a) return false;
		bc.setCardIndex(request.getCardIndex());
		bc.setMerchantNo(bc.getRequest().getOrganMerchantNo());
		return true;
	}

	@Override
	public boolean bizCheck(BaseContext<RefundReqDTO, RefundRespDTO> bc) throws ServiceException {
		PayMain pm = bc.getPayMain();
		if (pm == null) {
			setReturnResponse(bc, Msg.PAY_8012);
			return false;
		}
		if (!StringUtils.equals(bc.getCardIndex(), pm.getCardIndex())) {
			setReturnResponse(bc, Msg.PAY_8009);
			return false;
		}
		RefundReqDTO request = bc.getRequest();
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
