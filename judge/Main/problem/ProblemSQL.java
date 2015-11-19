package Main.problem;

import Main.Main;
import Main.User.User;
import Main.problem.Problem;
import Tool.HTML.HTML;
import Tool.HTML.problemHTML.problemHTML;
import Tool.HTML.problemListHTML.problemView;
import Tool.SQL;
import action.editproblem;
import javafx.util.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/5/23.
 */
public class ProblemSQL {
    /*
    * problem(pid,ptype,title,ojid,ojspid)
    * */
    //private List<Problem> pSQL;//if here is SQL
    private Map<Integer,Problem> pSQL;//缓存problem
    private final int MAXSIZE=10;
    public ProblemSQL(){
        pSQL=new HashMap<Integer, Problem>();
    }
    private void Insert(int pid,Problem p){
        if(pSQL.size()>=MAXSIZE){
            //控制缓存内的pid数量
            pSQL.clear();
        }
        pSQL.put(pid, p);
    }
    public void Remove(int pid){//从缓存里面清除
        pSQL.remove(pid);
    }
    public Problem getProblem(int pid){
        //select pid,type,title,ojid,ojspid,visiable
        if(pSQL.size()>=MAXSIZE) pSQL.clear();
        Problem pr=pSQL.get(pid);
        if(pr!=null) return pr;
        try {
            PreparedStatement p=null;
            p = Main.conn.prepareStatement("select pid,ptype,title,ojid,ojspid,visiable from problem where pid = ? ");
            p.setInt(1, pid);
            ResultSet r=p.executeQuery();
            r.next();
            Problem pro=new Problem(r);
            Insert(pid,pro);
            return pro;
        } catch (SQLException e) {
            return null;
        }
    }
    public List<problemView> getProblems(int pid1,int pid2,boolean showhide){
        List<problemView> ret=new ArrayList<problemView>();
        PreparedStatement p= null;
        try {
            String sql="select pid,title,visiable,0,0 from problem where pid>=? and pid<=?";
            if(!showhide){
                sql+=" and visiable=1";
            }
            p=Main.conn.prepareStatement(sql);
            p.setInt(1,pid1);
            p.setInt(2, pid2);
            ResultSet r=p.executeQuery();
            while(r.next()){
                ret.add(new problemView(r));
            }
            r.close();
        } catch (SQLException e) {
            return ret;
            //e.printStackTrace();
        }
        return ret;
    }
    public List<problemView> getProblems(int cid){
        List<problemView> list = new ArrayList<problemView>();
        PreparedStatement p=null;
        try {
            //pid,title,visiable,ac,submit
            String sql="SELECT tt.pid as pid,title,0,acuser,count(username) as submit\n" +
                    "FROM contestusersolve_view\n" +
                    "RIGHT JOIN\n" +
                    "    (SELECT t4.pid as pid,t4.title,count(username) acuser\n" +
                    "     FROM\n" +
                    "        (SELECT pid,username \n" +
                    "         FROM contestusersolve_view \n" +
                    "         WHERE cid=? AND solved=1\n" +
                    "        )t1\n" +
                    "     RIGHT JOIN\n" +
                    "        (SELECT t.pid as pid,problem.title as title \n" +
                    "         FROM \n" +
                    "            (SELECT pid,tpid \n" +
                    "             FROM contestproblems \n" +
                    "             WHERE cid=? order by pid\n" +
                    "            )t,\n" +
                    "            problem \n" +
                    "         WHERE t.tpid=problem.pid \n" +
                    "         ORDER BY t.pid\n" +
                    "        )t4\n" +
                    "     ON t1.pid=t4.pid \n" +
                    "     GROUP BY t4.pid\n" +
                    "    )tt\n" +
                    "ON tt.pid=contestusersolve_view.pid and contestusersolve_view.cid=?\n" +
                    "GROUP BY pid";
            p=Main.conn.prepareStatement(sql);
            p.setInt(1,cid);
            p.setInt(2,cid);
            p.setInt(3,cid);
            ResultSet r=p.executeQuery();
            while(r.next()){
                list.add(new problemView(r));
            }
            r.close();
            return list;
        } catch (SQLException e) {
            return list;
        }
    }
    public int getPageNum(int num,boolean showHide){
        PreparedStatement p= null;
        try {
            String sql="select MAX(pid) from problem where pid<10000";
            if(!showHide) {
                sql+=" and visiable=1";
            }
            p=Main.conn.prepareStatement(sql);
            ResultSet r=p.executeQuery();
            r.next();
            int maxpid=r.getInt(1);
            r.close();
            return (maxpid-1000)/num+1;
        } catch (SQLException e) {//没有任何题目默认有一页为空
            return 1;
            //e.printStackTrace();
        }
        //return 1;
    }
    public void editProblem(int pid,Problem pro){
        PreparedStatement p;
        try {
            p = Main.conn.prepareStatement("UPDATE problem SET title=?,ojid=?,ojspid=? WHERE pid=?");
            p.setString(1,pro.getTitle());
            p.setInt(2, pro.getOjid());
            p.setString(3, pro.getOjspid());
            p.setInt(4, pid);
            p.executeUpdate();
            Main.problems.delProblem(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    public int addProblem(int pid,Problem pro){
        PreparedStatement p;
        int newpid=1000;
        if(pid==-1){
            try {
                p = Main.conn.prepareStatement("select MAX(pid)+1 from problem");
                ResultSet rs=p.executeQuery();
                rs.next();
                newpid=rs.getInt(1);
                System.out.println("newpid:"+newpid);
            } catch (SQLException e) {
                e.printStackTrace();
                newpid=1000;
            } catch (NullPointerException e){
                e.printStackTrace();
                return -1;
            }
        }else{
            editProblem(pid,pro);
            return pid;
        }
        try {
            p = Main.conn.prepareStatement("Insert into problem values(?,?,?,?,?,?)");
            if(pid==-1) p.setString(1, newpid + "");
            else p.setString(1, pid + "");
            p.setString(2, pro.getType()+"");
            p.setString(3, pro.getTitle());
            p.setString(4, pro.getOjid()+"");
            p.setString(5, pro.getOjspid()+"");
            p.setInt(6,0);
            p.executeUpdate();
            Insert(pid,pro);//插入缓存 和 数据库
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        return newpid;
    }
    public void delProblem(int pid){pSQL.remove(pid);}
    public String setProblemVisiable(int pid){
        PreparedStatement p;
        if(pid!=-1){
            try {
                p = Main.conn.prepareStatement("update problem set visiable=1-visiable where pid=?");
                p.setInt(1, pid);
                p.executeUpdate();
                delProblem(pid);
                return "success";
            } catch (SQLException e) {
                return "error";
            } catch (NullPointerException e){
                return "error";
            }
        }else return "error";
    }
    public boolean setProblemVisiable(int pid,int z){
        delProblem(pid);
        return (SQL.update("update problem set visiable=? where pid=?",z,pid)==1);
    }
    public List<Integer> getProblemsByOjPid(int oj,String ojspid){
        ResultSet rs=SQL.query("SELECT pid FROM problem WHERE ojid=? AND ojspid=?",oj,ojspid);
        List<Integer> ret=new ArrayList<Integer>();
        try {
            while(rs.next()){
                ret.add(rs.getInt(1));
            }
            return ret;
        } catch (SQLException e) {
            return ret;
        }
    }

    public boolean isProblemLocal(int pid){
        return getProblem(pid).isLocal();
    }
    public int getOJid(int pid){
        return getProblem(pid).getOjid();
    }
    public String getOjspid(int pid){
        return getProblem(pid).getOjspid();
    }
    public int size(){
        return pSQL.size();
    }
    public String getTitle(int pid) { return getProblem(pid).getTitle();}
    public void saveProblemHTML(int pid,problemHTML ph){
        if(ph==null) return ;
        PreparedStatement p= null;
        try {
            String sql="INSERT INTO t_problemview VALUES(?,?,?,?,?,?,?,?)";
            p=Main.conn.prepareStatement(sql);
            p.setInt(1,pid);
            p.setString(2,ph.getTimeLimit());
            p.setString(3,ph.getMenoryLimit());
            p.setString(4,ph.getInt64());
            p.setInt(5, ph.getSpj());
            p.setString(6, ph.getDis());
            p.setString(7,ph.getInput());
            p.setString(8,ph.getOutput());
            p.executeUpdate();
            List<String> in=ph.getSampleInput();
            List<String> out=ph.getSampleOutput();
            for(int i=0;i<in.size();i++){
                sql="INSERT INTO t_problem_sample VALUES(?,?,?,?)";
                p=Main.conn.prepareStatement(sql);
                p.setInt(1,pid);
                p.setInt(2,i);
                p.setString(3, in.get(i));
                p.setString(4, out.get(i));
                System.out.println(p);
                p.executeUpdate();
            }
        } catch (SQLException ignored) {
            System.out.println("saveerror");
        }
    }
    public String getP(int pid,String edit,int num){
        problemHTML p=getProblemHTML(pid);
        if(edit.equals("dis")) return p.getDis();
        if(edit.equals("input")) return p.getInput();
        if(edit.equals("output")) return p.getOutput();
        if(edit.equals("sampleinput")){
            List<String> s=p.getSampleInput();
            if(num<s.size())
                return s.get(num);
            else return "";
        }
        if(edit.equals("sampleoutput")){
            List<String> s=p.getSampleOutput();
            if(num<s.size())
                return s.get(num);
            else return "";
        }
        return "";
    }
    public String editProlem(editproblem ep){
        try {
            PreparedStatement p=null;
            String sql;
            if(ep.getEdit().equals("dis")){
                sql="update t_problemview set Dis=? WHERE pid=?";
                p = Main.conn.prepareStatement(sql);
                p.setString(1,ep.getS());
                p.setInt(2, Integer.parseInt(ep.getPid()));
            }
            if(ep.getEdit().equals("input")){
                sql="update t_problemview set Input=? WHERE pid=?";
                p = Main.conn.prepareStatement(sql);
                p.setString(1,ep.getS());
                p.setInt(2,Integer.parseInt(ep.getPid()));
            }
            if(ep.getEdit().equals("output")){
                sql="update t_problemview set Output=? WHERE pid=?";
                p = Main.conn.prepareStatement(sql);
                p.setString(1,ep.getS());
                p.setInt(2,Integer.parseInt(ep.getPid()));
            }
            if(ep.getEdit().equals("sampleinput")){
                sql="update t_problem_sample set input=? WHERE pid=? AND id=?";
                p = Main.conn.prepareStatement(sql);
                p.setString(1,ep.getS());
                p.setInt(2, Integer.parseInt(ep.getPid()));
                p.setInt(3,Integer.parseInt(ep.getNum()));
            }
            if(ep.getEdit().equals("sampleoutput")){
                sql="update t_problem_sample set output=? WHERE pid=? AND id=?";
                p = Main.conn.prepareStatement(sql);
                p.setString(1,ep.getS());
                p.setInt(2,Integer.parseInt(ep.getPid()));
                p.setInt(3,Integer.parseInt(ep.getNum()));
            }
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }
    public boolean delProblemDis(int pid){
        SQL.update("delete from t_problemview where pid=?",pid);
        SQL.update("delete from t_problem_sample where pid=?",pid);
        return true;
    }
    public boolean addSample(int pid){
        return SQL.update("insert into t_problem_sample " +
                "values(?,0,'<pre style=\"padding:0px;border-style:none;background-color:transparent\"></pre>'" +
                ",'<pre style=\"padding:0px;border-style:none;background-color:transparent\"></pre>')",pid)==1;
    }
    public problemHTML getProblemHTML(int pid){
        problemHTML ph=new problemHTML(pid);
        PreparedStatement p= null;
        try {
            p=Main.conn.prepareStatement("SELECT pid,timelimit,MenoryLimit,Int64,spj,Dis,Input,Output FROM t_problemview WHERE pid= ? ");
            p.setInt(1, pid);
            ResultSet s=p.executeQuery();
            if(s.next()){
                ph.setTitle(getTitle(pid));
                ph.setTimeLimit(s.getString(2));
                ph.setMenoryLimit(s.getString(3));
                ph.setInt64(s.getString(4));
                ph.setSpj(s.getInt(5));
                ph.setDis(s.getString(6));
                ph.setInput(s.getString(7));
                ph.setOutput(s.getString(8));
                p=Main.conn.prepareStatement("SELECT input,output FROM t_problem_sample WHERE pid=? ORDER BY id");
                p.setInt(1,pid);
                s=p.executeQuery();
                while(s.next()) {
                    ph.addSample(s.getString(1), s.getString(2));
                }
                s.close();
                return ph;
            }else{
                return null;
            }
        } catch (SQLException ignored) {
            return null;
        }
    }
    public Pair<Integer,Integer> getProblemLimit(int pid){
        ResultSet rs=SQL.query("SELECT timelimit,MenoryLimit FROM t_problemview WHERE pid=?",pid);
        try {
            if(rs.next()){
                String timeLimitStr=rs.getString("timelimit");
                String memoryLimitStr=rs.getString("MenoryLimit");
                int timeLimit=Integer.parseInt(timeLimitStr.substring(0, timeLimitStr.length() - 2));
                int memoryLimit=Integer.parseInt(memoryLimitStr.substring(0, memoryLimitStr.length() - 2));
                return new Pair<Integer, Integer>(timeLimit,memoryLimit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Pair<Integer, Integer>(0,0);
    }
    public String toHref(int pid,int cid){
        return "Contest.jsp?cid="+cid+"#Problem_"+pid;
    }
    public String toHTML(int pid) {
        return toHTML(pid,-1,Main.problems.getProblem(pid));
    }
    public String toHTML(int pid,int cid){
        return toHTML(pid,cid,Main.contests.getContest(cid).getProblem(pid));
    }
    private String toHTML(int pid,int cid,Problem p){
        if(cid!=-1){
            return "<tr><td>"+(char)(pid+'A')+"</td><td>"+HTML.a(toHref(pid,cid),p.getTitle())+"</td></tr>";
        }else{
            return "<tr><td>"+pid+"</td><td>"+HTML.a("Problem.jsp?pid="+pid,p.getTitle())+"</td></tr>";
        }
    }
    public ResultSet getProblemListByTag(int tagid,int from,int num) {
        String sql = "";
        User u = Main.loginUser();
        boolean vis;
        vis = ( u != null && u.getPermission().getShowHideProblem() );
        sql += "SELECT v_problem_tag.pid, ptype, title, ojid, ojspid, visiable, acusernum, submitnum, tagid, rating, solved+1 as solved " +
                "FROM v_problem_tag " +
                "LEFT JOIN v_problem ON v_problem_tag.pid = v_problem.pid " +
                "LEFT JOIN usersolve_view ON username =  ? " +
                "AND usersolve_view.pid = v_problem_tag.pid " +
                "WHERE tagid = ? " +
                (vis ? "" : " and visiable=1 ") +
                "ORDER BY rating DESC " +
                "LIMIT ?,?";
        String username;
        if (u != null) username = u.getUsername();
        else username = "";
        return SQL.query(sql, username, tagid, from, num);
    }
}
