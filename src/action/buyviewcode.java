package action;

import Main.Main;
import Main.User.User;

/**
 * Created by Syiml on 2015/8/18 0018.
 */
public class buyviewcode {
    public int getPid() {
        return pid;
    }
    public void setPid(int pid) {
        this.pid = pid;
    }

    public int pid;
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
