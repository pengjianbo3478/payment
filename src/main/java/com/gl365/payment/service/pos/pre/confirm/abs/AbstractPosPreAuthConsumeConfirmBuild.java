package com.gl365.payment.service.pos.pre.confirm.abs;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.gl365.payment.dto.authconsumeconfirm.request.AuthConsumeConfirmReqDTO;
import com.gl365.payment.dto.authconsumeconfirm.response.AuthConsumeConfirmRespDTO;
import com.gl365.payment.dto.base.BaseContext;
import com.gl365.payment.enums.pay.DcType;
import com.gl365.payment.enums.pay.DealStatus;
import com.gl365.payment.enums.pay.OrderType;
import com.gl365.payment.enums.pay.Scene;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayDetail;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayPrepay;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.remote.dto.account.request.UpdateAccountBalanceOffLineReqDTO;
import com.gl365.payment.util.FormatUtil;
public abstract class AbstractPosPreAuthConsumeConfirmBuild extends AbstractPosPreAuthConsumeConfirmCheck {
	@Override
	public PayStream buildPayStream(BaseContext<AuthConsumeConfirmReqDTO, AuthConsumeConfirmRespDTO> bc) {
		PayStream ps = payAdapter.buildPayStream(bc);
		AuthConsumeConfirmReqDTO reqDTO = bc.getRequest();
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
	public PayMain buildPayMain(BaseContext<AuthConsumeConfirmReqDTO, AuthConsumeConfirmRespDTO> bc, DealStatus dealStatus) {
		PayMain ps = payAdapter.buildPayMainFromPayPrepay(bc.getPayPrepay(), dealStatus);
		ps.setRequestDate(FormatUtil.parseYyyyMMdd(bc.getRequest().getRequestDate()));
		ps.setPrePayId(bc.getRequest().getOrigPayId());
		ps.setPayId(bc.getPayId());
		ps.setTransType(bc.getTranType().getCode());
		ps.setRequestId(bc.getRequest().getRequestId());
		ps.setScene(Scene.POS_PAY.getCode());
		ps.setRewardUserId("");
		ps.setRewardPayId("");
		ps.setOrderType(OrderType.pos.getCode());
		ps.setOrganPayTime(LocalDateTime.now());
		bc.setPayMain(ps);
		return ps;
	}

	@Override
	public List<PayDetail> buildPayDetails(BaseContext<AuthConsumeConfirmReqDTO, AuthConsumeConfirmRespDTO> bc) {
		List<PayDetail> payDetails = payAdapter.buildPayDetailsFromPayMain(bc.getPayMain(), bc.getPayId());
		bc.setPayDetails(payDetails);
		return payDetails;
	}

	@Override
	public AuthConsumeConfirmRespDTO buildReturnResponse(BaseContext<AuthConsumeConfirmReqDTO, AuthConsumeConfirmRespDTO> bc) throws ServiceException {
		PayPrepay pp = bc.getPayPrepay();
		bc.getResponse().setOrganCode(pp.getOrganCode());
		bc.getResponse().setOrganMerchantNo(pp.getOrganMerchantNo());
		bc.getResponse().setTerminal(pp.getTerminal());
		bc.getResponse().setRequestId(bc.getRequest().getRequestId());
		bc.getResponse().setCardIndex(pp.getCardIndex());
		bc.getResponse().setPayId(bc.getPayId());
		bc.getResponse().setTotalMoney(pp.getTotalAmount());
		bc.getResponse().setMarketFee(pp.getMarcketFee());
		bc.getResponse().setCoinAmount(pp.getCoinAmount());
		bc.getResponse().setBeanAmount(pp.getBeanAmount());
		bc.getResponse().setCashMoney(pp.getCashAmount());
		bc.getResponse().setGiftAmount(pp.getGiftAmount());
		bc.getResponse().setGiftPoint(pp.getGiftPoint());
		bc.getResponse().setTxnDate(FormatUtil.formatYyyyMMdd(LocalDateTime.now()));
		bc.getResponse().setPayStatus(DealStatus.ALREADY_PAID.getCode());
		bc.getResponse().setPayDesc(DealStatus.ALREADY_PAID.getDesc());
		return bc.getResponse();
	}

	public UpdateAccountBalanceOffLineReqDTO buildUpdateAccountBalanceOffLineReqDTO(BaseContext<AuthConsumeConfirmReqDTO, AuthConsumeConfirmRespDTO> bc) {
		PayMain pm = bc.getPayMain();
		String code = getOperateType(bc).getCode();
		String origPayId = bc.getPayPrepay().getPayId();
		UpdateAccountBalanceOffLineReqDTO request = payAdapter.buildUpdateAccount(pm, code,origPayId);
		request.setOperateAmount(bc.getRequest().getBeanAmount());
		request.setGiftAmount(bc.getRequest().getGiftAmount());
		request.setDcType(DcType.D.getCode());
		request.setPayId(bc.getPayId());
		bc.setUpdateAccountBalanceOffLineReqDTO(request);
		return request;
	}
}
