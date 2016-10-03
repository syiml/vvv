package entity;

import net.sf.json.JSONObject;
import servise.ContestMain;
import util.JSON.JSON;
import util.Main;
import util.HTML.HTML;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Status implements IBeanResultSetCreate<Status> , ICanToJSON{
    private static Result[] r={
            Result.PENDING, Result.AC,      Result.WA,
            Result.CE,      Result.RE,      Result.TLE,
            Result.MLE,     Result.OLE,     Result.PE,
            Result.DANGER,  Result.RUNNING, Result.ERROR,
            Result.JUDGING
    };
    private String user;
    private int rid;//runID
    private int pid;
    private int cid;//如果是-1表示全局，否则表示contestID，对应的pid也表示为contest内的pid
    private int language;
    private String Code;
    private int codelen;
    private Timestamp SubmitTime;
    private Result result;
    private int score = -1;//得分 -1 表示不计分
    private String TimeUsed;
    private String MemoryUsed;
    //int user;
    public Status(int rid, String user, int pid, int cid, int language, String code, Timestamp time){
        this.rid = rid;
        this.user = user;
        this.pid = pid;//local pid
        this.cid = cid;
        this.language = language;
        this.Code = code;
        this.codelen = Code.replaceAll(" ","").replaceAll("\n","").replaceAll("\r","").replaceAll("\t","").length();
        this.SubmitTime = time;
        this.result = Result.PENDING;
        this.TimeUsed = "-";
        this.MemoryUsed = "-";
    }
    public Status(ResultSet r, int z){
        //id,ruser,pid,cid,lang,submittime,result,timeused,memoryUsed,code,codelen
        try {
            this.rid = r.getInt("id");
            this.user = r.getString("ruser");
            this.pid = r.getInt("pid");//local pid
            this.cid = r.getInt("cid");
            this.language = r.getInt("lang");
            this.SubmitTime = r.getTimestamp("submittime");
            this.result = intToResult(r.getInt("result"));
            this.score = r.getInt("score");
            this.TimeUsed = r.getString("timeused");
            this.MemoryUsed = r.getString("memoryUsed");
            if(z>=10) this.Code = r.getString("code");
            else this.Code = "";
            this.codelen = r.getInt("codelen");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Status(){}

    public static Result  intToResult(int i){
        return r[i];
    }

    public static int resultToInt(Result rr){
        return rr.getValue();
    }

    public int getPid() { return pid; }

    public int getRid() { return rid; }

    public void setRid(int rid) { this.rid=rid;}

    public int getLanguage() { return language; }

    public String getCode() {  return Code; }

    @Override
    public Status init(ResultSet rs) throws SQLException {
        rid = rs.getInt("id");
        user = rs.getString("ruser");
        pid = rs.getInt("pid");
        cid = rs.getInt("cid");
        language = rs.getInt("lang");
        SubmitTime = rs.getTimestamp("submittime");
        result = intToResult(rs.getInt("result"));
        score = rs.getInt("score");
        TimeUsed = rs.getString("timeused");
        MemoryUsed = rs.getString("memoryUsed");
        codelen = rs.getInt("codelen");
        return this;
    }

    public int getCodelen(){return codelen;}

    public void setStatusResult(Result res,String time,String Memory){
        result=res;
        TimeUsed=time;
        MemoryUsed=Memory;
        if(cid!=-1){
            Contest c= ContestMain.getContest(cid);
            c.getRank().add(Main.status.getStatu(rid),c);//通知更新排行榜
        }
    }

    public int getCid(){
        return cid;
    }

    public void setCid(int cid){
        this.cid=cid;
    }

    public Result getResult(){
        return result;
    }

    public String getResultString(){
        Result s=result;
        if(s==Result.AC) return "Accepted";
        if(s==Result.CE) return "Compilation Error";
        if(s==Result.DANGER) return "System Error";
        if(s==Result.MLE) return "Memory Limit Exceeded";
        if(s==Result.OLE) return"Output Limit Exceeded";
        if(s==Result.PE) return "Presentation Error";
        if(s==Result.PENDING) return "Pendding...";
        if(s==Result.JUDGING) return "Judging...";
        if(s==Result.RE) return "Runtime Error";
        if(s==Result.RUNNING) return "Running...";
        if(s==Result.TLE) return "Time Limit Exceeded";
        if(s==Result.WA) return "Wrong Answer";
        if(s==Result.ERROR) return "Submit Error";
        return "System Error";
    }

    public String getTimeUsed(){  return TimeUsed; }

    public String getMemoryUsed(){  return MemoryUsed; }

    public String getUser(){return user;}

    public Timestamp getSbmitTime(){return SubmitTime;}

    public Problem getProblem(){
        if(cid==-1)
            return Main.problems.getProblem(pid);
        else{
            return ContestMain.getContest(cid).getProblem(pid);
        }
    }

    public int getContestPid(){
        return ContestMain.getContest(cid).getcpid(pid);
    }

    public String resultToHTML(Result s){
        if(s==Result.AC) return HTML.span("success", "Accepted");
        if(s==Result.CE) return HTML.a("CEinfo.jsp?rid=" + rid, HTML.span("warning", "Compilation Error"));
        if(s==Result.DANGER) return HTML.span("info", "System Error");
        if(s==Result.MLE) return HTML.span("danger","Memory Limit Exceeded");
        if(s==Result.OLE) return HTML.span("danger","Output Limit Exceeded");
        if(s==Result.PE) return HTML.span("warning","Presentation Error");
        if(s==Result.PENDING) return HTML.span("default","Pendding...");
        if(s==Result.JUDGING) return HTML.span("default","Judging...");
        if(s==Result.RE) return HTML.span("danger","Runtime Error");
        if(s==Result.RUNNING) return HTML.span("default","Running...");
        if(s==Result.TLE) return HTML.span("danger","Time Limit Exceeded");
        if(s==Result.WA) return HTML.span("danger","Wrong Answer");
        if(s==Result.ERROR) return HTML.span("info","Submit Error");
        return HTML.span("primary","System Error");
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("rid",rid);
        jo.put("username",user);
        jo.put("pid",pid);
        jo.put("cid",cid);
        jo.put("lang",language);
        jo.put("submitTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getSbmitTime()));
        jo.put("result",result.value);
        jo.put("score",score);
        jo.put("timeUsed",TimeUsed);
        jo.put("memoryUsed",MemoryUsed);
        jo.put("codeLength",codelen);
        return jo;
    }
}
