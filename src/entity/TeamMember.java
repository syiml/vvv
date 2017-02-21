package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by QAQ on 2016/5/2 0002.
 */
public class TeamMember implements IBeanResultSetCreate {

    //详细信息：姓名，性别，学校，院系，专业班级，学号，手机
    public String username;
    String name;
    int    gender;//性别
    String school = "";//学校
    String faculty = "";//学院
    String major = "";//专业
    String cla = "";//班级
    String no = "";//学号
    String phone = "";//联系电话

    public TeamMember(){}

    @Override
    public void init(ResultSet rs) throws SQLException {
        this.username = rs.getString("username");
        this.name=rs.getString("name");
        this.gender=rs.getInt("gender");
        this.school=rs.getString("school");
        this.faculty=rs.getString("faculty");
        this.major=rs.getString("major");
        this.cla=rs.getString("cla");
        this.no=rs.getString("no");
        this.phone=rs.getString("phone");
    }

    public int getGender() {
        return gender;
    }

    public String getCla() {
        return cla;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getSchool() {
        return school;
    }

    public String getMajor() {
        return major;
    }

    public String getName() {
        return name;
    }

    public String getNo() {
        return no;
    }

    public String getPhone() {
        return phone;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setSchool(String school) {
        this.school = school;
    }
}
