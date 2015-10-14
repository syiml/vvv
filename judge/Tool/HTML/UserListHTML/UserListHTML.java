package Tool.HTML.UserListHTML;

import Main.Main;
import Main.User.User;
import Main.contest.Contest;
import Main.contest.RegisterUser;
import Tool.HTML.FromHTML.FormHTML;
import Tool.HTML.FromHTML.form;
import Tool.HTML.FromHTML.hidden.hidden;
import Tool.HTML.FromHTML.select.select;
import Tool.HTML.FromHTML.text.text;
import Tool.HTML.FromHTML.text_in.text_in;
import Tool.HTML.HTML;
import Tool.HTML.TableHTML.TableHTML;
import Tool.HTML.modal.modal;
import action.RegisterContest;
import sun.misc.FormattedFloatingDecimal;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syiml on 2015/6/24 0024.
 */
public class UserListHTML {
    List<User> list;
    List<List<String>> list2;
    int type=0;
    int cid;
    User u=null;//当前登录的用户
    Contest c=null;//列表所属比赛（仅kind==1时有效）
    int num,page;
    String search="";
    boolean admin=false;
    String order;
    boolean bo;
    public UserListHTML(int page,String search,String order,boolean bo){
        this.order=order;
        this.bo=bo;
        if(search!=null) this.search=search;
        this.page=page;
        num=Main.userShowNum;
        list= Main.users.getUsers(page * num, num + 1, search,order,bo);
        type=0;
        u=Main.loginUser();
        if(Main.loginUserPermission().getContestRegisterAdmin()) admin=true;
    }
    public UserListHTML(int cid,int page,String search){
        if(search!=null) this.search=search;
        this.page=page;
        int num=Main.userShowNum;
        type=1;
        this.cid=cid;
        c=Main.contests.getContest(cid);
        list2=Main.users.getUsers(cid, page * num, num + 1, search, c.getKind() == 3);
        u=Main.loginUser();
        if(Main.loginUserPermission().getContestRegisterAdmin()) admin=true;
    }
    private String contestuseradmin(){
        String ss="";
        if(c.getType()==3||c.getType()==4){
            if(u!=null){
                RegisterUser z=Main.contests.getRegisterStatu(u.getUsername(),cid);
                if(z==null){
                    if(c.getRegisterendtime().before(Main.now())){
                        ss+="报名已经结束";
                    }else if(c.getRegisterstarttime().after(Main.now())) {
                        ss+="报名还没开始";
                    }else{
                    ss+=HTML.a("registercontest.action?cid="+cid,"立即报名");
                }
                }else{
                    ss+="已报名，状态："+RegisterUser.statuToHTML(z.getStatu());
                }
            }else{
                if(c.getRegisterendtime().after(Main.now())){
                    ss+="报名前先"+HTML.a("Login.jsp","登录");
                }else if(c.getRegisterstarttime().after(Main.now())){
                    ss+="报名还未开始";
                }else{
                    ss+="报名已经结束";
                }
            }
        }
        String r="";
        if(c.getType()==3||c.getType()==4){
            r+="报名时间："+c.getRegisterstarttime().toString().substring(0,16)+
                    " ～ "+c.getRegisterendtime().toString().substring(0,16);
        }
        if(u!=null&&u.getPermission().getAddContest()){
            r=adminForm(r);
        }
        ss+=HTML.floatRight(r);
        return HTML.div("panel-body","style='padding:5px'",ss);
    }
    private String adminButtons(String username,String info){
        //pd、ac、no、*、del
        String s="";
        s+=HTML.a("setregistercontest.action?cid="+cid+"&username="+username+"&statu=0","等待")+" ";
        s+=HTML.a("setregistercontest.action?cid="+cid+"&username="+username+"&statu=1","通过")+" ";
        s+=HTML.a("setregistercontest.action?cid="+cid+"&username="+username+"&statu=-1","拒绝")+" ";
        s+=HTML.a("setregistercontest.action?cid="+cid+"&username="+username+"&statu=2","非正式")+" ";
        //String id,String title,String body,String btnlabel
        s+=HTML.a("javascript:retry('"+username+"','"+info+"','"+cid+"')","需修改")+" ";
        s+=HTML.a("setregistercontest.action?cid="+cid+"&username="+username+"&statu=-2","删除");
        return s;
    }
    private String adminForm(String s){
        FormHTML form=new FormHTML();
        form.setAction("setregistercontest.action");
        form.setType(1);
        text_in t=new text_in(s+" ");
        form.addForm(t);
        hidden t1=new hidden("cid",cid+"");
        form.addForm(t1);
        text t2=new text("username","添加用户");
        form.addForm(t2);
        select s1=new select("statu","状态");
        //0pending 1accepted -1no  2*
        s1.add(0,"等待");
        s1.add(1,"通过");
        s1.add(-1,"拒绝");
        s1.add(2,"非正式");
        s1.setValue("0");
        s1.setId("statu");
        s1.setType(1);

        form.addForm(s1);
        form.setSubmitText("提交");
        return form.toHTML();
    }
    private String toHTML1(){//contest users
        TableHTML table=new TableHTML();
        Contest c=Main.contests.getContest(cid);

        table.setClass("table table-striped table-hover table-condensed"+(c.getKind()==3?" table-bordered table-small":""));
        //table.addColname("#");
        table.addColname("用户名");
        if(c.getKind()==3){//正式比赛
            table.addColname("姓名","性别","学院","专业","班级","学号");
        }
        table.addColname("昵称");
        table.addColname("Rating");
        table.addColname("状态");
        table.addColname("时间");

        if(admin){
            table.addColname("admin");
        }
        for(int i=0;i<list2.size();i++){
            List<String> aList2=list2.get(i);
                if(c.getKind()==3) {
                        if(aList2.get(2).equals("1")){
                            aList2.set(2,"男");
                        }else if(aList2.get(2).equals("2")){
                            table.addCl(i+1,-1,"danger");
                            table.addCl(i+1,2,"danger");
                            aList2.set(2, "女");
                        }else {
                            aList2.set(2,"");
                        }
            }
            if(u!=null && u.getUsername().equals(aList2.get(0))) {
                table.addCl(i + 1, -1, "info");
            }
            if(admin){
                String info;
                if(c.getKind()==3){
                    info=aList2.get(11);
                    aList2.remove(11);
                }else{
                    info=aList2.get(5);
                    aList2.remove(5);
                }
                aList2.add(adminButtons(aList2.get(0),info));
            }
            User u=Main.users.getUser(aList2.get(0));
            String s=aList2.get(0);
            if(u!=null)  s=u.getUsernameHTML();
            aList2.set(0,s);
            table.addRow(aList2);
        }
        String title=Main.contests.getContest(cid).getName()+" 报名列表";
        return HTML.panelnobody(title, contestuseradmin()+table.HTML());
    }

    private String topHref(){
        return "User.jsp?search="+search+"&order="+order+(bo?"&desc=1":"");
    }
    private String preHref(){
        return "User.jsp?page="+(page-1)+"&search="+search+"&order="+order+(bo?"&desc=1":"");
    }
    private String nextHref(){
        return "User.jsp?page="+(page+1)+"&search="+search+"&order="+order+(bo?"&desc=1":"");
    }
    private String page(){
        String size="sm";
        String s ="<div class='btn-toolbar' role='toolbar'>";
        s+="<div class='btn-group' role='group'>";
        s+=HTML.abtn(size,topHref(),"首页","");
        s+="</div>";
        s+="<div class='btn-group' role='group'>";
        if(page!=0){
            s+=HTML.abtn(size,preHref(),"上一页","");
        }else{
            s+=HTML.abtn(size,"","上一页","disabled");
            //disabled='disabled'
        }
        s+=HTML.abtn(size,null,(page+1)+"","");
        if(list.size()==num+1){
            s+=HTML.abtn(size,nextHref(),"下一页","");
        }else{
            s+=HTML.abtn(size,"","下一页","disabled");
        }
        s+="</div></div>";
        return s;
    }
    private String seach(){
        FormHTML f=new FormHTML();
        f.setType(1);
        text t=new text("search", "查找");
        t.setValue(search);
        f.addForm(t);
        f.setAction("User.jsp");
        f.addForm(new hidden("order", order));
        if(bo) f.addForm(new hidden("desc","1"));
        f.setSubmitText("确定");
        return f.toHTML();
    }
    private String getHead(String name,String label,boolean def){
        String s="";
        if(order.equals(name)){
            if(bo) s="↓";
            else s="↑";
        }
        String link="";
        if(order==null||!order.equals(name)||(order.equals(name)&&bo!=def)){
            link+="&order="+name;
            if(def) link+="&desc=1";
        }else{
            link+="&order="+name;
            if(!def) link+="&desc=1";
        }
        return HTML.a("User.jsp?search="+search+link,label+s);
    }
    public String toHTML(){//users rank
        if(type==1) return toHTML1();
        TableHTML table=new TableHTML();
        table.setClass("table table-striped table-hover table-condensed");
        table.addColname(getHead("rank", "Rank", false));
        table.addColname(getHead("username","UserName",false));
        table.addColname(getHead("nick","Nick",false));
        table.addColname(getHead("motto","Motto",false));
        table.addColname(getHead("acnum","AC",true));
        table.addColname(getHead("acb","ACB",true));
        table.addColname(getHead("rating","Rating",true));
        table.addColname(getHead("ratingnum","#",true));
        User u=(User)Main.getSession().getAttribute("user");
        int size=list.size();
        if(size==num+1) size=num;
        for(int i=0;i<size;i++){
            table.addCl(i+1,3,"break");
            table.addCl(i+1,2,"break");
            table.addCl(i+1,1,"break");
            List<String> row=new ArrayList<String>();
            User s=list.get(i);
            if(u!=null && u.getUsername().equals(s.getUsername())){
                table.addCl(i+1,-1,"info");
            }
            row.add(s.getRank()+"");
            row.add(s.getUsernameHTML());//颜色和链接
            row.add(s.getNick());
            row.add(s.getMotto());
            //row.add(Main.status.getAcNum(s.getUsername())+"");
            row.add(s.getAcnum()+"");
            row.add(s.getACB()+"");
           /* if(s.getAcnum()==-1){
                s.setAcnum(Main.status.getAcNum(s.getUsername()));
            }
            row.add(s.getAcnum()+"");*/
//            row.add("");
            row.add(User.ratingToHTML(s.getShowRating()));//颜色
            row.add(s.getRatingnum()+"");
            table.addRow(row);
        }
        String sss=HTML.floatLeft(page())+HTML.floatRight(seach());
        return HTML.panelnobody("用户排名",HTML.div("panel-body","style='padding:5px'",sss)+table.HTML());
    }
}
