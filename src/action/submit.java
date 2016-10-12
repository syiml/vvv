package action;

import entity.Contest;
import entity.Contest_Type;
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
public class submit  extends BaseAction{
    public String code;
    public int pid;
    public int language;
    public int cid;
    public int noRedirect = 0; //如果是app提交的返回json

    public String submitProblem() {
        try{
            if(code.length()<=50){
                if(noRedirect == 1) {
                    out.print("{\"ret\":\"fail\",\"info\":\"代码长度至少为50\"}");
                    return NONE;
                }
                return INPUT;
            }
            //if(cid==null) cid="-1";
            User u=Main.loginUser();
            if(u == null ){
                if(noRedirect == 1){
                    out.print("{\"ret\":\"fail\",\"info\":\"登录已过期\"}");
                    return NONE;
                }
                return "login";
            }
            String username;
            if(cid == -1){
                if(Main.problems.getProblem(pid).visiable==0){
                    Permission p=Main.getPermission(u.getUsername());
                    if(!p.getShowHideProblem()){
                        if(noRedirect == 1){
                            out.print("{\"ret\":\"fail\",\"info\":\"没有权限\"}");
                            return NONE;
                        }
                        return "nopermission";
                    }
                }
                username = u.getUsername();
            }else{
                Contest contest = ContestMain.getContest(cid);
                if(contest.getType() == Contest_Type.TEAM_OFFICIAL){
                    Object obj = Main.getSession().getAttribute("trueusername"+cid);
                    //Tool.log("obj="+obj);
                    if(obj==null) {
                        if(noRedirect == 1){
                            out.print("{\"ret\":\"fail\",\"info\":\"没有权限\"}");
                            return NONE;
                        }
                        return "nopermission";
                    }else{
                        username = (String) obj;
                    }
                }else{
                    username = u.getUsername();
                }
            }
            int z=Main.submitter.doSubmit(username,pid,cid,language,code,Tool.now());
            if(z==-1){
                if(noRedirect == 1){
                    out.print("{\"ret\":\"fail\",\"info\":\"比赛已经结束\"}");
                    return NONE;
                }
                return "OutOfContest";
            }
            if(cid==-1){
                Tool.log("submit:"+username+"提交了"+pid);
                if(noRedirect == 1){
                    out.print("{\"ret\":\"success\"}");
                    return NONE;
                }
                return "success1";
            }else{
                Tool.log("submit:" + username + "提交了比赛"+cid+"的"+ pid);
                Main.saveURL("Contest.jsp?cid="+cid+"#Status");
                if(noRedirect == 1){
                    out.print("{\"ret\":\"success\"}");
                    return NONE;
                }
                return "success2";
            }
        }catch(Exception e){
            if(noRedirect == 1){
                out.print("{\"ret\":\"fail\",\"info\":\"提交失败\"}");
                return NONE;
            }
            return INPUT;
        }
    }
}
