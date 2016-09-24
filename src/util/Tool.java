package util;

import entity.Log;
import entity.User;
import util.SQL.SQL;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

enum ANSI{
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m");
    String color;
    ANSI(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color;
    }
}

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
        System.out.println("["+now()+"]"+s+"["+stacks[stackDepth]+"]");
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
            Thread current = Thread.currentThread();
            String nowTime = now().toString();
            while(nowTime.length() < 23) nowTime+="0";
            System.out.println(ANSI.CYAN+"【"+nowTime+"|"+current.getId()+"】"+ANSI.RESET+s+ANSI.GREEN+"["+stacks[stackDepth]+"]"+ANSI.RESET);
        }
    }
    public static void debug(String s,String className){
        if(Main.isDebug){
            StackTraceElement[] stacks = new Throwable().getStackTrace();
            int stackDepth;
            for(stackDepth=1;stackDepth<stacks.length;stackDepth++){
                if(!stacks[stackDepth].getClassName().equals(className)
                        &&!stacks[stackDepth].getMethodName().equals("debug")
                        &&!stacks[stackDepth].getMethodName().equals("SQLDebug")) break;
            }
            Thread current = Thread.currentThread();
            String nowTime = now().toString();
            while(nowTime.length() < 23) nowTime+="0";
            System.out.println(ANSI.CYAN+"【"+nowTime+"|"+current.getId()+"】"+ANSI.RESET+s+ANSI.GREEN+"["+stacks[stackDepth]+"]"+ANSI.RESET);
        }
    }
    public static void SQLDebug(Long time, String sql){
        if(Main.isDebug){
            if(time < 10){
                debug("{"+time+"}"+sql,SQL.class.getName());
            }else if(time < 100){
                debug(ANSI.YELLOW+"{"+time+"}"+ANSI.RESET+sql,SQL.class.getName());
            }else{
                debug(ANSI.RED+"{"+time+"}"+ANSI.RESET+sql,SQL.class.getName());
            }
        }
    }
    public static Timestamp getTimestamp(String d,String s,String m){
        //System.out.println(d + " " + s + ":" + m + ":00");
        return Timestamp.valueOf(d + " " + s + ":" + m + ":00");
    }
    public static int randNum(int l,int r){
        if(l>r) return 0;
        return (int)(Math.random()*(r-l+1)+l);
    }
}
