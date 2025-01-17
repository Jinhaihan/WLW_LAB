package com.example.jinha.wlwlab.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jinha.wlwlab.Adapter.SmartHome_Adapter;
import com.example.jinha.wlwlab.MainActivity;
import com.example.jinha.wlwlab.app.Config;
import com.example.jinha.wlwlab.R;
import com.example.jinha.wlwlab.base.BaseFragment;
import com.example.jinha.wlwlab.bean.VoiceCommandBean;
import com.example.jinha.wlwlab.weather.ApiManager;
import com.example.jinha.wlwlab.weather.dynamicweather.DynamicWeatherView;

/**
 * Created by jinha on 2017/9/25.
 */

@SuppressLint("ValidFragment")
public class SmartHome_Fragment extends Fragment implements BaseFragment {
    MainActivity activity;
    ViewPager mViewPager;
    SmartHome_Adapter smartHome_adapter;
    static Config configuration = new Config();
    DynamicWeatherView dynamicWeatherView;

    @SuppressLint("ValidFragment")
    public SmartHome_Fragment(MainActivity activity){
        this.activity = activity;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = activity.getLayoutInflater().inflate(R.layout.fragment_smarthome,null);

        smartHome_adapter = new SmartHome_Adapter(getChildFragmentManager(),activity);
        mViewPager =  view.findViewById(R.id.container_smarthome);
        mViewPager.setAdapter(smartHome_adapter);
        //mViewPager.setPageTransformer(true, new CubeOutTransformer());

        TabLayout tabLayout =  activity.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        dynamicWeatherView = view.findViewById(R.id.dynamicWeatherView);
        if(activity.getCurrentWeather() != null){
            dynamicWeatherView.setDrawerType(ApiManager.convertWeatherType(activity.getCurrentWeather().getHeWeather6().get(0)));
        }
        return view;
    }

    @Override
    public void onPause() {
        dynamicWeatherView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        dynamicWeatherView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        dynamicWeatherView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void voiceSend(VoiceCommandBean voiceCommandBean) {
        smartHome_adapter.getFragment(mViewPager.getCurrentItem()).voiceSend(voiceCommandBean);
    }
}
