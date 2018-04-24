package com.witkey.campuswitkey.model.entity;

/**
 * Created by 12554 on 2018/3/28.
 */

public class User {
    private Integer user_id;
    private String user_email;
    private String user_pwd;
    private String user_name;
    private String user_head;
    private String user_major;
    private String user_sex;
    private Integer user_wb;
    private String user_create_date;
    private String user_state;

    public User(){

    }
    public User(String user_email, String user_pwd){
        this.user_email = user_email;
        this.user_pwd = user_pwd;
    }
    public User(Integer user_id, String user_email, String user_pwd, String user_name, String user_head, String user_major, String user_sex, Integer user_wb, String user_create_date, String user_state) {
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_pwd = user_pwd;
        this.user_name = user_name;
        this.user_head = user_head;
        this.user_major = user_major;
        this.user_sex = user_sex;
        this.user_wb = user_wb;
        this.user_create_date = user_create_date;
        this.user_state = user_state;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_major() {
        return user_major;
    }

    public void setUser_major(String user_major) {
        this.user_major = user_major;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public Integer getUser_wb() {
        return user_wb;
    }

    public void setUser_wb(Integer user_wb) {
        this.user_wb = user_wb;
    }

    public String getUser_create_date() {
        return user_create_date;
    }

    public void setUser_create_date(String user_create_date) {
        this.user_create_date = user_create_date;
    }

    public void setUser_head(String user_head) {
        this.user_head = user_head;
    }

    public String getUser_head() {
        return user_head;
    }

    public String getUser_state() {
        return user_state;
    }

    public void setUser_state(String user_state) {
        this.user_state = user_state;
    }
}
