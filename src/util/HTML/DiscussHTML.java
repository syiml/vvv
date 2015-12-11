package util.HTML;

import util.Main;
import entity.Permission;
import entity.User;
import dao.DiscussSQL;
import entity.Discuss;
import entity.DiscussReply;
import util.HTML.FromHTML.FormHTML;
import util.HTML.FromHTML.check.check;
import util.HTML.FromHTML.select.select;
import util.HTML.FromHTML.text.text;
import util.HTML.FromHTML.textarea.textarea;
import util.HTML.modal.modal;

import java.util.List;

/**
 * Created by Syiml on 2015/7/3 0003.
 */
public class DiscussHTML {
    Discuss d;
    User loginuser;
    public static String IndexDiscuss(Discuss d){
        String title= d.getTitle();
        String s="";
        if(d.isShowtime() || d.isShowauthor()){
            s+="bulid";
            if(d.isShowauthor()) s+=" by "+ Main.users.getUser(d.getUsername()).getUsernameHTML();
            if(d.isShowtime()) s+=" at "+ d.getTime().toString().substring(0,16);
        }
        s+=" "+HTML.a("Discuss.jsp?id="+ d.getId(),"<span class='badge'>→</span>");
        title+=HTML.floatRight(s);
        String body;
        if(d.getShownum() ==-1|| d.getText().length()<= d.getShownum()){
            body= d.getText();
        }else{
            body= d.getText().substring(0, d.getShownum())+" ..."+HTML.a("Discuss.jsp?id="+ d.getId(),"show all");
        }
        if(d.isPanelnobody()){
            return HTML.panelnobody(title, Discuss.getPanelClass()[d.getPanelclass()],body,"style='word-break:break-all'");
        }else{
            return HTML.panel(title,body,null, Discuss.getPanelClass()[d.getPanelclass()],"style='word-break:break-all'");
        }
    }
    public static String IndexDiscuss(){
        String s="";
        List<Discuss> list= DiscussSQL.getDiscussTOP(false);
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
            if(d.d.isVisiable() ||(d.loginuser!=null&&d.loginuser.getPermission().getAddDiscuss()))
                return d.Discuss()+d.DiscussReply(paInt)+d.ReplyForm();
            else return HTML.panel("ERROR","没有权限",null,"danger");
        }catch(NumberFormatException e){
            return HTML.panel("ERROR", "参数错误", null, "danger");
        }
    }
    public static String DiscussList(int cid,int num,int pa,String seach,String user){
        DiscussListHTML d=new DiscussListHTML(cid,num,pa,seach,user);
        return d.HTML();
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

        text fcid=new text("cid","cid");
        fcid.setValue("-1");
        form.addForm(fcid);

        text f1=new text("title","标题");
        if(d!=null) f1.setValue(d.getTitle());
        form.addForm(f1);

        text f2=new text("priority","优先级");
        f2.setPlaceholder("默认等于id，接收浮点数");
        if(d!=null) f2.setValue(d.getPriority() +"");
        form.addForm(f2);

        select f6=new select("panelclass","样式");
        for(int i = 0; i< Discuss.getPanelClass().length; i++) {
            f6.add(i, Discuss.getPanelClass()[i]);
        }
        if(d!=null) f6.setValue(d.getPanelclass() +"");
        form.addForm(f6);

        text f10=new text("shownum","首页显示字数");
        if(d!=null) f10.setValue(d.getShownum() +"");
        else f10.setValue("-1");
        form.addForm(f10);

        check f3=new check("top","在首页显示");
        if(d!=null&& d.isTop()) f3.setChecked();
        form.addForm(f3);

        check f4=new check("visiable","可见");
        if(d!=null){if(d.isVisiable()) f4.setChecked();}
        else f4.setChecked();
        form.addForm(f4);

        check f5=new check("reply","可以回复");
        if(d!=null){if(d.isReply()) f5.setChecked();}
        else f5.setChecked();
        form.addForm(f5);

        check f7=new check("panelnobody","panelnobody");
        if(d!=null&& d.isPanelnobody()) f7.setChecked();
        form.addForm(f7);

        check f8=new check("showauthor","显示作者");
        if(d!=null){if(d.isShowauthor()) f8.setChecked();}
        else f8.setChecked();
        form.addForm(f8);

        check f9=new check("showtime","显示时间");
        if(d!=null){
            if(d.isShowtime()) f9.setChecked();
        }else f9.setChecked();
        form.addForm(f9);

        check f12=new check("replyhidden","回复默认隐藏");
        if(d!=null){
            if(d.isReplyHidden()) f12.setChecked();
        }
        form.addForm(f12);

        textarea f11 = new textarea("text","text");
        if(d!=null) f11.setValue(d.getText());
        else f11.setValue("");
        f11.setUEditor(true);
        f11.setId("container");
        f11.setPlaceholder("允许输入HTML代码");
        form.addForm(f11);

        form.setCol(2,10);
        return form.toHTML();
    }
    public String page(int page){
        int pagenum=(DiscussSQL.getNewReplyId(d.getId())-2)/Main.discussShowNum+1;
        if(pagenum==1) return "";
        String size="sm";
        String s ="<div class='btn-toolbar' role='toolbar'>";
        s+="<div class='btn-group' role='group'>";
        for(int i=0;i<pagenum;i++){s+=HTML.abtn(size,"Discuss.jsp?id="+ d.getId() +"&page="+(i),i+1+"",page==i?"btn-primary":"");}
        s+="</div></div>";
        return HTML.div("","style='margin-bottom: 15px;'",s);
    }
    public String DiscussReply(int page){
        int num=Main.discussShowNum;
        int did= d.getId();
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
        if(r.getAdminreplay() !=null) t.setValue(r.getAdminreplay());
        text t1=new text("id","text");
        t1.setDisabled();
        t1.setValue(r.getDid() +"");
        text t2=new text("rid","text");
        t2.setValue(r.getRid() +"");
        t2.setDisabled();
        modal m=new modal("reply-"+ r.getRid(),"回复",t.toHTML(2,10)+t1.toHTML()+t2.toHTML(),"回复");
        m.setBtnCls("link btn-xs");
        m.setAction("adminReplay");
        return m.toHTML();
    }
    public String EveryDiscussReply(DiscussReply r){
        boolean admin=false;
        if(loginuser!=null){
            admin=loginuser.getPermission().getAddDiscuss();
        }
        User u=Main.users.getUser(r.getUsername());
        //if(!r.visiable&&(loginuser==null||(loginuser!=null&&(!loginuser.getUsername().equals(u.getUsername())||!admin)))){
        if(!r.isVisiable()){
            if(loginuser==null) return "";
            else if(!loginuser.getUsername().equals(u.getUsername())){
                if(!admin){
                    return "";
                }
            }
        }
        if(!r.isVisiable()) r.setPanelclass(4);
        String title="<span class='badge'>"+ r.getRid() +"</span> reply by "+u.getUsernameHTML()+" at "+ r.getTime().toString().substring(0,16);
        String right="";
        if(!r.isVisiable()) right="该条已经被隐藏 ";
        if(admin){
            if(!r.isVisiable()) right+=HTML.a("showhide.action?id="+ r.getDid() +"&rid="+ r.getRid(),"<span class='badge'>Show</span>");
            else right+=HTML.a("showhide.action?id="+ r.getDid() +"&rid="+ r.getRid(),"<span class='badge'>Hide</span>");
        }
        title+=HTML.floatRight(right);

        if(admin) title+=Reply(r);
        String l="";
        l+= HTML.div("", HTML.headImg(u.getUsername(), 1));
        l+=HTML.center(HTML.a("UserInfo.jsp?user="+u.getUsername(), u.getNick()));
        l+=HTML.center("Rating:"+u.getRatingHTML());

        String footer=null;
        if(r.getAdminreplay() !=null){
            footer=HTML.textb("管理员回复: ","black")+ r.getAdminreplay();
        }

        TableHTML table=new TableHTML();
        table.addColname("","");
        table.addCl(0, -1, "hidden");
        table.addRow(l, HTML.pre(HTML.replaceAt(r.getText()))+(footer!=null?HTML.div("adminreply",footer):""));
        table.addCl(1, 1, "discussright");
        table.addCl(1,0,"discussleft");
        String body=table.HTML();


        return HTML.panel(title, body,null, Discuss.getPanelClass()[r.getPanelclass()],false);
    }
    public String ReplyForm(){
        int did= d.getId();
        User u=(User)Main.getSession().getAttribute("user");
        if(!d.isReply()){
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
        t2.setUEditor(true);
        t2.setId("replyDiscuss");
        t2.setPlaceholder("输入回复内容... "+(d.isReplyHidden() ?"本帖已经设置为回复仅管理员和自己可见，可放心回复~":""));
        form.addForm(t2);
        form.setCol(0,12);
        return HTML.panel("回复",HTML.col(12,form.toHTML()),null,"primary");
    }

    public static String addDiscussForm(int did,int cid){

        text f0=new text("id","id");
        f0.setDisabled();
        f0.setValue(did + "");
        text fcid=new text("cid","cid");
        fcid.setDisabled();
        fcid.setValue(cid+"");

        text f1=null;
        if(did==-1){
            f1=new text("title","标题");
            f1.setId("title");
        }

        textarea f11 = new textarea("text","text");
//        if(d!=null) f11.setValue(HTML.HTMLtoString(d.text));
//        else
        f11.setUEditor(true);
        f11.setId("addDiscuss");
        f11.setValue("");
        f11.setPlaceholder("这里输入正文");
        return HTML.row(fcid.toHTML()+f0.toHTML())+(f1!=null?f1.toHTML(2, 10):"")+HTML.row(HTML.col(12,f11.toHTML()));
    }

    public String Discuss(){
        String title= d.getTitle();
        String s="";
        User lz = Main.users.getUser(d.getUsername());
        if(d.isShowtime() || d.isShowauthor()) s+="bulid";
        if(d.isShowauthor()) s+=" by "+lz.getUsernameHTML();
        if(d.isShowtime()) s+=" at "+ d.getTime().toString().substring(0,16);
        User u=Main.loginUser();
//        System.out.print(u);
        Permission p;
        if(u!=null){
//            System.out.print(u.getUsername());
            p=Main.getPermission(u.getUsername());
        }else{
            p=new Permission();
        }
        if(u!=null&& d.getUsername().equals(u.getUsername())){
            modal m=new modal("adddiscuss","追加",addDiscussForm(d.getId(),-1),"追加");
            m.setAction("discussappend.action");
            m.setBtnCls("link btn-xs");
//            modal(String id,String title,String body,String btnlabel){
            s+=m.toHTML();
        }
        if(p.getAddDiscuss()) s+=HTML.a("admin.jsp?page=AddDiscuss&id="+ d.getId()," <span class='badge'>Edit</span>");

        title+=HTML.floatRight(s);

        String body;
        if(d.isShowauthor()){
            String l="";
            l+= HTML.div("", HTML.headImg(lz.getUsername(), 1));
            l+=HTML.center(HTML.a("UserInfo.jsp?user=" + lz.getUsername(), lz.getNick()));
            l+=HTML.center("Rating:"+lz.getRatingHTML());
            String r=HTML.replaceAt(d.getText());

            TableHTML t=new TableHTML();
            t.addColname("","");
            t.addCl(0,-1,"hidden");
            t.addRow(l,r);
            t.addCl(1,0,"discussleft");
            t.addCl(1,1,"discussright");
            body=t.HTML();

            //body=HTML.row(HTML.div("discussleft","", l) + HTML.div("discussright", "",r));
        }else{
            body= d.getText();
        }
        return HTML.panelnobody(title, Discuss.getPanelClass()[d.getPanelclass()],body,"style='word-break:break-all'");
        /*if(d.panelnobody){
            return HTML.panelnobody(title, Discuss.PanelClass[d.panelclass],body,"style='word-break:break-all'");
        }else{
            return HTML.panel(title, body, null, Discuss.PanelClass[d.panelclass], "style='word-break:break-all'");
        }*/
    }
}
