package entity.Discuss;

import entity.IBeanResultSetCreate;

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

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void init(ResultSet rs) throws SQLException {
        rid=rs.getInt("rid");
        did=rs.getInt("did");
        username=rs.getString("username");
        time=rs.getTimestamp("time");
        text=rs.getString("text");
        visiable=rs.getBoolean("visiable");
        panelclass=rs.getInt("panelclass");
        adminreplay=rs.getString("adminreplay");
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

    public boolean isVisiable() {
        return visiable;
    }

    public void setVisiable(boolean visiable) {
        this.visiable = visiable;
    }

    public int getPanelclass() {
        return panelclass;
    }

    public void setPanelclass(int panelclass) {
        this.panelclass = panelclass;
    }

    public String getAdminreplay() {
        return adminreplay;
    }

    public void setAdminreplay(String adminreplay) {
        this.adminreplay = adminreplay;
    }

}
