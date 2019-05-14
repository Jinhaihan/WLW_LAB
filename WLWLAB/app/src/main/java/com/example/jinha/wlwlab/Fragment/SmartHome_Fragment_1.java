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

import nl.dionsegijn.steppertouch.OnStepCallback;
import nl.dionsegijn.steppertouch.StepperTouch;

/**
 * Created by jinha on 2017/9/23.
 */

public class SmartHome_Fragment_1 extends Fragment implements View.OnClickListener, BaseFragment {
    MainActivity activity;
    int yinliang;
    int pindao;

    byte[] light1_on = {0x39, 0x39, 0x01, 0x01, 0x02, 0x01, 0x2A};
    byte[] light1_off = {0x39, 0x39, 0x01, 0x01, 0x02, 0x00, 0x2A};
    byte[] fan_on = {0x39, 0x39, 0x01, 0x01, 0x01, 0x01, 0x2A};
    byte[] fan_off = {0x39, 0x39, 0x01, 0x01, 0x01, 0x00, 0x2A};
    byte[] fountain_on = {0x39, 0x39, 0x01, 0x01, 0x03, 0x01, 0x2A};
    byte[] fountain_off = {0x39, 0x39, 0x01, 0x01, 0x03, 0x00, 0x2A};
    //byte[] light2_on =
    //byte[] light2_off =

    public SmartHome_Fragment_1() {
    }

    @SuppressLint("ValidFragment")
    public SmartHome_Fragment_1(Activity activity) {
        this.activity = (MainActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = activity.getLayoutInflater().inflate(R.layout.fragment_smarthome1, null);


        Button ent_on = view.findViewById(R.id.ent_on);
        Button ent_off = view.findViewById(R.id.ent_off);
        Button mee_on = view.findViewById(R.id.mee_on);
        final Button mee_off = view.findViewById(R.id.mee_off);
        Button tvd_on = view.findViewById(R.id.tv_door_on);
        Button tvd_off = view.findViewById(R.id.tv_door_off);
        Button tv_on = view.findViewById(R.id.TV_on);
        Button tv_off = view.findViewById(R.id.TV_off);
        Button tv_01 = view.findViewById(R.id.TV_1);
        Button tv_02 = view.findViewById(R.id.TV_2);
        Button tv_03 = view.findViewById(R.id.TV_3);
        Button tv_04 = view.findViewById(R.id.TV_4);
        Button tv_05 = view.findViewById(R.id.TV_5);
        Button tv_06 = view.findViewById(R.id.TV_6);
        Button tv_07 = view.findViewById(R.id.TV_7);
        Button tv_08 = view.findViewById(R.id.TV_8);
        Button tv_09 = view.findViewById(R.id.TV_9);
        Button tv_10 = view.findViewById(R.id.TV_10);
        Button tv_11 = view.findViewById(R.id.TV_11);
        Button tv_12 = view.findViewById(R.id.TV_12);
        Button tv_13 = view.findViewById(R.id.TV_13);
        Button tv_14 = view.findViewById(R.id.TV_14);
        Button tv_15 = view.findViewById(R.id.TV_15);
        SwitchButton switchButton = view.findViewById(R.id.jingyin);

        StepperTouch stepperTouch = view.findViewById(R.id.stepperTouch);
        StepperTouch stepperTouch2 = view.findViewById(R.id.stepperTouch2);

        ent_on.setOnClickListener(this);
        ent_off.setOnClickListener(this);
        mee_on.setOnClickListener(this);
        mee_off.setOnClickListener(this);
        tvd_on.setOnClickListener(this);
        tvd_off.setOnClickListener(this);
        tv_on.setOnClickListener(this);
        tv_off.setOnClickListener(this);
        tv_01.setOnClickListener(this);
        tv_02.setOnClickListener(this);
        tv_03.setOnClickListener(this);
        tv_04.setOnClickListener(this);
        tv_05.setOnClickListener(this);
        tv_06.setOnClickListener(this);
        tv_07.setOnClickListener(this);
        tv_08.setOnClickListener(this);
        tv_09.setOnClickListener(this);
        tv_10.setOnClickListener(this);
        tv_11.setOnClickListener(this);
        tv_12.setOnClickListener(this);
        tv_13.setOnClickListener(this);
        tv_14.setOnClickListener(this);
        tv_15.setOnClickListener(this);
        stepperTouch.enableSideTap(true);
        yinliang = stepperTouch.stepper.getValue();
        stepperTouch.stepper.addStepCallback(new OnStepCallback() {
            @Override
            public void onStep(int i, boolean b) {
                String s;
                if(i>yinliang){
                  s = "v+";

                }
                else{
                    s = "v-";
                }
                yinliang = i;
                sendMessage(0,2,s);
            }
        });

        stepperTouch2.enableSideTap(true);
        pindao = stepperTouch.stepper.getValue();
        stepperTouch2.stepper.addStepCallback(new OnStepCallback() {
            @Override
            public void onStep(int i, boolean b) {
                String s;
                if(i>pindao){
                    s = "c+";

                }
                else{
                    s = "c-";
                }
                pindao = i;
                sendMessage(0,2,s);
            }
        });

        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                sendMessage(0,2,"v0");
            }
        });
        return view;
    }


    @Override
    public void onClick(View view) {
        Log.e("JHH", "有按键按下");
        //arg2的值，1是发送name=obj，2是发送name固定，data=obj
        switch (view.getId()) {
            case R.id.ent_on:
                sendMessage(1,1,"ent");
                Log.e("JHH", "向子线程发送成功1");
                break;
            case R.id.ent_off:
                sendMessage(0,1,"ent");
                break;
            case R.id.mee_on:
                sendMessage(1,1,"mee");
                break;
            case R.id.mee_off:
                sendMessage(0,1,"mee");
                break;
            case R.id.tv_door_on:
                sendMessage(1,1,"tvd");
                break;
            case R.id.tv_door_off:
                sendMessage(0,1,"tvd");
                break;
            default:
                sendMessage(1,2,view.getTag().toString());
                break;

        }
    }

    public void sendMessage(int arg1, int arg2, String target){
        //arg2的值，1是发送name=obj，2是发送name固定，data=obj
        String s;
        JSONBean jsonBean = new JSONBean();
        switch (arg2){
            case 1:
                jsonBean.setName(target);
                jsonBean.setOp("write");
                if (arg1 == 1) jsonBean.setData("on");
                else jsonBean.setData("off");
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
        activity.sendMessage(NetService.SmartHome,s);
    }

    public void tv_btn_onclick(View view){
        Toast.makeText(activity,"aaa",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void voiceSend(VoiceCommandBean voiceCommandBean) {

    }

}
