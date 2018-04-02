package com.gl365.payment.service.pos.confirm.abs;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.gl365.payment.common.constants.PaymentConstants;
import com.gl365.payment.common.constants.PaymentConstants.Column;
import com.gl365.payment.dto.base.BaseContext;
import com.gl365.payment.dto.consumeconfirm.request.ConsumeConfirmReqDTO;
import com.gl365.payment.dto.consumeconfirm.response.ConsumeConfirmRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.exception.InvalidRequestException;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayPrepay;
import com.gl365.payment.util.BigDecimalUtil;
import com.gl365.payment.util.FormatUtil;
public abstract class AbstractConsumeConfirmCheck extends AbstractConsumeConfirmBuild {
	@Override
	public boolean checkAndSaveInputParameter(BaseContext<ConsumeConfirmReqDTO, ConsumeConfirmRespDTO> bc) throws InvalidRequestException {
		Map<PaymentConstants.Column, String> strColumns = new HashMap<PaymentConstants.Column, String>();
		strColumns.put(Column.organCode, bc.getRequest().getOrganCode());
		strColumns.put(Column.requestId, bc.getRequest().getRequestId());
		strColumns.put(Column.requestDate, bc.getRequest().getRequestDate());
		strColumns.put(Column.organMerchantNo, bc.getRequest().getOrganMerchantNo());
		strColumns.put(Column.terminal, bc.getRequest().getTerminal());
		strColumns.put(Column.cardIndex, bc.getRequest().getCardIndex());
		strColumns.put(Column.origPayId, bc.getRequest().getOrigPayId());
		Map<PaymentConstants.Column, String> strLenColumns = new HashMap<PaymentConstants.Column, String>();
		strLenColumns.put(Column.organCode, bc.getRequest().getOrganCode());
		strLenColumns.put(Column.requestId, bc.getRequest().getRequestId());
		strLenColumns.put(Column.requestDate, bc.getRequest().getRequestDate());
		strLenColumns.put(Column.organMerchantNo, bc.getRequest().getOrganMerchantNo());
		strLenColumns.put(Column.terminal, bc.getRequest().getTerminal());
		strLenColumns.put(Column.cardIndex, bc.getRequest().getCardIndex());
		strLenColumns.put(Column.origPayId, bc.getRequest().getOrigPayId());
		Map<PaymentConstants.Column, BigDecimal> bigDecimalColumns = new HashMap<PaymentConstants.Column, BigDecimal>();
		bigDecimalColumns.put(Column.totalAmount, bc.getRequest().getTotalAmount());
		bigDecimalColumns.put(Column.noBenefitAmount, bc.getRequest().getNoBenefitAmount());
		bigDecimalColumns.put(Column.giftPoint, bc.getRequest().getGiftPoint());
		bigDecimalColumns.put(Column.marketFee, bc.getRequest().getMarketFee());
		bigDecimalColumns.put(Column.coinAmount, bc.getRequest().getCoinAmount());
		bigDecimalColumns.put(Column.beanAmount, bc.getRequest().getBeanAmount());
		bigDecimalColumns.put(Column.cashMoney, bc.getRequest().getCashMoney());
		bigDecimalColumns.put(Column.giftAmount, bc.getRequest().getGiftAmount());
		if (!checkStringNPT(bc, strColumns) || !checkStringLen(bc, strLenColumns) || !checkBigDecimalNPT(bc, bigDecimalColumns)) return false;
		bc.setCardIndex(bc.getRequest().getCardIndex());
		return true;
	}

	@Override
	public boolean bizCheck(BaseContext<ConsumeConfirmReqDTO, ConsumeConfirmRespDTO> bc) throws ServiceException {
		// 通过查询号查询POS预交易表，结果不存在
		if (bc.getPayPrepay() == null) {
			setReturnResponse(bc, Msg.PAY_8012);
			return false;
		}
		// 用户卡索引
		if (!StringUtils.equals(bc.getCardIndex(), bc.getPayPrepay().getCardIndex())) {
			setReturnResponse(bc, Msg.PAY_8009);
			return false;
		}
		// 商户号
		if (!StringUtils.equals(bc.getRequest().getOrganMerchantNo(), bc.getPayPrepay().getOrganMerchantNo())) {
			setReturnResponse(bc, Msg.PAY_8010);
			return false;
		}
		// 终端号
		if (!StringUtils.equals(bc.getRequest().getTerminal(), bc.getPayPrepay().getTerminal())) {
			setReturnResponse(bc, Msg.REQ_0026);
			return false;
		}
		// 原单状态是否为“已交易”
		if (transactionService.isValidStatusWithTranCommit(bc.getPayPrepay().getPayStatus())) {
			setReturnResponse(bc, Msg.PAY_8004);
			return false;
		}
		// 是否超过2分钟
		if (FormatUtil.greaterThan(bc.getPayPrepay().getCreateTime(), 2)) {
			setReturnResponse(bc, Msg.PAY_8013);
			return false;
		}
		// 交易金额是否一致
		if (checkAmount(bc)) {
			setReturnResponse(bc, Msg.PAY_8014);
			return false;
		}
		return true;
	}

	private boolean checkAmount(BaseContext<ConsumeConfirmReqDTO, ConsumeConfirmRespDTO> bc) {
		PayPrepay pp = bc.getPayPrepay();
		ConsumeConfirmReqDTO request = bc.getRequest();
		boolean a = !BigDecimalUtil.equals(pp.getTotalAmount(), request.getTotalAmount());
		boolean b = !BigDecimalUtil.equals(pp.getNoBenefitAmount(), request.getNoBenefitAmount());
		// boolean c = !BigDecimalUtil.equals(pp.getCashAmount(),
		// request.getCashMoney());
		boolean d = !BigDecimalUtil.equals(pp.getMarcketFee(), request.getMarketFee());
		boolean e = !BigDecimalUtil.equals(pp.getCoinAmount(), request.getCoinAmount());
		boolean f = !BigDecimalUtil.equals(pp.getBeanAmount(), request.getBeanAmount());
		boolean g = !BigDecimalUtil.equals(pp.getGiftAmount(), request.getGiftAmount());
		boolean j = !BigDecimalUtil.equals(pp.getGiftPoint(), request.getGiftPoint());
		return a || b || d || e || f || g || j;
	}
}
