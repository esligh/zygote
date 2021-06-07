package com.yunzhu.module.bus.api.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

public class ExceptionEngine {

    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    private final static class ERROR {
        static final int UNKNOWN = 1000;
        static final int PARSE_ERROR = 1001;
        static final int NETWORK_ERROR = 1002;
        static final int HTTP_ERROR = 1003;
    }

    public static ApiException handleException(Throwable e){
        ApiException ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ApiException(e, ERROR.HTTP_ERROR);
            switch(httpException.code()){
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                    ex.errorMessage = "请求超时";
                    break;
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                    ex.errorMessage = "服务器出错啦";
                default:
                    ex.errorMessage = "请求出错啦";
                    break;
            }
            return ex;
        } else if (e instanceof ServerException){    //服务器返回的错误
            ServerException resultException = (ServerException) e;
            ex = new ApiException(resultException, resultException.getStatus());
            ex.errorMessage = resultException.getErrorMsg();
            ex.errorCode = resultException.getErrorCode();
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException){
            ex = new ApiException(e, ERROR.PARSE_ERROR);
            ex.errorCode = String.valueOf(ERROR.PARSE_ERROR);
            ex.errorMessage = "解析错误";            //均视为解析错误
            return ex;
        }else if(e instanceof ConnectException
                || e instanceof SocketTimeoutException
                || e instanceof UnknownHostException) {
            ex = new ApiException(e, ERROR.NETWORK_ERROR);
            ex.errorCode = String.valueOf(ERROR.NETWORK_ERROR);
            ex.errorMessage = "连接失败";  //均视为网络错误
            return ex;
        }else {
            ex = new ApiException(e, ERROR.UNKNOWN);
            ex.errorCode = String.valueOf(ERROR.UNKNOWN);
            ex.errorMessage = "未知错误";  //未知错误
            return ex;
        }
    }
}

