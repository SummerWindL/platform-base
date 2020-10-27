package com.platform.common.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = -776501442596076821L;
	
	private int status;
	
	private String message;

	public BaseException(String msg){
		super(msg);
		this.message = msg;
	}
	
	public BaseException(int status, String msg){
		super(msg);
		this.status = status;
		this.message = msg;
	}
	
	public BaseException(String msg, Throwable throwable){
		super(msg, throwable);
		this.message = msg;
	}
	
	public BaseException(int status, String msg, Throwable throwable){
		super(msg, throwable);
		this.status = status;
		this.message = msg;
	}

}
