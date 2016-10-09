package entity.rank.RankICPC;

import entity.Contest;
import entity.Result;
import entity.Status;
import entity.rank.RankBaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2015/5/26.
 */
public class user extends RankBaseUser{
    int submitnum;//解出题目的数量
    Long penalty;//罚时 单位毫秒
    List<Long> submittime;//解出第i题的时间  单位毫秒
    List<Integer> errortime;//第i题错误的次数

    public user(){}
    public void init(String user,int va,int pnum){
        super.init(user,va,pnum);
        submitnum=0;
        penalty = 0L;
        submittime=new ArrayList<Long>(pnum);
        errortime=new ArrayList<Integer>(pnum);
        for(int i=0;i<pnum;i++){
            submittime.add(-1L);
            errortime.add(0);
        }
    }

    public void add(Status s,long time,int p,Contest c){
        int contestpid=c.getcpid(s.getPid());
        if(contestpid==-1) return ;
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
    public Long getSubmittime(int pid){
        return submittime.get(pid);
    }
    public int getSubmitnum(){
        return submitnum;
    }
    public long getPenalty(){
        return penalty;
    }

    @Override
    public int compareTo(RankBaseUser o) {
        user u = (user)o;
        if(this.submitnum==u.submitnum){
            if(Objects.equals(this.penalty, u.penalty)) return 0;
            return (this.penalty - u.penalty)>0?1:-1;
        }else{
            return u.submitnum-this.submitnum;
        }
    }

    @Override
    protected boolean noRank() {
        return submitnum == 0;
    }

}
