package com.gl365.payment.exception;
public class RemoteServiceException extends ServiceException {
	private static final long serialVersionUID = 1L;

	public RemoteServiceException(String code, String desc, Throwable cause) {
		super(code, desc, cause);
	}

	public RemoteServiceException(String code, String desc) {
		super(code, desc);
	}
}
