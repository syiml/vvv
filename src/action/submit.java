package action;

import util.Main;
import entity.Permission;
import entity.User;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.sql.Timestamp;
/**
* Created by Administrator on 2015/5/19.
        */
public class submit {
    public String code;
    public String pid;
    public String language;
    public String cid;
    //    public String user;
    public HttpClient hc = new DefaultHttpClient();
    public void print(HttpResponse r){
        HttpEntity entity = r.getEntity();
        Main.debug(r.getStatusLine().toString());
        if (entity != null) {
            Main.debug("Response content lenght:"  + entity.getContentLength());
            String content = null;
            try {
                content = EntityUtils.toString(entity);
                Main.debug("Response content:"  +content);
                //System.out.println("Response content:"   + new String(content.getBytes("ISO-8859-1"),"UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String submitProblem() {
        try{
            //if(cid==null) cid="-1";
            User u=(User)Main.getSession().getAttribute("user");
            if(u==null){
                return "login";
            }
            if(cid.equals("-1")){
                if(Main.problems.getProblem(Integer.parseInt(pid)).visiable==0){
                    Permission p=Main.getPermission(u.getUsername());
                    if(!p.getShowHideProblem()){
                        return "nopermission";
                    }
                }
            }
            Timestamp submittime=new Timestamp(System.currentTimeMillis());
            String user=u.getUsername();
            int z=Main.doSubmit(user,Integer.parseInt(pid),Integer.parseInt(cid),Integer.parseInt(language),code,submittime);
            //Main.m.submitProblem(s);
            if(z==-1){
                return "OutOfContest";
            }
            if(cid.equals("-1")){
                //System.out.println("->submit:"+user+" contest:"+cid+" pid="+pid);
                Main.log("submit:"+user+"提交了"+pid);
                return "success1";
            }else{
                //System.out.println("->submit:"+user+" pid="+pid);
                Main.log("submit:" + user + "提交了比赛"+cid+"的"+ pid);
                Main.saveURL("Contest.jsp?cid="+cid+"#Status");
                return "success2";
            }
        }catch(NumberFormatException e){
            return "argerror";
        }
    }
}
