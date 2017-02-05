package entity.OJ;

import entity.Result;
import util.Main;
import util.Tool;
import util.Vjudge.VjSubmitter;
import entity.RES;
import util.HTML.problemHTML;

/**
 * 外网OJ基类
 * 新增OJ时要在Submitter.ojs类中添加列表
 * Created by Administrator on 2015/6/6.
 */
public abstract class OTHOJ {
    public static String GET_TITLE_ERROR="获取题目错误！";
    public abstract String getRid(String user,VjSubmitter s);
    public abstract problemHTML getProblemHTML(String pid);
    public abstract String getTitle(String pid);
    public abstract String submit(VjSubmitter s);
    public abstract RES getResult(VjSubmitter s);
    public abstract String getProblemURL(String pid);
    public abstract String getName();
    public abstract String get64IO(String pid);

    public RES getResultReturn(VjSubmitter s){
        int wait[] = {3,2,1,2,3,5,7,8,9,10};
        int i=0;
        RES r;
        do{
            Tool.sleep(wait[i<wait.length?i:wait.length-1]*1000);
            i++;
            r=getResult(s);
            //System.out.println(submitterID+":get res="+r.getR());
            s.setShowstatus("评测结果="+r.getR());
            if(i>=300){
                Main.status.setStatusResult(s.getSubmitInfo().rid, Result.ERROR, "-", "-", "ERROR:评测超时。可能是原oj繁忙");
                break;
            }
        }while(!r.canReturn());
        return r;
    }
}
