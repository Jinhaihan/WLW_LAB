package com.example.jinha.wlwlab.Fragment;
//
// {39 39 01 01 02 01 2A }开灯
// {39 39 01 01 02 00 2A }关灯
// {39 39 01 01 01 01 2A }开风扇
// {39 39 01 01 01 00 2A }关风扇
// {39 39 01 01 03 01 2A }开喷水
// {39 39 01 01 03 00 2A }关喷水。

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.jinha.wlwlab.MainActivity;
import com.example.jinha.wlwlab.app.Configuration;
import com.example.jinha.wlwlab.bean.JSONBean;
import com.example.jinha.wlwlab.R;
import com.example.jinha.wlwlab.base.BaseFragment;
import com.example.jinha.wlwlab.bean.VoiceCommandBean;
import com.example.jinha.wlwlab.network.NetService;
import com.google.gson.Gson;
import com.suke.widget.SwitchButton;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by jinha on 2017/9/23.
 */

public class SmartHome_Fragment_2 extends Fragment implements BaseFragment, View.OnClickListener,SwitchButton.OnCheckedChangeListener{
    MainActivity activity;

    Button led_AllOn;
    Button led_AllOff;

    SwitchButton led01;
    SwitchButton led02;
    SwitchButton led03;
    SwitchButton led04;
    SwitchButton led05;
    SwitchButton led06;
    SwitchButton led07;
    SwitchButton led08;
    SwitchButton led09;

    byte[] light1_on = {0x39,0x39,0x01,0x01,0x02,0x01,0x2A };
    byte[] light1_off = {0x39,0x39,0x01,0x01,0x02,0x00,0x2A };
    byte[] fan_on = {0x39,0x39,0x01,0x01,0x01,0x01,0x2A };
    byte[] fan_off = {0x39,0x39,0x01,0x01,0x01,0x00,0x2A };
    byte[] fountain_on = {0x39,0x39,0x01,0x01,0x03,0x01,0x2A };
    byte[] fountain_off = {0x39,0x39,0x01,0x01,0x03,0x00,0x2A };


    public SmartHome_Fragment_2(){}

    @SuppressLint("ValidFragment")
    public SmartHome_Fragment_2(Activity activity){
        this.activity = (MainActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = activity.getLayoutInflater().inflate(R.layout.fragment_smarthome2,null);

        led01 = view.findViewById(R.id.led01);
        led02 = view.findViewById(R.id.led02);
        led03 = view.findViewById(R.id.led03);
        led04 = view.findViewById(R.id.led04);
        led05 = view.findViewById(R.id.led05);
        led06 = view.findViewById(R.id.led06);
        led07 = view.findViewById(R.id.led07);
        led08 = view.findViewById(R.id.led08);
        led09 = view.findViewById(R.id.led09);
        Button window_on = view.findViewById(R.id.window_on);
        Button window_off = view.findViewById(R.id.window_off);
        Button curtains_on = view.findViewById(R.id.curtains_on);
        Button curtains_off = view.findViewById(R.id.curtains_off);

        led01.setOnCheckedChangeListener(this);
        led02.setOnCheckedChangeListener(this);
        led03.setOnCheckedChangeListener(this);
        led04.setOnCheckedChangeListener(this);
        led05.setOnCheckedChangeListener(this);
        led06.setOnCheckedChangeListener(this);
        led07.setOnCheckedChangeListener(this);
        led08.setOnCheckedChangeListener(this);
        led09.setOnCheckedChangeListener(this);



        led_AllOn = view.findViewById(R.id.led_on);
        led_AllOff = view.findViewById(R.id.led_off);


        led_AllOn.setOnClickListener(this);
        led_AllOff.setOnClickListener(this);
        window_on.setOnClickListener(this);
        window_off.setOnClickListener(this);
        curtains_on.setOnClickListener(this);
        curtains_off.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View view) {
        Log.e("JHH","有按键按下");
        switch (view.getId()){
            case R.id.led_on:
                sendMessage(3,"led01");
                break;
            case R.id.led_off:
                sendMessage(4,"led01");
                break;
            case R.id.curtains_on:
                sendMessage(1,view.getTag().toString());
                break;
            case R.id.curtains_off:
                sendMessage(2,view.getTag().toString());
                break;
            case R.id.window_on:
                sendMessage(1,view.getTag().toString());
                break;
            case R.id.window_off:
                sendMessage(2,view.getTag().toString());
                break;
        }
    }

    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
        if(isChecked){
            sendMessage(1,view.getTag().toString());
        }
        else {
            sendMessage(2,view.getTag().toString());
        }
    }

    public void sendMessage(int args, String target){
        JSONBean jsonBean = new JSONBean();
        jsonBean.setName(target);
        jsonBean.setOp("write");
        if(args == 1){
            jsonBean.setData("on");
        }
        else if(args == 2) jsonBean.setData("off");
        else if(args == 3) jsonBean.setData("allon");
        else if(args == 4) jsonBean.setData("alloff");
        String s = new Gson().toJson(jsonBean);
        Log.e("JHH",s);
        activity.sendMessage(NetService.SmartHome,s);
    }

    @Override
    public void voiceSend(VoiceCommandBean voiceCommandBean) {
        int arg = 0;
        String target;
        switch (voiceCommandBean.op){
            case "打开":
                arg = 1;
                break;
            case "关闭":
                arg = 2;
                break;
        }
        switch (voiceCommandBean.tar){
            case "厨房灯一":
                target = "led05";
                break;
            default:
                target = "";
                break;
        }
        Log.e("SmartHomeFragment2", "voiceSend: "+ arg +"  "+ target );
        sendMessage(arg,target);
    }
}
