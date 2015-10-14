package action;

import Main.Main;

/**
 * Created by Syiml on 2015/10/3 0003.
 */
public class awardACB {
    public String getUser() {
        return user;
    }
    public String getAcb() {
        return acb;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public void setAcb(String acb) {
        this.acb = acb;
    }
    public void setUser(String user) {
        this.user = user;
    }

    public String user;
    public String acb;
    public String text;

    public String award(){
        if(Main.loginUserPermission().getAwardACB()){
            int x=Main.users.awardACB(user,Integer.parseInt(acb),text);
            if(x>0) return "success";
            else return "error";
        }else{
            return "error";
        }
    }
}
