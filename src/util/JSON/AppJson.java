package util.JSON;

import entity.User;
import util.HTML.HTML;
import util.Main;

/**
 * Created by syimlzhu on 2016/9/18.
 */
public class AppJson {
    public static String getSelfUserInfo(){
        User u = Main.loginUser();
        if(u == null){
            return "{\"ret\":\"NotSigned\"}";
        }else{
            return JSON.getJSONObject(
                    "ret","success",
                    "username",u.getUsername(),
                    "nick",u.getNick(),
                    "headImg", "pic/head/"+u.getUsername()+".jpg"
            ).toString();
        }
    }
}
