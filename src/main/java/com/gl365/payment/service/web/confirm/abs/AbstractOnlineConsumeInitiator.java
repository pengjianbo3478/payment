package com.gl365.payment.service.web.confirm.abs;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.gl365.payment.enums.pay.DcType;
import com.gl365.payment.enums.pay.DealStatus;
import com.gl365.payment.enums.pay.Scene;
import com.gl365.payment.enums.system.Agent;
import com.gl365.payment.enums.system.Flag;
import com.gl365.payment.enums.system.GlSystem;
import com.gl365.payment.enums.system.OrganCode;
import com.gl365.payment.exception.InvalidRequestException;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayDetail;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.remote.dto.account.request.QueryAccountTotalBalanceReqDTO;
import com.gl365.payment.remote.dto.account.request.UpdateAccountBalanceOffLineReqDTO;
import com.gl365.payment.remote.dto.account.response.QueryAccountTotalBalanceRespDTO;
import com.gl365.payment.util.BigDecimalUtil;
import com.gl365.payment.util.CalculationFormula;
import com.gl365.payment.util.FormatUtil;
public abstract class AbstractOnlineConsumeInitiator extends AbstractConsume {
	@Override
	public boolean checkAndSaveInputParameter(BaseContext<OnlineConsumeReqDTO, OnlineConsumeRespDTO> bc) throws InvalidRequestException {
		Map<PaymentConstants.Column, String> strColumns = new HashMap<PaymentConstants.Column, String>();
		OnlineConsumeReqDTO request = bc.getRequest();
		strColumns.put(Column.organCode, request.getOrganCode());
		strColumns.put(Column.requestId, request.getRequestId());
		strColumns.put(Column.requestDate, request.getRequestDate());
		strColumns.put(Column.organMerchantNo, request.getOrganMerchantNo());
		strColumns.put(Column.cardIndex, request.getCardIndex());
		strColumns.put(Column.groupOrderId, request.getGroupOrderId());
		strColumns.put(Column.splitFlag, request.getSplitFlag());
		strColumns.put(Column.groupMerchantNo, request.getGroupMerchantNo());
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
		strLenColumns.put(Column.groupOrderId, request.getGroupOrderId());
		strLenColumns.put(Column.groupMerchantNo, request.getGroupMerchantNo());
		Map<PaymentConstants.Column, BigDecimal> bigDecimalColumns = new HashMap<PaymentConstants.Column, BigDecimal>();
		bigDecimalColumns.put(Column.totalAmount, request.getTotalAmount());
		bigDecimalColumns.put(Column.noBenefitAmount, request.getNoBenefitAmount());
		bigDecimalColumns.put(Column.groupMainUserPay, request.getGroupMainUserPay());
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
	public void beanPay(BaseContext<OnlineConsumeReqDTO, OnlineConsumeRespDTO> bc) {
		// 调帐户系统余额交易(线上)
		QueryAccountTotalBalanceReqDTO reqDTO = new QueryAccountTotalBalanceReqDTO(bc.getGl365UserAccount().getUserId());
		QueryAccountTotalBalanceRespDTO response = this.remoteMicroServiceAgent.queryAccountTotalBalance(reqDTO.getUserId());
		BigDecimal accountTotalBalance = response.getResultData();
		// 计算支付明细,规则：优先使用可用的乐豆 // if 乐豆余额>=(消费金额-1) then
		OnlineConsumeReqDTO request = bc.getRequest();
		BigDecimal c = BigDecimalUtil.subtract(request.getGroupMainUserPay(), ONE);
		boolean greaterOrEqual2 = BigDecimalUtil.GreaterOrEqual(accountTotalBalance, c);
		Finance finance = bc.getFinance();
		if (greaterOrEqual2) {
			BigDecimal totalAmount = request.getGroupMainUserPay();
			BigDecimal beanAmt = BigDecimalUtil.subtract(totalAmount, ONE);
			finance.setBeanAmount(beanAmt);
		}
		else {
			finance.setBeanAmount(accountTotalBalance);
		}
		BigDecimal totalAmount = request.getGroupMainUserPay();
		BigDecimal beanAmount = finance.getBeanAmount();
		BigDecimal cashAmt = BigDecimalUtil.subtract(totalAmount, beanAmount);
		finance.setCashAmount(cashAmt);
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
		payMain.setOrderType(request.getOrderType());
		payMain.setRequestId(request.getRequestId());
		payMain.setMerchantOrderNo(request.getMerchantOrderNo());
		payMain.setMerchantOrderTitle(request.getMerchantOrderTitle());
		payMain.setMerchentOrderDesc(request.getMerchantOrderDesc());
		payMain.setCardIndex(request.getCardIndex());
		payMain.setOrganOrderNo(request.getOrganOrderNo());
		payMain.setGroupOrderId(request.getGroupOrderId());
		payMain.setSplitFlag(request.getSplitFlag());
		payMain.setGroupMainuserPay(request.getGroupMainUserPay());
		payMain.setGroupPtPay(request.getTotalAmount().subtract(request.getGroupMainUserPay()));
		payMain.setGroupMerchantNo(request.getGroupMerchantNo());
		payMain.setIsNotify(Flag.N.getCode());
		payMain.setOrganPayTime(LocalDateTime.now());
		bc.setPayMain(payMain);
		return payMain;
	}
	
	public List<PayDetail> buildPayDetails(BaseContext<OnlineConsumeReqDTO, OnlineConsumeRespDTO> bc) {
		ArrayList<PayDetail> payDetails = new ArrayList<PayDetail>();
		PayMain payMain = bc.getPayMain();
		PayDetail ps = new PayDetail();
		if (StringUtils.isNotEmpty(bc.getPayId())) {
			ps.setPayId(bc.getPayId());
		}
		ps.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
		ps.setCreateTime(LocalDateTime.now());
		ps.setOrganCode(payMain.getOrganCode());
		BigDecimal groupPtPay = payMain.getGroupPtPay();
		BigDecimal groupMainUserPayBean = payMain.getGroupMainuserPayBean();
		BigDecimal groupMianUserPayCash = payMain.getTotalAmount().subtract(groupPtPay).subtract(groupMainUserPayBean);
		if (BigDecimalUtil.GreaterThan0(groupMianUserPayCash)) {
			ps.setPayType(PaymentConstants.PayType.PAY_CASH.getCode());
			ps.setPayAccount(payMain.getUserId());
			ps.setPayAmount(groupMianUserPayCash);
			payDetails.add(ps);
		}
		if (BigDecimalUtil.GreaterThan0(groupMainUserPayBean)) {
			PayDetail ps2 = ps.clone();
			ps2.setPayType(PaymentConstants.PayType.PAY_BEAN.getCode());
			ps2.setPayAccount(payMain.getUserId());
			ps2.setPayAmount(groupMainUserPayBean);
			payDetails.add(ps2);
		}
		if (BigDecimalUtil.GreaterThan0(groupPtPay)) {
			PayDetail ps3 = ps.clone();
			ps3.setPayType(PaymentConstants.PayType.PAY_BEAN.getCode());
			ps3.setPayAccount("1000001900343");
			ps3.setPayAmount(groupPtPay);
			payDetails.add(ps3);
		}
		
		bc.setPayDetails(payDetails);
		return payDetails;
	}
	
	public UpdateAccountBalanceOffLineReqDTO buildUpdateAccountBalanceOffLineReqDTO(BaseContext<OnlineConsumeReqDTO, OnlineConsumeRespDTO> bc) {
		UpdateAccountBalanceOffLineReqDTO request = new UpdateAccountBalanceOffLineReqDTO();
		request.setUserId(bc.getGl365User().getUserId());
		request.setOrganCode(OrganCode.GL.getCode());
		request.setPayId(bc.getPayId());
		request.setMerchantNo(bc.getGl365Merchant().getMerchantNo());
		request.setMerchantName(bc.getGl365Merchant().getMerchantShortname());
		request.setMerchantOrderNo(bc.getRequest().getMerchantOrderNo());
		request.setOperateType(bc.getTranType().getCode());
		// 支付乐豆
		request.setOperateAmount(bc.getPayMain().getGroupMainuserPayBean());
		// 返利乐豆
		request.setGiftAmount(bc.getPayMain().getGroupGiftAmount());
		request.setDcType(DcType.D.getCode());
		request.setScene(bc.getPayMain().getScene());
		request.setAgentId(Agent.GL365.getKey());
		bc.setUpdateAccountBalanceOffLineReqDTO(request);
		return request;
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
		bc.setMerchantNo(bc.getRequest().getGroupMerchantNo()); // change TODO
		bc.setGl365Merchant(this.remoteMicroServiceAgent.queryMerchantInfo(bc));
		Finance finance = new Finance();
		// 计算返利
		OnlineConsumeReqDTO request = bc.getRequest();
		BigDecimal totalAmount = request.getTotalAmount();
		BigDecimal noBenefitAmount = BigDecimal.ZERO;
		BigDecimal saleRate = bc.getGl365Merchant().getSaleRate();
		BigDecimal calcGiftAmount = CalculationFormula.calcGiftAmount(totalAmount, noBenefitAmount, saleRate);
		finance.setGiftAmount(calcGiftAmount);
		bc.setFinance(finance);
	}
}
