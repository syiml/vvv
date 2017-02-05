package entity.OJ.HDU;

import entity.OJ.OTHOJ;
import util.Main;
import util.Tool;
import util.Vjudge.VjSubmitter;
import entity.RES;
import entity.Result;
import util.MyClient;
import util.HTML.problemHTML;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/6/7.
 */
public class HDU extends OTHOJ {
    private static String URL= Main.GV.getJSONObject("hdu").getString("URL");
    private static String submitURL="/submit.php?action=submit";
    private static String problemURL="/showproblem.php";
    private static String TitleSelect="h1";
    private static String statusURL="/status.php";
    private static String statusFormUser="user";
    private static String statuSelect="table:nth-child(2) tr:nth-child(4) table tr:nth-child(3)";
    private static String DesSelect=".panel_content";
    private static String SampleInputSelect="pre";
    private static Map<String,Result> resultMap;
    private static String loginURL="/userloginex.php?action=login";
    private static Map<String,String>  languageMap;
    private static String Int64="%I64d";
    private MyClient hc = new MyClient();
    public HDU(){
        resultMap = new HashMap<String, Result>();
        resultMap.put("Queuing",Result.PENDING);
        resultMap.put("Compiling",Result.PENDING);
        resultMap.put("Running",Result.PENDING);
        resultMap.put("Accepted",Result.AC);
        resultMap.put("Wrong Answer",Result.WA);
        resultMap.put("Runtime Error (ACCESS_VIOLATION)",Result.RE);
        resultMap.put("Runtime Error (ARRAY_BOUNDS_EXCEEDED)",Result.RE);
        resultMap.put("Runtime Error (FLOAT_DENORMAL_OPERAND)",Result.RE);
        resultMap.put("Runtime Error (FLOAT_DIVIDE_BY_ZERO)",Result.RE);
        resultMap.put("Runtime Error (FLOAT_OVERFLOW)",Result.RE);
        resultMap.put("Runtime Error (FLOAT_UNDERFLOW)",Result.RE);
        resultMap.put("Runtime Error (INTEGER_DIVIDE_BY_ZERO)",Result.RE);
        resultMap.put("Runtime Error (INTEGER_OVERFLOW)",Result.RE);
        resultMap.put("Runtime Error (STACK_OVERFLOW)",Result.RE);
        resultMap.put("Time Limit Exceeded",Result.TLE);
        resultMap.put("Memory Limit Exceeded",Result.MLE);
        resultMap.put("Output Limit Exceeded",Result.OLE);
        resultMap.put("Presentation Error",Result.PE);
        resultMap.put("Compilation Error",Result.CE);
        resultMap.put("System Error",Result.DANGER);

        languageMap=new HashMap<String,String>();
        languageMap.put("0", "0");
        languageMap.put("1", "1");
        languageMap.put("2", "5");
    }

    private static String getSubmitURL(){
        return URL+submitURL;
    }

    private static String getLoginURL() {
        return URL+loginURL;
    }

    private static String getStatusURL(String user){
        return URL+statusURL+"?"+statusFormUser+"="+user;
    }

    public String getName(){
        return "HDU";
    }

    @Override
    public String get64IO(String pid) {
        return Int64;
    }

    public String getProblemURL(String pid){ return URL+problemURL+"?pid="+pid; }
    private Result getResultMap(String v){
        return resultMap.get(v);
    }

    public String getRid(String user,VjSubmitter s){
        try {
            Document d = Jsoup.connect(getStatusURL(user)).get();
            Element e = d.select(statuSelect).first();
            return (e.select("td:nth-child(1)").first().text());
        } catch (Exception ignored) {
//            Tool.log(ignored);
        }
        return "error";
    }
    public String getTitle(String pid){
        Document doc;
        try {
            doc = Jsoup.connect(getProblemURL(pid)).get();
            return doc.select(TitleSelect).get(0).text();
        } catch (IOException e) {
            Tool.log(e);
            return GET_TITLE_ERROR;
        }
    }
    public problemHTML getProblemHTML(String pid){
        Document doc;
        problemHTML p=new problemHTML();
        try {
            doc = Jsoup.connect(getProblemURL(pid)).get();
        } catch (IOException e) {
            Tool.log(e);
            return null;
        }
        Elements es=doc.select("img");
        for(Element e:es){
            String link=e.attr("src");
            e.attr("src",URL+"/"+link);
        }
        p.setTitle(doc.select(TitleSelect).get(0).text());
        p.setDis(doc.select(DesSelect).get(0).html());
        p.setInput(doc.select(DesSelect).get(1).html());
        p.setOutput(doc.select(DesSelect).get(2).html());
        Elements e2 = doc.select(SampleInputSelect);
        p.addSample("<pre class='sample'>"
                        + e2.get(e2.size() - 2).text() + "</pre>",
                "<pre class='sample'>"
                        + e2.get(e2.size() - 1).text() + "</pre>");
        p.setInt64(Int64);
        String s=doc.select("span").get(0).text();
        //System.out.println(s);
        String s1=s.substring(s.indexOf("/")+1, s.indexOf("(Java/Others)")-4)+"MS";
        String s2=s.substring(s.indexOf("Memory Limit:")+13);
        s2=s2.substring(s2.indexOf("/")+1,s2.indexOf("K (Java/Others)")-1)+"KB";
        p.setTimeLimit(s1);
        p.setMenoryLimit(s2);
        if(s.contains("Special Judge")) p.setSpj(1);
        return p;
    }
    public String submit(VjSubmitter s){
        String ret=Login(s);
        if(ret.equals("error")) return "error";
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("problemid",""+s.getSubmitInfo().pid));
        formparams.add(new BasicNameValuePair("language",languageMap.get(s.getSubmitInfo().language+"")));
        formparams.add(new BasicNameValuePair("usercode",s.getSubmitInfo().code));
        if(hc.Post(getSubmitURL(), formparams)==null) return "error";
        return "success";
    }
    public RES getResult(VjSubmitter s) {
        Element e;
        Document d = null;
        RES r=new RES();
        try {
            d = Jsoup.connect(getStatusURL(s.getUsername())).get();
        } catch (IOException e1) {
            e1.printStackTrace();
            r.setR(Result.PENDING);
            return r;
        }
        e = d.select(statuSelect).first();
        //System.out.println("get:"+getResultMap(e.select("td:nth-child(3)").first().text()).name());
        r.setR(getResultMap(e.select("td:nth-child(3)").first().text()));
        if(r.canReturn()){
            r.setTime(e.select("td:nth-child(5)").first().text());
            r.setMemory(e.select("td:nth-child(6)").first().text()+"B");
        }
        if(r.getR()==Result.CE){
            r.setCEInfo(getCEInfo(s));
        }
        return r;
    }
    public String Login(VjSubmitter s){
        //登录
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("username",s.getUsername()));
        formparams.add(new BasicNameValuePair("userpass",s.getPassword()));
        if(hc.Post(getLoginURL(), formparams)==null) return "error";
        return "success";
    }
    public String getCEInfo(VjSubmitter s){
        Element e;
        Document d = null;
        try {
            d = Jsoup.connect("http://acm.hdu.edu.cn/viewerror.php?rid="+s.getOjsrid()).get();
            return d.select("pre").first().html();
        } catch (IOException e1) {
            e1.printStackTrace();
            return "获取失败";
        } catch (NullPointerException e2){
            return "获取失败";
        }
    }
}
