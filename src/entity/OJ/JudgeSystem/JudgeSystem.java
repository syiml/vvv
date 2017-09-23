package entity.OJ.JudgeSystem;

import entity.OJ.OTHOJ;
import entity.Problem;
import entity.RES;
import entity.Result;
import entity.Status;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.comparators.BooleanComparator;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import util.HTML.problemHTML;
import util.Main;
import util.MyClient;
import util.Pair;
import util.Tool;
import util.Vjudge.VjSubmitter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by syimlzhu on 2017/1/17.
 */
public class JudgeSystem extends OTHOJ {
    private String url= Main.GV.getJSONObject("judge_system").getString("URL");
    private MyClient hc = MyClient.getMyClient();
    public RES res = new RES();
    public final Boolean lock = false;
    public JudgeSystem(){

    }

    public JudgeSystem(String name){
        if(name.equals("judge_system_game")){
            url= Main.GV.getJSONObject("judge_system_game").getString("URL");
            this.name = "JudgeSystemGame";
        }
    }
    @Override
    public String getRid(String user, VjSubmitter s) {
        return s.rid;
    }

    @Override
    public problemHTML getProblemHTML(String pid) {
        problemHTML problemHTML = new problemHTML();
        problemHTML.setTimeLimit("1000MS");
        problemHTML.setMenoryLimit("128M");
        problemHTML.setInt64("%lld");
        return problemHTML;
    }

    @Override
    public String getTitle(String pid) {
        return "JudgeSystem不能获取题目";
    }

    @Override
    public String submit(VjSubmitter s) {
        Pair<Integer,Integer> limit=Main.problems.getProblemLimit(Integer.parseInt(s.getSubmitInfo().pid));
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("type","submit"));
        formparams.add(new BasicNameValuePair("pid",s.getSubmitInfo().pid));
        formparams.add(new BasicNameValuePair("rid",s.getSubmitInfo().rid+""));
        formparams.add(new BasicNameValuePair("timelimit",limit.getKey()+""));
        formparams.add(new BasicNameValuePair("memorylimit",limit.getValue()+""));
        formparams.add(new BasicNameValuePair("code",s.getSubmitInfo().code));
        formparams.add(new BasicNameValuePair("language",s.getSubmitInfo().language+""));

        JSONObject jo = new JSONObject();
        Status st = Main.status.getStatu(s.getSubmitInfo().getRid());
        jo.put("username",st.getUser());
        formparams.add(new BasicNameValuePair("judge_data",jo.toString()));

        synchronized (lock) {
            String ret = hc.Post(s.getUsername()+"/",formparams);
            s.rid = Math.random()+"";
            /*Tool.debug("wait begin");
            try {
                lock.wait();
            } catch (InterruptedException e) {
                Tool.debug("wait end");
            }*/
        }
        //if(ret == null) return "error";
        //else {
        return "success";
        //}
    }
    private static Map<String,Result> resultmap = new HashMap<>();
    static{
        resultmap.put("AC",Result.AC);
        resultmap.put("TLE",Result.TLE);
        resultmap.put("MLE",Result.MLE);
        resultmap.put("RE",Result.RE);
        resultmap.put("OLE",Result.OLE);
        resultmap.put("WA",Result.WA);
        resultmap.put("PE",Result.PE);
        resultmap.put("SC",Result.SCORE);
    }

    /**
     * 计算得分。
     * 计算模式：
     *      AC以100分计算
     *      SC以得分计算
     *      如果有非AC和非SC，返回对应的结果，得分-1
     *      最后得分为平均分
     *      全部都是100分，则返回AC，否则返回SC
     *
     * @param retJson 评测结果
     */
    public void computeScore_all_avg(JSONArray retJson){
        int total_score = 0;
        Result result_1;
        for (int i = 0; i < retJson.size(); i++) {
            result_1 = resultmap.get(retJson.getJSONArray(i).getString(1));
            if(result_1 == Result.AC){
                total_score += 100;
            }else if(result_1 == Result.SCORE){
                total_score += retJson.getJSONArray(i).getInt(5);
            }else{
                res.setR(result_1);
                res.setScore(-1);
                return;
            }
        }
        if(total_score == 100*retJson.size()){
            res.setR(Result.AC);
            res.setScore(100);
            return ;
        }
        total_score /= retJson.size();
        res.setR(Result.SCORE);
        res.setScore(total_score);
    }
    public void setResult(String result){
        JSONObject resultJson = JSONObject.fromObject(result);
        res.setScore(-1);
        if(resultJson.getString("type").equals("padding")){
            res.setR(Result.PENDING);
            res.setTime("-");
            res.setMemory("-");
        }else if(resultJson.getString("type").equals("judging")){
            res.setR(Result.JUDGING);
            res.setTime("-");
            res.setMemory("-");
        }else if(resultJson.getString("type").equals("CE")){
            res.setR(Result.CE);
            res.setCEInfo(resultJson.getString("info"));
            res.setTime("-");
            res.setMemory("-");
        }else if(resultJson.has("game") && resultJson.getBoolean("game")){
            res.setCEInfo(resultJson.toString());
            if(resultJson.getInt("score")>=100){
                res.setR(Result.AC);
            }else{
                res.setR(Result.WA);
            }
            res.setScore(resultJson.getInt("score"));
            res.setTime("0MS");
            res.setMemory("0MB");
        }else{
            int time = 0;
            int memory = 0;
            boolean has_score = false;
            Result result_1;
            JSONArray retJson = resultJson.getJSONArray("ret");
            result_1 = Result.ERROR;
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < retJson.size(); i++) {
                result_1 = resultmap.get(retJson.getJSONArray(i).getString(1));
                time += retJson.getJSONArray(i).getInt(2);
                memory = Math.max(memory, retJson.getJSONArray(i).getInt(3));
                if(retJson.getJSONArray(i).size() >= 6 && retJson.getJSONArray(i).getInt(5) !=-1){
                    has_score = true;
                }
                if (result_1 != Result.AC && result_1 != Result.SCORE) {
                    break;
                }
            }
            for (int i = 0; i < retJson.size(); i++) {
                result_1 = resultmap.get(retJson.getJSONArray(i).getString(1));
                stringBuilder.append("测试文件：").append(retJson.getJSONArray(i).getString(0));
                stringBuilder.append(" 测试结果：").append(retJson.getJSONArray(i).getString(1));
                if(result_1 == Result.MLE || result_1 == Result.OLE) {
                    stringBuilder.append(" 用时：").append(retJson.getJSONArray(i).getInt(4)).append("MS");
                }else{
                    stringBuilder.append(" 用时：").append(retJson.getJSONArray(i).getInt(2)).append("MS");
                }
                stringBuilder.append(" 内存：").append(retJson.getJSONArray(i).getInt(3)).append("KB");
                if(retJson.getJSONArray(i).size() >= 6 && retJson.getJSONArray(i).getInt(5) !=-1) {
                    stringBuilder.append(" 得分：").append(retJson.getJSONArray(i).getInt(5));
                }
                stringBuilder.append("\n");
            }
            res.setR(result_1);
            res.setTime(time + "MS");
            res.setMemory(memory + "KB");
            res.setCEInfo(stringBuilder.toString());
            if(has_score){
                computeScore_all_avg(retJson);
            }
        }
    }

    @Override
    public RES getResultReturn(VjSubmitter s){
        int wait[] = {500};
        int i=0;
        RES r = res;
        do{
            Tool.sleep(wait[i<wait.length?i:wait.length-1]);
            i++;

            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("type","getResult"));
            formparams.add(new BasicNameValuePair("rid",s.getSubmitInfo().rid+""));
            String judge_system_ret = hc.Post(url+"/",formparams);
            Tool.debug(judge_system_ret);
            if(judge_system_ret!=null){//处理中文乱码
                byte source [] = new byte[0];
                try {
                    source = judge_system_ret.getBytes("iso8859-1");
                    judge_system_ret = new String (source,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            JSONObject jo = JSONObject.fromObject(judge_system_ret);
            if(jo.getString("ret").equals("success")){
                setResult(jo.getString("result"));
            }else if(jo.getString("ret").equals("noSubmit")){
                r.setR(Result.ERROR);
            }

            //JudgeSystem用https协议主动返回
            if(r.getR() == Result.AC || r.getR() == Result.SCORE) {
                Pair<Integer, Integer> limit = Main.problems.getProblemLimit(Integer.parseInt(s.getSubmitInfo().pid));
                if(r.getUseTime() > limit.getKey()){
                    r.setR(Result.TLE);
                    r.setScore(-1);
                }
            }
            if(r.getR() == Result.MLE || r.getR() == Result.OLE){
                r.setTime("-");
            }

            //System.out.println(submitterID+":get res="+r.getR());
            s.setShowstatus("评测结果="+r.getR());
            if(i>=1000){
                r.setR(Result.ERROR);
                r.setTime("-");
                r.setMemory("-");
                r.setCEInfo("获取评测结果超时");
                s.setShowstatus("获取评测结果超时 i="+i+"次");
                //Main.status.setStatusResult(s.getSubmitInfo().rid, Result.ERROR, "-", "-", "ERROR:评测超时。可能是原oj繁忙");
                break;
            }
        }while(!r.canReturn());
        return r;
    }

    @Override
    public RES getResult(VjSubmitter s) {
        return null;
    }

    @Override
    public String getProblemURL(String pid) {
        return "Problem.jsp?pid="+pid;
    }

    public String name = "JudgeSystem";
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String get64IO(String pid) {
        return "%lld";
    }
}
