package entity.OJ;

import entity.OJ.FOJ.FOJ;
import entity.RES;
import net.sf.json.JSONObject;
import util.JSON.JSON;
import util.Vjudge.SubmitInfo;
import util.Vjudge.VjSubmitter;

/**
 * Created by QAQ on 2017/5/5.
 */
public class TestOJ {
    public static void main(String[] args){
        OTHOJ oj = new FOJ();    //OJ测试类
        String testPid = "1000";     //测试题号
        String testUsername = "spsmoj";//测试用登录目标oj的用户名
        String testPassword = "spsmoj123";//测试用登录目标oj的密码


        VjSubmitter vjSubmitter = new VjSubmitter(0,testUsername,testPassword,0,null);
        vjSubmitter.info=new SubmitInfo(0,"1000",0,"main(){11111111111111111111111111111111111112222222}",false);
        System.out.println("OJ名字是:"+oj.getName());
        System.out.println("OJ的64位整数输出格式（"+testPid+"题）:"+oj.get64IO(testPid));
        System.out.println("OJ的题目链接（"+testPid+"题）:"+oj.getProblemURL(testPid));
        System.out.println("OJ的"+testPid+"题目的标题是:"+oj.getTitle(testPid));
        System.out.println(oj.getProblemHTML("1000"));
        System.out.println("提交"+testPid+"题目"+oj.submit(vjSubmitter));
        System.out.println("账号"+testUsername+"的最近一个提交rid="+oj.getRid(testUsername,vjSubmitter));

        RES res=oj.getResult(vjSubmitter);
        System.out.println("账号"+testUsername+"最近一个提交的评测结果："+res.getR()+" 时间："+res.getTime()+" 内存："+res.getMemory() +" CEInfo:"+res.getCEInfo());
    }
}
