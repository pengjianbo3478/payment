package com.gl365.payment.remote.dto.merchant.request;
import java.io.Serializable;
import com.gl365.payment.util.Gl365StrUtils;
public class QueryMerchantInfoReqDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 支付公司ID
	 */
	private String organCode;
	/**
	 * 支付公司商户号
	 */
	private String organMerchantNo;
	/**
	 * 
	 */
	private String type = "1";
	/**
	 * 渠道类型：wxpub:微信公众号;wxh5:微信H5
	 */
	private String channleType;

	public QueryMerchantInfoReqDTO(String organCode, String organMerchantNo) {
		super();
		this.organCode = organCode;
		this.organMerchantNo = organMerchantNo;
	}

	public String getOrganCode() {
		return organCode;
	}

	public QueryMerchantInfoReqDTO(String organMerchantNo) {
		super();
		this.organMerchantNo = organMerchantNo;
	}

	public QueryMerchantInfoReqDTO() {
		super();
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	public String getOrganMerchantNo() {
		return organMerchantNo;
	}

	public void setOrganMerchantNo(String organMerchantNo) {
		this.organMerchantNo = organMerchantNo;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toStr(this);
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String getChannleType() {
		return channleType;
	}

	public void setChannleType(String channleType) {
		this.channleType = channleType;
	}
}
