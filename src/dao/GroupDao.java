package dao;

import entity.UserGroup.Group;
import entity.UserGroup.GroupMember;
import entity.UserGroup.GroupMemberStatus;
import util.SQL.SQL;
import util.Tool;

import java.util.List;

/**
 * Created by QAQ on 2017/9/24.
 */
public class GroupDao extends BaseCacheLRU<Integer,Group>{

    private static GroupDao groupDao = new GroupDao();
    public static GroupDao getInstance(){return groupDao;}

    private GroupDao() {
        super(100);
    }

    public List<Integer> getAllGroupID(int type){
        return new SQL("SELECT id FROM t_group WHERE `type`=?",type).queryList();
    }

    public int createGroup(String name,String username,int type){
        int id = new SQL("INSERT INTO t_group(`name`,`type`,`time`) VALUES(?,?,?)",name,type, Tool.now()).insertGetLastInsertId();
        if(id<=0) return id;
        return joinGroup(id,username,GroupMemberStatus.LEADER);
    }

    private int joinGroup(int id, String username, GroupMemberStatus status){
        return new SQL("INSERT INTO t_group_member VALUES(?,?,?,?)",id,username,status.getId(),Tool.now()).update();
    }

    @Override
    protected Group getByKeyFromSQL(Integer key) {
        Group group = new SQL("SELECT * FROM t_group WHERE id=?",key).queryBean(Group.class);
        List<GroupMember> members = new SQL("SELECT * FROM t_group_member WHERE group_id=?",key).queryBeanList(GroupMember.class);
        group.setMembers(members);
        return group;
    }
}
