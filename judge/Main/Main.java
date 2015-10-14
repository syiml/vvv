package Main;

import CodeCompare.cplusplus.CPlusPlusCompare;
import Main.GlobalVariables.GlobalVariables;
import Main.OJ.BNUOJ.BNUOJ;
import Main.OJ.CF.CF;
import Main.OJ.HDU.HDU;
import Main.OJ.HUST.HUST;
import Main.OJ.NBUT.NBUT;
import Main.OJ.OTHOJ;
import Main.OJ.PKU.PKU;
import Main.contest.ContestSQL;
import Main.problem.ProblemSQL;
import Main.User.UserSQL;
import Main.status.Result;
import Main.status.statusSQL;
import Main.User.Permission;
import Main.User.User;
import Main.Vjudge.SubmitInfo;
import Main.Vjudge.VJudge;
import Main.contest.rank.RankSQL;
import Main.contest.Contest;
import Main.problem.Problem;
import Main.status.statu;
import action.addcontest;
import action.addproblem1;
import net.sf.json.JSONObject;
import org.apache.http.impl.io.HttpRequestWriter;
import org.apache.struts2.ServletActionContext;
import org.jsoup.Jsoup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/5/21.
 * 实现业务逻辑和一些简单的杂项功能
 */
public class Main {
    public static final JSONObject GV=GlobalVariables.read();
    public static Connection conn = null;
    public static ProblemSQL problems = new ProblemSQL();
    public static statusSQL status = new statusSQL();
    public static ContestSQL contests = new ContestSQL();
    public static UserSQL users = new UserSQL();
    public static OTHOJ[] ojs ={new HDU(),new BNUOJ(),new NBUT(),new PKU(),new HUST(),new CF()};
        //OJ列表。判题OJ顺序不能改变，否则导致已有题目的OJ不正确
    public static VJudge m=new VJudge();
    //常量获取
    final public static int problemShowNum=GV.getInt("problemShowNum");//每页显示的题目数量
    final public static int statuShowNum=GV.getInt("statuShowNum");//statu每页显示数量
    final public static int contestShowNum=GV.getInt("contestShowNum");//contest每页显示数量
    final public static int userShowNum=GV.getInt("userShowNum");//user每页显示数量
    final public static int discussShowNum=GV.getInt("discussShowNum");//discuss的显示数量
    final public static int autoConnectionTimeMinute=GV.getInt("autoConnectionTimeMinute");

    static{
        try {
            Class.forName(Main.GV.get("sqlclass").toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        autoConnecter cn=new autoConnecter();
        cn.conn();
        Thread connection=new Thread(cn);
        connection.start();
        status.init();
    }
    public static int doSubmit(String user,int pid,int cid,int language,String code,Timestamp submittime){//控制提交跳转到vj还是本地
        System.out.println("Main.doSubmit");
        System.out.println("cid="+cid+" pid="+pid);
        //statusSQL sts;
        //ProblemSQL prs;
        int rid;
        if(cid!=-1){//验证user是否有权限提交
            if(contests.getContest(cid).getEndTime().before(submittime)){
                return -1;//超出比赛结束时间
            }
        }
        if(cid!=-1) pid=Main.contests.getContest(cid).getGlobalPid(pid);//等于全局题目编号
        System.out.println("go=" + pid);
        statu s=new statu(0,user,pid,cid,language,code,submittime);
        rid = status.addStatu(s);//插入数据库，并获取rid
//        if(cid!=-1){//contest内添加对应的rid
//            //contests.getContest(cid).addStatus(rid);
//            //pid = contests.getContest(cid).getGlobalPid(pid);//获得实际的pid
//        }
        if(!problems.isProblemLocal(pid)){//is vj
            SubmitInfo ss=new SubmitInfo(rid,problems.getOjspid(pid),language,code,false);
            submitVJ(ss, problems.getOJid(pid));
            System.out.println("Main.doSubmit Done");
            return 0;//success submit to vj
        }else{//is local
            return 1;//success submit to local
        }
    }
    public static int submitVJ(SubmitInfo info,int oj){
        //System.out.println("Main.submitVJ");
        m.addSubmit(info, oj);
        return 1;
    }
    public static String addProblem(addproblem1 action){
        Problem p=new Problem(action.getOjid(),action.getOjspid(),action.getTitle());
        problems.addProblem(action.getPid(),p);
        return "success";
    }
    public static int rejudge(int rid){
        statu s=status.getStatu(rid);
        int pid=s.getPid();
        //if(s.getCid()==-1) pid=s.getPid();
        //else pid=Main.contests.getContest(s.getCid()).getGlobalPid(s.getPid());
        if(!problems.isProblemLocal(pid)){//is vj
            status.setStatusResult(rid, Result.PENDDING,"-","-",null);
            SubmitInfo ss=new SubmitInfo(rid,problems.getOjspid(pid),s.getLanguage(),s.getCode(),true);
            submitVJ(ss, problems.getOJid(pid));
            System.out.println("Main.ReJudge Done");
            return 0;//success submit to vj
        }else{//is local
            System.out.println("Main.doSubmit Done");
            return 1;//success submit to local
        }
    }
    public static String setProblemVisiable(int pid){
        return problems.setProblemVisiable(pid);
    }
    public static String addContest(addcontest a){
        int cid=Main.contests.getNewId();
        if(contests.addContest(cid,a).equals("error")) return "error";
        if(RankSQL.addRank(cid,a).equals("error")) return "error";
        return "success";
    }
    public static String editContest(addcontest a){
        int cid=Integer.parseInt(a.cid);
        if(contests.editContest(cid,a).equals("error")) return "error";
        if(RankSQL.editRank(cid,a).equals("error")) return "error";
        contests.deleteMapContest(Integer.parseInt(a.cid));
        return "success";
    }
    public static String registerContest(int cid){
        User u=(User)getSession().getAttribute("user");
        if(u==null) return "login";
        int statu=0;
        Contest c=contests.getContest(cid);
        if(c.getRegisterendtime().before(Main.now())||c.getRegisterstarttime().after(Main.now())){
            return "error";
        }
        if(c.getType()==3){
            statu=1;
        }
        if(c.getType()==4){
            statu=0;
        }
        if(c.getKind()==3&&!u.canRegisterOfficalContest()){
            return "info";
        }
        return contests.addUserContest(cid, u.getUsername(), statu);
    }
    public static Permission getPermission(String user){
        return users.getPermission(user);
    }
    public static String getURL(String URL){
        return Jsoup.connect(URL).toString();
    }
    public static String getCode(int rid){
        return status.getStatu(rid).getCode();
    }

    public static String TimeToString(long t){
        long s=t/1000%60;
        long m=t/60000%60;
        long h=t/3600000%24;
        long d=t/(3600000*24);
        String ret="";
        if(d>0) ret+=d+"days ";
        ret+=h+":"+m+":"+s;
        return ret;
    }
    public static int sleep(int t){
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            return 1;
        }
        return 1;
    }
    public static void log(String s){
        System.out.println(Main.now()+"-> "+s);
    }
    public static String nowDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }
    public static Timestamp now(){
        return new Timestamp(System.currentTimeMillis());
    }
    public static Timestamp getTimestamp(String d,String s,String m){
        //System.out.println(d + " " + s + ":" + m + ":00");
        return Timestamp.valueOf(d + " " + s + ":" + m + ":00");
    }
    public static String uploadFile(File upload,String path) throws IOException {
        InputStream is=new FileInputStream(upload);
        OutputStream os=new FileOutputStream(path);
        byte buffer[] = new byte[1024];
        int count=0;
        while((count=is.read(buffer))>0){
            os.write(buffer,0,count);
        }
        os.close();
        is.close();
        return "success";
    }
    public static HttpSession getSession(){
        return ServletActionContext.getRequest().getSession();
    }
    public static PrintWriter getOut(){
        try {
            return ServletActionContext.getResponse().getWriter();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void saveURL(){
        String url=getRequest().getRequestURI();
        if(getRequest().getQueryString()!=null){
            url+="?"+getRequest().getQueryString();
        }
        getSession().setAttribute("url",url);
    }
    public static void saveURL(String s){
        getSession().setAttribute("url",s);
    }
    public static HttpServletRequest getRequest(){
        return ServletActionContext.getRequest();
    }
    public static Permission loginUserPermission(){
        User u=loginUser();
        if(u==null) return new Permission();
        else return getPermission(u.getUsername());
    }
    public static User loginUser(){
        return (User)getSession().getAttribute("user");
    }
    public static boolean canInContest(int cid){
        Contest c=Main.contests.getContest(cid);
        //User u=(User)getSession().getAttribute("user");
        User u=loginUser();
        if(loginUserPermission().getAddContest()) return true;
        if(u==null) return false;
        int z=c.canin(u.getUsername());
        if(z==1) return true;
        if(z==-1){//need password
            Object pass=getSession().getAttribute("contestpass"+cid);
            if(pass!=null && pass.toString().equals(Main.contests.getContest(cid).getPassword())) {//密码正确
                return true;
            }
        }
        return false;
    }
    public static boolean canShowProblem(int cid){
        if(!canInContest(cid)) return false;
        if(loginUserPermission().getShowHideProblem()) return true;
        Contest c=Main.contests.getContest(cid);
        return c.isBegin();
    }
    public static boolean canViewCode(statu s,String user){
        if(user==null) return false;
        return user.equals(s.getUser())||Main.users.haveViewCode(user,s.getPid())||getPermission(user).getViewCode();
    }
    public static String getIP(){
        return getRequest().getRemoteAddr();
    }

    public static double codeCmp(String code1,String code2){
        CPlusPlusCompare cmp = new CPlusPlusCompare();
        return cmp.getSimilarity(code1,code2);
    }
}
