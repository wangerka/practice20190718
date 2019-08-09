package com.example.netredclock;

import java.util.Calendar;

public class Util {
    public static String getWeek(int index){
        String w="星期一";
        if(index == Calendar.MONDAY){
            w= "星期一";
        } else if(index == Calendar.TUESDAY){
            w= "星期二";
        } else if(index == Calendar.WEDNESDAY){
            w= "星期三";
        } else if(index == Calendar.THURSDAY){
            w= "星期四";
        } else if(index == Calendar.FRIDAY){
            w= "星期五";
        } else if(index == Calendar.SATURDAY){
            w= "星期六";
        } else if(index == Calendar.SUNDAY){
            w= "星期日";
        }
        return w;
    }
}
