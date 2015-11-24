package action;

import util.Main;
import entity.User;

/**
 * Created by Syiml on 2015/6/27 0027.
 */
public class edit {
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
    String pass;
    String newpass;
    String renewpass;
    String nick;
    String school;
    String email;
    String motto;

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
    //详细信息：姓名，性别，学校，院系，专业班级，学号，手机
    String name;

    public String getGender() {
        return gender;
    }

    String gender;//性别
    String faculty_text;//学院
    String major_text;//专业
    String cla;//班级
    String no;//学号
    String phone;//联系方式
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
    public String ed(){
//        System.out.println(motto);
        User u=(User)Main.getSession().getAttribute("user");
        if(u==null) return "error";
        if(!newpass.equals(renewpass)) return "error";
        String ret=Main.users.login(u.getUsername(),pass);
//        System.out.println(ret);
        if(!ret.equals("LoginSuccess")){return "error";}
        else{
            nick=nick.replace("'","''");
            school=school.replace("'","''");
            email=email.replace("'","''");
            motto=motto.replace("'","''");
            return Main.users.update(u.getUsername(),this);
        }
    }
    public String resetPassword(){
        if(Main.loginUserPermission().getResetPassword()){
            Main.users.resetPassword(name);
        }
        return "success";
    }
}
