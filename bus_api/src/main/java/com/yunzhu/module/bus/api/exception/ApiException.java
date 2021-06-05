package com.yunzhu.module.bus.api.exception;

public class ApiException extends Exception {
    public int status;
    public String errorMessage;
    public String errorCode;

    public ApiException(Throwable throwable, int status) {
        super(throwable);
        this.status = status;
    }
}

