package action;

import entity.Discuss;
import dao.DiscussSQL;
import util.HTML.HTML;
import util.Main;
import util.Tool;

/**
 * Created by Syiml on 2015/7/4 0004.
 */
public class addDiscuss extends BaseAction{
    public String id;
    public int cid;
    public String title;
    public String text;
    public String priority;
    public String top;
    public String visiable;
    public String reply;
    public String panelclass;
    public String panelnobody;
    public String showauthor;
    public String showtime;
    public String shownum;
    public String replyhidden;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getVisiable() {
        return visiable;
    }

    public void setVisiable(String visiable) {
        this.visiable = visiable;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getPanelclass() {
        return panelclass;
    }

    public void setPanelclass(String panelclass) {
        this.panelclass = panelclass;
    }

    public String getPanelnobody() {
        return panelnobody;
    }

    public void setPanelnobody(String panelnobody) {
        this.panelnobody = panelnobody;
    }

    public String getReplyhidden() {
        return replyhidden;
    }

    public void setReplyhidden(String replyhidden) {
        this.replyhidden = replyhidden;
    }

    public String getShowauthor() {
        return showauthor;
    }

    public void setShowauthor(String showauthor) {
        this.showauthor = showauthor;
    }

    public String getShowtime() {
        return showtime;
    }

    public void setShowtime(String showtime) {
        this.showtime = showtime;
    }

    public String getShownum() {
        return shownum;
    }

    public void setShownum(String shownum) {
        this.shownum = shownum;
    }

    public String add(){//管理员发帖
        if(!Main.loginUserPermission().getAddDiscuss()) return ERROR;
        Discuss d=new Discuss(this);
        d.setAdmin();
        return d.goAddOrEdit();
    }
    public String add2(){//一般用户的发帖和修改
        priority="-1";
        top=null;
        visiable="";
        reply="";
        panelclass="0";
        panelnobody=null;
        showauthor="";
        showtime="";
        shownum="-1";
        text = HTML.HTMLtoString(text);
        Discuss d=new Discuss(this);
        return d.goAddOrEdit();
    }
    public String append(){
        priority="-1";
        top=null;
        visiable="";
        reply="";
        panelclass="0";
        panelnobody=null;
        showauthor="";
        showtime="";
        shownum="-1";
        text = HTML.HTMLtoString(text);
        Discuss d=new Discuss(this);
        Tool.log(Main.loginUser().getUsername()+"追加了帖子【"+d.getTitle()+"】");
        DiscussSQL.append(d);
        return SUCCESS;
    }
}
