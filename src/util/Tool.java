package util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 杂项功能
 * Created by Administrator on 2015/11/24 0024.
 */
public class Tool {
    public static int sleep(int t){
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            return 1;
        }
        return 1;
    }
    public static String nowDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }
    public static Timestamp now(){
        return new Timestamp(System.currentTimeMillis());
    }

    public static void log(String s){
        System.out.println(now()+"-> "+s);
    }
    public static void debug(String s){
        if(Main.isDebug){
            System.out.println(now()+"=> "+s);
        }
    }

    public static Timestamp getTimestamp(String d,String s,String m){
        //System.out.println(d + " " + s + ":" + m + ":00");
        return Timestamp.valueOf(d + " " + s + ":" + m + ":00");
    }
}
