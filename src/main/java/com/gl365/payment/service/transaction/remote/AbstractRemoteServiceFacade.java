package com.gl365.payment.service.transaction.remote;
import java.time.LocalDate;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.gl365.payment.dto.base.PayContext;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.system.ResultCode;
import com.gl365.payment.exception.AccountRespException;
import com.gl365.payment.exception.MemberRespException;
import com.gl365.payment.exception.MerchantRespException;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.remote.dto.account.Gl365UserAccount;
import com.gl365.payment.remote.dto.account.request.BeanTransferReqDTO;
import com.gl365.payment.remote.dto.account.request.BeanTransferReverseReqDTO;
import com.gl365.payment.remote.dto.account.request.CancelOperateReqDTO;
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
import com.gl365.payment.remote.dto.account.response.ReverseOperateRespDTO;
import com.gl365.payment.remote.dto.account.response.UpdateAccountBalanceOffLineRespDTO;
import com.gl365.payment.remote.dto.member.Gl365User;
import com.gl365.payment.remote.dto.member.request.QueryUserInfoReqDTO;
import com.gl365.payment.remote.dto.member.response.QueryUserInfoRespDTO;
import com.gl365.payment.remote.dto.merchant.Gl365Merchant;
import com.gl365.payment.remote.dto.merchant.request.QueryMerchantInfoReqDTO;
import com.gl365.payment.remote.dto.merchant.response.QueryMerchantInfoRespDTO;
import com.gl365.payment.remote.dto.merchant.response.QueryMerchantInfoRespDTOBody;
import com.gl365.payment.remote.dto.settlement.request.ConfirmPreSettleDateReqDTO;
import com.gl365.payment.remote.dto.settlement.response.ConfirmPreSettleDateRespDTO;
import com.gl365.payment.remote.dto.settlement.response.ConfirmPreSettleDateRespDataDTO;
import com.gl365.payment.remote.service.AccountServiceRemote;
import com.gl365.payment.remote.service.MemberServiceRemote;
import com.gl365.payment.remote.service.MerchantServiceRemote;
import com.gl365.payment.remote.service.SettlementServiceRemote;
import com.gl365.payment.service.check.CheckGl365MerchantService;
import com.gl365.payment.service.check.CheckGl365UserAccountService;
import com.gl365.payment.service.check.CheckGl365UserService;
import com.gl365.payment.service.transaction.context.PayContextService;
import com.gl365.payment.util.Gl365StrUtils;
import com.gl365.payment.util.gson.GsonUtils;
public abstract class AbstractRemoteServiceFacade implements PayContextService {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractRemoteServiceFacade.class);
	@Autowired
	protected AccountServiceRemote accountServiceRemote;
	@Autowired
	private MemberServiceRemote memberServiceRemote;
	@Autowired
	private MerchantServiceRemote merchantServiceRemote;
	@Autowired
	private CheckGl365MerchantService checkGl365MerchantService;
	@Autowired
	private CheckGl365UserService checkGl365UserService;
	@Autowired
	private CheckGl365UserAccountService gl365UserAccountService;
	@Autowired
	private SettlementServiceRemote settlementServiceRemote;
	private static final String RESULT_CODE = "000000";

	public Gl365UserAccount queryAccount(PayContext item) throws ServiceException {
		String cardIndex = item.getCardIndex();
		String organCode = item.getOrganCode();
		QueryAccountReqDTO request = new QueryAccountReqDTO(cardIndex, organCode);
		LOG.info("查询账户信息请求参数={},JSON={}", Gl365StrUtils.toStr(request), GsonUtils.toJson(request));
		QueryAccountRespDTO resp = this.accountServiceRemote.queryAccount(request);
		LOG.info("查询账户信息返回结果={}", Gl365StrUtils.toMultiLineStr(resp));
		if (resp == null) throw new AccountRespException(Msg.REMOTE_ACC_4000.getCode(), Msg.REMOTE_ACC_4000.getDesc());
		Gl365UserAccount gl365UserAccount = new Gl365UserAccount();
		BeanUtils.copyProperties(resp, gl365UserAccount);
		this.gl365UserAccountService.check(gl365UserAccount);
		item.setGl365UserAccount(gl365UserAccount);
		return gl365UserAccount;
	}

	public boolean isBindCard(PayContext item) throws ServiceException {
		HasNormalBindInfoReqDTO request = new HasNormalBindInfoReqDTO();
		String userId = item.getGl365UserAccount().getUserId();
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

	public Gl365User queryUserInfo(PayContext item) throws ServiceException {
		QueryUserInfoReqDTO request = new QueryUserInfoReqDTO();
		String userId = item.getGl365UserAccount().getUserId();
		request.setUserId(userId);
		LOG.info("查询用户信息请求参数={},JSON={}", Gl365StrUtils.toStr(request), GsonUtils.toJson(request));
		QueryUserInfoRespDTO resp = this.memberServiceRemote.queryUserInfo(request);
		LOG.info("查询用户信息返回结果={}", Gl365StrUtils.toMultiLineStr(resp));
		if (resp == null) throw new MemberRespException(Msg.REMOTE_MEM_3000.getCode(), Msg.REMOTE_MEM_3000.getDesc());
		Gl365User gl365User = new Gl365User();
		BeanUtils.copyProperties(resp, gl365User);
		this.checkGl365UserService.check(gl365User);
		return gl365User;
	}

	public Gl365User queryUserInfoByNewUserId(PayContext item) throws ServiceException {
		QueryUserInfoReqDTO request = new QueryUserInfoReqDTO();
		String userId = item.getGl365UserAccount().getUserId();
		request.setUserId(userId);
		LOG.info("查询用户信息请求参数={}", GsonUtils.toJson(request));
		QueryUserInfoRespDTO resp = this.memberServiceRemote.queryUserInfoByNewUserId(request);
		LOG.info("查询用户信息返回结果={}", GsonUtils.toJson(resp));
		if (resp == null) throw new MemberRespException(Msg.REMOTE_MEM_3000.getCode(), Msg.REMOTE_MEM_3000.getDesc());
		Gl365User gl365User = new Gl365User();
		BeanUtils.copyProperties(resp, gl365User);
		this.checkGl365UserService.check(gl365User);
		return gl365User;
	}

	public Gl365Merchant queryMerchantInfo(PayContext item) throws ServiceException {
		String organMerchantNo = item.getMerchantNo();
		String organCode = item.getOrganCode();
		QueryMerchantInfoReqDTO request = new QueryMerchantInfoReqDTO(organCode, organMerchantNo);
		LOG.info("查询商户信息请求参数={}", GsonUtils.toJson(request));
		QueryMerchantInfoRespDTO resp = this.merchantServiceRemote.queryMerchantInfo(request);
		LOG.info("查询商户信息返回结果={}", GsonUtils.toJson(resp));
		if (resp == null) throw new MerchantRespException(Msg.REMOTE_MER_2000.getCode(), Msg.REMOTE_MER_2000.getDesc());
		String result = resp.getResult();
		boolean b = !StringUtils.equals(result, RESULT_CODE);
		if (b) throw new MerchantRespException(Msg.REMOTE_MER_2000.getCode(), Msg.REMOTE_MER_2000.getDesc());
		QueryMerchantInfoRespDTOBody body = resp.getData();
		LOG.info("查询商户信息返回结果详情={}", Gl365StrUtils.toMultiLineStr(body));
		if (body == null) throw new MerchantRespException(Msg.REMOTE_MER_2000.getCode(), Msg.REMOTE_MER_2000.getDesc());
		Gl365Merchant gl365Merchant = new Gl365Merchant();
		BeanUtils.copyProperties(body, gl365Merchant);
		this.checkGl365MerchantService.check(gl365Merchant);
		return gl365Merchant;
	}

	@Override
	public UpdateAccountBalanceOffLineRespDTO UpdateAccountBalanceOffLine(UpdateAccountBalanceOffLineReqDTO request) throws ServiceException {
		LOG.info("更新账户余额请求参数={},JSON={}", Gl365StrUtils.toStr(request), GsonUtils.toJson(request));
		UpdateAccountBalanceOffLineRespDTO result = this.accountServiceRemote.updateAccountBalanceOffLine(request);
		LOG.info("更新账户余额返回结果={}", GsonUtils.toJson(result));
		if (result == null) throw new AccountRespException(Msg.REMOTE_ACC_4000.getCode(), Msg.REMOTE_ACC_4000.getDesc());
		String resultCode = result.getResultCode();
		if (!StringUtils.equals(resultCode, RESULT_CODE)) throw new ServiceException(result.getResultCode(), result.getResultDesc());
		return result;
	}

	@Override
	public QueryAccountBalanceInfoRespDTO queryAccountBalanceInfo(QueryAccountBalanceInfoReqDTO request) throws ServiceException {
		LOG.info("查询账户余额请求参数={},JSON={}", Gl365StrUtils.toStr(request), GsonUtils.toJson(request));
		QueryAccountBalanceInfoRespDTO response = this.accountServiceRemote.queryAccountBalanceInfo(request);
		LOG.info("查询账户余额返回结果={}", Gl365StrUtils.toMultiLineStr(response));
		if (response == null) throw new AccountRespException(Msg.REMOTE_ACC_4000.getCode(), Msg.REMOTE_ACC_4000.getDesc());
		return response;
	}

	@Override
	public ReverseOperateRespDTO reverseOperate(ReverseOperateReqDTO request) {
		LOG.info("调用冲正接口请求参数={},JSON={}", Gl365StrUtils.toStr(request), GsonUtils.toJson(request));
		ReverseOperateRespDTO result = this.accountServiceRemote.reverseOperate(request);
		LOG.info("调用冲正接口返回结果={}", Gl365StrUtils.toMultiLineStr(result));
		if (result == null) throw new AccountRespException(Msg.REMOTE_ACC_4000.getCode(), Msg.REMOTE_ACC_4000.getDesc());
		String resultCode = result.getResultCode();
		if (!StringUtils.equals(resultCode, RESULT_CODE)) throw new ServiceException(result.getResultCode(), result.getResultDesc());
		return result;
	}

	@Override
	public CancelOperateRespDTO cancelOperate(CancelOperateReqDTO request) throws ServiceException {
		LOG.info("调用撤销接口请求参数={},JSON={}", Gl365StrUtils.toStr(request), GsonUtils.toJson(request));
		CancelOperateRespDTO result = this.accountServiceRemote.cancelOperate(request);
		LOG.info("调用撤销接口返回结果={}", Gl365StrUtils.toMultiLineStr(result));
		if (result == null) throw new AccountRespException(Msg.REMOTE_ACC_4000.getCode(), Msg.REMOTE_ACC_4000.getDesc());
		String resultCode = result.getResultCode();
		if (!StringUtils.equals(resultCode, RESULT_CODE)) throw new ServiceException(result.getResultCode(), result.getResultDesc());
		return result;
	}

	@Override
	public BeanTransferRespDTO beanTransfer(BeanTransferReqDTO request) {
		LOG.info("调用乐豆转账请求参数={}",  GsonUtils.toJson(request));
		BeanTransferRespDTO result = this.accountServiceRemote.beanTransfer(request);
		LOG.info("调用乐豆转账返回结果={}", GsonUtils.toJson(result));
		if (result == null) throw new AccountRespException(Msg.REMOTE_ACC_4000.getCode(), Msg.REMOTE_ACC_4000.getDesc());
		String resultCode = result.getResultCode();
		if (!StringUtils.equals(resultCode, RESULT_CODE)) throw new ServiceException(resultCode, result.getResultDesc());
		return result;
	}

	@Override
	public BeanTransferReverseRespDTO beanTransfeReverseOperate(BeanTransferReverseReqDTO request) {
		LOG.info("调用乐豆转账冲正请求参数={}", GsonUtils.toJson(request));
		BeanTransferReverseRespDTO result = this.accountServiceRemote.beanTransfeReverseOperate(request);
		LOG.info("调用乐豆转账冲正返回结果={}", GsonUtils.toJson(result));
		if (result == null) throw new AccountRespException(Msg.REMOTE_ACC_4000.getCode(), Msg.REMOTE_ACC_4000.getDesc());
		String resultCode = result.getResultCode();
		if (!StringUtils.equals(resultCode, RESULT_CODE)) throw new ServiceException(resultCode, result.getResultDesc());
		return result;
	}
	
	@Override
	public ConfirmPreSettleDateRespDTO getConfirmPreSettleDate(ConfirmPreSettleDateReqDTO request) {
		LOG.info("获取确认待清算日期请求参数={}", GsonUtils.toJson(request));
		ConfirmPreSettleDateRespDTO result = this.settlementServiceRemote.confirmPreSettleDate(request);
		LOG.info("获取确认待清算日期返回结果={}", GsonUtils.toJson(result));
		if (result == null) throw new AccountRespException(Msg.REMOTE_SETTLE_6000.getCode(), Msg.REMOTE_SETTLE_6000.getDesc());
		String resultCode = result.getResultCode();
		boolean b = StringUtils.equals(resultCode, ResultCode.SUCCESS.getCode());
		if (!b) throw new ServiceException(result.getResultCode(), result.getResultDesc());
		ConfirmPreSettleDateRespDataDTO data = result.getData();
		if (data == null) throw new ServiceException(Msg.REMOTE_SETTLE_P6002.getCode(), Msg.REMOTE_SETTLE_P6002.getDesc());
		LocalDate date = data.getPreSettleDate();
		if (date == null) throw new ServiceException(Msg.REMOTE_SETTLE_P6001.getCode(), Msg.REMOTE_SETTLE_P6001.getDesc());
		return result;
	}
}
