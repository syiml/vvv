package entity;

import util.Main;
import util.HTML.HTML;
import action.register;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2015/6/3.
 */
public class User {
    String username;
    String password;
    String nick;
    String school;
    String email;
    String motto;//座右铭
    Timestamp registertime;
    int type;
    int submissions;
    String mark;
    int acnum;
    Permission permission;
    int rating;//if rating == -100000    is null
    int ratingnum=0;
    int rank;//rating 的rank
    int acb;

    //详细信息：姓名，性别，学校，院系，专业班级，学号，手机
    String name;
    int    gender;//性别
    String faculty;//学院
    String major;//专业
    String cla;//班级
    String no;//学号
    String phone;//联系方式
    public String getName() {
        return name;
    }
    public String getFaculty() {
        return faculty;
    }
    public String getMajor() {
        return major;
    }
    public String getCla() {
        return cla;
    }
    public String getNo() {
        return no;
    }
    public String getPhone() {
        return phone;
    }

    public User(register r){
        username=r.username;
        password=r.password;
        nick=r.nick;
        gender=r.gender;
        school=r.school;
        email=r.email;
        motto=r.motto;
        registertime=new Timestamp(System.currentTimeMillis());
        type=0;
        submissions=0;
        mark="";
        acnum=-1;
        //acproblems=new HashSet<Integer>();
        acb=0;
    }
    public User(ResultSet rs) {
        //username,nick,gender,school,Email,motto,registertime,type,Mark,rating,rank,ratingnum,acnum,acb
        try{
            this.username=rs.getString("username");
            this.nick=rs.getString("nick");
            this.gender=rs.getInt("gender");
            this.school=rs.getString("school");
            this.email=rs.getString("Email");
            this.motto=rs.getString("motto");
            this.registertime=rs.getTimestamp("registertime");
            this.type=rs.getInt("type");
            this.mark=rs.getString("Mark");
            this.rating=rs.getInt("rating");
            this.ratingnum=rs.getInt("ratingnum");
            this.acb=rs.getInt("acb");

            this.name=rs.getString("name");
            this.faculty=rs.getString("faculty");
            this.major=rs.getString("major");
            this.cla=rs.getString("cla");
            this.no=rs.getString("no");
            this.phone=rs.getString("phone");

            try{
                this.acnum=rs.getInt("acnum");
            }catch(SQLException e){
                this.acnum=-1;
            }
            try{
                this.rank=rs.getInt("rank");
            }catch(SQLException e){
                this.rank=-1;
            }
            permission=Main.getPermission(username);
        }catch(SQLException e){
            this.rank=-1;
        }
    }
    public int getAcnum(){return acnum;}
    public int getACB(){
        return acb;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getNick() {
        return nick;
    }
    public int getGender() {
        return gender;
    }
    public String getSchool() {
        return school;
    }
    public String getEmail() {
        return email;
    }
    public String getMotto() {
        return motto;
    }
    public int getRank(){return rank;}
    public int getRating(){return rating;}
    public int getRatingnum(){return ratingnum;}
    public int getShowRating(){
        return getShowRating(ratingnum,rating);
    }
    public static int getShowRating(int ratingnum,int rating){
        int z=700;
        double f=0.6;
        if(ratingnum==0) return -100000;
        return (int)((rating-z)*(1-Math.pow(f,ratingnum))+z+0.5);
    }
    public String getRatingHTML() {
        return ratingToHTML(getShowRating());
    }
    public String getUsernameHTMLNoA() {
        return HTML.textb(username, ratingColor(getShowRating()));
    }
    public String getUsernameHTML(){
        return HTML.a("UserInfo.jsp?user="+username,HTML.textb(username,ratingColor(getShowRating())));
    }
    public Timestamp getRegistertime() {
        return registertime;
    }
    public int getType() {
        return type;
    }
    public String getMark() {
        return mark;
    }
//    public void countAcProblem(){
//        acproblems=Main.status.getAcProblems(username);
//    }
    public Permission getPermission(){
        return permission;
    }
    public static String ratingColor(int rating){
        if(rating==-100000) return "#000000";
        if(rating<1000) return "#808080";
        if(rating<1200) return "#40C040";
        if(rating<1400) return "#00FF00";
        if(rating<1500) return "#00C0FF";
        if(rating<1600) return "#0000FF";
        if(rating<1700) return "#C000FF";
        if(rating<1900) return "#FF00FF";
        if(rating<2000) return "#FF0080";
        if(rating<2200) return "#FF0000";
        if(rating<2600) return "#FF8000";
        return "#FFD700";
    }
    public static String ratingToHTML(int rating){
        if(rating==-100000) return"-";
        return HTML.textb(rating+"",ratingColor(rating));
    }
    public boolean canRegisterOfficalContest(){
        return gender!=0&&!name.equals("")&&!faculty.equals("")&&!major.equals("")&&!cla.equals("")&&!no.equals("")&&!phone.equals("");
    }
}
