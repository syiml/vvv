package dao;

import servise.ContestMain;
import util.Main;
import entity.User;
import entity.Contest;
import entity.RegisterUser;
import servise.MessageMain;
import util.HTML.HTML;
import util.SQL;
import action.addcontest;
import util.Tool;

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
        new SQL("delete from contestproblems where cid=?",cid).update();
        return "success";
    }
    public String addProblems(int cid,String s){
        if(s.equals("")) return "success";
        //参数：s：以半角逗号分隔开的pid列表
        String[] pids=s.split(",");
        PreparedStatement p=null;
        for (int i=0;i<pids.length;i++) {
            int tpid=Integer.parseInt(pids[i]);
            new SQL("INSERT INTO contestproblems values(?,?,?)",cid,i,tpid).update();
        }
        return "success";
    }
    public String addContest(int id,addcontest a){
        PreparedStatement p= null;
        int ret=0;
        int type=Integer.parseInt(a.getType());
        String user=((User)Main.getSession().getAttribute("user")).getUsername();
        new SQL("INSERT INTO contest values(?,?,?,?,?,?,?,?,?,?,?,?,?)"
                ,id
                ,a.getName()
                , Tool.getTimestamp(a.getBegintime_d(), a.getBegintime_s(), a.getBegintime_m())
                , Tool.getTimestamp(a.getEndtime_d(), a.getEndtime_s(), a.getEndtime_m())
                ,Integer.parseInt(a.getRank())
                ,type
                ,type==1?a.getPass():""
                ,type==3||type==4? Tool.getTimestamp(a.getRegisterstarttime_d(), a.getRegisterstarttime_s(), a.getRegisterstarttime_m()): Tool.now()
                ,type==3||type==4? Tool.getTimestamp(a.getRegisterendtime_d(), a.getRegisterendtime_s(), a.getRegisterendtime_m()): Tool.getTimestamp(a.getEndtime_d(), a.getEndtime_s(), a.getEndtime_m())
                ,a.getInfo()
                ,a.getComputerating()!=null
                ,user
                ,Integer.parseInt(a.getKind())).update();
        if(addProblems(id,a.getProblems()).equals("error")) return "error";
        return "success";
        //return ret;
    }
    public String editContest(int id,addcontest a){
        int type=Integer.parseInt(a.getType());
        new SQL("update contest set name=?,begintime=?,endtime=?," +
                "rankType=?,ctype=?,password=?,registerstarttime=?,registerendtime=?,info=?,computerating=?," +
                "kind=? where id=?"
                ,a.getName()
                , Tool.getTimestamp(a.getBegintime_d(), a.getBegintime_s(), a.getBegintime_m())
                , Tool.getTimestamp(a.getEndtime_d(), a.getEndtime_s(), a.getEndtime_m())
                ,Integer.parseInt(a.getRank())
                ,type
                ,type==1?a.getPass():""
                ,type==3||type==4? Tool.getTimestamp(a.getRegisterstarttime_d(), a.getRegisterstarttime_s(), a.getRegisterstarttime_m()):Tool.now()
                ,type==3||type==4? Tool.getTimestamp(a.getRegisterendtime_d(), a.getRegisterendtime_s(), a.getRegisterendtime_m()): Tool.getTimestamp(a.getEndtime_d(), a.getEndtime_s(), a.getEndtime_m())
                ,a.getInfo()
                ,a.getComputerating()!=null
                ,Integer.parseInt(a.getKind())
                ,id).update();
        PreparedStatement p= null;
        deleteproblems(id);
        if(addProblems(id,a.getProblems()).equals("error")) return "error";
        return "success";
    }
    public RegisterUser getRegisterStatu(String username, int cid){
        //null 未注册
        PreparedStatement p= null;
        SQL sql=new SQL("SELECT * FROM contestuser WHERE username=? AND cid=?",username,cid);
        try {
            ResultSet rs= sql.query();
            if(rs.next()){
                return new RegisterUser(rs);
            }else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }finally {
            sql.close();
        }
    }
    public String addUserContest(int cid,String username,int statu){
        new SQL("INSERT INTO contestuser VALUES(?,?,?,?,?)",cid,username,statu,"",Tool.now()).update();
        ContestMain.getContest(cid).reSetUsers();
        return "success";
    }
    public String setUserContest(int cid,String username,int statu,String info){
        if(getRegisterStatu(username,cid)==null){
            addUserContest(cid,username,statu);
        }
        if(statu==-2){
            ContestMain.getContest(cid).reSetUsers();
            return delUserContest(cid,username);
        }
        if(statu!=3){
            MessageMain.addMessageRegisterContest(username,cid,statu);
            new SQL("UPDATE contestuser set statu=? WHERE cid=? AND username=?",statu,cid,username).update();
        }else{
            MessageMain.addMessageRegisterContest(username,cid,statu);
            new SQL("UPDATE contestuser set statu=?,info=? WHERE cid=? AND username=?",statu,info,cid,username).update();
        }
        ContestMain.getContest(cid).reSetUsers();
        return "success";
    }
    public String delUserContest(int cid,String username){
        new SQL("DELETE FROM contestuser  WHERE cid=? AND username=?",cid,username).update();
        PreparedStatement p= null;
        ContestMain.getContest(cid).reSetUsers();
        return "success";
    }
    public Contest getContest(int cid) {
        //cSQL作为缓存，如果里面有这个contest的话就直接获取，否则从数据库获取
        if(cSQL.size()>=maxmapnum) cSQL.clear();
        if(cSQL.containsKey(cid)){
            //System.out.println("get("+cid+") in cSQL");
            return cSQL.get(cid);
        }
        ResultSet c,pros,rs,ru;
        SQL sql1=new SQL("select * from contest where id=?",cid);
        c=sql1.query();
        SQL sql2=new SQL("select pid,tpid from contestproblems where cid=? order by pid",cid);
        pros=sql2.query();
        SQL sql3=new SQL("select id,ruser,pid,cid,lang,submittime,result,timeused,memoryUsed,codelen from statu where cid=?",cid);
        rs=sql3.query();
        SQL sql4=new SQL("select * from contestuser where cid=?",cid);
        ru=sql4.query();
        try {
            Contest con = new Contest(c,pros,rs,ru);
            cSQL.put(cid,con);
            return con;
        }finally {
            sql1.close();
            sql2.close();
            sql3.close();
            sql4.close();
        }
    }
    public void deleteMapContest(int cid){
        cSQL.remove(cid);
    }
    public int getNewId(){
        return new SQL("select MAX(id)+1 from contest").queryNum();
    }
    public int size(){
        return cSQL.size();
    }
    public List<Contest> getContests(int from,int num,int statu,String name,int type,int kind){
        //statu: pending/running/end   0/1/2
        List<Contest> list=new ArrayList<Contest>();
        PreparedStatement p1=null;
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
        SQL sql1=new SQL(sql,from,num);
        //System.out.println(p1.toString());
        ResultSet c=sql1.query();
        try {
            while(c.next()){
                list.add(new Contest(c));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            sql1.close();
        }
        return list;
    }
    public int getContestsNum(int statu,String name,int type,int kind){
        String sql="";
        sql+="select count(*)";
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
        return new SQL(sql).queryNum();
    }
    public List<Contest> getRecentlyContests(int num){
        List<Contest> list=new ArrayList<Contest>();
        PreparedStatement p1=null;
        String sql="";
        sql+="select *";
        sql+=" from contest where endTime>=now()";
        sql+=" order by beginTime desc";
        sql+=" limit 0,?";
        SQL sql1=new SQL(sql,num);
        ResultSet c=sql1.query();
        try {
            while(c.next()){
                list.add(new Contest(c));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            sql1.close();
        }
        return list;
    }
    public List<Integer> getAcRidFromCidPid(int cid,int pid){
        return new SQL("SELECT id FROM statu WHERE cid=? AND pid=? AND result=1", cid,pid).queryList();
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
        SQL sql=new SQL("SELECT * FROM `contestproblems` left join contest on cid=id WHERE tpid=? order by begintime limit 0,1",pid);
        ResultSet rs=sql.query();
        try {
            if(rs.next()){
                Timestamp begintime=rs.getTimestamp("begintime");
                if(begintime.after(Tool.now())){
                    return "无";
                }else{
                    return HTML.a("Contest.jsp?cid="+rs.getInt("id"),rs.getString("name"));
                }
            }else{
                return "无";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            sql.close();
        }
        return "无";
    }
}
