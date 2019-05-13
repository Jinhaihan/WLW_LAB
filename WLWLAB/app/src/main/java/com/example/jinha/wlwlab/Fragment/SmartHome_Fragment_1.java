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

import com.example.jinha.wlwlab.app.Configuration;
import com.example.jinha.wlwlab.JSONBean;
import com.example.jinha.wlwlab.R;
import com.example.jinha.wlwlab.base.BaseFragment;
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
    Activity activity;
    Socket socket1;
    Socket socket2;
    NetThread netThread;
    Handler handler;
    Message message;
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
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = activity.getLayoutInflater().inflate(R.layout.fragment_smarthome1, null);

        netThread = new NetThread();
        netThread.start();

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
                Message message = new Message();
                if(i>yinliang){
                  message.obj = "v+";

                }
                else{
                    message.obj = "v-";
                }
                yinliang = i;
                message.arg2 = 2;
                handler.sendMessage(message);
            }
        });

        stepperTouch2.enableSideTap(true);
        pindao = stepperTouch.stepper.getValue();
        stepperTouch2.stepper.addStepCallback(new OnStepCallback() {
            @Override
            public void onStep(int i, boolean b) {
                Message message = new Message();
                if(i>pindao){
                    message.obj = "c+";

                }
                else{
                    message.obj = "c-";
                }
                pindao = i;
                message.arg2 = 2;
                handler.sendMessage(message);
            }
        });

        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                Message message = new Message();
                message.obj= "v0";
                message.arg2 = 2;
                handler.sendMessage(message);
            }
        });
        return view;
    }


    @Override
    public void onClick(View view) {
        Log.e("JHH", "有按键按下");
        switch (view.getId()) {
            case R.id.ent_on:
                message = new Message();
                message.obj = "ent";
                message.arg1 = 1;
                message.arg2 = 1;//1是发送name=obj，2是发送name固定，data=obj
                handler.sendMessage(message);
                Log.e("JHH", "向子线程发送成功1");
                break;
            case R.id.ent_off:
                message = new Message();
                message.obj = "ent";
                message.arg1 = 0;
                message.arg2 = 1;//1是发送name=obj，2是发送name固定，data=obj
                handler.sendMessage(message);
                break;
            case R.id.mee_on:
                message = new Message();
                message.obj = "mee";
                message.arg1 = 1;
                message.arg2 = 1;//1是发送name=obj，2是发送name固定，data=obj
                handler.sendMessage(message);
                break;
            case R.id.mee_off:
                message = new Message();
                message.obj = "mee";
                message.arg1 = 0;
                message.arg2 = 1;//1是发送name=obj，2是发送name固定，data=obj
                handler.sendMessage(message);
                break;
            case R.id.tv_door_on:
                message = new Message();
                message.obj = "tvd";
                message.arg1 = 1;
                message.arg2 = 1;//1是发送name=obj，2是发送name固定，data=obj
                handler.sendMessage(message);
                break;
            case R.id.tv_door_off:
                message = new Message();
                message.obj = "tvd";
                message.arg1 = 0;
                message.arg2 = 1;//1是发送name=obj，2是发送name固定，data=obj
                handler.sendMessage(message);
                break;
            default:
                message = new Message();
                message.obj = view.getTag();
                //message.
                message.arg1 = 1;
                message.arg2 = 2;//1是发送name=obj，2是发送name固定，data=obj
                handler.sendMessage(message);
                break;

        }
    }

    public void tv_btn_onclick(View view){
        Toast.makeText(activity,"aaa",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void voiceSend() {

    }

    public class NetThread extends Thread {
        @SuppressLint("HandlerLeak")
        @Override
        public void run() {
            super.run();
            Looper.prepare();

            Log.e("JHH", "开始新建Handler");
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    Log.e("JHH", "子线程收到消息");
                    super.handleMessage(msg);
                    String str = (String) msg.obj;
                    switch (msg.arg2){
                        case 1:
                            try {
                                Log.e("JHH", "试图发送");
                                JSONBean jsonBean = new JSONBean();
                                jsonBean.setName(str);
                                jsonBean.setOp("write");
                                if (msg.arg1 == 1) jsonBean.setData("on");
                                else jsonBean.setData("off");
//                                jsonBean.setCtrl("1");
//                                jsonBean.setFrequency("1");
//                                jsonBean.setOperation_type("byte");
//                                jsonBean.setOperation(SmartHome_Fragment.configuration.MoiveCenter_off);
                                String s = new Gson().toJson(jsonBean);
                                Log.e("JHH", s);
                                send(s);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
                                Log.e("JHH", "试图发送");
                                JSONBean jsonBean = new JSONBean();
                                jsonBean.setName("tvp");
                                jsonBean.setOp("write");
                                jsonBean.setData(str);
//                                jsonBean.setCtrl("1");
//                                jsonBean.setFrequency("1");
//                                jsonBean.setOperation_type("byte");
//                                jsonBean.setOperation(SmartHome_Fragment.configuration.MoiveCenter_off);
                                String s = new Gson().toJson(jsonBean);
                                Log.e("JHH", s);
                                send(s);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }



                }
            };
            init();
            Looper.loop();
        }

        public void init() {
            try {
                Toast.makeText(activity, "正在连接", Toast.LENGTH_LONG).show();
                socket1 = new Socket(Configuration.SMARTHOME_IP, 10100);
                Log.e("JHH", "新建了Sockect");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void send(String a) throws IOException {
            if (socket1 == null || !socket1.isConnected()) {
                Toast.makeText(activity, "未连接，重连", Toast.LENGTH_SHORT).show();
                init();
                return;
            }
            OutputStream ops = socket1.getOutputStream();
            PrintWriter pw = new PrintWriter(ops);
            pw.write(a);
            pw.flush();
            ops.flush();
        }


    }
}
