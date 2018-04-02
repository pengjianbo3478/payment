package com.gl365.payment.dto.wx.request;
import java.io.Serializable;
import com.gl365.payment.util.JsonUtil;
public class WxCancelQueryReqDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 机构代码
	 */
	private String organCode;
	/**
	 * 给乐订单号
	 */
	private String merchantOrderNo;

	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
