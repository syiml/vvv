package Discuss;

import Main.Main;
import Main.User.Permission;
import Main.User.User;
import Tool.HTML.FromHTML.FormHTML;
import Tool.HTML.FromHTML.check.check;
import Tool.HTML.FromHTML.select.select;
import Tool.HTML.FromHTML.text.text;
import Tool.HTML.FromHTML.textarea.textarea;
import Tool.HTML.HTML;
import Tool.HTML.TableHTML.TableHTML;
import Tool.HTML.modal.modal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syiml on 2015/7/3 0003.
 */
public class DiscussHTML {
    Discuss d;
    User loginuser;
    public static String IndexDiscuss(Discuss d){
        String title=d.title;
        String s="";
        if(d.showtime||d.showauthor){
            s+="bulid";
            if(d.showauthor) s+=" by "+ Main.users.getUser(d.username).getUsernameHTML();
            if(d.showtime) s+=" at "+d.time.toString().substring(0,16);
        }
        s+=" "+HTML.a("Discuss.jsp?id="+d.id,"<span class='badge'>→</span>");
        title+=HTML.floatRight(s);
        String body;
        if(d.shownum==-1||d.text.length()<=d.shownum){
            body=d.text;
        }else{
            body=d.text.substring(0,d.shownum)+" ..."+HTML.a("Discuss.jsp?id="+d.id,"show all");
        }
        if(d.panelnobody){
            return HTML.panelnobody(title, Discuss.PanelClass[d.panelclass],body,"style='word-break:break-all'");
        }else{
            return HTML.panel(title,body,null,Discuss.PanelClass[d.panelclass],"style='word-break:break-all'");
        }
    }
    public static String IndexDiscuss(){
        String s="";
        List<Discuss> list=DiscussSQL.getDiscussTOP(false);
        for(Discuss d:list){
            s+=IndexDiscuss(d);
        }
        return s;
    }
    public static String Discuss(String id,String pa){
        int idInt,paInt;
        try{
            idInt=Integer.parseInt(id);
            paInt=Integer.parseInt(pa);
            DiscussHTML d=new DiscussHTML();
            d.d=DiscussSQL.getDiscuss(idInt);
            d.loginuser=(User)Main.getSession().getAttribute("user");
            if(d.d.visiable||(d.loginuser!=null&&d.loginuser.getPermission().getAddDiscuss()))
                return d.Discuss()+d.DiscussReply(paInt)+d.ReplyForm();
            else return HTML.panel("ERROR","没有权限",null,"danger");
        }catch(NumberFormatException e){
            return HTML.panel("ERROR", "参数错误", null, "danger");
        }
    }
    public static String page(int page,String seach,String user,boolean havanext){
        if(seach==null) seach="";
        if(user==null) user="";
        String size="sm";
        String s ="<div class='btn-toolbar' role='toolbar'>";
        s+="<div class='btn-group' role='group'>";
        s+=HTML.abtn(size,"DiscussList.jsp?seach="+seach+"&user="+user,"首页","");
        s+="</div>";
        s+="<div class='btn-group' role='group'>";
        if(page!=0){
            s+=HTML.abtn(size,"DiscussList.jsp?page="+(page-1)+"&seach="+seach+"&user="+user,"上一页","");
        }else{
            s+=HTML.abtn(size,"","上一页","disabled");
            //disabled='disabled'
        }
        s+=HTML.abtn(size,null,(page+1)+"","");
        if(havanext){
            s+=HTML.abtn(size,"DiscussList.jsp?page="+(page+1)+"&seach="+seach+"&user="+user,"下一页","");
        }else{
            s+=HTML.abtn(size,"","下一页","disabled");
        }
        s+="</div></div>";
        return s;
    }
    public static String seach(String seach,String user){
        FormHTML f=new FormHTML();
        f.setType(1);
        f.setAction("DiscussList.jsp");
        text t1=new text("seach","名称");
        t1.setValue(seach);
        text t2=new text("user","作者");
        t2.setValue(user);
        f.addForm(t1);
        f.addForm(t2);
        f.setSubmitText("查找");
        return f.toHTML();
    }
    public static String DiscussList(int num,String page,String seach,String user){
        int pageInt;
        try{
            pageInt=Integer.parseInt(page);
        }catch(NumberFormatException e){
            pageInt=0;
        }
        User u=(User)Main.getRequest().getSession().getAttribute("user");
        Permission p;
        if(u!=null){
            p=Main.getPermission(u.getUsername());
        }else{
            p=new Permission();
        }
        List<Discuss> list;
        list=DiscussSQL.getDiscussList(num*pageInt,num+1,p.getAddDiscuss(),seach,user);
        TableHTML table=new TableHTML();
        table.setClass("table table-hover");
        table.addColname("#");
        table.addColname("title");
        table.addColname("time");
        table.addColname("author");
        if(p.getAddDiscuss()) table.addColname("priority");
        int size=list.size();
        boolean havenext=(size==num+1);
        if(havenext) size=num;
        for(int i=0;i<size;i++){
            Discuss d=list.get(i);
            List<String> row=new ArrayList<String>();
            row.add(d.id+"");
            if(d.visiable&&d.top){
//                row.add(HTML.a("Discuss.jsp?id="+d.id,HTML.textb(d.title,"red")));
                row.add(HTML.textb("【顶】","red")+HTML.a("Discuss.jsp?id="+d.id,d.title));
            }else{
                row.add(HTML.a("Discuss.jsp?id="+d.id,d.title));
            }
            String timetext=d.time.toString().substring(0,16);
            String authortext=Main.users.getUser(d.username).getUsernameHTML();
            if(d.showtime){row.add(timetext);}
            else if(p.getAddContest()){row.add("<em class='small'>"+timetext+"</em>");}
            else{row.add("");}
            if(d.showauthor){row.add(authortext);}
            else if(p.getAddContest()){row.add("<em class='small'>"+authortext+"</em>");}
            else{row.add("");}
            if(p.getAddDiscuss()){
                row.add(d.priority+"");
                if(!d.visiable)table.addCl(i+1,-1,"active");
            }
            table.addRow(row);
        }

        String head="Discuss";
        if(p.getAddDiscuss()) head+=HTML.floatRight(HTML.a("admin.jsp?page=AddDiscuss","New"));
        if(u!=null){
            modal m=new modal("adddiscuss","发帖",addDiscussForm(-1),"发起新讨论");
            m.setAction("adddiscuss2.action");
            m.setBtnCls("link btn-xs");
//            modal(String id,String title,String body,String btnlabel){
            head+=HTML.floatRight(m.toHTML());
        }
        String pageandseach=HTML.floatLeft(page(pageInt,seach,user,havenext))+HTML.floatRight(seach(seach,user));
        return HTML.panelnobody(head,HTML.div("panel-body","style='padding:5px'",pageandseach)+table.HTML());
    }
    public static String adminAddDiscussForm(){
        int did=-1;
        try{
            did=Integer.parseInt(Main.getRequest().getParameter("id"));
        }catch(NumberFormatException e){
            did=-1;
        }
        Discuss d=null;
        if(did!=-1) d=DiscussSQL.getDiscuss(did);
        FormHTML form =new FormHTML();
        form.setAction("adddiscuss.action");

        text f0=new text("id","id");
        f0.setDisabled();
        f0.setValue(did + "");
        form.addForm(f0);

        text f1=new text("title","标题");
        if(d!=null) f1.setValue(d.title);
        form.addForm(f1);

        text f2=new text("priority","优先级");
        f2.setPlaceholder("默认等于id，接收浮点数");
        if(d!=null) f2.setValue(d.priority+"");
        form.addForm(f2);

        select f6=new select("panelclass","样式");
        for(int i=0;i<Discuss.PanelClass.length;i++) {
            f6.add(i,Discuss.PanelClass[i]);
        }
        if(d!=null) f6.setValue(d.panelclass+"");
        form.addForm(f6);

        text f10=new text("shownum","首页显示字数");
        if(d!=null) f10.setValue(d.shownum+"");
        else f10.setValue("-1");
        form.addForm(f10);

        check f3=new check("top","在首页显示");
        if(d!=null&&d.top) f3.setChecked();
        form.addForm(f3);

        check f4=new check("visiable","可见");
        if(d!=null){if(d.visiable) f4.setChecked();}
        else f4.setChecked();
        form.addForm(f4);

        check f5=new check("reply","可以回复");
        if(d!=null){if(d.reply) f5.setChecked();}
        else f5.setChecked();
        form.addForm(f5);

        check f7=new check("panelnobody","panelnobody");
        if(d!=null&&d.panelnobody) f7.setChecked();
        form.addForm(f7);

        check f8=new check("showauthor","显示作者");
        if(d!=null){if(d.showauthor) f8.setChecked();}
        else f8.setChecked();
        form.addForm(f8);

        check f9=new check("showtime","显示时间");
        if(d!=null){
            if(d.showtime) f9.setChecked();
        }else f9.setChecked();
        form.addForm(f9);

        check f12=new check("replyhidden","回复默认隐藏");
        if(d!=null){
            if(d.replyHidden) f12.setChecked();
        }else f12.setChecked();
        form.addForm(f12);

        textarea f11 = new textarea("text","text");
        if(d!=null) f11.setValue(HTML.HTMLtoString(d.text));
        else f11.setValue("");
        f11.setPlaceholder("允许输入HTML代码");
        form.addForm(f11);

        form.setCol(2,10);
        return form.toHTML();
    }
    public static String addDiscussForm(int did){
        Discuss d=null;
        if(did!=-1) d=DiscussSQL.getDiscuss(did);

        text f0=new text("id","id");
        f0.setDisabled();
        f0.setValue(did + "");

        text f1=null;
        if(did==-1){
            f1=new text("title","标题");
            f1.setId("title");
        }

        textarea f11 = new textarea("text","text");
//        if(d!=null) f11.setValue(HTML.HTMLtoString(d.text));
//        else
        f11.setValue("");
        f11.setPlaceholder("这里输入正文");

        return HTML.row(f0.toHTML())+(f1!=null?f1.toHTML(2, 10):"")+HTML.row(HTML.col(12,f11.toHTML()));
    }
    public String page(int page){
        int pagenum=(DiscussSQL.getNewReplyId(d.id)-2)/Main.discussShowNum+1;
        if(pagenum==1) return "";
        String size="sm";
        String s ="<div class='btn-toolbar' role='toolbar'>";
        s+="<div class='btn-group' role='group'>";
        for(int i=0;i<pagenum;i++){s+=HTML.abtn(size,"Discuss.jsp?id="+d.id+"&page="+(i),i+1+"",page==i?"btn-primary":"");}
        s+="</div></div>";
        return HTML.div("","style='margin-bottom: 15px;'",s);
    }
    public String DiscussReply(int page){
        int num=Main.discussShowNum;
        int did=d.id;
        List<DiscussReply> list=DiscussSQL.getDiscussReplay(did, page * num+1, (page + 1) * num );
        String s="";
        for (DiscussReply aList : list) {
            s += EveryDiscussReply(aList);
        }
        s+=page(page);
        return s;
    }
    public String Reply(DiscussReply r){
        text t=new text("text","text");
        if(r.adminreplay!=null) t.setValue(r.adminreplay);
        text t1=new text("id","text");
        t1.setDisabled();
        t1.setValue(r.did+"");
        text t2=new text("rid","text");
        t2.setValue(r.rid+"");
        t2.setDisabled();
        modal m=new modal("reply-"+r.rid,"回复",t.toHTML(2,10)+t1.toHTML()+t2.toHTML(),"回复");
        m.setBtnCls("link btn-xs");
        m.setAction("adminReplay");
        return m.toHTML();
    }
    public String EveryDiscussReply(DiscussReply r){
        boolean admin=false;
        if(loginuser!=null){
            admin=loginuser.getPermission().getAddDiscuss();
        }
        User u=Main.users.getUser(r.username);
        //if(!r.visiable&&(loginuser==null||(loginuser!=null&&(!loginuser.getUsername().equals(u.getUsername())||!admin)))){
        if(!r.visiable){
            if(loginuser==null) return "";
            else if(!loginuser.getUsername().equals(u.getUsername())){
                if(!admin){
                    return "";
                }
            }
        }
        if(!r.visiable) r.panelclass=4;
        String title="<span class='badge'>"+r.rid+"</span> reply by "+u.getUsernameHTML()+" at "+r.time.toString().substring(0,16);
        String right="";
        if(!r.visiable) right="该条已经被隐藏 ";
        if(admin){
            if(!r.visiable) right+=HTML.a("showhide.action?id="+r.did+"&rid="+r.rid,"<span class='badge'>Show</span>");
            else right+=HTML.a("showhide.action?id="+r.did+"&rid="+r.rid,"<span class='badge'>Hide</span>");
        }
        title+=HTML.floatRight(right);

        if(admin) title+=Reply(r);
        String l="";
        l+= HTML.div("", HTML.headImg(u.getUsername(), 1));
        l+=HTML.center(HTML.a("UserInfo.jsp?user="+u.getUsername(), u.getNick()));
        l+=HTML.center("Rating:"+u.getRatingHTML());

        String footer=null;
        if(r.adminreplay!=null){
            footer=HTML.textb("管理员回复: ","black")+r.adminreplay;
        }

        TableHTML table=new TableHTML();
        table.addColname("","");
        table.addCl(0, -1, "hidden");
        table.addRow(l, HTML.pre(r.text)+(footer!=null?HTML.div("adminreply",footer):""));
        table.addCl(1, 1, "discussright");
        table.addCl(1,0,"discussleft");
        String body=table.HTML();


        return HTML.panel(title, body,null, Discuss.PanelClass[r.panelclass],false);
    }
    public String ReplyForm(){
        int did=d.id;
        User u=(User)Main.getSession().getAttribute("user");
        if(!d.reply){
            return HTML.panel("回复","该帖子已经被设置为禁止回复",null,"primary");
        }
        if(u==null){
            return HTML.panel("回复","要回复，请先"+HTML.a("Login.jsp","登录"),null,"primary");
        }
        FormHTML form=new FormHTML();
        form.setAction("replydiscuss.action");
        text t1=new text("id","id");
        t1.setValue(did+"");
        t1.setDisabled();
        form.addForm(t1);
        textarea t2=new textarea("text", "text");
        t2.setValue("");
        t2.setPlaceholder("输入回复内容... "+(d.replyHidden?"本帖已经设置为回复仅管理员和自己可见，可放心回复~":""));
        form.addForm(t2);
        form.setCol(0,12);
        return HTML.panel("回复",HTML.col(12,form.toHTML()),null,"primary");
    }
    public String Discuss(){
        String title=d.title;
        String s="";
        User lz = Main.users.getUser(d.username);
        if(d.showtime||d.showauthor) s+="bulid";
        if(d.showauthor) s+=" by "+lz.getUsernameHTML();
        if(d.showtime) s+=" at "+d.time.toString().substring(0,16);
        User u=Main.loginUser();
//        System.out.print(u);
        Permission p;
        if(u!=null){
//            System.out.print(u.getUsername());
            p=Main.getPermission(u.getUsername());
        }else{
            p=new Permission();
        }
        if(u!=null&&d.username.equals(u.getUsername())){
            modal m=new modal("adddiscuss","追加",addDiscussForm(d.id),"追加");
            m.setAction("discussappend.action");
            m.setBtnCls("link btn-xs");
//            modal(String id,String title,String body,String btnlabel){
            s+=m.toHTML();
        }
        if(p.getAddDiscuss()) s+=HTML.a("admin.jsp?page=AddDiscuss&id="+d.id," <span class='badge'>Edit</span>");

        title+=HTML.floatRight(s);

        String body;
        if(d.showauthor){
            String l="";
            l+= HTML.div("", HTML.headImg(lz.getUsername(), 1));
            l+=HTML.center(HTML.a("UserInfo.jsp?user=" + lz.getUsername(), lz.getNick()));
            l+=HTML.center("Rating:"+lz.getRatingHTML());
            String r=d.text;

            TableHTML t=new TableHTML();
            t.addColname("","");
            t.addCl(0,-1,"hidden");
            t.addRow(l,r);
            t.addCl(1,0,"discussleft");
            t.addCl(1,1,"discussright");
            body=t.HTML();

            //body=HTML.row(HTML.div("discussleft","", l) + HTML.div("discussright", "",r));
        }else{
            body=d.text;
        }
        return HTML.panelnobody(title, Discuss.PanelClass[d.panelclass],body,"style='word-break:break-all'");
        /*if(d.panelnobody){
            return HTML.panelnobody(title, Discuss.PanelClass[d.panelclass],body,"style='word-break:break-all'");
        }else{
            return HTML.panel(title, body, null, Discuss.PanelClass[d.panelclass], "style='word-break:break-all'");
        }*/
    }
}
