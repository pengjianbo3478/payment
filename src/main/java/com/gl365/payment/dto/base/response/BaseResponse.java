/**
 * Project Name:payment
 * File Name:BaseResponse.java
 * Package Name:com.gl365.payment.dto
 * Date:2017年4月27日下午4:36:58
 * Copyright (c) 2017, chenzhou1025@126.com All Rights Reserved.
 *
*/
/**
 * Project Name:payment
 * File Name:BaseResponse.java
 * Package Name:com.gl365.payment.dto
 * Date:2017年4月27日下午4:36:58
 * Copyright (c) 2017, 深圳市给乐信息科技有限公司 All Rights Reserved.
 *
 */

package com.gl365.payment.dto.base.response;

import com.gl365.payment.enums.msg.Msg;

/**
 * 
 * date: 2017年4月27日 下午4:36:58 <br/>
 *
 * @author lenovo
 * @version 
 * @since JDK 1.8
 */
public class BaseResponse {
	/* 给乐流水号 ,最大长度32 ,不能为空 */
	private String payId;
	
	/** 返回代码 ,最大长度2 ,不能为空 */
	private String payStatus;

	/** 返回描述 ,最大长度64 ,不能为空 */
	private String payDesc;

	public BaseResponse(String payStatus, String payDesc) {
		if(null == payStatus || "".equalsIgnoreCase(payStatus)) {
			this.payStatus = Msg.F000.getCode();
		} else {
			this.payStatus = payStatus;
		}
		if(null == payDesc || "".equalsIgnoreCase(payDesc)) {
			this.payDesc = Msg.F000.getDesc();
		} else {
			this.payDesc = payDesc;
		}
	}
	public BaseResponse() {
		super();
	}
	public static BaseResponse fallback() {
		return new BaseResponse(Msg.F000.getCode(), Msg.F000.getDesc());
	}
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayDesc() {
		return payDesc;
	}

	public void setPayDesc(String payDesc) {
		this.payDesc = payDesc;
	}
}

