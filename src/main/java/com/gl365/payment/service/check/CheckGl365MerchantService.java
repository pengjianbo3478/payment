package com.gl365.payment.service.check;

import com.gl365.payment.remote.dto.merchant.Gl365Merchant;

public interface CheckGl365MerchantService {
	
	void check(Gl365Merchant gl365Merchant);
}
