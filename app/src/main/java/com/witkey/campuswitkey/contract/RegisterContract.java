package com.witkey.campuswitkey.contract;

import com.witkey.campuswitkey.model.entity.User;

/**
 * Created by 12554 on 2018/4/1.
 */

public class RegisterContract {
    public interface IRegisterActivity extends IBaseActivity<RegisterContract.IRegisterPresenter>{
        //在界面设置获取到的验证码
        void showResult(String checkcode);
        void showFailed();
        void countSendCode(int count);
        void checgSnedTv();
        void toLoginActivity(String message);


    }
    public interface IRegisterPresenter extends IBasePresenter<IRegisterActivity>{
        void getCheckCode(String user_email);
        void registerUser(User user);


    }
}
