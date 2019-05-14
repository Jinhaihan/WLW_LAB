package com.example.jinha.wlwlab.Adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jinha.wlwlab.Fragment.Agriculture_Fragment_1;
import com.example.jinha.wlwlab.Fragment.Agriculture_Fragment_2;
import com.example.jinha.wlwlab.Fragment.SmartHome_Fragment_1;
import com.example.jinha.wlwlab.Fragment.SmartHome_Fragment_2;
import com.example.jinha.wlwlab.Fragment.SmartHome_Fragment_3;
import com.example.jinha.wlwlab.base.BaseFragment;

/**
 * Created by jinha on 2017/9/24.
 */

public class SmartHome_Adapter extends FragmentPagerAdapter {
    Activity activity;
    SmartHome_Fragment_1 smartHome_fragment_1;
    SmartHome_Fragment_2 smartHome_fragment_2;
    SmartHome_Fragment_3 smartHome_fragment_3;

    FragmentManager fm;

    public SmartHome_Adapter(FragmentManager fm, Activity activity) {
        super(fm);
        this.fm = fm;
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below)

            if(position == 0){
                if(smartHome_fragment_1 == null)
                    smartHome_fragment_1 = new SmartHome_Fragment_1(activity);
                return smartHome_fragment_1;
            }
            else if(position == 1){
                if(smartHome_fragment_2 == null)
                    smartHome_fragment_2 = new SmartHome_Fragment_2(activity);
                return smartHome_fragment_2;
            }
            else{
                if(smartHome_fragment_3 == null)
                    smartHome_fragment_3 = new SmartHome_Fragment_3(activity);
                return smartHome_fragment_3;
            }


    }

    public BaseFragment getFragment(int i){
        switch (i){
            case 0:
                return smartHome_fragment_1;
            case 1:
                return smartHome_fragment_2;
            case 2:
                return smartHome_fragment_3;
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "娱乐";
            case 1:
                return "开关";
            case 2:
                return "测试";

        }
        return null;
    }



    @Override
    public int getItemPosition(Object object) {
        // 最简单解决 notifyDataSetChanged() 页面不刷新问题的方法
        return POSITION_NONE;
    }
}