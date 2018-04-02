package com.gl365.payment.remote.dto.account.request;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author duanxz
* 2017-27-25 15:04:109
*/
public class DeductBalanceReqDto implements Serializable {

private static final long serialVersionUID = 1L;

/** 会员ID ,最大长度32 ,不能为空 */
private String userId;

/** 支付流水号 ,最大长度32 ,不能为空 */
private String payId;

/** 商户ID ,最大长度15 ,不能为空 */
private String merchantNo;

/** 商户名称 ,最大长度128 ,不能为空 */
private String merchantName;

/** 商户订单号 ,最大长度32 ,不能为空 */
private String merchantOrderNo;

/** 操作类型 ,最大长度2 ,不能为空 */
private String operateType;

/** 操作金额 ,最大长度 ,不能为空 */
private BigDecimal operateAount;

/** 支付场景 ,最大长度2 ,不能为空 */
private String scene;

public String getUserId() {
	return userId;
}

public void setUserId(String userId) {
	this.userId = userId;
}

public String getPayId() {
	return payId;
}

public void setPayId(String payId) {
	this.payId = payId;
}

public String getMerchantNo() {
	return merchantNo;
}

public void setMerchantNo(String merchantNo) {
	this.merchantNo = merchantNo;
}

public String getMerchantName() {
	return merchantName;
}

public void setMerchantName(String merchantName) {
	this.merchantName = merchantName;
}

public String getMerchantOrderNo() {
	return merchantOrderNo;
}

public void setMerchantOrderNo(String merchantOrderNo) {
	this.merchantOrderNo = merchantOrderNo;
}

public String getOperateType() {
	return operateType;
}

public void setOperateType(String operateType) {
	this.operateType = operateType;
}

public BigDecimal getOperateAount() {
	return operateAount;
}

public void setOperateAount(BigDecimal operateAount) {
	this.operateAount = operateAount;
}

public String getScene() {
	return scene;
}

public void setScene(String scene) {
	this.scene = scene;
}

@Override
public String toString() {
	return "DeductBalanceReqDto [userId=" + userId + ", payId=" + payId + ", merchantNo=" + merchantNo
			+ ", merchantName=" + merchantName + ", merchantOrderNo=" + merchantOrderNo + ", operateType=" + operateType
			+ ", operateAount=" + operateAount + ", scene=" + scene + "]";
}


}