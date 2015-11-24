package entity;

import util.Main;
import entity.rank.Rank;
import entity.rank.RankSQL;
import util.HTML.HTML;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/5/22.
 */
public class Contest {
    private int cid;
    private String name;
    private Timestamp begintime;
    private Timestamp endtime;
    //private ProblemSQL problems;
    private List<Integer> problems;
    //private statusSQL status;
    //private List<Integer> status;
    private int type;// 0public 1password 2private 3register 4register2(要审核)//修改时要改addcontest
    public static int typenum=5;
    private String password;//if type is 1
    private List<RegisterUser> users;//if type is 2
    private Timestamp registerstarttime;//if type is 3 or 4
    private Timestamp registerendtime;//if type is 3 or 4
    private int rankType;
    private Rank rank;
    private String info;
    private boolean computerating;
    private int kind;//练习、积分、趣味、正式
//    public ContestCodeCompare comparer=null;//代码重复率计算器
    private int group;//分类  //专题、积分、趣味、正式
                             //练习:里面可以编辑题目标签


    private String createuser;//创建人。创建人和超级管理员可以修改其配置
    public Contest(ResultSet re,ResultSet ps,ResultSet rs,ResultSet us){
        //re:   id,name,beginTime,endTime,rankType,ctype,password,registerstarttime,registerendtime
        //ps:   pid,tpid
        //rs:   id,ruser,pid,cid,lang,submittime,result,timeused,memoryUsed,code,codelen
        //us:   username,statu,info(0未审核 1通过 -1未通过)
        try {
            re.next();
            //获取基础信息
            cid=re.getInt("id");
            name=re.getString("name");
            begintime=re.getTimestamp("beginTime");
            endtime=re.getTimestamp("endTime");
            rankType=re.getInt("rankType");
            type=re.getInt("ctype");
            password=re.getString("password");
            registerstarttime=re.getTimestamp("registerstarttime");
            registerendtime=re.getTimestamp("registerendtime");
            info=re.getString("info");
            computerating=re.getBoolean("computerating");
            kind=re.getInt("kind");
            //获取题目列表
            problems=new ArrayList<Integer>();
            while(ps.next()){
                problems.add(ps.getInt(2));
            }
            //读取user列表
            setUsers(us);
            //创建Rank
            rank=RankSQL.getRank(this,rs);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("没有result");
        }
    }
    public void setUsers(ResultSet us) throws SQLException {
        users = new ArrayList<RegisterUser>();
        while(us.next()){
            users.add(new RegisterUser(us));
        }
    }
    public void reSetUsers(){
        Main.contests.deleteMapContest(cid);
//        try {
//            setUsers(Main.contests.getUser(cid));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
    public Contest(ResultSet re){
        //用ResultSet创建临时Contest变量
        //id,name,beignTime,endTime,rankType,ctype
        int rankType;
        try {
            cid=re.getInt(1);
            name=re.getString(2);
            begintime=re.getTimestamp(3);
            endtime=re.getTimestamp(4);
            rankType=re.getInt(5);
            type=re.getInt(6);
            kind=re.getInt("kind");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getCid(){return cid;}
    public int getRankType(){return rankType;}
    public Problem getProblem(int pid){
        return Main.problems.getProblem(problems.get(pid));
    }
    public int getGlobalPid(int pid) {
        return problems.get(pid);
    }
    public int getProblemNum(){
        return problems.size();
    }
    public RegisterUser getUser(int i){return users.get(i);}
    public int getUserNum(){ return users.size();}
    public String getName(){
        return name;
    }
    public Rank getRank(){return rank;}
    public String getBeginTimeString(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(begintime);
    }
    public String getEndTimeString(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(endtime);
    }
    public Timestamp getBeginDate(){return begintime;}
    public Timestamp getEndTime(){return endtime;}
    public Timestamp getRegisterendtime(){return registerendtime;}
    public Timestamp getRegisterstarttime(){return registerstarttime;}
    public String getProblemId(int pid){//pid->
        int x=problems.indexOf(pid);
        if(problems.size()>26) return 1+x+"";
        else return (char)(x+'A')+"";
    }
    public int getcpid(int tpid){
        return problems.indexOf(tpid);
    }
    public String getProblems(){
        String s="";
        for(int i=0;i<problems.size();i++){
            if(i!=0) s+=",";
            s+=problems.get(i);
        }
        return s;
    }
    public List<Integer> getProblemList(){
        return problems;
    }
    public String getInfo(){return  info;}
    public boolean isComputerating(){return computerating;}
    public boolean isBegin(){
        return getBeginDate().before(Main.now());
    }
    public boolean isPending(){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return getBeginDate().after(now);
    }
    public boolean isRunning(){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return getBeginDate().before(now)&&getEndTime().after(now);
    }
    public boolean isEnd(){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return getEndTime().before(now);
    }
    public String getRankHTML(){
        return rank.toHTML();
    }
    public String getTypeHTML(int type){
        if(type==0){
            return "<b style='color:green'>公开</b>";
        }else if(type==1){
            return "<b style='color:red'>密码</b>";
        }else if(type==2){
            return HTML.a("User.jsp?cid="+cid,"<b style='color:#AE57A4'>私有</b>");
        }else if(type==3){
            return HTML.a("User.jsp?cid="+cid,"<b style='color:blue'>注册</b>");
        }else if(type==4){
            return HTML.a("User.jsp?cid="+cid,"<b style='color:orange'>正式</b>");
        }else{
            return "";
        }
    }
    public static String getTypeText(int type){
        if(type==0){
            return "public";
        }else if(type==1){
            return "password";
        }else if(type==2){
            return "private";
        }else if(type==3){
            return "register";
        }else if(type==4){
            return "official";
        }else{
            return "";
        }
    }
    public static String getTypeStyle(int type){
        if(type==0){
            return "style='color:green'";
        }else if(type==1){
            return "style='color:red'";
        }else if(type==2){
            return "style='color:#AE57A4'";
        }else if(type==3){
            return "style='color:blue'";
        }else if(type==4){
            return "style='color:orange'";
        }else{
            return "";
        }
    }
    public String getTypeHTML(){
        return getTypeHTML(type);
    }
    public int getType(){
        return type;
    }
    public int getKind(){
        return kind;
    }
    public String getStatuHTML(){
        Timestamp now=new Timestamp(System.currentTimeMillis());
        if(begintime.after(now)){
            return "<b style='color:green'>未开始</b>";//PENDDING
        }else if(endtime.before(now)){
            return "<b>已结束</b>";//END
        }else{
            return "<b style='color:red'>进行中</b>";//RUNNING
        }
    }
    public int canin(String user){//判断用户是否有权限进入比赛
        if(type==0) return 1;//public 可以进入
        if(type==1) return -1;//password 需要密码
        if(type==2||type==3||type==4){//需要注册
            for (RegisterUser u : users) {
                if (u.getUsername().equals(user)) {
                    int statu = u.getStatu();
                    if (statu == 1 || statu == 2) {//是AC或者星号状态，可以进入
                        return 1;
                    }
                }
            }
            return 0;
        }
        return 0;
    }
    public String getPassword(){return password;}
    public String getHomeHTML(){
        String s="";
//        s+=HTML.floatLeft("<h3>Start Time:"+getBeginTimeString()+"</h3>");
//        s+=HTML.floatRight("<h3>End Time:"+getEndTimeString()+"</h3>");
//        s+=HTML.center("<h3>Start Time:" + getBeginTimeString() + "</h3>");
//        s+=HTML.center("<h3>End   Time:"+getEndTimeString()+"</h3>");
        s+=HTML.row(HTML.col(5,0,"text-right","<h3>Start Time:</h3>")+HTML.col(6, "<h3>"+getBeginTimeString()+"</h3>"));
        s+=HTML.row(HTML.col(5,0,"text-right","<h3>End Time:</h3>")+HTML.col(6, "<h3>"+getEndTimeString()+"</h3>"));
        s+=HTML.row(HTML.col(5,0,"text-right","<h3>Status:</h3>")+HTML.col(6, "<h3>"+getStatuHTML()+"</h3>"));
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if(getBeginDate().after(now)){
            long time=getBeginDate().getTime()-now.getTime();
//            long time=17*1000*3600;
            s+=HTML.row(
                    HTML.col(5,0,"text-right","<h3>Start At:</h3>")+
                    HTML.col(6,"<h3><div id='countdowner'>"+HTML.time_djs(time/1000,"djs")+"</div></h3>"));
        }
        return s;
    }
}
