package com.gl365.payment.service.pos.abs;
import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;
import com.gl365.payment.dto.rollback.RollbackContext;
import com.gl365.payment.dto.rollback.request.RollbackReqDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayMain;
public abstract class AbstractRollbackCheck extends AbstractRollbackMQ {
	public void checkRequestParams(RollbackReqDTO request) {
		this.checkPayRequestService.checkOrganCode(request.getOrganCode());
		this.checkPayRequestService.checkRequestId(request.getRequestId());
		this.checkPayRequestService.checkRequestDate(request.getRequestDate());
		this.checkPayRequestService.checkOrganMerchantNo(request.getOrganMerchantNo());
		this.checkPayRequestService.checkCardIndex(request.getCardIndex());
		this.checkPayRequestService.checkTotalAmount(request.getTotalAmount());
		this.checkReqeust(request);
	}

	public void checkPayMian(RollbackContext ctx) {
		PayMain payMain = ctx.getPayMain();
		if (payMain == null) throw new ServiceException(Msg.PAY_8012.getCode(), Msg.PAY_8012.getDesc());
		this.checkPayMianStatus(ctx);
		this.checkTransType(ctx);
		this.checkRollbackService.checkPayMianOrganMerchantNo(ctx);
		this.checkRollbackService.checkPayMianCardIndex(ctx);
		//this.checkRollbackService.checkTotalAmount(ctx);
		this.checkRollbackService.checkTerminal(ctx);
	}

	public void checkCompletePay(RollbackContext ctx) {
		PayMain payMain = ctx.getPayMain();
		String payStatus = payMain.getPayStatus();
		boolean cp = StringUtils.equals(payStatus, PayStatus.COMPLETE_PAY.getCode());
		if (!cp) throw new ServiceException(Msg.PAY_8004.getCode(), Msg.PAY_8004.getDesc());
	}

	public boolean isUpdateAccountBalance(RollbackContext ctx) {
		PayMain payMain = ctx.getPayMain();
		BigDecimal beanAmt = payMain.getBeanAmount();
		BigDecimal giftAmt = payMain.getGiftAmount();
		int a = beanAmt.compareTo(BigDecimal.ZERO);
		int b = giftAmt.compareTo(BigDecimal.ZERO);
		if (a == 1 || b == 1) return true;
		return false;
	}
}
