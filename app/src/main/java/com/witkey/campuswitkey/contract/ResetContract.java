package com.witkey.campuswitkey.contract;

import com.witkey.campuswitkey.model.entity.User;

/**
 * Created by 12554 on 2018/4/15.
 */

public class ResetContract {
    public interface IResetActivity extends IBaseActivity<IResetPresenter>{
        void showResult(String result);
        void toLoginActivity(String message);
        void countSendCode(int count);
        void checgSnedTv();

    }
    public interface IResetPresenter extends IBasePresenter<IResetActivity>{
        void getCheckCode(String user_email);
        void resetPwd(User user);

    }
}
