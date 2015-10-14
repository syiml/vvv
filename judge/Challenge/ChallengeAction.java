package Challenge;

import org.apache.struts2.ServletActionContext;

/**
 * Created by Syiml on 2015/10/10 0010.
 */
public class ChallengeAction {
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private String user;
    public String init(){
        if(user==null||user.equals("")) user=Main.Main.loginUser().getUsername();
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        Main.Main.getOut().println(ChallengeJSON.getBlockList(user));
        return "none";
    }
}
