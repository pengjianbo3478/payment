package com.gl365.payment.service.mq.consumer.service;
public interface PaymentConsumerService {
	void exePaymentResult(String data);

	void exeGetWayPaymentNotify(String data);
}
