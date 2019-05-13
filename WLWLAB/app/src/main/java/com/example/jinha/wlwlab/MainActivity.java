package com.example.jinha.wlwlab;
//
// {39 39 01 01 02 01 2A }开灯
// {39 39 01 01 02 00 2A }关灯
// {39 39 01 01 01 01 2A }开风扇
// {39 39 01 01 01 00 2A }关风扇
// {39 39 01 01 03 01 2A }开喷水
// {39 39 01 01 03 00 2A }关喷水。

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.example.jinha.wlwlab.Fragment.Lab_Fragment;
import com.example.jinha.wlwlab.Fragment.Agriculture_Fragment;
import com.example.jinha.wlwlab.Fragment.SmartHome_Fragment;
import com.example.jinha.wlwlab.base.BaseFragment;
import com.example.jinha.wlwlab.callback.VoiceFinishCallBack;
import com.example.jinha.wlwlab.dialog.VoiceDialog;
import com.example.jinha.wlwlab.weather.WeahterService;
import com.example.jinha.wlwlab.weather.bean.WeatherBean;
import com.example.jinha.wlwlab.network.retrofit.RetrofitCreator;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , EventListener{

    LinearLayout img_title ;
    Toolbar toolbar;
    TextView toolbar_text;
    DrawerLayout drawer;

    Agriculture_Fragment agriculture_fragment;
    Lab_Fragment lab_fragment;
    SmartHome_Fragment smartHome_fragment;

    TabLayout tabLayout;
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    BaseFragment currentFragment;

    WeatherBean currentWeatherInfo;

    private EventManager wakeup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
        //setTitle("物联网与云计算重点实验室");
        toolbar_text = findViewById(R.id.weather_text);
        toolbar_text.setText("sdasdsadasdasd");
        getWeather("auto_ip");
        setStatusBar(drawer,Color.parseColor("#47AA71"));


        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_main, agriculture_fragment);
        fragmentTransaction.commit();
        currentFragment = agriculture_fragment;
        startVoiceReco();
    }

    public void startVoiceReco(){
        // 基于SDK唤醒词集成1.1 初始化EventManager
        wakeup = EventManagerFactory.create(this, "wp");
        // 基于SDK唤醒词集成1.3 注册输出事件
        wakeup.registerListener(this); //  EventListener 中 onEvent方法

        // 基于SDK唤醒词集成第2.1 设置唤醒的输入参数
        Map<String, Object> params = new TreeMap<String, Object>();
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
        params.put(SpeechConstant.WP_WORDS_FILE, "assets:///WakeUp.bin");
        // "assets:///WakeUp.bin" 表示WakeUp.bin文件定义在assets目录下

        String json = null; // 这里可以替换成你需要测试的json
        json = new JSONObject(params).toString();
        wakeup.send(SpeechConstant.WAKEUP_START, json, null, 0, 0);
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
        if (id == R.id.action_voice) {
            DiyDialog2();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * 自定义dialog2 简单自定义布局
     */
    private void DiyDialog2() {
        VoiceDialog voiceDialog = new VoiceDialog(this, new VoiceFinishCallBack() {
            @Override
            public void success(String result) {
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failed() {

            }
        });
        voiceDialog.show();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_Agriculture) {
            if(img_title == null)
                img_title = (LinearLayout) findViewById(R.id.img_title);
            img_title.setBackgroundResource(R.drawable.agriculture_title);
            toolbar.setBackgroundColor(Color.parseColor("#47AA71"));
            setStatusBar(drawer,Color.parseColor("#47AA71"));
            tabLayout.setBackgroundColor(Color.parseColor("#47AA71"));

           if(agriculture_fragment == null)
               agriculture_fragment = new Agriculture_Fragment(this);
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_main, agriculture_fragment);
            fragmentTransaction.commit();
            Log.e("JHH","切换为Agriculture");
            currentFragment = agriculture_fragment;
        } else if (id == R.id.nav_lab) {
            if(img_title == null)
                img_title = (LinearLayout) findViewById(R.id.img_title);
            img_title.setBackgroundResource(R.drawable.lab_title);
            toolbar.setBackgroundColor(Color.parseColor("#1976D2"));
            setStatusBar(drawer,Color.parseColor("#1976D2"));
            tabLayout.setBackgroundColor(Color.parseColor("#1976D2"));

            if(lab_fragment == null)
               lab_fragment = new Lab_Fragment(this);
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_main,lab_fragment);
            fragmentTransaction.commit();
            currentFragment = lab_fragment;
        } else if (id == R.id.nav_SmartHome) {
            if(img_title == null)
                img_title = (LinearLayout) findViewById(R.id.img_title);
            img_title.setBackgroundResource(R.drawable.smarthome_title2);
            toolbar.setBackgroundColor(Color.parseColor("#5D4037"));
            setStatusBar(drawer,Color.parseColor("#5D4037"));
            tabLayout.setBackgroundColor(Color.parseColor("#5D4037"));

            if(smartHome_fragment == null)
                smartHome_fragment = new SmartHome_Fragment(this);
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_main, smartHome_fragment);
            fragmentTransaction.commit();
            Log.e("JHH","切换为SmartHome");
            currentFragment = smartHome_fragment;
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



    public void getWeather(String city){
        RetrofitCreator.create(WeahterService.class)
                .getUserInfo(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(WeatherBean weahter) {
                        currentWeatherInfo = weahter;
                        WeatherBean.HeWeather6Bean.NowBean now = weahter.getHeWeather6().get(0).getNow();
                        toolbar_text.setText(now.getTmp() + "   " +now.getCond_txt());
                    }

                    @Override
                    public void onError(Throwable e) {
                        //获取失败
                    }

                    @Override
                    public void onComplete() {}
                });
    }

    public WeatherBean getCurrentWeather(){
        return currentWeatherInfo;
    }


    @Override
    public void onEvent(String s, String s1, byte[] bytes, int i, int i1) {
        Log.e("MainActivity", "onEvent: " + s);
        if(s.equals("wp.data")){
            DiyDialog2();
        }
    }
}


