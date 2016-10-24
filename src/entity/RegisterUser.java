package entity;


import util.HTML.HTML;
import util.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2015/6/5.
 */
public class RegisterUser implements IBeanResultSetCreate<RegisterUser> {
    //注册状态 0:等待 1:已参加 -1:拒绝  2:* 3:需修改  4:通过
    public final static int STATUS_FAILD=-1;
    public final static int STATUS_PADDING=0;
    public final static int STATUS_APPENDED=1;
    public final static int STATUS_UNOFFICIAL=2;
    public final static int STATUS_MUST_EDIT=3;
    public final static int STATUS_ACCEPTED=4;
    public final static int STATUS_ADMIN = 5; //管理员
    public final static int STATUS_TEAM_AUTO = 6;//集训队员自动报名，扣除了ACB，计算完rating后要返还ACB

    public String getInfo() {
        return info;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStatu() {
        return statu;
    }
    public Timestamp getTime(){return time;}

    private String username;
    private int statu;//注册状态 0:等待 1:已参加 -1:拒绝  2:* 3:需修改  4:通过
    private String info = "";
    private Timestamp time;

    public RegisterUser(){}
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
            case 5: return HTML.textb("管理员","#00aaaa");
            case STATUS_TEAM_AUTO: return HTML.textb("集训队员","#7E48B7");
        }
        return HTML.textb("ERROR","orange");
    }

    @Override
    public RegisterUser init(ResultSet rs) throws SQLException {
        username=rs.getString("username");
        statu=rs.getInt("statu");
        info=rs.getString("info");
        time=rs.getTimestamp("time");
        return this;
    }
    public String getShowUserName(){
        User user = Main.users.getUser(username);
        if(user!=null){
           return (user.getUsernameHTML());
        }else{
           return (username);
        }
    }
    public String getShowNick(){
        User user = Main.users.getUser(username);
        if(user!=null) {
            return (user.getNick());
        }else{
           return (HTML.textb("未注册","red"));
        }
    }

    public void setStatu(int statu) {
        this.statu = statu;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
