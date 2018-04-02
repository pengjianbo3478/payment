package com.gl365.payment.service.web.confirm.abs;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.common.Finance;
import com.gl365.payment.common.constants.PaymentConstants;
import com.gl365.payment.common.constants.PaymentConstants.Column;
import com.gl365.payment.dto.base.BaseContext;
import com.gl365.payment.dto.onlineconsume.request.OnlineConsumeReqDTO;
import com.gl365.payment.dto.onlineconsume.response.OnlineConsumeRespDTO;
import com.gl365.payment.enums.pay.DealStatus;
import com.gl365.payment.enums.pay.OrderType;
import com.gl365.payment.enums.pay.Scene;
import com.gl365.payment.enums.system.Flag;
import com.gl365.payment.exception.InvalidRequestException;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.util.CalculationFormula;
import com.gl365.payment.util.FormatUtil;
public abstract class AbstractOnlineConsume extends AbstractConsume {
	@Override
	public boolean checkAndSaveInputParameter(BaseContext<OnlineConsumeReqDTO, OnlineConsumeRespDTO> bc) throws InvalidRequestException {
		Map<PaymentConstants.Column, String> strColumns = new HashMap<PaymentConstants.Column, String>();
		OnlineConsumeReqDTO request = bc.getRequest();
		strColumns.put(Column.organCode, request.getOrganCode());
		strColumns.put(Column.requestId, request.getRequestId());
		strColumns.put(Column.requestDate, request.getRequestDate());
		strColumns.put(Column.organMerchantNo, request.getOrganMerchantNo());
		strColumns.put(Column.cardIndex, request.getCardIndex());
		Map<PaymentConstants.Column, String> strLenColumns = new HashMap<PaymentConstants.Column, String>();
		strLenColumns.put(Column.organCode, request.getOrganCode());
		strLenColumns.put(Column.requestId, request.getRequestId());
		strLenColumns.put(Column.requestDate, request.getRequestDate());
		strLenColumns.put(Column.organMerchantNo, request.getOrganMerchantNo());
		strLenColumns.put(Column.operator, request.getOperator());
		strLenColumns.put(Column.cardIndex, request.getCardIndex());
		strLenColumns.put(Column.merchantOrderTitle, request.getMerchantOrderTitle());
		strLenColumns.put(Column.merchantOrderDesc, request.getMerchantOrderDesc());
		strLenColumns.put(Column.merchantOrderNo, request.getMerchantOrderNo());
		strLenColumns.put(Column.organOrderNo, request.getOrganOrderNo());
		Map<PaymentConstants.Column, BigDecimal> bigDecimalColumns = new HashMap<PaymentConstants.Column, BigDecimal>();
		bigDecimalColumns.put(Column.totalAmount, request.getTotalAmount());
		bigDecimalColumns.put(Column.noBenefitAmount, request.getNoBenefitAmount());
		boolean b = !checkStringNPT(bc, strColumns);
		boolean c = !checkStringLen(bc, strLenColumns);
		boolean c2 = !checkBigDecimalNPT(bc, bigDecimalColumns);
		boolean d = !checkMerchantOrderDesc(bc, request.getMerchantOrderDesc());
		if (b || c || c2 || d) return false;
		bc.setCardIndex(request.getCardIndex());
		bc.setOrganCode(bc.getRequest().getOrganCode());
		bc.setMerchantNo(bc.getRequest().getOrganMerchantNo());
		this.splitOrderDesc(request);
		return true;
	}

	@Override
	public PayMain buildPayMain(BaseContext<OnlineConsumeReqDTO, OnlineConsumeRespDTO> bc, DealStatus dealStatus) {
		PayMain payMain = payAdapter.buildPayMain(bc, dealStatus);
		payMain = payAdapter.setPayMain(payMain, bc.getGl365UserAccount());
		payMain = payAdapter.setPayMain(payMain, bc.getGl365User());
		String cardType = bc.getGl365UserAccount().getCardType();
		payMain = payAdapter.setPayMain(payMain, bc.getGl365Merchant(), getDcType(cardType), Scene.FAST_PAY);
		payMain = payAdapter.setPayMain(payMain, bc.getFinance());
		OnlineConsumeReqDTO request = bc.getRequest();
		payMain.setRequestDate(FormatUtil.parseYyyyMMdd(request.getRequestDate()));
		payMain.setOrganCode(request.getOrganCode());
		payMain.setOrganMerchantNo(request.getOrganMerchantNo());
		payMain.setTerminal(request.getTerminal());
		payMain.setOperator(request.getOperator());
		payMain.setTotalAmount(request.getTotalAmount());
		payMain.setNoBenefitAmount(request.getNoBenefitAmount());
		String scene = bc.getRequest().getScene();
		payMain.setScene(StringUtils.defaultString(scene, Scene.FAST_PAY.getCode()));
		payMain.setRewardUserId("");
		payMain.setRewardPayId("");
		payMain.setOrderType(OrderType.pos.getCode());
		payMain.setRequestId(request.getRequestId());
		payMain.setMerchantOrderNo(request.getMerchantOrderNo());
		payMain.setMerchantOrderTitle(request.getMerchantOrderTitle());
		payMain.setMerchentOrderDesc(request.getMerchantOrderDesc());
		payMain.setCardIndex(request.getCardIndex());
		payMain.setOrganOrderNo(request.getOrganOrderNo());
		payMain.setIsNotify(Flag.N.getCode());
		payMain.setOrganPayTime(LocalDateTime.now());
		bc.setPayMain(payMain);
		return payMain;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public void query(BaseContext<OnlineConsumeReqDTO, OnlineConsumeRespDTO> bc) throws ServiceException {
		// 通过卡索引号查询绑卡表，得到用户SN、绑卡状态/
		bc.setGl365UserAccount(this.remoteMicroServiceAgent.queryAccount(bc));
		this.remoteMicroServiceAgent.isBindCard(bc);
		// 通过用户SN查询用户表，取出用户所属发展机构、乐豆支付总开关 用户状态是否正常
		bc.setGl365User(this.remoteMicroServiceAgent.queryUserInfo(bc));
		// 通过付费通商户号查询给乐商户号 查询商户信息 商户状态是否正常
		bc.setGl365Merchant(this.remoteMicroServiceAgent.queryMerchantInfo(bc));
		Finance finance = new Finance();
		// finance.setTotalAmount(bc.getRequest().getTotalAmount());
		// 计算返利
		OnlineConsumeReqDTO request = bc.getRequest();
		BigDecimal totalAmount = request.getTotalAmount();
		BigDecimal noBenefitAmount = request.getNoBenefitAmount();
		BigDecimal saleRate = bc.getGl365Merchant().getSaleRate();
		BigDecimal calcGiftAmount = CalculationFormula.calcGiftAmount(totalAmount, noBenefitAmount, saleRate);
		finance.setGiftAmount(calcGiftAmount);
		bc.setFinance(finance);
	}
}
