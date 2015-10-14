package Main.contest;

import Tool.HTML.HTML;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2015/6/5.
 */
public class RegisterUser {
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
    private int statu;//注册状态 0:pending 1:accepted -1:no  2:* 3:拒绝但可重新报名
    private String info;
    private Timestamp time;

    public RegisterUser(ResultSet r) throws SQLException {
        //username,statu,info
        username=r.getString("username");
        statu=r.getInt("statu");
        info=r.getString("info");
        time=r.getTimestamp("time");
    }
    public static String statuToHTML(int statu){
        if(statu==0){
            return HTML.textb("等待","gray");
        }
        if(statu==1){
            return HTML.textb("通过","green");
        }
        if(statu==-1){
            return HTML.textb("拒绝","red");
        }
        if(statu==2){
            return HTML.textb("＊","blue");
        }
        if(statu==3){
            return HTML.textb("需修改","#ff00ff");
        }
        return HTML.textb("ERROR","orange");
    }
}
