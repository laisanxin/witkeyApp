package com.witkey.campuswitkey.contract;

/**
 * Created by 12554 on 2018/3/28.
 */

public class LoginContract {
    public interface ILoginActivity extends IBaseActivity<ILoginActivityPresenter>{
        void showloginResult(String reslut);
        void startActivityTo();
    }
    public interface ILoginActivityPresenter extends IBasePresenter<ILoginActivity> {
        int checkUser(String user_email, String pwd);
    }
}
