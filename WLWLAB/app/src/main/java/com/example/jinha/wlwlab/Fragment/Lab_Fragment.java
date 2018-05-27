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

import com.example.jinha.wlwlab.Adapter.Lab_Adapter;
import com.example.jinha.wlwlab.R;

/**
 * Created by jinha on 2017/9/24.
 */

@SuppressLint("ValidFragment")
public class Lab_Fragment extends Fragment {
    Activity activity;
    ViewPager mViewPager;
    Lab_Adapter lab_adapter;

    @SuppressLint("ValidFragment")
    public Lab_Fragment(Activity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = activity.getLayoutInflater().inflate(R.layout.fragment_lab,null);

        lab_adapter = new Lab_Adapter(getChildFragmentManager(),activity);
        mViewPager =  view.findViewById(R.id.container_lab);
        mViewPager.setAdapter(lab_adapter);

        TabLayout tabLayout =  activity.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        return view;
    }
}
