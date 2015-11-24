package util;

import entity.OJ.OJ;
import util.HTML.HTML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Administrator on 2015/5/23.
 */
public class ProblemGetter {
    Document doc;
    OJ oj;
    private int TimeOutTimes=20;
    public ProblemGetter(OJ oj,String pid){
        int bo=0;
        this.oj=oj;
        try {
            System.out.println(oj.getProblemURL(pid));
            doc = Jsoup.connect(oj.getProblemURL(pid)).get();
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("connect time out");
            bo++;
        }
        //System.out.println("获取题目超时");
    }
    public String getTitle(){
        return doc.select(oj.getTitleSelect()).get(0).text();
    }
    public String getDesHTML(){
        return doc.select(oj.getDesSelect()).get(0).html();
    }
    public String getInputHTML(){
        return doc.select(oj.getDesSelect()).get(1).html();
    }
    public String getOutputHTML(){
        return doc.select(oj.getDesSelect()).get(2).html();
    }
    public String getSampleInputHTML(){
        Elements e2 = doc.select(oj.getSampleInputSelect());
        if(oj.getName().equals("HDU")) {
            return HTML.code(e2.get(e2.size()-2).text(),false,-1);
        }
        return HTML.code(e2.get(e2.size()-2).html(),false,-1);
    }
    public  String getSampleOutputHTML(){
        Elements e2 = doc.select(oj.getSampleInputSelect());
        if(oj.getName().equals("HDU")) {
            return HTML.code(e2.get(e2.size()-1).text(),false,-1);
        }
        return HTML.code(e2.get(e2.size()-1).html(),false,-1);
    }
}
