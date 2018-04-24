package com.witkey.campuswitkey.model;

import java.lang.reflect.Type;

/**
 * Created by 12554 on 2018/4/1.
 */

public interface LoadTasksCallBack<T> {
    void onSuccess(T t);

    void start();

    void onFailed();

    void onFinish();
}
