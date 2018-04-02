package com.gl365.payment.remote.service;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gl365.payment.dto.base.CommonDTO;
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
import com.gl365.payment.remote.service.impl.AccountServiceRemoteFallback;
@FeignClient(name = "account", fallback = AccountServiceRemoteFallback.class)
public interface AccountServiceRemote {
	/**
	 * <p>根据绑卡索引号查询有效的会员ID</p>
	 * @param queryAccountReqDTO
	 * @return QueryAccountRespDTO
	 */
	@RequestMapping(value = "/bindinfo/queryAccount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public QueryAccountRespDTO queryAccount(@RequestBody QueryAccountReqDTO queryAccountReqDTO);

	/**
	 * <p>绑卡状态是否存在、是否正常</p>
	 * @param hasNormalBindInfoReqDTO
	 * @return HasNormalBindInfoRespDTO
	 */
	@RequestMapping(value = "/bindinfo/hasNormalBindInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public HasNormalBindInfoRespDTO hasNormalBindInfo(@RequestBody HasNormalBindInfoReqDTO hasNormalBindInfoReqDTO);

	/**
	 * <p>查询特定账户余额信息，包含余额、状态信息。</p>
	 * @param accountBalanceInfoReqDTO
	 * @return QueryAccountBalanceInfoRespDTO
	 */
	@RequestMapping(value = "/account/queryAccountBalanceInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public QueryAccountBalanceInfoRespDTO queryAccountBalanceInfo(@RequestBody QueryAccountBalanceInfoReqDTO accountBalanceInfoReqDTO);

	/**
	 * <p>扣除余额，不足时失败(deductBalance)</p>
	 */
	@RequestMapping(value = "/deductBalance", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public CommonDTO deductBalance(@RequestBody DeductBalanceReqDto reqDto);

	/**
	 * <p>余额交易(线下)</p>
	 * @param accountBalanceOffLineReqDTO
	 * @return UpdateAccountBalanceOffLineRespDTO
	 */
	@RequestMapping(value = "/account/updateAccountBalanceOffLine", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public UpdateAccountBalanceOffLineRespDTO updateAccountBalanceOffLine(@RequestBody UpdateAccountBalanceOffLineReqDTO accountBalanceOffLineReqDTO);

	/**
	 * 
	 * @param queryAccountTotalBalanceReqDTO
	 * @return
	 */
	@RequestMapping(value = "/account/queryAccountTotalBalance/{userId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public QueryAccountTotalBalanceRespDTO queryAccountTotalBalance(@PathVariable("userId") String userId);

	/**
	 *<p>对于POS消费冲正、网上消费冲正、预授权完成确认冲正，送消费交易的payId；
	 *<p>对于退货冲正，送退货交易的payId</p>
	 *<p>对于撤销冲正、网上撤销冲正，送撤销、网上撤销交易的payId</p>
	 * @param reverseOperateReqDTO
	 * @return ReverseOperateRespDTO
	 */
	@RequestMapping(value = "/account/reverseOperate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ReverseOperateRespDTO reverseOperate(@RequestBody ReverseOperateReqDTO reverseOperateReqDTO);

	/**
	 * 撤销
	 * @param cancelOperateReqDTO
	 * @return
	 */
	@RequestMapping(value = "/account/cancelOperate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public CancelOperateRespDTO cancelOperate(@RequestBody CancelOperateReqDTO cancelOperateReqDTO);

	/**
	 * 乐豆转账
	 * @param beanTransferReqDTO
	 * @return BeanTransferRespDTO
	 */
	@RequestMapping(value = "/account/beanAmountTransfer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public BeanTransferRespDTO beanTransfer(@RequestBody BeanTransferReqDTO beanTransferReqDTO);
	
	/**
	 * 乐豆转账冲正
	 * @param BeanTransferReverseReqDTO
	 * @return BeanTransferReverseRespDTO
	 */
	@RequestMapping(value = "/account/beanAmountTransferReverse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public BeanTransferReverseRespDTO beanTransfeReverseOperate(@RequestBody BeanTransferReverseReqDTO beanTransferReverseReqDTO);
}
