package com.witkey.campuswitkey.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.witkey.campuswitkey.R;

import java.util.ArrayList;

/**
 * 项目名称：witkey
 * 类描述：
 * 创建人：lsx
 * 创建时间：2018/4/30 20:31
 * 修改人：lsx
 * 修改时间：
 * 修改备注：
 */

public class ListAdapter2 extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private ArrayList<ListItem> mListItems;

    public ListAdapter2(Context mContext, ArrayList<ListItem> mListItems) {
        this.mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mListItems = mListItems;
    }

    @Override
    public int getCount() {
        return mListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ListAdapter2.ViewHolder holder = null;
        if(convertView == null){
            holder = new ListAdapter2.ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item2, null);
            holder.item_ll = (LinearLayout) convertView.findViewById(R.id.item_ll);
            holder.item_content_tv = (TextView)convertView.findViewById(R.id.item_content_tv);
            holder.item_warning_tv = (TextView)convertView.findViewById(R.id.item_warning_tv);
            holder.item_view_right = (ImageView) convertView.findViewById(R.id.item_view_right);
        }else {
            holder = (ListAdapter2.ViewHolder)convertView.getTag();
        }
        ListItem item =  mListItems.get(position);
        holder.item_content_tv.setText(item.getItemContent());
        if(item.getItemWarning() != null){
            holder.item_warning_tv.setText(item.getItemWarning());
        }
        holder.item_view_right.setImageResource(item.getItemViewRightId());
        return convertView;
    }

    public final class ViewHolder {
        private LinearLayout item_ll;
        public ImageView item_view_right;
        public TextView item_content_tv;
        public TextView item_warning_tv;

    }
}
