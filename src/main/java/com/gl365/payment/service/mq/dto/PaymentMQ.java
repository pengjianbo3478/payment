package com.gl365.payment.service.mq.dto;
import java.io.Serializable;
import java.util.UUID;
public class PaymentMQ implements Serializable {
	private static final long serialVersionUID = 1L;
	private String tranType;
	private String msgCategory;
	private String messageId = UUID.randomUUID().toString();
	private PaymentBody paymentBody;

	public PaymentBody getPaymentBody() {
		return paymentBody;
	}

	public void setPaymentBody(PaymentBody paymentBody) {
		this.paymentBody = paymentBody;
	}

	/**
	 * @return the tranType
	 */
	public String getTranType() {
		return tranType;
	}

	/**
	 * @param tranType the tranType to set
	 */
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	/**
	 * @return the msgCategory
	 */
	public String getMsgCategory() {
		return msgCategory;
	}

	/**
	 * @param msgCategory the msgCategory to set
	 */
	public void setMsgCategory(String msgCategory) {
		this.msgCategory = msgCategory;
	}

	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId the messageId to set
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
}
