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

import com.example.jinha.wlwlab.R;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by jinha on 2017/9/23.
 */

public class Agriculture_Fragment_1 extends Fragment implements View.OnClickListener {
    Activity activity;
    Socket socket1;
    Socket socket2;
    NetThread netThread;
    Handler handler;
    Message message;

    byte[] light1_on = {0x39,0x39,0x01,0x01,0x02,0x01,0x2A };
    byte[] light1_off = {0x39,0x39,0x01,0x01,0x02,0x00,0x2A };
    byte[] fan_on = {0x39,0x39,0x01,0x01,0x01,0x01,0x2A };
    byte[] fan_off = {0x39,0x39,0x01,0x01,0x01,0x00,0x2A };
    byte[] fountain_on = {0x39,0x39,0x01,0x01,0x03,0x01,0x2A };
    byte[] fountain_off = {0x39,0x39,0x01,0x01,0x03,0x00,0x2A };
    //byte[] light2_on =
    //byte[] light2_off =

    public Agriculture_Fragment_1(){}

    @SuppressLint("ValidFragment")
    public Agriculture_Fragment_1(Activity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = activity.getLayoutInflater().inflate(R.layout.fragment_agriculture1,null);

        netThread = new NetThread();
        netThread.start();



        Button light1_on = view.findViewById(R.id.light1_on);
        Button light1_off = view.findViewById(R.id.light1_off);
        Button fan_on = view.findViewById(R.id.fan_on);
        Button fan1_off = view.findViewById(R.id.fan_off);
        Button fountain_on = view.findViewById(R.id.fountain_on);
        Button fountain_off = view.findViewById(R.id.fountain_off);
        Button light2_on = view.findViewById(R.id.light2_on);
        Button light2_off = view.findViewById(R.id.light2_off);

        light1_on.setOnClickListener(this);
        light1_off.setOnClickListener(this);
        fan_on.setOnClickListener(this);
        fan1_off.setOnClickListener(this);
        fountain_on.setOnClickListener(this);
        fountain_off.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View view) {
        Log.e("JHH","有按键按下");
        switch (view.getId()){
            case R.id.light1_on:
                message = new Message();
                message.arg1 = 1;
                handler.sendMessage(message);
                Log.e("JHH","向子线程发送成功1");
                break;
            case R.id.light1_off:
                message = new Message();
                message.arg1 = 2;
                handler.sendMessage(message);
                Log.e("JHH","向子线程发送成功2");
                break;
            case R.id.fan_on:
                message = new Message();
                message.arg1 = 3;
                handler.sendMessage(message);
                Log.e("JHH","向子线程发送成功3");
                break;
            case R.id.fan_off:
                message = new Message();
                message.arg1 = 4;
                handler.sendMessage(message);
                Log.e("JHH","向子线程发送成功4");
                break;
            case R.id.fountain_on:
                message = new Message();
                message.arg1 = 5;
                handler.sendMessage(message);
                Log.e("JHH","向子线程发送成功5");
                break;
            case R.id.fountain_off:
                message = new Message();
                message.arg1 = 6;
                handler.sendMessage(message);
                Log.e("JHH","向子线程发送成功6");
                break;
            case R.id.light2_on:
            case R.id.light2_off:
        }
    }

    public class NetThread extends Thread{
        @Override
        public void run() {
            super.run();
            Looper.prepare();
            init();

            Log.e("JHH","开始新建Handler");
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    Log.e("JHH","子线程收到消息");
                    super.handleMessage(msg);
                    switch (msg.arg1){
                        case 1:
                            try {
                                Log.e("JHH","试图发送");
                                send(light1_on);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
                                Log.e("JHH","试图发送");
                                send(light1_off);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            try {
                                Log.e("JHH","试图发送");
                                send(fan_on);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 4:
                            try {
                                Log.e("JHH","试图发送");
                                send(fan_off);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 5:
                            try {
                                Log.e("JHH","试图发送");
                                send(fountain_on);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 6:
                            try {
                                Log.e("JHH","试图发送");
                                send(fountain_off);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
            };
            Looper.loop();
        }
        public void init(){
            try {
                        Toast.makeText(activity,"正在连接",Toast.LENGTH_LONG).show();
                        socket1 = new Socket("192.168.1.159", 8080);//192.168.1.159  49.122.47.231

                        Log.e("JHH","新建了Sockect");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void send(byte[] a) throws IOException {
            if(socket1 == null || !socket1.isConnected()){
                    Toast.makeText(activity,"断开，重连",Toast.LENGTH_SHORT).show();
                    init();
            }
            OutputStream ops = socket1.getOutputStream();
            ops.write(a);
            ops.flush();
        }


    }
}
