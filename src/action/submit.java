package action;

import entity.Contest;
import servise.ContestMain;
import util.Main;
import entity.Permission;
import entity.User;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import util.Tool;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Objects;

/**
* Created by Administrator on 2015/5/19.
        */
public class submit {
    public String code;
    public String pid;
    public String language;
    public String cid;
    public void print(HttpResponse r){
        HttpEntity entity = r.getEntity();
        Tool.debug(r.getStatusLine().toString());
        if (entity != null) {
            Tool.debug("Response content lenght:"  + entity.getContentLength());
            String content = null;
            try {
                content = EntityUtils.toString(entity);
                Tool.debug("Response content:"  +content);
                //System.out.println("Response content:"   + new String(content.getBytes("ISO-8859-1"),"UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String submitProblem() {
        try{
            //if(cid==null) cid="-1";
            int iCid = Integer.parseInt(cid);
            User u=Main.loginUser();
            String username;
            if(iCid == -1){
                if(u == null ) return "login";
                if(Main.problems.getProblem(Integer.parseInt(pid)).visiable==0){
                    Permission p=Main.getPermission(u.getUsername());
                    if(!p.getShowHideProblem()){
                        return "nopermission";
                    }
                }
                username = u.getUsername();
            }else{
                Contest contest = ContestMain.getContest(iCid);
                if(contest.getType()==Contest.TYPE_TEAM_OFFICIAL){
                    Object obj = Main.getSession().getAttribute("trueusername"+cid);
                    Tool.log("obj="+obj);
                    if(obj==null) {
                        return "nopermission";
                    }else{
                        username = (String) obj;
                    }
                }else{
                    if(u == null ) return "login";
                    username = u.getUsername();
                }
            }
            Timestamp submittime=Tool.now();

            Tool.log("username="+username);
            int z=Main.submitter.doSubmit(username,Integer.parseInt(pid),Integer.parseInt(cid),Integer.parseInt(language),code,submittime);
            //Main.m.submitProblem(s);
            if(z==-1){
                return "OutOfContest";
            }
            if(cid.equals("-1")){
                //System.out.println("->submit:"+user+" contest:"+cid+" pid="+pid);
                Tool.log("submit:"+username+"提交了"+pid);
                return "success1";
            }else{
                //System.out.println("->submit:"+user+" pid="+pid);
                Tool.log("submit:" + username + "提交了比赛"+cid+"的"+ pid);
                Main.saveURL("Contest.jsp?cid="+cid+"#Status");
                return "success2";
            }
        }catch(NumberFormatException e){
            return "argerror";
        }
    }
}
