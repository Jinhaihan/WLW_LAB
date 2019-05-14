package com.example.jinha.wlwlab.network;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.jinha.wlwlab.app.Configuration;
import com.example.jinha.wlwlab.bean.JSONBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class NetService extends Service {
    public static int SmartHome = 1;

    Socket smartHomeSocket = null;
    Handler handler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new NetBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        NetThread netThread = new NetThread();
        netThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void initSocket(){
        try {
            smartHomeSocket = new Socket(Configuration.SMARTHOME_IP,Configuration.SMARTHOME_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String a) throws IOException {
        if(smartHomeSocket == null || !smartHomeSocket.isConnected()){
            Toast.makeText(getApplicationContext(),"断开，重连",Toast.LENGTH_SHORT).show();
            initSocket();
        }
        OutputStream ops = smartHomeSocket.getOutputStream();
        PrintWriter pw=new PrintWriter(ops);
        pw.write(a);
        pw.flush();
        ops.flush();
    }

    public class NetBinder extends Binder{
        public void sendMessage(int type, String msg){
            if(type == SmartHome){
                Message message = new Message();
                message.obj = msg;
                handler.sendMessage(message);
            }
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
                    try{
                        send(str);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }
            };
            initSocket();
            Looper.loop();
        }
    }
}
