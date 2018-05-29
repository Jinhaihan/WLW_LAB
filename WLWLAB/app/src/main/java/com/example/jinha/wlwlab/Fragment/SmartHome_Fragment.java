package com.example.jinha.wlwlab.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.example.jinha.wlwlab.Adapter.Agriculture_Adapter;
import com.example.jinha.wlwlab.Adapter.SmartHome_Adapter;
import com.example.jinha.wlwlab.Configuration;
import com.example.jinha.wlwlab.R;

/**
 * Created by jinha on 2017/9/25.
 */

@SuppressLint("ValidFragment")
public class SmartHome_Fragment extends Fragment {
    Activity activity;
    ViewPager mViewPager;
    SmartHome_Adapter smartHome_adapter;
    static Configuration configuration = new Configuration();

    @SuppressLint("ValidFragment")
    public SmartHome_Fragment(Activity activity){
        this.activity = activity;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = activity.getLayoutInflater().inflate(R.layout.fragment_smarthome,null);

        smartHome_adapter = new SmartHome_Adapter(getChildFragmentManager(),activity);
        mViewPager =  view.findViewById(R.id.container_smarthome);
        mViewPager.setAdapter(smartHome_adapter);
        mViewPager.setPageTransformer(true, new CubeOutTransformer());

        TabLayout tabLayout =  activity.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        return view;
    }

}
