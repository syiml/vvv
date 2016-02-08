package entity;


import util.HTML.HTML;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2015/6/5.
 */
public class RegisterUser {
    public static int STATUS_FAILD=-1;
    public static int STATUS_PADDING=0;
    public static int STATUS_APPENDED=1;
    public static int STATUS_UNOFFICIAL=2;
    public static int STATUS_MUST_EDIT=3;
    public static int STATUS_ACCEPTED=4;

    public String getInof() {
        return info;
    }
    public String getUsername() {
        return username;
    }
    public int getStatu() {
        return statu;
    }
    public Timestamp getTime(){return time;}

    private String username;
    private int statu;//注册状态 0:等待 1:已参加 -1:拒绝  2:* 3:需修改  4:通过
    private String info;
    private Timestamp time;

    public RegisterUser(ResultSet r) throws SQLException {
        //username,statu,info
        username=r.getString("username");
        statu=r.getInt("statu");
        info=r.getString("info");
        time=r.getTimestamp("time");
    }
    public static String statuToHTML(int status){
        switch (status){
            case -1: return HTML.textb("拒绝","red");
            case 0: return HTML.textb("等待", "gray");
            case 1: return HTML.textb("已参加","#00ff00");
            case 2: return HTML.textb("＊","blue");
            case 3: return HTML.textb("需修改","#ff00ff");
            case 4: return HTML.textb("通过","green");
        }
        return HTML.textb("ERROR","orange");
    }
}
