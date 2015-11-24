package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Syiml on 2015/7/24 0024.
 */
public class ProblemTagRecord implements Comparable<ProblemTagRecord>,IBeanResultSetCreate {
    public int getPid() {
        return pid;
    }

    public String getUsername() {
        return username;
    }

    public int getTagid() {
        return tagid;
    }

    public int getRating() {
        return rating;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTagid(int tagid) {
        this.tagid = tagid;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    int pid;
    String username;
    int tagid;
    int rating;

    @Override
    public ProblemTagRecord init(ResultSet rs) throws SQLException {
        pid=rs.getInt("pid");
        username=rs.getString("username");
        tagid=rs.getInt("tagid");
        rating=rs.getInt("rating");
        return this;
    }
    public ProblemTagRecord(int pid,String username,int tagid,int rating){
        this.pid=pid;
        this.username=username;
        this.tagid=tagid;
        this.rating=rating;
    }
    public int compareTo(ProblemTagRecord u){
        return u.rating-rating;
    }

}
