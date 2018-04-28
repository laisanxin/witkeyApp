package com.witkey.campuswitkey.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.witkey.campuswitkey.R;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.find_tasks_tv) TextView find_tasks_tv;
    @BindView(R.id.find_witkeys_tv) TextView find_witkeys_tv;
    @BindView(R.id.post_tasks_tv) TextView post_tasks_tv;
    @BindView(R.id.personal_tv) TextView personal_tv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
