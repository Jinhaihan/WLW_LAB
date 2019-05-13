package com.example.jinha.wlwlab.callback;

import com.example.jinha.wlwlab.bean.VoiceBean;

public interface VoiceFinishCallBack {
    void success(VoiceBean result);
    void failed();
}
