package com.gl365.payment.service.wz.common.impl;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.system.Agent;
import com.gl365.payment.enums.system.ResultCode;
import com.gl365.payment.enums.user.AccountResultCode;
import com.gl365.payment.exception.AccountRespException;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.remote.dto.account.Gl365UserAccount;
import com.gl365.payment.remote.dto.account.request.BeanTransferReqDTO;
import com.gl365.payment.remote.dto.account.request.CancelOperateReqDTO;
import com.gl365.payment.remote.dto.account.request.HasNormalBindInfoReqDTO;
import com.gl365.payment.remote.dto.account.request.QueryAccountBalanceInfoReqDTO;
import com.gl365.payment.remote.dto.account.request.UpdateAccountBalanceOffLineReqDTO;
import com.gl365.payment.remote.dto.account.response.BeanTransferRespDTO;
import com.gl365.payment.remote.dto.account.response.CancelOperateRespDTO;
import com.gl365.payment.remote.dto.account.response.HasNormalBindInfoRespDTO;
import com.gl365.payment.remote.dto.account.response.QueryAccountBalanceInfoRespDTO;
import com.gl365.payment.remote.dto.account.response.UpdateAccountBalanceOffLineRespDTO;
import com.gl365.payment.remote.service.AccountServiceRemote;
import com.gl365.payment.service.check.CheckGl365UserAccountService;
import com.gl365.payment.service.wz.common.Gl365UserAccountService;
import com.gl365.payment.util.Gl365StrUtils;
import com.gl365.payment.util.gson.GsonUtils;
@Service
public class Gl365UserAccountServiceImpl implements Gl365UserAccountService {
	private static final Logger LOG = LoggerFactory.getLogger(Gl365UserAccountServiceImpl.class);
	@Autowired
	private AccountServiceRemote accountServiceRemote;
	@Autowired
	private CheckGl365UserAccountService gl365UserAccountService;

	@Override
	public QueryAccountBalanceInfoRespDTO queryGl365AccountBalance(String userId, String agentId) {
		QueryAccountBalanceInfoReqDTO request = new QueryAccountBalanceInfoReqDTO(userId, Agent.GL365.getKey());
		LOG.info("查询账户余额请求参数JSON={}", GsonUtils.toJson(request));
		QueryAccountBalanceInfoRespDTO response = this.accountServiceRemote.queryAccountBalanceInfo(request);
		LOG.info("查询账户余额返回结果JSON={}", GsonUtils.toJson(response));
		if (response == null) throw new AccountRespException(Msg.REMOTE_ACC_4000.getCode(), Msg.REMOTE_ACC_4000.getDesc());
		return response;
	}

	@Override
	public Gl365UserAccount check(QueryAccountBalanceInfoRespDTO resp) {
		Gl365UserAccount gl365UserAccount = new Gl365UserAccount();
		BeanUtils.copyProperties(resp, gl365UserAccount);
		this.gl365UserAccountService.check(gl365UserAccount);
		return gl365UserAccount;
	}

	@Override
	public boolean isBindCard(String userId) {
		HasNormalBindInfoReqDTO request = new HasNormalBindInfoReqDTO();
		request.setUserId(userId);
		LOG.info("查询是否绑卡请求参数={},JSON={}", Gl365StrUtils.toStr(request), GsonUtils.toJson(request));
		HasNormalBindInfoRespDTO response = this.accountServiceRemote.hasNormalBindInfo(request);
		LOG.info("查询是否绑卡返回结果={}", Gl365StrUtils.toStr(response));
		if (response == null) throw new AccountRespException(Msg.REMOTE_ACC_4000.getCode(), Msg.REMOTE_ACC_4000.getDesc());
		String flag = response.getFlag();
		if (StringUtils.isEmpty(flag)) throw new AccountRespException(Msg.REMOTE_ACC_4002.getCode(), Msg.REMOTE_ACC_4002.getDesc());
		boolean result = BooleanUtils.toBoolean(flag);
		if (!result) throw new AccountRespException(Msg.PAY_8003.getCode(), Msg.PAY_8003.getDesc());
		return result;
	}

	@Override
	public UpdateAccountBalanceOffLineRespDTO UpdateAccountBalanceOffLine(UpdateAccountBalanceOffLineReqDTO request) {
		LOG.info("更新账户余额请求参数JSON={}", GsonUtils.toJson(request));
		UpdateAccountBalanceOffLineRespDTO result = this.accountServiceRemote.updateAccountBalanceOffLine(request);
		LOG.info("更新账户余额返回结果JSON={}", GsonUtils.toJson(result));
		if (result == null) throw new AccountRespException(Msg.REMOTE_ACC_4000.getCode(), Msg.REMOTE_ACC_4000.getDesc());
		String resultCode = result.getResultCode();
		boolean s = StringUtils.equals(resultCode, ResultCode.SUCCESS.getCode());
		boolean u = StringUtils.equals(resultCode, ResultCode.BALANCE_UNENOUGH.getCode());
		if (!s && !u) throw new ServiceException(result.getResultCode(), result.getResultDesc());
		return result;
	}

	@Override
	public String wxCancelOperate(CancelOperateReqDTO request) {
		LOG.info("调用撤销接口请求参数={}", GsonUtils.toJson(request));
		CancelOperateRespDTO result = this.accountServiceRemote.cancelOperate(request);
		LOG.info("调用撤销接口返回结果={}", GsonUtils.toJson(result));
		if (result == null) throw new AccountRespException(Msg.REMOTE_ACC_4000.getCode(), Msg.REMOTE_ACC_4000.getDesc());
		String resultCode = result.getResultCode();
		boolean b = StringUtils.equals(resultCode, AccountResultCode.SUCCESS.getCode()) || StringUtils.equals(resultCode, AccountResultCode.BALANCE_UNENOUGH.getCode());
		if (!b) throw new ServiceException(result.getResultCode(), result.getResultDesc());
		return resultCode;
	}

	@Override
	public BeanTransferRespDTO beanTransfer(BeanTransferReqDTO request) {
		LOG.info("调用乐豆转账请求参数={},JSON={}", Gl365StrUtils.toMultiLineStr(request), GsonUtils.toJson(request));
		BeanTransferRespDTO result = this.accountServiceRemote.beanTransfer(request);
		LOG.info("调用乐豆转账返回结果={}", Gl365StrUtils.toMultiLineStr(result));
		if (result == null) throw new AccountRespException(Msg.REMOTE_ACC_4000.getCode(), Msg.REMOTE_ACC_4000.getDesc());
		String resultCode = result.getResultCode();
		if (!StringUtils.equals(resultCode, AccountResultCode.SUCCESS.getCode())) throw new ServiceException(resultCode, result.getResultDesc());
		return result;
	}
}
