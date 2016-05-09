package action;

import entity.Contest;
import servise.ContestMain;
import util.Main;

/**
 * Created by Syiml on 2015/7/4 0004.
 */

public class RegisterContest {
    public String cid;
    public String prefix;
    public String username;
    public void setCid(String cid) {
        this.cid = cid;
    }
    public String getCid() {
        return cid;
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    public String getPrefix() {

        return prefix;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {

        return username;
    }

    public String regCon(){
        int cidInt=Integer.parseInt(cid);
        return ContestMain.registerContest(cidInt);
    }

    public String computeUsernamePassword(){
        int cidInt=Integer.parseInt(cid);
        Contest contest = ContestMain.getContest(cidInt);
        if(contest.getType() == Contest.TYPE_TEAM_OFFICIAL){
            contest.computeUsernamePassword(prefix);
            return "success";
        }
        return "success";
    }
    public String computeOneUseranemPassword(){
        int cidInt = Integer.parseInt(cid);
        Contest contest = ContestMain.getContest(cidInt);
        if(contest.getType() == Contest.TYPE_TEAM_OFFICIAL){
            contest.computeOneUsernamePassword(username);
        }
        return "success";
    }
}
