package dao;

import entity.*;
import servise.ContestMain;
import util.Main;
import servise.MessageMain;
import util.HTML.HTML;
import util.SQL;
import action.addcontest;
import util.Tool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/5/24.
 */
public class ContestSQL extends BaseCache<Integer,Contest> {
    /*
    * contest(id,name,beginTime,endTime,rankType,ctype,password,registerendtime)
    * contestproblems(cid,pid,tpid)
    * contestuser(cid,username,statu,info)
    * */
    public ContestSQL(){
        this.maxSize = 10;
        this.cachTime = 60*60*1000;
    }
    private String deleteproblems(int cid){
        new SQL("delete from contestproblems where cid=?",cid).update();
        return "success";
    }
    private String addProblems(int cid,String s){
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
                ,a.getPass()
                ,Tool.getTimestamp(a.getRegisterstarttime_d(), a.getRegisterstarttime_s(), a.getRegisterstarttime_m())
                ,Tool.getTimestamp(a.getRegisterendtime_d(), a.getRegisterendtime_s(), a.getRegisterendtime_m())
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
        Contest c = ContestMain.getContest(cid);
        if(c.getType()==Contest_Type.TEAM_OFFICIAL){
            return new SQL("SELECT * FROM t_register_team WHERE username=? AND cid=?",
                    username,cid).setLog(false)
                    .queryBean(RegisterTeam.class);
        }else {
            return new SQL("SELECT * FROM contestuser WHERE username=? AND cid=?", username, cid).setLog(false).queryBean(RegisterUser.class);
        }
    }
    public String addUserContest(int cid,String username,int statu){
        new SQL("INSERT INTO contestuser VALUES(?,?,?,?,?)",cid,username,statu,"",Tool.now()).update();
        removeCatch(cid);
        return "success";
    }
    public String setUserContest(int cid,String username,int statu,String info){
        Contest c = ContestMain.getContest(cid);
        if(getRegisterStatu(username,cid)==null){
            if(c.getType()==Contest_Type.TEAM_OFFICIAL){
                RegisterTeam rt = new RegisterTeam();
                rt.setUsername(username);
                rt.teamName = username;
                rt.setStatu(statu);
                rt.setInfo(info==null?"":info);
                rt.setTime(Tool.now());
                addRegisterTeam(cid,rt);
            }else {
                addUserContest(cid, username, statu);
            }
        }
        if(statu==-2){//-2 -> 删除
            removeCatch(cid);
            if(c.getType() == Contest_Type.TEAM_OFFICIAL) {
                return delTeamContest(cid,username);
            }else {
                return delUserContest(cid, username);
            }
        }
        String table = "contestuser";
        if(c.getType() == Contest_Type.TEAM_OFFICIAL){
            table = "t_register_team";
        }
        if(statu!=3){
            new SQL("UPDATE "+table+" set statu=? WHERE cid=? AND username=?",statu,cid,username).update();
            MessageMain.addMessageRegisterContest(username, cid, statu);
        }else{//3 -> 需修改
            new SQL("UPDATE "+table+" set statu=?,info=? WHERE cid=? AND username=?",statu,info,cid,username).update();
            MessageMain.addMessageRegisterContest(username, cid, statu);
        }
        removeCatch(cid);
        return "success";
    }
    public String delUserContest(int cid,String username){
        new SQL("DELETE FROM contestuser  WHERE cid=? AND username=?",cid,username).update();
        removeCatch(cid);
        return "success";
    }
    public String delTeamContest(int cid,String username){
        Tool.log("delTeamContest");
        new SQL("DELETE FROM t_register_team  WHERE cid=? AND username=?",cid,username).update();
        new SQL("DELETE FROM t_userinfo WHERE cid=? AND username=?",cid,username).update();
        removeCatch(cid);
        return "success";
    }
    public Contest getContest(int cid) {
        return getBeanByKey(cid);
    }
    public int getNewId(){
        return new SQL("select MAX(id)+1 from contest").queryNum();
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
        }else{
            if(!Main.loginUserPermission().getAddContest()){
                sql+=" and kind!=4";//4->隐藏
            }
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
        //List<Contest> list=new ArrayList<Contest>();
        String sql="SELECT *";
        sql+=" FROM contest WHERE endTime>=NOW() and kind!=4";
        sql+=" ORDER BY beginTime DESC";
        sql+=" LIMIT 0,?";
        return new SQL(sql,num).queryBeanList(Contest.class);
        /*SQL sql1=new SQL(sql,num);
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
        return list;*/
    }
    public List<Integer> getAcRidFromCidPid(int cid,int pid){
        return new SQL("SELECT id FROM statu WHERE cid=? AND pid=? AND result=1", cid,pid).queryList();
    }

    /**
     * 题目所属的第一场比赛（即题目的出处） 已经开始才显示
     * @param pid 题目ID
     * @return 返回题目连接和名称的超链接
     */
    public String problemContest(int pid){
        SQL sql=new SQL("SELECT * FROM `contestproblems` left join contest on cid=id WHERE tpid=? and kind!=4 order by begintime limit 0,1",pid);
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

    public List<RegisterTeam> getRegisterTeamByCid(int cid){
        List<RegisterTeam> list = new SQL("SELECT * FROM t_register_team WHERE cid = ? " +
                "ORDER BY time DESC",cid).queryBeanList(RegisterTeam.class);
        List<TeamMember> list_member = new SQL("SELECT * FROM t_userinfo WHERE cid = ? ",cid).queryBeanList(TeamMember.class);
        int i = 0;
        for(TeamMember tm : list_member){
            for(RegisterTeam rt:list){
                if(tm.username.equals(rt.getUsername())){
                    rt.addMember(tm);
                }
            }
        }
        return list;
    }
    public RegisterTeam getRegisterTeam(int cid,String username){
        RegisterTeam rt = new SQL("SELECT * FROM t_register_team WHERE cid = ? AND username = ?",
                cid,username).queryBean(RegisterTeam.class);
        if(rt==null) return null;
        List<TeamMember> list_member = new SQL("SELECT * FROM t_userinfo WHERE cid = ? AND username = ? ORDER BY id"
                ,cid,username).queryBeanList(TeamMember.class);
        for(TeamMember tm:list_member){
            rt.addMember(tm);
        }
        for(int i=0;i<3;i++){
            if(rt.getMember(i)==null){
                rt.addMember(new TeamMember());
            }
        }
        return rt;
    }
    public void addRegisterTeam(int cid,RegisterTeam rt){
        new SQL("INSERT INTO t_register_team VALUES(?,?,?,?,?,?,?,?)",
                rt.getUsername(),
                cid,
                rt.teamUserName,
                rt.teamPassword,
                rt.teamName,
                rt.getStatu(),
                rt.getInfo(),
                rt.getTime()).update();

        for(int i=0;i<3;i++){
            TeamMember tm = rt.getMember(i);
            if(tm==null) continue;
            new SQL("INSERT INTO t_userinfo VALUES(?,?,?,?,?,?,?,?,?,?,?)",
                    rt.getUsername(),
                    cid,
                    i,
                    tm.getName(),
                    tm.getGender(),
                    tm.getSchool(),
                    tm.getFaculty(),
                    tm.getMajor(),
                    tm.getCla(),
                    tm.getNo(),
                    tm.getPhone()).update();
        }
    }
    public void updatePassword(int cid,String username,String teamusername,String pass){
        new SQL("UPDATE t_register_team SET teamusername=?,teampassword=? WHERE cid=? AND username=?",
                teamusername,pass,cid,username).update();
    }
    public String getMaxTeamUsername(int cid){
        return new SQL("SELECT MAX(teamusername) FROM t_register_team WHERE cid=?",cid).queryString();
    }

    @Override
    protected Contest getByKeyFromSQL(Integer cid) {
        //cSQL作为缓存，如果里面有这个contest的话就直接获取，否则从数据库获取
        ResultSet c,pros;
        SQL sql1=new SQL("select * from contest where id=?",cid);
        c=sql1.query();
        SQL sql2=new SQL("select pid,tpid from contestproblems where cid=? order by pid",cid);
        pros=sql2.query();
        try {
            return new Contest(c,pros);
        }finally {
            sql1.close();
            sql2.close();
        }
    }
}
