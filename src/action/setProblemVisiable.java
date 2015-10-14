package action;

import Main.Main;
import Main.User.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Syiml on 2015/6/18 0018.
 */
public class setProblemVisiable {
    public String getPid() {
        return pid;
    }
    public void setPid(String pid) {
        this.pid = pid;
    }
    String pid;
    public String setProVis(){
        if(!Main.loginUserPermission().getShowHideProblem()) return "error";
        try{
            if(pid==null) return "error";
            else return Main.setProblemVisiable(Integer.parseInt(pid));
        }catch(NumberFormatException e){
            return "error";
        }
    }
}
