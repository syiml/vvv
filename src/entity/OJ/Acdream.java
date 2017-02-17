package entity.OJ;

import entity.RES;
import entity.Result;
import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.HTML.HTML;
import util.HTML.problemHTML;
import util.Main;
import util.MyClient;
import util.Tool;
import util.Vjudge.VjSubmitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ACDream oj 支持
 * Created by QAQ on 2017/2/17.
 */
public class Acdream extends OTHOJ{
    public static String URL = Main.GV.getJSONObject("acdream").getString("URL");

    public static void setRes_map(Map<String, Result> res_map) {
        Acdream.res_map = res_map;
    }

    @Override
    public String getRid(String user, VjSubmitter s) {
        Document d = s.client.get(URL+"/status?name="+s.getUsername());
        Element e = d.select("table tbody tr td").first();
        if(e!=null){
            return e.text();
        }else{
            return "0";
        }
    }

    @Override
    public problemHTML getProblemHTML(String pid) {
        Document d = MyClient.getMyClient().get(getProblemURL(pid));
        problemHTML pHTML = new problemHTML();
        pHTML.setTitle(d.select(".problem-header").text());
        Elements ds = d.select(".prob-content");
        pHTML.setDis(ds.get(0).html());
        pHTML.setInput(ds.get(1).html());
        pHTML.setOutput(ds.get(2).html());
        pHTML.addSample(ds.get(3).html(),ds.get(4).html());
        Elements limit = d.select(".user-black");
        String time_limit = limit.get(0).text();
        time_limit = time_limit.substring(time_limit.indexOf("/")+1,time_limit.indexOf("MS"));
        String memory_limit = limit.get(1).text();
        memory_limit = memory_limit.substring(memory_limit.indexOf("/")+1,memory_limit.indexOf("KB"));
        pHTML.setTimeLimit(time_limit+"MS");
        pHTML.setMenoryLimit((Integer.parseInt(memory_limit)/1000)+"MB");
        pHTML.setInt64(get64IO(null));
        return pHTML;
    }

    @Override
    public String getTitle(String pid) {
        Document d = MyClient.getMyClient().get(getProblemURL(pid));
        return d.select(".problem-header").text();
    }

    @Override
    public String submit(VjSubmitter s) {
        MyClient client = s.client;
        if(client == null) return "error";

        List<NameValuePair> login_para = new ArrayList<>();
        login_para.add(new BasicNameValuePair("username",s.getUsername()));
        login_para.add(new BasicNameValuePair("password",s.getPassword()));
        login_para.add(new BasicNameValuePair("remember","false"));
        client.Post(URL+"/login",login_para);

        int lang = 2;
        List<NameValuePair> para = new ArrayList<>();
        para.add(new BasicNameValuePair("pid",s.getSubmitInfo().pid));
        para.add(new BasicNameValuePair("code",s.getSubmitInfo().code));
        if(s.getSubmitInfo().language == 0) lang =  2;
        else if(s.getSubmitInfo().language == 1) lang =  1;
        else if(s.getSubmitInfo().language == 2) lang =  3;
        para.add(new BasicNameValuePair("lang",lang+""));
        client.Post(URL+"/submit",para);
        return "success";
    }

    private static Map<String,Result> res_map;

    static {
        res_map = new HashMap<>();
        res_map.put("Pending...",Result.PENDING);
        res_map.put("Running...",Result.RUNNING);
        res_map.put("Accepted",Result.AC);
        res_map.put("Wrong Answer",Result.WA);
        res_map.put("Presentation Error",Result.PE);
        res_map.put("Time Limit Exceeded",Result.TLE);
        res_map.put("Memory Limit Exceeded",Result.MLE);
        res_map.put("Output Limit Exceeded",Result.OLE);
        res_map.put("Compilation Error",Result.CE);
        res_map.put("Runtime Error",Result.RE);
        res_map.put("Dangerous Code",Result.DANGER);
        res_map.put("System Error",Result.ERROR);
    }
    private String getCEInfo(VjSubmitter s, String rid){
        List<NameValuePair> para = new ArrayList<>();
        para.add(new BasicNameValuePair("rid",rid));
        String ret = s.client.Post(URL+"/status/CE",para);
        return HTML.HTMLtoString(JSONObject.fromObject(ret).getString("msg"));
    }
    @Override
    public RES getResult(VjSubmitter s) {
        Document d = s.client.get(URL+"/status?name="+s.getUsername());
        Elements e = d.select("table tbody tr").first().select("td");
        RES res = new RES();
        if(e == null) {
            res.setR(Result.ERROR);
        }else{
            String res_str = e.get(3).text();
            Result rs = res_map.get(res_str);
            if(rs == null) res.setR(Result.ERROR);
            else res.setR(rs);
            res.setTime(e.get(4).text().replace(" ",""));
            res.setMemory(e.get(5).text().replace(" ",""));
            if(res.getR()==Result.CE || res.getTime().equals("---")) res.setTime("-");
            if(res.getR()==Result.CE || res.getMemory().equals("---")) res.setMemory("-");
            if(res.getR() == Result.CE){
                res.setCEInfo(getCEInfo(s,e.get(0).text()));
            }
        }
        return res;
    }

    @Override
    public String getProblemURL(String pid) {
        return URL+"/problem?pid="+pid;
    }

    @Override
    public String getName() {
        return "Acdream";
    }

    @Override
    public String get64IO(String pid) {
        return "%lld";
    }
}
