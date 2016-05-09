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
public class User implements IBeanResultSetCreate{
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
    Permission permission = null;
    int rating;//if rating == -100000    is null
    int ratingnum=0;
    int rank;//rating 的rank
    int acb;

    int inTeamLv;//队员等级
    int inTeamStatus;//队员状态 0非队员 1现役队员 2退役队员

    //详细信息：姓名，性别，学校，院系，专业班级，学号，手机
    String name;
    int    gender;//性别
    String faculty;//学院
    String major;//专业
    String cla;//班级
    String no;//学号
    String phone;//联系方式

    public User(){}
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
        this.inTeamLv=0;
        this.inTeamStatus=0;
    }
    public User init(ResultSet rs) throws SQLException {
        //username,nick,gender,school,Email,motto,registertime,type,Mark,rating,rank,ratingnum,acnum,acb
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

        this.inTeamLv=rs.getInt("inTeamLv");
        this.inTeamStatus=rs.getInt("inTeamStatus");

        this.acnum=rs.getInt("acnum");
        try{
            this.rank=rs.getInt("rank");
        }catch(SQLException e){
            this.rank=-1;
        }
        //permission=Main.getPermission(username);
        return this;
    }
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
    public String getUsernameHTMLNoA(){
        if(inTeamStatus==1){//集训队员标记
            return HTML.textb(username, ratingColor(getShowRating()))+"<em class='inTeam inTeam-"+inTeamLv+"'></em>";
        }else if(inTeamStatus==2){//退役队员
            return HTML.textb(username, ratingColor(getShowRating()))+"<em class='levTeam levTeam-"+inTeamLv+"'></em>";
        }
        return HTML.textb(username, ratingColor(getShowRating()));
    }
    public String getUsernameHTML(){
        return HTML.a("UserInfo.jsp?user="+username,getUsernameHTMLNoA());
    }
//    public void countAcProblem(){
//        acproblems=Main.status.getAcProblems(username);
//    }
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

    /////get set////

    public int getRank(){return rank;}
    public int getRating(){return rating;}
    public int getRatingnum(){return ratingnum;}
    public Timestamp getRegistertime() {
        return registertime;
    }
    public Permission getPermission(){
        if(permission == null){
            permission = Main.getPermission(getUsername());
        }
        return permission;
    }
    public int getType() {
        return type;
    }
    public String getMark() {
        return mark;
    }
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
    public void setUsername(String username) {
        this.username = username;
    }
    public void setNick(String nick) {
        this.nick = nick;
    }
    public void setSchool(String school) {
        this.school = school;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setGender(int gender) {
        this.gender = gender;
    }
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
    public void setMajor(String major) {
        this.major = major;
    }
    public void setCla(String cla) {
        this.cla = cla;
    }
    public void setNo(String no) {
        this.no = no;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setMotto(String motto) {
        this.motto = motto;
    }
    public void setRegistertime(Timestamp registertime) {
        this.registertime = registertime;
    }
    public void setType(int type) {
        this.type = type;
    }
    public void setSubmissions(int submissions) {
        this.submissions = submissions;
    }
    public void setMark(String mark) {
        this.mark = mark;
    }
    public void setAcnum(int acnum) {
        this.acnum = acnum;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setRatingnum(int ratingnum) {
        this.ratingnum = ratingnum;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setAcb(int acb) {
        this.acb = acb;
    }

    public void setInTeamLv(int inTeamLv) {
        this.inTeamLv = inTeamLv;
    }

    public void setInTeamStatus(int inTeamStatus) {
        this.inTeamStatus = inTeamStatus;
    }

    public int getSubmissions() {
        return submissions;
    }

    public int getAcb() {
        return acb;
    }

    public int getInTeamLv() {
        return inTeamLv;
    }

    public int getInTeamStatus() {
        return inTeamStatus;
    }
}
