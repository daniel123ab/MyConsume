package com.example.myconsume.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    public static String FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";

    public static String getTime(GregorianCalendar calendar,String dateFormat){
        SimpleDateFormat format=new SimpleDateFormat(dateFormat);
        return format.format(calendar.getTime());
    }

    public static GregorianCalendar getCalendar(long time){
        GregorianCalendar calendar=new GregorianCalendar();
        calendar.setTimeInMillis(time);
        return calendar;
    }


    public static long getLongTime(int year,int month){
        GregorianCalendar calendar=new GregorianCalendar();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,0);
        calendar.set(Calendar.HOUR,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        return calendar.getTimeInMillis();
    }
    public static long getLongTime(int year,int month,int day){
        GregorianCalendar calendar=new GregorianCalendar();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.HOUR,-12);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTimeInMillis();
    }

    public static long getMillis(String strDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parse(strDate));
        return calendar.getTimeInMillis();
    }

    /**
     * 使用预设格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @return 提取字符串的日期
     */
    public static Date parse(String strDate) {
        return parse(strDate, getDatePattern());
    }
    /**
     * 获得默认的 date pattern
     *
     * @return 默认的格式
     */
    public static String getDatePattern() {
        return FORMAT_YMDHMS;
    }
    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return 提取字符串日期
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
