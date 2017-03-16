package action;

import entity.OJ.JudgeSystem.JudgeSystem;
import entity.RES;
import entity.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.JSON.JSON;
import util.Submitter;
import util.Tool;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by syimlzhu on 2017/1/17.
 */
public class JudgeSystemReturn extends BaseAction{
    public int pid;
    public int rid;
    public String result;

    public String judgeDone(){
        Tool.debug("judge done ->pid="+pid+"&rid="+rid+"&result: "+result);
        ((JudgeSystem)Submitter.ojs[7]).setResult(result);
        synchronized (((JudgeSystem)Submitter.ojs[7]).lock) {
            ((JudgeSystem) Submitter.ojs[7]).lock.notify();
        }
        out.print("{\"ret\":\"success\"}");
        return NONE;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
