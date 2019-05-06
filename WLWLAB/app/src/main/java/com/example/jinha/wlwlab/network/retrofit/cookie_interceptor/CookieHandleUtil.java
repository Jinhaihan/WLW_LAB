package com.example.jinha.wlwlab.network.retrofit.cookie_interceptor;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.tencent.mmkv.MMKV;

import static com.example.jinha.wlwlab.network.retrofit.cookie_interceptor.AddCookiesInterceptor.COOKIE_PREF;
import static com.example.jinha.wlwlab.network.retrofit.cookie_interceptor.SaveCookiesInterceptor.REMEMBERME;
import static com.example.jinha.wlwlab.network.retrofit.cookie_interceptor.SaveCookiesInterceptor.SESSION;

public class CookieHandleUtil {


    private CookieHandleUtil() {
    }

    private static class SingleTonHoler {
        private static CookieHandleUtil INSTANCE = new CookieHandleUtil();
    }

    public static CookieHandleUtil getInstance() {
        return SingleTonHoler.INSTANCE;
    }

    //保存cookie到本地，设置相同的host
    public void saveCookie(String domain, String cookies, String session, String remember_me) {
        synchronized (this) {
            MMKV sp = MMKV.mmkvWithID(COOKIE_PREF);
            SharedPreferences.Editor editor = sp.edit();

            if (!TextUtils.isEmpty(domain) && !TextUtils.isEmpty(cookies)) {
                editor.putString(domain, cookies);
            }
            if (!TextUtils.isEmpty(session)) {
                editor.putString(SESSION, session);
            }
            if (!TextUtils.isEmpty(remember_me)) {
                editor.putString(REMEMBERME, remember_me);
            }
            //MMKV 无需调用 apply()
            //editor.apply();
        }
    }

    public String getCookie(String domain) {
        synchronized (this) {
            MMKV sp = MMKV.mmkvWithID(COOKIE_PREF);
            if (!TextUtils.isEmpty(domain) && sp.contains(domain)) {
                String cookie = sp.getString(domain, "");
                if (!TextUtils.isEmpty(cookie)) {
                    return cookie;
                }
            }
            return null;
        }
    }

    public String getCookieSession() {
        MMKV sp = MMKV.mmkvWithID(COOKIE_PREF);
        if (sp.contains(SESSION)) {
            String session = sp.getString(SESSION, "");
            if (!TextUtils.isEmpty(session)) {
                return session;
            }
        }
        return null;
    }

    public String getCookieRememberme() {
        MMKV sp = MMKV.mmkvWithID(COOKIE_PREF);
        if (sp.contains(REMEMBERME)) {
            String rememberme = sp.getString(REMEMBERME, "");
            if (!TextUtils.isEmpty(rememberme)) {
                return rememberme;
            }
        }
        return null;
    }

}
