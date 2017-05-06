package util.Vjudge;

/**
 * Created by Administrator on 2015/5/21.
 */
public class SubmitInfo {
    public int rid;//评测的rid
    public String pid;//目标oj对应的题目id
    public String code;//代码
    public int language;//语言id 0:G++  1:GCC 2:JAVA
    public boolean rejduge;//是否是重判
    public int getRid() {
        return rid;
    }
    public SubmitInfo(int rid,String pid,int language,String code,boolean rejduge){
        this.rid=rid;
        this.pid=pid;
        this.code=code;
        this.language=language;
        this.rejduge=rejduge;
    }
}
