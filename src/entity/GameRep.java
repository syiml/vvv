package entity;

import dao.AiSQL;
import net.sf.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by QAQ on 2017/9/4.
 */
public class GameRep implements IBeanCanCatch,IBeanResultSetCreate {

    private int id;
    private int blackId;
    private String blackAuthor;
    private int whiteId;
    private String whiteAuthor;
    private JSONObject processes;
    private int win;
    private Timestamp time;



    Timestamp expired;
    @Override
    public Timestamp getExpired() {
        return expired;
    }

    @Override
    public void setExpired(Timestamp t) {
        expired = t;
    }

    @Override
    public void init(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        blackId = rs.getInt("blackId");
        blackAuthor = rs.getString("blackAuthor");
        whiteId = rs.getInt("whiteId");
        whiteAuthor = rs.getString("whiteAuthor");
        processes = JSONObject.fromObject(rs.getString("processes"));
        win = rs.getInt("win");
        time = rs.getTimestamp("time");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBlackId() {
        return blackId;
    }

    public void setBlackId(int blackId) {
        this.blackId = blackId;
    }

    public String getBlackAuthor() {
        return blackAuthor;
    }

    public void setBlackAuthor(String blackAuthor) {
        this.blackAuthor = blackAuthor;
    }

    public int getWhiteId() {
        return whiteId;
    }

    public void setWhiteId(int whiteId) {
        this.whiteId = whiteId;
    }

    public String getWhiteAuthor() {
        return whiteAuthor;
    }

    public void setWhiteAuthor(String whiteAuthor) {
        this.whiteAuthor = whiteAuthor;
    }

    public JSONObject getProcesses() {
        return processes;
    }

    public void setProcesses(JSONObject processes) {
        this.processes = processes;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getBlack(){
        if(blackId == 0){
            return blackAuthor;
        }
        return AiSQL.getInstance().getBeanByKey(blackId).getAiName();
    }
    public String getWhite(){
        if(whiteId == 0){
            return whiteAuthor;
        }
        return AiSQL.getInstance().getBeanByKey(whiteId).getAiName();
    }
}
