package ClockIn;

import Main.Main;
import Main.User.User;
import Tool.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syiml on 2015/7/27 0027.
 */
public class ClockInSQL {

    public static List<List<Long>> times;//[][3]  开始时间 迟到时间 截止时间
    public static String ip="10.10.115";//ip段
//    public static String ip="";
    static{
        init();
    }
    public static void init(){
        times=new ArrayList<List<Long>>();
        ArrayList<Long> t=new ArrayList<Long>();
        t.add(time(7,0,0));
        t.add(time(9,10,0));
        t.add(time(11,0,0));
        times.add(t);

        t=new ArrayList<Long>();
        t.add(time(13, 30, 0));
        t.add(time(14,10,0));
        t.add(time(17,0,0));
        times.add(t);

        t=new ArrayList<Long>();
        t.add(time(18, 0, 0));
        t.add(time(18,40,0));
        t.add(time(21, 0,0));
        times.add(t);
    }
    public static long time(int h,int m,int s){
        return (long)(h*60*60+m*60+s)*1000;
    }
    public static boolean isin(Timestamp t,long l,long r){
        long time=(t.getTime()+1000*60*60*8)%(1000*60*60*24);
        //System.out.println(""+ClockInHTML.timeShow(time)+" "+l+" "+r);
        if(l<r){
            return l<=time&&time<=r;
        }else{
            return time<=r||l<=time;
        }
    }
    public static int getTimeNum(Timestamp t){//begin from 0
        for(int i=0;i<times.size();i++){
            if(isin(t,times.get(i).get(0),times.get(i).get(2))) return i;
        }
        return -1;
    }
    public static int mustClockIn(){
        if(Main.loginUser()==null) return -1;//未登录
        Timestamp now=Main.now();
        int z=getTimeNum(now);
//        System.out.println("z="+z);
        if(z>=0){//签到时间
            if(!ipCan(Main.getIP())) return -3;//非机房IP
            if(!haveClockIn()){
                return z;//需要签到
            }else{
                return -4;//已经签到
            }
        }else{
            return -2;//不在签到时段
        }
    }
    public static long getDay(Timestamp t){
        return (t.getTime()+8*1000*60*60)/(1000*60*60*24);
    }
    public static int getTimeNum2(Timestamp t){//begin from 0
        for(int i=times.size()-1;i>=0;i--){
            long time=(t.getTime()+1000*60*60*8)%(1000*60*60*24);
            if(times.get(i).get(0)<time) return i;
        }
        return -1;
    }
    public static Timestamp getL(long day){
        return new Timestamp(day*1000*60*60*24-8*1000*60*60);
    }
    public static Timestamp getR(long day){
        return new Timestamp(day*1000*60*60*24+16*1000*60*60);
    }
    ///////////////////////////////////////////////
    public static void ClockIn(){
        int z=mustClockIn();
        if(z!=-1){
            ClockInRecord cir=new ClockInRecord();
            cir.ip=Main.getIP();
            Timestamp now=Main.now();
            cir.time=now;
            cir.username=Main.loginUser().getUsername();
            cir.todytimes=z;
            if(isin(now,times.get(z).get(0),times.get(z).get(1))){
                cir.sign="正常";
            }else{
                cir.sign="迟到";
            }
            add(cir);
        }
    }
    public static boolean haveClockIn(){
        User u=Main.loginUser();
        Timestamp now=Main.now();
        int x=getTimeNum(now);
        if(x!=-1){
            long nowtime=(now.getTime()+(1000*60*60*8))%(1000*60*60*24);
            long nowdate=(now.getTime()+(1000*60*60*8))/(1000*60*60*24)*(1000*60*60*24)-1000*60*60*8;
            Timestamp l=new Timestamp(nowdate+times.get(x).get(0));
            Timestamp r;
            if(times.get(x).get(0)<times.get(x).get(2)) r=new Timestamp(nowdate+times.get(x).get(2));
            else r=new Timestamp(nowdate+times.get(x).get(2)+1000*60*60*24);
            ResultSet rs=SQL.query("SELECT * FROM t_clock_in WHERE time<=? AND time>=? AND username=? ",r,l,u.getUsername());
            try {
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    public static List<ClockInRecord> getClockInStatus(String username){
        List<ClockInRecord> list=new ArrayList<ClockInRecord>();
        ResultSet rs=SQL.query("SELECT * FROM t_clock_in WHERE username=?",username);
        try {
            while(rs.next()){
                list.add(new ClockInRecord(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static String ClockInStatus(String username,Long day,int times){
//        System.out.rintln(new Timestamp((day + 1) * (1000 * 60 * 60 * 24)-(1000*60*60*8)).toString()
//                +" "+new Timestamp((day * (1000 * 60 * 60 * 24)-(1000*60*60*8))).toString() +" "+times);
        ResultSet rs=SQL.query("SELECT * FROM t_clock_in WHERE username=? AND todytimes=? AND time<=? AND time>=?",
                username, times, getR(day), getL(day));
        try {
            if(rs.next()){
                ClockInRecord cir=new ClockInRecord(rs);
                return cir.sign;
            }else{
                return "旷课";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "未知";
    }
    public static void add(ClockInRecord cir){
        SQL.update("INSERT INTO t_clock_in(username,time,sign,ip,todytimes) VALUES(?,?,?,?,?)",
                cir.username,cir.time,cir.sign,cir.ip,cir.todytimes);
    }
    public static void del(int id){
        SQL.update("DELETE FROM t_clock_in WHERE id=?",id);
    }
    public static void update(int id,ClockInRecord cir){
        SQL.update("UPDATE t_clock_in SET username=?,sign=?",cir.username,cir.sign);
    }
    public static List<ClockInRecord> get(long day,int times){
        Timestamp l=getL(day);
        Timestamp r=getR(day);
        ResultSet rs=SQL.query("SELECT * FROM t_clock_in WHERE time<=? AND time>=? AND todytimes=? ORDER BY time",r,l,times);
        List<ClockInRecord> list=new ArrayList<ClockInRecord>();
        try {
            while(rs.next()){
                list.add(new ClockInRecord(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static List<ClockInRecord> getWithUser(String user){
        ResultSet rs=SQL.query("SELECT * FROM t_clock_in WHERE username=? ORDER BY time desc", user);
        List<ClockInRecord> list=new ArrayList<ClockInRecord>();
        try {
            while(rs.next()){
                list.add(new ClockInRecord(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static boolean ipCan(String ip){
        int len=ClockInSQL.ip.length();
        return ip.substring(0, len).equals(ClockInSQL.ip);
    }
}
