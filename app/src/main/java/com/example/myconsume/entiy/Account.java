package com.example.myconsume.entiy;

import org.litepal.crud.DataSupport;

public class Account extends DataSupport {
    private int id;
    private int userId;
    private String type;
    private float balance; //余额
    private String accountId;   //账户Id

    public static final String[] TYPE={"支付宝","微信","银行卡","花呗"};

    public Account(){

    };
    public Account(int userId, String type, float balance, String accountId) {
        this.userId = userId;
        this.type = type;
        this.balance = balance;
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
