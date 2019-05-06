package com.example.jinha.wlwlab.weather;

import com.example.jinha.wlwlab.weather.bean.WeatherBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * @author JHH
 */
public interface WeahterService {

    @GET("now?key=613a5123d89a4f64b9eb3a62580f0d8b")
    Observable<WeatherBean> getUserInfo(@Query("location") String location);

}
