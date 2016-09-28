package entity.rank.RankNOIP;

import entity.Contest;
import entity.Result;
import entity.Status;
import entity.rank.RankBaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QAQ on 2016/9/27.
 */
public class user extends RankBaseUser{
    int pnum;
    int totalScore = 0;
    List<Integer> scores;
    List<Integer> submitNum;

    public user(){}
    public void init(String username,int val,int pnum){
        super.init(username,val,pnum);
        this.username = username;
        this.valid = val;
        this.pnum = pnum;
        scores = new ArrayList<>(pnum);
        submitNum = new ArrayList<>(pnum);
        for(int i=0;i<pnum;i++){
            scores.add(0);
            submitNum.add(0);
        }
    }
    void add(Status s, Contest c){
        int cpid = c.getcpid(s.getPid());
        if(cpid <0 || cpid>=pnum) return ;
        submitNum.set(cpid,submitNum.get(cpid)+1);
        int score = s.getScore();
        if(score == -1){
            if(s.getResult() == Result.AC)
                score = 100;
            else score = 0;
        }
        if(score <= scores.get(cpid)) return ;
        totalScore -= scores.get(cpid);
        scores.set(cpid,score);
        totalScore += score;
    }

    @Override
    public int compareTo(RankBaseUser o) {
        return ((user)o).totalScore - totalScore ;
    }

    @Override
    protected boolean noRank() {
        return totalScore == 0;
    }

}
