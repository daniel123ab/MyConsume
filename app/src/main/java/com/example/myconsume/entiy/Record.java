package com.example.myconsume.entiy;

import org.litepal.crud.DataSupport;

import java.sql.Date;
import java.sql.Time;
import java.util.GregorianCalendar;

public class Record extends DataSupport {
    private int id;
    private int userId;
    private float money;
    private String type;
    private String pay_method;
    private long time;
    private String remarks;     // 备注
    private int accountId;      //消费该记录所有的卡号

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public static final String PAY_TYPE_1="图书教育";
    public static final String PAY_TYPE_2="餐饮美食";
    public static final String PAY_TYPE_3="投资理财";
    public static final String PAY_TYPE_4="休闲生活";
    public static final String PAY_TYPE_5="充值缴费";
    public static final String PAY_TYPE_6="其它";


    public Record(int userId, float money, String type, String pay_method, long time,String remarks,int accountId) {
        this.userId = userId;
        this.money = money;
        this.type = type;
        this.pay_method = pay_method;
        this.time = time;
        this.remarks=remarks;
        this.accountId=accountId;

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

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPay_method() {
        return pay_method;
    }

    public void setPay_method(String pay_method) {
        this.pay_method = pay_method;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
