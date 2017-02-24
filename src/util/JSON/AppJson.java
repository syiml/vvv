package util.JSON;

import entity.Problem;
import entity.User;
import net.sf.json.JSONObject;
import util.HTML.HTML;
import util.HTML.problemHTML;
import util.Main;
import util.Submitter;

/**
 * Created by syimlzhu on 2016/9/18.
 */
public class AppJson {
    public static String getSelfUserInfo(){
        User u = Main.loginUser();
        if(u == null){
            return "{\"ret\":\"NotSigned\"}";
        }else{
            return JSON.getJSONObject(
                    "ret","success",
                    "rank",u.getRank()+"",
                    "username",u.getUsername(),
                    "nick",u.getNick(),
                    "motto",u.getMotto(),
                    "gender",u.getGender()+"",
                    "school",u.getSchool(),
                    "acb",u.getAcb()+"",
                    "acnum",u.getAcnum()+"",
                    "submitnum",Main.status.getSubmitTime(u.getUsername())+"",
                    "rating",u.getShowRating()+"",
                    "ratingnum",u.getRatingnum()+"",
                    "email",u.getEmail()
                   // "headImg", "pic/head/"+u.getUsername()+".jpg"
            ).toString();
        }
    }
    public static JSONObject getProblem(int pid){
        Problem p = Main.problems.getProblem(pid);
        if(p==null || p.visiable != 1){
            return JSON.getJSONObject("ret","error");
        }else{
            problemHTML ph = Main.getProblemHTML(pid);
            if(ph == null){
                return JSON.getJSONObject("ret","error");
            }
            JSONObject jo = JSON.getJSONObject(
                    "ret","success",
                    "pid",pid+"",
                    "title",p.getTitle(),
                    "type",p.getType()+"",//0:LOCAL,1:OTHOJ
                    "oj", p.getType()==0?"本OJ":Submitter.ojs[p.getOjid()].getName(),
                    "ojpid",p.getOjspid(),
                    "int64",ph.getInt64(),
                    "timelimit",ph.getTimeLimit(),
                    "memoryLimit",ph.getMenoryLimit(),
                    "spj",ph.isSpj()+"",//true/false
                    "des",ph.getDis(),
                    "input",ph.getInput(),
                    "output",ph.getOutput()
            );
            jo.put("sampleinput",ph.getSampleInput());//数组
            jo.put("sampleoutput",ph.getSampleOutput());//数组
            return jo;
        }
    }
}
