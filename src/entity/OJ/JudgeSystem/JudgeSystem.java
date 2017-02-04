package entity.OJ.JudgeSystem;

import entity.OJ.OTHOJ;
import entity.Problem;
import entity.RES;
import entity.Result;
import org.apache.commons.collections.comparators.BooleanComparator;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import util.HTML.problemHTML;
import util.Main;
import util.MyClient;
import util.Pair;
import util.Tool;
import util.Vjudge.VjSubmitter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by syimlzhu on 2017/1/17.
 */
public class JudgeSystem extends OTHOJ {
    private String url= Main.GV.getJSONObject("judge_system").getString("URL");
    private MyClient hc = MyClient.getMyClient();
    public RES res = new RES();
    public final Boolean lock = false;
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
        formparams.add(new BasicNameValuePair("language","0"));
        synchronized (lock) {
            String ret = hc.Post(url+"/",formparams);
            s.rid = Math.random()+"";
            Tool.debug("wait begin");
            try {
                lock.wait();
            } catch (InterruptedException e) {
                Tool.debug("wait end");
            }
        }
        //if(ret == null) return "error";
        //else {
        return "success";
        //}
    }

    @Override
    public RES getResultReturn(VjSubmitter s){
        //JudgeSystem用https协议主动返回
        if(res.getR() == Result.AC) {
            Pair<Integer, Integer> limit = Main.problems.getProblemLimit(Integer.parseInt(s.getSubmitInfo().pid));
            if(res.getUseTime() > limit.getKey()){
                res.setR(Result.TLE);
            }
        }
        if(res.getR() == Result.MLE || res.getR() == Result.OLE){
            res.setTime("-");
        }
        return res;
    }

    @Override
    public RES getResult(VjSubmitter s) {
        return null;
    }

    @Override
    public String getProblemURL(String pid) {
        return "";
    }

    @Override
    public String getName() {
        return "JudgeSystem";
    }
}
