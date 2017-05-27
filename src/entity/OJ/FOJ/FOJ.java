package entity.OJ.FOJ;

import entity.OJ.OTHOJ;
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
import util.Vjudge.VjSubmitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FOJ extends OTHOJ{
    public static String URL = Main.GV.getJSONObject("foj").getString("URL");
    @Override
    public String getRid(String user, VjSubmitter s) {
        Document d = s.client.get(URL+"/log.php?user="+s.getUsername());
        Element e = d.select("table tr:nth-child(2) td").first();
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
        pHTML.setTitle(d.select(".problem_title b").text());
        Elements ds = d.select(".pro_desc");
        pHTML.setDis(ds.get(0).html());
        pHTML.setInput(ds.get(1).html());
        pHTML.setOutput(ds.get(2).html());
        pHTML.addSample(d.select(".data").get(0).tagName("pre").html(),d.select(".data").get(1).tagName("pre").html());
        Elements limit = d.select(".problem_desc");
        String time_limit = limit.get(0).text();
        time_limit = time_limit.substring(time_limit.indexOf("Time Limit:")+11,time_limit.indexOf("mSec"));
        String memory_limit = limit.get(0).text();
        memory_limit = memory_limit.substring(memory_limit.indexOf("Memory Limit :")+14,memory_limit.indexOf("KB"));
        pHTML.setTimeLimit(time_limit.trim()+"MS");
        pHTML.setMenoryLimit((Integer.parseInt(memory_limit.trim())/1000)+"MB");
        pHTML.setInt64(get64IO(null));
        return pHTML;
    }

    @Override
    public String getTitle(String pid) {
        Document d = MyClient.getMyClient().get(getProblemURL(pid));
        return d.select(".problem_title b").text();
    }

    @Override
    public String submit(VjSubmitter s) {
        MyClient client = s.client;
        if(client == null) return "error";

        Document d = s.client.get(URL+"/login.php?dir=L2luZGV4LnBocA==");
        String action = d.select(".form_login form").attr("action");

        List<NameValuePair> login_para = new ArrayList<>();
        login_para.add(new BasicNameValuePair("uname",s.getUsername()));
        login_para.add(new BasicNameValuePair("upassword",s.getPassword()));
        login_para.add(new BasicNameValuePair("submit","Submit"));

        //System.out.println("action = "+action);
        client.Post(URL+"/"+action,login_para);

        int lang = 0;
        List<NameValuePair> para = new ArrayList<>();
        para.add(new BasicNameValuePair("usr",s.getUsername()));
        para.add(new BasicNameValuePair("pid",s.getSubmitInfo().pid));
        para.add(new BasicNameValuePair("code",s.getSubmitInfo().code));
        if(s.getSubmitInfo().language == 0) lang =  0;
        else if(s.getSubmitInfo().language == 1) lang =  1;
        else if(s.getSubmitInfo().language == 2) lang =  3;
        para.add(new BasicNameValuePair("lang",lang+""));
        para.add(new BasicNameValuePair("submit","Submit"));
        client.Post(URL+"/submit.php?act=5",para);
        return "success";
    }
    private static Map<String,Result> res_map;
    static {
        res_map = new HashMap<>();
        res_map.put("Queuing...",Result.PENDING);
        res_map.put("Judging...",Result.RUNNING);
        res_map.put("Accepted",Result.AC);
        res_map.put("Wrong Answer",Result.WA);
        res_map.put("Presentation Error",Result.PE);
        res_map.put("Time Limit Exceed",Result.TLE);
        res_map.put("Memory Limit Exceed",Result.MLE);
        res_map.put("Output Limit Exceed",Result.OLE);
        res_map.put("Compile Error",Result.CE);
        res_map.put("Runtime Error",Result.RE);
        res_map.put("Restrict Function Call",Result.DANGER);
        res_map.put("System Error",Result.ERROR);
    }


    @Override
    public RES getResult(VjSubmitter s) {
        Document d = s.client.get(URL+"/log.php?user="+s.getUsername());
        Elements e = d.select("table tr:nth-child(2)").select("td");
        RES res = new RES();
        if(e == null) {
            res.setR(Result.ERROR);
        }else{
            String res_str = e.get(2).text();
            System.out.println("res_str="+res_str);
            Result rs = res_map.get(res_str);
            if(rs == null) res.setR(Result.ERROR);
            else res.setR(rs);
            res.setTime(e.get(5).text().replace(" ","").replace("ms","MS"));
            res.setMemory(e.get(6).text().replace(" ",""));
            if(res.getR()==Result.CE || res.getTime().equals("")) res.setTime("-");
            if(res.getR()==Result.CE || res.getMemory().equals("")) res.setMemory("-");
            if(res.getR()==Result.CE){
                String rid = e.get(0).text();
                res.setCEInfo(getCEInfo(rid,s));
            }
        }
        return res;
    }

    private String getCEInfo(String rid,VjSubmitter s){
        Document d = s.client.get(URL+"/ce.php?sid="+rid);
        return d.select(".form_src_content font").html();
    }

    @Override
    public String getProblemURL(String pid) {
        return URL+"/problem.php?pid="+pid;
    }

    @Override
    public String getName() {
        return "FOJ";
    }

    @Override
    public String get64IO(String pid) {
        return "%I64d";
    }
}
