package entity;

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

    public int getMid() {
        return mid;
    }

    public String getUser() {
        return user;
    }

    public int getStatu() {
        return statu;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public long getTime() {
        return time;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setStatu(int statu) {
        this.statu = statu;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }
}
