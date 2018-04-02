package com.gl365.payment.remote.dto.merchant.response;
public class QueryMerchantInfoRespDTO extends BaseRespDTO {
	private static final long serialVersionUID = 1L;
	private QueryMerchantInfoRespDTOBody data;

	/**
	 * @return the data
	 */
	public QueryMerchantInfoRespDTOBody getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(QueryMerchantInfoRespDTOBody data) {
		this.data = data;
	}
}
