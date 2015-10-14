package ProblemTag;

import Main.Main;
import Tool.SQL;

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
        ResultSet rs= SQL.query("SELECT * FROM t_problem_tag ORDER BY id");
        try {
            while(rs.next()){
                problemTag.add(new ProblemTag(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<ProblemTagRecord> getProblemTags(int pid){
        List<ProblemTagRecord> list=new ArrayList<ProblemTagRecord>();
        ResultSet rs=SQL.query("SELECT * FROM t_problem_tag_record WHERE pid=?",pid);
        try {
            while(rs.next()){
                list.add(new ProblemTagRecord(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static List<ProblemTagRecord> getProblemTags(int pid,String user){
        List<ProblemTagRecord> list=new ArrayList<ProblemTagRecord>();
        ResultSet rs=SQL.query("SELECT * FROM t_problem_tag_record WHERE pid=? AND username=?",pid,user);
        try {
            while(rs.next()){
                list.add(new ProblemTagRecord(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static List<ProblemTagRecord> getProblemTags(int pid,int from,int num){
        List<ProblemTagRecord> list=new ArrayList<ProblemTagRecord>();
        ResultSet rs=SQL.query("SELECT * FROM t_problem_tag_record WHERE pid=? order by rating desc,username  limit ?,?",pid,from,num);
        try {
            while(rs.next()){
                list.add(new ProblemTagRecord(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static int getTagNum(String user){//给多少题目贴过标签
        ResultSet rs=SQL.query("select count(pid) from (select username,pid from t_problem_tag_record group by username,pid)t WHERE username=? group by username ", user);
        try {
            if(rs.next()){
                return rs.getInt(1);
            }else{
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static void addTag(int pid,String username,int tagid){
        int rating= Main.users.getUser(username).getShowRating();
        if(rating==-100000) rating=700;
        SQL.update("INSERT INTO t_problem_tag_record VALUES(?,?,?,?)",pid,username,tagid,rating);
        if(Main.users.addViewCode(username,pid)==1){
            Main.users.addACB(username,20);
        }
    }
    public static void delTag(int pid,String username,int tagid){
        SQL.update("DELETE FROM t_problem_tag_record WHERE pid=? AND username=? AND tagid=?"
                                                            ,pid        ,username       ,tagid);
    }
    public static void addTag(String name){
        SQL.update("INSERT INTO t_problem_tag(name) VALUES(?)",name);
    }
    public static void renameTag(int id,String name){
        SQL.update("UPDATE t_problem_tag SET name=? WHERE id=?",name,id);
    }
    public static String userTag(String user){

        ResultSet rs=SQL.query("(select username,ttype,count(*) as num from (SELECT username,pid FROM usersolve_view where username=? and solved=1) a join (SELECT pid,(SELECT ttype FROM t_problem_tag WHERE id=tagid) as ttype,sum(rating-500) as ss  FROM t_problem_tag_record group by ttype,pid) b on a.pid=b.pid where b.ss>? group by ttype)",user,0);
        ResultSet rs2=SQL.query("(select ttype,count(*) as num from (SELECT username,pid FROM usersolve_view where  solved=1) a join (SELECT pid,(SELECT ttype FROM t_problem_tag WHERE id=tagid) as ttype,sum(rating-500) as ss  FROM t_problem_tag_record group by ttype,pid) b on a.pid=b.pid where b.ss>? group by ttype)",0);
//        ResultSet rs=SQL.query("SELECT  username,ttype,count(*),sum(rating)" +
//                " FROM  `t_problem_tag_record` LEFT JOIN `t_problem_tag` ON tagid=id  " +
//                " WHERE username=?" +
//                " GROUP BY ttype,username",user);
//        ResultSet rs2=SQL.query("SELECT ttype,count(*),sum(rating)" +
//                " FROM  `t_problem_tag_record` left join `t_problem_tag` on tagid=id  " +
//                " group by ttype");

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
        String ret="[";
        for(int i=0;i<=6;i++){
            if(i!=0) ret+=",";
            ret+=(b[i]!=0?a[i]*100+"/"+b[i]:"0");
        }
        //System.out.println(ret+"]");
        return ret+ "]";
    }
}
