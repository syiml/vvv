package util.HTML.problemListHTML;

import entity.PermissionType;
import servise.ContestMain;
import util.HTML.FromHTML.check.check;
import util.Main;
import entity.Permission;
import entity.User;
import dao.ProblemTagSQL;
import entity.ProblemTag;
import util.HTML.FromHTML.FormHTML;
import util.HTML.FromHTML.select.select;
import util.HTML.FromHTML.text.text;
import util.HTML.HTML;
import util.HTML.TableHTML;
import util.Tool;

import java.util.*;

/**
 * Created by Syiml on 2015/6/12 0012.
 */
public class problemListHTML {
    List<problemView> list;
    boolean incontest;
    int page,num,cid;
    Permission p;
    User user;
    public problemListHTML(int num,int page,User user){
        this.page=page;
        this.num=num;
        this.user=user;
        p= (user==null?new Permission():user.getPermission());
        list= Main.problems.getProblems((page-1)*num+1000,(page-1)*num+1000+num-1,p.getShowHideProblem()
                ,(user!=null&&p.havePermissions(PermissionType.partAddProblem))?user.getUsername():null);
        incontest=false;
    }
    public problemListHTML(int cid){
        this.user=Main.loginUser();
        if(user==null){
            this.user=null;
            return ;
        }
        list=Main.problems.getProblems(cid);
        incontest=true;
        p=this.user.getPermission();
        this.cid=cid;
    }
    public String HTMLincontest(){
        if(!incontest) return HTML();
        if(user==null){
            return HTML.panel("error","please login first",null,"danger");
        }
        TableHTML table=new TableHTML();
        table.setClass("table table-striped table-hover table-bordered");
        table.addColname("S");//Solved
        table.addColname("#");
        table.addColname("Title");
        table.addColname("R");//Ratio
        for(int i=0;i<list.size();i++){
            int pid=list.get(i).pid;
            List<String> row=new ArrayList<String>();
            if(user!=null){
                int result=Main.status.submitResult(cid, pid, user.getUsername());
                Tool.log(cid+"-"+pid+"-"+user.getUsername()+"-"+result);
                if(result==1){
                    row.add(HTML.text("✔","green"));
                }else if(result==0){
                    row.add(HTML.text("✘","red"));
                }else row.add("");
            }
            row.add(ContestMain.getContest(cid).getProblemId(pid));
//            if(list.size()<=26){
//                row.add((char)(pid+'A')+"");
//            }else{
//                row.add(pid+"");
//            }
            row.add(HTML.a("Contest.jsp?cid="+cid+"#Problem_"+pid,list.get(i).title));
            //AC率，AC人数/提交人数    ->或改成  AC人数/提交次数 ？
            int ac,sub;
            ac=list.get(i).ac;
            sub=list.get(i).submit;
            if(sub!=0){
                row.add(String.format("%.2f", 1.0*ac/sub*100)+
                        "%("+
                        HTML.a("Contest.jsp?pid="+pid+"&result=1&cid="+cid+"#Status",ac+"")+
                        "/"+
                        HTML.a("Contest.jsp?pid="+pid+"&cid="+cid+"#Status",sub+"")+")");
            }
            else row.add("0.00%(0/0)");
            table.addRow(row);
        }
        return HTML.panelnobody("Problems List",table.HTML());
    }
    public String HTML(){
        if(incontest) return HTMLincontest();
        TableHTML table=new TableHTML();
        table.setClass("table table-striped table-hover table-condensed");
        if(user!=null){
            table.addColname("S");
        }
        table.addColname("#");
        table.addColname("Title");
        table.addColname("Ratio");
        if(p.getShowHideProblem()){
            table.addColname("Visiable");
        }
        Map<Integer,Integer> submitResult;
        if(user!=null&&list.size()>=1) submitResult=Main.status.submitResult(user.getUsername(),list.get(0).pid, list.get(list.size()-1).pid);
        else submitResult=new HashMap<Integer, Integer>();

        for(int i=0;i<list.size();i++){
            int pid=list.get(i).pid;
            List<String> row=new ArrayList<String>();
            if(user!=null){
//                Integer result=Main.status.submitResult(pid,user.getUsername());
                if(!submitResult.containsKey(pid)){
                    row.add("");
                }else if(submitResult.get(pid)==1){
                    row.add(HTML.text("✔","green"));
                }else
                    row.add(HTML.text("✘","red"));
            }
            row.add(pid+"");
            row.add(HTML.a("Problem.jsp?pid="+pid,list.get(i).title));
            //AC率，AC人数/提交人数    ->或改成  AC人数/提交次数 ？
            //或放进problemView内
            int ac,sub;



            ac=list.get(i).ac;
//            sub=Main.status.getProblemSubmitUserNum(pid);
            sub=list.get(i).submit;
            if(sub!=0){
                row.add(String.format("%.2f", 1.0*ac/sub*100)+
                        "%("+
                        HTML.a("Status.jsp?all=1&pid="+pid+"&result=1",ac+"")+
                        "/"+
                        HTML.a("Status.jsp?all=1&pid="+pid,sub+"")+")");
            }
            else row.add("0.00%(0/0)");

            if(p.getShowHideProblem()){
                if(p.getAddContest()){
                    row.add(HTML.a("pvis.action?pid="+pid,list.get(i).hide==1?"YES":"NO"));
                }else{
                    row.add(list.get(i).hide==1?"YES":"NO");
                }
            }
            table.addRow(row);
        }
        String head="题目列表";
        if(p.getAddProblem()||p.havePermissions(PermissionType.partAddProblem)){
            head+=HTML.floatRight(HTML.a("admin.jsp?page=AddProblem","New"));
        }
        return HTML.panelnobody(
                head,
                HTML.div(
                        "panel-body","style='padding:5px'",
                        HTML.floatLeft(pageHTML())
                                +HTML.floatRight(form())
                )
                +table.HTML());
    }
    public String pageHTML(){
        if(incontest) return "";
        int pagenum=Main.problems.getPageNum(num,p.getShowHideProblem());
        String size="sm";
        String s ="<div class='btn-toolbar' role='toolbar'>";
        s+="<div class='btn-group' role='group'>";
        for(int i=0;i<pagenum;i++){
            s+=HTML.abtn(size,"ProblemSet.jsp?page="+(i+1),i+1+"",page==i+1?"btn-primary":"");
        }
        s+="</div></div>";
        return s;
    }
    public String form(){
        FormHTML form=new FormHTML();
        form.setAction("ProblemListFilter.jsp");
        form.setType(1);
        check ch = new check("star","我的收藏 ");
        form.addForm(ch);

        text t=new text("name","标题");
        form.addForm(t);

        select s=new select("tag","标签");
        s.add(-1," - ");
        for(ProblemTag pt: ProblemTagSQL.problemTag){
            s.add(pt.getId(),pt.getName());
        }
        s.setType(1);
        s.setValue("-1");
        form.setSubmitText("筛选");
        form.addForm(s);
        return form.toHTML();
    }
}
