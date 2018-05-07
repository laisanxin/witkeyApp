package com.witkey.campuswitkey.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.witkey.campuswitkey.R;
import com.witkey.campuswitkey.adapter.ListAdapter;
import com.witkey.campuswitkey.adapter.ListItem;
import com.witkey.campuswitkey.contract.PersonalContract;
import com.witkey.campuswitkey.presenter.PersonalPresenter;
import com.witkey.campuswitkey.utils.Url;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * @version 1.0
 * @description
 * @authon lsx
 * create at 2018/4/30 12:09
 */
public class PersonalFragment extends Fragment implements PersonalContract.IPersonalFragment,View.OnClickListener {
    private PersonalContract.IPersonalPresenter presenter;
    @BindView(R.id.pn_list_view) ListView list_view;
    @BindView(R.id.system_settings_tv) TextView system_settings_tv;
    @BindView(R.id.user_head_view) ImageView user_head_view;
    @BindView(R.id.user_name_tv) TextView user_name_tv;
    @BindView(R.id.tasks_posted_tv) TextView tasks_posted_tv;
    @BindView(R.id.tasks_accepted_tv) TextView tasks_accepted_tv;
    private Context mContext;
    private ArrayList<ListItem> list = new ArrayList<>();
    private SharedPreferences preferences;
    public PersonalFragment() {
    }
    public static PersonalFragment newInstance() {
        PersonalFragment fragment = new PersonalFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        ButterKnife.bind(this, view);
        system_settings_tv.setOnClickListener(this);
        user_head_view.setOnClickListener(this);
        user_name_tv.setOnClickListener(this);
        tasks_posted_tv.setOnClickListener(this);
        tasks_accepted_tv.setOnClickListener(this);
        mContext = view.getContext();
        ListItem item = new ListItem("充值W币",R.drawable.ic_chozhi,R.drawable.ic_chevron_right);
        item.setItemWarning("0");
        list.add(item);
        item = new ListItem("交易记录",R.drawable.ic_jilu,R.drawable.ic_chevron_right);
        list.add(item);
        item = new ListItem("我要提现",R.drawable.ic_tixian,R.drawable.ic_chevron_right);
        list.add(item);
        item = new ListItem("账号管理",R.drawable.ic_security,R.drawable.ic_chevron_right);
        list.add(item);
        final ListAdapter adapter = new ListAdapter(view.getContext(), list);
        list_view.setAdapter(adapter);
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:

                        break;
                    case 1:

                        break;
                    default:
                        break;
                }
            }
        });
        system_settings_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = list_view.getChildAt(1);
                TextView vsasa =(TextView)view.findViewById(R.id.item_warning_tv);
                vsasa.setText("1.0");

            }
        });


        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.system_settings_tv:
                break;
            case R.id.user_head_view:
                preferences = mContext.getSharedPreferences("user",Context.MODE_PRIVATE);
                if(preferences.getBoolean("user_is_login",false)){
                    Intent intent = new Intent(mContext, UserInfoActivity.class);
                    startActivityForResult(intent,1);
                }else {
                    Intent intent = new Intent(mContext,LoginActivity.class);
                    startActivityForResult(intent,0);
                }
                break;
            case R.id.user_name_tv:
                preferences = mContext.getSharedPreferences("user",Context.MODE_PRIVATE);
                if(preferences.getBoolean("user_is_login",false)){
                    Intent intent = new Intent(mContext, UserInfoActivity.class);
                    startActivityForResult(intent,1);
                }else {
                    Intent intent = new Intent(mContext,LoginActivity.class);
                    startActivityForResult(intent,0);
                }
                break;
            case R.id.tasks_posted_tv:
                break;
            case R.id.tasks_accepted_tv:
                break;
            default:
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 0:
                    Log.i("user_name",data.getStringExtra("user_name"));
                    user_name_tv.setText(data.getStringExtra("user_name"));
                    Glide.with(mContext)
                            .load(Url.Base_URL_HEAD+"?path="+data.getStringExtra("user_head"))
                            .error(R.mipmap.ic_launcher)
                            .into(user_head_view);
                    View view_msg = list_view.getChildAt(0);
                    TextView msg =(TextView)view_msg.findViewById(R.id.item_warning_tv);
                    msg.setText(data.getStringExtra("msgs"));
                    View view_wb = list_view.getChildAt(1);
                    TextView wb =(TextView)view_wb.findViewById(R.id.item_warning_tv);
                    wb.setText(data.getIntExtra("user_wb",0)+"");
                    SharedPreferences preferences = mContext.getSharedPreferences("user",Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit= preferences.edit();
                    edit.putBoolean("user_is_login",true);
                    edit.apply();
                    break;
                case 1:
                    user_name_tv.setText(data.getStringExtra("user_name"));
                    Glide.with(mContext)
                            .load(data.getStringExtra("head"))
                            .error(R.mipmap.ic_launcher)
                            .into(user_head_view);
                    break;

                default:
                    break;

            }


        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attach(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dettach();
    }
    @Override
    public PersonalContract.IPersonalPresenter initPresenter() {
        return new PersonalPresenter();
    }


}
