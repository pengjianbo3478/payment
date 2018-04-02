package com.gl365.payment.service.pos.reverse.abs;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;
import com.gl365.payment.dto.rollback.RollbackContext;
import com.gl365.payment.dto.rollback.request.RollbackReqDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.exception.InvalidRequestException;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayMain;
public abstract class AbstractConsumeReverseCheck extends AbstractReverseTran {
	public void checkPayMianStatus(RollbackContext ctx) {
		PayMain payMain = ctx.getPayMain();
		String payStatus = payMain.getPayStatus();
		boolean cp = StringUtils.equals(payStatus, PayStatus.COMPLETE_PAY.getCode());
		boolean wp = StringUtils.equals(payStatus, PayStatus.WAIT_PAY.getCode());
		if (!cp && !wp) throw new ServiceException(Msg.PAY_8004.getCode(), Msg.PAY_8004.getDesc());
	}

	public void checkReqeust(RollbackReqDTO request) {
		String origRequestId = request.getOrigRequestId();
		boolean e = StringUtils.isEmpty(origRequestId);
		if (e) throw new InvalidRequestException(Msg.REQ_0022.getCode(), Msg.REQ_0022.getDesc());
		boolean res = origRequestId.length() > 32;
		if (res) throw new InvalidRequestException(Msg.REQ_0022.getCode(), Msg.REQ_0022.getDesc());
		LocalDate origTxnDate = request.getOrigTxnDate();
		if (origTxnDate == null) throw new InvalidRequestException(Msg.REQ_0023.getCode(), Msg.REQ_0023.getDesc());
	}
}
