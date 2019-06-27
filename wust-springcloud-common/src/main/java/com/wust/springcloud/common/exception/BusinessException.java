package com.wust.springcloud.common.exception;

/**
 * Created by WST on 2019/4/28.
 */
public class BusinessException extends RuntimeException{

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message,Throwable cause) {
        super(message,cause);
    }
}
