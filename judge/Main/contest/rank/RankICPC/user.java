package Main.contest.rank.RankICPC;

import Main.contest.Contest;
import Main.status.Result;
import Main.status.statu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/5/26.
 */
public class user implements Comparable<user>{
    String username;
    boolean valid;
    int submitnum;//解出题目的数量
    Long penalty;//罚时 单位毫秒
    List<Long> submittime;//解出第i题的时间  单位毫秒
    List<Integer> errortime;//第i题错误的次数
    public user(String user,boolean va,int pnum){
        valid=va;
        username=user;
        submitnum=0;
        penalty = 0L;
        submittime=new ArrayList<Long>(pnum);
        errortime=new ArrayList<Integer>(pnum);
        for(int i=0;i<pnum;i++){
            submittime.add(-1L);
            errortime.add(0);
        }
    }
    public void add(statu s,long time,int p,Contest c){
        int contestpid=c.getcpid(s.getPid());
        if(submittime.get(contestpid)==-1L){
             if(s.getResult() == Result.AC) {
                 submittime.set(contestpid, time);
                 submitnum+=1;
                 Integer et=errortime.get(contestpid);
                 penalty += time + et * p * 60 * 1000;
             }else{
                 Integer i = errortime.get(contestpid);
                 errortime.set(contestpid,i+1);
             }
        }
    }
    public String resultclass(int pid){
        if(submittime.get(pid)==-1){
            if(errortime.get(pid)!=0){
                return "danger";
            }else{
                return "";
            }
        }else{
            return "success";
        }
    }
    public String result(int pid){
        if(submittime.get(pid)==-1){
            if(errortime.get(pid)!=0){
                return errortime.get(pid)+"";
            }else{
                return "";
            }
        }else{
            if(errortime.get(pid)!=0){
                return submittime.get(pid)/1000/60+"(-"+errortime.get(pid)+")";
            }else{
                return submittime.get(pid)/1000/60+"";
            }
        }
    }
    public int compareTo(user u){
        int z=0;
        if(this.submitnum==u.submitnum){
            return (int)(this.penalty - u.penalty);
        }else{
            return u.submitnum-this.submitnum;
        }
    }
    public Long getSubmittime(int pid){
        return submittime.get(pid);
    }
    public int getSubmitnum(){
        return submitnum;
    }
    public long getPenalty(){
        return penalty;
    }
}
