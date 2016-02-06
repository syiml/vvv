package entity.OJ.HUST;

import entity.OJ.OTHOJ;
import util.Main;
import util.Vjudge.VjSubmitter;
import entity.RES;
import entity.Result;
import util.HTML.problemHTML;
import util.MyClient;
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
 * Created by Syiml on 2015/8/24 0024.
 */
public class HUST extends OTHOJ {
    //status.php?user_id=
    String url= Main.GV.getJSONObject("hust").getString("URL");
    public static Map<String,Result> ResultMap;
    public HUST(){
        ResultMap=new HashMap<String, Result>();
        ResultMap.put("Accepted",Result.AC);
        ResultMap.put("Wrong Answer",Result.WA);
        ResultMap.put("Presentation Error",Result.PE);
        ResultMap.put("Time Limit Exceed",Result.TLE);
        ResultMap.put("Memory Limit Exceed",Result.MLE);
        ResultMap.put("Runtime Error",Result.RE);
        ResultMap.put("Output Limit Exceed",Result.OLE);
        ResultMap.put("Compile Error",Result.CE);
        ResultMap.put("Running & Judging",Result.JUDGING);
        ResultMap.put("Pending",Result.JUDGING);
        ResultMap.put("Compiling",Result.JUDGING);
        ResultMap.put("Pending Rejudge",Result.JUDGING);
//        ResultMap.put("Validator Error",Result.ERROR);
    }
    public String getName(){
        return "FJUTOJ";
    }
    public String getRid(String user){
        Element e;
        Document d;
        try {
            d = Jsoup.connect(url + "/status.php?user_id=" + user).get();
            try{e = d.select("#result-tab tbody tr").get(0);}catch(IndexOutOfBoundsException ee){return "new";}
            return e.select("td:nth-child(1)").first().text();
        } catch (Exception ignored) {}
        return "error";
    }
    public String getProblemURL(String pid){
        return url+"/problem.php?id="+pid;
    }
    public problemHTML getProblemHTML(String pid){
        problemHTML ph=new problemHTML();
        Element e=null;
        Document d = null;
        try {
            d = Jsoup.connect(url+"/problem.php?id="+pid).get();
            Elements es=d.select("img");
            for(Element ee:es){
                String link=ee.attr("src");
                ee.attr("src",d+"/"+link);
            }
            String title=d.select("#main h2").first().text();
            ph.setTitle(title);
            ph.setDis(d.select("#main .content").eq(0).html());
            ph.setInput(d.select("#main .content").eq(1).html());
            ph.setOutput(d.select("#main .content").eq(2).html());
            ph.addSample("<pre style='padding:0px;border-style:none;background-color:transparent'>"
                            + d.select("#main .content").eq(3).text() + "</pre>",
                    "<pre style='padding:0px;border-style:none;background-color:transparent'>"
                            + d.select("#main .content").eq(4).text() + "</pre>");
            ph.setInt64("%lld");
            String limit=d.select("#main center").text().substring(title.length());
            ph.setTimeLimit(limit.substring(limit.indexOf("Time Limit:")+12,limit.indexOf("Sec")-1)+"000ms");
            ph.setMenoryLimit(limit.substring(limit.indexOf("Memory Limit:")+14,limit.indexOf("MB")-1)+"000KB");
            if(limit.contains("Special Judge")) ph.setSpj(1);
            //String limit=d.select("#limit").text();
//            System.out.println("get:"+d.select(".plm").select("td").get(2));

//            ph.setTimeLimit(d.select("#main").select("td").get(0).text().substring(12));
//            ph.setMenoryLimit(d.select(".plm").select("td").get(2).text().substring(14));
//            if(d.select(".plm").text().contains("Special Judge")) ph.setSpj(1);
        } catch (IOException e1) {
            System.out.print("connect timed out");
            //e1.printStackTrace();
        }
        return ph;
    }
    public String getTitle(String pid){
        Element e=null;
        Document d = null;
        try {
            d = Jsoup.connect(url+"/problem.php?id="+pid).get();
            return d.select("#main h2").first().text();
        } catch (IOException e1) {
            System.out.print("connect timed out");
            //e1.printStackTrace();
        }
        return "";
    }
    private void login(MyClient hc,VjSubmitter s){
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("user_id",s.getUsername()));
        formparams.add(new BasicNameValuePair("password",s.getPassword()));
//        System.out.println("user_id" + s.getUsername());
//        System.out.println("password"+s.getPassword());
        hc.Post(url+"/login.php", formparams);
    }
    private String getLanguage(int l){
        if(l==0){
            return "1";
        }if(l==1){
            return "0";
        }if(l==2){
            return "3";
        }
        return "1";
    }
    public String submit(VjSubmitter s){
        MyClient hc=new MyClient();
        login(hc,s);

        List<NameValuePair> formparams1 = new ArrayList<NameValuePair>();
        formparams1.add(new BasicNameValuePair("language",getLanguage(s.getSubmitInfo().language)));
        formparams1.add(new BasicNameValuePair("id",s.getSubmitInfo().pid));

        //String code= new BASE64Encoder().encode(s.getSubmitInfo().code.getBytes());

        formparams1.add(new BasicNameValuePair("source", s.getSubmitInfo().code));
//        formparams1.add(new BasicNameValuePair("encoded", "1"));
        if(hc.Post(url+"/submit.php",formparams1)==0) return "error";
        else return "success";

    }
    private Result getResultMap(String s){
        return ResultMap.get(s);
    }
    public String getCEInfo(VjSubmitter s){
        MyClient hc=new MyClient();
        login(hc,s);
        Document d = hc.get(url+"/ceinfo.php?sid="+s.getOjsrid());
        return d.select("#errtxt").html();
    }
    public RES getResult(VjSubmitter s){
        Element e;
        Document d = null;
        RES r=new RES();
        try {
            d = Jsoup.connect(url + "/status.php?user_id=" + s.getUsername()).get();
        } catch (IOException e1) {
            e1.printStackTrace();
            r.setR(Result.PENDING);
            return r;
        }
        e = d.select("#result-tab tbody tr").get(0);
//      System.out.println("get:" + e.select("td:nth-child(4)").first().text());
        Result re=getResultMap(e.select("td:nth-child(4)").first().text());
        if(re!=null){
            r.setR(re);
        }else{
            r.setR(Result.ERROR);
        }
        if(r.getR()==Result.AC){
//           System.out.println("time:"+e.select("td:nth-child(5)"));
//           System.out.println("memory:"+e.select("td:nth-child(6)"));
            r.setMemory(e.select("td:nth-child(5)").first().text()+"KB");
            r.setTime(e.select("td:nth-child(6)").first().text() + "MS");
        }
        if(r.getR()==Result.CE){
            r.setCEInfo(getCEInfo(s));
        }
        return r;
    }
}
