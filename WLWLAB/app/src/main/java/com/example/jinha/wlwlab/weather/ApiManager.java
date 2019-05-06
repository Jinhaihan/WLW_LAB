package com.example.jinha.wlwlab.weather;

import com.example.jinha.wlwlab.weather.bean.WeatherBean;
import com.example.jinha.wlwlab.weather.dynamicweather.BaseDrawer;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author JHH on 2019/3/22
 */
public class ApiManager {
    /**
     * 把Weather转换为对应的BaseDrawer.Type
     *
     * @param weather
     * @return
     */
    public static BaseDrawer.Type convertWeatherType(final WeatherBean.HeWeather6Bean weather) {
        if (weather == null) {
            return BaseDrawer.Type.DEFAULT;
        }
        final boolean isNight = isNight();
        try {
            final int w = Integer.valueOf(weather.getNow().getCond_code());
            switch (w) {
                case 100:
                    return isNight ? BaseDrawer.Type.CLEAR_N : BaseDrawer.Type.CLEAR_D;
                case 101:// 多云
                case 102:// 少云
                case 103:// 晴间多云
                    return isNight ? BaseDrawer.Type.CLOUDY_N : BaseDrawer.Type.CLOUDY_D;
                case 104:// 阴
                    return isNight ? BaseDrawer.Type.OVERCAST_N : BaseDrawer.Type.OVERCAST_D;
                // 200 - 213是风
                case 200:
                case 201:
                case 202:
                case 203:
                case 204:
                case 205:
                case 206:
                case 207:
                case 208:
                case 209:
                case 210:
                case 211:
                case 212:
                case 213:
                    return isNight ? BaseDrawer.Type.WIND_N : BaseDrawer.Type.WIND_D;
                case 300:// 阵雨Shower Rain
                case 301:// 强阵雨 Heavy Shower Rain
                case 302:// 雷阵雨 Thundershower
                case 303:// 强雷阵雨 Heavy Thunderstorm
                case 304:// 雷阵雨伴有冰雹 Hail
                case 305:// 小雨 Light Rain
                case 306:// 中雨 Moderate Rain
                case 307:// 大雨 Heavy Rain
                case 308:// 极端降雨 Extreme Rain
                case 309:// 毛毛雨/细雨 Drizzle Rain
                case 310:// 暴雨 Storm
                case 311:// 大暴雨 Heavy Storm
                case 312:// 特大暴雨 Severe Storm
                case 313:// 冻雨 Freezing Rain
                    return isNight ? BaseDrawer.Type.RAIN_N : BaseDrawer.Type.RAIN_D;
                case 400:// 小雪 Light Snow
                case 401:// 中雪 Moderate Snow
                case 402:// 大雪 Heavy Snow
                case 403:// 暴雪 Snowstorm
                case 407:// 阵雪 Snow Flurry
                    return isNight ? BaseDrawer.Type.SNOW_N : BaseDrawer.Type.SNOW_D;
                case 404:// 雨夹雪 Sleet
                case 405:// 雨雪天气 Rain And Snow
                case 406:// 阵雨夹雪 Shower Snow
                    return isNight ? BaseDrawer.Type.RAIN_SNOW_N : BaseDrawer.Type.RAIN_SNOW_D;
                case 500:// 薄雾
                case 501:// 雾
                    return isNight ? BaseDrawer.Type.FOG_N : BaseDrawer.Type.FOG_D;
                case 502:// 霾
                case 504:// 浮尘
                    return isNight ? BaseDrawer.Type.HAZE_N : BaseDrawer.Type.HAZE_D;
                case 503:// 扬沙
                case 506:// 火山灰
                case 507:// 沙尘暴
                case 508:// 强沙尘暴
                    return isNight ? BaseDrawer.Type.SAND_N : BaseDrawer.Type.SAND_D;
                default:
                    return isNight ? BaseDrawer.Type.UNKNOWN_N : BaseDrawer.Type.UNKNOWN_D;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isNight ? BaseDrawer.Type.UNKNOWN_N : BaseDrawer.Type.UNKNOWN_D;
    }


    public static boolean isNight(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String hour= sdf.format(new Date());
        int k  = Integer.parseInt(hour)  ;
        if ((k>=0 && k<6) ||(k >=18 && k<24)){
            return true;
        } else {
            return false;
        }
    }
}
