package Discuss;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by Syiml on 2015/7/5 0005.
 */
public class DiscussReply {
    int rid;
    int did;
    String username;
    Timestamp time;
    String text;
    boolean visiable;
    int panelclass;
    String adminreplay;
    public String getUsername(){return username;}
    public DiscussReply(ResultSet rs) throws SQLException {
        rid=rs.getInt("rid");
        did=rs.getInt("did");
        username=rs.getString("username");
        time=rs.getTimestamp("time");
        text=rs.getString("text");
        visiable=rs.getBoolean("visiable");
        panelclass=rs.getInt("panelclass");
        adminreplay=rs.getString("adminreplay");
    }
}
