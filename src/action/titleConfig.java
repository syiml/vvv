package action;

import entity.PermissionType;
import entity.User;
import servise.UserService;
import util.Main;
import util.Pair;
import util.Tool;

import java.util.List;

/**
 * Created by QAQ on 2017/5/28.
 */
public class titleConfig extends BaseAction {
    private int type ;//1交换，2隐藏,3显示,4置顶,5删除,6add
    private int id;
    private int id2;

    private int jd;
    private String time;
    private String isForever;
    private String username;

    public String go(){
        User u = Main.loginUser();
        if(u==null) return ERROR;
        switch (type) {
            case 1:
                u.titleSet.swap(id, id2);
                break;
            case 2:
                u.titleSet.hide(id);
                break;
            case 3:
                u.titleSet.show(id);
                break;
            case 4:
                u.titleSet.top(id);
                break;
            case 5:
                if (!Main.loginUserPermission().havePermissions(PermissionType.titleAdmin)) return ERROR;
                UserService.addTitle(Main.users.getUser(username), id, 0, null);
                break;
            case 6:
                if (!Main.loginUserPermission().havePermissions(PermissionType.titleAdmin)) return ERROR;
                if(isForever != null){
                    UserService.addTitle(Main.users.getUser(username), id, jd, null);
                }else{
                    UserService.addTitle(Main.users.getUser(username), id, jd, Tool.getTimestamp(time,"0","0"));
                }
                break;
        }
        Main.users.setTitleConfig(u.getUsername(),u.titleSet.isShow,u.titleSet.getOrder());
        return SUCCESS;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }

    public int getJd() {
        return jd;
    }

    public void setJd(int jd) {
        this.jd = jd;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIsForever() {
        return isForever;
    }

    public void setIsForever(String isForever) {
        this.isForever = isForever;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
