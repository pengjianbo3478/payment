package com.gl365.payment.remote.dto.account.request;
public class CancelOperateReqDTO extends UpdateAccountBalanceOffLineReqDTO {
	private static final long serialVersionUID = 1L;
	private String origPayId;

	/**
	 *对于POS消费冲正、网上消费冲正、预授权完成确认冲正，送消费交易的payId；
	 *对于退货冲正，送退货交易的payId；
	 *对于撤销冲正、网上撤销冲正，送撤销、网上撤销交易的payId
	 * @return the origPayId
	 */
	public String getOrigPayId() {
		return origPayId;
	}

	/**
	 *对于POS消费冲正、网上消费冲正、预授权完成确认冲正，送消费交易的payId；
	 *对于退货冲正，送退货交易的payId；
	 *对于撤销冲正、网上撤销冲正，送撤销、网上撤销交易的payId
	 * @param origPayId the origPayId to set
	 */
	public void setOrigPayId(String origPayId) {
		this.origPayId = origPayId;
	}
}
