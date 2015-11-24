package action;

import ClockIn.*;
import util.Tool;

/**
 * Created by Syiml on 2015/7/27 0027.
 */
public class ClockIn {
    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getSign() {
        return sign;
    }
    public String getIp() {
        return ip;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTtime_d() {
        return ttime_d;
    }

    public String getTodytimes() {
        return todytimes;
    }

    public void setTtime_d(String time_d) {
        this.ttime_d = time_d;
    }

    public void setTodytimes(String todytimes) {
        this.todytimes = todytimes;
    }

    public int id;
    public String username;
    public String ttime_d;
    public String sign;
    public String ip;
    public String todytimes;
    public String info;

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
}
