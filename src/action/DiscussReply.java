package action;


import dao.DiscussSQL;
import entity.User;
import util.Main;
import servise.MessageMain;

/**
 * Created by Syiml on 2015/7/5 0005.
 */
public class DiscussReply {
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setRid(int rid) {
        this.rid = rid;
    }
    public int getId() {
        return id;
    }
    public int getRid() {
        return rid;
    }

    public String text;
    public int id;//discuss id
    public int rid;//replay id

    public String dr(){
        try{
            User u=Main.loginUser();
            Main.log(u.getUsername()+"回复了id为"+id+"的帖子");
            MessageMain.discussAt(id,text,true);
            return DiscussSQL.reply(u,id,text);
        }catch(NumberFormatException e){
            return "error";
        }
    }
    public String hideshow(){
        return DiscussSQL.hideshow(id,rid);
    }
    public String adminReply(){
        Main.log("管理员"+Main.loginUser().getUsername()+"回复了id为"+id+"的回复");
        return DiscussSQL.adminReply(id,rid,text);
    }
}
