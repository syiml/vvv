package util.HTML;

import servise.ContestMain;
import util.Main;
import entity.User;
import entity.Result;
import entity.statu;
import util.HTML.FromHTML.FormHTML;
import util.HTML.FromHTML.select.select;
import util.HTML.FromHTML.text.text;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2015/6/8.
 */
public class statuListHTML extends pageBean {
    String user;
    int cid;
    int num;
    int page;

    //筛选信息
    int pid;
    int result;
    int Language;
    String ssuser;//筛选的用户
    boolean incontest;
    boolean all;
    List<statu> status;
    int PageNum;
    public statuListHTML(String user,int cid,int num,int page,
                         int pid,int result,int Language,String ssuser,boolean all){
        this.user=user;
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
        status= Main.status.getStatus(this.cid, this.num * (this.page - 1), this.num, this.pid, this.result, this.Language, this.ssuser, all);
        this.PageNum=getPageNum(Main.status.getStatusNum(this.cid, this.pid, this.result, this.Language, this.ssuser, all),num);
        setCl("table table-striped table-hover table-condensed");
        addTableHead("#", "用户", "题目", "评测结果", "语言", "耗时", "使用内存", "代码长", "提交时间");
    }

    @Override
    public String getTitle() {
        return "评测列表";
    }

    @Override
    public int getPageSize() {
        return status.size();
    }

    @Override
    public int getPageNum() {
        return PageNum;
    }

    @Override
    public int getNowPage() {
        return page;
    }

    @Override
    public String getCellByHead(int i, String colname) {
        statu s=status.get(i);
        if(colname.equals("#")){
            return s.getRid()+"";
        }else if(colname.equals("用户")){
            if(s.getUser().equals(user)){
                addClass(i+1,-1,"info");
            }
            return userToHtml(s);
        }else if(colname.equals("题目")){
            return ""+pidToHtml(s,incontest);
        }else if(colname.equals("评测结果")){
            if(s.getResult()== Result.CE){
                if(incontest){
                    return HTML.a("javascript:ceinfo(" + s.getRid() + ");", HTML.span("warning", "Compilation Error"));
                }else{
                    return HTML.a("CEinfo.jsp?rid=" + s.getRid(), HTML.span("warning", "Compilation Error"));
                }
            }else if(s.getResult() == Result.ERROR) {
                if(incontest){
                    return HTML.a("javascript:ceinfo(" + s.getRid() + ");", HTML.span("info","Submit Error"));
                }else{
                    return HTML.a("CEinfo.jsp?rid=" + s.getRid(), HTML.span("info","Submit Error"));
                }
            }else{
                return ""+s.resultToHTML(s.getResult());
            }
        }else if(colname.equals("语言")){
            return LanguageToHtml(s);
        }else if(colname.equals("耗时")){
            return s.getTimeUsed();
        }else if(colname.equals("使用内存")){
            return s.getMemoryUsed();
        }else if(colname.equals("代码长")){
            return s.getCodelen()+"";
        }else if(colname.equals("提交时间")){
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(s.getSbmitTime());
        }
        return "error";
    }

    @Override
    public String getLinkByPage(int page) {
        if(cid==-1){
            return "Status.jsp?page="+(page)+"&pid="+pid+"&result="+result+"&lang="+Language+"&user="+ssuser+(all?"&all=1":"");
        }else{
            //return "Contest.jsp?cid="+cid+"&page="+(page+1)+"&pid="+pid+"&result="+result+"&lang="+Language+"&user="+ssuser+"#Status";
            return "javascript:topage("+page+");";
        }
    }

    @Override
    public String rightForm(){
        FormHTML f=new FormHTML();
        if(cid==-1) f.setAction("Status.jsp");
        else{
//            f.setAction("Contest.jsp");
            f.setScript("javascript:seachStatus();");
        }
        f.setType(1);
        text f1=new text("user","用户");
        f1.setType(1);
        if(ssuser!=null) f1.setValue(ssuser);
        f1.setId("user");
        f.addForm(f1);
        //////////////////////////
        if(cid==-1){
            text f2=new text("pid","题目");
            f2.setType(1);
            if(pid!=-1)f2.setValue(pid+"");
            f2.setId("pid");
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
        f3.setValue(result+"");
        f.addForm(f3);
        ///////////////////
        select f4=new select("lang","语言");
        f4.setType(1);
        f4.add(-1, "All");
        f4.add(0,"G++");
        f4.add(1,"GCC");
        f4.add(2,"JAVA");
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
    private String LanguageToHtml(statu s){
        int l=s.getLanguage();
        int rid=s.getRid();
        if(Main.canViewCode(s,user)){
            if(!incontest){
                if(l==0)return HTML.a("ViewCode.jsp?rid="+rid,"C++");
                if(l==1)return HTML.a("ViewCode.jsp?rid="+rid,"C");
                if(l==2)return HTML.a("ViewCode.jsp?rid="+rid,"JAVA");
                return HTML.a("ViewCode.jsp?rid="+rid,"UNKNOW");
            }else{
                if(l==0)return HTML.a("javascript:viewcode("+rid+")","C++");
                if(l==1)return HTML.a("javascript:viewcode("+rid+")","C");
                if(l==2)return HTML.a("javascript:viewcode("+rid+")","JAVA");
                return HTML.a("javascript:viewcode("+rid+")","UNKNOW");
            }
        }else{
            if(l==0)return "C++";
            if(l==1)return "C";
            if(l==2)return "JAVA";
            return "UNKNOW";
        }
    }
    private String pidToHtml(statu s,boolean in){
        if(!in){
            return HTML.a("Problem.jsp?pid="+s.getPid(),""+s.getPid());
        }else{
            String ss=ContestMain.getContest(cid).getProblemId(s.getPid());
            return HTML.a("#P"+s.getContestPid(),ss);
        }
    }
    private String userToHtml(statu s){
        User u=Main.users.getUser(s.getUser());
        if(!incontest) return u.getUsernameHTML()+"("+u.getNick()+")";
        else return HTML.a("#R"+s.getUser(),u.getUsernameHTMLNoA())+"("+u.getNick()+")";
    }
}