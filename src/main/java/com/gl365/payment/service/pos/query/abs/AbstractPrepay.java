package com.gl365.payment.service.pos.query.abs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.gl365.payment.dto.pretranscation.PreTranContext;
import com.gl365.payment.dto.pretranscation.request.PreTranReqDTO;
import com.gl365.payment.dto.pretranscation.response.PreTranRespDTO;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.model.PayPrepay;
import com.gl365.payment.remote.dto.account.response.QueryAccountBalanceInfoRespDTO;
import com.gl365.payment.service.transaction.AbstractPay;
import com.gl365.payment.service.transaction.context.PayContextService;
@Service
public abstract class AbstractPrepay extends AbstractPay<PreTranContext, PreTranReqDTO> {
	@Autowired
	@Qualifier("payContextService")
	public PayContextService payContextService;

	public abstract void putGl365User(PreTranContext ctx);

	public abstract void putRequest(PreTranContext ctx);

	public abstract void putGl365Merchant(PreTranContext ctx);

	public abstract void calculate(PreTranContext ctx);

	public abstract boolean isPayBean();

	public abstract void buildPayStream(PreTranContext ctx);

	public abstract PreTranRespDTO buildResp(PreTranContext ctx);

	public abstract void checkRequestParams(PreTranReqDTO request);

	public abstract void initPayDefault(PreTranContext ctx);

	public abstract PayPrepay buildPayPrepay(PreTranContext ctx);

	public abstract void calculateBeanAmount(PreTranContext ctx);

	public abstract boolean checkMerchantPayBean(PreTranContext ctx);

	public abstract boolean lessOnePay(PreTranContext ctx);

	public abstract void greaterOnePay(PreTranContext ctx);

	public abstract int savePrepay(PayPrepay payPrepay);

	public abstract QueryAccountBalanceInfoRespDTO queryAccountBalanceInfo(PreTranContext ctx);

	public abstract boolean checkUserPayBean(PreTranContext ctx);

	public abstract TranType initTranCate();

	public abstract String getOrderType(PreTranContext ctx);
}
