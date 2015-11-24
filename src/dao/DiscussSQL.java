package dao;

import entity.Discuss;
import entity.Permission;
import entity.User;
import util.Main;
import entity.DiscussReply;
import servise.MessageMain;
import util.HTML.HTML;
import util.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syiml on 2015/7/3 0003.
 */
public class DiscussSQL {
    public static Discuss getDiscuss(int id){
        SQL sql=new SQL("SELECT *,(select count(*) from t_discussreply where did=t_discuss.id )as replynum FROM t_discuss WHERE id=?",id);
        try {
            ResultSet rs=sql.query();
            if(rs.next()){
                return new Discuss(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            sql.close();
        }
        return null;
    }
    public static List<Discuss> getDiscussTOP(boolean all){
        List<Discuss> list=new ArrayList<Discuss>();
        String sql="SELECT *,(select count(*) from t_discussreply where did=t_discuss.id )as replynum FROM t_discuss WHERE top=1";
        if(!all){
            sql+=" AND visiable=1";
        }
        sql+=" ORDER BY priority DESC";
        SQL sql1=new SQL(sql);
        try {
            ResultSet rs=sql1.query();
            while(rs.next()){
                 list.add(new Discuss(rs));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            sql1.close();
        }
        return null;
    }
    public static List<Discuss> getDiscussList(int cid,int from,int num,boolean all,String seach,String user){//admin is all
        List<Discuss> list=new ArrayList<Discuss>();
        String sql="SELECT *,(select count(*) from t_discussreply where did=t_discuss.id )as replynum FROM t_discuss WHERE cid="+cid;
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
        SQL sql1=new SQL(sql);
        try {
            ResultSet rs=sql1.query();
            while(rs.next()){
                list.add(new Discuss(rs));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            sql1.close();
        }
        return null;
    }
    public static int getDiscussListNum(int cid,boolean all,String seach,String user){//admin is all
        String sql="SELECT count(*) FROM t_discuss WHERE cid="+cid;
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
        SQL sql1=new SQL(sql);
        try {
            ResultSet rs=sql1.query();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sql1.close();
        }
        return 0;
    }
    public static int newid(){
        return new SQL("SELECT max(id)+1 FROM t_discuss").queryNum();
    }
    public static int addDiscuss(Discuss d){
        int id=newid();
        new SQL("INSERT INTO t_discuss values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
                ,id
                , d.getCid()
                ,d.isadmin()? d.getTitle() :HTML.HTMLtoString(d.getTitle())
                , d.getPanelclass()
                , d.getUsername()
                , d.getTime()
                ,d.isadmin()? d.getText() :HTML.pre(HTML.HTMLtoString(d.getText()))
                , d.getPriority() ==-1?id: d.getPriority()
                , d.isTop()
                , d.isVisiable()
                , d.isReply()
                , d.getShownum()
                , d.isPanelnobody()
                , d.isShowauthor()
                , d.isShowtime()
                , d.isReplyHidden()).update();
        return id;
    }
    public static void append(Discuss d){
        String text= getDiscuss(d.getId()).getText();
        text+="<hr/>以下内容于["+HTML.textb(Main.now().toString().substring(0,19),"blue")+"]补充";
        text+=HTML.pre(HTML.HTMLtoString(d.getText()));
        String sql="UPDATE t_discuss SET";
        sql+=" text=?";
        sql+="WHERE id=?";
        new SQL(sql,text, d.getId()).update();
    }
    public static void editDiscuss(Discuss d){
            String sql="UPDATE t_discuss SET "+
                        " title=?"+
                        ",panelclass=?"+
                        ",text=?"+
                        ",priority=?"+
                        ",top=?"+
                        ",visiable=?"+
                        ",reply=?"+
                        ",shownum=?"+
                        ",panelnobody=?"+
                        ",showauthor=?"+
                        ",showtime=?"+
                        ",replyhidden=?"+
                        " WHERE id=?";
            new SQL(sql
                    ,d.isadmin()? d.getTitle() :HTML.HTMLtoString(d.getTitle())
                    , d.getPanelclass()
                    ,d.isadmin()? d.getText() :HTML.pre(HTML.HTMLtoString(d.getText()))
                    , d.getPriority() ==-1? d.getId() : d.getPriority()
                    , d.isTop()
                    , d.isVisiable()
                    , d.isReply()
                    , d.getShownum()
                    , d.isPanelnobody()
                    , d.isShowauthor()
                    , d.isShowtime()
                    , d.isReplyHidden()
                    , d.getId()).update();
    }
    ////////////////discuss replay///////////////////
    public static List<DiscussReply> getDiscussReplay(int did, int from, int to){
        List<DiscussReply> list=new ArrayList<DiscussReply>();
        String sql="SELECT * FROM t_discussreply WHERE did=?";
        sql+=" AND rid>="+from+" AND rid<="+to;
        SQL sql1=new SQL(sql,did);
        try {
            ResultSet rs=sql1.query();
            while(rs.next()){
                list.add(new DiscussReply(rs));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            sql1.close();
        }
        return list;
    }
    public static DiscussReply getDiscussReply(int did,int rid){
        SQL sql=new SQL("SELECT * FROM t_discussreply WHERE did=? AND rid=?", did, rid);
        ResultSet rs=sql.query();
        try {
            if(rs.next()){
                sql.close();
                return new DiscussReply(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql.close();
        return null;
    }
    public static int getNewReplyId(int did){
        return new SQL("SELECT MAX(rid) FROM t_discussreply WHERE did=?",did).queryNum()+1;
    }
    public static String reply(User loginuser,int did,String text){
        if(loginuser==null) return "error";
        Discuss d=getDiscuss(did);
        if(d==null) return "error";
        int newid=getNewReplyId(did);
        new SQL("INSERT INTO t_discussreply VALUES(?,?,?,?,?,?,?,null)",newid,did,loginuser.getUsername(),Main.now(),HTML.HTMLtoString(text),!d.isReplyHidden(), d.getPanelclass()).update();
        MessageMain.addMessageDisscussReply(d.getCid(),loginuser.getUsername(),did,HTML.HTMLtoString(text));
        return "success";
    }
    public static String hideshow(int did,int rid){
        Permission per=Main.loginUserPermission();
        if(!per.getAddDiscuss())return "error";
        new SQL("UPDATE t_discussreply SET visiable=not visiable WHERE did=? AND rid=?",did,rid).update();
        return "success";
    }
    public static String adminReply(int did,int rid,String text){
        Permission per=Main.loginUserPermission();
        if(!per.getAddDiscuss())return "error";
        if(text.equals("")) text=null;
        String sql="UPDATE t_discussreply SET adminreplay=? WHERE did=? AND rid=?";
        new SQL(sql,text,did,rid).update();
        MessageMain.addMessageDiscussReplyAdmin(did, rid, text);
        return "success";
    }
}
