package com.gl365.payment.service.pos.pre.reverse.abs;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;
import com.gl365.payment.dto.rollback.RollbackContext;
import com.gl365.payment.dto.rollback.request.RollbackReqDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.exception.InvalidRequestException;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.service.pos.reverse.abs.AbstractReverseTran;
public abstract class AbstractPosPreAuthReverseCheck extends AbstractReverseTran {
	@Override
	public void checkReqeust(RollbackReqDTO request) {
		String origRequestId = request.getOrigRequestId();
		boolean ep = StringUtils.isEmpty(origRequestId);
		if (ep) throw new InvalidRequestException(Msg.REQ_0024.getCode(), Msg.REQ_0024.getDesc());
		LocalDate otd = request.getOrigTxnDate();
		if (otd == null) throw new InvalidRequestException(Msg.REQ_0025.getCode(), Msg.REQ_0025.getDesc());
	}

	@Override
	public void checkPayMianStatus(RollbackContext ctx) {
		PayMain payMain = ctx.getPayMain();
		String payStatus = payMain.getPayStatus();
		boolean cp = StringUtils.equals(payStatus, PayStatus.COMPLETE_PAY.getCode());
		if (!cp) throw new ServiceException(Msg.PAY_8004.getCode(), Msg.PAY_8004.getDesc());
	}

	@Override
	public void checkTransType(RollbackContext ctx) {
		PayMain payMain = ctx.getPayMain();
		String transType = payMain.getTransType();
		boolean b = StringUtils.equals(transType, TranType.PRE_AUTH_CONSUME_CONFIRM.getCode());
		if (!b) throw new ServiceException(Msg.PAY_8020.getCode(), Msg.PAY_8020.getDesc());
	}

	@Override
	public void queryPayMian(RollbackContext ctx) {
		RollbackReqDTO request = ctx.getRollbackReqDTO();
		String requestId = request.getOrigRequestId();
		String terminal = request.getTerminal();
		ctx.setRequestId(requestId);
		ctx.setTerminal(terminal);
		PayMain payMain = this.payContextService.queryPayMainByRequestId(ctx);
		if (payMain == null) throw new ServiceException(Msg.PAY_8012.getCode(), Msg.PAY_8012.getDesc());
		ctx.setPayMain(payMain);
	}
}
