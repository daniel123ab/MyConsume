package com.example.myconsume.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.CalendarContract;

import com.example.myconsume.activity.login.MainActivity;
import com.example.myconsume.entiy.Account;
import com.example.myconsume.entiy.Record;
import com.example.myconsume.entiy.User;
import com.example.myconsume.ui.card.CardFragment;

import org.litepal.crud.DataSupport;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {
    //获得当前登录用户Id
    public static int getUserId(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("user",context.MODE_PRIVATE);
        int id=sharedPreferences.getInt("id",-1);
        return id;
    }
    public static User getUser(Context context){
        int userId=getUserId(context);
        User user=DataSupport.where("id=?",String.valueOf(userId)).findFirst(User.class);
        return user;
    }
    //获得当前用户所有的消费记录
    public static List<Record> getRecords(Context context){
        int userId=getUserId(context);
        List<Record> records=DataSupport.where("userid=?",String.valueOf(userId)).find(Record.class);
        Collections.sort(records, new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return o1.getId()>o2.getId()?-1:1;
            }
        });
        return records;
    }

    public static List<Record> getOutRecords(Context context){
        int userId=getUserId(context);
        List<Record> records=DataSupport.where("userid=? and money<?",String.valueOf(userId),"0").find(Record.class);
        Collections.sort(records, new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return o1.getId()>o2.getId()?-1:1;
            }
        });
        for (Record record:records) {
            record.setMoney(-record.getMoney());
        }
        return records;
    }
    //获得当前用户的本月消费金额
    public static float getCurrentMonthConsumeMoney(Context context){
        List<Record> records=getRecords(context);
        GregorianCalendar nowTime=new GregorianCalendar();
        nowTime.setTimeInMillis(System.currentTimeMillis());
        float allMoney=0;
        for (Record record : records) {
            GregorianCalendar recordTime=DateUtil.getCalendar(record.getTime());
            int y=recordTime.get(Calendar.YEAR);
            int m=recordTime.get(Calendar.MONTH);
            int y1=nowTime.get(Calendar.YEAR);
            int m1=nowTime.get(Calendar.MONTH);
            if (y==y1&&m==m1&&record.getMoney()<0){
                allMoney+=record.getMoney();
            }
        }
        return allMoney;
    }
    //获得当前用户的所有消费类型的统计
    public static Map<String,Float> analyzeRecords(List<Record> records){
        Map<String,Float> analyze=new HashMap<>();
        for (Record record : records) {
            if (analyze.containsKey(record.getType())){
                float money=analyze.get(record.getType());
                analyze.remove(record.getType());
                analyze.put(record.getType(),money+record.getMoney());
            }else{
                analyze.put(record.getType(),record.getMoney());
            }
        }
        return analyze;
    }
    //获得当前用户所有的账户
    public static List<Account> getAccount(Context context){
        int userId=getUserId(context);
        List<Account> accounts=DataSupport.where("userid=?",String.valueOf(userId)).find(Account.class);
        return accounts;
    }
    //通过AccountId获得Account
    public static Account getAccountById(int accountId){
        Account account=DataSupport.where("id=?",String.valueOf(accountId)).findFirst(Account.class);
        return account;
    }

    //通过recordId获得Record
    public static Record getRecordById(int id){
        Record record=DataSupport.where("id=?",String.valueOf(id)).findFirst(Record.class);
        return record;
    }

    //获得用户的所有账户类型名
    public static List<String> getAccountType(List<Account> accounts){
        List<String> accountTypes=new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++) {
            Account account=accounts.get(i);
            if (account.getType().equals("默认")){
                accountTypes.add(account.getType());
            }else {
                accountTypes.add(account.getType()+"("+account.getAccountId()+")");
            }
        }
        return accountTypes;
    }
    //通过卡Id获得通过该卡消费的记录
    public static List<Record> getRecordsByAccountId(Context context, int accountId){
        List<Record> records=DataSupport
                .where("userid=? and accountid=?",String.valueOf(getUserId(context)),String.valueOf(accountId)).find(Record.class);
        return records;
    }

    public static String formatMoney(float money){
        DecimalFormat decimalFormat=new DecimalFormat("0.00");
        return decimalFormat.format(money);
    }

    //通过时间查询Record
    public static List<Record> getRecordsByTime(Context context,long start,long end){
        List<Record> records=DataSupport
                .where("userid=? and time>? and time<=?",String.valueOf(getUserId(context)),String.valueOf(start),String.valueOf(end)).find(Record.class);
        return records;
    }



    //获取用户当月的记录
    public static List<Record> getCRecords(Context context){
        GregorianCalendar calendar=new GregorianCalendar();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        return getRecordsByTime(context,DateUtil.getLongTime(year,month),DateUtil.getLongTime(year,month+1));
    }
    //获得用户当月的总支出
    public static float getCOut(Context context){
        List<Record> records=getCRecords(context);
        float allOut=0;
        for (Record record : records) {
            if (record.getMoney()<0){
                allOut+=-record.getMoney();
            }
        }
        return allOut;
    }

    //获得用户当月总收入
    public static float getCIn(Context context){
        List<Record> records=getCRecords(context);
        float allIn=0;
        for (Record record : records) {
            if (record.getMoney()>0){
                allIn+=record.getMoney();
            }
        }
        return allIn;
    }
    //获得用户当日的记录
    public static List<Record> getCDRecord(Context context){
        GregorianCalendar calendar=new GregorianCalendar();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        return getRecordsByTime(context,DateUtil.getLongTime(year,month,day),DateUtil.getLongTime(year,month,day+1));
    }

}
