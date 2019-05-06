package com.example.jinha.wlwlab.network.retrofit;

/**
 * @ProjectName: SportsHealth
 * @Package: cn.ac.ia.iot.healthlibrary.network.retrofit
 * @ClassName: NetException
 * @Author: mebee
 * @CreateDate: 2018/8/23 10:54
 * @Description: 自定义网络错误类
 */
public class NetException{
    private int code;
    private String msg;

    public NetException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
