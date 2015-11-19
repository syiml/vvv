package Discuss;

import Main.Main;
import Main.User.User;
import action.addDiscuss;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by Syiml on 2015/7/3 0003.
 */
public class Discuss {
    int id;
    int cid;
    String title;
    String username;
    Timestamp time;
    String text;
    double priority;
    boolean top;
    boolean visiable;
    boolean reply;
    int panelclass;
    boolean panelnobody=false;
    boolean showauthor;
    boolean showtime;
    int shownum;
    int replyNum=0;
    boolean isadmin=false;

    boolean replyHidden=false;

    public boolean isReplyHidden() {
        return replyHidden;
    }
    public void setAdmin(){isadmin=true;}
//    public Discuss(){
//
//    }
    public String getUsername(){return username;}
    public String getTitle(){return title;}
    public Discuss(ResultSet rs) throws SQLException {
        id=rs.getInt("id");
        cid=rs.getInt("cid");
        title=rs.getString("title");
        username=rs.getString("username");
        time=rs.getTimestamp("time");
        text=rs.getString("text");
        priority=rs.getDouble("priority");
        top=rs.getBoolean("top");
        shownum=rs.getInt("shownum");
        visiable=rs.getBoolean("visiable");
        reply=rs.getBoolean("reply");
        panelclass=rs.getInt("panelclass");
        panelnobody=rs.getBoolean("panelnobody");
        showauthor=rs.getBoolean("showauthor");
        showtime=rs.getBoolean("showtime");
        replyHidden=rs.getBoolean("replyhidden");
        replyNum=rs.getInt("replynum");
        //System.out.print(rs.getBoolean("panelnobody"));
    }
    public Discuss(addDiscuss ad){
        id= Integer.parseInt(ad.getId());
        cid=ad.getCid();
        title=ad.getTitle();
        User u=((User) Main.getSession().getAttribute("user"));
        if(u==null) username="";
        else  username=u.getUsername();
        time=new Timestamp(System.currentTimeMillis());
        text=ad.getText();
        try {
            priority = Double.parseDouble(ad.getPriority());
        }catch(NumberFormatException e){
            priority = -1;
        }
        top=ad.getTop()!=null;
        visiable=ad.getVisiable()!=null;
        reply=ad.getReply()!=null;
        panelclass= Integer.parseInt(ad.getPanelclass());
        panelnobody=ad.getPanelnobody()!=null;
        showauthor=ad.getShowauthor()!=null;
        showtime=ad.getShowtime()!=null;
        shownum= Integer.parseInt(ad.getShownum());
        replyHidden=ad.getReplyhidden()!=null;
    }
    static String[] PanelClass={"default","primary","success","info","warning","danger"};
    public String goAddOrEdit(){
        User u=Main.loginUser();
        if(u==null) return "error";
        else username=u.getUsername();
        if(id==-1){
            Main.log(Main.loginUser().getUsername()+"发表了新帖子 【"+title+"】");
            DiscussSQL.addDiscuss(this);
        }else{
            Main.log(Main.loginUser().getUsername()+"修改了帖子 【"+title+"】");
            DiscussSQL.editDiscuss(this);
        }
        return "error";
    }
}
