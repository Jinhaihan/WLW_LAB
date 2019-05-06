package com.example.jinha.wlwlab.network.retrofit;

import java.io.Serializable;

/**
 * @ProjectName: SportsHealth
 * @Package: cn.ac.ia.iot.healthlibrary.network.retrofit
 * @ClassName: BaseResponse
 * @Author: mebee
 * @CreateDate: 2018/8/23 10:01
 * @Description:
 */
public class BaseResponse<T> implements Serializable {

    private String result;
    private String msg;
    private T data;

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    private String msgCode;
    private String length;


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public T getData() {
        return data;
    }


    public void setData(T data) {
        this.data = data;
    }

}
