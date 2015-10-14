package action;


import Discuss.DiscussSQL;
import Main.User.User;
import Main.Main;

/**
 * Created by Syiml on 2015/7/5 0005.
 */
public class DiscussReply {
    public String getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public String getRid() {
        return rid;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setId(String did) {
        this.id = did;
    }
    public void setRid(String rid) {
        this.rid = rid;
    }

    public String text;
    public String id;
    public String rid;//replay id

    public String dr(){
        try{
            User u=Main.loginUser();
            Main.log(u.getUsername()+"回复了id为"+id+"的帖子");
            return DiscussSQL.reply(u,Integer.parseInt(id),text);
        }catch(NumberFormatException e){
            return "error";
        }
    }
    public String hideshow(){
        return DiscussSQL.hideshow(Integer.parseInt(id),Integer.parseInt(rid));
    }
    public String adminReply(){
        Main.log("管理员"+Main.loginUser().getUsername()+"回复了id为"+id+"的回复");
        return DiscussSQL.adminReply(Integer.parseInt(id),Integer.parseInt(rid),text);
    }
}
