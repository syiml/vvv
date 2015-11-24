package entity.OJ.BNUOJ;

import entity.OJ.OTHOJ;
import util.Main;
import util.Vjudge.Submitter;
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
 * Created by Administrator on 2015/6/9.
 */
public class BNUOJ extends OTHOJ {
    private static String URL= Main.GV.getJSONObject("bnuoj").getString("URL");
    private static String submitURL="/v3/ajax/problem_submit.php";
    private static String problemURL="/v3/problem_show.php";
    private static String TitleSelect="h2";
    private static String statusURL="/contest/status.php";
    private static String statusFormUser="showname";
    private static String statuSelect="center table:nth-child(3) tr:nth-child(2)";
    private static String DesSelect=".content-wrapper";
    private static String SampleInputSelect="#showproblem pre";
    private static String loginURL="/v3/ajax/login.php";
    private static MyClient hc = new MyClient();
    private static String Int64="%lld";

    private static Map<String,Result> resultMap;
    private static Map<String,String>  languageMap;
    public BNUOJ(){
        resultMap = new HashMap<String, Result>();

        resultMap.put("Waiting",Result.PENDING);
        resultMap.put("Judging",Result.PENDING);
        resultMap.put("Accepted",Result.AC);
        resultMap.put("Wrong Answer",Result.WA);
        resultMap.put("Runtime Error",Result.RE);
        resultMap.put("Time Limit Exceed",Result.TLE);
        resultMap.put("Memory Limit Exceed",Result.MLE);
        resultMap.put("Output Limit Exceed",Result.OLE);
        resultMap.put("Presentation Error",Result.PE);
        resultMap.put("Compile Error",Result.CE);
        resultMap.put("Restricted Function",Result.DANGER);

        languageMap=new HashMap<String,String>();
        languageMap.put("0","1");//C++
        languageMap.put("1","2");//C
        languageMap.put("2","3");//JAVA
    }
    public String getName(){
        return "BNUOJ";
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
    public String getProblemURL(String pid){ return URL+problemURL+"?pid="+pid; }
    private Result getResultMap(String v){
        return resultMap.get(v);
    }

    public String getRid(String user){
        Element e=null;
        Document d = null;
        RES r=new RES();
        boolean bo;
        bo=false;
        try {
            d = Jsoup.connect(getStatusURL(user)).get();
            e = d.select(statuSelect).first();
            return (e.select("td:nth-child(2)").first().text());
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch(NullPointerException e2){
            bo=true;
            Main.sleep(1000);
        }
        if(bo) return "0";
        return "error";
    }
    public String getTitle(String pid){
        Document doc;
        try {
            doc = Jsoup.connect(getProblemURL(pid)).get();
            return doc.select(TitleSelect).get(0).text();
        } catch (IOException e) {
            return "";
        }
    }
    public problemHTML getProblemHTML(String pid){
        Document doc;
        problemHTML p=new problemHTML();
        try {
            doc = Jsoup.connect(getProblemURL(pid)).get();
        } catch (IOException e) {
            return null;
        }
        Elements es=doc.select("img");
        for(Element e:es){
            String link=e.attr("src");
            e.attr("src",URL+"/"+link);
        }
        Elements e=doc.select("#showproblem");
        e.attr("abs:href");
        p.setTitle(e.select(TitleSelect).get(0).text());
        p.setDis(e.select(DesSelect).get(0).html());
        p.setInput(e.select(DesSelect).get(1).html());
        p.setOutput(e.select(DesSelect).get(2).html());
        String ss=e.toString();
        if (ss.contains("<span class=\"badge badge-info\">CodeForces</span>")){
            Elements e3=e.select(".sample-test pre");
            for(int i=1;i<e3.size();i+=2){
                p.addSample("<pre style='padding:0px;border-style:none;background-color:transparent'>"
                        +e3.get(i-1).html()+"</pre>",
                        "<pre style='padding:0px;border-style:none;background-color:transparent'>"
                        +e3.get(i).html()+"</pre>");
            }
        }else{
            Elements e2 = e.select("pre");
            int k=0;
            String in="",out;
            for(int i=0;i<e2.size();i++){
                if(e2.get(i).hasClass("content-wrapper")){
                    k++;
                    if(k%2==1){
                        in="<pre style='padding:0px;border-style:none;background-color:transparent'>"
                                + e2.get(i).html() + "</pre>";
                    }else{
                        out="<pre style='padding:0px;border-style:none;background-color:transparent'>"
                                + e2.get(i).html() + "</pre>";
                        p.addSample(in,out);
                    }
                }
            }
        }
        p.setInt64(doc.select(".badge-inverse").get(0).text());
        String s=doc.select(".span6").get(0).text();
        p.setTimeLimit(s.substring(11, s.length()));
        s=doc.select(".span6").get(1).text();
        p.setMenoryLimit(s.substring(14,s.length()));
        return p;
    }
    public String submit(Submitter s){
        String ret=Login(s);
        if(ret.equals("error")) return "error";
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("problem_id",""+s.getSubmitInfo().pid));
        formparams.add(new BasicNameValuePair("language",languageMap.get(s.getSubmitInfo().language+"")));
        formparams.add(new BasicNameValuePair("source",s.getSubmitInfo().code));
        formparams.add(new BasicNameValuePair("isshare","0"));//share code
        formparams.add(new BasicNameValuePair("user_id",s.getUsername()));
        if(hc.Post(getSubmitURL(), formparams)==0) return "error";
        return "success";
    }
    public RES getResult(Submitter s) {
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
        //System.out.println("get:"+e.html());
        r.setR(getResultMap(e.select("td:nth-child(4)").first().text()));
        if(r.canReturn()){
            r.setTime(e.select("td:nth-child(6)").first().text().replaceAll(" ms","MS"));
            r.setMemory(e.select("td:nth-child(7)").first().text().replaceAll(" KB","KB"));
        }
        if(r.getR()==Result.CE){
            r.setCEInfo(getCEInfo(s));
        }
        return r;
    }
    public String Login(Submitter s){
        //登录
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("username",s.getUsername()));
        formparams.add(new BasicNameValuePair("password",s.getPassword()));
        formparams.add(new BasicNameValuePair("cksave","365"));//cookie save
        if(hc.Post(getLoginURL(), formparams)==0) return "error";
        return "success";
    }
    public String getCEInfo(Submitter s){
        Element e;
        Document d = null;
        try {
            d = Jsoup.connect("http://www.bnuoj.com/contest/show_ce_info.php?runid="+s.getOjsrid()).get();
            return d.select("pre").first().html();
        } catch (IOException e1) {
            e1.printStackTrace();
            return "none";
        }
    }
}
