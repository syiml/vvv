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

    private static Map<String,Result> resultmap = new HashMap<>();
    static{
        resultmap.put("AC",Result.AC);
        resultmap.put("TLE",Result.TLE);
        resultmap.put("MLE",Result.MLE);
        resultmap.put("RE",Result.RE);
        resultmap.put("OLE",Result.OLE);
        resultmap.put("WA",Result.WA);
        resultmap.put("PE",Result.PE);
    }
    public String judgeDone(){
        Tool.debug("judge done ->pid="+pid+"&rid="+rid+"&result: "+result);
        RES res = ((JudgeSystem)Submitter.ojs[7]).res;
        JSONObject resultJson = JSONObject.fromObject(result);
        if(resultJson.getString("type").equals("CE")){
            res.setR(Result.CE);
            res.setCEInfo(resultJson.getString("info"));
            res.setTime("-");
            res.setMemory("-");
        }else {
            int time = 0;
            int memory = 0;
            Result result;
            JSONArray retJson = resultJson.getJSONArray("ret");
            result = Result.ERROR;
            for (int i = 0; i < retJson.size(); i++) {
                result = resultmap.get(retJson.getJSONArray(i).getString(1));
                time += retJson.getJSONArray(i).getInt(2);
                memory = Math.max(memory, retJson.getJSONArray(i).getInt(3));
                if (result != Result.AC) {
                    break;
                }
            }
            res.setR(result);
            res.setTime(time + "MS");
            res.setMemory(memory + "KB");
        }
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
