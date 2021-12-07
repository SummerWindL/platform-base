package com.platform.common.exception;

public class Exception extends RuntimeException {

    private static final long serialVersionUID = 7637174399993976947L;

    public Exception() {
    }

    public Exception(String msg) {
        super(msg);
    }

    public Exception(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
