package com.gl365.payment.service.check;
import com.gl365.payment.dto.rollback.RollbackContext;
public interface CheckRollbackService {
	void checkPaymentDate(RollbackContext ctx);

	void checkPayMianOrganMerchantNo(RollbackContext ctx);

	void checkPayMianCardIndex(RollbackContext ctx);

	void checkTotalAmount(RollbackContext ctx);

	void checkTerminal(RollbackContext ctx);
}
