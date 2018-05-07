package com.witkey.campuswitkey.adapter;

import android.view.View;

/**
 * 项目名称：witkey
 * 类描述：
 * 创建人：lsx
 * 创建时间：2018/4/30 12:18
 * 修改人：lsx
 * 修改时间：
 * 修改备注：
 */

public class ListItem {
    private String itemContent;
    private String itemWarning;
    private int itemViewLeftId;
    private int itemViewRightId;

    public ListItem(String itemContent, int itemViewLeftId, int itemViewRightId) {
        this.itemContent = itemContent;
        this.itemViewLeftId = itemViewLeftId;
        this.itemViewRightId = itemViewRightId;
    }
    public ListItem(String itemContent,String itemWarning, int itemViewRightId){
        this.itemContent = itemContent;
        this.itemWarning = itemWarning;
        this.itemViewRightId = itemViewRightId;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }

    public String getItemWarning() {
        return itemWarning;
    }

    public void setItemWarning(String itemWarning) {
        this.itemWarning = itemWarning;
    }

    public int getItemViewLeftId() {
        return itemViewLeftId;
    }

    public void setItemViewLeftId(int itemViewLeftId) {
        this.itemViewLeftId = itemViewLeftId;
    }

    public int getItemViewRightId() {
        return itemViewRightId;
    }

    public void setItemViewRightId(int itemViewRightId) {
        this.itemViewRightId = itemViewRightId;
    }


}
