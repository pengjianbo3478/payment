package com.gl365.payment.service.check.impl;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.pay.SplitFlag;
import com.gl365.payment.exception.InvalidRequestException;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.service.check.CheckPayRequestService;
import com.gl365.payment.util.RegExUtil;
@Service
public class CheckPayRequestServiceImpl implements CheckPayRequestService {
	@Override
	public void checkOrganCode(String organCode) throws ServiceException {
		boolean b = StringUtils.isEmpty(organCode) || organCode.length() > 15;
		if (b) throw new InvalidRequestException(Msg.REQ_0015.getCode(), Msg.REQ_0015.getDesc());
	}

	@Override
	public void checkRequestId(String requestId) {
		boolean b = StringUtils.isEmpty(requestId) || requestId.length() > 32 || !RegExUtil.isLetterAndNum(requestId);
		if (b) throw new InvalidRequestException(Msg.REQ_0016.getCode(), Msg.REQ_0016.getDesc());
	}

	@Override
	public void checkOrganMerchantNo(String organMerchantNo) {
		boolean b = StringUtils.isEmpty(organMerchantNo) || organMerchantNo.length() > 15;
		if (b) throw new InvalidRequestException(Msg.REQ_0018.getCode(), Msg.REQ_0018.getDesc());
	}

	@Override
	public void checkTerminal(String terminal) {
		boolean b = StringUtils.isNotEmpty(terminal) && terminal.length() > 20;
		if (b) throw new ServiceException(Msg.REQ_0019.getCode(), Msg.REQ_0019.getDesc());
	}

	@Override
	public void checkCardIndex(String cardIndex) {
		boolean b = StringUtils.isEmpty(cardIndex) || cardIndex.length() > 32;
		if (b) throw new InvalidRequestException(Msg.REQ_0020.getCode(), Msg.REQ_0020.getDesc());
	}

	@Override
	public void checkTotalAmount(BigDecimal totalAmount) {
		if (totalAmount == null) throw new InvalidRequestException(Msg.REQ_0021.getCode(), Msg.REQ_0021.getDesc());
		int b = totalAmount.compareTo(BigDecimal.ZERO);
		if (b != 1) throw new InvalidRequestException(Msg.REQ_0021.getCode(), Msg.REQ_0021.getDesc());
	}

	@Override
	public void checkRequestDate(LocalDate requestDate) {
		if (requestDate == null) throw new InvalidRequestException(Msg.REQ_0017.getCode(), Msg.REQ_0017.getDesc());
	}

	@Override
	public void checkScene(String scene) {
		boolean bl = StringUtils.isNotEmpty(scene) && scene.length() > 2;
		if (bl) throw new InvalidRequestException(Msg.REQ_0027.getCode(), Msg.REQ_0027.getDesc());
	}

	@Override
	public void checkMerchantOrderTitle(String mt) {
		boolean bl = StringUtils.isNotEmpty(mt) && mt.length() > 128;
		if (bl) throw new InvalidRequestException(Msg.REQ_0029.getCode(), Msg.REQ_0029.getDesc());
	}

	@Override
	public void checkMerchantOrderDesc(String md) {
		boolean bl = StringUtils.isNotEmpty(md) && md.length() > 512;
		if (bl) throw new InvalidRequestException(Msg.REQ_0030.getCode(), Msg.REQ_0030.getDesc());
	}

	@Override
	public void checkOperator(String opr) {
		boolean bl = StringUtils.isNotEmpty(opr) && opr.length() > 64;
		if (bl) throw new InvalidRequestException(Msg.REQ_0031.getCode(), Msg.REQ_0031.getDesc());
	}

	@Override
	public void checkNoBenefitAmount(BigDecimal val) {
		if (val == null) throw new InvalidRequestException(Msg.REQ_0032.getCode(), Msg.REQ_0032.getDesc());
	}

	@Override
	public void checkMerchantNo(String merchantNo) {
		boolean b = StringUtils.isEmpty(merchantNo) || merchantNo.length() > 15;
		if (b) throw new InvalidRequestException(Msg.REQ_0036.getCode(), Msg.REQ_0036.getDesc());
	}

	@Override
	public void checkUserId(String userId) {
		boolean b = StringUtils.isEmpty(userId) || userId.length() > 32;
		if (b) throw new InvalidRequestException(Msg.REQ_0037.getCode(), Msg.REQ_0037.getDesc());
	}

	@Override
	public void checkRewardUserId(String userId, String rewardUserId) {
		boolean b = StringUtils.equals(userId, rewardUserId);
		if (b) throw new InvalidRequestException(Msg.REQ_0052.getCode(), Msg.REQ_0052.getDesc());
	}

	@Override
	public void checkMerchantOrderNo(String merchantOrderNo) {
		boolean b = StringUtils.isEmpty(merchantOrderNo) || merchantOrderNo.length() > 32;
		if (b) throw new InvalidRequestException(Msg.REQ_0038.getCode(), Msg.REQ_0038.getDesc());
	}

	@Override
	public void checkOrderType(String orderType) {
		boolean b = StringUtils.isEmpty(orderType) || orderType.length() > 1;
		if (b) throw new InvalidRequestException(Msg.REQ_0039.getCode(), Msg.REQ_0039.getDesc());
	}

	@Override
	public void checkOrganOrderNo(String organOrderNo) {
		boolean b = StringUtils.isEmpty(organOrderNo) || organOrderNo.length() > 32;
		if (b) throw new InvalidRequestException(Msg.REQ_0040.getCode(), Msg.REQ_0040.getDesc());
	}

	@Override
	public void checkTransactionId(String transactionId) {
		boolean b = StringUtils.isEmpty(transactionId) || transactionId.length() > 32;
		if (b) throw new InvalidRequestException(Msg.REQ_0041.getCode(), Msg.REQ_0041.getDesc());
	}

	@Override
	public void checkCashAmount(BigDecimal cashAmount) {
		if (cashAmount == null) throw new InvalidRequestException(Msg.REQ_0042.getCode(), Msg.REQ_0042.getDesc());
		int b = cashAmount.compareTo(BigDecimal.ZERO);
		if (b != 1) throw new InvalidRequestException(Msg.REQ_0042.getCode(), Msg.REQ_0021.getDesc());
	}

	@Override
	public void checkPayResult(String payResult) {
		boolean b = StringUtils.isEmpty(payResult) || payResult.length() > 1;
		if (b) throw new InvalidRequestException(Msg.REQ_0043.getCode(), Msg.REQ_0043.getDesc());
	}

	@Override
	public void checkPayTime(LocalDateTime payTime) {
		if (payTime == null) throw new InvalidRequestException(Msg.REQ_0044.getCode(), Msg.REQ_0044.getDesc());
	}

	@Override
	public void checkPayDesc(String payDesc) {
	}

	@Override
	public void checkDecAmount(BigDecimal decAmount) {
	}

	@Override
	public void checkDecResult(String decResult) {
	}

	@Override
	public void checkBankType(String bankType) {
	}

	@Override
	public void checkPayLdMoney(BigDecimal val) {
		if (val == null) throw new InvalidRequestException(Msg.REQ_0045.getCode(), Msg.REQ_0045.getDesc());
	}

	@Override
	public void checkRewardPayId(String payId) {
		boolean b = StringUtils.isEmpty(payId) || payId.length() > 32;
		if (b) throw new InvalidRequestException(Msg.REQ_0046.getCode(), Msg.REQ_0046.getDesc());
	}

	@Override
	public void checkGroupOrderId(String groupOrderId) {
		boolean b = StringUtils.isEmpty(groupOrderId) || groupOrderId.length() > 32;
		if (b) throw new InvalidRequestException(Msg.REQ_0047.getCode(), Msg.REQ_0047.getDesc());
	}

	@Override
	public void checkGroupMainuserPay(BigDecimal groupMainuserPay) {
		if (groupMainuserPay == null) throw new InvalidRequestException(Msg.REQ_0050.getCode(), Msg.REQ_0050.getDesc());
	}

	@Override
	public void checkSplitFlag(String splitFlag) {
		boolean b = StringUtils.equals(SplitFlag.childOrder.getCode(), splitFlag);
		if (!b) throw new InvalidRequestException(Msg.REQ_0048.getCode(), Msg.REQ_0048.getDesc());
	}

	@Override
	public void checkGroupMerchantNo(String groupMerchantNo) {
		boolean b = StringUtils.isEmpty(groupMerchantNo) || groupMerchantNo.length() > 32;
		if (b) throw new InvalidRequestException(Msg.REQ_0049.getCode(), Msg.REQ_0049.getDesc());
	}
}
