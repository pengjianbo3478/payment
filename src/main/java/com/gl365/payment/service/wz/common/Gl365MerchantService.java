package com.gl365.payment.service.wz.common;
import com.gl365.payment.remote.dto.merchant.Gl365Merchant;
public interface Gl365MerchantService {
	Gl365Merchant queryGl365Merchant(String merchantNo, String organCode,String channleType);

	void checkGl365Merchant(Gl365Merchant gl365Merchant);
}
