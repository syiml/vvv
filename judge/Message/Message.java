package Message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by Syiml on 2015/7/10 0010.
 */
public class Message {
    int mid;
    String user;
    int statu;
    String title;
    String text;
    long time;
    Timestamp deadline;
    public Message(){

    }
    public Message(ResultSet rs) throws SQLException {
        mid=rs.getInt("mid");
        title=rs.getString("title");
        user=rs.getString("user");
        statu=rs.getInt("statu");
        text=rs.getString("text");
        time=rs.getTimestamp("time").getTime();
        deadline=rs.getTimestamp("deadline");
    }
}
