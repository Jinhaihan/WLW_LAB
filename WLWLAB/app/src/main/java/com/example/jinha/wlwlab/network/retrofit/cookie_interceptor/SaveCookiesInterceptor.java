package com.example.jinha.wlwlab.network.retrofit.cookie_interceptor;

import android.text.TextUtils;

import com.example.jinha.wlwlab.app.AppConfigKeys;
import com.example.jinha.wlwlab.app.ApplicationDelegate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class SaveCookiesInterceptor implements Interceptor {

    public static final String REMEMBERME = "remember-me";
    public static final String SESSION = "SESSION";

    public SaveCookiesInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        //set-cookie可能为多个
        List<String> cookieList = response.headers("set-cookie");
        if (!cookieList.isEmpty()) {
            HashMap<String, String> cookieMap = encodeCookie(cookieList);
            if (request.url().toString()
                    .equals(ApplicationDelegate.getConfiguration(AppConfigKeys.API_HOST) + "/login")) {
                String session = cookieMap.get(SESSION);
                String remember_me = cookieMap.get(REMEMBERME);
                String newCookie = compareCookie(session, remember_me);
                if (!TextUtils.isEmpty(newCookie)) {
                    CookieHandleUtil.getInstance().saveCookie(request.url().host(), newCookie, session, remember_me);
                }
            } else {
                String session = cookieMap.get(SESSION);
                String remember_me = cookieMap.get(REMEMBERME);
                if (TextUtils.isEmpty(session)) {
                    session = CookieHandleUtil.getInstance().getCookieSession();
                }
                if (TextUtils.isEmpty(remember_me)) {
                    remember_me = CookieHandleUtil.getInstance().getCookieRememberme();
                }
                String newCookie = compareCookie(session, remember_me);
                if (!TextUtils.isEmpty(newCookie)) {
                    CookieHandleUtil.getInstance().saveCookie(request.url().host(), newCookie, session, remember_me);
                }
            }
        }

        return response;
    }

    private String compareCookie(String session, String remember_me) {
        String cookie = "";
        if (!TextUtils.isEmpty(session) && !TextUtils.isEmpty(remember_me)) {
            cookie = SESSION + "=" + session + ";" + REMEMBERME + "=" + remember_me+";";
        } else if (!TextUtils.isEmpty(session)) {
            cookie = SESSION + "=" + session +";";
        } else if (!TextUtils.isEmpty(remember_me)) {
            cookie = REMEMBERME + "=" + remember_me +";";
        }
        return cookie;
    }

    private String parseCookie(String filter, String sourceCookie) {
        if (!TextUtils.isEmpty(filter) && !TextUtils.isEmpty(sourceCookie)) {
            String rules = filter + "=(\\S+)";
            Pattern pattern = Pattern.compile(rules);
            Matcher m = pattern.matcher(sourceCookie);
            while (m.find()) {
                return m.group(1);
            }
        }
        return "";
    }

    private HashMap<String, String> encodeCookie(List<String> cookies) {
        HashMap<String, String> hashMap = new HashMap<>();
        for (String cookie : cookies) {
            String[] arr = cookie.split(";");
            for (String str : arr) {
                String session = parseCookie(SESSION, str);
                if (!TextUtils.isEmpty(session)) {
                    hashMap.put(SESSION, session);
                }
                String remember_me = parseCookie(REMEMBERME, str);
                if (!TextUtils.isEmpty(remember_me)) {
                    hashMap.put(REMEMBERME, remember_me);
                }

            }
        }
        return hashMap;
    }

}
