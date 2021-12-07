package com.platform.common.exception;

public class JWTTokenExpiredException extends JWTTokenException {

    private static final long serialVersionUID = -2752176214675829321L;

    public JWTTokenExpiredException(String msg) {
        super(msg);
    }

    public JWTTokenExpiredException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
