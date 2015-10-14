package ProblemTag;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Syiml on 2015/7/24 0024.
 */
public class ProblemTagRecord implements Comparable<ProblemTagRecord>{
    int pid;
    String username;
    int tagid;
    int rating;
    public ProblemTagRecord(ResultSet rs){
        //pid,username,tagid,rating
        try {
            pid=rs.getInt("pid");
            username=rs.getString("username");
            tagid=rs.getInt("tagid");
            rating=rs.getInt("rating");
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
