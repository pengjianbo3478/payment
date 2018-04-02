package com.gl365.payment.exception;
public class MerchantRespException extends ServiceException {
	private static final long serialVersionUID = 1L;

	public MerchantRespException(String code, String desc, Throwable cause) {
		super(code, desc, cause);
	}

	public MerchantRespException(String code, String desc) {
		super(code, desc);
	}

	public MerchantRespException(String code) {
		super(code);
	}
}
