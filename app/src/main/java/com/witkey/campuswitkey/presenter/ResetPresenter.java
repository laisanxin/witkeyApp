package com.witkey.campuswitkey.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import com.witkey.campuswitkey.contract.ResetContract;
import com.witkey.campuswitkey.model.HttpResult;
import com.witkey.campuswitkey.model.LoadTasksCallBack;
import com.witkey.campuswitkey.model.UserModel;
import com.witkey.campuswitkey.model.entity.User;
import com.witkey.campuswitkey.utils.MyApplication;

/**
 * Created by 12554 on 2018/4/15.
 */

public class ResetPresenter implements ResetContract.IResetPresenter, LoadTasksCallBack<HttpResult<String>> {
    private ResetContract.IResetActivity resetActivity;
    private UserModel userModel;
    private Handler handler;

    public ResetPresenter() {
        if (this.userModel == null) {
            this.userModel = new UserModel();

        }
    }

    @Override
    public void attach(ResetContract.IResetActivity view) {
        if (resetActivity == null) {
            resetActivity = view;
        }


    }

    @Override
    public void dettach() {
        if (this.resetActivity != null) {
            this.resetActivity = null;
        }
    }

    @Override
    public void getCheckCode(String user_email) {
        this.userModel.getCheckCode(user_email, this);

    }

    @Override
    public void resetPwd(User user) {
        this.userModel.resetPwd(user, this);

    }


    @Override
    public void onSuccess(HttpResult<String> result) {
        if(result != null){
            switch (result.getCode()) {
                case 199:
                    //验证码发送失败
                    resetActivity.showResult(result.getMessage());
                    break;
                case 200:
                    //验证码发送成功
                    SharedPreferences sha = MyApplication.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sha.edit();
                    editor.putString("checkCodeForReset", result.getData());
                    editor.apply();
                    resetActivity.showResult(result.getMessage());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 70; i >= 0; i--){
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
                    handler = new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            if(resetActivity != null){
                                resetActivity.countSendCode(msg.arg1);

                            }

                        }
                    };
                    resetActivity.checgSnedTv();
                    break;
                case 201:
                    //重制密码成功
                    resetActivity.toLoginActivity(result.getMessage());
                    break;
                case 202:
                    //重置密码失败
                    resetActivity.showResult(result.getMessage());
                    break;
                default:
                    resetActivity.showResult("出现异常！");
                    break;
            }


        }

    }

    @Override
    public void start() {

    }

    @Override
    public void onFailed() {
        resetActivity.showResult("网络连接异常！");
    }

    @Override
    public void onFinish() {

    }
}
