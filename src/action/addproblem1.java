package action;

import util.Main;

/**
 * Created by Syiml on 2015/6/14 0014.
 */
public class addproblem1 extends BaseAction{
    String pid;
    String ojid;
    String ojspid;
    String title;
    String author;
    String isSpj;

    public int getPid() {
        if(pid==null||pid.equals("")) return -1;
        return Integer.parseInt(pid);
    }

    public void setPid(String pid) {
        this.pid = pid;
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

        return isSpj;
    }

    public void setIsSpj(String isSpj) {
        this.isSpj = isSpj;
    }

    public String addproblem1(){
        if(!Main.loginUserPermission().getAddProblem()) return "error";
        //System.out.println(pid+" "+ojid+" "+ojspid);
        return Main.addProblem(this);
    }
}
