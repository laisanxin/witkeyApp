package com.witkey.campuswitkey.contract;

/**
 * Created by 12554 on 2018/3/28.
 */

public interface IBasePresenter<T> {
    //绑定view
    void attach(T view);
    //解绑view
    void dettach();


}
