package action;

import util.Main;

/**
 * Created by Syiml on 2015/7/4 0004.
 */

public class RegisterContest {
    public String cid;

    public void setCid(String cid) {
        this.cid = cid;
    }
    public String getCid() {
        return cid;
    }

    public String regCon(){
        int cidInt=Integer.parseInt(cid);
        return Main.registerContest(cidInt);
    }
}
