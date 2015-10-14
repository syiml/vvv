package action;

import Main.Main;
import Main.User.User;

/**
 * Created by Administrator on 2015/6/3.
 */
public class register {
    public String username;
    public String password;
    public String rpass;
    public String nick="";
    public int gender=0;//性别
    public String school="";
    public String email="";
    public String motto="";//座右铭
    public String reg(){
        if(username==null) return "error";
        if(password==null) return "error";
        if(rpass==null) return "error";
        if(username.length()<5) return "error";
        if(username.length()>15) return "error";
        if(password.length()<5) return "error";
        if(password.length()>15) return "error";
        if(!rpass.equals(password)) return "error";
        if(nick.length()>20) return "error";
        if(nick.equals("")) nick=username;
        if(school.length()>30) return "error";
        if(motto.length()>50) return "error";
        User u=new User(this);
        int ret=Main.users.register(u);
        if(ret==1){
            Main.log("Register:"+username);
            return "success";
        }else if(ret==-1){
            return "error";//用户名已存在
        }else{
            return "error";
        }
    }
}
