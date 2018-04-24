package com.witkey.campuswitkey.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import com.witkey.campuswitkey.contract.RegisterContract;
import com.witkey.campuswitkey.model.HttpResult;
import com.witkey.campuswitkey.model.UserModel;
import com.witkey.campuswitkey.model.LoadTasksCallBack;
import com.witkey.campuswitkey.model.entity.User;
import com.witkey.campuswitkey.utils.MyApplication;

/**
 * Created by 12554 on 2018/4/1.
 */

public class RegisterPresenter implements RegisterContract.IRegisterPresenter, LoadTasksCallBack<HttpResult<String>> {
    private RegisterContract.IRegisterActivity registerActivity;
    private UserModel userModel;
    private Handler handler;

    public RegisterPresenter() {
        if (userModel == null) {
            userModel = new UserModel();
        }
    }


    @Override
    public void onSuccess(HttpResult<String> result) {
        if (result != null) {
            switch (result.getCode()) {
                case 199:
                    registerActivity.showResult(result.getMessage());
                    break;
                case 200:
                    SharedPreferences sha = MyApplication.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sha.edit();
                    editor.putString("checkCodeForRg", result.getData());
                    editor.apply();
                    registerActivity.showResult(result.getMessage());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 70; i >= 0; i--) {
                                Message message = new Message();
                                message.arg1 = i;
                                handler.sendMessage(message);
                                try {
                                    Thread.sleep(1000);

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }


                            }
                        }
                    }).start();
                    handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            if (registerActivity != null) {
                                registerActivity.countSendCode(msg.arg1);

                            }

                        }
                    };
                    registerActivity.checgSnedTv();
                    break;
                case 201://注册成功
                    registerActivity.toLoginActivity(result.getMessage());
                    break;
                case 202://注册失败
                    registerActivity.showResult(result.getMessage());
                    break;
                case 203://用户已存在
                    registerActivity.showResult(result.getMessage());
                    break;
                default:
                    registerActivity.showResult("出现异常");
                    break;
            }


        }


    }


    @Override
    public void start() {

    }

    @Override
    public void onFailed() {
        registerActivity.showFailed();

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void getCheckCode(String user_email) {
        userModel.getCheckCode(user_email, this);

    }

    @Override
    public void registerUser(User user) {
        userModel.addUser(user, this);

    }

    @Override
    public void attach(RegisterContract.IRegisterActivity view) {
        if (this.registerActivity == null) {
            this.registerActivity = view;

        }

    }

    @Override
    public void dettach() {
        if (this.registerActivity != null) {
            this.registerActivity = null;
        }

    }
}
