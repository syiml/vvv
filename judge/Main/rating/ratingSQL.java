package Main.rating;

import Main.Main;
import Main.User.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Message.MessageSQL;
import Tool.SQL.SQL;
import com.google.gson.Gson;

/**
 * Created by Syiml on 2015/7/3 0003.
 */
public class ratingSQL {
    public static void save(RatingCase r){
        new SQL("INSERT INTO t_rating VALUES(?,?,?,?,?,?,?) ",r.username,r.getTime(),r.cid,r.prating,r.rating,r.ratingnum,r.rank).update();
        new SQL("UPDATE users set ratingnum=ratingnum+1,rating=? WHERE username=?",r.rating,r.username).update();
        int prating=Main.users.getUser(r.username).getShowRating();
        int newrating=Main.users.getUser(r.username).getShowRating();
        MessageSQL.AddMessageRatingChange(r.cid,r.username,prating,newrating);
        System.out.println(r.username + ":" + r.rating);
    }
    public static List<RatingCase> getRating(int cid){
        List<RatingCase> list=new ArrayList<RatingCase>();
        SQL sql=new SQL("SELECT username,time,cid,prating,rating,ratingnum,rank,(select name from contest where id=cid) as cname FROM t_rating WHERE cid=? order by rank",cid);
        try {
            ResultSet rs=sql.query();
            while(rs.next()){
                list.add(new RatingCase(rs.getString(1),rs.getTimestamp(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),rs.getString("cname")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            sql.close();
        }
        return list;
    }
    public static List<RatingCase> getRating(String username){
        List<RatingCase> list=new ArrayList<RatingCase>();
        SQL sql=new SQL("SELECT username,time,cid,prating,rating,ratingnum,rank,(select name from contest where id=cid) as cname FROM t_rating WHERE username=? order by ratingnum desc",username);
        try {
            ResultSet rs=sql.query();
            while(rs.next()){
                list.add(new RatingCase(rs.getString(1),rs.getTimestamp(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),rs.getString("cname")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            sql.close();
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
