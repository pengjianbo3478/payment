package com.gl365.payment.service.transaction.remote;
import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.gl365.payment.common.constants.PeripheralSystemConstants;
import com.gl365.payment.dto.base.PayContext;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.remote.dto.account.request.CancelOperateReqDTO;
import com.gl365.payment.remote.dto.account.request.UpdateAccountBalanceOffLineReqDTO;
import com.gl365.payment.remote.dto.account.response.CancelOperateRespDTO;
import com.gl365.payment.remote.dto.account.response.QueryAccountTotalBalanceRespDTO;
import com.gl365.payment.remote.dto.account.response.UpdateAccountBalanceOffLineRespDTO;
import com.gl365.payment.util.Gl365StrUtils;
@Service("remoteMicroServiceAgent")
public class RemoteMicroServiceAgent extends AbstractRemoteServiceFacade {
	private static final Logger LOG = LoggerFactory.getLogger(RemoteMicroServiceAgent.class);

	@Override
	public PayMain queryPayMainByPayId(String payId) {
		return null;
	}

	@Override
	public PayMain queryPayMainByRequestId(PayContext ctx) {
		return null;
	}

	@Override
	public int savePayStream(PayContext ctx) {
		return 0;
	}

	@Override
	public String generatePayId(String payCategory) {
		return null;
	}

	@Override
	public int savePayDetails(PayContext ctx) {
		return 0;
	}

	@Override
	public PayStream queryPayStreamByRequestId(String requestId) {
		return null;
	}

	@Override
	public UpdateAccountBalanceOffLineRespDTO UpdateAccountBalanceOffLine(UpdateAccountBalanceOffLineReqDTO reqDTO) throws ServiceException {
		return super.UpdateAccountBalanceOffLine(reqDTO);
	}

	public QueryAccountTotalBalanceRespDTO queryAccountTotalBalance(String userId) {
		LOG.debug("request={}", userId);
		QueryAccountTotalBalanceRespDTO result = accountServiceRemote.queryAccountTotalBalance(userId);
		if (result == null) throw new ServiceException(Msg.MICRO_SERVICE_FAIL.getCode(), Msg.MICRO_SERVICE_FAIL.getDesc());
		String resultCode = result.getResultCode();
		boolean b = !StringUtils.equals(resultCode, PeripheralSystemConstants.SUCCESS_CODE);
		if (b) throw new ServiceException(result.getResultCode(), result.getResultDesc());
		BigDecimal resultData = result.getResultData();
		if (resultData == null) throw new ServiceException(Msg.PAY_8016.getCode(), Msg.PAY_8016.getDesc());
		LOG.debug("resultData={}", resultData);
		LOG.debug("response={}", Gl365StrUtils.toStr(result));
		return result;
	}

	@Override
	public CancelOperateRespDTO cancelOperate(CancelOperateReqDTO request) throws ServiceException {
		return super.cancelOperate(request);
	}

	@Override
	public int updatePayStreamStatus(PayStream payStream) {
		return 0;
	}

	@Override
	public PayMain queryPayMainByRequestIdAndTerminal(String terminal, String requestId) {
		return null;
	}
}
