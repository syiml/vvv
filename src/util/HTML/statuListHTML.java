package util.HTML;

import entity.*;
import entity.Enmu.UserStarType;
import servise.ContestMain;
import servise.UserService;
import util.HTML.FromHTML.hidden.hidden;
import util.Main;
import util.HTML.FromHTML.FormHTML;
import util.HTML.FromHTML.select.select;
import util.HTML.FromHTML.text.text;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2015/6/8.
 */
public class statuListHTML extends pageBean {
    User user;
    int cid;
    int num;
    int page;
    String teamUser;

    //筛选信息
    int pid;
    int result;
    int Language;
    String ssuser;//筛选的用户
    boolean incontest;
    boolean all;
    boolean star;
    List<Status> status;
    int PageNum;
    Contest contest = null;
    public statuListHTML(int cid,int num,int page,
                         int pid,int result,int Language,String ssuser,boolean all,boolean star){
        if(cid != -1) star = false;
        this.star = star;
        this.cid=cid;
        this.num=num;
        this.page=page;
        this.pid=pid;
        this.result=result;
        this.Language=Language;
        this.ssuser=ssuser;
        this.incontest=(cid>0);
        this.all=all;
        if(this.ssuser==null) this.ssuser="";
        if(cid>0){
            contest = ContestMain.getContest(cid);
            if(contest!=null&&contest.getType() == Contest_Type.TEAM_OFFICIAL){
                this.user = Main.users.getUser((String)Main.getSession().getAttribute("trueusername"+cid));
                this.teamUser = (String)Main.getSession().getAttribute("contestusername"+cid);
            }else{
                this.user=Main.loginUser();
            }
        }else {
            this.user = Main.loginUser();
        }
        setCl("table table-striped table-hover table-condensed");
        if(this.user == null) this.star = false;
        if(this.star){
            status = UserService.userStarSQL.getStarStatus(user.getUsername(),this.num * (this.page - 1), this.num);
            this.PageNum = getTotalPageNum(UserService.userStarSQL.getStarStatusNum(user.getUsername()),num);
            addTableHead("#"/*, "用户"*/, "昵称", "题目", "评测结果", "语言", "耗时", "使用内存", "代码长", "提交时间","收藏备注");
            return;
        }
        if(contest!=null&&contest.getType()==Contest_Type.TEAM_OFFICIAL) {
            status = Main.status.getTeamStatus(cid,this.num * (this.page - 1), this.num, this.pid, this.result, this.Language, this.ssuser);
            this.PageNum= getTotalPageNum(Main.status.getTeamStatusNum(cid, this.pid, this.result, this.Language, this.ssuser),num);
        }else if(contest!=null && contest.isStatusReadOut()){//读取比赛外的status
            status = Main.status.getStatusKind0(this.cid, this.num * (this.page - 1), this.num, this.pid, this.result, this.Language, this.ssuser);
            this.PageNum = getTotalPageNum(Main.status.getStatusKind0Num(this.cid, this.pid, this.result, this.Language, this.ssuser),num);
        }else{
            status = Main.status.getStatus(this.cid, this.num * (this.page - 1), this.num, this.pid, this.result, this.Language, this.ssuser, all);
            this.PageNum= getTotalPageNum(Main.status.getStatusNum(this.cid, this.pid, this.result, this.Language, this.ssuser, all), num);
        }

        addTableHead("#"/*, "用户"*/, "昵称", "题目", "评测结果", "语言", "耗时", "使用内存", "代码长", "提交时间");

    }

    @Override
    public String getTitle() {
        return "评测列表"+(cid==-1?(star?HTML.a("Status.jsp","【全部评测】"):HTML.a("Status.jsp?star=on","【我收藏的评测】")):"");
    }

    @Override
    public int getCurrPageSize() {
        return status.size();
    }

    @Override
    public int getTotalPageNum() {
        return PageNum;
    }

    @Override
    public int getCurrPage() {
        return page;
    }

    private String scoreHTML(int score){
        if(score == -1) return "";
        if(score==100) return HTML.textb(" "+score,"#27c24c");
        else if(score>=60) return HTML.textb(" "+score,"#ff8520");
        else return HTML.textb(" "+score,"#f05050");
    }
    public static boolean canViewCEInfo(Status s,User loginUser){
        if(loginUser == null) return false;
        if(loginUser.getPermission().getViewCode()) return true;
        if(s.getUser().equals(loginUser.getUsername())){
            if(s.getResult() == Result.CE || s.getResult() == Result.ERROR || s.getScore()!=-1) return true;
            if(Main.users.haveDownloadData(s.getUser(),s.getPid())) return true;
        }else{
            if(s.getResult() == Result.CE && Main.users.haveViewCode(loginUser.getUsername(),s.getPid())) return true;
        }
        return false;
    }
    @Override
    public String getCellByHead(int i, String colname) {
        Status s=status.get(i);
        if(colname.equals("#")){
            if(contest!=null && contest.getType()==Contest_Type.TEAM_OFFICIAL){
                if(s.getUser().equals(teamUser)){
                    addClass(i + 1, -1, "info");
                }
            }else {
                if (user!=null && s.getUser().equals(user.getUsername())) {
                    addClass(i + 1, -1, "info");
                }
            }
            return s.getRid()+"";
        }else if(colname.equals("用户")){
            return userToHtml(s);
        }else if(colname.equals("昵称")){
            if(contest==null||contest.getType()!=Contest_Type.TEAM_OFFICIAL){
                User u=Main.users.getUser(s.getUser());
                if(!incontest) return u.getTitleAndNick();
                else return HTML.a("#R"+s.getUser(),u.getTitleAndNickNoA());
            }else{
                return HTML.a("#R"+s.getUser(),s.getUser());
            }
        }else if(colname.equals("题目")){
            return ""+pidToHtml(s,incontest);
        }else if(colname.equals("评测结果")){
            if(canViewCEInfo(s,user)){
                if(incontest){
                    return HTML.a("javascript:ceinfo(" + s.getRid() + ");", s.resultToHTML(s.getResult())) + scoreHTML(s.getScore());
                }else{
                    return HTML.a("CEinfo.jsp?rid=" + s.getRid(), s.resultToHTML(s.getResult())) + scoreHTML(s.getScore());
                }
            }else{
                return ""+s.resultToHTML(s.getResult())+ scoreHTML(s.getScore());
            }
        }else if(colname.equals("语言")){
            return LanguageToHtml(s);
        }else if(colname.equals("耗时")){
            if(contest!=null && contest.getType() == Contest_Type.TEAM_OFFICIAL){
                if(s.getUser().equals(teamUser) || (user!=null&&user.getPermission().getViewCode())){
                    return s.getTimeUsed()+"";
                }
                return "-";
            }
            return s.getTimeUsed();
        }else if(colname.equals("使用内存")){
            if(contest!=null && contest.getType() == Contest_Type.TEAM_OFFICIAL){
                if(s.getUser().equals(teamUser) ||  (user!=null&&user.getPermission().getViewCode())){
                    return s.getMemoryUsed()+"";
                }
                return "-";
            }
            return s.getMemoryUsed();
        }else if(colname.equals("代码长")){
            if(contest!=null && contest.getType() == Contest_Type.TEAM_OFFICIAL){
                if(s.getUser().equals(teamUser) ||  (user!=null&&user.getPermission().getViewCode())){
                    return s.getCodelen()+"";
                }
                return "-";
            }
            return s.getCodelen()+"";
        }else if(colname.equals("提交时间")){
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(s.getSbmitTime());
        }else if(colname.equals("收藏备注")){
            if(user == null) return "";
            UserStar userStar = UserService.userStarSQL.getBeanByKey(user.getUsername()).getStar(UserStarType.STATUS,s.getRid());
            if(userStar == null) return "";
            return userStar.getText();
        }
        return "error";
    }

    @Override
    public String getLinkByPage(int page) {
        if(star) return "Status.jsp?page="+page+"&star=on";
        if(cid==-1){
            return "Status.jsp?page="+(page)+"&pid="+pid+"&result="+result+"&lang="+Language+"&user="+ssuser+(all?"&all=1":"");
        }else{
            //return "Contest.jsp?cid="+cid+"&page="+(page+1)+"&pid="+pid+"&result="+result+"&lang="+Language+"&user="+ssuser+"#Status";
            return "javascript:topage("+page+");";
        }
    }

    @Override
    public String rightForm(){
        if(star) return "";
        FormHTML f=new FormHTML();
        if(cid==-1) f.setAction("Status.jsp");
        else{
//            f.setAction("Contest.jsp");
            f.setScript("javascript:seachStatus();");
        }
        f.setType(1);
        text f1=new text("user","用户");
        f1.setCss("width:120px");
        f1.setType(1);
        if(ssuser!=null) f1.setValue(ssuser);
        f1.setId("user");
        f.addForm(f1);
        //////////////////////////
        if(cid==-1){
            text f2=new text("pid","题号");
            f2.setType(1);
            if(pid!=-1)f2.setValue(pid+"");
            f2.setId("pid");
            f2.setCss("width:70px");
            f.addForm(f2);
        }else{
            select f2=new select("pid","pid");
            f2.setType(1);
            int size= ContestMain.getContest(cid).getProblemNum();
            f2.add(-1,"All");
            if(size>26){
                for(int i=0;i<size;i++){
                    f2.add(i,(i+1)+"");
                }
            }else{
                for(int i=0;i<size;i++){
                    f2.add(i,(char)(i+'A')+"");
                }
            }
            f2.setValue(pid + "");
            f2.setId("pid");
            f.addForm(f2);
        }
        //////////////////
        select f3=new select("result","结果");
        f3.setType(1);
        f3.setId("result");
        f3.add(-1, "All");
        f3.add(1,"AC");
        f3.add(2,"WA");
        f3.add(3,"CE");
        f3.add(4,"RE");
        f3.add(5,"TLE");
        f3.add(6,"MLE");
        f3.add(7,"OLE");
        f3.add(8,"PE");
        f3.add(0, "PD");
        f3.add(11,"SE");
        f3.add(13,"JDG");
        f3.setValue(result+"");
        f.addForm(f3);
        ///////////////////
        select f4=new select("lang","语言");
        f4.setType(1);
        f4.add(-1, "All");
        f4.add(0,"G++");
        f4.add(1,"GCC");
        f4.add(2,"JAVA");
        f4.add(3,"Python");
        f4.setId("lang");
        f4.setValue(Language+"");
        f.addForm(f4);
        //////////////////
        if(cid!=-1){
            text f5= new text("cid","cid");
            f5.setValue(cid+"");
            f5.setDisabled();
            f.addForm(f5);
            text f6= new text("main","main");
            f6.setValue("Status");
            f6.setDisabled();
            f.addForm(f6);
        }
        /////////////////
        if(all){
            text f7=new text("all","all");
            f7.setValue("1");
            f7.setDisabled();
            f.addForm(f7);
        }
        f.setSubmitText("筛选");
        return f.toHTML();
    }
    private String LanguageToHtml(Status s){
        int l=s.getLanguage();
        int rid=s.getRid();
        if(contest!=null && contest.getType() == Contest_Type.TEAM_OFFICIAL){
            if(s.getUser().equals(teamUser)|| (user!=null&&user.getPermission().getViewCode())){
                if(l==0)return HTML.a("javascript:viewcode("+rid+")","C++");
                if(l==1)return HTML.a("javascript:viewcode("+rid+")","C");
                if(l==2)return HTML.a("javascript:viewcode("+rid+")","JAVA");
                if(l==3)return HTML.a("javascript:viewcode("+rid+")","Python");
                return HTML.a("javascript:viewcode("+rid+")","UNKNOW");
            }else{
                return "-";
            }
        }else if(Main.canViewCode(s, user)){
            if(!incontest){
                if(l==0)return HTML.a("ViewCode.jsp?rid="+rid,"C++");
                if(l==1)return HTML.a("ViewCode.jsp?rid="+rid,"C");
                if(l==2)return HTML.a("ViewCode.jsp?rid="+rid,"JAVA");
                if(l==3)return HTML.a("ViewCode.jsp?rid="+rid,"Python");
                return HTML.a("ViewCode.jsp?rid="+rid,"UNKNOW");
            }else{
                if(l==0)return HTML.a("javascript:viewcode("+rid+")","C++");
                if(l==1)return HTML.a("javascript:viewcode("+rid+")","C");
                if(l==2)return HTML.a("javascript:viewcode("+rid+")","JAVA");
                if(l==3)return HTML.a("javascript:viewcode("+rid+")","Python");
                return HTML.a("javascript:viewcode("+rid+")","UNKNOW");
            }
        }
        if(l==0)return "C++";
        if(l==1)return "C";
        if(l==2)return "JAVA";
        if(l==3)return "Python";
        return "UNKNOW";
    }
    private String pidToHtml(Status s,boolean in){
        if(!in){
            return HTML.a("Problem.jsp?pid="+s.getPid(),""+s.getPid());
        }else{
            String ss=contest.getProblemId(s.getPid());
            s.setCid(cid);
            return HTML.a("#P"+s.getContestPid(),ss);
        }
    }
    private String userToHtml(Status s){
        if(contest==null||contest.getType()!=Contest_Type.TEAM_OFFICIAL){
            User u=Main.users.getUser(s.getUser());
            if(!incontest) return u.getUsernameHTML()/*+"("+u.getNick()+")"*/;
            else return HTML.a("#R"+s.getUser(),u.getUsernameHTMLNoA())/*+"("+u.getNick()+")"*/;
        }else{
            return HTML.a("#R"+s.getUser(),s.getUser());
        }
    }
}