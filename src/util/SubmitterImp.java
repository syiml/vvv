package util;

import WebSocket.MatchServer;
import entity.Contest;
import entity.Problem;
import entity.Result;
import entity.Status;
import servise.ContestMain;
import util.Event.EventMain;
import util.Event.Events.EventStatusAdd;
import util.Vjudge.SubmitInfo;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Administrator on 2015/12/14 0014.
 */
public class SubmitterImp implements Submitter{

    @Override
    public int doSubmit(String user, int pid, int cid, int language, String code, Timestamp submittime) {
        int rid;
        if(cid!=-1){//验证user是否有权限提交
            if(ContestMain.getContest(cid).getEndTime().before(submittime)){
                return -1;//超出比赛结束时间
            }
        }
        int ppid=pid;
        if(cid!=-1) pid=ContestMain.getContest(cid).getGlobalPid(pid);//等于全局题目编号
        Status s=new Status(0,user,pid,cid,language,code,submittime);
        rid = Main.status.addStatu(s);//插入数据库，并获取rid
        s.setRid(rid);
        Problem p = Main.problems.getProblem(pid);
        if(p.getType()!=Problem.LOCAL){//is vj
            SubmitInfo ss=new SubmitInfo(rid,p.getOjspid(),language,code,false);
            submitVJ(ss, Main.problems.getOJid(pid));
        }else{//is local
            SubmitInfo ss=new SubmitInfo(rid,pid+"",language,code,false);
            m.addSubmit(ss);
        }

        EventMain.triggerEvent(new EventStatusAdd(s));
        if(cid!=-1) {//提交发送到观战模式
            try{
                Contest c=ContestMain.getContest(s.getCid());
                MatchServer.sendStatus(cid, rid, ppid, user, -1, (submittime.getTime()-c.getBeginDate().getTime())/1000);
            }catch(Exception e){
                Tool.log(e);
            }
        }
        if(!Main.problems.isProblemLocal(pid)) {//is vj
            return 0;//success submit to vj
        }
        return 1;//success submit to local
    }

    @Override
    public void onSubmitDone(Status s) {
        if(s.getCid()!=-1){
            Contest c=ContestMain.getContest(s.getCid());
            int ppid=c.getcpid(s.getPid());
            if(s.getResult()== Result.AC){
                MatchServer.sendStatus(s.getCid(),s.getRid(),ppid,s.getUser(),1,(s.getSbmitTime().getTime()-c.getBeginDate().getTime())/1000);
            }else{
                MatchServer.sendStatus(s.getCid(),s.getRid(),ppid,s.getUser(),0,(s.getSbmitTime().getTime()-c.getBeginDate().getTime())/1000);
            }
        }
    }

    @Override
    public int reJudge(int rid) {
        Status s=Main.status.getStatu(rid);
        int pid=s.getPid();
        if(!Main.problems.isProblemLocal(pid)){//is vj
            Main.status.setStatusResult(rid, Result.PENDING,"-","-",null);
            SubmitInfo ss=new SubmitInfo(rid,Main.problems.getOjspid(pid),s.getLanguage(),s.getCode(),true);
            submitVJ(ss, Main.problems.getOJid(pid));
            return 0;//success submit to vj
        }else{//is local
            Main.status.setStatusResult(rid, Result.PENDING,"-","-",null);
            SubmitInfo ss=new SubmitInfo(rid,pid+"",s.getLanguage(),s.getCode(),true);
            m.addSubmit(ss);
            return 1;//success submit to local
        }
    }
    /**
     * 批量重判
     * @param pid 题目id
     * @param fromRid 开始rid，限制重判的范围
     * @param status status==1 表示 只重判ac代码，status==2 表示重判除了CE以外的所有代码，status==3表示全部，status==4表示重判所有padding和judging
     * @return
     */
    @Override
    public int reJudge(int pid, int fromRid , int status) {
        List<Integer> rids=Main.status.getRidsToRejudge(pid,fromRid,status);
        for(Integer rid:rids){
            reJudge(rid);
        }
        return 0;
    }
    private int submitVJ(SubmitInfo info,int oj){
        //System.out.println("Main.submitVJ");
        m.addSubmit(info, oj);
        return 1;
    }
}
