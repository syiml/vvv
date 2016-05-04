package util.HTML.UserListHTML;

import entity.*;
import servise.ContestMain;
import util.Main;
import dao.UserSQL;
import util.HTML.FromHTML.FormHTML;
import util.HTML.FromHTML.hidden.hidden;
import util.HTML.FromHTML.select.select;
import util.HTML.FromHTML.text.text;
import util.HTML.FromHTML.text_in.text_in;
import util.HTML.HTML;
import util.HTML.pageBean;
import util.Tool;

import java.util.List;

/**
 * Created by Syiml on 2015/11/2 0002.
 */
public class UserListContest extends pageBean {
    Contest c;
    boolean admin=false;
    int NowPage=1;
    int PageNum=1;
    List<List<String>> list2;
    List<RegisterTeam> list_register_team;
    User u;
    int RegisterUserNum;
    public UserListContest(int cid,int NowPage){
        u=Main.loginUser();
        c= ContestMain.getContest(cid);
        admin=Main.loginUserPermission().getContestRegisterAdmin();
        this.NowPage=NowPage;
        int num=Main.userShowNum;
        if(c.getType() == Contest.TYPE_TEAM_OFFICIAL){
            super.addTableHead("用户名","队名","队员1","队员2","队员3","状态","时间");
            if(admin){
                super.addTableHead("账号","密码");
            }
            list_register_team = ContestMain.getRegisterTeamByCid(cid);
            RegisterUserNum = list_register_team.size();
            PageNum=1;
        }else {
            if (c.getKind() == 3) {
                super.addTableHead("用户名", "姓名", "性别", "学院", "专业", "班级", "学号", "昵称", "Rating", "状态", "时间");
            } else {
                super.addTableHead("用户名", "昵称", "Rating", "状态", "时间");
            }
            list2=Main.users.getUsers(cid, (NowPage - 1) * num, num, "", c.getKind() == 3);
            RegisterUserNum=UserSQL.getUsersNum(c.getCid(),"");
            PageNum=getPageNum(RegisterUserNum,Main.userShowNum);
        }
        if(admin){
            super.addTableHead("admin");
        }
    }

    public String getTableClass(){
        return "table table-striped table-hover table-condensed"+(c.getKind()==3?" table-bordered table-small":"");
    }

    @Override
    public String getTitle() {
        return c.getName()+" 报名列表";
    }

    @Override
    public int getPageSize() {
        if(c.getType() == Contest.TYPE_TEAM_OFFICIAL){
            return list_register_team.size();
        }
        return list2.size();
    }

    @Override
    public int getPageNum() {
        return PageNum;
    }

    @Override
    public int getNowPage() {
        return NowPage;
    }

    private String adminButtons(String username,String info){
        int cid=c.getCid();
        String s="";
        if(c.getType() == Contest.TYPE_TEAM_OFFICIAL){
            s+=HTML.a("RegisterTeam.jsp?cid="+cid+"&username="+username,"详细信息")+" ";
        }
        s+=HTML.a("setregistercontest.action?cid="+cid+"&username="+username+"&statu=0","等待")+" ";
        s+=HTML.a("setregistercontest.action?cid="+cid+"&username="+username+"&statu=4","通过")+" ";
        s+=HTML.a("setregistercontest.action?cid="+cid+"&username="+username+"&statu=-1","拒绝")+" ";
        s+=HTML.a("setregistercontest.action?cid="+cid+"&username="+username+"&statu=2","非正式")+" ";
        //String id,String title,String body,String btnlabel
        s+=HTML.a("javascript:retry('"+username+"','"+info+"','"+cid+"')","需修改")+" ";
        s+=HTML.a("setregistercontest.action?cid="+cid+"&username="+username+"&statu=1","已签到")+" ";
        s+=HTML.a("setregistercontest.action?cid="+cid+"&username="+username+"&statu=-2","删除");
        return s;
    }
    @Override
    public String getCellByHead(int i, String colname) {
        if(c.getType() == Contest.TYPE_TEAM_OFFICIAL){
            RegisterTeam rt = list_register_team.get(i);
            //"用户名","队名","队员1","队员2","队员3","状态","时间"
            if(colname.equals("用户名")){
                return rt.getUsername();
            }else if(colname.equals("队名")){
                return rt.teamName;
            }else if(colname.equals("队员1")) {
                TeamMember mb = rt.getMember(0);
                if(mb==null){
                    return "";
                }else{
                    return mb.getName();
                }
            }else if(colname.equals("队员2")){
                TeamMember mb = rt.getMember(1);
                if(mb==null){
                    return "";
                }else{
                    return mb.getName();
                }
            }else if(colname.equals("队员3")){
                TeamMember mb = rt.getMember(2);
                if(mb==null){
                    return "";
                }else{
                    return mb.getName();
                }
            }else if(colname.equals("状态")){
                if(rt.getStatu() == RegisterUser.STATUS_MUST_EDIT) {
                    return HTML.a("javascript:retry_show('" + rt.getUsername() +
                            "','" +
                            rt.getInfo() +
                            "','" +
                            c.getCid() +
                            "'," +
                            (Main.loginUser() != null && Main.loginUser().getUsername().equals(rt.getUsername())) +
                            ")"
                            , RegisterUser.statuToHTML(rt.getStatu()));
                }else{
                    return RegisterUser.statuToHTML(rt.getStatu());
                }
            }else if(colname.equals("时间")){
                return rt.getTime().toString().substring(0, 19);
            }else if(colname.equals("账号")){
                return rt.teamUserName==null?"":rt.teamUserName;
            }else if(colname.equals("密码")){
                return rt.teamPassword==null?"":rt.teamPassword;
            }else if(colname.equals("admin")) {
                return adminButtons(rt.getUsername(),rt.getInfo());
            }
            return "error";
        }else {
            List<String> aList=list2.get(i);
            int z = 0;
            if (c.getKind() == 3) z = 6;
            if (colname.equals("用户名")) {
                User u = Main.users.getUser(aList.get(0));
                if (u == null) {
                    return aList.get(0);
                } else {
                    return u.getUsernameHTML();
                }
            } else if (colname.equals("昵称")) {
                if (Main.users.getUser(aList.get(0)) == null) {
                    return HTML.textb("未注册", "red");
                }
                return aList.get(z + 1);
            } else if (colname.equals("Rating")) {
                return aList.get(z + 2);
            } else if (colname.equals("状态")) {
                return aList.get(z + 3);
            } else if (colname.equals("时间")) {
                return aList.get(z + 4);
            } else if (colname.equals("姓名")) {
                return aList.get(1);
            } else if (colname.equals("性别")) {
                if (aList.get(2).equals("1")) {
                    return "男";
                } else if (aList.get(2).equals("2")) {
                    addClass(i + 1, -1, "danger");
                    addClass(i + 1, 2, "danger");
                    return "女";
                } else {
                    return "";
                }
            } else if (colname.equals("学院")) {
                return aList.get(3);
            } else if (colname.equals("专业")) {
                return aList.get(4);
            } else if (colname.equals("班级")) {
                return aList.get(5);
            } else if (colname.equals("学号")) {
                return aList.get(6);
            } else if (colname.equals("admin")) {
                String info;
                if (c.getKind() == 3) {
                    info = aList.get(11);
                    aList.remove(11);
                } else {
                    info = aList.get(5);
                    aList.remove(5);
                }
                return adminButtons(aList.get(0), info);
            }
            return "error";
        }
    }
    @Override
    public String getLinkByPage(int page) {
        return "User.jsp?cid="+c.getCid()+"&page="+page;
    }

    @Override
    public String rightForm() {
        String r="";
        if(admin){
            FormHTML form=new FormHTML();
            form.setAction("setregistercontest.action");
            form.setType(1);
            text_in t=new text_in(r+" ");
            form.addForm(t);
            hidden t1=new hidden("cid",c.getCid()+"");
            form.addForm(t1);
            text t2=new text("username","添加用户");
            form.addForm(t2);
            select s1=new select("statu","状态");
            //0pending 1accepted -1no  2*
            s1.add(0,"等待");
            s1.add(4,"通过");
            s1.add(-1,"拒绝");
            s1.add(2,"非正式");
            s1.add(1,"已签到");
            s1.setValue("0");
            s1.setId("statu");
            s1.setType(1);

            form.addForm(s1);
            form.setSubmitText("提交");
            r=form.toHTML();
        }
        return r;
    }
    public String head(){
        String ss="";
        if(c.getType()==3||c.getType()==4||c.getType()==Contest.TYPE_TEAM_OFFICIAL){
            if(u!=null){
                RegisterUser z=ContestMain.getRegisterStatu(u.getUsername(),c.getCid());
                if(z==null){
                    if(c.getRegisterendtime().before(Tool.now())){
                        ss+="报名已经结束";
                    }else if(c.getRegisterstarttime().after(Tool.now())) {
                        ss+="报名还未开始";
                    }else{
                        if(c.getType()==Contest.TYPE_TEAM_OFFICIAL){
                            ss += HTML.a("RegisterTeam.jsp?cid=" + c.getCid(), "立即报名");
                        }else {
                            ss += HTML.a("registercontest.action?cid=" + c.getCid(), "立即报名");
                        }
                    }
                }else{
                    ss+="已报名，状态："+RegisterUser.statuToHTML(z.getStatu());
                    if(z.getStatu() == RegisterUser.STATUS_PADDING ||
                            z.getStatu() == RegisterUser.STATUS_MUST_EDIT){
                        ss+=" "+HTML.a("RegisterTeam.jsp?cid="+c.getCid(),"修改");
                    }
                }
            }else{
                if(c.getRegisterendtime().after(Tool.now())){
                    ss+="报名前先"+HTML.a("Login.jsp","登录");
                }else if(c.getRegisterstarttime().after(Tool.now())){
                    ss+="报名还未开始";
                }else{
                    ss+="报名已经结束";
                }
            }
        }
        String r="";
        if(c.getType()==3||c.getType()==4||c.getType()==Contest.TYPE_TEAM_OFFICIAL){
            r+="报名时间："+c.getRegisterstarttime().toString().substring(0,16)+
                    " ～ "+c.getRegisterendtime().toString().substring(0,16);
        }
        String count="总报名人数："+RegisterUserNum+"】【"+
                "审核通过人数："+ (UserSQL.getUsersNum(c.getCid(), RegisterUser.STATUS_ACCEPTED)+UserSQL.getUsersNum(c.getCid(), RegisterUser.STATUS_APPENDED));
        return HTML.div("panel-body","style='padding:5px'",HTML.floatLeft("【"+ss+"】【"+count+"】【"+r+"】"))+
               HTML.div("panel-body","style='padding:5px'",HTML.floatLeft(page())+HTML.floatRight(rightForm()));
    }
}
