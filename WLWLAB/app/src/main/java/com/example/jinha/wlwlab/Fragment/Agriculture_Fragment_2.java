package com.example.jinha.wlwlab.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jinha.wlwlab.R;

/**
 * Created by jinha on 2017/9/23.
 */

public class Agriculture_Fragment_2 extends Fragment {
    Activity activity;

    public Agriculture_Fragment_2(){}

    @SuppressLint("ValidFragment")
    public Agriculture_Fragment_2(Activity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = activity.getLayoutInflater().inflate(R.layout.fragment_agriculture2,null);

        return view;
    }




}
