package Tool.HTML.contestListHTML;

import Main.Main;
import Main.User.Permission;
import Main.User.User;
import Main.contest.Contest;
import Tool.HTML.FromHTML.FormHTML;
import Tool.HTML.FromHTML.select.select;
import Tool.HTML.FromHTML.text.text;
import Tool.HTML.HTML;
import Tool.HTML.TableHTML.TableHTML;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syiml on 2015/6/11 0011.
 */

/*
* 赛选条件：状态、名字、type
* */
public class contestListHTML {
    //筛选条件
    private int num;//每页个数
    private int page;//第几页
    private int status;//状态
    private String name;//包含
    private int type;//类型
    private int kind;
    List<Contest> list;//显示列表
    public contestListHTML(int num, int page){
        this.num=num;
        this.page=page;
        this.status=-1;
        this.name=null;
        this.type=-1;
        this.kind=-1;
        list=null;
    }
    public void setStatu(int status){this.status=status;}
    public void setName(String name){this.name=name;}
    public void setType(int type){this.type=type;}
    public void setList(List<Contest> list){
        this.list=list;
    }
    public void setKind(int kind){this.kind=kind;}
    public String table(int kind){
        TableHTML table=new TableHTML();
        table.setClass("table table-striped table-hover");
        table.addColname("#");
        table.addColname("名称");
        table.addColname("开始时间");
        table.addColname("结束时间");
        table.addColname("权限");
        table.addColname("状态");
        if(kind==-1) table.addColname("类型");
        String s="";
        int size;
        if(havenext()){
            size=list.size()-1;
        }else size=list.size();
        for(int i=0;i<size;i++){
            table.addRow(contestToRow(list.get(i),kind==-1));
        }
        return table.HTML();
    }
    public String HTML(){
        User u=(User)Main.getSession().getAttribute("user");
        Permission p;
        if(u==null){
            p=new Permission();
        }else{
            p=Main.getPermission(u.getUsername());
        }
        if(list==null) list=Main.contests.getContests(page*num,num+1,status,name,type,kind);
        String sss=HTML.floatLeft(pageHTML())+HTML.floatRight(HTML.div("right-block",formHTML()));
        String head="比赛列表";
        if(kind==-1){
            head="比赛列表";
        }else if(kind==0){
            head=HTML.textb("比赛列表 - 练习","green");
        }else if(kind==1){
            head=HTML.textb("比赛列表 - 积分","blue");
        }else if(kind==2){
            head=HTML.textb("比赛列表 - 趣味","");
        }else if(kind==3){
            head=HTML.textb("比赛列表 - 正式","orange");
        }
        if(p.getAddContest()){
            head+=HTML.floatRight(HTML.a("admin.jsp?page=AddContest","New"));
        }
        return HTML.panelnobody(head,HTML.div("panel-body","style='padding:5px'",sss) + table(kind));
    }
    private List<String> contestToRow(Contest c,boolean showkind){
        List<String> l=new ArrayList<String>();
        l.add(c.getCid() + "");
        String name=HTML.a("Contest.jsp?cid=" + c.getCid(), c.getName());
        if(c.getType()==3||c.getType()==4) name+=HTML.floatRight("["+HTML.a("User.jsp?cid="+c.getCid(),"报名")+"]");
        l.add(name);
        l.add(c.getBeginTimeString());
        l.add(c.getEndTimeString());
        l.add(c.getTypeHTML());
        l.add(c.getStatuHTML());
        if(showkind){
            int k=c.getKind();
            if(k==0){
                l.add(HTML.textb("练习","green"));
            }else if(k==1){
                l.add(HTML.textb("积分","blue"));
            }else if(k==2){
                l.add(HTML.textb("趣味",""));
            }else if(k==3){
                l.add(HTML.textb("正式","orange"));
            }
        }
        return l;
    }
    private String formHTML(){
        FormHTML f = new FormHTML();
        f.setType(1);
        text f1=new text("name","名称");
        f1.setId("name");
        f1.setType(1);
        f1.setValue(name);
        f.addForm(f1);

        select f3=new select("type","权限");
        f3.setId("type");
        f3.setType(1);
        f3.add(-1,"全部");
        for(int i=0;i<5;i++){
            f3.add(i,Contest.getTypeText(i),Contest.getTypeStyle(i));
        }
        f3.setValue(type+"");
        f.addForm(f3);

        select f2=new select("statu","状态");
        f2.setId("statu");
        f2.setType(1);
        f2.add(-1, "全部");
        f2.add(0,"未开始");
        f2.add(1,"进行中");
        f2.add(2,"已结束");
        f2.setValue(status+"");
        f.addForm(f2);

        text ki=new text("kind","kind");
        ki.setValue(kind+"");
        ki.setDisabled();
        f.addForm(ki);
        f.setSubmitText("筛选");
        return f.toHTML();
    }
    boolean havenext(){
        return list.size()==num+1;
    }
    private String pageHTML(){
        String url="Contests.jsp" +
                "?statu="+status;
        if(name!=null) url+="&name="+name ;
        url+="&type="+type;
        url+="&kind="+kind;
        String preurl;
        if(page==0){
            preurl=null;
        }else{
            preurl=url+"&page="+(page-1);
        }
        String nexturl;
        if(havenext()){
            nexturl=url+"&page="+(page+1);
        }else {
            nexturl = null;
        }
        String s="<div class='btn-toolbar' role='toolbar'>"+HTML.btngroup(HTML.abtn("sm",url,"首页",""));
        s+=HTML.btngroup(
                HTML.abtn("sm",preurl,"上一页",preurl==null?"disabled":"")+
                HTML.abtn("sm",null,page+1+"","")+
                HTML.abtn("sm",nexturl,"下一页",nexturl==null?"disabled":"")
        );
        return s+"</div>";
    }
}
