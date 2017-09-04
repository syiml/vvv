package entity;

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
    private String win;



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
        win = rs.getString("win");
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

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }
}
