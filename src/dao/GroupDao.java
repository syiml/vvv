package dao;

import entity.UserGroup.Group;
import entity.UserGroup.GroupMember;
import entity.UserGroup.GroupMemberStatus;
import entity.UserGroup.GroupType;
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
        if(type == -1){
            return new SQL("SELECT id FROM t_group").queryList();
        }
        return new SQL("SELECT id FROM t_group WHERE `type`=?",type).queryList();
    }

    public int createGroup(String name,String username,int type){
        int id = new SQL("INSERT INTO t_group(`name`,`type`,`time`) VALUES(?,?,?)",name,type, Tool.now()).insertGetLastInsertId();
        if(id<=0) return id;
        GroupType groupType = GroupType.getByID(type);
        return joinGroup(id,username,groupType.getRoles().get(0));
    }

    public int updateGroup(int id,String name){
        new SQL("UPDATE t_group SET `name`=? WHERE id=?",name, id).update();
        remove_catch(id);
        return 1;
    }

    public int joinGroup(int id, String username, int role){
        int ret = new SQL("INSERT INTO t_group_member VALUES(?,?,?,?)",id,username,role,Tool.now()).update();
        remove_catch(id);
        return ret;
    }

    public int leaveGroup(int id, String username){
        int ret = new SQL("DELETE FROM t_group_member WHERE group_id=? AND username=?",id,username).update();
        remove_catch(id);
        return ret;
    }

    public int deleteGroup(int id){
        new SQL("DELETE FROM t_group_member WHERE group_id=?",id).update();
        new SQL("DELETE FROM t_group WHERE id=?",id).update();
        remove_catch(id);
        return 1;
    }

    /**
     *
     * @param username 用户名
     * @param type 分组类型
     * @return 组id 不存在返回0
     */
    public int inTeam(String username,int type){
        return new SQL("SELECT group_id FROM t_group_member LEFT JOIN t_group ON group_id=id WHERE username=? AND `type`=?",username,type).queryNum();
    }

    @Override
    protected Group getByKeyFromSQL(Integer key) {
        Group group = new SQL("SELECT * FROM t_group WHERE id=?",key).queryBean(Group.class);
        if(group == null) return null;
        List<GroupMember> members = new SQL("SELECT * FROM t_group_member WHERE group_id=?",key).queryBeanList(GroupMember.class);
        group.setMembers(members);
        return group;
    }
}
