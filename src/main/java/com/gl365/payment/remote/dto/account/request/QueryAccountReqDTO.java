package com.gl365.payment.remote.dto.account.request;
import java.io.Serializable;
public class QueryAccountReqDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 绑卡索引号
	 */
	private String cardIndex;
	/**
	 * 支付渠道-FFT001:付费通
	 */
	private String organCode;

	public QueryAccountReqDTO(String cardIndex,String organCode) {
		this.cardIndex = cardIndex;
		this.organCode=organCode;
	}

	public QueryAccountReqDTO() {
	}

	/**
	 * @return the cardIndex
	 */
	public String getCardIndex() {
		return cardIndex;
	}

	/**
	 * @param cardIndex the cardIndex to set
	 */
	public void setCardIndex(String cardIndex) {
		this.cardIndex = cardIndex;
	}

	/**
	 * @return the organCode
	 */
	public String getOrganCode() {
		return organCode;
	}

	/**
	 * @param organCode the organCode to set
	 */
	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}
}
