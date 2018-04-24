package com.witkey.campuswitkey.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.witkey.campuswitkey.contract.UserInfoContract;
import com.witkey.campuswitkey.model.HttpResult;
import com.witkey.campuswitkey.model.UserModel;
import com.witkey.campuswitkey.model.LoadTasksCallBack;
import com.witkey.campuswitkey.model.entity.User;
import com.witkey.campuswitkey.utils.MyApplication;


/**
 * Created by 12554 on 2018/3/30.
 */

public class UserInfoActivityPresenter implements UserInfoContract.IUserInfoActivityPresenter, LoadTasksCallBack<HttpResult<User>> {
    private UserInfoContract.IUserInfoActivity mIUserInfoActivity;
    private UserModel userModel;

    public UserInfoActivityPresenter(UserInfoContract.IUserInfoActivity mIUserInfoActivity) {

        if(userModel == null){
            userModel = new UserModel();
        }
    }


    @Override
    public void onSuccess(HttpResult<User> result) {
        switch (result.getCode()) {
            case 199:
                //获取用户信息失败
                mIUserInfoActivity.showUserInfo(null);
                break;
            case 200:
                //获取用户信息 将获取的数据通过调用Activity的回调方法传递给Activity进行显示
                mIUserInfoActivity.showUserInfo(result.getData());

                break;
            case 201:
                //获取更新用户信息 且更新失败
                mIUserInfoActivity.showUpdateResult("更新失败！");
                break;
            case 202:
                //获取更新用户信息 且更新成功
                mIUserInfoActivity.showUpdateResult("更新成功！");
                break;
            default:
                break;


        }

    }

    @Override
    public void start() {

    }

    @Override
    public void onFailed() {
        mIUserInfoActivity.showResult("加载失败");

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void loadUserInfo() {
        SharedPreferences sha = MyApplication.getContext().getSharedPreferences("user", 0);
        int id = sha.getInt("user_id", 0);
        Log.i("user_id", String.valueOf(id));
        userModel.getUserInfo(id, this);



    }

    @Override
    public void updateUserInfo(User user) {
        SharedPreferences sha = MyApplication.getContext().getSharedPreferences("user", 0);
        int id = sha.getInt("user_id", 0);
        user.setUser_id(id);
        userModel.updateUser(user, this);


    }

    @Override
    public void attach(UserInfoContract.IUserInfoActivity view) {
        if(this.mIUserInfoActivity == null){
            this.mIUserInfoActivity = view;
        }


    }

    @Override
    public void dettach() {
        if(this.mIUserInfoActivity != null){
            this.mIUserInfoActivity = null;
        }

    }
}
