package com.gl365.payment.service.pos.confirm.abs;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import com.gl365.payment.dto.base.BaseContext;
import com.gl365.payment.dto.consumeconfirm.request.ConsumeConfirmReqDTO;
import com.gl365.payment.dto.consumeconfirm.response.ConsumeConfirmRespDTO;
import com.gl365.payment.enums.pay.DcType;
import com.gl365.payment.enums.pay.DealStatus;
import com.gl365.payment.enums.pay.OrderType;
import com.gl365.payment.enums.pay.Scene;
import com.gl365.payment.enums.system.Flag;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayDetail;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayPrepay;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.remote.dto.account.request.UpdateAccountBalanceOffLineReqDTO;
import com.gl365.payment.service.transaction.AbstractTranscation;
import com.gl365.payment.util.FormatUtil;
public abstract class AbstractConsumeConfirmBuild extends AbstractTranscation<ConsumeConfirmReqDTO, ConsumeConfirmRespDTO> {
	@Override
	public PayStream buildPayStream(BaseContext<ConsumeConfirmReqDTO, ConsumeConfirmRespDTO> bc) {
		PayStream ps = payAdapter.buildPayStream(bc);
		ConsumeConfirmReqDTO reqDTO = bc.getRequest();
		ps.setRequestId(reqDTO.getRequestId());
		ps.setRequestDate(FormatUtil.parseYyyyMMdd(reqDTO.getRequestDate()));
		ps.setOrganCode(reqDTO.getOrganCode());
		ps.setOrganMerchantNo(reqDTO.getOrganMerchantNo());
		ps.setTerminal(reqDTO.getTerminal());
		ps.setTransType(bc.getTranType().getCode());
		ps.setTotalAmount(reqDTO.getTotalAmount());
		ps.setReturnAmount(BigDecimal.ZERO);
		ps.setUniqueSerial(FormatUtil.mergeString(bc.getRequest().getTerminal(), bc.getRequest().getRequestId()));
		bc.setPayStream(ps);
		return ps;
	}

	@Override
	public PayMain buildPayMain(BaseContext<ConsumeConfirmReqDTO, ConsumeConfirmRespDTO> bc, DealStatus dealStatus) {
		PayMain pm = payAdapter.buildPayMainFromPayPrepay(bc.getPayPrepay(), dealStatus);
		pm.setRequestDate(FormatUtil.parseYyyyMMdd(bc.getRequest().getRequestDate()));
		pm.setPrePayId(bc.getRequest().getOrigPayId());
		pm.setPayId(bc.getPayId());
		pm.setScene(Scene.POS_PAY.getCode());
		pm.setRewardUserId("");
		pm.setRewardPayId("");
		pm.setOrderType(OrderType.pos.getCode());
		pm.setTransType(bc.getTranType().getCode());
		pm.setRequestId(bc.getRequest().getRequestId());
		pm.setIsNotify(Flag.N.getCode());
		pm.setOrganPayTime(LocalDateTime.now());
		bc.setPayMain(pm);
		return pm;
	}

	@Override
	public List<PayDetail> buildPayDetails(BaseContext<ConsumeConfirmReqDTO, ConsumeConfirmRespDTO> bc) {
		List<PayDetail> payDetails = payAdapter.buildPayDetailsFromPayMain(bc.getPayMain(), bc.getPayId());
		bc.setPayDetails(payDetails);
		return payDetails;
	}

	@Override
	public ConsumeConfirmRespDTO buildReturnResponse(BaseContext<ConsumeConfirmReqDTO, ConsumeConfirmRespDTO> bc) throws ServiceException {
		ConsumeConfirmReqDTO request = bc.getRequest();
		ConsumeConfirmRespDTO response = bc.getResponse();
		response.setOrganCode(request.getOrganCode());
		response.setOrganMerchantNo(request.getOrganMerchantNo());
		response.setTerminal(request.getTerminal());
		response.setRequestId(request.getRequestId());
		response.setCardIndex(request.getCardIndex());
		response.setPayId(bc.getPayId());
		response.setTotalMoney(request.getTotalAmount());
		response.setMarketFee(request.getMarketFee().setScale(2, RoundingMode.HALF_UP));
		response.setCoinAmount(request.getCoinAmount());
		PayPrepay pp = bc.getPayPrepay();
		response.setBeanAmount(pp.getBeanAmount());
		response.setCashMoney(pp.getCashAmount());
		response.setGiftAmount(request.getGiftAmount());
		response.setGiftPoint(request.getGiftPoint());
		response.setTxnDate(FormatUtil.formatYyyyMMdd(LocalDateTime.now()));
		response.setPayStatus(DealStatus.ALREADY_PAID.getCode());
		response.setPayDesc(DealStatus.ALREADY_PAID.getDesc());
		return bc.getResponse();
	}

	public UpdateAccountBalanceOffLineReqDTO buildUpdateAccountBalanceOffLineReqDTO(BaseContext<ConsumeConfirmReqDTO, ConsumeConfirmRespDTO> bc) {
		String oc = getOperateType(bc).getCode();
		String origPayId = bc.getPayPrepay().getPayId();
		UpdateAccountBalanceOffLineReqDTO request = payAdapter.buildUpdateAccount(bc.getPayMain(), oc,origPayId);
		request.setGiftAmount(bc.getRequest().getGiftAmount());
		request.setDcType(DcType.D.getCode());
		request.setPayId(bc.getPayId());
		bc.setUpdateAccountBalanceOffLineReqDTO(request);
		return request;
	}
}
