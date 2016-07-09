package action;

import util.Main;

/**
 * Created by Syiml on 2015/10/20 0020.
 */
public class addLocalProblem {
    public String getMemory() {
        return memory;
    }
    public String getTime() {
        return time;
    }
    public String getTitle() {
        return title;
    }
    public String getPid() {
        return pid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setMemory(String memory) {
        this.memory = memory;
    }
    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setIsSpj(String isSpj) {
        this.isSpj = isSpj!=null;
    }

    public String getIsSpj() {
        return isSpj?"true":null;
    }

    public String title;
    public String time;
    public String memory;
    public String pid;
    public boolean isSpj;
    String author;


    public String add(){
        if(!Main.loginUserPermission().getAddLocalProblem()) return "error";
        if(pid.equals("-1"))
            return Main.addLocalProblem(this);
        else return Main.editLocalProblem(this);
    }
}
