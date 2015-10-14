package Discuss;

import Main.User.Permission;
import Main.User.User;
import Main.Main;
import Message.MessageSQL;
import Tool.HTML.HTML;
import Tool.SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syiml on 2015/7/3 0003.
 */
public class DiscussSQL {
    public static Discuss getDiscuss(int id){
        try {
            PreparedStatement p=null;
            p=Main.conn.prepareStatement("SELECT * FROM t_discuss WHERE id=?");
            p.setInt(1,id);
            ResultSet rs=p.executeQuery();
            if(rs.next()){
                return new Discuss(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Discuss> getDiscussTOP(boolean all){
        List<Discuss> list=new ArrayList<Discuss>();
        try {
            PreparedStatement p=null;
            String sql="SELECT * FROM t_discuss WHERE top=1";
            if(!all){
                sql+=" AND visiable=1";
            }
            sql+=" ORDER BY priority DESC";
            p=Main.conn.prepareStatement(sql);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                 list.add(new Discuss(rs));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Discuss> getDiscussList(int from,int num,boolean all,String seach,String user){//admin is all
        List<Discuss> list=new ArrayList<Discuss>();
        try {
            PreparedStatement p=null;
            String sql="SELECT * FROM t_discuss WHERE 1";
            if(!all){
                sql+=" AND visiable=1";
            }
            if(seach!=null&&!seach.equals("")){
                sql+=" AND title like '%"+seach+"%'";
            }
            if(user!=null&&!user.equals("")){
                sql+=" AND username='"+user+"'";
                if(!all){
                    sql+=" AND showauthor=1";
                }
            }
            sql+=" ORDER BY priority DESC";
            sql+=" LIMIT "+from+","+num;
            p=Main.conn.prepareStatement(sql);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                list.add(new Discuss(rs));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static int newid(){
        try {
            PreparedStatement p=null;
            p=Main.conn.prepareStatement("SELECT max(id)+1 FROM t_discuss");
            ResultSet rs=p.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }else{
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }
    public static void addDiscuss(Discuss d){
        try {
            PreparedStatement p=null;
            int id=newid();
            p=Main.conn.prepareStatement("INSERT INTO t_discuss values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            p.setInt(1, id);
            if(d.isadmin) p.setString(2, d.title);
            else p.setString(2,HTML.HTMLtoString(d.title));
            p.setInt(3, d.panelclass);
            p.setString(4, d.username);
            p.setTimestamp(5, d.time);
            if(d.isadmin)p.setString(6, d.text);
            else p.setString(6,HTML.pre(HTML.HTMLtoString(d.text)));
            if(d.priority==-1){
                p.setDouble(7,id);
            }else{
                p.setDouble(7,d.priority);
            }
            p.setBoolean(8, d.top);
            p.setBoolean(9, d.visiable);
            p.setBoolean(10,d.reply);
            p.setInt(11, d.shownum);
            p.setBoolean(12, d.panelnobody);
            p.setBoolean(13, d.showauthor);
            p.setBoolean(14,d.showtime);
            p.setBoolean(15,d.replyHidden);
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void apend(Discuss d){
        try{
            PreparedStatement p=null;
            String text=getDiscuss(d.id).text;
//            text+=HTML.textb("--------------------------------------","black")+"<br>";
            text+="<hr/>以下内容于["+HTML.textb(Main.now().toString().substring(0,19),"blue")+"]补充";
//            text+=HTML.textb("--------------------------------------","black")+"<br>";
            text+=HTML.pre(HTML.HTMLtoString(d.text));
            String sql="UPDATE t_discuss SET";
            sql+=" text=?";
            sql+="WHERE id=?";
            p=Main.conn.prepareStatement(sql);
            p.setString(1,text);
            p.setInt(2,d.id);
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void editDiscuss(Discuss d){
        try {
            PreparedStatement p=null;
            String sql="UPDATE t_discuss SET ";
            sql+=" title=?";
            sql+=",panelclass=?";
            sql+=",text=?";
            sql+=",priority=?";
            sql+=",top=?";
            sql+=",visiable=?";
            sql+=",reply=?";
            sql+=",shownum=?";
            sql+=",panelnobody=?";
            sql+=",showauthor=?";
            sql+=",showtime=?";
            sql+=",replyhidden=?";
            sql+=" WHERE id=?";
            p=Main.conn.prepareStatement(sql);
            if(d.isadmin) p.setString(1, d.title);
            else p.setString(1,HTML.HTMLtoString(d.title));
            p.setInt(2, d.panelclass);
//            p.setString(3, d.text);
            if(d.isadmin)p.setString(3, d.text);
            else p.setString(3,HTML.pre(HTML.HTMLtoString(d.text)));
            if(d.priority==-1){
                p.setDouble(4,d.id);
            }else{
                p.setDouble(4,d.priority);
            }
            p.setBoolean(5, d.top);
            p.setBoolean(6, d.visiable);
            p.setBoolean(7, d.reply);
            p.setInt(8, d.shownum);
            p.setBoolean(9, d.panelnobody);
            p.setBoolean(10, d.showauthor);
            p.setBoolean(11, d.showtime);
            p.setBoolean(12, d.replyHidden);
            p.setInt(13, d.id);
//            System.out.println(p);
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    ////////////////discuss replay///////////////////
    public static List<DiscussReply> getDiscussReplay(int did,int from,int to){
        List<DiscussReply> list=new ArrayList<DiscussReply>();
        try {
            PreparedStatement p;
            String sql="SELECT * FROM t_discussreply WHERE did=?";
            sql+=" AND rid>="+from+" AND rid<="+to;
            p=Main.conn.prepareStatement(sql);
            p.setInt(1,did);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                list.add(new DiscussReply(rs));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static DiscussReply getDiscussReply(int did,int rid){
        ResultSet rs=SQL.query("SELECT * FROM t_discussreply WHERE did=? AND rid=?", did, rid);
        try {
            if(rs.next()){
                return new DiscussReply(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static int getNewReplyId(int did){
        PreparedStatement p;
        try {
            p=Main.conn.prepareStatement("SELECT MAX(rid) FROM t_discussreply WHERE did=?");
            p.setInt(1,did);
            ResultSet rs=p.executeQuery();
            if(rs.next()){
                return rs.getInt(1)+1;
            }else{
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
    }
    public static String reply(User loginuser,int did,String text){
        Discuss d=getDiscuss(did);
        if(loginuser==null) return "error";
        try {
            PreparedStatement p;
            int newid=getNewReplyId(did);
            String sql="INSERT INTO t_discussreply VALUES(?,?,?,?,?,?,?,null)";
            p=Main.conn.prepareStatement(sql);
            p.setInt(1,newid);
            p.setInt(2,did);
            p.setString(3, loginuser.getUsername());
            p.setTimestamp(4, Main.now());
            p.setString(5, HTML.HTMLtoString(text));
            p.setBoolean(6, !d.isReplyHidden());
            p.setInt(7, 0);
            //System.out.println(p);
            p.executeUpdate();
            MessageSQL.AddMessageDisscussReply(loginuser.getUsername(),did,HTML.HTMLtoString(text));
            return "success";
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }
    public static String hideshow(int did,int rid){
        Permission per=Main.loginUserPermission();
        if(!per.getAddDiscuss())return "error";
        try {
            PreparedStatement p;
            String sql="UPDATE t_discussreply SET visiable=not visiable WHERE did=? AND rid=?";
            p=Main.conn.prepareStatement(sql);
            p.setInt(1,did);
            p.setInt(2, rid);
            p.executeUpdate();
            return "success";
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }
    public static String adminReply(int did,int rid,String text){
        Permission per=Main.loginUserPermission();
        if(!per.getAddDiscuss())return "error";
        if(text.equals("")) text=null;
        try {
            PreparedStatement p;
            String sql="UPDATE t_discussreply SET adminreplay=? WHERE did=? AND rid=?";
            p=Main.conn.prepareStatement(sql);
            p.setString(1,text);
            p.setInt(2,did);
            p.setInt(3,rid);
            p.executeUpdate();
            MessageSQL.AddMessageDiscussReplyAdmin(did, rid, text);
            return "success";
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
