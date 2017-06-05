package action;

import entity.UserVerifyInfo;
import servise.UserService;
import util.HTML.HTML;
import util.Main;
import entity.User;
import util.MainResult;
import util.Tool;

import java.io.File;
import java.io.IOException;

/**
 * Created by Syiml on 2015/6/27 0027.
 */
public class edit extends BaseAction{

    //基本信息
    String username;
    String pass;
    String newpass;
    String renewpass;
    String nick;
    String motto;

    int inTeamLv=-1;
    int inTeamStatus=-1;

    //验证信息：姓名，性别，学校，院系，专业班级，学号，手机
    String name;
    int gender;//性别
    String school;
    String faculty_text;//学院
    String major_text;//专业
    String cla;//班级
    String no;//学号
    String phone;//联系方式
    String email;
    int graduationTime;

    int VerifyType = -1;//验证类型
    int result;
    String reason;
    int id;
    int pre_id;
    File pic;

    public String ed(){
//      System.out.println(motto);
        User u=Main.loginUser();
        if(u==null) return "error";
        if(!newpass.equals(renewpass)) return "error";
        String ret=Main.users.login(u.getUsername(),pass);
//      System.out.println(ret);
        if(!ret.equals("LoginSuccess")){return "error";}
        else{
            if(!"".equals(newpass)) {
                if (newpass.equals(renewpass)) {
                    u.setPassword(newpass);
                }
            }
            if(UserService.editUser(getEditUser())){
                return "success";
            }else{
                return "error";
            }
        }
    }
    public String verify() throws IOException {
        User u = Main.loginUser();
        if (u == null) return LOGIN;
        if(graduationTime<2000||graduationTime>2050){
            this.setPrompt("毕业年份不正确");
            return ERROR;
        }
        if(getVerifyType()==User.V_RETIRED && u.getInTeamStatus() != User.V_TEAM ){
            this.setPrompt("只有现役队员才能申请退役");
            return ERROR;
        }else if(getVerifyType()==User.V_SCHOOL && u.getInTeamStatus() != User.V_NONE){
            this.setPrompt("你已经认证为在校学生，不用重复认证");
            return ERROR;
        }else if(getVerifyType()==User.V_ASSOCIATION){
            if(u.getInTeamStatus() == User.V_TEAM || u.getInTeamStatus() == User.V_RETIRED){
                this.setPrompt("集训队员和退役队员也是协会成员");
                return ERROR;
            }
            if(u.getInTeamStatus() == User.V_ASSOCIATION){
                this.setPrompt("你已经是协会成成员了");
                return ERROR;
            }
        }else if(getVerifyType()==User.V_TEAM){
            if(u.getInTeamStatus() == User.V_TEAM){
                this.setPrompt("你已经是集训队员了");
                return ERROR;
            }
        }
        MainResult mr = UserService.submitVerify(this);
        if(mr == MainResult.SUCCESS) {
            return SUCCESS;
        }
        else{
            setPrompt(mr.getPrompt());
            return ERROR;
        }
    }
    public String adminVerify(){
        MainResult mr = MainResult.FAIL;
        if(result == UserVerifyInfo.RESULT_ACCEPTED){
            mr = UserService.acceptedVerify(id);
        }else if(result == UserVerifyInfo.RESULT_REFUSE){
            mr = UserService.refuseVerify(id,reason);
        }
        setPrompt(mr.getPrompt());
        return SUCCESS;
    }
    public String resetPassword(){
        if(!reason.equals("重置密码")) return "fail";
        if(Main.loginUserPermission().getResetPassword()){
            UserService.resetPassword(username);
        }
        return "success";
    }
    public String adminEdit(){
        if(Main.loginUserPermission().getUserAdmin()){
            UserService.editUser(getUser());
        }
        return "success";
    }
    public String gotoVerifyJsp(){
        User u = Main.loginUser();
        if(u==null) return LOGIN;
        UserVerifyInfo userVerifyInfo = UserService.verifySQL.getUserVerifyInfo(u.getUsername());
        if(userVerifyInfo==null) {
            name = u.getName();
            gender = u.getGender();
            school = u.getSchool();
            faculty_text = u.getFaculty();
            major_text = u.getMajor();
            cla = u.getCla();
            no = u.getNo();
            phone = u.getPhone();
            email = u.getEmail();
            graduationTime = u.getGraduationTime()==null?0:u.getGraduationTime().getYear()+1900;
            userVerifyInfo = UserService.verifySQL.getLastUserVerifyInfo(u.getUsername());
            if(userVerifyInfo != null) setPre_id(userVerifyInfo.id);
        }else{
            this.setId(userVerifyInfo.id);
        }
        return SUCCESS;
    }
    private User getEditUser(){
        nick=nick.replace("'","''");
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
        }
        if(nick!=null) u.setNick(HTML.HTMLtoString(nick));
        if("".equals(nick)) nick = "把nick改成空白的智障";
        if(motto!=null) u.setMotto(HTML.HTMLtoString(motto));
        return u;
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
        u.setGender(gender);
        if(faculty_text!=null) u.setFaculty(HTML.HTMLtoString(faculty_text));
        if(major_text!=null) u.setMajor(HTML.HTMLtoString(major_text));
        if(cla!=null) u.setCla(HTML.HTMLtoString(cla));
        if(no!=null) u.setNo(HTML.HTMLtoString(no));
        if(phone!=null) u.setPhone(phone);
        return u;
    }

    public int getInTeamLv() {
        return inTeamLv;
    }

    public void setInTeamLv(int inTeamLv) {
        this.inTeamLv = inTeamLv;
    }

    public int getInTeamStatus() {
        return inTeamStatus;
    }

    public void setInTeamStatus(int inTeamStatus) {
        this.inTeamStatus = inTeamStatus;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getVerifyType() {
        return VerifyType;
    }

    public void setVerifyType(int verifyType) {
        VerifyType = verifyType;
    }

    public String getName() {
        return name;
    }

    ///////////////get set ///////////////////
    public void setName(String name) {
        this.name = name;
    }

    public String getFaculty_text() {
        return faculty_text;
    }

    public void setFaculty_text(String faculty) {
        this.faculty_text = faculty;
    }

    public int getPre_id() {
        return pre_id;
    }

    public void setPre_id(int pre_id) {
        this.pre_id = pre_id;
    }

    public File getPic() {
        return pic;
    }

    public void setPic(File pic) {
        this.pic = pic;
    }

    public String getMajor_text() {
        return major_text;
    }

    public void setMajor_text(String major) {
        this.major_text = major;
    }

    public String getCla() {
        return cla;
    }

    public void setCla(String cla) {
        this.cla = cla;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGraduationTime() {
        return graduationTime;
    }

    public void setGraduationTime(int graduationTime) {
        this.graduationTime = graduationTime;
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

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNewpass() {
        return newpass;
    }

    public void setNewpass(String newpass) {
        this.newpass = newpass;
    }

    public String getRenewpass() {
        return renewpass;
    }

    public void setRenewpass(String renewpass) {
        this.renewpass = renewpass;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
