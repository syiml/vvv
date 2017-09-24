package entity.UserGroup;

import entity.IBeanCanCatch;
import entity.IBeanResultSetCreate;
import util.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by QAQ on 2017/9/24.
 */
public class Group implements IBeanResultSetCreate, IBeanCanCatch{
    private int id;
    private GroupType type;//队伍类型
    private String groupName;//队名
    private Timestamp createTime;
    private List<GroupMember> members;

    @Override
    public void init(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        type = GroupType.getByID(rs.getInt("type"));
        groupName = rs.getString("name");
        createTime = rs.getTimestamp("time");
    }

    public int getMemberTotalAC(boolean isIncludeLeader){
        int totalAC=0;
        for(GroupMember member : members){
            if(member.getStatus() != GroupMemberStatus.LEADER || (member.getStatus() == GroupMemberStatus.LEADER && isIncludeLeader)){
                totalAC+= Main.users.getUser(member.getUsername()).getAcnum();
            }
        }
        return totalAC;
    }

    public GroupMember getLeader(){
        for(GroupMember member : members){
            if(member.getStatus() == GroupMemberStatus.LEADER) return member;
        }
        return null;
    }

    public int getMemberTotalRating(boolean isIncludeLeader){
        int total=0;
        for(GroupMember member : members){
            if(member.getStatus() != GroupMemberStatus.MEMBER && (member.getStatus() == GroupMemberStatus.MEMBER && isIncludeLeader)){
                int showRating = Main.users.getUser(member.getUsername()).getShowRating();
                if(showRating == -100000) showRating = 0;
                total += showRating;
            }
        }
        return total;
    }

    public GroupMemberStatus getMemberStatus(String username){
        for(GroupMember member : members){
            if(member.getUsername().equals(username)) return member.getStatus();
        }
        return GroupMemberStatus.NOT;
    }

    public int getId() {
        return id;
    }

    public GroupType getType() {
        return type;
    }

    public String getGroupName() {
        return groupName;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public List<GroupMember> getMembers() {
        return members;
    }

    public void setMembers(List<GroupMember> members) {
        this.members = members;
    }

    private Timestamp expired;
    @Override
    public Timestamp getExpired() {
        return expired;
    }

    @Override
    public void setExpired(Timestamp t) {
        expired = t;
    }
}
