package com.gl365.payment.exception;
public class MemberRespException extends ServiceException {
	private static final long serialVersionUID = 1L;

	public MemberRespException(String code, String desc, Throwable cause) {
		super(code, desc, cause);
	}

	public MemberRespException(String code, String desc) {
		super(code, desc);
	}
}
