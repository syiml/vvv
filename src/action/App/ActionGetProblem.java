package action.App;

import action.BaseAction;
import util.JSON.AppJson;

/**
 * Created by QAQ on 2016/9/23.
 */
public class ActionGetProblem extends BaseAction{
    private int pid;
    public String getProblem(){
        out.print(AppJson.getProblem(pid).toString());
        return NONE;
    }

    public int getPid() {

        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

}
