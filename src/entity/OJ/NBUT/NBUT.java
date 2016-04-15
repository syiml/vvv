package entity.OJ.NBUT;

import entity.OJ.OTHOJ;
import util.Main;
import util.Tool;
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
 * Created by Syiml on 2015/7/4 0004.
 */
public class NBUT extends OTHOJ {
    String url= Main.GV.getJSONObject("nbut").getString("URL");
    MyClient hc = MyClient.getMyClient();
    public NBUT(){
        ResultMap=new HashMap<String, Result>();
        ResultMap.put("ACCEPTED",Result.AC);
        ResultMap.put("WRONG_ANSWER",Result.WA);
        ResultMap.put("PRESENTATION_ERROR",Result.PE);
        ResultMap.put("RUNTIME_ERROR [ACCESS_VIOLATION]",Result.RE);
        ResultMap.put("RUNTIME_ERROR",Result.RE);
        ResultMap.put("RUNTIME_ERROR [INTEGER_DIVIDE_BY_ZERO]",Result.RE);
        ResultMap.put("RUNTIME_ERROR [STACK_OVERFLOW]",Result.RE);
        ResultMap.put("TIME_LIMIT_EXCEEDED",Result.TLE);
        ResultMap.put("MEMORY_LIMIT_EXCEEDED",Result.MLE);
        ResultMap.put("OUTPUT_LIMIT_EXCEEDED",Result.OLE);
        ResultMap.put("COMPILATION_ERROR",Result.CE);
        ResultMap.put("QUEUING",Result.JUDGING);
        ResultMap.put("RUNNING",Result.JUDGING);
        ResultMap.put("COMPILING",Result.JUDGING);
    }
    public String getName(){
        return "NBUT";
    }
    public String getRid(String user){
        Element e;
        Document d;
        try {
            d = MyClient.getMyClient().get(url+"/Problem/status.xhtml?username="+user);
            e = d.select("#prob-list-wrapper tbody tr").first();
            if(e==null) return "new";
            return e.select("td:nth-child(1)").first().text();
        } catch (Exception ignored) {}
        return "error";
    }
    public String getProblemURL(String pid){
        return url+"/Problem/view.xhtml?id="+pid;
    }
    public problemHTML getProblemHTML(String pid){
        problemHTML ph=new problemHTML();
        Element e=null;
        Document d = null;
        d = MyClient.getMyClient().get(url + "/Problem/view.xhtml?id=" + pid);
        if(d == null) return ph;
        Elements es=d.select("img");
        for(Element ee:es){
            String link=ee.attr("src");
            ee.attr("src",url+"/"+link);
        }

        ph.setTitle(d.select("#title").first().text());
        ph.setDis(d.select("#description").first().text());
        ph.setInput(d.select("#input").first().text());
        ph.setOutput(d.select("#output").first().text());
        ph.addSample("<pre style='padding:0px;border-style:none;background-color:transparent'>"
                +d.select("#sampleinput").first().text()+"</pre>",
                "<pre style='padding:0px;border-style:none;background-color:transparent'>"
                +d.select("#sampleoutput").first().text()+"</pre>" );
        ph.setInt64("%I64d");
        String limit=d.select("#limit").text();
        ph.setTimeLimit(limit.substring(limit.indexOf("时间限制: ")+5,limit.indexOf("内存限制:")-1));
        ph.setMenoryLimit(limit.substring(limit.indexOf("内存限制: ")+5));
        return ph;
    }
    public String getTitle(String pid){
        Document d = MyClient.getMyClient().get(url + "/Problem/view.xhtml?id=" + pid);
        if(d==null) return GET_TITLE_ERROR;
        return d.select("#title").first().text();
    }
    private String getOJVERIFY(){
        Document d = MyClient.getMyClient().get(url + "/User/login.xhtml?url=%2F");
        if(d==null) return "";
        String s=d.select("#login-left-form").first().html();
        int z=s.indexOf("__OJVERIFY__");
        System.out.println(s.substring(z+21,z+21+32));
        return s.substring(z+21,z+21+32);
    }
    private String getLanguage(int l){
        if(l==0){
            return "2";
        }if(l==1){
            return "1";
        }if(l==2){
            return "0";
        }
        return "0";
    }
    public String submit(VjSubmitter s){
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("username",s.getUsername()));
        formparams.add(new BasicNameValuePair("password",s.getPassword()));
        formparams.add(new BasicNameValuePair("__OJVERIFY__",getOJVERIFY()));
        if(hc.Post(url+"/User/chklogin.xhtml", formparams)==0) return "error";
        else {
            List<NameValuePair> formparams1 = new ArrayList<NameValuePair>();
            formparams1.add(new BasicNameValuePair("language",getLanguage(s.getSubmitInfo().language)));
            formparams1.add(new BasicNameValuePair("id",s.getSubmitInfo().pid));
            formparams1.add(new BasicNameValuePair("code",s.getSubmitInfo().code));
            if(hc.Post(url+"/Problem/submitok.xhtml",formparams1)==0) return "error";
            else return "success";
        }
    }
    public static Map<String,Result> ResultMap;
    private Result getResultMap(String s){
        return ResultMap.get(s);
    }
    public String getCEInfo(VjSubmitter s){
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("username",s.getUsername()));
        formparams.add(new BasicNameValuePair("password",s.getPassword()));
        formparams.add(new BasicNameValuePair("__OJVERIFY__",getOJVERIFY()));
        if(hc.Post(url+"/User/chklogin.xhtml", formparams)==0) return "error";
        else {
            Element e;
            Document d = hc.get(url+"/Problem/viewce.xhtml?submitid="+s.getOjsrid());
            //d = Jsoup.connect(url+"/Problem/viewce.xhtml?submitid="+s.getOjsrid()).get();
            return d.select("#prob-info-ul pre").html();
        }
    }
    public RES getResult(VjSubmitter s){
        Element e;
        Document d = null;
        RES r=new RES();
        d = MyClient.getMyClient().get(url + "/Problem/status.xhtml?username=" + s.getUsername());
        e = d.select("#prob-list-wrapper tbody tr").first();
//        System.out.println("get:" + e.select("td:nth-child(4)").first().text());
        Result re=getResultMap(e.select("td:nth-child(4)").first().text());
        if(re!=null){
            r.setR(re);
        }else{
            r.setR(Result.ERROR);
        }
        if(r.getR()==Result.AC){
//            System.out.println("time:"+e.select("td:nth-child(5)"));
//            System.out.println("memory:"+e.select("td:nth-child(6)"));
            r.setTime(e.select("td:nth-child(5)").first().text()+"MS");
            r.setMemory(e.select("td:nth-child(6)").first().text()+"KB");
        }
        if(r.getR()==Result.CE){
            r.setCEInfo(getCEInfo(s));
        }
        return r;
    }
}
