package entity.OJ;

import entity.Result;
import util.Main;
import util.Tool;
import util.Vjudge.VjSubmitter;
import entity.RES;
import util.HTML.problemHTML;
import util.Submitter;
/**
 * 外网OJ基类 （Other OJ）
 * 新增OJ时要在Submitter.ojs类中添加列表
 * Created by Administrator on 2015/6/6.
 */
public abstract class OTHOJ {
    protected static String GET_TITLE_ERROR="获取题目错误！";

    /**
     * 获取某账号在目标oj上最近提交过的rid
     *
     * 如果提交的http请求无法返回rid，那么实现这个方法。
     *      评测时，先获取rid。然后提交，再获取rid。如果两次rid不同则表示提交成功，rid为第二次获取的rid
     * @param user 用户名
     * @param s 提交器
     * @return rid
     */
    public abstract String getRid(String user,VjSubmitter s);

    /**
     * 获取题目描述
     * @param pid 目标oj题目id
     * @return 返回题目描述的内容
     */
    public abstract problemHTML getProblemHTML(String pid);

    /**
     * 获取题目标题
     * @param pid 目标oj题目id
     * @return 题目标题
     */
    public abstract String getTitle(String pid);

    /**
     * 提交代码
     * @param s 提交器。提交内容在s.getSubmitInfo()
     * @return 字符串
     */
    public abstract String submit(VjSubmitter s);

    /**
     * 获取评测结果
     * @param s 提交器
     * @return 返回的结果
     */
    public abstract RES getResult(VjSubmitter s);

    /**
     * 根据pid返回查看题目的链接
     * @param pid 目标oj题号
     * @return 链接
     */
    public abstract String getProblemURL(String pid);

    /**
     * @return OJ的名字
     */
    public abstract String getName();

    /**
     * 返回  %I64d 或者 %lld
     * @param pid 题号
     * @return 64位整数的表示方式
     */
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
