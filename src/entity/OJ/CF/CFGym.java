package entity.OJ.CF;

import entity.OJ.OTHOJ;
import entity.RES;
import entity.Result;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.HTML.problemHTML;
import util.Main;
import util.MyClient;
import util.Tool;
import util.Vjudge.VjSubmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syiml on 2015/10/9 0009.
 */
public class CFGym extends CF {
    private static String URL=Main.GV.getJSONObject("cf_gym").getString("URL");

    @Override
    public String getRid(String user,VjSubmitter s) {
        String pid = s.getSubmitInfo().pid;
        String pid1=pid.substring(0,pid.length()-1);
        login(s,s.client);
        Document doc = s.client.get(URL+"/gym/"+pid1+"/my");
        Element e=doc.select(".status-frame-datatable tr").get(1);
        if(e.select(".hiddenSource").size()==0){
            return "0";
        }
        return e.select(".hiddenSource").get(0).text();
    }

    @Override
    public String getTitle(String pid) {
        Document doc;
        try {
            doc = Jsoup.connect(getProblemURL(pid)).get();
        } catch (IOException e) {
            return GET_TITLE_ERROR;
        }
        return doc.select(".problem-statement").select(".title").get(0).text().substring(3);
    }

    private String get_csrf_token(MyClient hc,int z,VjSubmitter s){
        if(z==1){
            try {
                return hc.get(URL + "/enter").select("#enterForm").select("input").get(0).attr("value");
            }catch (Exception ignored){}
            return null;
        }
        else{
            String pid = s.getSubmitInfo().pid;
            String pid1=pid.substring(0,pid.length()-1);
            return hc.get(URL + "/gym/"+pid1+"/submit").select(".submit-form").select("input").get(0).attr("value");
        }
    }
    private int login(VjSubmitter s, MyClient hc){
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        String token = get_csrf_token(hc,1,s);
        if(token == null) return 1;
        formparams.add(new BasicNameValuePair("csrf_token",token));
        formparams.add(new BasicNameValuePair("action","enter"));
        formparams.add(new BasicNameValuePair("handle",s.getUsername()));
        formparams.add(new BasicNameValuePair("password",s.getPassword()));
        formparams.add(new BasicNameValuePair("remember","1"));
        formparams.add(new BasicNameValuePair("_tta","451"));
        String ret = hc.Post(URL+"/enter",formparams);
        if(ret == null ) return 0;
        return 1;
    }

    @Override
    public String submit(VjSubmitter s) {
        String pid = s.getSubmitInfo().pid;
        String pid1 = pid.substring(0, pid.length() - 1);
        String pid2 = pid.substring(pid.length()-1);
        MyClient hc = s.client;
        //login
        login(s, hc);
        //submit
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        String csrf_token = get_csrf_token(hc, 0, s);
        formparams.add(new BasicNameValuePair("csrf_token", csrf_token));
        formparams.add(new BasicNameValuePair("action", "submitSolutionFormSubmitted"));
        formparams.add(new BasicNameValuePair("submittedProblemIndex", pid2));
        formparams.add(new BasicNameValuePair("programTypeId", getLanguage(s.getSubmitInfo().language)));
        formparams.add(new BasicNameValuePair("source", s.getSubmitInfo().code + getRandomCode()));
        if (hc.Post(URL + "/gym/" + pid1 + "/submit?csrf_token=" + csrf_token, formparams) != null) return "success";
        return "error";
    }
    @Override
    public RES getResult(VjSubmitter s) {
        String pid = s.getSubmitInfo().pid;
        String pid1=pid.substring(0,pid.length()-1);
        RES res=new RES();
        res.setR(Result.PENDING);
        login(s,s.client);
        Document doc = s.client.get(URL+"/gym/"+pid1+"/my");
        Element row=doc.select(".status-frame-datatable tr").get(1);
        if(row==null){
            return res;
        }
        res.setR(getRes(row.select(".status-verdict-cell").text()));
        if(res.getR()==Result.AC){
            res.setTime(row.select(".time-consumed-cell").text().replace(" ms", "MS"));
            res.setMemory(row.select(".memory-consumed-cell").text().replace(" KB", "KB"));
        }
        if(res.getR()==Result.CE){
            res.setCEInfo("无信息");
        }
        return res;
    }

    @Override
    public String getProblemURL(String pid) {
        String pid2=pid.substring(pid.length()-1);
        String pid1=pid.substring(0,pid.length()-1);
        return URL+"/gym/"+pid1+"/problem/"+pid2;
    }

    @Override
    public String getName() {
        return "CFGym";
    }
}
