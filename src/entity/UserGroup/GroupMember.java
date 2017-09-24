package entity.UserGroup;

import entity.IBeanResultSetCreate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by QAQ on 2017/9/24.
 */
public class GroupMember implements IBeanResultSetCreate{
    private String username;
    private GroupMemberStatus status;
    private Timestamp joinTime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public GroupMemberStatus getStatus() {
        return status;
    }

    public void setStatus(GroupMemberStatus status) {
        this.status = status;
    }

    public Timestamp getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Timestamp joinTime) {
        this.joinTime = joinTime;
    }

    @Override
    public void init(ResultSet rs) throws SQLException {
        username = rs.getString("username");
        status = GroupMemberStatus.getByID(rs.getInt("status"));
        joinTime = rs.getTimestamp("join_time");
    }
}
