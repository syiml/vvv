package action;

import dao.GroupDao;
import entity.PermissionType;
import entity.User;
import entity.UserGroup.Group;
import entity.UserGroup.GroupMember;
import entity.UserGroup.GroupMemberStatus;
import entity.UserGroup.GroupType;
import util.Main;
import util.MainResult;

/**
 * Created by syimlzhu on 2017/9/25.
 */
public class GroupAction extends BaseAction {
    private int id;
    private String name;
    private String username;
    private String leader;
    private int type;
    private int role;

    public String addGroup(){
        User loginUser = Main.loginUser();
        if(loginUser == null || !loginUser.getPermission().havePermissions(PermissionType.groupAdmin)){
            this.setPrompt(MainResult.NO_PERMISSION.getPrompt());
            return ERROR;
        }
        User user = Main.users.getUser(leader);
        if(user == null) {
            this.setPrompt(MainResult.USER_ERROR.getPrompt());
            return ERROR;
        }
        if(name.length()<2 || name.length()>15){
            this.setPrompt("队名长度应该在2~15之间");
            return ERROR;
        }
        if(GroupType.getByID(type) == null) {
            this.setPrompt(MainResult.ARR_ERROR.getPrompt());
            return ERROR;
        }
        if(GroupDao.getInstance().inTeam(leader,type) != 0){
            this.setPrompt("该用户已经加入过一个该类型的队伍了");
            return ERROR;
        }
        if(GroupDao.getInstance().createGroup(name,leader,type) > 0 ) return SUCCESS;
        return ERROR;
    }
    public String editGroup(){
        User loginUser = Main.loginUser();
        if(loginUser == null || !loginUser.getPermission().havePermissions(PermissionType.groupAdmin)){
            this.setPrompt(MainResult.NO_PERMISSION.getPrompt());
            return ERROR;
        }
        if(name.length()<2 || name.length()>15){
            this.setPrompt("队名长度应该在2~15之间");
            return ERROR;
        }
        if(GroupDao.getInstance().updateGroup(id,name) > 0 ) return SUCCESS;
        return ERROR;
    }
    public String addMember(){
        User loginUser = Main.loginUser();
        if(loginUser == null || !loginUser.getPermission().havePermissions(PermissionType.groupAdmin)){
            this.setPrompt(MainResult.NO_PERMISSION.getPrompt());
            return ERROR;
        }
        User user = Main.users.getUser(username);
        if(user == null){
            this.setPrompt(MainResult.USER_ERROR.getPrompt());
            return ERROR;
        }
        Group group = GroupDao.getInstance().getBeanByKey(id);
        if(group == null){
            this.setPrompt("队伍不存在");
            return ERROR;
        }
        if(!group.getType().isCanRepeatJoin()
                &&GroupDao.getInstance().inTeam(username,group.getType().getId()) != 0){
            this.setPrompt("该用户已经加入过一个该类型的队伍了");
            return ERROR;
        }
        if(GroupDao.getInstance().joinGroup(id,username, role) > 0) return SUCCESS;
        return ERROR;
    }
    public String delMember(){
        User loginUser = Main.loginUser();
        if(loginUser == null || !loginUser.getPermission().havePermissions(PermissionType.groupAdmin)){
            this.setPrompt(MainResult.NO_PERMISSION.getPrompt());
            return ERROR;
        }
        User user = Main.users.getUser(username);
        if(user == null){
            this.setPrompt(MainResult.USER_ERROR.getPrompt());
            return ERROR;
        }
        Group group = GroupDao.getInstance().getBeanByKey(id);
        if(group == null){
            this.setPrompt("队伍不存在");
            return ERROR;
        }
        boolean flag = false;
        GroupMember leader = group.getLeader();
        for(GroupMember member : group.getMembers()){
            if(member.getUsername().equals(username)){
                if(member == leader){
                    this.setPrompt("删除失败，队长不能被删除");
                    return ERROR;
                }else{
                    flag = true;
                }
            }
        }
        if(!flag){
            this.setPrompt("该用户不在这个队伍里");
            return ERROR;
        }
        if(GroupDao.getInstance().leaveGroup(id,username) > 0) return SUCCESS;
        this.setPrompt("删除失败");
        return ERROR;
    }

    ///////////get/set////////////
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
