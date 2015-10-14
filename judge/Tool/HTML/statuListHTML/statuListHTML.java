package Tool.HTML.statuListHTML;

import Main.Main;
import Main.User.User;
import Main.status.Result;
import Main.status.statu;
import Tool.HTML.FromHTML.FormHTML;
import Tool.HTML.FromHTML.select.select;
import Tool.HTML.FromHTML.text.text;
import Tool.HTML.HTML;
import Tool.HTML.TableHTML.TableHTML;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/8.
 */
public class statuListHTML {
    String user;
    int cid;
    int num;
    int page;

    //筛选信息
    int pid;
    int result;
    int Language;
    String ssuser;
    boolean incontest;
    boolean all;
    List<statu> status;
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
        status= Main.status.getStauts(this.cid,this.num*this.page,this.num+1,this.pid,this.result,this.Language,this.ssuser,all);
    }
    public String HTML(){
        TableHTML table=new TableHTML();
        table.setClass("table table-striped table-hover table-condensed");
//        table.addColname("Run ID");
//        table.addColname("User");
//        table.addColname("Pid");
//        table.addColname("Result");
//        table.addColname("Language");
//        table.addColname("TimeUsed");
//        table.addColname("Memory");
//        table.addColname("CodeLen");
//        table.addColname("SubmitTime");
        table.addColname("#");
        table.addColname("用户");
        table.addColname("题目");
        table.addColname("评测结果");
        table.addColname("语言");
        table.addColname("耗时");
        table.addColname("使用内存");
        table.addColname("代码长");
        table.addColname("提交时间");
        User u=(User)Main.getSession().getAttribute("user");
        int size;
        if(status.size()==num+1){//当前页满，有下一页
            size=num;
        }else{
            size=status.size();
        }
        for(int i=0;i<size;i++){
            if(u!=null && u.getUsername().equals(status.get(i).getUser())){
                table.addCl(i+1,-1,"info");
            }
            table.addRow(statuToRow(status.get(i),incontest));
        }
        String sss=HTML.floatLeft(pageHTML())+HTML.floatRight(HTML.div("right-block", formHTML()));
        String ret=HTML.panelnobody("评测列表",HTML.div("panel-body","style='padding:5px'",sss)+table.HTML());
        return ret;
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
            String ss=Main.contests.getContest(cid).getProblemId(s.getPid());
            //return HTML.a("Contest.jsp?cid="+s.getCid()+"#Problem_"+s.getPid(),ss);
            return HTML.a("#P"+s.getContestPid(),ss);
        }
    }
    private String userToHtml(statu s){
        User u=Main.users.getUser(s.getUser());
        if(!incontest) return u.getUsernameHTML()+"("+u.getNick()+")";
        else return HTML.a("#R"+s.getUser(),u.getUsernameHTMLNoA())+"("+u.getNick()+")";
    }
    private List<String> statuToRow(statu s,boolean incontest){
        List<String> row=new ArrayList<String>();
        //rid
        row.add(""+s.getRid());
        //username
        row.add(userToHtml(s));
        //pid
        row.add(""+pidToHtml(s,incontest));
        //res
        if(s.getResult()== Result.CE){
            if(incontest){
                row.add(HTML.a("javascript:ceinfo(" + s.getRid() + ");", HTML.span("warning", "Compilation Error")));
            }else{
                row.add(HTML.a("CEinfo.jsp?rid=" + s.getRid(), HTML.span("warning", "Compilation Error")));
            }
        }else  row.add(""+s.resultToHTML(s.getResult()));
        row.add(""+LanguageToHtml(s));
        row.add(""+s.getTimeUsed());
        row.add(""+s.getMemoryUsed());
        row.add(""+s.getCodelen());
        row.add(""+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(s.getSbmitTime()));
        return row;
    }
    private String preHref(){
        if(cid==-1){
            return "Status.jsp?page="+(page-1)+"&pid="+pid+"&result="+result+"&lang="+Language+"&user="+ssuser+(all?"&all=1":"");
        }else{
            //return "Contest.jsp?cid="+cid+"&page="+(page-1)+"&pid="+pid+"&result="+result+"&lang="+Language+"&user="+ssuser+"#Status";
            return "javascript:prepage();";
        }
    }
    private String topHref(){
        if(cid==-1){
            return "Status.jsp"+"?pid="+pid+"&result="+result+"&lang="+Language+"&user="+ssuser+(all?"&all=1":"");
        }else{
            //return "Contest.jsp?cid="+cid+"&pid="+pid+"&result="+result+"&lang="+Language+"&user="+ssuser+"#Status";
            return "javascript:toppage();";
        }
    }
    private String nextHref(){
        if(cid==-1){
            return "Status.jsp?page="+(page+1)+"&pid="+pid+"&result="+result+"&lang="+Language+"&user="+ssuser+(all?"&all=1":"");
        }else{
            //return "Contest.jsp?cid="+cid+"&page="+(page+1)+"&pid="+pid+"&result="+result+"&lang="+Language+"&user="+ssuser+"#Status";
            return "javascript:nextpage();";
        }
    }
    private String pageHTML(){
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
        if(status.size()==num+1){
            s+=HTML.abtn(size,nextHref(),"下一页","");
        }else{
            s+=HTML.abtn(size,"","下一页","disabled");
        }
        s+="</div></div>";
        return s;
    }
    private String formHTML(){
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
            int size=Main.contests.getContest(cid).getProblemNum();
            f2.add(-1,"All");
            for(int i=0;i<size;i++){
                f2.add(i,(char)(i+'A')+"");
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
        select f4=new select("lang","语音");
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
}