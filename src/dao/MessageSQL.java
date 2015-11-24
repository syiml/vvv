package dao;

import entity.Message;
import util.SQL;
import util.Main;
import util.Tool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syiml on 2015/7/10 0010.
 */
public class MessageSQL {
    public static Message getMessage(int mid){
        return new SQL("SELECT * FROM t_message WHERE mid=?", mid).queryBean(Message.class);
    }
    public static List<Message> getMessages(String user,int from,int num){
        return new SQL("SELECT * FROM t_message WHERE user=? ORDER BY time DESC LIMIT ?,?",user,from,num).queryBeanList(Message.class);
    }
    public static int save(Message m){
        return new SQL("INSERT INTO t_message(user,statu,title,text,time,deadline) values(?,?,?,?,?,?)",
                m.getUser(),m.getStatu(),m.getTitle(),m.getText(), Tool.now(),m.getDeadline()).update();
    }
    public static int setReaded(int mid){
        return new SQL("UPDATE t_message SET statu=1 WHERE mid=?",mid).update();
    }
    public static int getNoRead(String user){
        return new SQL("SELECT COUNT(*) FROM t_message WHERE statu=0 AND user=?",user).queryNum();
    }
    public static int setReaded(String user){
        return new SQL("UPDATE t_message SET statu=1 WHERE user=?",user).update();
    }
}
