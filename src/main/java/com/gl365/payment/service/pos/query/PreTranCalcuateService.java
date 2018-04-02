package com.gl365.payment.service.pos.query;
import com.gl365.payment.dto.pretranscation.PreTranContext;
public interface PreTranCalcuateService {
	void calculatePayFeeRate(PreTranContext ctx);

	void calculateCommAmount(PreTranContext ctx);

	void calculateMarcketFee(PreTranContext ctx);

	void calculateMerchantSettleAmount(PreTranContext ctx);
}
