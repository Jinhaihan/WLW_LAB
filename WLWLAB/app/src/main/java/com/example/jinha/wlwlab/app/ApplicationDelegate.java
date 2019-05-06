package com.example.jinha.wlwlab.app;

import android.content.Context;


import com.tencent.mmkv.MMKV;

import java.util.logging.Handler;


/**
 * @ProjectName: SportsHealth
 * @Package: cn.ac.ia.iot.healthlibrary.app
 * @ClassName: ApplicationDelegate
 * @Author: mebee
 * @CreateDate: 2018/8/21 10:15
 * @Description: Application的代理实现
 */
public final class ApplicationDelegate {

    /**
     *
     * 初始化Application代理
     *
     * @param context
     * @return
     */
    public static AppConfigurator init(Context context, boolean isRealse) {

        MMKV.initialize(context);
        return AppConfigurator.getInstance()
                .withAppContext(context.getApplicationContext());
    }

    public static AppConfigurator getAppConfigurator() {
        return AppConfigurator.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return getAppConfigurator().getConfiguration(key);
    }


    public static Context getAppContext() {
        return getConfiguration(AppConfigKeys.APP_CONTEXT);
    }



    public static Handler getHandler() {
        return getConfiguration(AppConfigKeys.HANDLER);
    }







}
