package entity.Discuss;

import entity.IBeanResultSetCreate;
import entity.ICanToJSON;
import entity.User;
import net.sf.json.JSONObject;
import util.Main;
import dao.Discuss.DiscussSQL;
import servise.MessageMain;
import action.addDiscuss;
import util.Tool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by Syiml on 2015/7/3 0003.
 */
public class Discuss implements IBeanResultSetCreate<Discuss>, ICanToJSON {
    static String[] PanelClass={"default","primary","success","info","warning","danger"};
    /**
     * id 主键
     */
    int id;
    /**
     * cid 所属contest编号，-1表示不属于任何contest（即全局）
     */
    int cid;
    /**
     * 标题
     */
    String title;
    /**
     * 发布人
     */
    String username;
    /**
     * 发布时间
     */
    Timestamp time;
    /**
     * 内容，支持HTML代码
     */
    String text;
    /**
     * 优先级
     */
    double priority;
    /**
     * 置顶，true则在首页显示
     */
    boolean top;
    /**
     * 可见，false即为隐藏
     */
    boolean visiable;
    /**
     * 可以回复，true即可以回复
     */
    boolean reply;
    /**
     * 所属panel的calss样式
     */
    int panelclass;
    /**
     * 所示panel不显示body，一般来讲如果内容是一个表格的话，为true
     */
    boolean panelnobody=false;
    /**
     * 显示作者。为false则一般看不见作者
     */
    boolean showauthor;
    /**
     * 显示时间。为false则一般用户看不见发布时间
     */
    boolean showtime;
    /**
     * 首页显示字数。top==true时有效，实际上是在首页显示时取出text字符串的前shownum个字符。-1为全部显示。bug：会截断HTML代码。。。
     */
    int shownum;
    /**
     * 回复默认隐藏。如果为true则新发表的回复默认是隐藏的
     */
    boolean replyHidden=false;
    /**
     * 回复的数量
     */
    int replyNum=0;
    /**
     * 当前登录是否有admin权限
     */
    boolean isAdmin=false;
    public Discuss(){}

    public Discuss(addDiscuss ad){
        id= Integer.parseInt(ad.getId());
        cid=ad.getCid();
        title=ad.getTitle();
        User u=((User) Main.getSession().getAttribute("user"));
        if(u==null) username="";
        else  username=u.getUsername();
        time=new Timestamp(System.currentTimeMillis());
        text=ad.getText();
        if(text == null) text = "";
        try {
            priority = Double.parseDouble(ad.getPriority());
        }catch(NumberFormatException e){
            priority = -1;
        }
        top=ad.getTop()!=null;
        visiable=ad.getVisiable()!=null;
        reply=ad.getReply()!=null;
        panelclass= Integer.parseInt(ad.getPanelclass());
        panelnobody=ad.getPanelnobody()!=null;
        showauthor=ad.getShowauthor()!=null;
        showtime=ad.getShowtime()!=null;
        shownum= Integer.parseInt(ad.getShownum());
        replyHidden=ad.getReplyhidden()!=null;
    }

    public static String[] getPanelClass() {
        return PanelClass;
    }

    public static void setPanelClass(String[] panelClass) {
        PanelClass = panelClass;
    }

    @Override
    public Discuss init(ResultSet rs) throws SQLException {
        id=rs.getInt("id");
        cid=rs.getInt("cid");
        title=rs.getString("title");
        username=rs.getString("username");
        time=rs.getTimestamp("time");
        text=rs.getString("text");
        priority=rs.getDouble("priority");
        top=rs.getBoolean("top");
        shownum=rs.getInt("shownum");
        visiable=rs.getBoolean("visiable");
        reply=rs.getBoolean("reply");
        panelclass=rs.getInt("panelclass");
        panelnobody=rs.getBoolean("panelnobody");
        showauthor=rs.getBoolean("showauthor");
        showtime=rs.getBoolean("showtime");
        replyHidden=rs.getBoolean("replyhidden");
        replyNum=rs.getInt("replynum");
        return this;
    }

    public boolean isReplyHidden() {
        return replyHidden;
    }

    public void setReplyHidden(boolean replyHidden) {
        this.replyHidden = replyHidden;
    }

    public void setAdmin(){
        isAdmin =true;}

//    public Discuss(){
//
//    }
    public String getUsername(){return username;}

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle(){return title;}

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId(){return id;}

    public void setId(int id) {
        this.id = id;
    }

    public String goAddOrEdit(){
        User u=Main.loginUser();
        if(u==null) return "error";
        else username=u.getUsername();
        if(id==-1){
            Tool.log(Main.loginUser().getUsername()+"发表了新帖子 【"+title+"】");
            int id= DiscussSQL.addDiscuss(this);
            if(visiable){
                this.id=id;
                MessageMain.discussAt(id,text,false);
            }
        }else{
            Tool.log(Main.loginUser().getUsername()+"修改了帖子 【"+title+"】");
            DiscussSQL.editDiscuss(this);
        }
        return "error";
    }

    public void setAdmin(boolean admin) {
        this.isAdmin = admin;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public boolean isVisiable() {
        return visiable;
    }

    public void setVisiable(boolean visiable) {
        this.visiable = visiable;
    }

    public boolean isReply() {
        return reply;
    }

    public void setReply(boolean reply) {
        this.reply = reply;
    }

    public int getPanelclass() {
        return panelclass;
    }

    public void setPanelclass(int panelclass) {
        this.panelclass = panelclass;
    }

    public boolean isPanelnobody() {
        return panelnobody;
    }

    public void setPanelnobody(boolean panelnobody) {
        this.panelnobody = panelnobody;
    }

    public boolean isShowauthor() {
        return showauthor;
    }

    public void setShowauthor(boolean showauthor) {
        this.showauthor = showauthor;
    }

    public boolean isShowtime() {
        return showtime;
    }

    public void setShowtime(boolean showtime) {
        this.showtime = showtime;
    }

    public int getShownum() {
        return shownum;
    }

    public void setShownum(int shownum) {
        this.shownum = shownum;
    }

    public int getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }

    public boolean isadmin() {
        return isAdmin;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("id",id);
        jo.put("title",title);
        jo.put("text",text);
        jo.put("username",username);
        jo.put("time",time.toString().substring(0,16));
        return jo;
    }
}
