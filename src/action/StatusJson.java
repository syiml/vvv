package action;

import entity.Contest;
import entity.Result;
import entity.Status;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import servise.ContestMain;
import util.SQL.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Syiml on 2015/11/15 0015.
 */
public class StatusJson extends BaseAction{
    int cid;
    public String getJson(){
        //{pnum,status:[{rid,pid,username,result,time},...]}
        Contest c= ContestMain.getContest(cid);
        JSONObject data=new JSONObject();
        data.put("pnum",c.getProblemNum());
        JSONArray status=new JSONArray();
        SQL sql=new SQL("select id,ruser,pid,submittime,result from statu where cid=?",cid);
        ResultSet rs=sql.query();
        try {
            while(rs.next()){
                JSONObject astatus=new JSONObject();
                astatus.put("rid",rs.getInt("id"));
                astatus.put("pid",c.getcpid(rs.getInt("pid")));
                astatus.put("username",rs.getString("ruser"));
                int res=rs.getInt("result");
                if(Status.intToResult(res)== Result.AC){
                    astatus.put("result",1);
                }else if(Status.intToResult(res)== Result.PENDING|| Status.intToResult(res)== Result.JUDGING){
                    astatus.put("result",-1);
                }else astatus.put("result",0);
                astatus.put("time",(rs.getTimestamp("submittime").getTime()-c.getBeginDate().getTime())/1000);
                status.add(astatus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            sql.close();
        }
        data.put("status",status);
        out.println(data.toString());
        return null;
    }

    public int getCid() {
        return cid;
    }
    public void setCid(int cid) {
        this.cid = cid;
    }
}
