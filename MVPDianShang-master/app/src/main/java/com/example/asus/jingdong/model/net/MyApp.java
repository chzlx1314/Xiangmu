package com.example.asus.jingdong.model.net;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.BuildConfig;
import org.xutils.x;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * App
 */

public class MyApp extends Application {

    private static Context appContext;
    private static OkHttpClient okHttpClient;
    private static SharedPreferences sp;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = this;
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);

        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(this)
                .threadPoolSize(5)
                .threadPriority(1000)
                .build();
        ImageLoader.getInstance().init(configuration);
        x.Ext.init(this);


        okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();

        // 存放登录的用户信息
        sp = getSharedPreferences("UserInfo",MODE_PRIVATE);

    }

    public static Context getAppContext(){
        return appContext;
    }

    public static OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }

    public static SharedPreferences getUserInfoSp(){
        return sp;
    }
}
