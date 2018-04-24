package com.witkey.campuswitkey.contract;

import com.witkey.campuswitkey.model.entity.User;

/**
 * Created by 12554 on 2018/3/30.
 */

public class UserInfoContract {
    public interface IUserInfoActivity extends IBaseActivity<UserInfoContract.IUserInfoActivityPresenter>{
        void showResult(String reslut);
        void showLoading();//展示加载框
        void dismissLoading();//取消加载框展示
        void showUserInfo(User user);
        void showUpdateResult(String result);

    }
    public interface IUserInfoActivityPresenter extends IBasePresenter<IUserInfoActivity> {
        void loadUserInfo();
        void updateUserInfo(User user);

    }
}
