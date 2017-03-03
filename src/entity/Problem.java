package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2015/5/22.
 */
public class Problem implements IBeanResultSetCreate, IBeanCanCatch {
    static public int LOCAL=0;
    static public int OTHEROJ=1;
    static public int ONLYDES=2;
    public String Title;
    public String Author;//作者
    public int visiable;//0隐藏1可见
    public boolean spj;
    public int totalSubmit;//总提交量
    public int totalSubmitUser;//总提交人数
    public int totalAc;//总AC量
    public int totalAcUser;//总AC人数
    int pid;
    int type;//LOCAL OR OTHEROJ OR ONLYDES
    //otheroj
    int ojid;
    String ojspid;

    String owner;
    private Timestamp expiredTime;
    public Problem(){}
    public Problem(int ojid,String ojspid,String title,String author,boolean spj){
        this.ojid=ojid;
        this.ojspid=ojspid;
        this.Title=title;
        this.type=OTHEROJ;
        this.Author=author;
        this.spj=spj;
    }

    /**
     * 新增一个本地题
     * @param title
     */
    public Problem(String title){
        this.ojid=0;
        this.ojspid="0";
        this.Title=title;
        this.type=LOCAL;
        this.Author="";
    }
    public Problem(ResultSet r) throws SQLException {
        //pid,type,title,ojid,ojspid,visiable,author,spj
        pid=r.getInt(1);
        type=r.getInt(2);
        Title=r.getString(3);
        ojid=r.getInt(4);
        ojspid=r.getString(5);
        visiable=r.getInt(6);
        Author=r.getString(7);
        spj = r.getBoolean("spj");
    }
    public Problem(ResultSet r,int prid){
        // id,Title
        try {
            Title=r.getString(prid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getTitle(){
        return Title;
    }

    public String getOjspid() { return ojid==7?pid+"":ojspid; }

    public String getAuthor(){return Author;}

    public boolean isLocal(){return type==LOCAL;}

    public int getOjid() { return ojid; }

    public int getType(){
        return type;
    }

    public boolean isSpj(){return spj;}

    @Override
    public void init(ResultSet rs) throws SQLException {
        pid=rs.getInt("pid");
        type=rs.getInt("ptype");
        Title=rs.getString("title");
        ojid=rs.getInt("ojid");
        ojspid=rs.getString("ojspid");
        visiable=rs.getInt("visiable");
        Author=rs.getString("author");
        spj = rs.getBoolean("spj");
        totalSubmit = rs.getInt("totalSubmit");
        totalSubmitUser = rs.getInt("totalSubmitUser");
        totalAc = rs.getInt("totalAc");
        totalAcUser = rs.getInt("totalAcUser");
        owner = rs.getString("owner");
    }


    @Override
    public Timestamp getExpired() {
        return expiredTime;
    }

    @Override
    public void setExpired(Timestamp t) {
        expiredTime = t;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public int getVisiable() {
        return visiable;
    }

    public void setVisiable(int visiable) {
        this.visiable = visiable;
    }

    public void setSpj(boolean spj) {
        this.spj = spj;
    }

    public int getTotalSubmit() {
        return totalSubmit;
    }

    public void setTotalSubmit(int totalSubmit) {
        this.totalSubmit = totalSubmit;
    }

    public int getTotalSubmitUser() {
        return totalSubmitUser;
    }

    public void setTotalSubmitUser(int totalSubmitUser) {
        this.totalSubmitUser = totalSubmitUser;
    }

    public int getTotalAc() {
        return totalAc;
    }

    public void setTotalAc(int totalAc) {
        this.totalAc = totalAc;
    }

    public int getTotalAcUser() {
        return totalAcUser;
    }

    public void setTotalAcUser(int totalAcUser) {
        this.totalAcUser = totalAcUser;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setOjid(int ojid) {
        this.ojid = ojid;
    }

    public void setOjspid(String ojspid) {
        this.ojspid = ojspid;
    }

    public Timestamp getExpiredTime() {
        return expiredTime;
    }

    public String getOwner() {
        return owner;
    }

    public void setExpiredTime(Timestamp expiredTime) {
        this.expiredTime = expiredTime;
    }
}
