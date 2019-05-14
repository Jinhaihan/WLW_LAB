package com.example.jinha.wlwlab.voice;

import com.example.jinha.wlwlab.GetCommand;
import com.example.jinha.wlwlab.app.Constant;
import com.example.jinha.wlwlab.bean.VoiceBean;
import com.example.jinha.wlwlab.bean.VoiceCommandBean;

import io.reactivex.CompletableOnSubscribe;

public class VoiceCommand {
    public static String toJSON(VoiceCommandBean voiceCommandBean){
        int arg1 = 0;
        int arg2 = 1;
        String target;
        switch (voiceCommandBean.op){
            case "打开":
                arg1 = Constant.ON;
                arg2 = Constant.OBJ_NAME;
                break;
            case "关闭":
                arg1 = Constant.OFF;
                arg2 = Constant.OBJ_NAME;
                break;
        }
        switch (voiceCommandBean.tar){
            case "门外灯一":
                target = "led01";
                break;
            case "门外灯二":
                target = "led02";
                break;
            case "门外灯三":
                target = "led03";
                break;
            case "走廊灯":
                target = "led04";
                break;
            case "厨房灯一":
                target = "led05";
                break;
            case "厨房灯二":
                target = "led06";
                break;
            case "后门灯一":
                target = "led07";
                break;
            case "后门灯二":
                target = "led08";
                break;
            case "后门灯三":
                target = "led09";
                break;
            case "窗帘":
                target = "cur";
                break;
            case "窗户":
                target = "window";
                break;
            case "所有灯":
                if(arg1 == Constant.ON){
                    arg1 = Constant.ALL_ON;
                }
                else if(arg1 == Constant.OFF){
                    arg1 = Constant.ALL_OFF;
                }
                target = "led01";
                break;
            default:
                target = "";
                break;
        }
        return GetCommand.getString(arg1,arg2,target);
    }

}
