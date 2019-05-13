package com.example.jinha.wlwlab.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.example.jinha.wlwlab.R;
import com.example.jinha.wlwlab.callback.VoiceFinishCallBack;
import com.example.voicereco.mini.AutoCheck;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class VoiceDialog extends Dialog implements EventListener {
    private EventManager asr;
    private boolean logTime = true;
    VoiceFinishCallBack voiceFinishCallBack;

    protected boolean enableOffline = false; // 测试离线命令词，需要改成true

    public VoiceDialog(@NonNull Context context, VoiceFinishCallBack voiceFinishCallBack) {
        super(context);
        this.voiceFinishCallBack = voiceFinishCallBack;
    }

    public VoiceDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected VoiceDialog(@NonNull Context context, boolean cancelable, @Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_voice);

        // 基于sdk集成1.1 初始化EventManager对象
        asr = EventManagerFactory.create(getContext(), "asr");
        // 基于sdk集成1.3 注册自己的输出事件类
        asr.registerListener(this); //  EventListener 中 onEvent方法

        start();
    }

    String last;
    @Override
    public void onEvent(String s, String s1, byte[] bytes, int i, int i1) {
        Log.e("VoiceDialog", "Evenr: " + s);
        switch (s) {
            case "asr.partial":
                last = s1;
                break;
            case "asr.finish":
                voiceFinishCallBack.success(last);
                break;
            case "asr.exit":
                stop();
                dismiss();
                break;
        }
    }

    /**
     * 基于SDK集成2.2 发送开始事件
     * 点击开始按钮
     * 测试参数填在这里
     */
    private void start() {
        //txtLog.setText("");
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        String event = null;
        event = SpeechConstant.ASR_START; // 替换成测试的event

        if (enableOffline) {
            params.put(SpeechConstant.DECODER, 2);
        }
        // 基于SDK集成2.1 设置识别参数
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
        // params.put(SpeechConstant.NLU, "enable");
        // params.put(SpeechConstant.VAD_ENDPOINT_TIMEOUT, 0); // 长语音
        // params.put(SpeechConstant.IN_FILE, "res:///com/baidu/android/voicedemo/16k_test.pcm");
        // params.put(SpeechConstant.VAD, SpeechConstant.VAD_DNN);
        // params.put(SpeechConstant.PID, 1537); // 中文输入法模型，有逗号
        // 请先使用如‘在线识别’界面测试和生成识别参数。 params同ActivityRecog类中myRecognizer.start(params);
        // 复制此段可以自动检测错误
        (new AutoCheck(getContext(), new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 100) {
                    AutoCheck autoCheck = (AutoCheck) msg.obj;
                    synchronized (autoCheck) {
                        String message = autoCheck.obtainErrorMessage(); // autoCheck.obtainAllMessage();
                        //txtLog.append(message + "\n");
                        ; // 可以用下面一行替代，在logcat中查看代码
                        // Log.w("AutoCheckMessage", message);
                    }
                }
            }
        },enableOffline)).checkAsr(params);
        String json = null; // 可以替换成自己的json
        json = new JSONObject(params).toString(); // 这里可以替换成你需要测试的json
        asr.send(event, json, null, 0, 0);

    }

    /**
     * 点击停止按钮
     *  基于SDK集成4.1 发送停止事件
     */
    private void stop() {
        //printLog("停止识别：ASR_STOP");
        asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0); //
    }

    @Override
    public void dismiss() {
        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
        asr.unregisterListener(this);
        super.dismiss();
    }
}
