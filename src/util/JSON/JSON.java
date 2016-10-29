package util.JSON;

import entity.*;
import entity.OJ.OTHOJ;
import dao.ratingSQL;
import org.jsoup.nodes.Document;
import servise.ContestMain;
import util.*;
import dao.MessageSQL;
import util.HTML.problemListHTML.problemListFilterHTML.problemListFilterHTML;
import util.HTML.problemListHTML.problemView;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Syiml on 2015/7/10 0010.
 */
public class JSON {
    private static String otherOjsContest = "{}";
    private static Timestamp nextOtherOjsContestTime = new Timestamp(0);

    public static String Messages(String from,String num){
        User user=Main.loginUser();
        int fromInt=0,numInt=20;
        try{
            fromInt=Integer.parseInt(from);
            numInt=Integer.parseInt(num);
        }catch(NumberFormatException ignored){ }
        if(user==null){
            return "[]";
        }
        List<Message> list= MessageSQL.getMessages(user.getUsername(), fromInt, numInt);
        MessageSQL.setReaded(user.getUsername());
        Gson gson=new Gson();
        return gson.toJson(list);
    }

    /**
     * 通过cid获得contest的基础信息
     * @return {cid,name,begintime,endtime,type,now,admin,rating,info,computerating}
     */
    public static String getContestInfo(int cid){
        Contest c= ContestMain.getContest(cid);
        JSONObject ret=new JSONObject();
        ret.put("cid",cid);
        ret.put("name",c.getName());
        ret.put("begintime",c.getBeginDate().getTime());
        ret.put("endtime",c.getEndTime().getTime());
        ret.put("type",c.getType());
        ret.put("now", Tool.now().getTime());
        Permission p=Main.loginUserPermission();
        ret.put("admin", p.getAddContest());
        List<RatingCase> list= ratingSQL.getRating(cid);
        ret.put("rating",p.getComputrating()||list.size()!=0);
        ret.put("info",c.getInfo());
        ret.put("computerating",c.isComputerating());
        ret.put("compare",p.getComputrating());
        return ret.toString();
    }

    /**
     *  通过cid获得题目列表
     *  @return [{result,pid,title,acnum,submitnum},...]
     */
    public static String getContestProblemList(int cid){
        User u=Main.loginUser();
        if((u!=null&&u.getPermission().getAddContest())||ContestMain.getContest(cid).isBegin()){
            List<problemView> list=Main.problems.getProblems(cid);
            JSONArray ja=new JSONArray();
            for(problemView pv:list){
                int result;
                if(ContestMain.getContest(cid).getType() == Contest_Type.TEAM_OFFICIAL) {
                    result = Main.status.submitResult(cid, pv.getPid(), (String) Main.getSession().getAttribute("trueusername" + cid));
                }else{
                    assert u != null;
                    if(ContestMain.getContest(cid).getKind() == 0){
                        result = Main.status.submitResult(pv.getPid(),u.getUsername());
                    }else {
                        result = Main.status.submitResult(cid, pv.getPid(), u.getUsername());
                    }
                }
                JSONObject jo=new JSONObject();
                jo.put("result",result);
                jo.put("pid",pv.getPid());
                jo.put("title",pv.getTitle());
                jo.put("acnum",pv.getAc());
                jo.put("submitnum",pv.getSubmit());
                ja.add(jo);
            }
            return ja.toString();
        }else{
            return "[]";
        }
    }

    /**
     * 通过题目标签id获取题目列表
     * @param tag 题目标签id
     * @param page 页，从0开始
     * @return {islast,text} islast返回true表示当前是最后一页 text返回显示表格的HTML代码
     */
    public static String getProblemByTag(int tag,int page){
        problemListFilterHTML p=new problemListFilterHTML(tag,page);
        JSONObject jo=new JSONObject();
        jo.put("text",p.HTML());
        jo.put("islast",p.islast());
        return jo.toString();
    }

    /**
     * 获取指定题目的标题，并获得当前题库内是否已经有这题
     * @param ojid ojid
     * @param ojspid 对应OJ的Pid
     * @return {title,list:[localpid,...]}
     */
    public static String getProblemValidate(String ojid,String ojspid){
        JSONObject jo=new JSONObject();
        if(ojid==null||ojspid==null) return "";
        try{
            OTHOJ oj= Submitter.ojs[Integer.parseInt(ojid)];
            jo.put("title",oj.getTitle(ojspid));
            List<Integer> list=Main.problems.getProblemsByOjPid(Integer.parseInt(ojid),ojspid);
            JSONArray ja=new JSONArray();
            for(Integer i:list){
                ja.add(i);
            }
            jo.put("list",ja);
            return jo.toString();
        }catch (NumberFormatException e){
            return "{title:\"获取错误\",list:[]}";
        }
    }

    /**
     * 获取某用户最近提交和AC统计记录
     * @param user 用户，如果为null则统计全部
     * @param num 最近多少个时间区间的记录
     * @param sec 每个时间区间的秒数
     * @return {ac:[ac1,ac2,ac3,...,acnum],submit:[s1,s2,s3,...,snum],user,num,sec}
     */
    public static String getSubmitCount(String user,int num,int sec){
        if(num>1000) num=1000;
        List<Pair<Integer,Integer>> rsSubmit=Main.status.getSubmitCount(user, num, sec);
        List<Pair<Integer,Integer>> rsAc=Main.status.getAcCount(user, num, sec);
        JSONObject jo=new JSONObject();
        if(user!=null)jo.put("user",user);
        jo.put("num",num);
        jo.put("sec",sec);
        JSONArray sb_ja=new JSONArray();
        JSONArray ac_ja=new JSONArray();
        int now=0;
        for(Pair<Integer,Integer> ep:rsSubmit){
            int t=ep.getKey();
            int count=ep.getValue();
            while(now<t&&now<num){
                sb_ja.add(0);
                now++;
            }
            if(now==t)sb_ja.add(count);
            now++;
        }
        while(now<num){sb_ja.add(0);now++;}
        now=0;
        for(Pair<Integer,Integer> ep:rsAc){
            int t=ep.getKey();
            int count=ep.getValue();
            while(now<t&&now<num){
                ac_ja.add(0);
                now++;
            }
            if(now==t)ac_ja.add(count);
            now++;
        }
        while(now<num) {
            ac_ja.add(0);now++;}
        jo.put("ac",ac_ja);
        jo.put("submit",sb_ja);
        return jo.toString();
    }

    public static String getOtherOjsContest(){
        if(nextOtherOjsContestTime.before(Tool.now())) {
            Document d = new MyClient().get("http://contests.acmicpc.info/contests.json");
            if(d != null) {//拉取成功一小时后再拉取
                otherOjsContest = d.text();
                nextOtherOjsContestTime = new Timestamp(Tool.now().getTime() + 1000*60*60);
            }else{//拉取失败10分钟后再拉取
                nextOtherOjsContestTime = new Timestamp(Tool.now().getTime() + 1000*60*10);
            }
        }
        return otherOjsContest;
    }

    public static JSONObject getJSONObject(String ... args){
        JSONObject json = new JSONObject();
        for(int i=0;i+1<args.length;i+=2){
            json.put(args[i],args[i+1]);
        }
        return json;
    }
}
