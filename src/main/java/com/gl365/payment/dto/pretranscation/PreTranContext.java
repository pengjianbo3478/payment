package com.gl365.payment.dto.pretranscation;
import java.io.Serializable;
import com.gl365.payment.dto.base.PayContext;
import com.gl365.payment.dto.pretranscation.request.PreTranReqDTO;
import com.gl365.payment.model.PayPrepay;
import com.gl365.payment.remote.dto.account.response.QueryAccountBalanceInfoRespDTO;
import com.gl365.payment.util.Gl365StrUtils;
public class PreTranContext extends PayContext implements Serializable {
	private static final long serialVersionUID = 1L;
	private PayPrepay payPrepay = new PayPrepay();
	private PreTranReqDTO preTranReqDTO;
	private QueryAccountBalanceInfoRespDTO queryAccountBalanceInfoRespDTO;

	public PreTranContext(PreTranReqDTO consumeQueryReqDTO) {
		super();
		this.preTranReqDTO = consumeQueryReqDTO;
	}

	public PreTranContext() {
		super();
	}

	public PayPrepay getPayPrepay() {
		return payPrepay;
	}

	public void setPayPrepay(PayPrepay payPrepay) {
		this.payPrepay = payPrepay;
	}

	public PreTranReqDTO getPreTranReqDTO() {
		return preTranReqDTO;
	}

	public void setPreTranReqDTO(PreTranReqDTO preTranReqDTO) {
		this.preTranReqDTO = preTranReqDTO;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toStr(this);
	}

	public QueryAccountBalanceInfoRespDTO getQueryAccountBalanceInfoRespDTO() {
		return queryAccountBalanceInfoRespDTO;
	}

	public void setQueryAccountBalanceInfoRespDTO(QueryAccountBalanceInfoRespDTO queryAccountBalanceInfoRespDTO) {
		this.queryAccountBalanceInfoRespDTO = queryAccountBalanceInfoRespDTO;
	}
}
