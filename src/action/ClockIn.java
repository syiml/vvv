package action;

import ClockIn.*;
import util.MainResult;
import util.Tool;

import java.util.Calendar;

/**
 * Created by Syiml on 2015/7/27 0027.
 */
public class ClockIn extends BaseAction{
    public int id;
    public String username;
    public String ttime_d;
    public String sign;
    public String ip;
    public String todytimes;
    public String info;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTtime_d() {
        return ttime_d;
    }

    public void setTtime_d(String time_d) {
        this.ttime_d = time_d;
    }

    public String getTodytimes() {
        return todytimes;
    }

    public void setTodytimes(String todytimes) {
        this.todytimes = todytimes;
    }

    public String userClockIn(){
        ClockInSQL.ClockIn();
        return "success";
    }
    public String adminAdd(){
        ClockInRecord cir=new ClockInRecord();
        cir.username=username;
        cir.sign=sign.equals("0")?"请假"+info:sign.equals("1")?"正常":"迟到";
        cir.time= Tool.getTimestamp(ttime_d, "23", "59");
        cir.todytimes=Integer.parseInt(todytimes);
        cir.ip="";
        ClockInSQL.add(cir);
        return "success";
    }
    public String del(){
        ClockInSQL.del(id);
        return "success";
    }


    public String userNewClockIn(){
        MainResult mr = ClockInMain.clockIn();
        this.setPrompt(mr.getPrompt());
        return SUCCESS;
    }
}
