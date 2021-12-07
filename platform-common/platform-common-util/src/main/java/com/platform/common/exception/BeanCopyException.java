package com.platform.common.exception;

public class BeanCopyException extends Exception {

    private static final long serialVersionUID = -6901572848436003077L;

    public BeanCopyException(String msg) {
        super(msg);
    }

    public BeanCopyException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
