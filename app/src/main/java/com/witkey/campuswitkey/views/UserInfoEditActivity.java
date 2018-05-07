package com.witkey.campuswitkey.views;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.witkey.campuswitkey.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoEditActivity extends AppCompatActivity {
    @BindView(R.id.userinfo_edit_toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_title_tv)
    TextView edit_title_tv;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.add_tv)
    TextView add_tv;
    @BindView(R.id.submit_btn)
    Button submit_btn;
    private Intent intent;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_edit);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        intent = getIntent();
        id = intent.getIntExtra("id",-1);
        editInfo();
        Editable etable = content.getText();
        Selection.setSelection(etable, etable.length());
        if(actionbar != null){
            actionbar.setTitle(null);
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_action_back);
        }

    }

    private void editInfo() {
        switch (id){
            case 0:
                edit_title_tv.setText("修改昵称");
                content.setText(intent.getStringExtra("content"));
                break;
            case 1:
                edit_title_tv.setText("性别");
                content.setText(intent.getStringExtra("content"));
                break;
            case 2:
                edit_title_tv.setText("专业");
                content.setText(intent.getStringExtra("content"));
                break;
            case 3:
                edit_title_tv.setText("邮箱");
                content.setText(intent.getStringExtra("content"));
                content.setEnabled(false);
                break;
            default:
                break;

        }
    }
    @OnClick(R.id.submit_btn)
    public void submitEdit(){
        switch (id){
            case 0:
                intent = new Intent();
                intent.putExtra("content",content.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                break;
            case 1:
                intent = new Intent();
                intent.putExtra("content",content.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                break;
            case 2:
                intent = new Intent();
                intent.putExtra("content",content.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                break;
            case 3:
                intent = new Intent();
                intent.putExtra("content",content.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;

        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
