package com.example.jinha.wlwlab.bean;

/**
 * 语音识别结果解析出来的语音指令
 */
public class VoiceCommandBean {
    public String op; //操作类型，打开、关闭、切换（切换仅代表电视节目）
    public String tar; //目标，操作仅代表目标
}
