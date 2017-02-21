package entity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by QAQ on 2016/6/30 0030.
 */
public class TeamMemberAwardInfo implements IBeanResultSetCreate{
    int id;
    Date time;
    String username1;
    String username2;
    String username3;
    String name1;
    String name2;
    String name3;
    TeamMemberAwardInfo_ContestLevel contestLevel;
    TeamMemberAwardInfo_AwardLevel awardLevel;
    String text;

    @Override
    public void init(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        time = rs.getDate("time");
        username1 = rs.getString("username1");
        username2 = rs.getString("username2");
        username3 = rs.getString("username3");
        name1 = rs.getString("name1");
        name2 = rs.getString("name2");
        name3 = rs.getString("name3");
        contestLevel = TeamMemberAwardInfo_ContestLevel.getByCode(rs.getInt("contest_level"));
        awardLevel = TeamMemberAwardInfo_AwardLevel.getByCode(rs.getInt("awards_level"));
        text = rs.getString("text");
    }

    public String getUsername1() {
        return username1;
    }

    public int getId() {
        return id;
    }

    public String getUsername2() {
        return username2;
    }

    public String getUsername3() {
        return username3;
    }

    public String getName1() {
        return name1;
    }

    public String getName2() {
        return name2;
    }

    public String getName3() {
        return name3;
    }

    public TeamMemberAwardInfo_ContestLevel getContestLevel() {
        return contestLevel;
    }

    public TeamMemberAwardInfo_AwardLevel getAwardLevel() {
        return awardLevel;
    }

    public String getText() {
        return text;
    }

    public Date getTime() {
        return time;
    }
}
