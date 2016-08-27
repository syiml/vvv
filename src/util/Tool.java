package util;

import entity.Log;
import entity.User;

import java.io.*;
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
        log(s,2);
    }
    public static void log(String s,int stackDepth){
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        System.out.println("["+now()+"|"+stacks[stackDepth]+"]"+s);
    }

    public static void log(Exception e){
        try{
            ByteArrayOutputStream buf = new java.io.ByteArrayOutputStream();
            e.printStackTrace(new java.io.PrintWriter(buf, true));
            String expMessage = buf.toString();
            User loginUser=Main.loginUser();
            Log log=new Log(now(),expMessage,loginUser==null?null:loginUser.getUsername());
            Main.logs.save(log);
            if(Main.isDebug) e.printStackTrace();
        }catch (Exception e1){
            e1.printStackTrace();
        }
    }
    public static void debug(String s){
        debug(s,2);
    }
    public static void debug(String s,int stackDepth){
        if(Main.isDebug){
            StackTraceElement[] stacks = new Throwable().getStackTrace();
            System.out.println("【"+now()+"|"+stacks[stackDepth]+"】"+s);
        }
    }
    public static void debug(String s,String className){
        if(Main.isDebug){
            StackTraceElement[] stacks = new Throwable().getStackTrace();
            int stackDepth;
            for(stackDepth=1;stackDepth<stacks.length;stackDepth++){
                if(!stacks[stackDepth].getClassName().equals(className)) break;
            }
            System.out.println("【"+now()+"|"+stacks[stackDepth]+"】"+s);
        }
    }
    public static Timestamp getTimestamp(String d,String s,String m){
        //System.out.println(d + " " + s + ":" + m + ":00");
        return Timestamp.valueOf(d + " " + s + ":" + m + ":00");
    }
}
