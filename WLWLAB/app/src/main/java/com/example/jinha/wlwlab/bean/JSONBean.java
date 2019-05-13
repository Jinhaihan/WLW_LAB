package com.example.jinha.wlwlab.bean;

public class JSONBean {

    /**
     * Name : light_1                     //控制名称
     * frequency : 1                      //1 315   2是433   3是红外
     * ctrl : 1                           //控制类型   1控制类   0感知类
     * operation_type : byte              //指令类型，字符串或16进制
     * operation : F1 28 15 35 35 13      //具体指令，直接转发对应串口
     */

    private String name;
    private String op;
    private String data;
    private String frequency;
    private String ctrl;
    private String operation_type;
    private byte operation[];

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getCtrl() {
        return ctrl;
    }

    public void setCtrl(String ctrl) {
        this.ctrl = ctrl;
    }

    public String getOperation_type() {
        return operation_type;
    }

    public void setOperation_type(String operation_type) {
        this.operation_type = operation_type;
    }

    public byte[] getOperation() {
        return operation;
    }

    public void setOperation(byte operation[]) {
        this.operation = operation;
    }
}
