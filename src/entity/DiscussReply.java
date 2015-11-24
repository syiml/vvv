package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by Syiml on 2015/7/5 0005.
 */
public class DiscussReply implements IBeanResultSetCreate {
    int rid;
    int did;
    String username;
    Timestamp time;
    String text;
    boolean visiable;
    int panelclass;
    String adminreplay;
    public String getUsername(){return username;}

    @Override
    public DiscussReply init(ResultSet rs) throws SQLException {
        rid=rs.getInt("rid");
        did=rs.getInt("did");
        username=rs.getString("username");
        time=rs.getTimestamp("time");
        text=rs.getString("text");
        visiable=rs.getBoolean("visiable");
        panelclass=rs.getInt("panelclass");
        adminreplay=rs.getString("adminreplay");
        return this;
    }

    public int getRid() {
        return rid;
    }

    public int getDid() {
        return did;
    }

    public Timestamp getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    public boolean isVisiable() {
        return visiable;
    }

    public int getPanelclass() {
        return panelclass;
    }

    public String getAdminreplay() {
        return adminreplay;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setVisiable(boolean visiable) {
        this.visiable = visiable;
    }

    public void setPanelclass(int panelclass) {
        this.panelclass = panelclass;
    }

    public void setAdminreplay(String adminreplay) {
        this.adminreplay = adminreplay;
    }

}
