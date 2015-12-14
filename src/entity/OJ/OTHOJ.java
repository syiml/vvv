package entity.OJ;

import util.Vjudge.VjSubmitter;
import entity.RES;
import util.HTML.problemHTML;

/**
 * Created by Administrator on 2015/6/6.
 */
public abstract class OTHOJ {
    public abstract String getRid(String user);
    public abstract problemHTML getProblemHTML(String pid);
    public abstract String getTitle(String pid);
    public abstract String submit(VjSubmitter s);
    public abstract RES getResult(VjSubmitter s);
    public abstract String getProblemURL(String pid);
    public abstract String getName();
}
