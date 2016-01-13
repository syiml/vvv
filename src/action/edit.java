package action;

import servise.UserServise;
import util.HTML.HTML;
import util.Main;
import entity.User;
import util.Tool;

/**
 * Created by Syiml on 2015/6/27 0027.
 */
public class edit {

    String username;
    String pass;
    String newpass;
    String renewpass;
    String nick;
    String school;
    String email;
    String motto;
    int inTeamLv=-1;
    int inTeamStatus=-1;

    //详细信息：姓名，性别，学校，院系，专业班级，学号，手机
    String name;
    String gender;//性别
    String faculty_text;//学院
    String major_text;//专业
    String cla;//班级
    String no;//学号
    String phone;//联系方式

    public String ed(){
//      System.out.println(motto);
        User u=(User)Main.getSession().getAttribute("user");
        if(u==null) return "error";
        if(!newpass.equals(renewpass)) return "error";
        String ret=Main.users.login(u.getUsername(),pass);
//      System.out.println(ret);
        if(!ret.equals("LoginSuccess")){return "error";}
        else{
            if(UserServise.editUser(getUser())){
                return "success";
            }else{
                return "error";
            }
        }
    }
    public String resetPassword(){
        if(Main.loginUserPermission().getResetPassword()){
            UserServise.editUser(getUser());
        }
        return "success";
    }
    public String adminEdit(){
        if(Main.loginUserPermission().getUserAdmin()){
            UserServise.editUser(getUser());
        }
        return "success";
    }
    public User getUser(){
        nick=nick.replace("'","''");
        school=school.replace("'","''");
        email=email.replace("'","''");
        motto=motto.replace("'","''");
        User u;
        if(username==null){
            u=Main.users.getUser(Main.loginUser().getUsername());
        }else{
            u=Main.users.getUser(username);
        }
        if(u==null) return null;
        if(newpass==null||newpass.equals("")){
            u.setPassword(null);
        }else if(newpass.equals(renewpass)){
            u.setPassword(newpass);
        }else if(pass!=null&&!pass.equals("")){
            u.setPassword("123456");
        }
        if(nick!=null) u.setNick(HTML.HTMLtoString(nick));
        if(school!=null) u.setSchool(HTML.HTMLtoString(school));
        if(email!=null) u.setEmail(HTML.HTMLtoString(email));
        if(motto!=null) u.setMotto(HTML.HTMLtoString(motto));
        if(inTeamLv!=-1) u.setInTeamLv(inTeamLv);
        if(inTeamStatus!=-1) u.setInTeamStatus(inTeamStatus);
        if(name!=null) u.setName(HTML.HTMLtoString(name));
        if(gender!=null) u.setGender(Integer.parseInt(gender));
        if(faculty_text!=null) u.setFaculty(HTML.HTMLtoString(faculty_text));
        if(major_text!=null) u.setMajor(HTML.HTMLtoString(major_text));
        if(cla!=null) u.setCla(HTML.HTMLtoString(cla));
        if(no!=null) u.setNo(HTML.HTMLtoString(no));
        if(phone!=null) u.setPhone(phone);
        return u;
    }
    ///////////////get set ///////////////////
    public void setName(String name) {
        this.name = name;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setFaculty_text(String faculty) {
        this.faculty_text = faculty;
    }
    public void setMajor_text(String major) {
        this.major_text = major;
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
    public int getInTeamLv() {
        return inTeamLv;
    }
    public int getInTeamStatus() {
        return inTeamStatus;
    }
    public void setInTeamLv(int inTeamLv) {
        this.inTeamLv = inTeamLv;
    }
    public void setInTeamStatus(int inTeamStatus) {
        this.inTeamStatus = inTeamStatus;
    }
    public String getGender() {
        return gender;
    }
    public String getName() {
        return name;
    }
    public String getFaculty_text() {
        return faculty_text;
    }
    public String getMajor_text() {
        return major_text;
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
    public void setPass(String pass) {
        this.pass = pass;
    }
    public void setNewpass(String newpass) {
        this.newpass = newpass;
    }
    public void setRenewpass(String renewpass) {
        this.renewpass = renewpass;
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
    public void setMotto(String motto) {
        this.motto = motto;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getMotto() {
        return motto;
    }
    public String getPass() {
        return pass;
    }
    public String getNewpass() {
        return newpass;
    }
    public String getRenewpass() {
        return renewpass;
    }
    public String getNick() {
        return nick;
    }
    public String getSchool() {
        return school;
    }
    public String getEmail() {
        return email;
    }
}
