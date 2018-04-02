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
import com.gl365.payment.remote.dto.account.request.CancelOperateReqDTO;
import com.gl365.payment.remote.dto.settlement.request.ConfirmPreSettleDateReqDTO;
import com.gl365.payment.remote.dto.settlement.response.ConfirmPreSettleDateRespDTO;
import com.gl365.payment.service.dbservice.TranDbService;
@Service
public class RefundDBService extends TranDbService {
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public boolean firstCommit(SuperContext bc) throws ServiceException {
		return false;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public boolean secondCommit(SuperContext bc) throws ServiceException {
		payReturnMapper.insert(bc.getPayReturn());
		// 写付款明细
		List<PayDetail> payDetails = bc.getPayDetails();
		for (PayDetail pd : payDetails) {
			payDetailMapper.insert(pd);
		}
		// 调用account系统服务
		CancelOperateReqDTO req = bc.getCancelOperateReqDTO();
		if (isNeedOperateBeanAmount(req.getOperateAmount(), req.getGiftAmount())) {
			remoteMicroServiceAgent.cancelOperate(req);
		}
		ConfirmPreSettleDateReqDTO request = new ConfirmPreSettleDateReqDTO();
		request.setOrganCode(OrganCode.FFT.getCode());
		request.setPayId(bc.getPayReturn().getPayId());
		request.setTransType("2");
		request.setOrganPayTime(bc.getPayReturn().getPayTime());
		request.setOrigOrganPayTime(bc.getPayMain().getPayTime());
		request.setOrigPreSettleDate(bc.getPayMain().getPreSettleDate());
		ConfirmPreSettleDateRespDTO result = remoteMicroServiceAgent.getConfirmPreSettleDate(request);
		LocalDate preSettleDate = result.getData().getPreSettleDate();
		// 更新主付款表
		PayMain payMain = bc.getPayMain();
		payMain.setPreSettleDate(preSettleDate);
		payMainMapper.updateStatusByPayId(payMain);
		return true;
	}
}
