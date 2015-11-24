package action;

import util.Main;

/**
 * Created by Syiml on 2015/6/15 0015.
 */
public class rejudge {
    String rid;
    public String getRid(){return rid;}
    public void setRid(String rid){this.rid=rid;}
    public String rej(){
        if(!Main.loginUserPermission().getReJudge()) return "error";
        Main.rejudge(Integer.parseInt(rid));
        return "success";
    }
}
