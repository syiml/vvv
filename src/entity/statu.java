package entity;

import servise.ContestMain;
import util.Main;
import util.HTML.HTML;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class statu {
    public int getPid() { return pid; }
    public int getRid() { return rid; }
    public int getLanguage() { return language; }
    public String getCode() {  return Code; }

    private String user;
    private int rid;//runID
    private int pid;
    private int cid;//如果是-1表示全局，否则表示contestID，对应的pid也表示为contest内的pid
    private int language;
    private String Code;
    private int codelen;
    private Timestamp SubmitTime;
    private Result result;
    private String TimeUsed;
    private String MemoryUsed;
    //int user;
    public statu(int rid,String user,int pid,int cid,int language,String code,Timestamp time){
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
    public statu(ResultSet r,int z){
        //id,ruser,pid,cid,lang,submittime,result,timeused,memoryUsed,code,codelen
        try {
            this.rid = r.getInt("id");
            this.user = r.getString("ruser");
            this.pid = r.getInt("pid");//local pid
            this.cid = r.getInt("cid");
            this.language = r.getInt("lang");
            this.SubmitTime = r.getTimestamp("submittime");
            this.result = intToResult(r.getInt("result"));
            this.TimeUsed = r.getString("timeused");
            this.MemoryUsed = r.getString("memoryUsed");
            if(z>=10) this.Code = r.getString("code");
            else this.Code = "";
            this.codelen = r.getInt("codelen");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        if(cid==-1)
            return -1;
        else{
            return ContestMain.getContest(cid).getcpid(pid);
        }
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
    private static Result[] r={
            Result.PENDING, Result.AC,      Result.WA,
            Result.CE,      Result.RE,      Result.TLE,
            Result.MLE,     Result.OLE,     Result.PE,
            Result.DANGER,  Result.RUNNING, Result.ERROR,
            Result.JUDGING
    };
    public static Result  intToResult(int i){
        return r[i];
    }
    public static int resultToInt(Result rr){
        return rr.getValue();
    }
}
