package entity.OJ;

import entity.OJ.FOJ.FOJ;
import entity.OJ.HDU.HDU;
import entity.RES;
import net.sf.json.JSONObject;
import util.HTML.problemHTML;
import util.JSON.JSON;
import util.Tool;
import util.Vjudge.SubmitInfo;
import util.Vjudge.VjSubmitter;

/**
 * Created by QAQ on 2017/5/5.
 */
public class TestOJ {
    public static void main(String[] args){
        OTHOJ oj = new FOJ();    //OJ测试类
        String testPid = "1900";     //测试题号
        String testUsername = "spsmoj";//测试用登录目标oj的用户名
        String testPassword = "spsmoj123";//测试用登录目标oj的密码
        String code = "#include<stdio.h>\nint main(){int a,b;while(~scanf(\"%d%d\",&a,&b))printf(\"%d\\n\",a+b);}";

        VjSubmitter vjSubmitter = new VjSubmitter(0,testUsername,testPassword,0,null);
        vjSubmitter.info=new SubmitInfo(0,testPid,0,code,false);
        System.out.println("OJ名字是:"+oj.getName());
        System.out.println("OJ的64位整数输出格式（"+testPid+"题）:"+oj.get64IO(testPid));
        System.out.println("OJ的题目链接（"+testPid+"题）:"+oj.getProblemURL(testPid));
        System.out.println("OJ的"+testPid+"题目的标题是:"+oj.getTitle(testPid));

        problemHTML ph = oj.getProblemHTML(testPid);
        System.out.println("时间："+ph.getTimeLimit());
        System.out.println("内存："+ph.getMenoryLimit());
        System.out.println("SPJ："+ph.isSpj());
        System.out.println("====题目描述====\n"+ph.getDis());
        System.out.println("====输入描述====\n"+ph.getInput());
        System.out.println("====输出描述====\n"+ph.getOutput());
        for(int i=0;i<ph.getSampleInput().size();i++){
            System.out.println("====输入样例"+i+"====\n"+ph.getSampleInput().get(i));
            System.out.println("====输出样例"+i+"====\n"+ph.getSampleOutput().get(i));
        }

        String prid = oj.getRid(testUsername,vjSubmitter);
        System.out.println("账号"+testUsername+"的最近一个提交rid="+prid);
        System.out.println("提交" + testPid + "题目");
        oj.submit(vjSubmitter);
        int k = 0;
        do {
            Tool.sleep(1000);
            String nrid = oj.getRid(testUsername, vjSubmitter);
            System.out.println("账号" + testUsername + "的提交之后的rid=" + nrid);
            if(!nrid.equals(prid)){
                System.out.println("rid改变了,提交成功");

                do{
                    RES res=oj.getResult(vjSubmitter);
                    System.out.println("账号"+testUsername+"最近一个提交的评测结果："+res.getR()+" 时间："+res.getTime()+" 内存："+res.getMemory() +" CEInfo:"+res.getCEInfo());
                    if(res.canReturn()){
                        System.out.println("评测结束");
                        break;
                    }
                    Tool.sleep(1000);
                }while(k<10);

                break;
            }else{
                System.out.println("rid没改变,提交失败，重新获取rid");
            }
            k++;
        }while(k<10);
    }
}
