package entity;

import util.Tool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by QAQ on 2016/5/2 0002.
 */
public class RegisterTeam  extends  RegisterUser{
    public String teamUserName;//用于进入比赛的账号和密码
    public String teamPassword;
    public String teamName;//队名
    private ArrayList<TeamMember> members = new ArrayList<>();//队员

    public RegisterTeam(){}
    public RegisterTeam(ResultSet rs) throws SQLException {
        super(rs);
        teamUserName = rs.getString("teamusername");
        teamPassword = rs.getString("teampassword");
        Tool.log(teamUserName+"+"+teamPassword);
        teamName = rs.getString("teamname");
    }

    @Override
    public RegisterTeam init(ResultSet rs) throws SQLException {
        super.init(rs);
        teamUserName = rs.getString("teamusername");
        teamPassword = rs.getString("teampassword");
        teamName = rs.getString("teamname");
        return this;
    }

    @Override
    public String getShowUserName() {
        return teamUserName;
    }

    @Override
    public String getShowNick() {
        return teamName;
    }

    public void addMember(TeamMember tm){
        members.add(tm);
    }

    public TeamMember getMember(int i){
        if(i<members.size()){
            return members.get(i);
        }else{
            return null;
        }
    }

}
