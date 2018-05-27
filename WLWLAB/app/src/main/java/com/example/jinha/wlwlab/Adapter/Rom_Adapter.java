package com.example.jinha.wlwlab.Adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.example.jinha.wlwlab.Fragment.Lab_Fragment;
import com.example.jinha.wlwlab.Fragment.Rom_Fragment_1;
import com.example.jinha.wlwlab.Fragment.Rom_Fragment_2;
import com.example.jinha.wlwlab.MainActivity;

/**
 * Created by jinha on 2017/9/24.
 */

public class Rom_Adapter extends FragmentPagerAdapter {
    Activity activity;
    Rom_Fragment_1 rom_fragment_1;
    Rom_Fragment_2 rom_fragment_2;


    FragmentManager fm;

    public Rom_Adapter(FragmentManager fm,Activity activity) {
        super(fm);
        this.fm = fm;
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below)

            if(position == 0){
                if(rom_fragment_1 == null)
                    rom_fragment_1 = new Rom_Fragment_1(activity);
                return rom_fragment_1;
            }
            else {
                if(rom_fragment_2 == null)
                    rom_fragment_2 = new Rom_Fragment_2(activity);
                return rom_fragment_2;
            }


    }


    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "控制";
            case 1:
                return "设置";

        }
        return null;
    }



    @Override
    public int getItemPosition(Object object) {
        // 最简单解决 notifyDataSetChanged() 页面不刷新问题的方法
        return POSITION_NONE;
    }
}