package com.example.myconsume.ui.slideshow;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class Constant {
    public static List<String> A ;
    public static List<String> B ;
    static{
        GregorianCalendar calendar=new GregorianCalendar();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int year_=year-2018;
        A=new ArrayList<>();
        for (int i = 0; i < year_; i++) {
            A.add(""+(2019+i));
        }
        B=new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            B.add(""+(i+1));
        }
    }
}
