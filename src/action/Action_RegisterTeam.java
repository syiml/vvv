package action;

import entity.Contest;
import entity.RegisterTeam;
import entity.TeamMember;
import servise.ContestMain;
import util.Main;
import util.Tool;

/**
 * Created by QAQ on 2016/5/4 0004.
 */
public class Action_RegisterTeam extends BaseAction{
    int cid;
    String username;

    String teamName;
    String name1;
    int gender1;
    String school1;
    String faculty1;
    String major1;
    String cla1;
    String no1;
    String phone1;

    String name2;
    int gender2;
    String school2;
    String faculty2;
    String major2;
    String cla2;
    String no2;
    String phone2;

    String name3;
    int gender3;
    String school3;
    String faculty3;
    String major3;
    String cla3;
    String no3;
    String phone3;

    public String RegisterTeam() {
        if(Main.loginUser()==null) return "error";
        Contest contest = ContestMain.getContest(cid);
        if(contest == null) return "error";
        Tool.log("RegisterTeam");
        RegisterTeam rt = new RegisterTeam();
        rt.setUsername(username);
        //rt.setUsername(Main.loginUser().getUsername());
        rt.teamName = teamName;
        TeamMember tm1 = new TeamMember();
        tm1.setUsername(rt.getUsername());
        tm1.setName(name1);
        tm1.setGender(gender1);
        tm1.setSchool(school1);
        //Tool.log("school1 = "+school1);
        tm1.setFaculty(faculty1);
        tm1.setMajor(major1);
        tm1.setCla(cla1);
        tm1.setNo(no1);
        tm1.setPhone(phone1);
        rt.addMember(tm1);
        TeamMember tm2 = null;
        if(name2!=null && !name2.equals("")){
            tm2=new TeamMember();
            tm2.setUsername(rt.getUsername());
            tm2.setName(name2);
            tm2.setGender(gender2);
            tm2.setSchool(school2);
            tm2.setFaculty(faculty2);
            tm2.setMajor(major2);
            tm2.setCla(cla2);
            tm2.setNo(no2);
            tm2.setPhone(phone2);
            rt.addMember(tm2);
        }
        TeamMember tm3 = null;
        if(name3!=null && !name3.equals("")){
            tm3=new TeamMember();
            tm3.setUsername(rt.getUsername());
            tm3.setName(name3);
            tm3.setGender(gender3);
            tm3.setSchool(school3);
            tm3.setFaculty(faculty3);
            tm3.setMajor(major3);
            tm3.setCla(cla3);
            tm3.setNo(no3);
            tm3.setPhone(phone3);
            rt.addMember(tm3);
        }
        ContestMain.addRegisterTeam(cid,rt);
        return "success";
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public int getGender1() {
        return gender1;
    }

    public void setGender1(int gender1) {
        this.gender1 = gender1;
    }

    public String getSchool1() {
        return school1;
    }

    public void setSchool1(String school1) {
        this.school1 = school1;
    }

    public String getFaculty1() {
        return faculty1;
    }

    public void setFaculty1(String faculty1) {
        this.faculty1 = faculty1;
    }

    public String getMajor1() {
        return major1;
    }

    public void setMajor1(String major1) {
        this.major1 = major1;
    }

    public String getCla1() {
        return cla1;
    }

    public void setCla1(String cla1) {
        this.cla1 = cla1;
    }

    public String getNo1() {
        return no1;
    }

    public void setNo1(String no1) {
        this.no1 = no1;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getSchool2() {
        return school2;
    }

    public void setSchool2(String school2) {
        this.school2 = school2;
    }

    public String getFaculty2() {
        return faculty2;
    }

    public void setFaculty2(String faculty2) {
        this.faculty2 = faculty2;
    }

    public String getMajor2() {
        return major2;
    }

    public void setMajor2(String major2) {
        this.major2 = major2;
    }

    public String getCla2() {
        return cla2;
    }

    public void setCla2(String cla2) {
        this.cla2 = cla2;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getSchool3() {
        return school3;
    }

    public void setSchool3(String school3) {
        this.school3 = school3;
    }

    public String getFaculty3() {
        return faculty3;
    }

    public void setFaculty3(String faculty3) {
        this.faculty3 = faculty3;
    }

    public String getMajor3() {
        return major3;
    }

    public void setMajor3(String major3) {
        this.major3 = major3;
    }

    public String getCla3() {
        return cla3;
    }

    public void setCla3(String cla3) {
        this.cla3 = cla3;
    }

    public String getNo3() {
        return no3;
    }

    public void setNo3(String no3) {
        this.no3 = no3;
    }

    public String getPhone3() {
        return phone3;
    }

    public void setPhone3(String phone3) {
        this.phone3 = phone3;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public int getGender2() {
        return gender2;
    }

    public void setGender2(int gender2) {
        this.gender2 = gender2;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public int getGender3() {
        return gender3;
    }

    public void setGender3(int gender3) {
        this.gender3 = gender3;
    }
}