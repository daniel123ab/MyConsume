package com.example.myconsume.entiy;

import org.litepal.crud.DataSupport;

public class User extends DataSupport {
    private int id;
    private String userName;
    private String password;
    private String sex;
    private long phone;

    public User() {
    }

    public User(String userName, String password, String sex, long phone) {
        this.userName = userName;
        this.password = password;
        this.sex = sex;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getSex() {
        return sex;
    }

    public long getPhone() {
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }
}
