package util;

import WebSocket.MatchServer;
import entity.Contest;
import entity.Result;
import entity.statu;
import util.Vjudge.SubmitInfo;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2015/12/14 0014.
 */
public class SubmitterImp implements Submitter{

    @Override
    public int doSubmit(String user, int pid, int cid, int language, String code, Timestamp submittime) {Tool.log("Main.doSubmit");
        Tool.debug("cid="+cid+" pid="+pid);
        int rid;
        if(cid!=-1){//验证user是否有权限提交
            if(Main.contests.getContest(cid).getEndTime().before(submittime)){
                return -1;//超出比赛结束时间
            }
        }
        int ppid=pid;
        if(cid!=-1) pid=Main.contests.getContest(cid).getGlobalPid(pid);//等于全局题目编号
        Tool.debug("go=" + pid);
        statu s=new statu(0,user,pid,cid,language,code,submittime);
        rid = Main.status.addStatu(s);//插入数据库，并获取rid

        if(!Main.problems.isProblemLocal(pid)){//is vj
            SubmitInfo ss=new SubmitInfo(rid,Main.problems.getOjspid(pid),language,code,false);
            submitVJ(ss, Main.problems.getOJid(pid));
        }else{//is local
            SubmitInfo ss=new SubmitInfo(rid,pid+"",language,code,false);
            m.addSubmit(ss);
        }

        if(cid!=-1) {//提交发送到观战模式
            try{
                Contest c=Main.contests.getContest(s.getCid());
                MatchServer.sendStatus(cid, rid, ppid, user, -1, (submittime.getTime()-c.getBeginDate().getTime())/1000);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        if(!Main.problems.isProblemLocal(pid)) {//is vj
            return 0;//success submit to vj
        }
        return 1;//success submit to local
    }

    @Override
    public void onSubmitDone(statu s) {
        if(s.getCid()!=-1){
            Contest c=Main.contests.getContest(s.getCid());
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
        statu s=Main.status.getStatu(rid);
        int pid=s.getPid();
        if(!Main.problems.isProblemLocal(pid)){//is vj
            Main.status.setStatusResult(rid, Result.PENDING,"-","-",null);
            SubmitInfo ss=new SubmitInfo(rid,Main.problems.getOjspid(pid),s.getLanguage(),s.getCode(),true);
            submitVJ(ss, Main.problems.getOJid(pid));
            Tool.debug("Main.ReJudge Done");
            return 0;//success submit to vj
        }else{//is local
            Main.status.setStatusResult(rid, Result.PENDING,"-","-",null);
            SubmitInfo ss=new SubmitInfo(rid,pid+"",s.getLanguage(),s.getCode(),true);
            m.addSubmit(ss);
            Tool.debug("Main.ReJudge Done");
            return 1;//success submit to local
        }
    }

    private int submitVJ(SubmitInfo info,int oj){
        //System.out.println("Main.submitVJ");
        m.addSubmit(info, oj);
        return 1;
    }
}
