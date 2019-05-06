package com.example.jinha.wlwlab.app;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Interceptor;

/**
 * @ProjectName: SportsHealth
 * @Package: cn.ac.ia.iot.healthlibrary.app
 * @ClassName: AppConfigurator
 * @Author: mebee
 * @CreateDate: 2018/8/21 10:13
 * @Description:
 */
public class AppConfigurator {

    private static final HashMap<Object, Object> APP_CONFIGS = new HashMap<>();
    private static final Handler HANDLER = new Handler();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();


    /**
     *
     * 获取配置单例
     *
     * @return
     */
    public static AppConfigurator getInstance() {
        return Holder.INSTANCE;
    }

    /**
     *
     * 配置全局Context (ApplicationContext)
     *
     * @param applicationContext
     * @return
     */
    public AppConfigurator withAppContext(Context applicationContext) {
        APP_CONFIGS.put(AppConfigKeys.APP_CONTEXT, applicationContext);
        return this;
    }

    private static class Holder{
        private static final AppConfigurator INSTANCE = new AppConfigurator();
    }

    private AppConfigurator() {
        // 默认设置未配置完成
        APP_CONFIGS.put(AppConfigKeys.CONFIG_READY, false);
        APP_CONFIGS.put(AppConfigKeys.HANDLER,HANDLER);
    }


    /**
     * 完成配置
     */
    public final void configure() {
        APP_CONFIGS.put(AppConfigKeys.CONFIG_READY, true);
    }

    public final HashMap<Object, Object> getAppConfigs() {
        return APP_CONFIGS;
    }

    public final AppConfigurator withApiHost(String host){
        APP_CONFIGS.put(AppConfigKeys.API_HOST, host);
        return this;
    }
//    public final AppConfigurator withApiHost(String domainName,String host){
//        RetrofitUrlManager.getInstance().putDomain(domainName, host);
//        return this;
//    }

    /**
     *
     * 设置主Activity
     *
     * @param activity
     * @return
     */
    public final AppConfigurator withActivity(Activity activity) {
        APP_CONFIGS.put(AppConfigKeys.ACTIVITY, activity);
        return this;
    }

    /**
     * 为网络请求添加自己的拦截器 （暂时没有这个需求）
     * @param interceptor
     * @return
     */
    public final AppConfigurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        APP_CONFIGS.put(AppConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final AppConfigurator withInterceptors(List<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        APP_CONFIGS.put(AppConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }


    /**
     * 检查是否已经配置完成
     */
    private void checkConfiguration() {
        final boolean isReady = (boolean) APP_CONFIGS.get(AppConfigKeys.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("App Configuration is not ready, configure must be call");
        }
    }

    /**
     *
     * 根据Key获取具体配置
     *
     * @param key
     * @param <T>
     * @return
     */
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = APP_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + "is Null");
        }

        return (T) value;
    }
}
