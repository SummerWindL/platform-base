package com.platform.common.exception;

public class JWTTokenException extends Exception {

	private static final long serialVersionUID = 670535258127719788L;

	public JWTTokenException(String msg) {
		super(msg);
	}
	
	public JWTTokenException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

}
