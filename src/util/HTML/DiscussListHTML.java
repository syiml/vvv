package util.HTML;

import util.Main;
import entity.User;
import dao.Discuss.DiscussSQL;
import entity.Discuss.Discuss;
import util.HTML.FromHTML.FormHTML;
import util.HTML.FromHTML.text.text;
import util.HTML.modal.modal;

import java.util.List;

/**
 * Created by Syiml on 2015/11/20 0020.
 */
public class DiscussListHTML extends pageBean {

    private static String ID="#";
    private static String TITLE="标题";
    private static String TIME="发表时间";
    private static String AUTHOR="发布人";
    private static String REPLY="回复";
    private static String PRIORITY="优先级";
    int cid;
    int num;
    int page;
    String seach;
    String user;
    User LoginUser;
    boolean admin;
    List<Discuss> list;
    int PageNum;
    public DiscussListHTML(int cid,int num,int page,String seach,String user){
        if(seach==null) seach="";
        if(user==null) user="";
        this.cid=cid;
        this.num=num;
        this.page=page;
        this.seach=seach;
        this.user=user;
        this.LoginUser=Main.loginUser();
        admin=Main.loginUserPermission().getAddDiscuss();
        list= DiscussSQL.getDiscussList(cid, num * (page-1), num , admin, seach, user);

        PageNum = getTotalPageNum(DiscussSQL.getDiscussListNum(cid, admin, seach, user) , num );
        addTableHead(ID, TITLE, TIME, AUTHOR, REPLY);
        if(admin) addTableHead(PRIORITY);
    }

    public static String seach(String seach,String user){
        FormHTML f=new FormHTML();
        f.setType(1);
        f.setAction("DiscussList.jsp");
        text t1=new text("search","名称");
        t1.setValue(seach);
        text t2=new text("user","作者");
        t2.setValue(user);
        f.addForm(t1);
        f.addForm(t2);
        f.setSubmitText("查找");
        return f.toHTML();
    }

    @Override
    public String getTitle() {
        String head="讨论";
        if(admin) head+=HTML.floatRight(HTML.a("admin.jsp?page=AddDiscuss","New"));
        if(LoginUser!=null){
            modal m=new modal("adddiscuss","发帖", DiscussHTML.addDiscussForm(-1, cid),"发起新讨论");
            m.setLage();
            m.setAction("adddiscuss2.action");
            m.setFormId("adddiscussForm");
            m.setBtnCls("link btn-xs");
//            modal(String id,String title,String body,String btnlabel){

            String script = "<script type=\"text/javascript\">$(\"#adddiscussForm\").validate({onfocusout: false,rules: {title:{required: true}}});</script>";
            head+=HTML.floatRight(m.toHTML())+script;
        }
        return head;
    }

    @Override
    public int getCurrPageSize() {
        return list.size();
    }

    @Override
    public int getTotalPageNum() {
        return PageNum;
    }

    @Override
    public int getCurrPage() {
        return page;
    }

    @Override
    public String getCellByHead(int i, String colname) {
        Discuss d=list.get(i);
        if(colname.equals(ID)){
            return d.getId() +"";
        }else if(colname.equals(TITLE)){
            String url;
            if(cid==-1){
                url="Discuss.jsp?id="+ d.getId();
            }else{
                url="#D"+ d.getId();
            }
            if(d.isVisiable() && d.isTop()){
                return HTML.textb("【顶】", "red")+HTML.a(url, d.getTitle());
            }else{
                return HTML.a(url, d.getTitle());
            }
        }else if(colname.equals(TIME)){
            if(d.isShowtime()){
                return (d.getTime().toString().substring(0,16));
            }else if(admin){
                return("<em class='small'>"+ d.getTime().toString().substring(0,16)+"</em>");
            }else return "";
        }else if(colname.equals(AUTHOR)){
            if(d.isShowauthor()){
                return Main.users.getUser(d.getUsername()).getTitleAndNick();
            }else if(admin){
                return "<em class='small'>"+Main.users.getUser(d.getUsername()).getTitleAndNick()+"</em>";
            }else return "";
        }else if(colname.equals(REPLY)){
            return d.getReplyNum() +"";
        }else if(colname.equals(PRIORITY)){
            if(!d.isVisiable()) addClass(i+1,-1,"active");
            return d.getPriority() +"";
        }
        return "=ERROR=";
    }

    @Override
    public String getLinkByPage(int page) {
        if(cid==-1){
            return "DiscussList.jsp?page="+page+"&search="+seach+"&user="+user;
        }else{
            return "#DP"+page;
        }
    }

    @Override
    public String rightForm() {
        if(cid!=-1) return "";
        return HTML.floatRight(seach(seach,user));
    }
}
