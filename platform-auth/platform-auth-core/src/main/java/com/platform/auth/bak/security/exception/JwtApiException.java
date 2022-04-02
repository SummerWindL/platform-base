package com.platform.auth.bak.security.exception;


import com.platform.auth.bak.security.enums.ResultCodeEnum;

/**
 * @author Advance
 * @date 2021年12月09日 16:23
 * @since V1.0.0
 */
public class JwtApiException extends RuntimeException{
    private static final long serialVersionUID = 970152056841039187L;

    private ResultCodeEnum responseResult;

    private Object data;


    public JwtApiException(ResultCodeEnum responseResult) {
        this(responseResult, null);
    }



    public JwtApiException(ResultCodeEnum responseResult, Object data) {
        this.responseResult = responseResult;
        this.data = data;
    }


    public ResultCodeEnum getResponseResult() {
        return this.responseResult;
    }

    public Object getData() {
        return this.data;
    }

    @Override
    public String getMessage() {
        // TODO Auto-generated method stub
        if (responseResult != null) {
            return responseResult.message();
        }

        return super.getMessage();
    }
}
