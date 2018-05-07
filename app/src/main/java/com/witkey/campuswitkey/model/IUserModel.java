package com.witkey.campuswitkey.model;

import com.witkey.campuswitkey.model.entity.User;

/**
 * Created by 12554 on 2018/4/10.
 */

public interface IUserModel {
    void checkUser(String username, String pwd, final LoadTasksCallBack callBack);
    void getCheckCode(String email, final LoadTasksCallBack callBack);
    void resetPwd(User user, final LoadTasksCallBack callBack);
    void addUser(User user, final LoadTasksCallBack callBack);
    void getUserInfo(int id, final LoadTasksCallBack callBack);
    void updateUser(User user, final LoadTasksCallBack callBack);
    void updateUserAndHead(User user, final LoadTasksCallBack callBack);
}
