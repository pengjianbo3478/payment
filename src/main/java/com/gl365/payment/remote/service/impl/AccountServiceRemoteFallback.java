package com.gl365.payment.remote.service.impl;
import org.springframework.stereotype.Component;

import com.gl365.payment.dto.base.CommonDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.exception.RemoteServiceException;
import com.gl365.payment.remote.dto.account.request.BeanTransferReqDTO;
import com.gl365.payment.remote.dto.account.request.BeanTransferReverseReqDTO;
import com.gl365.payment.remote.dto.account.request.CancelOperateReqDTO;
import com.gl365.payment.remote.dto.account.request.DeductBalanceReqDto;
import com.gl365.payment.remote.dto.account.request.HasNormalBindInfoReqDTO;
import com.gl365.payment.remote.dto.account.request.QueryAccountBalanceInfoReqDTO;
import com.gl365.payment.remote.dto.account.request.QueryAccountReqDTO;
import com.gl365.payment.remote.dto.account.request.ReverseOperateReqDTO;
import com.gl365.payment.remote.dto.account.request.UpdateAccountBalanceOffLineReqDTO;
import com.gl365.payment.remote.dto.account.response.BeanTransferRespDTO;
import com.gl365.payment.remote.dto.account.response.BeanTransferReverseRespDTO;
import com.gl365.payment.remote.dto.account.response.CancelOperateRespDTO;
import com.gl365.payment.remote.dto.account.response.HasNormalBindInfoRespDTO;
import com.gl365.payment.remote.dto.account.response.QueryAccountBalanceInfoRespDTO;
import com.gl365.payment.remote.dto.account.response.QueryAccountRespDTO;
import com.gl365.payment.remote.dto.account.response.QueryAccountTotalBalanceRespDTO;
import com.gl365.payment.remote.dto.account.response.ReverseOperateRespDTO;
import com.gl365.payment.remote.dto.account.response.UpdateAccountBalanceOffLineRespDTO;
import com.gl365.payment.remote.service.AccountServiceRemote;
@Component
public class AccountServiceRemoteFallback implements AccountServiceRemote {
	public QueryAccountRespDTO queryAccount(QueryAccountReqDTO reqDTO) {
		throw new RemoteServiceException(Msg.ACCOUNT_SERVICE_ERROR.getCode(), Msg.ACCOUNT_SERVICE_ERROR.getDesc());
	}

	public HasNormalBindInfoRespDTO hasNormalBindInfo(HasNormalBindInfoReqDTO reqDTO) {
		throw new RemoteServiceException(Msg.ACCOUNT_SERVICE_ERROR.getCode(), Msg.ACCOUNT_SERVICE_ERROR.getDesc());
	}

	public QueryAccountBalanceInfoRespDTO queryAccountBalanceInfo(QueryAccountBalanceInfoReqDTO reqDTO) {
		throw new RemoteServiceException(Msg.ACCOUNT_SERVICE_ERROR.getCode(), Msg.ACCOUNT_SERVICE_ERROR.getDesc());
	}

	public CommonDTO deductBalance(DeductBalanceReqDto reqDto) {
		throw new RemoteServiceException(Msg.ACCOUNT_SERVICE_ERROR.getCode(), Msg.ACCOUNT_SERVICE_ERROR.getDesc());
	}

	public UpdateAccountBalanceOffLineRespDTO updateAccountBalanceOffLine(UpdateAccountBalanceOffLineReqDTO reqDTO) {
		throw new RemoteServiceException(Msg.ACCOUNT_SERVICE_ERROR.getCode(), Msg.ACCOUNT_SERVICE_ERROR.getDesc());
	}

	public QueryAccountTotalBalanceRespDTO queryAccountTotalBalance(String userId) {
		throw new RemoteServiceException(Msg.ACCOUNT_SERVICE_ERROR.getCode(), Msg.ACCOUNT_SERVICE_ERROR.getDesc());
	}

	public ReverseOperateRespDTO reverseOperate(ReverseOperateReqDTO reqDTO) {
		throw new RemoteServiceException(Msg.ACCOUNT_SERVICE_ERROR.getCode(), Msg.ACCOUNT_SERVICE_ERROR.getDesc());
	}

	@Override
	public CancelOperateRespDTO cancelOperate(CancelOperateReqDTO cancelOperateReqDTO) {
		throw new RemoteServiceException(Msg.ACCOUNT_SERVICE_ERROR.getCode(), Msg.ACCOUNT_SERVICE_ERROR.getDesc());
	}

	@Override
	public BeanTransferRespDTO beanTransfer(BeanTransferReqDTO beanTransferReqDTO) {
		throw new RemoteServiceException(Msg.ACCOUNT_SERVICE_ERROR.getCode(), Msg.ACCOUNT_SERVICE_ERROR.getDesc());
	}
	
	@Override
	public BeanTransferReverseRespDTO beanTransfeReverseOperate(BeanTransferReverseReqDTO beanTransferReverseReqDTO) {
		throw new RemoteServiceException(Msg.ACCOUNT_SERVICE_ERROR.getCode(), Msg.ACCOUNT_SERVICE_ERROR.getDesc());
	}
}
