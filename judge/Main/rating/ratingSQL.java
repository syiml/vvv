package Main.rating;

import Main.Main;
import Main.User.User;
import Main.contest.Contest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Message.MessageSQL;
import com.google.gson.Gson;

/**
 * Created by Syiml on 2015/7/3 0003.
 */
public class ratingSQL {
    public static void save(RatingCase r){
        Contest c=Main.contests.getContest(r.cid);
        PreparedStatement p;
        try{
            p= Main.conn.prepareStatement("INSERT INTO t_rating VALUES(?,?,?,?,?,?,?) ");
            p.setString(1, r.username);
            p.setTimestamp(2, r.getTime());
            p.setInt(3, r.cid);
            p.setInt(4,r.prating);
            p.setInt(5,r.rating);
            p.setInt(6,r.ratingnum);
            p.setInt(7,r.rank);
            p.executeUpdate();

            p= Main.conn.prepareStatement("UPDATE users set ratingnum=ratingnum+1,rating=? WHERE username=?");
            p.setInt(1,r.rating);
            p.setString(2,r.username);

            int prating=Main.users.getUser(r.username).getShowRating();
            p.executeUpdate();
            int newrating=Main.users.getUser(r.username).getShowRating();
            MessageSQL.AddMessageRatingChange(r.cid,r.username,prating,newrating);
            System.out.println(r.username + ":" + r.rating);
        }catch(SQLException e){
            System.out.println("rating uptate error");
        }
    }
    public static List<RatingCase> getRating(int cid){
        List<RatingCase> list=new ArrayList<RatingCase>();
        PreparedStatement p;
        try {
            p= Main.conn.prepareStatement("SELECT username,time,cid,prating,rating,ratingnum,rank,(select name from contest where id=cid) as cname FROM t_rating WHERE cid=? order by rank");
            p.setInt(1,cid);
            //System.out.println(p);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                list.add(new RatingCase(rs.getString(1),rs.getTimestamp(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),rs.getString("cname")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static List<RatingCase> getRating(String username){
        List<RatingCase> list=new ArrayList<RatingCase>();
        PreparedStatement p;
        try {
            p= Main.conn.prepareStatement("SELECT username,time,cid,prating,rating,ratingnum,rank,(select name from contest where id=cid) as cname FROM t_rating WHERE username=? order by ratingnum desc");
            p.setString(1, username);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                list.add(new RatingCase(rs.getString(1),rs.getTimestamp(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),rs.getString("cname")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static String getJson(String username,boolean t){
        List<RatingCase> list=getRating(username);
        for(RatingCase r:list){
            if(t){
                r.rating= r.getRating();
            }else{
                r.rating= User.getShowRating(r.getRatingnum(),r.getRating());
            }
        }
        Gson g=new Gson();
        return g.toJson(list);
    }
}
