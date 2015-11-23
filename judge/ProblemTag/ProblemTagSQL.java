package ProblemTag;

import Main.Main;
import Tool.SQL.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syiml on 2015/7/24 0024.
 */
public class ProblemTagSQL {
    public static List<ProblemTag> problemTag;
    static{
        readTag();
    }
    public static ProblemTag get(int tagid){
        return problemTag.get(tagid);
    }
    public static void readTag(){
        problemTag=new ArrayList<ProblemTag>();
        SQL sql=new SQL("SELECT * FROM t_problem_tag ORDER BY id");
        ResultSet rs= sql.query();
        try {
            while(rs.next()){
                problemTag.add(new ProblemTag(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql.close();
    }
    public static List<ProblemTagRecord> getProblemTags(int pid){
        return getProblemTags(pid,null);
    }
    public static List<ProblemTagRecord> getProblemTags(int pid,String user){
        List<ProblemTagRecord> list=new ArrayList<ProblemTagRecord>();
        SQL sql;
        if(user==null){
            sql=new SQL("SELECT * FROM t_problem_tag_record WHERE pid=?",pid);
        }else{
            sql=new SQL("SELECT * FROM t_problem_tag_record WHERE pid=? AND username=?",pid,user);
        }
        ResultSet rs=sql.query();
        try {
            while(rs.next()){
                list.add(new ProblemTagRecord(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql.close();
        return list;
    }
    public static List<ProblemTagRecord> getProblemTags(int pid,int from,int num){
        SQL sql=new SQL("SELECT * FROM t_problem_tag_record WHERE pid=? order by rating desc,username  limit ?,?",pid,from,num);
        List<ProblemTagRecord> list=new ArrayList<ProblemTagRecord>();
        ResultSet rs=sql.query();
        try {
            while(rs.next()){
                list.add(new ProblemTagRecord(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql.close();
        return list;
    }
    public static int getTagNum(String user){//给多少题目贴过标签
        return new SQL("select count(pid) from (select username,pid from t_problem_tag_record group by username,pid)t WHERE username=? group by username ", user).queryNum();
    }
    public static void addTag(int pid,String username,int tagid){
        int rating= Main.users.getUser(username).getShowRating();
        if(rating==-100000) rating=700;
        new SQL("INSERT INTO t_problem_tag_record VALUES(?,?,?,?)",pid,username,tagid,rating).update();
        if(Main.users.addViewCode(username,pid)==1){
            Main.users.addACB(username,20);
        }
    }
    public static void delTag(int pid,String username,int tagid){
        new SQL("DELETE FROM t_problem_tag_record WHERE pid=? AND username=? AND tagid=?"
                                                            ,pid        ,username       ,tagid).update();
    }
    public static void addTag(String name){
        new SQL("INSERT INTO t_problem_tag(name) VALUES(?)",name).update();
    }
    public static void renameTag(int id,String name){
        new SQL("UPDATE t_problem_tag SET name=? WHERE id=?",name,id).update();
    }
    public static String userTag(String user){
        SQL sql1=new SQL("(select username,ttype,count(*) as num from (SELECT username,pid FROM usersolve_view where username=? and solved=1) a join (SELECT pid,(SELECT ttype FROM t_problem_tag WHERE id=tagid) as ttype,sum(rating-500) as ss  FROM t_problem_tag_record group by ttype,pid) b on a.pid=b.pid where b.ss>? group by ttype)",user,0);
        ResultSet rs=sql1.query();
        SQL sql2=new SQL("(select ttype,count(*) as num from (SELECT username,pid FROM usersolve_view where  solved=1) a join (SELECT pid,(SELECT ttype FROM t_problem_tag WHERE id=tagid) as ttype,sum(rating-500) as ss  FROM t_problem_tag_record group by ttype,pid) b on a.pid=b.pid where b.ss>? group by ttype)",0);
        ResultSet rs2=sql2.query();
        int[] a=new int[7];
        int[] b=new int[7];
        try {
            while(rs.next()){
                int x=rs.getInt(2);
                if(x>=0&&x<=6)
                    a[x]=rs.getInt(3);
            }
            while(rs2.next()){
                int x=rs2.getInt(1);
                if(x>=0&&x<=6)
                    b[x]=rs2.getInt(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql1.close();
        sql2.close();
        String ret="[";
        for(int i=0;i<=6;i++){
            if(i!=0) ret+=",";
            ret+=(b[i]!=0?a[i]*100+"/"+b[i]:"0");
        }
        //System.out.println(ret+"]");
        return ret+ "]";
    }
}
