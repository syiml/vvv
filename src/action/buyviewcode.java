package action;

import util.Main;
import entity.User;

/**
 * Created by Syiml on 2015/8/18 0018.
 */
public class buyviewcode extends BaseAction{
    public int pid;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String buy(){
        User u=Main.loginUser();
        if(Main.users.subACB(u.getUsername(),100)==1){
            if(Main.users.addViewCode(u.getUsername(),pid)==1){
                return "success";
            }
        }
        return "error";
    }
}
