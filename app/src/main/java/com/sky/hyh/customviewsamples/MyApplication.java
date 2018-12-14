package com.sky.hyh.customviewsamples;

import android.app.Application;
import android.content.Context;

/**
 * Created by hyh on 2018/12/14 16:26
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class MyApplication extends Application {
    private static MyApplication mApplication;

    public static Application getApplication(){
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }
}
