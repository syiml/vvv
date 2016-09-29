package action;

import util.Main;

/**
 * Created by Syiml on 2015/10/20 0020.
 */
public class addLocalProblem extends BaseAction{
    public String title;
    public int time;
    public int memory;
    public int pid;
    public boolean isSpj;
    public String author;

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

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public boolean isSpj() {
        return isSpj;
    }

    public void setSpj(boolean spj) {
        isSpj = spj;
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

    public String add(){
        if(!Main.loginUserPermission().getAddLocalProblem()) return "error";
        if(pid == -1)
            return Main.addLocalProblem(this);
        else return Main.editLocalProblem(this);
    }
}
