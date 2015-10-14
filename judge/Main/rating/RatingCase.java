package Main.rating;

import Tool.HTML.HTML;

import java.sql.Timestamp;

/**
 * Created by Syiml on 2015/7/3 0003.
 */
public class RatingCase {
    public String getUsername() {
        return username;
    }
    public Timestamp getTime() {
        return new Timestamp(time);
    }
    public int getCid() {
        return cid;
    }
    public int getPrating() {
        return prating;
    }
    public int getRating() {
        return rating;
    }
    public int getRatingnum() {
        return ratingnum;
    }
    public int getRank() {
        return rank;
    }

    String username;
    long time;
    int cid;
    int prating;
    int rating;
    int ratingnum;
    int rank;
    String text;

    public RatingCase(String u,Timestamp t,int cid,int prating,int rating,int ratingnum,int rank,String cname){
        username=u;
        time=t.getTime();
        this.cid=cid;
        this.prating=prating;
        this.rating=rating;
        this.ratingnum=ratingnum;
        this.rank=rank;
        this.text= cname + " rank:" + rank;
    }
}
