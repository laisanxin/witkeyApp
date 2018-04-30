package com.witkey.campuswitkey.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.witkey.campuswitkey.R;
import com.witkey.campuswitkey.adapter.ListAdapter;
import com.witkey.campuswitkey.adapter.ListItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @version 1.0
 * @description
 * @authon lsx
 * create at 2018/4/30 12:09
 */
public class PersonalFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ListView list_view;
    private ArrayList<ListItem> list = new ArrayList<>();
    private TextView system_settings_tv;
    public PersonalFragment() {

    }
    public static PersonalFragment newInstance(String param1, String param2) {
        PersonalFragment fragment = new PersonalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        system_settings_tv  = (TextView) view.findViewById(R.id.system_settings_tv);

        list_view  = (ListView) view.findViewById(R.id.pn_list_view);
        ListItem item = new ListItem("我的消息",R.drawable.ic_textsms,R.drawable.ic_chevron_right);
        list.add(item);
        item = new ListItem("充值W币",R.drawable.ic_chozhi,R.drawable.ic_chevron_right);
        item.setItemWarning("0.0");
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
                        Intent intent = new Intent(view.getContext(),ShowMsgActivity.class);
                        intent.putExtra("test","0");
                        startActivity(intent);
                        break;
                    case 1:
                       intent = new Intent(view.getContext(),ShowMsgActivity.class);
                        intent.putExtra("test","1");
                        startActivity(intent);
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

    public void onButtonPressed(Uri uri) {


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
