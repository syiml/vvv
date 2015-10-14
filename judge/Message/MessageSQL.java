package Message;

import Discuss.DiscussSQL;
import Discuss.Discuss;
import Discuss.DiscussReply;
import Main.User.User;
import Main.contest.Contest;
import Main.contest.RegisterUser;
import Main.status.Result;
import Tool.HTML.HTML;
import Tool.SQL;
import Main.Main;
import com.google.gson.Gson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syiml on 2015/7/10 0010.
 */
public class MessageSQL {
    public static Message getMessage(int mid){
        ResultSet rs=SQL.query("SELECT * FROM t_message WHERE mid=?", mid);
        try {
            if(rs.next()) {
                return new Message(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static int insert(Message m){
        return SQL.update("INSERT INTO t_message(user,statu,title,text,time,deadline) values(?,?,?,?,?,?)",
                m.user,m.statu,m.title,m.text,Main.now(),m.deadline);
    }
    public static int setReaded(int mid){
        return SQL.update("UPDATE t_message SET statu=1 WHERE mid=?",mid);
    }
    public static List<Message> getMessages(String user,int from,int num){
        ResultSet rs=SQL.query("SELECT * FROM t_message WHERE user=? ORDER BY time DESC LIMIT ?,?",user,from,num);
        List<Message> list=new ArrayList<Message>();
        try {
            while(rs.next()){
                list.add(new Message(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static int setReaded(String user){
        return SQL.update("UPDATE t_message SET statu=1 WHERE user=?",user);
    }
    public static int AddMessageDisscussReply(String append,int did,String text){
        Discuss d=DiscussSQL.getDiscuss(did);
        User u=Main.users.getUser(append);
        Message m=new Message();
        m.user=d.getUsername();
        if(m.user.equals(append)) return 0;
        m.statu=0;
        m.title="你的帖子【"+d.getTitle()+"】有新回复";
        m.text=u.getUsernameHTML()+"("+u.getNick()+")回复了你的帖子【"+d.getTitle()+"】：</br>"+text+"</br>"+HTML.a("Discuss.jsp?id="+did,"查看帖子");
        m.deadline=new Timestamp(86400000L * 30 + System.currentTimeMillis());//保留30天
        return insert(m);
    }
    public static int AddMessageDiscussReplyAdmin(int did,int rid,String text){//管理员回复
        DiscussReply dr=DiscussSQL.getDiscussReply(did,rid);
        Discuss d=DiscussSQL.getDiscuss(did);
        Message m=new Message();
        m.user=dr.getUsername();
        m.statu=0;
        m.title="管理员回复了你在帖子【"+d.getTitle()+"】的回复";
        m.text="管理员回复了你在帖子【"+d.getTitle()+"】的回复：</br>"+text+"</br>"+HTML.a("Discuss.jsp?id="+did,"查看帖子");
        m.deadline=new Timestamp(System.currentTimeMillis()+ 86400000L * 30L );//保留30天
        return insert(m);
    }
    public static int getNoRead(String user){
        ResultSet rs=SQL.query("SELECT COUNT(*) FROM t_message WHERE statu=0 AND user=?",user);
        try {
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static int AddMessageRatingChange(int cid,String user,int prating,int rating){
        Contest c=Main.contests.getContest(cid);
        Message m=new Message();
        m.user=user;
        m.statu=0;
        m.title="Rating值有新变化";
        if(prating==-100000){
            m.text="由于参加了比赛【"+c.getName()+"】,您的rating值变为"+User.ratingToHTML(rating)+"。";
        }else{
            m.text="由于参加了比赛【"+c.getName()+"】,您的rating值由"+User.ratingToHTML(prating)+"变为"+User.ratingToHTML(rating)+"。";

        }
        m.deadline=new Timestamp(86400000L * 30 + System.currentTimeMillis());//保留30天
        return insert(m);
    }
    public static int addMessageRegisterContest(String user,int cid,int statu){
        Contest c=Main.contests.getContest(cid);
        Message m=new Message();
        m.user=user;
        m.statu=0;
        m.title="你注册的比赛【"+c.getName()+"】已经审核完成了";
        m.text="你注册的比赛【"+c.getName()+"】已经审核完成了：</br>审核结果："+ RegisterUser.statuToHTML(statu)+"<br>"+HTML.a("User.jsp?cid="+cid,"点击查看");
        m.deadline=new Timestamp(86400000L * 30 + System.currentTimeMillis());//保留30天
        return insert(m);
    }
    public static int addMessageWelcome(User u){
        Message m=new Message();
        m.user=u.getUsername();
        m.statu=0;
        m.title="欢迎新用户";
        m.text=u.getNick()+"，欢迎您入驻T^T Online Judge，当前OJ是测试版，如有发现BUG，可以提交到【"+HTML.a("Discuss.jsp?id=6","BUG和建议收集")+"】贴<br>";
        m.text+="更多新用户帮助，可以查看【"+HTML.a("Discuss.jsp?id=4","FAQ")+"】也可以直接在底下回复提问。";
        m.deadline=new Timestamp(86400000L * 30 + System.currentTimeMillis());//保留30天
        return insert(m);
    }
    public static int addMessageAwardACB(String u,int num,String text){
        Message m=new Message();
        m.user=u;
        m.statu=0;
        m.title="您收到了"+num+"ACB奖励";
        m.text="您收到了"+num+"ACB奖励：<br>备注信息："+text;
        m.deadline=new Timestamp(86400000L * 30 + System.currentTimeMillis());//保留30天
        return insert(m);
    }
}
