package entity;

import servise.ContestMain;
import util.Main;
import entity.rank.Rank;
import entity.rank.RankSQL;
import util.HTML.HTML;
import util.SQL.SQL;
import util.Tool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2015/5/22.
 */
public class Contest implements IBeanResultSetCreate<Contest>, IBeanCanCatch {
    public static String TRUE_USERNAME = "trueusername";
    public static int typeNum=Contest_Type.SIZE.getCode();
    private int cid;
    private String name;
    private Timestamp begintime;
    private Timestamp endtime;
    private List<Integer> problems;
    private Contest_Type type;//修改时要改addcontest
    private String password;//if type is 1
    private List<RegisterUser> users;//if type is 2
    private Timestamp registerstarttime;//if type is 3 or 4
    private Timestamp registerendtime;//if type is 3 or 4
    private int rankType;
    private Rank rank;
    private String info;
    private boolean computerating;//是否计算rating只影响save链接的显示
    private int kind;//练习、积分、趣味、正式、隐藏
    private int group;//分类  //专题、积分、趣味、正式  -1隐藏
                             //练习:里面可以编辑题目标签
    private String createuser;//创建人。创建人和超级管理员可以修改其配置

    private boolean problemCanPutTag = false;//内部题目是否可以直接贴标签
    private boolean statusReadOut = false;   //计算排名时，是否把全局的提交也计算入内
    private boolean registerShowComplete = false;//注册是否需要完整的个人信息

    private Timestamp t;

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
            type=Contest_Type.getByCode(re.getInt(6));
            kind=re.getInt("kind");
            createuser = re.getString("createuser");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Contest(){}
    public Contest(ResultSet re,ResultSet ps) throws SQLException {
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
            type=Contest_Type.getByCode(re.getInt("ctype"));
            password=re.getString("password");
            registerstarttime=re.getTimestamp("registerstarttime");
            registerendtime=re.getTimestamp("registerendtime");
            info=re.getString("info");
            computerating=re.getBoolean("computerating");
            kind=re.getInt("kind");
            createuser = re.getString("createuser");
            problemCanPutTag=re.getBoolean("problemCanPutTag");
            statusReadOut=re.getBoolean("statusReadOut");
            registerShowComplete=re.getBoolean("registerShowComplete");
            //获取题目列表
            problems=new ArrayList<Integer>();
            while(ps.next()){
                problems.add(ps.getInt(2));
            }
            //读取user列表
            ResultSet ru;
            SQL sql4;
            if(getType() == Contest_Type.TEAM_OFFICIAL) {
                sql4 = new SQL("select * from t_register_team where cid = ?",cid);
                ru = sql4.query();
            }else{
                sql4 = new SQL("select * from contestuser where cid=?",cid);
                ru=sql4.query();
            }
            setUsers(ru);

            ResultSet rs;
            if(!isStatusReadOut()) {
                rs=new SQL("select id,ruser,pid,cid,lang,submittime,result,score,timeused,memoryUsed,codelen from statu where cid=?",cid).query();
            }else{
                rs=new SQL("SELECT id,ruser,statu.pid,statu.cid,lang,submittime,result,score,timeused,memoryUsed,codelen" +
                        " FROM statu LEFT JOIN contestproblems" +
                        " ON contestproblems.cid=? AND tpid=statu.pid" +
                        " WHERE contestproblems.cid=? ",cid,cid).query();
            }
            //创建Rank
            rank=RankSQL.getRank(this,rs);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("没有result");
        }
    }

    public static String getTypeText(int type){
        if(type==0){
            return "公开";
        }else if(type==1){
            return "密码";
        }else if(type==2){
            return "私有";
        }else if(type==3){
            return "注册";
        }else if(type==4){
            return "正式";
        }else if(type==5){
            return "组队";
        }else{
            return "ERROR";
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
        }else if(type==5) {
            return "style='color:#FF00FF'";
        }else {
            return "";
        }
    }

    private static String randomPassword(int len){
        String rad = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
        String ret="";
        for(int i=0;i<len;i++){
            ret+=rad.charAt((int)(Math.random()*rad.length()));
        }
        return ret;
    }

    @Override
    public Contest init(ResultSet rs) throws SQLException {
        cid=rs.getInt(1);
        name=rs.getString(2);
        begintime=rs.getTimestamp(3);
        endtime=rs.getTimestamp(4);
        rankType=rs.getInt(5);
        type=Contest_Type.getByCode(rs.getInt(6));
        kind=rs.getInt("kind");
        createuser = rs.getString("createuser");
        problemCanPutTag=rs.getBoolean("problemCanPutTag");
        statusReadOut=rs.getBoolean("statusReadOut");
        registerShowComplete=rs.getBoolean("registerShowComplete");
        return this;
    }

    public void setUsers(ResultSet us) throws SQLException {
        users = new ArrayList<RegisterUser>();
        if(this.getType() == Contest_Type.TEAM_OFFICIAL){
            while (us.next()) {
                users.add(new RegisterTeam(us));
            }
        }else {
            while (us.next()) {
                users.add(new RegisterUser(us));
            }
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

    public RegisterUser getRegisterUser(String username){
        for (RegisterUser user : users) {
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

    public boolean isRegistered(String username){
        for(RegisterUser registerUser : users){
            if(registerUser.getUsername().equals(username)) return true;
        }
        return false;
    }

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
        return getBeginDate().before(Tool.now());
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

    public Contest_Type getType(){
        return type;
    }

    public int getKind(){
        return kind;
    }

    public String getTypeHTML(){
        return type.getHTML(cid);
    }

    public String getStatuHTML(){
        Timestamp now=new Timestamp(System.currentTimeMillis());
        if(begintime.after(now)){
            return "<b style='color:green'>未开始</b>";//PENDING
        }else if(endtime.before(now)){
            return "<b>已结束</b>";//END
        }else{
            return HTML.a("match/?cid="+cid,"<b style='color:red'>可观战</b>");//RUNNING
        }
    }

    public int canin(User user){//判断用户是否有权限进入比赛
        if(type==Contest_Type.TEAM_OFFICIAL){
            String username = (String)Main.getSession().getAttribute("contestusername"+cid);
            String pass = (String)Main.getSession().getAttribute("contestpass"+cid);
            //Tool.log(username+" "+pass);
            if (username!=null && pass!=null && team_canin(username,pass)){
                return 1;
            }else {
                //判断管理员
                User loginUser = Main.loginUser();
                if(loginUser!=null){
                    for(RegisterUser u:users){
                        if(u.getStatu() == RegisterUser.STATUS_ADMIN
                                && u.getUsername().equals(loginUser.getUsername())){
                            Main.getSession().setAttribute(TRUE_USERNAME + getCid(), loginUser.getUsername());
                            return 1;
                        }
                    }
                }
                return -2;//password 需要密码
            }
        }
        if(user == null) return 0;
        if(type==Contest_Type.PUBLIC) return 1;//public 可以进入
        if(type==Contest_Type.PASSWORD){
            String pass = (String)Main.getSession().getAttribute("contestpass"+cid);
            if (pass!=null && pass.equals(getPassword())){
                return 1;
            }else {
                return -1;//password 需要密码
            }
        }
        if(type==Contest_Type.PRIVATE||type==Contest_Type.REGISTER||type==Contest_Type.REGISTER2){//需要注册
            for (RegisterUser u : users) {
                if (u.getUsername().equals(user.getUsername())) {
                    int statu = u.getStatu();
                    if (statu == RegisterUser.STATUS_APPENDED
                            || statu == RegisterUser.STATUS_UNOFFICIAL
                            || statu == RegisterUser.STATUS_TEAM_AUTO) {//是已经签到或者星号或者是集训队员自动报名状态，可以进入
                        return 1;
                    }
                    if (statu == RegisterUser.STATUS_ACCEPTED){
                        if(isBegin()&&!isEnd()){//是第一次进入，改成已经签到
                            ContestMain.setUserContest(cid,u.getUsername(),RegisterUser.STATUS_APPENDED,"");
                        }
                        return 1;
                    }
                    if(statu == RegisterUser.STATUS_ADMIN){
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

    public boolean team_canin(String username,String password){
        if(getType()!=Contest_Type.TEAM_OFFICIAL) return false;
        for(RegisterUser ru: users){
            RegisterTeam rt = (RegisterTeam)ru;
            if(rt.teamUserName==null) continue;
            if(rt.teamUserName.equals(username)){
                if(rt.teamPassword.equals(password)){
                    Main.getSession().setAttribute(TRUE_USERNAME + getCid(), rt.getUsername());
                    int statu = ru.getStatu();
                    if (statu == RegisterUser.STATUS_APPENDED || statu == RegisterUser.STATUS_UNOFFICIAL) {//是已经签到或者星号状态，可以进入
                        return true;
                    }
                    if (statu == RegisterUser.STATUS_ACCEPTED){
                        if(isBegin()&&!isEnd()){//是第一次进入，改成已经签到
                            ContestMain.setUserContest(cid,ru.getUsername(),RegisterUser.STATUS_APPENDED,"");
                        }
                        return true;
                    }
                    return false;
                }else{
                    return false;
                }
            }
        }
        return false;
    }

    public void computeUsernamePassword(String prefix){
        List<RegisterTeam> list = new ArrayList<>();
        for(RegisterUser ru:users){
            /*if(ru.getStatus() == RegisterUser.STATUS_ACCEPTED
                    ||ru.getStatus() == RegisterUser.STATUS_UNOFFICIAL){
            }*/
            list.add((RegisterTeam)ru);
        }
        Collections.shuffle(list);
        int number=1;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getStatu() == RegisterUser.STATUS_ACCEPTED
                    ||list.get(i).getStatu() == RegisterUser.STATUS_UNOFFICIAL
                    ||list.get(i).getStatu() == RegisterUser.STATUS_APPENDED) {
                list.get(i).teamUserName = String.format("%s%03d", prefix, number);
                list.get(i).teamPassword = randomPassword(6);
                number++;
            }else{
                list.get(i).teamUserName = null;
                list.get(i).teamPassword = null;
            }
            ContestMain.updateRegisterTeamPassword(cid, list.get(i));
        }
    }

    public void computeOneUsernamePassword(String username){
        String prefix = null;
        for(RegisterUser ru:users){
            RegisterTeam rt=((RegisterTeam)ru);
            if(rt.teamUserName!=null&&!rt.teamUserName.equals("")){
                prefix =  rt.teamUserName.substring(0,rt.teamUserName.length()-3);
                break;
            }
        }
        int number;
        if(prefix==null){
            number = 1;
            prefix = "team";
        }else {
            String maxusername = ContestMain.getMaxTeamUsername(cid);
            number = Integer.parseInt(maxusername.substring(maxusername.length()-3))+1;
        }
        ContestMain.updateRegisterTeamPassword(cid,username,String.format("%s%03d", prefix, number),randomPassword(6));
    }

    public boolean isProblemCanPutTag() {
        return problemCanPutTag;
    }

    public void setProblemCanPutTag(boolean problemCanPutTag) {
        this.problemCanPutTag = problemCanPutTag;
    }

    public boolean isStatusReadOut() {
        return statusReadOut;
    }

    public void setStatusReadOut(boolean statusReadOut) {
        this.statusReadOut = statusReadOut;
    }

    public boolean isRegisterShowComplete() {
        return registerShowComplete;
    }

    public void setRegisterShowComplete(boolean registerShowComplete) {
        this.registerShowComplete = registerShowComplete;
    }

    @Override
    public Timestamp getExpired() {
        return t;
    }

    @Override
    public void setExpired(Timestamp t) {
        this.t = t;
    }
}
