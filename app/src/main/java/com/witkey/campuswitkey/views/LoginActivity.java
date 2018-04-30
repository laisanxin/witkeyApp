package com.witkey.campuswitkey.views;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.witkey.campuswitkey.R;
import com.witkey.campuswitkey.contract.IActivityLifeCycle;
import com.witkey.campuswitkey.contract.LoginContract;
import com.witkey.campuswitkey.presenter.LoginActivityPresenter;

public class LoginActivity extends AppCompatActivity implements LoginContract.ILoginActivity, View.OnClickListener {
    private LoginContract.ILoginActivityPresenter presenter;
    private EditText user_email_et;
    private EditText user_pwd_et;
    private Button login_btn;
    private TextView rg_tv;
    private TextView reset_pwd_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        toolbar.setTitle(null);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_action_back);
        }
        //初始化控件
        initView();
        presenter = initPresenter();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }

    private void initView() {
        user_email_et = (EditText) findViewById(R.id.user_email_et);
        user_pwd_et = (EditText) findViewById(R.id.user_pwd_et);
        login_btn = (Button) findViewById(R.id.login_btn);
        rg_tv= (TextView) findViewById(R.id.register_tv);
        reset_pwd_tv = (TextView) findViewById(R.id.login_resetpwd_tv);
        login_btn.setOnClickListener(this);
        rg_tv.setOnClickListener(this);
        reset_pwd_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                if (TextUtils.isEmpty(user_email_et.getText()) || TextUtils.isEmpty(user_pwd_et.getText())) {
                    Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    presenter.checkUser(user_email_et.getText().toString(), user_pwd_et.getText().toString());
                    Log.d("Login ", user_email_et.getText().toString());
                    Log.d("Login ",  user_pwd_et.getText().toString());
                }
                break;
            case R.id.register_tv:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_resetpwd_tv:
                Intent intent1 = new Intent(LoginActivity.this, ResetActivity.class);
                startActivityForResult(intent1,1);
                break;
            default:
                break;

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if(requestCode == 1){
                Toast.makeText(this, data.getStringExtra("message") ,Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void showloginResult(String reslut) {
        Toast.makeText(LoginActivity.this, reslut, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void startActivityTo(Intent intent) {
        setResult(RESULT_OK, intent);
        finish();
    }




    @Override
    public LoginContract.ILoginActivityPresenter initPresenter() {
        return  new LoginActivityPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attach(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dettach();
    }
}
