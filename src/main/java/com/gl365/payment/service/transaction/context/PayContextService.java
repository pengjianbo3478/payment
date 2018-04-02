package com.gl365.payment.service.transaction.context;
import com.gl365.payment.dto.base.PayContext;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.remote.dto.account.Gl365UserAccount;
import com.gl365.payment.remote.dto.account.request.BeanTransferReqDTO;
import com.gl365.payment.remote.dto.account.request.BeanTransferReverseReqDTO;
import com.gl365.payment.remote.dto.account.request.CancelOperateReqDTO;
import com.gl365.payment.remote.dto.account.request.QueryAccountBalanceInfoReqDTO;
import com.gl365.payment.remote.dto.account.request.ReverseOperateReqDTO;
import com.gl365.payment.remote.dto.account.request.UpdateAccountBalanceOffLineReqDTO;
import com.gl365.payment.remote.dto.account.response.BeanTransferRespDTO;
import com.gl365.payment.remote.dto.account.response.BeanTransferReverseRespDTO;
import com.gl365.payment.remote.dto.account.response.CancelOperateRespDTO;
import com.gl365.payment.remote.dto.account.response.QueryAccountBalanceInfoRespDTO;
import com.gl365.payment.remote.dto.account.response.ReverseOperateRespDTO;
import com.gl365.payment.remote.dto.account.response.UpdateAccountBalanceOffLineRespDTO;
import com.gl365.payment.remote.dto.member.Gl365User;
import com.gl365.payment.remote.dto.merchant.Gl365Merchant;
import com.gl365.payment.remote.dto.settlement.request.ConfirmPreSettleDateReqDTO;
import com.gl365.payment.remote.dto.settlement.response.ConfirmPreSettleDateRespDTO;
public interface PayContextService {
	/**
	 * <p>通过卡索引号查询绑卡表，得到用户SN、绑卡状态<p>
	 * @param accountNo
	 * @return String
	 */
	Gl365UserAccount queryAccount(PayContext ctx) throws ServiceException;

	/**
	 * <p>绑卡状态是否存在、是否正常<p>
	 * @param userId
	 * @return boolean
	 */
	boolean isBindCard(PayContext ctx) throws ServiceException;

	/**
	 * <p>通过用户SN查询用户表，取出用户所属发展机构、乐豆支付总开关,用户状态<p>
	 * @param userId
	 * @return QueryUserInfoRespDTO
	 */
	Gl365User queryUserInfo(PayContext ctx) throws ServiceException;

	/**
	 * <p>通过付费通商户号查询给乐商户号<p>
	 * @param organMerchantNo
	 * @return QueryMerchantInfoRespDTO
	 */
	Gl365Merchant queryMerchantInfo(PayContext ctx) throws ServiceException;

	/**
	 * <p>根据给乐交易单号查询原单</p>
	 * @param ctx
	 * @return PayMain
	 * @throws ServiceException
	 */
	PayMain queryPayMainByPayId(String payId);

	/**
	 * <p>根据请求交易流水号查询原单</p>
	 * @param ctx
	 * @return PayMain
	 * @throws ServiceException
	 */
	PayMain queryPayMainByRequestId(PayContext ctx);

	/**
	 * <p>根据请求交易流水号-终端号查询原单</p>
	 * @param ctx
	 * @return
	 */
	PayMain queryPayMainByRequestIdAndTerminal(String terminal, String requestId);

	/**
	 * <p>写交易流水</p>
	 * @return int
	 * @throws ServiceException
	 */
	int savePayStream(PayContext ctx);

	/**
	 * <p>生成给乐交易号</p>
	 * @return String
	 */
	String generatePayId(String payCategory);

	/**
	 * <p>保存付款明细</p>
	 * @param ctx
	 * @return int
	 * @throws ServiceException
	 */
	int savePayDetails(PayContext ctx);

	/**
	 * <p>根据请求流水号查询交易流水记录</p>
	 * @param ctx
	 * @return PayStream
	 */
	PayStream queryPayStreamByRequestId(String requestId);

	UpdateAccountBalanceOffLineRespDTO UpdateAccountBalanceOffLine(UpdateAccountBalanceOffLineReqDTO reqDTO);

	QueryAccountBalanceInfoRespDTO queryAccountBalanceInfo(QueryAccountBalanceInfoReqDTO reqDTO);

	ReverseOperateRespDTO reverseOperate(ReverseOperateReqDTO reqDTO);

	CancelOperateRespDTO cancelOperate(CancelOperateReqDTO cancelOperateReqDTO);

	int updatePayStreamStatus(PayStream payStream);

	BeanTransferRespDTO beanTransfer(BeanTransferReqDTO beanTransferReqDTO);
	
	BeanTransferReverseRespDTO beanTransfeReverseOperate(BeanTransferReverseReqDTO beanTransferReverseReqDTO);
	
	ConfirmPreSettleDateRespDTO getConfirmPreSettleDate(ConfirmPreSettleDateReqDTO request);
}
