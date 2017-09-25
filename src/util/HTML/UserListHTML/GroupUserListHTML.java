package util.HTML.UserListHTML;

import dao.GroupDao;
import entity.PermissionType;
import entity.User;
import entity.UserGroup.Group;
import entity.UserGroup.GroupMemberStatus;
import util.HTML.HTML;
import util.HTML.TableHTML;
import util.Main;
import util.SQL.SQL;
import util.SimplePageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QAQ on 2017/9/24.
 */
public class GroupUserListHTML extends SimplePageBean<User>{
    private int groupID;
    private Group group;
    private boolean admin;

    public GroupUserListHTML(int groupID,boolean admin) {
        super(1);
        this.groupID = groupID;
        this.admin = admin;
        group = GroupDao.getInstance().getBeanByKey(groupID);
    }

    @Override
    public List<User> getElement(int from, int num) {
        return new SQL("SELECT users.* FROM t_group_member join users on t_group_member.username = users.username WHERE group_id=? ORDER BY status ASC,acnum DESC",groupID).queryBeanList(User.class);
    }

    @Override
    public int getTotalNumber() {
        return list.size();
    }

    @Override
    public int getEveryPageNumber() {
        return 100;
    }

    @Override
    public String getCellByClass(int i, User user, String colname) {
        switch (colname){
            case "用户名":return user.getUsernameHTML();
            case "姓名":return user.getName();
            case "昵称":return user.getTitleAndNick();
            case "状态":return group.getMemberStatus(user.getUsername()).HTML();
            case "AC":return user.getAcnum()+"";
            case "Rating":return user.getRatingHTML();
            case "#":return user.getRatingnum()+"";
            case "admin":
                return group.getMemberStatus(user.getUsername())== GroupMemberStatus.LEADER?"":HTML.a("delMember.action?id="+groupID+"&username="+user.getUsername(),"删除队员") +" "+ HTML.a("#","设为队长");
        }
        return ERROR_CELL_TEXT;
    }

    @Override
    public String[] getColNames() {
        ArrayList<String> list = new ArrayList<>();
        list.add("用户名");
        User loginUser = Main.loginUser();
        if(loginUser!=null && (loginUser.getPermission().havePermissions(PermissionType.userAdmin) || loginUser.getUsername().equals(group.getLeader().getUsername()))){
            list.add("姓名");
        }
        list.add("昵称");
        list.add("状态");
        list.add("AC");
        list.add("Rating");
        list.add("#");
        if(admin){
            list.add("admin");
        }
        String[] ret = new String[list.size()];
        list.toArray(ret);
        return ret;
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public String getLinkByPage(int page) {
        return "";
    }

    @Override
    public String rightForm() {
        return "";
    }

    public String HTML(){
        this.list = getElement( getEveryPageNumber() * (page-1) ,getEveryPageNumber());
        addTableHead(getColNames());
        return tableHTML();
    }
}
