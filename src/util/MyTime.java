package util;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by QAQ on 2016/9/16.
 */
public class MyTime {
    public static long SECOND = 1000L;
    public static long MINUTE = 1000L*60;
    public static long HOUR   = 1000L*60*60;
    public static long DAY    = 1000L*60*60*24;
    public static long MONTH  = 1000L*60*60*24*30;
    public static long YEAR   = 1000L*60*60*24*365;

    /**
     * 时间偏移计算，返回新的时间
     * @param time 基准时间
     * @param millisecond 偏移量
     * @return 返回一个新的Timestamp对象等于 基准时间加偏移量
     */
    public static Timestamp addTimestamp(Timestamp time, long millisecond){
        return new Timestamp(time.getTime()+millisecond);
    }

    /**
     * 获取某月份的第一天的0点0分0秒
     * 东八区为准
     * @param year 年份
     * @param month 月份
     * @return 新的Timestamp对象
     */
    public static Timestamp getFistTimeOfMonth(int year,int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return new Timestamp(calendar.getTimeInMillis());
    }
    /**
     * 获取某月份的结束时间
     * 东八区为准
     * @param year 年份
     * @param month 月份
     * @return 新的Timestamp对象
     */
    public static Timestamp getLastTimeOfMonth(int year,int month){
        month ++;
        if(month == 13){
            month = 1;
            year ++;
        }
        return getFistTimeOfMonth(year,month);
    }

    /**
     * 获取时间所在天的0点0分0秒
     * 东八区为准
     * @param time 时间
     * @return 新的Timestamp对象
     */
    public static Timestamp getFistTimeOfDay(Timestamp time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time.getTime());
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return new Timestamp(calendar.getTimeInMillis());
    }
    /**
     * 获取某天的0点0分0秒
     * 东八区为准
     * @param year 年份
     * @param month 月份
     * @return 新的Timestamp对象
     */
    public static Timestamp getFistTimeOfDay(int year,int month,int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return new Timestamp(calendar.getTimeInMillis());
    }
    /**
     * 获取时间所在天的结束时间
     * 东八区为准
     * @param time 时间
     * @return 新的Timestamp对象
     */
    public static Timestamp getLastTimeOfDay(Timestamp time){
        return addTimestamp(getFistTimeOfDay(time),DAY);
    }
    /**
     * 获取某天的结束时间
     * 东八区为准
     * @param year 年份
     * @param month 月份
     * @return 新的Timestamp对象
     */
    public static Timestamp getLastTimeOfDay(int year,int month,int day){
        return addTimestamp(getFistTimeOfDay(year,month,day),DAY);
    }

    public static Timestamp getFistTimeOfHour(Timestamp time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time.getTime());
        calendar.set(Calendar.MINUTE , 0);
        calendar.set(Calendar.SECOND , 0);
        calendar.set(Calendar.MILLISECOND , 0);
        return new Timestamp(calendar.getTimeInMillis());
    }
    public static Timestamp getLastTimeOfHour(Timestamp time){
        return addTimestamp(getFistTimeOfHour(time),HOUR);
    }
}
