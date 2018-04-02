package com.gl365.payment.service.dbservice;

import com.gl365.payment.model.PayNotify;

public interface PayNotifyService {

	int insert(PayNotify payNotify);
	
	PayNotify queryByPayNotify(PayNotify payNotify);
	
}
