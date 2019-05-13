package com.example.jinha.wlwlab.Fragment;
//16:12:27 收到数据：pwm_open
//16:12:28 收到数据：pwm_close
//16:12:28 收到数据：nvopen
//16:12:29 收到数据：nvclose

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
import com.example.jinha.wlwlab.base.BaseFragment;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by jinha on 2017/9/25.
 */

@SuppressLint("ValidFragment")
public class Lab_Fragment1 extends Fragment implements View.OnClickListener, BaseFragment {
    Activity activity;
    Socket socket1;
    NetThread netThread;
    Handler handler;
    Message message;
    BufferedWriter bufferedWriter;


    @SuppressLint("ValidFragment")
    public Lab_Fragment1(Activity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = activity.getLayoutInflater().inflate(R.layout.fragment_lab1,null);

        Button light_on = view.findViewById(R.id.light_on);
        Button light_off = view.findViewById(R.id.light_off);
        Button fengmingqi_on = view.findViewById(R.id.fengmingqi_on);
        Button fengmingqi_off = view.findViewById(R.id.fengmingqi_off);


        netThread = new NetThread();
        netThread.start();


        light_on.setOnClickListener(this);
        light_off.setOnClickListener(this);
        fengmingqi_on.setOnClickListener(this);
        fengmingqi_off.setOnClickListener(this);



        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.light_on:
                message = new Message();
                message.arg1 = 1;
                handler.sendMessage(message);
                Log.e("JHH","向子线程发送成功1");
                break;
            case R.id.light_off:
                message = new Message();
                message.arg1 = 2;
                handler.sendMessage(message);
                Log.e("JHH","向子线程发送成功1");
                break;
            case R.id.fengmingqi_on:
                message = new Message();
                message.arg1 = 3;
                handler.sendMessage(message);
                Log.e("JHH","向子线程发送成功1");
                break;
            case R.id.fengmingqi_off:
                message = new Message();
                message.arg1 = 4;
                handler.sendMessage(message);
                Log.e("JHH","向子线程发送成功1");
                break;

        }
    }

    @Override
    public void voiceSend() {

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
                                send("nvclose");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
                                Log.e("JHH","试图发送");
                                send("nvopen");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            try {
                                Log.e("JHH","试图发送");
                                send("pwm_open");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 4:
                            try {
                                Log.e("JHH","试图发送");
                                send("pwm_close");
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
                if(socket1 == null){
                    Toast.makeText(activity,"正在连接",Toast.LENGTH_LONG).show();
                    socket1 = new Socket("192.168.1.28", 10098);
                    Log.e("JHH","新建了Sov ckect");

                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(activity,"已连接",Toast.LENGTH_SHORT).show();
            }
        }

        public void send(String a) throws IOException {
            if(!socket1.isConnected()){
                Toast.makeText(activity,"断开，重连",Toast.LENGTH_SHORT).show();
                init();
            }
            OutputStream ops = socket1.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(ops));
            bufferedWriter.write(a);
            bufferedWriter.flush();
        }


    }
}
