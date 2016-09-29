package action;

import servise.ContestMain;
import util.Main;

/**
 * Created by Syiml on 2015/7/4 0004.
 */
public class setRegisterContest  extends BaseAction{
    String cid;
    String username;
    String statu;
    String info;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String set(){
        if(username == null || username.equals("")) return ERROR;
        if(Main.loginUserPermission().getContestRegisterAdmin()){
            return ContestMain.setUserContest(Integer.parseInt(cid),username,Integer.parseInt(statu),info);
        }
        return "success";
    }
}
