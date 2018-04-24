package com.witkey.campuswitkey.contract;

/**
 * Created by 12554 on 2018/3/28.
 */

public interface IBaseFragment<T> {
    // 设置逻辑
    void setPresenter(T mIFragmentPresenter);
}
