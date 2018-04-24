package com.witkey.campuswitkey.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.witkey.campuswitkey.contract.LoginContract;
import com.witkey.campuswitkey.model.HttpResult;
import com.witkey.campuswitkey.model.UserModel;
import com.witkey.campuswitkey.model.LoadTasksCallBack;
import com.witkey.campuswitkey.model.entity.User;
import com.witkey.campuswitkey.utils.MyApplication;

/**
 * Created by 12554 on 2018/3/28.
 */

public class LoginActivityPresenter implements LoginContract.ILoginActivityPresenter, LoadTasksCallBack<HttpResult<User>>{
    private LoginContract.ILoginActivity mILoginActivity;
    private UserModel userModel;
    public LoginActivityPresenter() {
        if(userModel == null){
            userModel = new UserModel();

        }
    }

    @Override
    public void onSuccess(HttpResult<User> result) {
        if(result != null){
            switch (result.getCode()){
                case 199://服务器异常
                    mILoginActivity.showloginResult(result.getMessage());
                    break;
                case 201://用户不存在
                    mILoginActivity.showloginResult(result.getMessage());
                    break;
                case 202://密码错误
                    mILoginActivity.showloginResult(result.getMessage());
                    break;
                case 200:
                    //登录成功 服务器返回该用户的id 将用户Id存到本地
                    SharedPreferences sha = MyApplication.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sha.edit();
                    editor.putInt("user_id", result.getData().getUser_id());
                    editor.apply();
                    //通知Activity进行界面跳转
                    mILoginActivity.startActivityTo();

                    break;
                default:

                    break;
            }

        }else {
            mILoginActivity.showloginResult("服务器异常");
        }



    }

    @Override
    public int checkUser(String user_email, String pwd) {
        userModel.checkUser(user_email, pwd, this);
        Log.d("Login p", user_email);
        Log.d("Login p", pwd);
        return 0;
    }

    @Override
    public void start() {

    }

    @Override
    public void onFailed() {
        mILoginActivity.showloginResult("网络出错，登录失败！");


    }

    @Override
    public void onFinish() {

    }


    @Override
    public void attach(LoginContract.ILoginActivity view) {
        this.mILoginActivity = view;


    }

    @Override
    public void dettach() {
        this.mILoginActivity = null;

    }

}
