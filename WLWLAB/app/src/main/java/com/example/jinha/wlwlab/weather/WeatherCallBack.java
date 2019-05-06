package com.example.jinha.wlwlab.weather;

import com.example.jinha.wlwlab.weather.bean.WeatherBean;

public interface WeatherCallBack {
    void success(WeatherBean w);
    void failed();
}
