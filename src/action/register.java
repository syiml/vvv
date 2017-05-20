package action;

import util.JSON.JSON;
import util.Main;
import entity.User;
import util.Tool;

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
    public int noRedirect = 0;

    private String returnError(){
        out.print(JSON.getJSONObject("ret","fail"));
        return noRedirect==0?ERROR:NONE;
    }
    public String reg(){
        Tool.log(" - Register:"+username);
        if(username==null) return returnError();
        for(int i=0;i<username.length();i++){
            char c=username.charAt(i);
            if(c >='0' && c <='9') continue;
            if(c >='a' && c <='z') continue;
            if(c >='A' && c <='Z') continue;
            if(c == '_') continue;
            return returnError();
        }
        if(password==null) return returnError();
        if(rpass==null) return returnError();
        if(username.length()<5) return returnError();
        if(username.length()>15) return returnError();
        if(password.length()<5) return returnError();
        if(password.length()>15) return returnError();
        if(!rpass.equals(password)) return returnError();
        if(nick.length()>20) return returnError();
        if(nick.equals("")) nick=username;
        if(school.length()>30) return returnError();
        if(motto.length()>50) return returnError();
        User u=new User(this);
        int ret=Main.users.register(u);
        if(ret==1){
            Tool.log("Register:"+username);
            out.print(JSON.getJSONObject("ret","success"));
            return noRedirect==0?SUCCESS:NONE;
        }else if(ret==-1){
            out.print(JSON.getJSONObject("ret","UsernameExist"));
            return noRedirect==0?ERROR:NONE;//用户名已存在
        }else{
            out.print(JSON.getJSONObject("ret","fail"));
            return noRedirect==0?ERROR:NONE;
        }
    }
}
