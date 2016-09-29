package action;

import util.Main;
import util.Tool;

/**
 * Created by Administrator on 2015/6/4.
 */
public class login extends BaseAction{
    public String user;
    public String pass;
    public String login(){
        String ret= Main.users.login(user,pass);
        if(ret.equals("LoginSuccess")) {
            session.setAttribute("user", Main.users.getUser(user));
            Tool.log("Login:"+user+" IP:"+Main.getIP());
            out.println("{\"ret\":\"LoginSuccess\"}");
        }else {
            out.println("{\"ret\":\""+ret+"\"}");
        }
        return NONE;
    }
}
