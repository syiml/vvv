package action;

import entity.Permission;
import entity.PermissionType;
import entity.Problem;
import entity.User;
import util.Main;

import java.beans.PersistenceDelegate;

/**
 * Created by Syiml on 2015/6/27 0027.
 */
public class editproblem  extends BaseAction{
    String s;
    String pid;
    String edit;
    String num;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getEdit() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String edit(){
        User u = Main.loginUser();
        if(u==null) return "error";
        Permission per = u.getPermission();
        if(per.getAddProblem()) return Main.problems.editProlem(this);
        Problem p = Main.problems.getProblem(Integer.parseInt(pid));
        if(p == null) return "error";
        if(per.havePermissions(PermissionType.partAddProblem) && p.getOwner().equals(u.getUsername())) return Main.problems.editProlem(this);
        return "error";
    }
    public String delProblemDis(){
        User u = Main.loginUser();
        if(u==null) return "error";
        Permission per = u.getPermission();
        if(per.getAddProblem()) {Main.problems.delProblemDis(Integer.parseInt(pid));return "success";}
        Problem p = Main.problems.getProblem(Integer.parseInt(pid));
        if(p == null) return "error";
        if(per.havePermissions(PermissionType.partAddProblem) && p.getOwner().equals(u.getUsername())) {Main.problems.delProblemDis(Integer.parseInt(pid));return "success";}
        return "error";
    }
}
