package action;

import servise.ContestMain;
import util.Main;
import entity.Contest;
import util.Tool;
import util.rating._rank;

import java.util.List;

/**
 * Created by Syiml on 2015/11/7 0007.
 */
public class ContestAward {
    public void setCid(int cid) {
        this.cid = cid;
    }
    public void setRank(List<Integer> rank) {
        this.rank = rank;
    }
    public void setAcb(List<Integer> acb) {
        this.acb = acb;
    }

    public int getCid() {
        return cid;
    }
    public List<Integer> getRank() {
        return rank;
    }
    public List<Integer> getAcb() {
        return acb;
    }

    int cid;
    List<Integer> rank;
    List<Integer> acb;

    public String award(){
        Tool.debug("award: cid="+cid);
        Tool.debug("award: size="+rank.size());
        Contest c= ContestMain.getContest(cid);
        _rank r=c.getRank().get_rank();
        for(int i=0;i<r.size();i++){
            int j;
            for(j=0;j<rank.size();j++){
                if(r.getRank(i)<=rank.get(j)) break;
            }
            if(j<acb.size())
                Main.users.awardACB(r.getUsername(i),acb.get(j),"由于在比赛【"+c.getName()+"】中获得了第【"+r.getRank(i)+"】名，获得了【"+acb.get(j)+"】ACB奖励。");
        }
        return "success";
    }
}
