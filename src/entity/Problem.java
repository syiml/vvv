package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2015/5/22.
 */
public class Problem {
    int type;//LOCAL OR OTHEROJ OR ONLYDES
    public String Title;
    public String Author;//作者
    public int visiable;//0隐藏1可见
    //otheroj
    int ojid;
    String ojspid;
    public boolean spj;
    public Problem(int ojid,String ojspid,String title,String author,boolean spj){
        this.ojid=ojid;
        this.ojspid=ojspid;
        this.Title=title;
        this.type=1;
        this.Author=author;
        this.spj=spj;
    }
    public Problem(String title){
        this.ojid=0;
        this.ojspid="0";
        this.Title=title;
        this.type=0;
        this.Author="";
    }
    public Problem(ResultSet r) throws SQLException {
        //pid,type,title,ojid,ojspid,visiable,author,spj
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
    public String getOjspid() { return ojspid; }
    public String getAuthor(){return Author;}
    public boolean isLocal(){return type==LOCAL;}
    public int getOjid() { return ojid; }
    public int getType(){
        return type;
    }
    public boolean isSpj(){return spj;}
    static public int LOCAL=0;
    static public int OTHEROJ=1;
    static public int ONLYDES=2;
}
