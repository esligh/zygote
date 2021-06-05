package com.yunzhu.module.bus.api.exception;

public class ServerException extends RuntimeException {
    public int status;
    public String errorCode;
    public String errorMsg;

    public ServerException(int status, String errorCode,String failureDetails) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMsg = failureDetails;
    }
}