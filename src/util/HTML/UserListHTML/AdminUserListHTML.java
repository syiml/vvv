package util.HTML.UserListHTML;

import util.Main;
import entity.User;
import util.HTML.FromHTML.FormHTML;
import util.HTML.FromHTML.hidden.hidden;
import util.HTML.FromHTML.text.text;
import util.HTML.HTML;
import util.HTML.pageBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/13 0013.
 */
public class AdminUserListHTML extends pageBean {
    List<User> list;
    User u=null;//当前登录的用户
    int num,page;
    int pageNum;
    String search="";
    String order;
    boolean bo;
    public AdminUserListHTML(int page,String search,String order,boolean bo){
        this.order=order;
        this.bo=bo;
        if(search!=null) this.search=search;
        this.page=page;
        num=Main.config.userShowNum;
        list= Main.users.getUsers((page-1) * num, num, search,order,bo);
        pageNum= getTotalPageNum(Main.users.getUsersNum(search),num);
        u=Main.loginUser();
        addTableHead("rank","username");
        addTableHead("name");
        addTableHead("nick","motto","acnum","acb","rating","ratingnum");
        setCl("table table-striped table-hover table-condensed");
    }
    private String search(){
        FormHTML f=new FormHTML();
        f.setType(1);
        text t=new text("search", "查找");
        t.setValue(search);
        f.addForm(t);
        f.setAction("admin.jsp");
        f.addForm(new hidden("order", order));
        f.addForm(new hidden("page", "UserAdmin"));
        if(bo) f.addForm(new hidden("desc","1"));
        f.setSubmitText("确定");
        return f.toHTML();
    }
    private String getHead(String name,String label,boolean def){
        String s="";
        if(order!=null&&order.equals(name)){
            if(bo) s="↓";
            else s="↑";
        }
        StringBuilder link=new StringBuilder("");
        if(order==null||!order.equals(name)||(order.equals(name)&&bo!=def)){
            link.append("&order=").append(name);
            if(def) link.append("&desc=1");
        }else{
            link.append("&order=").append(name);
            if(!def) link.append("&desc=1");
        }
        return HTML.a("admin.jsp?page=UserAdmin&search="+search+link,label+s);
    }

    @Override
    public String getTitle() {
        return "用户排名-管理";
    }

    @Override
    public String getColname(String colname){
        if(colname.equals("rank")) return (getHead("rank", "Rank", false));
        if(colname.equals("name")) return "Name";
        if(colname.equals("username")) return (getHead("username","UserName",false));
        if(colname.equals("nick")) return (getHead("nick","Nick",false));
        if(colname.equals("motto")) return (getHead("motto","Motto",false));
        if(colname.equals("acnum")) return (getHead("acnum","AC",true));
        if(colname.equals("acb")) return (getHead("acb","ACB",true));
        if(colname.equals("rating")) return (getHead("rating","Rating",true));
        if(colname.equals("ratingnum")) return (getHead("ratingnum","#",true));
        return "=ERROR=";
    }

    @Override
    public int getCurrPageSize() {
        return list.size();
    }

    @Override
    public int getTotalPageNum() {
        return pageNum;
    }

    @Override
    public int getCurrPage() {
        return page;
    }

    @Override
    public String getCellByHead(int i, String colname) {
        User s=list.get(i);
        if(colname.equals("rank")){
            return s.getRank()+"";
        }
        if(colname.equals("username")){
//            addClass(i+1,3,"break");
//            addClass(i+1,2,"break");
//            addClass(i+1,1,"break");
//            if(u!=null && u.getUsername().equals(s.getUsername())){
//                addClass(i+1,-1,"info");
//            }
            return HTML.a("admin.jsp?page=UserAdmin&user="+s.getUsername(),s.getUsernameHTMLNoA());
        }
        if(colname.equals("name")){
            return s.getName();
        }
        if(colname.equals("nick")){
            return s.getNick();
        }
        if(colname.equals("motto")){
            return s.getMotto();
        }
        if(colname.equals("acnum")){
            return s.getAcnum()+"";
        }
        if(colname.equals("acb")) {
            return s.getAcb()+"";
        }
        if(colname.equals("rating")){
            return s.getRatingHTML();
        }
        if(colname.equals("ratingnum")){
            return s.getRatingnum()+"";
        }
        return "=ERROR=";
    }

    @Override
    public String getLinkByPage(int page) {
        return "admin.jsp?page=UserAdmin&pa="+page+(search==null?"":"&search="+search)+(order==null?"":"&order="+order)+(bo?"&desc=1":"");
    }

    @Override
    public String rightForm() {
        return search();
    }

}
