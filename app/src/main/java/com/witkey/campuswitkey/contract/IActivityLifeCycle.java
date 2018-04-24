package com.witkey.campuswitkey.contract;

/**
 * Created by 12554 on 2018/3/28.
 */

public interface IActivityLifeCycle {
    void onCreate();
    void onRestart();
    void onStart();
    void onResume();
    void onPause();
    void onStop();
    void onDestroy();
}
