package Main.status;

import Main.Main;
import Main.status.Result;
import Main.status.statu;
import Tool.HTML.HTML;
import Tool.HTML.statuListHTML.statuListHTML;
import Tool.SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Administrator on 2015/5/23.
 */
public class statusSQL {
    /*
    * statu(id,ruser,pid,cid,lang,submitTime,result,timeUsed,memoryUsed,code)
    * ceinfo(rid,info)
    * */
    private int maxRID;
    public void init(){
        maxRID=getNewRid();
        //status = new ArrayList<statu>();
    }
    private int getNewRid(){
        PreparedStatement p= null;
        int ret=0;
        try {
            p = Main.conn.prepareStatement("select MAX(id) from statu");
            ResultSet r=p.executeQuery();
            r.next();
            ret = r.getInt(1);
            r.close();
            p.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
    public int addStatu(statu s){
        int rid=maxRID+1;
        maxRID++;
        PreparedStatement p= null;
        try {
            p = Main.conn.prepareStatement("insert into statu values(?,?,?,?,?,?,?,?,?,?,?)");
            p.setInt(1,rid);
            p.setString(2, s.getUser());
            p.setInt(3,s.getPid());
            p.setInt(4, s.getCid());
            p.setInt(5, s.getLanguage());
            p.setTimestamp(6, s.getSbmitTime());
            p.setInt(7, statu.resultToInt(s.getResult()));
            p.setString(8, s.getTimeUsed());
            p.setString(9, s.getMemoryUsed());
            p.setString(10, s.getCode());
            p.setInt(11,s.getCodelen());
            p.executeUpdate();
            p.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rid;
    }
    public statu getStatu(int rid){
        PreparedStatement p= null;
        statu s=null;
        try {
            p = Main.conn.prepareStatement("select id,ruser,pid,cid,lang,submittime,result,timeUsed,memoryUsed,code,codelen from statu where id=?");
            p.setInt(1,rid);
            ResultSet r=p.executeQuery();
            r.next();
            s = new statu(r,10);
            r.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }
    public List<statu> getStatus(int cid,int from,int num,
                     /*筛选信息：*/int pid,int result,int Language,String ssuser,boolean all){
        List<statu> l=new ArrayList<statu>();
        PreparedStatement p= null;
        try {
            String sql="select id,ruser,pid,cid,lang,submitTime,result,timeUsed,memoryUsed,codelen from statu where ";
            if(!all) sql+=" cid=?";
            else sql+=" 1";
            //筛选
            if(pid!=-1){
                if(cid!=-1){
                    sql+=" and pid="+Main.contests.getContest(cid).getGlobalPid(pid);
                }else{
                    sql+=" and pid="+pid;
                }
            }
            if(result!=-1) sql+=" and result="+result;
            if(Language!=-1) sql+=" and lang="+Language;
            if(ssuser!=null&&!ssuser.equals("")) sql+=" and ruser='"+ssuser+"'";

            sql+=" order by id desc";
            sql+=" limit "+from+","+num;
            p = Main.conn.prepareStatement(sql);
            if(!all) p.setInt(1,cid);
            ResultSet r=p.executeQuery();
            while(r.next()){
                statu st= new statu(r,9);
                l.add(st);
            }
            r.close();
            p.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return l;
    }
    public int getStatusNum(int cid,
                     /*筛选信息：*/int pid,int result,int Language,String ssuser,boolean all){
        List<statu> l=new ArrayList<statu>();
        int ret=1;
        PreparedStatement p= null;
        try {
            String sql="select count(*) from statu where ";
            if(!all) sql+=" cid=?";
            else sql+=" 1";
            //筛选
            if(pid!=-1){
                if(cid!=-1){
                    sql+=" and pid="+Main.contests.getContest(cid).getGlobalPid(pid);
                }else{
                    sql+=" and pid="+pid;
                }
            }
            if(result!=-1) sql+=" and result="+result;
            if(Language!=-1) sql+=" and lang="+Language;
            if(ssuser!=null&&!ssuser.equals("")) sql+=" and ruser='"+ssuser+"'";
            p = Main.conn.prepareStatement(sql);
            if(!all) p.setInt(1,cid);
            ResultSet r=p.executeQuery();
            if(r.next()){
                ret=r.getInt(1);
            }
            r.close();
            p.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
    public void setStatusResult (int rid,Result res,String time,String Meory,String CEinfo){
        //System.out.print("Change rid="+rid+" result:");
        //System.out.print("    ->res=  "+res);
        //System.out.print("    ->time= "+time);
        //System.out.println("    ->Meory="+Meory);
        PreparedStatement p= null;
        try {
            p = Main.conn.prepareStatement("update statu set result=?,timeUsed=?,memoryUsed=? where id=?");
            p.setInt(4,rid);
            p.setInt(1, statu.resultToInt(res));
            p.setString(2,time);
            p.setString(3,Meory);
            p.executeUpdate();
            Main.log(rid+"->"+res);
            statu s=getStatu(rid);
            if(s.getCid()!=-1&&s.getResult()!=Result.JUDGING){
                Main.contests.getContest(s.getCid()).getRank().add(s,Main.contests.getContest(s.getCid()));
            }
            if(res==Result.CE){
                addCEInof(rid, CEinfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Set<Integer> getAcProblems(String user){//获取user已经AC题目列表（包括contest内的）
        Set<Integer> ret=new TreeSet<Integer>();
        if(user==null) return null;
        PreparedStatement p;
        try{
            p=Main.conn.prepareStatement("SELECT pid FROM usersolve_view WHERE solved=1 AND username=?");
            p.setString(1, user);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                ret.add(rs.getInt(1));
            }
            rs.close();
            return ret;
        }catch(SQLException e){
            return ret;
        }
    }
    public Set<Integer> getNotAcProblems(String user){//获取user提交过但没有AC的题目列表（不包括contest内的）
        Set<Integer> ret=new TreeSet<Integer>();
        if(user==null) return null;
        PreparedStatement p;
        try{
            p=Main.conn.prepareStatement("SELECT pid FROM usersolve_view WHERE solved=0 AND username=? ");
            p.setString(1, user);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                ret.add(rs.getInt(1));
            }
            rs.close();
            return ret;
        }catch(SQLException e){
            return ret;
        }
    }
    public int getAcNum(String user){
        if(user==null) return 0;
        PreparedStatement p;
        try{
            p=Main.conn.prepareStatement("SELECT Count(pid) FROM usersolve_view WHERE solved=1 AND username=?");
            p.setString(1, user);
            ResultSet rs=p.executeQuery();
            if(rs.next()){
                return (rs.getInt(1));
            }
            return 0;
        }catch(SQLException e){
            return 0;
        }
    }
    public int getSubmitTime(String username){//获取user的提交次数
        PreparedStatement p;
        try{
            p=Main.conn.prepareStatement("select COUNT(id) from statu where ruser=?");
            p.setString(1, username);
            ResultSet rs=p.executeQuery();
            rs.next();
            return rs.getInt(1);
        }catch(SQLException e){
            return 0;
        }
    }
    public Map<Integer,Integer> getProblemACUserNum(int pidl,int pidr){
        Map<Integer,Integer> ret=new HashMap<Integer, Integer>();
        ResultSet rs=SQL.query("SELECT pid,COUNT(username) FROM usersolve_view WHERE pid>=? AND pid<=? AND solved=1 group by pid",pidl,pidr);
        try {
            while(rs.next()){
                ret.put(rs.getInt(1),rs.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
    public Map<Integer,Integer> getProblemSubmitUserNum(int pidl,int pidr){
        Map<Integer,Integer> ret=new HashMap<Integer, Integer>();
        ResultSet rs=SQL.query("SELECT pid,COUNT(username) FROM usersolve_view WHERE pid>=? AND pid<=? group by pid",pidl,pidr);
        try {
            while(rs.next()){
                ret.put(rs.getInt(1),rs.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
    public Map<Integer,Integer> getProblemSubmitNum(int pidl,int pidr){
        Map<Integer,Integer> ret=new HashMap<Integer, Integer>();
        ResultSet rs=SQL.query("SELECT pid,COUNT(id) FROM statu WHERE pid>=? AND pid<=? group by pid",pidl,pidr);
        try {
            while(rs.next()){
                ret.put(rs.getInt(1),rs.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
    public int getProblemAcUserNum(int pid){
        PreparedStatement p;
        try{
            p=Main.conn.prepareStatement("SELECT COUNT(username) FROM usersolve_view WHERE pid=? AND solved=1");
            p.setInt(1, pid);
            ResultSet rs=p.executeQuery();
            rs.next();
            return rs.getInt(1);
        }catch(SQLException e){
            return 0;
        }
    }
    public int getProblemSubmitUserNum(int pid){
        PreparedStatement p;
        try{
            p=Main.conn.prepareStatement("SELECT COUNT(username) FROM usersolve_view WHERE pid=?");
            p.setInt(1, pid);
            ResultSet rs=p.executeQuery();
            rs.next();
            return rs.getInt(1);
        }catch(SQLException e){
            return 0;
        }
    }
    public int getProblemSubmitNum(int pid){
        ResultSet rs= SQL.query("SELECT COUNT(id) FROM statu WHERE pid=?",pid);
        try {
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return 0;
    }
    public ResultSet getSubmitCount(String user,int num,int sec){
        String sql="";
        sql+=" select floor(timestampdiff(second,submittime ,current_timestamp())/(?)) as T,count(*) as count from statu ";
        if(user!=null) sql+=" WHERE ruser='"+user+"' ";
        sql+=" group by T order by T limit 0,?";
        return SQL.query(sql,sec,num);
    }
    public ResultSet getAcCount(String user,int num,int sec){
        String sql="";
        sql+=" select floor(timestampdiff(second,submittime ,current_timestamp())/(?)) as T,count(*) as count from statu where result=1 ";
        if(user!=null) sql+=" and ruser='"+user+"' ";
        sql+=" group by T order by T limit 0,?";
        return SQL.query(sql,sec,num);
    }
    public Map<Integer,Integer> getProblemStatus(int pid){
        ResultSet rs=SQL.query("SELECT result,count(id) FROM statu WHERE pid=? GROUP BY result",pid);
        //List<Integer> list=new ArrayList<Integer>(20);
        Map<Integer,Integer> map = new HashMap<Integer, Integer>();
        try {
            while(rs.next()){
                map.put(rs.getInt(1),rs.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }
    public List<String[]> getProblemShortCodeTop10(int pid){
        ResultSet rs=SQL.query("SELECT ruser,min(codelen) as minlen FROM statu WHERE pid=? AND result=1 group by ruser order by minlen  limit 0,10", pid);
        List<String[]> list=new ArrayList<String[]>();
        try {
            while(rs.next()){
                String[] ret=new String[2];
                ret[0]=rs.getString(1);
                ret[1]=rs.getInt(2)+"";
                list.add(ret);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public void addCEInof(int rid,String info){
        PreparedStatement p=null;
        try {
            p = Main.conn.prepareStatement("delete from ceinfo where rid=?");
            p.setInt(1,rid);
            p.executeUpdate();
            p = Main.conn.prepareStatement("insert into ceinfo values(?,?)");
            p.setInt(1, rid);
            if(info.length()<=800){
                p.setString(2, info);
            }else{
                p.setString(2, info.substring(0,797)+"...");
            }
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String getCEInofHTML(int rid,boolean havepanle){
        PreparedStatement p = null;
        String ret;
        try {
            p = Main.conn.prepareStatement("select info from ceinfo where rid=?");
            p.setInt(1,rid);
            ResultSet r=p.executeQuery();
            r.next();
            ret = r.getString(1);
        } catch (SQLException e) {
            ret="none";
        }
        return havepanle?HTML.panel("Compilation Error Info", HTML.code(ret,false,-1),null,"warning"):HTML.code(ret,false,-1);
    }
    public Map<Integer,Integer> sbumitResult(String username,int pid1,int pid2){
        //1->AC
        //0->submit but no AC
        //-1->no submit
        Map<Integer,Integer> ret=new HashMap<Integer,Integer>();
        PreparedStatement p;
        try{
            p=Main.conn.prepareStatement("select pid,solved from usersolve_view where username=? AND pid>=? AND pid<=?");
            p.setString(1, username);
            p.setInt(2, pid1);
            p.setInt(3, pid2);
            ResultSet rs=p.executeQuery();
//            System.out.println(p.toString());
            while(rs.next()){
//                System.out.println(rs.getInt(1)+"~"+rs.getInt(2));
                ret.put(rs.getInt(1),rs.getInt(2));
            }
            return ret;
        }catch(SQLException e){
            e.printStackTrace();
            return ret;
        }
    }
    public int sbumitResult(int cid,int pid,String username){
        //1->AC
        //0->submit but no AC
        //-1->no submit
        PreparedStatement p;
        pid=Main.contests.getContest(cid).getGlobalPid(pid);
        try{
            p=Main.conn.prepareStatement("select MAX(result=1)+1 from statu where ruser=? AND cid=? AND pid=?");
            p.setString(1, username);
            p.setInt(2, cid);
            p.setInt(3, pid);
            ResultSet rs=p.executeQuery();
            rs.next();
            return rs.getInt(1)-1;
        }catch(SQLException e){
            return -1;
        }
    }
    public int sbumitResult(int pid,String username){
        //1->AC
        //0->submit but no AC
        //-1->no submit
        PreparedStatement p;
        try{
            p=Main.conn.prepareStatement("select solved from usersolve_view where username=? AND pid=?");
            p.setString(1, username);
            p.setInt(2, pid);
            ResultSet rs=p.executeQuery();
            rs.next();
            return rs.getInt(1);
        }catch(SQLException e){
            return -1;
        }
    }
}
