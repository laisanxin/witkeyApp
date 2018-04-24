package com.witkey.campuswitkey.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.witkey.campuswitkey.R;
import com.witkey.campuswitkey.contract.IActivityLifeCycle;
import com.witkey.campuswitkey.contract.RegisterContract;
import com.witkey.campuswitkey.model.entity.User;
import com.witkey.campuswitkey.presenter.RegisterPresenter;
import com.witkey.campuswitkey.utils.MyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.IRegisterActivity {
    @BindView(R.id.rg_pwd_et)
    EditText user_pwd_et;

    @BindView(R.id.rg_email_et)
    EditText user_email_et;

    @BindView(R.id.rg_checkcode_et)
    EditText rg_checkcode_et;

    @BindView(R.id.rg_get_checkcode_tv)
    TextView rg_get_checkcode_tv;

    @BindView(R.id.rg_pwd_isvb_tv)
    TextView rg_pwd_isvb_tv;

    @BindView(R.id.rg_submit_btn)
    Button rg_submit_btn;

    private RegisterContract.IRegisterPresenter registerPresenter;
    private String checkCode;
    private String user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.rg_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_action_back);
        }
        user_pwd_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        registerPresenter = initPresenter();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.i("back", "。。。");
                finish();
                break;
            default:
        }
        return true;
    }

    @OnClick(R.id.rg_get_checkcode_tv)
    public void getCheckCode() {
        user_email = user_email_et.getText().toString().trim();
        if (!TextUtils.isEmpty(user_email)) {
            Log.i("email", user_email);
            registerPresenter.getCheckCode(user_email);
        }
    }

    @OnClick(R.id.rg_pwd_isvb_tv)
    public void isPwdVisibility() {
        int inputType = user_pwd_et.getInputType();
        if (inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD + InputType.TYPE_CLASS_TEXT) {
            user_pwd_et.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            rg_pwd_isvb_tv.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.ic_visibility), null, null, null);

        } else{
            user_pwd_et.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
            rg_pwd_isvb_tv.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.ic_visibility_off), null, null, null);

        }
        Editable etable = user_pwd_et.getText();
        Selection.setSelection(etable, etable.length());

    }

    @OnClick(R.id.rg_submit_btn)
    public void addUser() {
        SharedPreferences sha = MyApplication.getContext().getSharedPreferences("user", 0);
        checkCode = sha.getString("checkCodeForRg", null);
        String user_emailIn = user_email_et.getText().toString();
        String checkCodeIn = rg_checkcode_et.getText().toString();
        String user_pwd = user_pwd_et.getText().toString();
        if (user_emailIn.equals(user_email) && checkCodeIn.equals(checkCode)) {
            User user = new User();
            user.setUser_email(user_emailIn);
            user.setUser_pwd(user_pwd);
            registerPresenter.registerUser(user);
        } else {
            Toast.makeText(this, "验证码输入错误", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void showResult(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void showFailed() {
        Toast.makeText(this, "操作失败", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void countSendCode(int count) {
        if (count > 10) {
        } else if (count == 10) {
            rg_get_checkcode_tv.setText("获取验证码");
            rg_get_checkcode_tv.setTextColor(getResources().getColor(R.color.holo_bule_dark));
            rg_get_checkcode_tv.setClickable(true);
        } else if (count == 0) {
            SharedPreferences sha = MyApplication.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sha.edit();
            editor.putString("checkCodeForRg", "1111111");
            editor.apply();

        }
    }

    @Override
    public void checgSnedTv() {
        rg_get_checkcode_tv.setTextColor(getResources().getColor(R.color.darker_gray));
        rg_get_checkcode_tv.setClickable(false);

    }

    @Override
    public void toLoginActivity(String msg) {
        closeKeyboard();
        Snackbar.make(rg_get_checkcode_tv , msg , Snackbar.LENGTH_LONG)
                .setAction("返回登录页面", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).show();

    }
    private void closeKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void showKeyboard(){

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(user_pwd_et, 0);
    }
    @Override
    public RegisterContract.IRegisterPresenter initPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerPresenter.attach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerPresenter.dettach();
    }
}
