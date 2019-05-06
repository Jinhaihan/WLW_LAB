package com.example.jinha.wlwlab.app;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationDelegate.init(this,true)
                .withApiHost("https://free-api.heweather.net/s6/weather/")
                .configure();
    }
}
