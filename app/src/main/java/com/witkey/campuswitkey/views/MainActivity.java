package com.witkey.campuswitkey.views;


import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.witkey.campuswitkey.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.find_tasks_tv) TextView find_tasks_tv;
    @BindView(R.id.find_witkeys_tv) TextView find_witkeys_tv;
    @BindView(R.id.post_tasks_tv) TextView post_tasks_tv;
    @BindView(R.id.personal_tv) TextView personal_tv;

    private FindTasksFragment findTasksFragment;
    private FindWitkeysFragment findWitkeysFragment;
    private PostTasksFragment postTasksFragment;
    private PersonalFragment personalFragment;

    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        setTabSelection(0);
        find_tasks_tv.setOnClickListener(this);
        find_witkeys_tv.setOnClickListener(this);
        post_tasks_tv.setOnClickListener(this);
        personal_tv.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.find_tasks_tv:
                setTabSelection(0);
                break;
            case R.id.find_witkeys_tv:
                setTabSelection(1);
                break;
            case R.id.post_tasks_tv:
                setTabSelection(2);
                break;
            case R.id.personal_tv:
                setTabSelection(3);
                break;
            default:
                break;
        }
    }
    private void setTabSelection(int index){
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch(index){
            case 0:
                find_tasks_tv.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.drawable.ic_find_tasks_selected),
                       null, null);
                find_tasks_tv.setTextColor(getResources().getColor(R.color.holo_bule_dark));
                if (findTasksFragment == null) {

                    findTasksFragment = FindTasksFragment.newInstance(null,null);
                    transaction.add(R.id.content, findTasksFragment);
                } else {

                    transaction.show(findTasksFragment);
                }
                break;
            case 1:
                find_witkeys_tv.setCompoundDrawablesWithIntrinsicBounds(null,
                        getDrawable(R.drawable.ic_find_witkeys_selected),null, null);
                find_witkeys_tv.setTextColor(getResources().getColor(R.color.holo_bule_dark));
                if (findWitkeysFragment == null) {

                    findWitkeysFragment = FindWitkeysFragment.newInstance(null,null);
                    transaction.add(R.id.content, findWitkeysFragment);
                } else {

                    transaction.show(findWitkeysFragment);
                }
                break;
            case 2:
                post_tasks_tv.setCompoundDrawablesWithIntrinsicBounds(null,
                        getDrawable(R.drawable.ic_post_tasks_selected), null, null);
                post_tasks_tv.setTextColor(getResources().getColor(R.color.holo_bule_dark));
                if ( postTasksFragment== null) {

                    postTasksFragment = PostTasksFragment.newInstance(null,null);
                    transaction.add(R.id.content, postTasksFragment);
                } else {

                    transaction.show(postTasksFragment);
                }
                break;
            case 3:
                personal_tv.setCompoundDrawablesWithIntrinsicBounds(null,
                        getDrawable(R.drawable.ic_personal_selected), null, null);
                personal_tv.setTextColor(getResources().getColor(R.color.holo_bule_dark));
                if ( personalFragment== null) {

                    personalFragment = PersonalFragment.newInstance(null,null);
                    transaction.add(R.id.content, personalFragment);
                } else {

                    transaction.show(personalFragment);
                }
                break;
            default:
                break;

        }
        transaction.commit();
    }
    //清除掉选中的状态
    private void clearSelection() {
        find_tasks_tv.setTextColor(getResources().getColor(R.color.darker_gray));
        find_tasks_tv.setCompoundDrawablesWithIntrinsicBounds(null,
                getDrawable(R.drawable.ic_find_tasks_unselected), null, null);

        find_witkeys_tv.setTextColor(getResources().getColor(R.color.darker_gray));
        find_witkeys_tv.setCompoundDrawablesWithIntrinsicBounds(null,
                getDrawable(R.drawable.ic_find_witkeys_unselected), null, null);

        post_tasks_tv.setTextColor(getResources().getColor(R.color.darker_gray));
        post_tasks_tv.setCompoundDrawablesWithIntrinsicBounds(null,
                getDrawable(R.drawable.ic_post_tasks_unselected),null, null);

        personal_tv.setTextColor(getResources().getColor(R.color.darker_gray));
        personal_tv.setCompoundDrawablesWithIntrinsicBounds(null,
                getDrawable(R.drawable.ic_personal_unselected), null, null);



    }
    private void hideFragments(FragmentTransaction transaction){
        if(findTasksFragment != null){
            transaction.hide(findTasksFragment);
        }
        if(findWitkeysFragment != null){
            transaction.hide(findWitkeysFragment);

        }
        if(postTasksFragment != null){
            transaction.hide(postTasksFragment);

        }
        if(personalFragment != null){
            transaction.hide(personalFragment);
        }
    }
}
