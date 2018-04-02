package com.gl365.payment.service.pos.query.abs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.gl365.payment.dto.pretranscation.PreTranContext;
import com.gl365.payment.enums.system.Agent;
import com.gl365.payment.remote.dto.account.request.QueryAccountBalanceInfoReqDTO;
import com.gl365.payment.remote.dto.account.response.QueryAccountBalanceInfoRespDTO;
import com.gl365.payment.remote.dto.account.response.QueryAccountRespDTO;
import com.gl365.payment.util.Gl365StrUtils;
public abstract class AbstractConsumeQueryRemote extends AbstractConsumeQueryCalc {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractConsumeQueryRemote.class);

	public QueryAccountBalanceInfoRespDTO queryAccountBalanceInfo(PreTranContext ctx) {
		QueryAccountRespDTO account = ctx.getGl365UserAccount();
		String userId = account.getUserId();
		QueryAccountBalanceInfoReqDTO request = new QueryAccountBalanceInfoReqDTO(userId, Agent.GL365.getKey());
		LOG.debug("request={}", Gl365StrUtils.toStr(request));
		QueryAccountBalanceInfoRespDTO response = this.payContextService.queryAccountBalanceInfo(request);
		ctx.setQueryAccountBalanceInfoRespDTO(response);
		return response;
	}
}
