package action;

import entity.Permission;
import entity.PermissionType;
import entity.User;
import util.Main;

/**
 * Created by Syiml on 2015/6/14 0014.
 */
public class addproblem1 extends BaseAction{
    int pid;
    String ojid;
    String ojspid;
    String title;
    int time;
    int memory;
    boolean isSpj;
    String author;

    public int getPid() {
        return pid;
    }

    public void setPid(String pid) {
        if(pid==null||pid.equals("")) this.pid = -1;
        else this.pid = Integer.parseInt(pid);
    }

    public int getOjid() {
        return Integer.parseInt(ojid);
    }

    public void setOjid(String ojid) {
        this.ojid = ojid;
    }

    public String getOjspid() {
        return ojspid;
    }

    public void setOjspid(String ojspid) {
        this.ojspid = ojspid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsSpj() {
        return isSpj?"true":null;
    }

    public void setIsSpj(String isSpj) {
        this.isSpj = isSpj!=null;
    }

    public boolean isSpj() {
        return isSpj;
    }

    public void setSpj(boolean spj) {
        isSpj = spj;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public String addproblem1(){
        Permission p = Main.loginUserPermission();
        if(!p.getAddProblem()&& !p.havePermissions(PermissionType.partAddProblem)) return "error";
        //System.out.println(pid+" "+ojid+" "+ojspid);
        return Main.addProblem(this);
    }
}
