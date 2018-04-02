package com.gl365.payment.service.dbservice.impl;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.dto.base.SuperContext;
import com.gl365.payment.enums.system.OrganCode;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayDetail;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayPrepay;
import com.gl365.payment.remote.dto.account.request.UpdateAccountBalanceOffLineReqDTO;
import com.gl365.payment.remote.dto.settlement.request.ConfirmPreSettleDateReqDTO;
import com.gl365.payment.remote.dto.settlement.response.ConfirmPreSettleDateRespDTO;
import com.gl365.payment.service.dbservice.TranDbService;
@Service
public class ConsumeDBService extends TranDbService {
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public boolean firstCommit(SuperContext bc) throws ServiceException {
		// 写交易主表
		PayMain payMain = bc.getPayMain();
		payMainMapper.insert(payMain);
		// 写付款表
		List<PayDetail> payDetails = bc.getPayDetails();
		for (PayDetail pd : payDetails) {
			payDetailMapper.insert(pd);
		}
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public boolean secondCommit(SuperContext bc) throws ServiceException {
		// 调用account系统服务
		UpdateAccountBalanceOffLineReqDTO req = bc.getUpdateAccountBalanceOffLineReqDTO();
		if (isNeedOperateBeanAmount(req.getOperateAmount(), req.getGiftAmount())) {
			remoteMicroServiceAgent.UpdateAccountBalanceOffLine(req);
		}
		ConfirmPreSettleDateReqDTO request = new ConfirmPreSettleDateReqDTO();
		request.setOrganCode(OrganCode.FFT.getCode());
		request.setPayId(bc.getPayMain().getPayId());
		request.setTransType("1");
		request.setOrganPayTime(bc.getPayMain().getPayTime());
		ConfirmPreSettleDateRespDTO result = remoteMicroServiceAgent.getConfirmPreSettleDate(request);
		LocalDate preSettleDate = result.getData().getPreSettleDate();
		// 更新交易主表
		PayMain payResult = bc.getPayMain();
		payResult.setPreSettleDate(preSettleDate);
		payMainMapper.updateStatusByPayId(payResult);
		// 更新支付交易查询表状态
		PayPrepay payPrepayResult = bc.getPayPrepay();
		payPrepayMapper.updateStatusByPayId(payPrepayResult);
		return true;
	}
}
