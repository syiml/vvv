package entity.OJ.CodeVS;

import entity.OJ.OTHOJ;
import entity.RES;
import entity.Result;
import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.HTML.HTML;
import util.HTML.problemHTML;
import util.MyClient;
import util.Pair;
import util.Tool;
import util.Vjudge.VjSubmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QAQ on 2016/9/21.
 */
public class CodeVS extends OTHOJ {
    private static String URL = "http://codevs.cn";
    private static String problemURL="/problem/";
    private static String loginURL = "http://login.codevs.com/api/auth/login";
    private static String submitURL = "http://codevs.cn/judge/";

    private MyClient hc = new MyClient();
    @Override
    public String getRid(String user,VjSubmitter s) {
        return s.rid;
    }

    @Override
    public problemHTML getProblemHTML(String pid) {
        Document doc;
        try {
            doc = Jsoup.connect(getProblemURL(pid)).get();
        } catch (IOException e) {
            Tool.log(e);
            return null;
        }
        Elements e =  doc.select("h3 b");
        if(e == null) return null;
        Elements panels =  doc.select(".panel-body");

        Elements img=doc.select("img");
        for(Element element:img){
            String link=element.attr("src");
            if(element.attr("src").indexOf("htpp") != 0)
                element.attr("src",URL+link);
        }

        if(panels.size()!=8) return null;

        problemHTML p = new problemHTML();
        String title = e.text().substring(5);
        p.setTitle(title);
        p.setDis(panels.get(0).html());
        p.setInput(panels.get(1).html()+"<br>数据范围及提示：<br>"+panels.get(5).html());
        p.setOutput(panels.get(2).html());
        p.addSample(panels.get(3).html(),panels.get(4).html());

        p.setInt64("%lld");

        Elements limits =  doc.select(".m-r-xs");
        String timeLimit = limits.get(0).text();
        timeLimit = timeLimit.substring(timeLimit.indexOf("时间限制")+6,timeLimit.indexOf("s")-1)+"000MS";
        p.setTimeLimit(timeLimit);

        String memoryLimit = limits.get(1).text();
        memoryLimit = memoryLimit.substring(memoryLimit.indexOf("空间限制")+6,memoryLimit.indexOf("KB")-1)+"KB";
        p.setMenoryLimit(memoryLimit);

        return p;
    }

    @Override
    public String getTitle(String pid) {
        try {
            Document doc;
            try {
                doc = Jsoup.connect(getProblemURL(pid)).get();
            } catch (IOException e) {
                Tool.log(e);
                return null;
            }
            Elements e = doc.select("h3 b");
            if (e.size()==0) return null;
            return e.text().substring(5);
        }catch (Exception e){
            return null;
        }
    }

    public boolean haveProblem(String pid){
        return getTitle(pid)!=null;
    }

    public String login(VjSubmitter s){
        hc.get("http://login.codevs.com/auth/login").text();
        List<NameValuePair> para = new ArrayList<>();
        para.add(new BasicNameValuePair("username",s.getUsername()));
        para.add(new BasicNameValuePair("password",s.getPassword()));
        String json = hc.Post(loginURL,para);
        JSONObject jsonObject = JSONObject.fromObject(json);
        hc.get("http://login.codevs.com/auth/redirect/?next=http://codevs.cn/accounts/token/login/&token");
        hc.header.add(new Pair<>("Authorization","JWT "+jsonObject.get("jwt")));
        Document d = hc.get("http://login.codevs.com/api/auth/token");
        hc.header.clear();
        Tool.debug("token = " + d.text());
        JSONObject token = JSONObject.fromObject(d.text());
        String tokenString = token.getString("token");
        hc.get("http://codevs.cn/accounts/token/login/?token="+tokenString);
        return null;
    }

    @Override
    public String submit(VjSubmitter s) {
        login(s);
        Document doc;
        doc = hc.get(getProblemURL(s.getSubmitInfo().pid));
        Element e = doc.select("input[name=csrfmiddlewaretoken]").first();
        String csrfmiddlewaretoken = e.attr("value");
        Tool.debug("csrfmiddlewaretoken="+csrfmiddlewaretoken);

        List<NameValuePair> para = new ArrayList<>();
        para.add(new BasicNameValuePair("code",s.getSubmitInfo().code));
        para.add(new BasicNameValuePair("id",s.getSubmitInfo().pid));
        para.add(new BasicNameValuePair("format","cpp"));
        para.add(new BasicNameValuePair("csrfmiddlewaretoken",csrfmiddlewaretoken));

        String ret = hc.Post(submitURL,para);
        JSONObject response = JSONObject.fromObject(ret);
        if(response.getBoolean("success")){
            s.rid = response.get("id").toString();
            return "success";
        }
        return "error";
    }

    public Result getResult(String s){
        if(s.contains("Pending")) return Result.RUNNING;
        if(s.contains("Accepted")) return Result.AC;
        if(s.contains("Compile Error")) return Result.CE;
        if(s.contains("Running")) return Result.RUNNING;
        if(s.contains("Wrong Answer")) return Result.WA;
        if(s.contains("Restricted Call")) return Result.RE;
        if(s.contains("Runtime Error")) return Result.RE;
        if(s.contains("Time Limit Exceeded")) return Result.TLE;
        if(s.contains("Memory Limit Exceeded")) return Result.TLE;
        if(s.contains("Output Limit Exceeded")) return Result.OLE;
        if(s.contains("Rejudge Padding")) return Result.RUNNING;
        if(s.contains("COMPILING")) return Result.RUNNING;
        return Result.ERROR;
    }
    private int getScoreFormResults(String Results){
        int totalTestPoint = 0;
        int index = 0;
        while((index = Results.indexOf("测试点",index)) !=-1){
            totalTestPoint ++;
            index ++;
        }
        int acTestPointNum = 0;
        index = 0;
        while((index = Results.indexOf("结果:AC",index)) !=-1){
            acTestPointNum ++;
            index ++;
        }
        return acTestPointNum*100/totalTestPoint;
    }
    private String getInfoFormResults(JSONObject ret){
        StringBuilder sb = new StringBuilder();
        if(ret.containsKey("inputname")){
            sb.append("最近的错误点信息：").append(ret.getString("inputname")).append(" ")
                    .append(ret.getString("outputname")).append("<br>");
        }
        if(ret.containsKey("input")) sb.append(ret.getString("input")).append("<br>");
        if(ret.containsKey("useroutput")) sb.append(ret.getString("useroutput")).append("<br>");
        if(ret.containsKey("rightoutput")) sb.append(ret.getString("rightoutput")).append("<br><br>");
        if(ret.containsKey("results")) sb.append("运行结果：").append(ret.getString("results").replaceAll("测试点","<br>测试点"));
        return sb.toString();
    }
    @Override
    public RES getResult(VjSubmitter s) {
        Document d = hc.get("http://codevs.cn/submission/api/refresh/?id="+s.rid+"&waiting_time=0");
        Tool.debug(d.select("body").html());
        JSONObject json = JSONObject.fromObject(d.select("body").text());
        Result r = getResult(json.getString("status"));
        RES res = new RES();
        res.setR(r);

        if(res.canReturn()) {
            res.setTime(json.getInt("time_cost") + "MS");
            res.setMemory(json.getLong("memory_cost")/1024 + "KB");
            if(json.containsKey("results")) res.setScore(getScoreFormResults(json.getString("results")));
            if(r == Result.CE)
                res.setCEInfo(json.getString("results"));
            else
                res.setCEInfo(getInfoFormResults(json));
        }
        return res;
    }

    @Override
    public String getProblemURL(String pid) {
        return URL+problemURL+pid;
    }

    @Override
    public String getName() {
        return "CodeVS";
    }
}
