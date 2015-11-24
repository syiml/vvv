package dao;

import util.Main;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.RatingCase;
import servise.MessageMain;
import util.SQL;
import com.google.gson.Gson;

/**
 * Created by Syiml on 2015/7/3 0003.
 */
public class ratingSQL {
    public static void save(RatingCase r){
        new SQL("INSERT INTO t_rating VALUES(?,?,?,?,?,?,?) ", r.getUsername(),r.getTime(), r.getCid(), r.getPrating(), r.getRating(), r.getRatingnum(), r.getRank()).update();
        new SQL("UPDATE users set ratingnum=ratingnum+1,rating=? WHERE username=?", r.getRating(), r.getUsername()).update();
        int prating=Main.users.getUser(r.getUsername()).getShowRating();
        int newrating=Main.users.getUser(r.getUsername()).getShowRating();
        MessageMain.addMessageRatingChange(r.getCid(), r.getUsername(),prating,newrating);
        System.out.println(r.getUsername() + ":" + r.getRating());
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
                r.setRating(r.getRating());
            }else{
                r.setRating(User.getShowRating(r.getRatingnum(), r.getRating()));
            }
        }
        Gson g=new Gson();
        return g.toJson(list);
    }
}
