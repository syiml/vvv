package Main.contest;

import Main.Main;
import Main.User.User;
import Main.contest.Contest;
import Main.contest.RegisterUser;
import Message.MessageSQL;
import Tool.HTML.HTML;
import Tool.SQL;
import action.addcontest;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/5/24.
 */
public class ContestSQL {
    /*
    * contest(id,name,beginTime,endTime,rankType,ctype,password,registerendtime)
    * contestproblems(cid,pid,tpid)
    * contestuser(cid,username,statu,info)
    * */
    private static int maxmapnum=5;
    private Map<Integer,Contest> cSQL;
    public ContestSQL(){
        cSQL=new HashMap<Integer, Contest>();
        //cSQL.put(0, new Contest());
    }
    public String deleteproblems(int cid){
        try {
            PreparedStatement p=null;
            p=Main.conn.prepareStatement("delete from contestproblems where cid=?");
            p.setInt(1,cid);
            p.executeUpdate();
            return "success";
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }catch (NumberFormatException e){
            e.printStackTrace();
            return "error";
        }
    }
    public String addProblems(int cid,String s){
        if(s.equals("")) return "success";
        //参数：s：以半角逗号分隔开的pid列表
        String[] pids=s.split(",");
        try {
            PreparedStatement p=null;
            for (int i=0;i<pids.length;i++) {
                int tpid=Integer.parseInt(pids[i]);
                p=Main.conn.prepareStatement("INSERT INTO contestproblems values(?,?,?)");
                p.setInt(1,cid);
                p.setInt(2,i);
                p.setInt(3,tpid);
                p.executeUpdate();
            }
            return "success";
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }catch (NumberFormatException e){
            e.printStackTrace();
            return "error";
        }
    }
    public String addContest(int id,addcontest a){
        PreparedStatement p= null;
        int ret=0;
        try {
            p = Main.conn.prepareStatement("INSERT INTO contest values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
            p.setInt(1,id);
            p.setString(2, a.getName());
            p.setTimestamp(3, Main.getTimestamp(a.getBegintime_d(), a.getBegintime_s(), a.getBegintime_m()));
            p.setTimestamp(4, Main.getTimestamp(a.getEndtime_d(), a.getEndtime_s(), a.getEndtime_m()));
            p.setInt(5,Integer.parseInt(a.getRank()));
            int type=Integer.parseInt(a.getType());
            p.setInt(6,type);
            if(type==1){
                p.setString(7,a.getPass());
            }else p.setString(7,"");
            if(type==3||type==4){
                p.setTimestamp(8, Main.getTimestamp(a.getRegisterstarttime_d(), a.getRegisterstarttime_s(), a.getRegisterstarttime_m()));
                p.setTimestamp(9, Main.getTimestamp(a.getRegisterendtime_d(), a.getRegisterendtime_s(), a.getRegisterendtime_m()));
            }else{
                p.setTimestamp(8, Main.now());
                p.setTimestamp(9, Main.getTimestamp(a.getEndtime_d(), a.getEndtime_s(), a.getEndtime_m()));
            }
            p.setString(10,a.getInfo());
            p.setBoolean(11,a.getComputerating()!=null);
            String user=((User)Main.getSession().getAttribute("user")).getUsername();
            p.setString(12,user);
            p.setInt(13,Integer.parseInt(a.getKind()));
            p.executeUpdate();
            if(addProblems(id,a.getProblems()).equals("error")) return "error";
            return "success";
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        } catch (NumberFormatException e){
            e.printStackTrace();
            return "error";
        }
        //return ret;
    }
    public String editContest(int id,addcontest a){
        PreparedStatement p= null;
        int ret=0;
        try {
            p = Main.conn.prepareStatement("update contest set name=?,begintime=?,endtime=?," +
                    "rankType=?,ctype=?,password=?,registerstarttime=?,registerendtime=?,info=?,computerating=?," +
                    "kind=? where id=?");
            p.setString(1, a.getName());
            p.setTimestamp(2, Main.getTimestamp(a.getBegintime_d(), a.getBegintime_s(), a.getBegintime_m()));
            p.setTimestamp(3, Main.getTimestamp(a.getEndtime_d(), a.getEndtime_s(), a.getEndtime_m()));
            p.setInt(4,Integer.parseInt(a.getRank()));
            int type=Integer.parseInt(a.getType());
            p.setInt(5,type);
            if(type==1){
                p.setString(6,a.getPass());
            }else p.setString(6,"");
            if(type==3||type==4){
                p.setTimestamp(7, Main.getTimestamp(a.getRegisterstarttime_d(), a.getRegisterstarttime_s(), a.getRegisterstarttime_m()));
                p.setTimestamp(8, Main.getTimestamp(a.getRegisterendtime_d(), a.getRegisterendtime_s(), a.getRegisterendtime_m()));
            }else{
                p.setTimestamp(7, Main.now());
                p.setTimestamp(8, Main.getTimestamp(a.getEndtime_d(), a.getEndtime_s(), a.getEndtime_m()));
            }
            p.setString(9,a.getInfo());
            p.setBoolean(10, a.getComputerating() != null);
            p.setInt(11, Integer.parseInt(a.getKind()));
            p.setInt(12,id);
            p.executeUpdate();
            deleteproblems(id);
            if(addProblems(id,a.getProblems()).equals("error")) return "error";
            return "success";
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        } catch (NumberFormatException e){
            e.printStackTrace();
            return "error";
        }
        //return ret;
    }
    public RegisterUser getRegisterStatu(String username,int cid){
        //null 未注册
        PreparedStatement p= null;
        int ret=0;
        try {
            ResultSet rs= SQL.query("SELECT * FROM contestuser WHERE username=? AND cid=?",username,cid);
            if(rs.next()){
                return new RegisterUser(rs);
            }else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String addUserContest(int cid,String username,int statu){
        PreparedStatement p= null;
        int ret=0;
        try {
            p = Main.conn.prepareStatement("INSERT INTO contestuser VALUES(?,?,?,?,?)");
            p.setInt(1, cid);
            p.setString(2, username);
            p.setInt(3, statu);
            p.setString(4, "");
            p.setTimestamp(5,Main.now());
            p.executeUpdate();
            p.close();
            Main.contests.getContest(cid).reSetUsers();
            return "success";
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
//      System.out.println(ret);
    }
    public String setUserContest(int cid,String username,int statu,String info){
        if(getRegisterStatu(username,cid)==null){
            addUserContest(cid,username,statu);
        }
        if(statu==-2){
            Main.contests.getContest(cid).reSetUsers();
            return delUserContest(cid,username);
        }
        if(statu!=3){
            MessageSQL.addMessageRegisterContest(username,cid,statu);
            SQL.update("UPDATE contestuser set statu=? WHERE cid=? AND username=?",statu,cid,username);
        }else{
            MessageSQL.addMessageRegisterContest(username,cid,statu);
            SQL.update("UPDATE contestuser set statu=?,info=? WHERE cid=? AND username=?",statu,info,cid,username);
        }
        Main.contests.getContest(cid).reSetUsers();
        return "success";
    }
    public String delUserContest(int cid,String username){
        PreparedStatement p= null;
        int ret=0;
        try {
            p = Main.conn.prepareStatement("DELETE FROM contestuser  WHERE cid=? AND username=?");
            p.setInt(1,cid);
            p.setString(2, username);
            p.executeUpdate();
            Main.contests.getContest(cid).reSetUsers();
            return "success";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "error";
    }
    public ResultSet getUser(int cid) throws SQLException {
        PreparedStatement p4 = Main.conn.prepareStatement("select * from contestuser where cid=?");
        p4.setInt(1,cid);
        return p4.executeQuery();
    }
    public Contest getContest(int cid) {
        //cSQL作为缓存，如果里面有这个contest的话就直接获取，否则从数据库获取
        if(cSQL.size()>=maxmapnum) cSQL.clear();
        if(cSQL.containsKey(cid)){
            //System.out.println("get("+cid+") in cSQL");
            return cSQL.get(cid);
        }
        //System.out.println("->in Contest "+cid);
        PreparedStatement p1= null,p2=null,p3=null;
        ResultSet c=null,pros=null,rs=null,ru=null;
        try {
            p1 = Main.conn.prepareStatement("select * from contest where id=?");
            p1.setInt(1, cid);
            c=p1.executeQuery();
            p2 = Main.conn.prepareStatement("select pid,tpid from contestproblems where cid=? order by pid");
            p2.setInt(1, cid);
            pros=p2.executeQuery();
            p3 = Main.conn.prepareStatement("select id,ruser,pid,cid,lang,submittime,result,timeused,memoryUsed,codelen from statu where cid=?");
            p3.setInt(1,cid);
            rs=p3.executeQuery();
            ru=getUser(cid);
            Contest con = new Contest(c,pros,rs,ru);
            cSQL.put(cid,con);
            c.close();pros.close();rs.close();ru.close();p1.close();p2.close();p3.close();
            return con;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //id,name,beignTime,endTime,rankType
//      return cSQL.get(cid);
        return null;
    }
    public void deleteMapContest(int cid){
        cSQL.remove(cid);
    }
    public int getNewId(){
        PreparedStatement p= null;
        int ret=0;
        try {
            p = Main.conn.prepareStatement("select MAX(id)+1 from contest");
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                ret=rs.getInt(1);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println(ret);
        return ret;
    }
    public int size(){
        return cSQL.size();
    }
    public List<Contest> getContests(int from,int num,int statu,String name,int type,int kind){
        //statu: pending/running/end   0/1/2
        List<Contest> list=new ArrayList<Contest>();
        PreparedStatement p1=null;
        try {
            String sql="";
            sql+="select *";
            sql+=" from contest where 1";
            if(type!=-1){
                sql+=" and ctype="+type;
            }
            if(statu==0){//pending
                sql+=" and NOW()<beginTime";
            }else if(statu==1){//RUNNING
                sql+=" and NOW()>=beginTime and NOW()<endTime";
            }else if(statu==2){//end
                sql+=" and NOW()>=endTime";
            }
            if(name!=null&&!name.equals("")){
                sql+=" and name like '%"+name+"%'";
            }
            if(kind!=-1){
                sql+=" and kind="+kind;
            }
            sql+=" order by endTime<now(),begintime desc";
            sql+=" limit ?,?";
            p1 = Main.conn.prepareStatement(sql);
            p1.setInt(1,from);
            p1.setInt(2,num);
            //System.out.println(p1.toString());
            ResultSet c=p1.executeQuery();
            while(c.next()){
                list.add(new Contest(c));
            }
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<Contest> getRecentlyContests(int num){
        List<Contest> list=new ArrayList<Contest>();
        PreparedStatement p1=null;
        try {
            String sql="";
            sql+="select *";
            sql+=" from contest where endTime>=now()";
            sql+=" order by beginTime desc";
            sql+=" limit 0,?";
            p1 = Main.conn.prepareStatement(sql);
            p1.setInt(1,num);
            //System.out.println(p1.toString());
            ResultSet c=p1.executeQuery();
            while(c.next()){
                list.add(new Contest(c));
            }
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<Integer> getAcRidFromCid(int cid){
        List<Integer> list=new ArrayList<Integer>();
        ResultSet rs=SQL.query("SELECT id FROM statu WHERE cid=? AND result=1", cid);
        try {
            while(rs.next()){
                list.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public String toHTML(int cid,Contest c){
        return   "<tr><td>"+cid+ "</td>"
                +"<td>"+HTML.a("Contest.jsp?cid="+cid,c.getName())+"</td>"
                +"<td>"+c.getBeginTimeString()+"</td>"
                +"<td>"+c.getEndTimeString()+"</td>"
                +"<td>"+c.getTypeHTML()+"</td>"
                +"<td>"+c.getStatuHTML()+"</td>"
                +"</tr>";
    }
    public String toHTML(int cid){
        return toHTML(cid,cSQL.get(cid));
    }

    /**
     * 题目所属的第一场比赛（即题目的出处） 已经开始才显示
     * @param pid 题目ID
     * @return 返回题目连接和名称的超链接
     */
    public String problemContest(int pid){
        ResultSet rs=SQL.query("SELECT * FROM `contestproblems` left join contest on cid=id WHERE tpid=? order by begintime limit 0,1",pid);
        try {
            if(rs.next()){
                Timestamp begintime=rs.getTimestamp("begintime");
                if(begintime.after(Main.now())){
                    return "无";
                }else{
                    return HTML.a("Contest.jsp?cid="+rs.getInt("id"),rs.getString("name"));
                }
            }else{
                return "无";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "无";
    }
}
