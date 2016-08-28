package dao;

import entity.*;
import servise.ContestMain;
import util.Event.EventMain;
import util.Event.Events.EventStatusChange;
import util.Main;
import util.HTML.HTML;
import util.Pair;
import util.SQL;
import util.Tool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Administrator on 2015/5/23.
 */
public class statusSQL {
    /**
    * statu(id,ruser,pid,cid,lang,submitTime,result,timeUsed,memoryUsed,code)
    * ceinfo(rid,info)
    */
    private int maxRID;
    public void init(){
        maxRID=getNewRid();
        //status = new ArrayList<statu>();
    }
    private int getNewRid(){
        return new SQL("select MAX(id) from statu").queryNum();
    }
    public synchronized int addStatu(Status s){
        int rid=maxRID+1;
        maxRID++;
        new SQL("insert into statu values(?,?,?,?,?,?,?,?,?,?,?)"
                ,rid
                ,s.getUser()
                ,s.getPid()
                ,s.getCid()
                ,s.getLanguage()
                ,s.getSbmitTime()
                , Status.resultToInt(s.getResult())
                ,s.getTimeUsed()
                ,s.getMemoryUsed()
                ,s.getCode()
                ,s.getCodelen()).update();
        return rid;
    }
    public Status getStatu(int rid){
        SQL sql=new SQL("select id,ruser,pid,cid,lang,submittime,result,timeUsed,memoryUsed,code,codelen from statu where id=?",rid);
        ResultSet r=sql.query();
        Status s=null;
        try {
            r.next();
            s = new Status(r,10);
            r.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            sql.close();
        }
        return s;
    }
    public List<Status> getStatusKind0(int cid,int from,int num,
                                      int pid,int result,int Language,String ssuser){
        String sql="SELECT id,ruser,statu.pid,statu.cid,lang,submittime,result,timeused,memoryUsed,codelen" +
                " FROM statu LEFT JOIN contestproblems" +
                " ON contestproblems.cid=? AND tpid=statu.pid" +
                " WHERE contestproblems.cid=? ";
        if(pid!=-1){
            sql+=" AND pid="+ ContestMain.getContest(cid).getGlobalPid(pid);
        }
        if(result!=-1) sql+=" AND result="+result;
        if(Language!=-1) sql+=" AND lang="+Language;
        if(ssuser!=null&&!ssuser.equals("")) sql+=" AND ruser='"+ssuser+"'";
        sql+=" ORDER BY id DESC";
        sql+=" LIMIT ?,?";
        return new SQL(sql,cid,cid,from,num).queryBeanList(Status.class);
    }
    public int getStatusKind0Num(int cid,
                                       int pid,int result,int Language,String ssuser){
        String sql="SELECT COUNT(*)" +
                " FROM statu LEFT JOIN contestproblems" +
                " ON contestproblems.cid=? AND tpid=statu.pid" +
                " WHERE contestproblems.cid=? ";
        if(pid!=-1){
            sql+=" AND pid="+ ContestMain.getContest(cid).getGlobalPid(pid);
        }
        if(result!=-1) sql+=" AND result="+result;
        if(Language!=-1) sql+=" AND lang="+Language;
        if(ssuser!=null&&!ssuser.equals("")) sql+=" AND ruser='"+ssuser+"'";
        sql+=" ORDER BY id DESC";
        return new SQL(sql,cid,cid).queryNum();
    }
    public List<Status> getStatus(int cid,int from,int num,
                     /*筛选信息：*/int pid,int result,int Language,String ssuser,boolean all){
        List<Status> l=new ArrayList<Status>();
        PreparedStatement p= null;
        SQL sql1;
        String sql="select id,ruser,pid,cid,lang,submitTime,result,timeUsed,memoryUsed,codelen from statu where ";
        if(!all) sql+=" cid=?";
        else sql+=" 1";
        //筛选
        if(pid!=-1){
            if(cid!=-1){
                sql+=" and pid="+ ContestMain.getContest(cid).getGlobalPid(pid);
            }else{
                sql+=" and pid="+pid;
            }
        }
        if(result!=-1) sql+=" and result="+result;
        if(Language!=-1) sql+=" and lang="+Language;
        if(ssuser!=null&&!ssuser.equals("")) sql+=" and ruser='"+ssuser+"'";
        sql+=" order by id desc";
        sql+=" limit "+from+","+num;
        if(!all){
            sql1=new SQL(sql,cid);
        }else sql1=new SQL(sql);
        try {
            ResultSet r=sql1.query();
            while(r.next()){
                Status st= new Status(r,9);
                l.add(st);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            sql1.close();
        }
        return l;
    }
    public int getStatusNum(int cid,
                     /*筛选信息：*/int pid,int result,int Language,String ssuser,boolean all){
        List<Status> l=new ArrayList<Status>();
        int ret=1;
        PreparedStatement p= null;
        String sql="select count(*) from statu where ";
        if(!all) sql+=" cid=?";
        else sql+=" 1";
        //筛选
        if(pid!=-1){
            if(cid!=-1){
                sql+=" and pid="+ContestMain.getContest(cid).getGlobalPid(pid);
            }else{
                sql+=" and pid="+pid;
            }
        }
        if(result!=-1) sql+=" and result="+result;
        if(Language!=-1) sql+=" and lang="+Language;
        if(ssuser!=null&&!ssuser.equals("")) sql+=" and ruser='"+ssuser+"'";
        if(!all) return new SQL(sql,cid).queryNum();
        else return new SQL(sql).queryNum();
    }
    public List<Status> getTeamStatus(int cid,int from,int num,
                                     int pid,int result,int Language,String ssuser){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id,")
                .append("teamusername as ruser,")
                .append("pid,statu.cid as cid,lang,submitTime,result,timeUsed,memoryUsed,codelen")
                .append(" FROM statu RIGHT JOIN t_register_team")
                .append(" ON statu.cid=t_register_team.cid AND ruser=username")
                .append(" WHERE statu.cid=?");
        if(pid!=-1){
            sql.append(" AND pid=").append(pid);
        }
        if(result!=-1){
            sql.append(" AND result=").append(result);
        }
        if(Language!=-1){
            sql.append(" AND language=").append(Language);
        }
        if(ssuser!=null&&!ssuser.equals("")){
            sql.append(" AND ruser='").append(ssuser).append("'");
        }
        sql.append(" ORDER BY id DESC")
                .append(" LIMIT ?,?");
        return new SQL(sql.toString(),cid,from,num).queryBeanList(Status.class);
    }
    public int getTeamStatusNum(int cid,int pid,int result,int Language,String ssuser){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*)")
                .append(" FROM statu RIGHT JOIN t_register_team")
                .append(" ON statu.cid=t_register_team.cid AND ruser=username")
                .append(" WHERE statu.cid=?");
        if(pid!=-1){
            sql.append(" AND pid=").append(pid);
        }
        if(result!=-1){
            sql.append(" AND result=").append(result);
        }
        if(Language!=-1){
            sql.append(" AND language=").append(Language);
        }
        if(ssuser!=null&&!ssuser.equals("")){
            sql.append(" AND ruser='").append(ssuser).append("'");
        }
        sql.append(" ORDER BY id DESC");
        return new SQL(sql.toString(),cid).queryNum();
    }

    public synchronized Status setStatusResult (int rid, Result res, String time, String Meory, String CEinfo){
        Status ps = getStatu(rid);
        new SQL("update statu set result=?,timeUsed=?,memoryUsed=? where id=?", Status.resultToInt(res),time,Meory,rid).update();
        Tool.log(rid+"->"+res);
        Status s=getStatu(rid);
        EventMain.triggerEvent(new EventStatusChange(ps,s));
        if(s.getCid()!=-1&&res!=Result.JUDGING){
            Contest c=ContestMain.getContest(s.getCid());
            c.getRank().add(s, c);
        }
        if(res==Result.CE||res==Result.ERROR){
            addCEInfo(rid, CEinfo);
        }
        return s;
    }
    public void onStatusChange(Status ps, Status s){
        if(ps.getRid() != s.getRid()) return ;
        int chg;
        if(!ps.getResult().isAc() && s.getResult().isAc()){
            chg = 1;
        }else if(ps.getResult().isAc() && !s.getResult().isAc()){
            chg = -1;
        }else{
            return ;
        }
        Problem p = Main.problems.getProblem(s.getPid());
        int count = new SQL("SELECT COUNT(*) FROM statu WHERE ruser=? AND pid=? AND result=? AND id!=?",s.getUser(), s.getPid(), Result.AC.getValue(),s.getRid()).queryNum();
        if(count >0){
            //如果有其他的AC
            Main.problems.updateProblemTotals(s.getPid(), p.totalSubmit, p.totalSubmitUser, p.totalAc + chg, p.totalAcUser);
        }else{
            Main.problems.updateProblemTotals(s.getPid(), p.totalSubmit, p.totalSubmitUser, p.totalAc + chg, p.totalAcUser + chg);
        }
        new SQL("REPLACE INTO t_usersolve VALUES(?,?,(SELECT MAX(result=1) FROM statu WHERE ruser=? AND pid=? GROUP BY ruser))",
                s.getUser(),
                s.getPid(),
                s.getUser(),
                s.getPid()).update();
    }
    public void onStatusAdd(Status s){
        Problem p = Main.problems.getProblem(s.getPid());
        int count = new SQL("SELECT COUNT(*) FROM statu WHERE ruser=? AND pid=? AND id<?",s.getUser(), s.getPid(),s.getRid()).queryNum();
        if(count == 0){
            Main.problems.updateProblemTotals(s.getPid(), p.totalSubmit + 1, p.totalSubmitUser+1, p.totalAc, p.totalAcUser);
        } else{
            Main.problems.updateProblemTotals(s.getPid(), p.totalSubmit + 1, p.totalSubmitUser, p.totalAc, p.totalAcUser);
        }
        new SQL("replace into t_usersolve values(?,?,(SELECT MAX(result=1) FROM statu WHERE ruser=? AND pid=? GROUP BY ruser))",
                s.getUser(),
                s.getPid(),
                s.getUser(),
                s.getPid()).update();
    }
    private Set<Integer> getProblems(String user,int solved){
        Set<Integer> ret=new TreeSet<Integer>();
        if(user==null) return null;
        SQL sql=new SQL("SELECT pid FROM t_usersolve WHERE status=? AND username=?",solved,user);
        try{
            ResultSet rs=sql.query();
            while(rs.next()){
                ret.add(rs.getInt(1));
            }
            rs.close();
            return ret;
        }catch(SQLException e){
            return ret;
        }finally {
            sql.close();
        }
    }
    public Set<Integer> getAcProblems(String user){//获取user已经AC题目列表（包括contest内的）
        return getProblems(user, 1);
    }
    public Set<Integer> getNotAcProblems(String user){//获取user提交过但没有AC的题目列表（不包括contest内的）
        return getProblems(user, 0);
    }
    public int getSubmitTime(String username){//获取user的提交次数
        return new SQL("SELECT COUNT(id) FROM statu WHERE ruser=?",username).queryNum();
    }
    public List<Pair<Integer,Integer>> getSubmitCount(String user, int num, int sec){
        String sql="";
        sql+=" select floor(timestampdiff(second,submittime ,current_timestamp())/(?)) as T,count(*) as count from statu ";
        if(user!=null) sql+=" WHERE ruser='"+user+"' ";
        sql+=" group by T order by T limit 0,?";
        return new SQL(sql,sec,num){
            protected Integer getObject(int i) throws SQLException {return rs.getInt(i);}
        }.queryPairList();
    }
    public List<Pair<Integer,Integer>> getAcCount(String user,int num,int sec){
        String sql="";
        sql+=" select floor(timestampdiff(second,submittime ,current_timestamp())/(?)) as T,count(*) as count from statu where result=1 ";
        if(user!=null) sql+=" and ruser='"+user+"' ";
        sql+=" group by T order by T limit 0,?";
        return new SQL(sql,sec,num){
            protected Integer getObject(int i) throws SQLException {return rs.getInt(i);}
        }.queryPairList();
    }
    public Map<Integer,Integer> getProblemStatus(int pid){
        SQL sql=new SQL("SELECT result,count(id) FROM statu WHERE pid=? GROUP BY result",pid);
        ResultSet rs=sql.query();
        //List<Integer> list=new ArrayList<Integer>(20);
        Map<Integer,Integer> map = new HashMap<Integer, Integer>();
        try {
            while(rs.next()){
                map.put(rs.getInt(1),rs.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            sql.close();
        }
        return map;
    }
    public List<String[]> getProblemShortCodeTop10(int pid){
        SQL sql=new SQL("SELECT ruser,min(codelen) as minlen FROM statu WHERE pid=? AND result=1 group by ruser order by minlen  limit 0,10", pid);
        ResultSet rs=sql.query();
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
        }finally {
            sql.close();
        }
        return list;
    }
    public void addCEInfo(int rid, String info){
        new SQL("delete from ceinfo where rid=?",rid).update();
        if(info.length()>800){
            info=info.substring(0,797)+"...";
        }
        new SQL("insert into ceinfo values(?,?)",rid,info).update();
    }
    public String getCEInfoHTML(int rid, boolean havepanle){
        String ret=new SQL("select info from ceinfo where rid=?",rid).queryString();
        if(ret.equals("")){
            ret="none";
        }
        return havepanle?HTML.panel("Compilation Error Info", HTML.code(ret,false,-1),null,"warning"):HTML.code(ret,false,-1);
    }
    public Map<Integer,Integer> sbumitResult(String username,int pid1,int pid2){
        return new SQL("select pid,status from t_usersolve where username=? AND pid>=? AND pid<=?",username,pid1,pid2){
            protected Integer getObject(int i) throws SQLException {
                return rs.getInt(i);
            }
        }.queryMap();
    }
    public int submitResult(int cid, int pid, String username){
        //1->AC
        //0->submit but no AC
        //-1->no submit
        return new SQL("select MAX(result=1)+1 from statu where ruser=? AND cid=? AND pid=?",username,cid,pid).queryNum()-1;
    }
    public int submitResult(int pid, String username){
        //1->AC
        //0->submit but no AC
        //-1->no submit
        return new SQL("select MAX(result=1)+1 from statu where ruser=? AND pid=?",username,pid).queryNum()-1;
    }

    /**
     * 批量重判
     * @param pid 题目id
     * @param fromRid 开始rid，限制重判的范围
     * @param status status==1 表示 只重判ac代码，status==2 表示重判除了CE以外的所有代码，status==3表示全部，status==4表示重判所有padding和judging
     * @return
     */
    public List<Integer> getRidsToRejudge(int pid, int fromRid , int status){
        SQL sql;
        String sqlString="SELECT id FROM statu WHERE pid=? AND id>=? ";
        if(status==1){
            sqlString=sqlString+"AND result==1";
        }else if(status==2){
            sqlString=sqlString+"AND result!=3";
        }else if(status==4){
            sqlString=sqlString+"AND (result==0 OR result==12)";
        }
        sql=new SQL(sqlString,pid,fromRid){
            @Override
            protected Object getObject(int i) throws SQLException {
                return rs.getInt(i);
            }
        };
        return sql.queryList();
    }
}
