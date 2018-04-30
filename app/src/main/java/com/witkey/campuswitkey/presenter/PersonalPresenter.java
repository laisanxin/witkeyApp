package com.witkey.campuswitkey.presenter;

import com.witkey.campuswitkey.contract.PersonalContract;

/**
 * 项目名称：witkey
 * 类描述：
 * 创建人：lsx
 * 创建时间：2018/4/30 15:01
 * 修改人：lsx
 * 修改时间：
 * 修改备注：
 */

public class PersonalPresenter implements PersonalContract.IPersonalPresenter {
    private PersonalContract.IPersonalFragment view;

    public PersonalPresenter() {
    }

    @Override
    public void attach(PersonalContract.IPersonalFragment view) {
        if(view != null){
            this.view = view;
        }
    }
    @Override
    public void dettach() {
        if(this.view != null){
            this.view = null;
        }

    }
}
