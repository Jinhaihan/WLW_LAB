package com.example.jinha.wlwlab.network.retrofit;

import com.google.gson.JsonParseException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

/**
 * @ProjectName: SportsHealth
 * @Package: cn.ac.ia.iot.healthlibrary.network.retrofit
 * @ClassName: NetExceptionHandler
 * @Author: mebee
 * @CreateDate: 2018/8/23 10:49
 * @Description: 网络错误处理具体逻辑
 */
public class NetExceptionHandler {

    public static class ERROR {

        public static final int UNKNOW_ERROR    = 1001;
        public static final int HTTP_ERROR      = 1002;
        public static final int NET_ERROR       = 1003;
        public static final int PARSE_ERROR     = 1004;
        public static final int SSL_ERROR       = 1005;
        public static final int DATA_ERROR      = 1006;
        public static final int UNLOGIN_ERROR   = 1007;

    }

    public static NetException handleException(Throwable e) {
        NetException exception;
        e.printStackTrace(System.err);
        if (e instanceof BziException){
            exception = new NetException(((BziException) e).type, e.getMessage());
        } else if (e instanceof HttpException) {
            exception = new NetException(ERROR.HTTP_ERROR, "网络错误");
        } else if (e instanceof ConnectException) {
            exception = new NetException(ERROR.NET_ERROR, "链接超时");
        } else if (e instanceof JsonParseException) {
            exception = new NetException(ERROR.PARSE_ERROR,"数据解析错误");
        } else if (e instanceof SocketTimeoutException) {
            exception = new NetException(ERROR.NET_ERROR, "网络错误");
        } else {
            exception = new NetException(ERROR.UNKNOW_ERROR, "未知错误");
        }

        return exception;
    }

    public static NetException handleException(int type, String msg) {

        return handleException(new BziException(type, msg));
    }


    public static class BziException extends Throwable{

        private int type;

        public BziException(int type, String msg) {
            super(msg);
            this.type = type;
        }

    }
}
