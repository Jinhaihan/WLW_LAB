package com.example.jinha.wlwlab;

import com.example.jinha.wlwlab.app.Constant;
import com.example.jinha.wlwlab.bean.JSONBean;
import com.google.gson.Gson;

import io.reactivex.CompletableOnSubscribe;

public class GetCommand {
    public static String getString(int arg1, int arg2, String target){
        //arg2=1是name=obj，2是发送name固定为“tvp”，data=obj，这是为了遥控器指令
        String s;
        JSONBean jsonBean = new JSONBean();
        switch (arg2){
            case 1:
                jsonBean.setName(target);
                jsonBean.setOp("write");
                if(arg1 == Constant.ON){
                    jsonBean.setData("on");
                }
                else if(arg1 == Constant.OFF) jsonBean.setData("off");
                else if(arg1 == Constant.ALL_ON) jsonBean.setData("allon");
                else if(arg1 == Constant.ALL_OFF) jsonBean.setData("alloff");
                s = new Gson().toJson(jsonBean);
                break;
            case 2:
                jsonBean.setName("tvp");
                jsonBean.setOp("write");
                jsonBean.setData(target);
                s = new Gson().toJson(jsonBean);
                break;
            default:
                s = "";
                break;
        }
        return s;
    }
}
