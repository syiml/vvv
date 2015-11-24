package action;

import util.Main;
import entity.User;

/**
 * Created by Administrator on 2015/6/3.
 */
public class register extends BaseAction{
    public String username;
    public String password;
    public String rpass;
    public String nick="";
    public int gender=0;//性别
    public String school="";
    public String email="";
    public String motto="";//座右铭
    public String reg(){
        if(username==null) return ERROR;
        if(password==null) return ERROR;
        if(rpass==null) return ERROR;
        if(username.length()<5) return ERROR;
        if(username.length()>15) return ERROR;
        if(password.length()<5) return ERROR;
        if(password.length()>15) return ERROR;
        if(!rpass.equals(password)) return ERROR;
        if(nick.length()>20) return ERROR;
        if(nick.equals("")) nick=username;
        if(school.length()>30) return ERROR;
        if(motto.length()>50) return ERROR;
        User u=new User(this);
        int ret=Main.users.register(u);
        if(ret==1){
            Main.log("Register:"+username);
            return SUCCESS;
        }else if(ret==-1){
            return ERROR;//用户名已存在
        }else{
            return ERROR;
        }
    }
}
