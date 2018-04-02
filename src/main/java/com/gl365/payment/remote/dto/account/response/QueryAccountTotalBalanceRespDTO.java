package com.gl365.payment.remote.dto.account.response;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author duanxz
* 2017-11-04 15:05:370
* 生成get/set方法,toString()
*/
public class QueryAccountTotalBalanceRespDTO implements Serializable {

private static final long serialVersionUID = 1L;

/* 会员ID ,最大长度32 ,不能为空 */
private String userId;

/* 所有正常账户的总余额 ,最大长度 ,不能为空 */
private BigDecimal resultData;

/* 返回码 ,最大长度6 ,不能为空 */
private String resultCode;

/* 返回描述 ,最大长度64 ,不能为空 */
private String resultDesc;

public String getUserId() {
	return userId;
}

public void setUserId(String userId) {
	this.userId = userId;
}

public BigDecimal getResultData() {
	return resultData;
}

public void setResultData(BigDecimal resultData) {
	this.resultData = resultData;
}

public String getResultCode() {
	return resultCode;
}

public void setResultCode(String resultCode) {
	this.resultCode = resultCode;
}

public String getResultDesc() {
	return resultDesc;
}

public void setResultDesc(String resultDesc) {
	this.resultDesc = resultDesc;
}

@Override
public String toString() {
	return "QueryAccountTotalBalanceRespDTO [userId=" + userId + ", resultData=" + resultData + ", resultCode="
			+ resultCode + ", resultDesc=" + resultDesc + "]";
}


}