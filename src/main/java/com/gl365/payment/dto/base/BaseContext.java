package com.gl365.payment.dto.base;

import com.gl365.payment.dto.base.request.BaseRequest;
import com.gl365.payment.dto.base.response.BaseResponse;

public class BaseContext<Q extends BaseRequest, P extends BaseResponse> extends SuperContext {

	private static final long serialVersionUID = 1L;

	private Q request;
	
	private P response;

	public Q getRequest() {
		return request;
	}

	public void setRequest(Q request) {
		this.request = request;
	}

	public P getResponse() {
		return response;
	}

	public void setResponse(P response) {
		this.response = response;
	}
	

	

}
