package com.example.jinha.wlwlab.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ToxicBakery.viewpager.transforms.BackgroundToForegroundTransformer;
import com.ToxicBakery.viewpager.transforms.CubeInTransformer;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.example.jinha.wlwlab.Adapter.Agriculture_Adapter;
import com.example.jinha.wlwlab.R;
import com.example.jinha.wlwlab.base.BaseFragment;
import com.example.jinha.wlwlab.bean.VoiceCommandBean;

/**
 * Created by jinha on 2017/9/25.
 */

@SuppressLint("ValidFragment")
public class Agriculture_Fragment extends Fragment implements BaseFragment {
    Activity activity;
    ViewPager mViewPager;
    Agriculture_Adapter agriculture_adapter;

    @SuppressLint("ValidFragment")
    public Agriculture_Fragment(Activity activity){
        this.activity = activity;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = activity.getLayoutInflater().inflate(R.layout.fragment_agriculture,null);

        agriculture_adapter = new Agriculture_Adapter(getChildFragmentManager(),activity);
        mViewPager =  view.findViewById(R.id.container_rom);
        mViewPager.setAdapter(agriculture_adapter);
        mViewPager.setPageTransformer(true, new CubeOutTransformer());

        TabLayout tabLayout =  activity.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        return view;
    }

    @Override
    public void voiceSend(VoiceCommandBean voiceCommandBean) {

    }
}
