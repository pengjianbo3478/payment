package com.gl365.payment.service.pos.abs;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.gl365.payment.dto.rollback.RollbackContext;
import com.gl365.payment.dto.rollback.request.RollbackReqDTO;
import com.gl365.payment.dto.rollback.response.RollbackRespDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.util.Gl365StrUtils;
public abstract class AbstractRollbackResp extends AbstractRollbackBuild {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractRollbackResp.class);

	public RollbackRespDTO getResp(RollbackContext ctx) {
		RollbackRespDTO result = new RollbackRespDTO();
		RollbackReqDTO reqDTO = ctx.getRollbackReqDTO();
		result.setOrganCode(reqDTO.getOrganCode());
		result.setOrganMerchantNo(reqDTO.getOrganMerchantNo());
		result.setTerminal(reqDTO.getTerminal());
		result.setRequestId(reqDTO.getRequestId());
		result.setCardIndex(reqDTO.getCardIndex());
		String payId = ctx.getPayId();
		result.setPayId(payId);
		result.setTxnDate(LocalDate.now());
		result.setPayStatus(Msg.S000.getCode());
		result.setPayDesc(Msg.S000.getDesc());
		LOG.info("返回结果={}", Gl365StrUtils.toStr(result));
		return result;
	}
}
