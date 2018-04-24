package com.witkey.campuswitkey.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by 12554 on 2018/3/30.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}
