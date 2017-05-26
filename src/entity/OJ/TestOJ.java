package entity.OJ;

import entity.RES;
import util.Vjudge.VjSubmitter;

/**
 * Created by QAQ on 2017/5/5.
 */
public class TestOJ {
    public static void main(String[] args){
        OTHOJ oj = new Acdream();    //OJ测试类
        String testPid = "1000";     //测试题号
        String testUsername = "spsmoj";//测试用登录目标oj的用户名
        String testPassword = "spsmoj123";//测试用登录目标oj的密码


        VjSubmitter vjSubmitter = new VjSubmitter(0,testUsername,testPassword,0,null);
        System.out.println("OJ名字是:"+oj.getName());
        System.out.println("OJ的64位整数输出格式（"+testPid+"题）:"+oj.get64IO(testPid));
        System.out.println("OJ的题目链接（"+testPid+"题）:"+oj.getProblemURL(testPid));
        System.out.println("OJ的"+testPid+"题目的标题是:"+oj.getTitle(testPid));
        System.out.println("账号"+testUsername+"的最近一个提交rid="+oj.getRid(testUsername,vjSubmitter));

        RES res=oj.getResult(vjSubmitter);
        System.out.println("账号"+testUsername+"最近一个提交的评测结果："+res.getR()+" 时间："+res.getTime()+" 内存："+res.getMemory() +" CEInfo:"+res.getCEInfo());
    }
}
