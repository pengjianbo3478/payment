package com.gl365.payment.service.wz.common;
import com.gl365.payment.remote.dto.account.Gl365UserAccount;
import com.gl365.payment.remote.dto.account.request.BeanTransferReqDTO;
import com.gl365.payment.remote.dto.account.request.CancelOperateReqDTO;
import com.gl365.payment.remote.dto.account.request.UpdateAccountBalanceOffLineReqDTO;
import com.gl365.payment.remote.dto.account.response.BeanTransferRespDTO;
import com.gl365.payment.remote.dto.account.response.QueryAccountBalanceInfoRespDTO;
import com.gl365.payment.remote.dto.account.response.UpdateAccountBalanceOffLineRespDTO;
public interface Gl365UserAccountService {
	
	QueryAccountBalanceInfoRespDTO queryGl365AccountBalance(String userId, String agentId);

	Gl365UserAccount check(QueryAccountBalanceInfoRespDTO resp);

	boolean isBindCard(String userId);

	UpdateAccountBalanceOffLineRespDTO UpdateAccountBalanceOffLine(UpdateAccountBalanceOffLineReqDTO request);

	String wxCancelOperate(CancelOperateReqDTO request);

	BeanTransferRespDTO beanTransfer(BeanTransferReqDTO request);
}
