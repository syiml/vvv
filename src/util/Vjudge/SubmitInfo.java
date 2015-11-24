package util.Vjudge;

/**
 * Created by Administrator on 2015/5/21.
 */
public class SubmitInfo {
    public int rid;
    public String pid;
    public String code;
    public int language;
    public boolean rejduge;
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
