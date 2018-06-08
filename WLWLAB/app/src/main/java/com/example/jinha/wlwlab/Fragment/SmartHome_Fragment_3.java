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
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.jinha.wlwlab.JSONBean;
import com.example.jinha.wlwlab.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by jinha on 2017/9/23.
 */

public class SmartHome_Fragment_3 extends Fragment implements View.OnClickListener {
    Activity activity;
    Socket socket1;
    NetThread netThread;
    Handler handler;
    Message message;

    RadioGroup radioGroup;
    EditText ip;
    EditText port;
    EditText name;
    //EditText op;
    EditText data;
    String ip_Text;
    String port_Text;
    String name_Text;
    String op_Text;
    String data_Text;

    public SmartHome_Fragment_3(){}

    @SuppressLint("ValidFragment")
    public SmartHome_Fragment_3(Activity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = activity.getLayoutInflater().inflate(R.layout.fragment_smarthome3,null);

        netThread = new NetThread();
        netThread.start();

        Button button = view.findViewById(R.id.commit_btn);
        button.setOnClickListener(this);

        ip = view.findViewById(R.id.opip);
        port = view.findViewById(R.id.opport);
        name = view.findViewById(R.id.opname);
        //op = view.findViewById(R.id.optext);
        radioGroup = view.findViewById(R.id.setopGroup);
        data = view.findViewById(R.id.opdata);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.write)
                    op_Text = "write";
                else op_Text = "read";
            }
        });
        return view;
    }


    @Override
    public void onClick(View view) {
        Log.e("JHH","有按键按下");
        switch (view.getId()){
            case R.id.commit_btn:
                ip_Text = ip.getText().toString();
                port_Text = port.getText().toString();
                name_Text = name.getText().toString();
                data_Text = data.getText().toString();
                if(!name_Text.equals("") && !op_Text.equals("") && !data_Text.equals("") && !ip_Text.equals("") && !port_Text.equals("")){
                    Log.e("填写字段：" , ip_Text);
                    Log.e("填写字段：" , port_Text);
                    Log.e("填写字段：" , name_Text);
                    Log.e("填写字段：" , op_Text);
                    Log.e("填写字段：" , data_Text);
                    message = new Message();
                    message.obj = "btn_down";
                    handler.sendMessage(message);
                }
                else {
                    Toast.makeText(activity,"信息不完整",Toast.LENGTH_SHORT).show();
                }
                Log.e("JHH","向子线程发送成功1");
                break;
        }
    }

    public class NetThread extends Thread{
        @SuppressLint("HandlerLeak")
        @Override
        public void run() {
            super.run();
            Looper.prepare();

            Log.e("JHH","开始新建Handler");
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    Log.e("JHH","子线程收到消息");
                    super.handleMessage(msg);
                    String str = (String) msg.obj;
                    switch (str){
                        case "btn_down":
                            try {
                                Log.e("JHH","试图发送");
                                JSONBean jsonBean = new JSONBean();
                                jsonBean.setName(name_Text);
                                jsonBean.setOp(op_Text);
                                jsonBean.setData(data_Text);
                                String s = new Gson().toJson(jsonBean);
                                Log.e("JHH",s);
                                send(s);
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
                if(!ip_Text.equals("") && !port_Text.equals("")){
                    Toast.makeText(activity,"正在连接",Toast.LENGTH_SHORT).show();
                    socket1 = new Socket(ip_Text,Integer.valueOf(port_Text));
                    Log.e("JHH","新建了Sockect");
                }
                else Toast.makeText(activity,"请检查IP端口是否正确",Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(activity,"错误，检查IP端口是否正确",Toast.LENGTH_LONG).show();
            }
        }

        public void send(String a) throws IOException {
            if(socket1 == null || !socket1.isConnected()){
                    Toast.makeText(activity,"断开，重连",Toast.LENGTH_SHORT).show();
                    init();
            }
            OutputStream ops = socket1.getOutputStream();
            //ops.write(a);
            PrintWriter pw=new PrintWriter(ops);
            pw.write(a);
            pw.flush();
            //socket1.shutdownOutput();
            ops.flush();
        }


    }
}
