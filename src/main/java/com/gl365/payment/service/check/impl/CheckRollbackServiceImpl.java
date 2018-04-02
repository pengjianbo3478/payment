package com.gl365.payment.service.check.impl;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.gl365.payment.dto.rollback.RollbackContext;
import com.gl365.payment.dto.rollback.request.RollbackReqDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.service.check.CheckRollbackService;
@Service
public class CheckRollbackServiceImpl implements CheckRollbackService {
	private static final Logger LOG = LoggerFactory.getLogger(CheckRollbackServiceImpl.class);

	public void checkPaymentDate(RollbackContext ctx) {
		RollbackReqDTO request = ctx.getRollbackReqDTO();
		LocalDate requestDate = request.getRequestDate();
		LocalDate payTime = ctx.getPayMain().getRequestDate();
		if (payTime == null) throw new ServiceException(Msg.PAY_8015.getCode(), Msg.PAY_8015.getDesc());
		boolean res = requestDate.isEqual(payTime);
		if (!res) throw new ServiceException(Msg.PAY_8005.getCode(), Msg.PAY_8005.getDesc());
		LOG.info("检查交易日期是否一致={}", res);
	}

	public void checkPayMianOrganMerchantNo(RollbackContext ctx) {
		RollbackReqDTO reqDTO = ctx.getRollbackReqDTO();
		PayMain payMain = ctx.getPayMain();
		String sourceNo = payMain.getOrganMerchantNo();
		String targetNo = reqDTO.getOrganMerchantNo();
		boolean b = StringUtils.equals(sourceNo, targetNo);
		if (!b) throw new ServiceException(Msg.PAY_8010.getCode(), Msg.PAY_8010.getDesc());
		LOG.info("检查商户号是否一致={}", b);
	}

	public void checkPayMianCardIndex(RollbackContext ctx) {
		RollbackReqDTO reqDTO = ctx.getRollbackReqDTO();
		PayMain payMain = ctx.getPayMain();
		String sourceNo = payMain.getCardIndex();
		String targetNo = reqDTO.getCardIndex();
		boolean b = StringUtils.equals(sourceNo, targetNo);
		if (!b) throw new ServiceException(Msg.PAY_8009.getCode(), Msg.PAY_8009.getDesc());
		LOG.info("检查卡索引号是否一致={}", b);
	}

	public void checkTotalAmount(RollbackContext ctx) {
		RollbackReqDTO reqDTO = ctx.getRollbackReqDTO();
		BigDecimal s = reqDTO.getTotalAmount();
		PayMain payMain = ctx.getPayMain();
		BigDecimal t = payMain.getTotalAmount();
		int i = s.compareTo(t);
		if (i != 0) throw new ServiceException(Msg.PAY_8014.getCode(), Msg.PAY_8014.getDesc());
		LOG.info("检查交易金额是否一致={}", i);
	}

	public void checkTerminal(RollbackContext ctx) {
		RollbackReqDTO reqDTO = ctx.getRollbackReqDTO();
		//网上消费冲正终端号可为空
		String reqTerminal = StringUtils.defaultString(reqDTO.getTerminal(), StringUtils.EMPTY) ;
		PayMain payMain = ctx.getPayMain();
		String oldTerminal = payMain.getTerminal();
		boolean b = StringUtils.equals(reqTerminal, oldTerminal);
		if (!b) throw new ServiceException(Msg.PAY_8019.getCode(), Msg.PAY_8019.getDesc());
		LOG.info("检查终端号是否一致={}", b);
	}
}
