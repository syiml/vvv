package action;

import util.Main;

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
        if(!Main.loginUserPermission().getAddProblem()) return "error";
        //System.out.print(s);
        return Main.problems.editProlem(this);
    }
    public String delProblemDis(){
        if(!Main.loginUserPermission().getAddProblem()) return "error";
        //System.out.print(s);
        Main.problems.delProblemDis(Integer.parseInt(pid));
        return "success";
    }
}
