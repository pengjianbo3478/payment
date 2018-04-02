package com.gl365.payment.service.web.reward.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gl365.payment.dto.base.BaseContext;
import com.gl365.payment.dto.onlineconsume.request.OnlineRewardBeanConsumeReqDTO;
import com.gl365.payment.dto.onlineconsume.response.OnlineRewardBeanConsumeRespDTO;
import com.gl365.payment.enums.pay.DealStatus;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.service.dbservice.impl.OnlineConsumeDBService;
import com.gl365.payment.service.web.reward.OnlineRewardBeanConsumeService;
import com.gl365.payment.service.web.reward.abs.AbstractOnlineRewardBeanConsume;
@Service
public class OnlineRewardBeanConsumeServiceImpl extends AbstractOnlineRewardBeanConsume implements OnlineRewardBeanConsumeService {
	@Autowired
	private OnlineConsumeDBService onlineConsumeDBService;

	@Override
	public OnlineRewardBeanConsumeRespDTO onlineRewardBeanConsume(OnlineRewardBeanConsumeReqDTO reqDTO, OnlineRewardBeanConsumeRespDTO respDTO) {
		this.buildReqDTO(reqDTO);
		return this.service(reqDTO, respDTO);
	}

	@Override
	public void setTranType(BaseContext<OnlineRewardBeanConsumeReqDTO, OnlineRewardBeanConsumeRespDTO> bc) {
		bc.setTranType(TranType.ONLINE_CONSUME);
	}

	@Override
	public boolean bizCheck(BaseContext<OnlineRewardBeanConsumeReqDTO, OnlineRewardBeanConsumeRespDTO> bc) throws ServiceException {
		return true;
	}

	@Override
	public boolean firstCommit(BaseContext<OnlineRewardBeanConsumeReqDTO, OnlineRewardBeanConsumeRespDTO> bc) throws ServiceException {
		this.buildPayMain(bc, DealStatus.ALREADY_PAID);
		this.buildPayDetails(bc);
		this.onlineConsumeDBService.firstCommit(bc);
		return true;
	}

	@Override
	public boolean secondCommit(BaseContext<OnlineRewardBeanConsumeReqDTO, OnlineRewardBeanConsumeRespDTO> bc) throws ServiceException {
		return true;
	}
	
	@Override
	public boolean isSendPaymentResultMQ() {
		return false;
	}

	@Override
	public boolean isSendRewardResultMQ() {
		return true;
	}
}
