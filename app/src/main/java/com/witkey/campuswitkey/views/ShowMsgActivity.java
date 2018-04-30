package com.witkey.campuswitkey.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.witkey.campuswitkey.R;

public class ShowMsgActivity extends AppCompatActivity {
    private TextView test_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_msg);
        Intent intent = getIntent();
        test_tv = (TextView)findViewById(R.id.show_msg_test);
        test_tv.setText(intent.getStringExtra("test"));
    }
}
