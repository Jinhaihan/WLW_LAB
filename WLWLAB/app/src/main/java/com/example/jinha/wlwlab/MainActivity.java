package com.example.jinha.wlwlab;
//
// {39 39 01 01 02 01 2A }开灯
// {39 39 01 01 02 00 2A }关灯
// {39 39 01 01 01 01 2A }开风扇
// {39 39 01 01 01 00 2A }关风扇
// {39 39 01 01 03 01 2A }开喷水
// {39 39 01 01 03 00 2A }关喷水。

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.jinha.wlwlab.Fragment.Lab_Fragment;
import com.example.jinha.wlwlab.Fragment.Agriculture_Fragment;
import com.example.jinha.wlwlab.Fragment.SmartHome_Fragment;
import com.jaeger.library.StatusBarUtil;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout img_title ;
    Toolbar toolbar;
    DrawerLayout drawer;
    Agriculture_Fragment agriculture_fragment;
    Lab_Fragment lab_fragment;
    SmartHome_Fragment smartHome_fragment;
    TabLayout tabLayout;
    FragmentManager fragmentManager = getSupportFragmentManager();

    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        img_title = (LinearLayout) findViewById(R.id.img_title);

        agriculture_fragment = new Agriculture_Fragment(this);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_Agriculture);
        setTitle("物联网与云计算重点实验室");
        setStatusBar(drawer,Color.parseColor("#47AA71"));


        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_main, agriculture_fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_Agriculture) {
            if(img_title == null)
                img_title = (LinearLayout) findViewById(R.id.img_title);
            img_title.setBackgroundResource(R.drawable.rom_title);
            toolbar.setBackgroundColor(Color.parseColor("#47AA71"));
            setStatusBar(drawer,Color.parseColor("#47AA71"));
            tabLayout.setBackgroundColor(Color.parseColor("#47AA71"));

           if(agriculture_fragment == null)
               agriculture_fragment = new Agriculture_Fragment(this);
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_main, agriculture_fragment);
            fragmentTransaction.commit();
            Log.e("JHH","切换为Agriculture");

        } else if (id == R.id.nav_lab) {
            if(img_title == null)
                img_title = (LinearLayout) findViewById(R.id.img_title);
            img_title.setBackgroundResource(R.drawable.lab_title);
            toolbar.setBackgroundColor(Color.parseColor("#8A2BE2"));
            setStatusBar(drawer,Color.parseColor("#8A2BE2"));
            tabLayout.setBackgroundColor(Color.parseColor("#8A2BE2"));

            if(lab_fragment == null)
               lab_fragment = new Lab_Fragment(this);
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_main,lab_fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_SmartHome) {
            if(img_title == null)
                img_title = (LinearLayout) findViewById(R.id.img_title);
            img_title.setBackgroundResource(R.drawable.rom_title);
            toolbar.setBackgroundColor(Color.parseColor("#47AA71"));
            setStatusBar(drawer,Color.parseColor("#47AA71"));
            tabLayout.setBackgroundColor(Color.parseColor("#47AA71"));

            if(smartHome_fragment == null)
                smartHome_fragment = new SmartHome_Fragment(this);
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_main, smartHome_fragment);
            fragmentTransaction.commit();
            Log.e("JHH","切换为SmartHome");

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    protected void setStatusBar(DrawerLayout D,int color) {
        //StatusBarUtil.setColor(this,Color.parseColor("#47AA71"));
        StatusBarUtil.setColorNoTranslucentForDrawerLayout(this, D,color);

    }



}


