package Main.problem;

import Main.Main;
import Main.OJ.OJ;
import Tool.HTML.problemHTML.problemHTML;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2015/5/22.
 */
public class Problem {
    int type;//LOCAL OR OTHEROJ OR ONLYDES
    public String Title;
    //local  des
    public String Description;//题目描述
    public String Input;
    public String Output;
    public String SampleInput;
    public String SampleOutput;
    public String Hint;//提示
    public String Author;//作者
    public int visiable;//0隐藏1可见
    //otheroj
    int ojid;
    String ojspid;
    public Problem(int ojid,String ojspid,String title){
        this.ojid=ojid;
        this.ojspid=ojspid;
        this.Title=title;
        this.type=1;
    }
    public Problem(Problem p){
        type=p.type;
        Title=p.getTitle();
        Description=p.Description;
        Input=p.Input;
        Output=p.Output;
        SampleInput=p.SampleInput;
        SampleOutput=p.SampleOutput;
        Hint=p.Hint;
        Author=p.Author;
        ojid=p.ojid;
        ojspid=p.ojspid;
    }
    public Problem(ResultSet r) throws SQLException {
        //pid,type,title,ojid,ojspid,visiable
            type=r.getInt(2);
            Title=r.getString(3);
            ojid=r.getInt(4);
            ojspid=r.getString(5);
            visiable=r.getInt(6);
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
    public boolean isLocal(){return type==LOCAL;}
    public int getOjid() { return ojid; }
    public int getType(){
        return type;
    }
    static public int LOCAL=0;
    static public int OTHEROJ=1;
    static public int ONLYDES=2;
}
