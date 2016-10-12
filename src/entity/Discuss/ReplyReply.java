package entity.Discuss;

import entity.IBeanResultSetCreate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * 楼中楼
 * Created by QAQ on 2016/10/10.
 */
public class ReplyReply implements IBeanResultSetCreate {
    /**
     * 属于哪个DiscussReply的
     */
    private int did;
    private int rid;
    private int rrid;

    /**
     * 这条是回复哪个的，如果是-1表示是回复层主的
     */
    private int replyRid;

    private String username;
    private Timestamp time;
    private String text;
    private boolean visible;



    @Override
    public Object init(ResultSet rs) throws SQLException {
        did = rs.getInt("did");
        rid = rs.getInt("rid");
        rrid = rs.getInt("rrid");
        replyRid = rs.getInt("replyRid");
        username = rs.getString("username");
        time = rs.getTimestamp("time");
        text = rs.getString("text");
        visible = rs.getBoolean("visible");
        return this;
    }


    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public int getReplyRid() {
        return replyRid;
    }

    public void setReplyRid(int replyRid) {
        this.replyRid = replyRid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
