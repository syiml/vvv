package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by QAQ on 2016/10/15.
 */
public class UserVerifyInfo implements IBeanResultSetCreate<UserVerifyInfo>, IBeanCanCatch {
    public static final int RESULT_PADDING = 0;
    public static final int RESULT_ACCEPTED = 1;
    public static final int RESULT_REFUSE = -1;
    public int id;
    public int VerifyType;//验证类型
    public String username;
    public String name;
    public String school;
    public int gender;//性别
    public String faculty;//学院
    public String major;//专业
    public String cla;//班级
    public String no;//学号
    public String phone;//联系方式
    public String email;
    public Timestamp graduationTime;
    public Timestamp time;
    public int result;
    public String reason;
    private Timestamp t;

    @Override
    public UserVerifyInfo init(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        VerifyType = rs.getInt("VerifyType");
        username = rs.getString("username");
        school = rs.getString("school");
        name = rs.getString("name");
        gender = rs.getInt("gender");
        faculty = rs.getString("faculty");
        major = rs.getString("major");
        cla = rs.getString("cla");
        no = rs.getString("no");
        phone = rs.getString("phone");
        email = rs.getString("email");
        time = rs.getTimestamp("time");
        reason = rs.getString("reason");
        result = rs.getInt("result");
        graduationTime = rs.getTimestamp("graduationTime");
        return this;
    }

    public String getVerifyTypeText(){
        switch (VerifyType)
        {
            case 0:return "申请修改资料";
            case 1:return "申请为ACM集训队员";
            case 2:return "申请ACM退役";
            case 3:return "认证为中世竞创协会成员";
            case 4:return "认证为FJUT在校学生";
            default:return "ERROR";
        }
    }
    @Override
    public Timestamp getExpired() {
        return t;
    }

    @Override
    public void setExpired(Timestamp t) {
        this.t = t;
    }
}
