package util.HTML;

import util.Main;
import entity.Permission;
import entity.User;
import entity.Contest;
import util.HTML.FromHTML.FormHTML;
import util.HTML.FromHTML.select.select;
import util.HTML.FromHTML.text.text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syiml on 2015/6/11 0011.
 */

/*
* 赛选条件：状态、名字、type
* */
public class contestListHTML extends pageBean{
    //筛选条件
    private int num;//每页个数
    private int page;//第几页
    private int status;//状态
    private String name;//包含
    private int type;//类型
    private int kind;
    List<Contest> list;//显示列表
    private int pageNum=0;
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

    public String getTableClass(){return "table table-striped table-hover";}
    @Override
    public String getTitle() {
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
        Permission p;
        User u=(User)Main.getSession().getAttribute("user");
        if(u==null){
            p=new Permission();
        }else{
            p=Main.getPermission(u.getUsername());
        }
        if(p.getAddContest()){
            head+=HTML.floatRight(HTML.a("admin.jsp?page=AddContest","New"));
        }
        return head;
    }

    @Override
    public int getPageSize() {
        return list.size();
    }

    @Override
    public int getPageNum() {
        return pageNum;
    }

    @Override
    public int getNowPage() {
        return page;
    }

    @Override
    public String getCellByHead(int i, String colname) {
        Contest c=list.get(i);
        if(colname.equals("#")){
            return c.getCid()+"";
        }else if(colname.equals("名称")){
            String name=HTML.a("Contest.jsp?cid=" + c.getCid(), c.getName());
            if(c.getType()==3||c.getType()==4) name+=HTML.floatRight("["+HTML.a("User.jsp?cid="+c.getCid(),"报名")+"]");
            return name;
        }else if(colname.equals("开始时间")){
            return c.getBeginTimeString();
        }else if(colname.equals("结束时间")){
            return c.getEndTimeString();
        }else if(colname.equals("权限")){
            return c.getTypeHTML();
        }else if(colname.equals("状态")){
            return c.getStatuHTML();
        }else if(colname.equals("类型")){
            int k=c.getKind();
            if(k==0){
                return HTML.textb("练习","green");
            }else if(k==1){
                return HTML.textb("积分","blue");
            }else if(k==2){
                return HTML.textb("趣味","");
            }else if(k==3){
                return HTML.textb("正式","orange");
            }
        }
        return "=ERROR=";
    }

    @Override
    public String getLinkByPage(int page) {
        String url="Contests.jsp?statu="+status+"&type="+type+"&kind="+kind;
        if(name!=null) url+="&name="+name ;
        url+="&page="+(page);
        return url;
    }

    @Override
    public String rightForm() {
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

    public String HTML(){
        if(list==null) list=Main.contests.getContests((page-1)*num,num,status,name,type,kind);
        pageNum=getPageNum(Main.contests.getContestsNum(status,name,type,kind),num);
        addTableHead("#","名称","开始时间","结束时间","权限","状态");
        if(kind==-1) addTableHead("类型");
        return super.HTML();
    }
}
