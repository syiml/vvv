package action;

import Main.Main;

/**
 * Created by Administrator on 2015/6/4.
 */
public class login extends BaseAction{
    public String user;
    public String pass;
    public String login(){
        String ret= Main.users.login(user,pass);
        if(ret.equals("LoginSuccess")) {
            Main.log("Login:"+user+" IP:"+Main.getIP());
            session.setAttribute("user", Main.users.getUser(user));
            out.println("{\"ret\":\"LoginSuccess\"}");
        }else {
            out.println("{\"ret\":\""+ret+"\"}");
        }
        return null;
    }
}
