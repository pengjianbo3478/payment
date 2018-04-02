package com.gl365.payment.remote.dto.settlement.response;
import java.util.List;
import io.swagger.annotations.ApiModelProperty;
public class CurrentPayProfitRespDTO<T> {
	/** 接口结果返回编码 */
	@ApiModelProperty(value = "接口结果返回编码")
	private String resultCode;
	/** 接口结果返回描述 */
	@ApiModelProperty(value = "返回结果列表数据")
	private String resultDesc;
	/** 返回结果列表对象 */
	@ApiModelProperty(value = "接口结果返回描述")
	protected List<T> data;

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

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
