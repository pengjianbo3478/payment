package com.gl365.payment.service.check;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
public interface CheckPayRequestService {
	void checkOrganCode(String organCode);

	void checkRequestId(String requestId);

	void checkRequestDate(LocalDate requestDate);

	void checkOrganMerchantNo(String organMerchantNo);

	void checkTerminal(String terminal);

	void checkCardIndex(String cardIndex);

	void checkTotalAmount(BigDecimal totalAmount);

	void checkScene(String scene);

	void checkMerchantOrderTitle(String mt);

	void checkMerchantOrderDesc(String md);

	void checkOperator(String opr);

	void checkNoBenefitAmount(BigDecimal noBenefitAmount);

	void checkMerchantNo(String merchantNo);

	void checkUserId(String userId);

	void checkRewardUserId(String userId, String rewardUserId);

	void checkMerchantOrderNo(String merchantOrderNo);

	void checkOrderType(String orderType);

	void checkOrganOrderNo(String organOrderNo);

	void checkTransactionId(String transactionId);

	void checkCashAmount(BigDecimal cashAmount);

	void checkPayResult(String payResult);

	void checkPayDesc(String payDesc);

	void checkDecAmount(BigDecimal decAmount);

	void checkDecResult(String decResult);

	void checkBankType(String bankType);

	void checkPayTime(LocalDateTime payTime);

	void checkPayLdMoney(BigDecimal val);

	void checkRewardPayId(String payId);

	void checkGroupOrderId(String groupOrderId);

	void checkGroupMainuserPay(BigDecimal groupMainuserPay);

	void checkSplitFlag(String splitFlag);

	void checkGroupMerchantNo(String groupMerchantNo);
}
