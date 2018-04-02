package com.gl365.payment.dto.rollback.response;
import com.gl365.payment.dto.base.response.PayRespDTO;
import com.gl365.payment.util.Gl365StrUtils;
public class RollbackRespDTO extends PayRespDTO {

	private static final long serialVersionUID = 1L;
	/**
	 * 请求交易日期
	 */
	private String requestId;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}


	public RollbackRespDTO() {
		super();
	}
	
	public RollbackRespDTO(String payStatus, String payDesc) {
		super(payStatus, payDesc);
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toStr(this);
	}
}
