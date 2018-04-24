package com.witkey.campuswitkey.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.AndroidCharacter;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.witkey.campuswitkey.R;
import com.witkey.campuswitkey.contract.ResetContract;
import com.witkey.campuswitkey.model.entity.User;
import com.witkey.campuswitkey.presenter.ResetPresenter;
import com.witkey.campuswitkey.utils.MyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.color.holo_blue_dark;

public class ResetActivity extends AppCompatActivity implements ResetContract.IResetActivity {
    @BindView(R.id.reset_pwd_et)
    EditText user_pwd_et;

    @BindView(R.id.reset_email_et)
    EditText user_email_et;


    @BindView(R.id.reset_checkcode_et)
    EditText reset_checkcode_et;

    @BindView(R.id.reset_get_checkcode_tv)
    TextView reset_get_checkcode_tv;

    @BindView(R.id.reset_pwd_isvb_tv)
    TextView reset_pwd_isvb_tv;

    @BindView(R.id.reset_submit_btn)
    Button reset_submit_btn;
    private ResetContract.IResetPresenter presenter;
    private String checkCode;
    private String user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.reset_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_action_back);
        }
        user_pwd_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        presenter = initPresenter();
    }

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

    @OnClick(R.id.reset_get_checkcode_tv)
    public void getCheckCode() {
        Log.i("getcheckcode", "getcheckcode");

        user_email = user_email_et.getText().toString().trim();
        if (!TextUtils.isEmpty(user_email)) {
            Log.i("email", user_email);
            presenter.getCheckCode(user_email);

        } else {
            Toast.makeText(this, "请输入邮箱", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.reset_pwd_isvb_tv)
    public void isPwdVisibility() {
        int inputType = user_pwd_et.getInputType();
        if (inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD + InputType.TYPE_CLASS_TEXT) {
            user_pwd_et.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            reset_pwd_isvb_tv.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.ic_visibility), null, null, null);

        } else {
            user_pwd_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            reset_pwd_isvb_tv.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.ic_visibility_off), null, null, null);

        }
        Editable etable = user_pwd_et.getText();
        Selection.setSelection(etable, etable.length());

    }

    @OnClick(R.id.reset_submit_btn)
    public void resetPwd() {
        SharedPreferences sha = MyApplication.getContext().getSharedPreferences("user", 0);
        checkCode = sha.getString("checkCodeForReset", null);
        String user_emailIn = user_email_et.getText().toString();
        String checkCodeIn = reset_checkcode_et.getText().toString();
        String user_pwd = user_pwd_et.getText().toString();
        if (TextUtils.isEmpty(user_emailIn) || TextUtils.isEmpty(user_pwd) || TextUtils.isEmpty(checkCodeIn)) {
            Toast.makeText(this, "请将信息填写完整", Toast.LENGTH_SHORT).show();
        } else {
            if (checkCode != 1111111 + "" && user_emailIn.equals(user_email) && checkCodeIn.equals(checkCode)) {
                User user = new User();
                user.setUser_email(user_emailIn);
                user.setUser_pwd(user_pwd);
                presenter.resetPwd(user);
            } else {
                Toast.makeText(this, "验证码输入错误", Toast.LENGTH_SHORT).show();
            }

        }


    }

    @Override
    public ResetContract.IResetPresenter initPresenter() {

        return new ResetPresenter();
    }

    @Override
    public void showResult(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void toLoginActivity(String message) {
        Intent intent = new Intent();
        intent.putExtra("message", message);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void countSendCode(int count) {

        if (count > 10) {
            reset_get_checkcode_tv.setText("获取验证码" + "(" + (count - 10) + ")");


        } else if (count == 10) {
            reset_get_checkcode_tv.setText("获取验证码");
            reset_get_checkcode_tv.setTextColor(getResources().getColor(R.color.holo_bule_dark));
            reset_get_checkcode_tv.setClickable(true);
        } else if (count == 0) {
            SharedPreferences sha = MyApplication.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sha.edit();
            editor.putString("checkCodeForReset", "1111111");
            editor.apply();

        }

    }

    @Override
    public void checgSnedTv() {
        reset_get_checkcode_tv.setTextColor(getResources().getColor(R.color.darker_gray));
        reset_get_checkcode_tv.setClickable(false);
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
