package com.example.jinha.wlwlab.Adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jinha.wlwlab.Fragment.Lab_Fragment;
import com.example.jinha.wlwlab.Fragment.Lab_Fragment1;

/**
 * Created by jinha on 2017/9/24.
 */

public class Lab_Adapter extends FragmentPagerAdapter {
    Activity activity;

    public Lab_Adapter(FragmentManager fm,Activity activity) {
        super(fm);
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return new Lab_Fragment1(activity);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "实验箱";
        }
        return null;
    }

    @Override
    public int getItemPosition(Object object) {
        // 最简单解决 notifyDataSetChanged() 页面不刷新问题的方法
        return POSITION_NONE;
    }
}
