package com.witkey.campuswitkey.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.witkey.campuswitkey.R;
import com.witkey.campuswitkey.contract.IActivityLifeCycle;
import com.witkey.campuswitkey.contract.UserInfoContract;
import com.witkey.campuswitkey.model.entity.User;
import com.witkey.campuswitkey.presenter.UserInfoActivityPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoActivity extends AppCompatActivity implements UserInfoContract.IUserInfoActivity {
    private UserInfoContract.IUserInfoActivityPresenter presenter;
    @BindView(R.id.user_head_view)
    ImageView user_head_view;
    @BindView(R.id.user_name_et)
    EditText user_name_et;
    @BindView(R.id.user_sex_et)
    EditText user_sex_et;
    @BindView(R.id.user_major_et)
    EditText user_major_et;
    @BindView(R.id.user_update_btn)
    Button user_update_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        presenter = initPresenter();



    }

    @OnClick(R.id.user_update_btn)
    public void userUpdate() {
        String user_name = user_name_et.getText().toString();
        String user_sex = user_sex_et.getText().toString();
        String user_major = user_major_et.getText().toString();
        User user = new User();
        user.setUser_name(user_name);
        user.setUser_sex(user_sex);
        user.setUser_major(user_major);
        presenter.updateUserInfo(user);
    }

    @Override
    public void showResult(String reslut) {
        Toast.makeText(this, reslut, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showUserInfo(final User user) {
        user_name_et.setText(user.getUser_name());
        user_sex_et.setText(user.getUser_sex());
        user_major_et.setText(user.getUser_major());
    }

    @Override
    public void showUpdateResult(String result) {
        Toast.makeText(UserInfoActivity.this, result, Toast.LENGTH_SHORT).show();


    }

    @Override
    public UserInfoContract.IUserInfoActivityPresenter initPresenter() {
        return new UserInfoActivityPresenter(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attach(this);
        presenter.loadUserInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dettach();
    }
}
