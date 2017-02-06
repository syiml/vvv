package util.HTML;

import entity.Enmu.UserStarType;
import entity.User;
import entity.UserStar;
import servise.UserService;
import util.HTML.FromHTML.FormHTML;
import util.HTML.FromHTML.hidden.hidden;
import util.HTML.FromHTML.text.text;
import util.Main;
import entity.Problem;
import util.HTML.modal.modal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/6.
 */
public class problemHTML {

    public String getTitle() {
        return Title;
    }
    public String getDis() {
        return Dis;
    }
    public String getInput() {
        return Input;
    }
    public String getOutput() {
        return Output;
    }
    public List<String> getSampleInput() {
        return SampleInput;
    }
    public List<String> getSampleOutput() {
        return SampleOutput;
    }
    public String getTimeLimit() {
        return TimeLimit;
    }
    public String getMenoryLimit() {
        return MenoryLimit;
    }
    public String getInt64() {
        return Int64;
    }
    public int getSpj(){return spj?1:0;}
    public boolean isSpj() {
        return spj;
    }

    private String Title;
    private String Dis;
    private String Input;
    private String Output;
    private List<String> SampleInput;
    private List<String> SampleOutput;
    private String TimeLimit;
    private String MenoryLimit;
    private String Int64;
    private boolean spj;
    private boolean admin=false;
    private int pid;
    private boolean isInContest = false;
    private User user = Main.loginUser();
    public void setPid(int pid){this.pid=pid;}
    public void setSpj(int i){spj=(i!=0);}
    public problemHTML(){
        Dis="";
        Input="";
        Output="";
        TimeLimit="";
        MenoryLimit="";
        Int64="";
        spj=false;
        SampleInput=new ArrayList<String>();
        SampleOutput=new ArrayList<String>();
    }
    public problemHTML(int pid){
        Dis="";
        Input="";
        Output="";
        TimeLimit="";
        MenoryLimit="";
        Int64="";
        spj=false;
        SampleInput=new ArrayList<String>();
        SampleOutput=new ArrayList<String>();
        this.pid=pid;
    }
    public void setTitle(String title){
        Title=title;
    }
    public void setDis(String dis) {
        Dis = dis;
    }
    public void setInput(String input) {
        Input = input;
    }
    public void setOutput(String output) {
        Output = output;
    }
    public void setTimeLimit(String t){
        TimeLimit = t;
    }
    public void setMenoryLimit(String m){
        MenoryLimit = m;
    }
    public void setInt64(String m){
        Int64 = m;
    }
    public void addSample(String sampleinput,String sampleoutput){
        SampleInput.add(sampleinput);
        SampleOutput.add(sampleoutput);
    }
    private String getTitleHTML(){
        return "<h1 style='text-align:center'>"+Title+"</h1>";
    }
    private String getLimitHTML(){
        String s="";
        s+="<div class='row'>" +
                "<div style='text-align:center'>" +
                    "TimeLimit:"+TimeLimit+"&nbsp;&nbsp;MemoryLimit:"+MenoryLimit+
                "</div>"+
           "</div>";
        return s;
    }
    private String getInt64HTML(){
        String s="";
        s+="<div class='row'>" +
                "<div style='text-align:center'>" +
                    "64-bit integer IO format:<span class='badge'>" + Int64 +"</span>"+
        "</div>"+
           "</div>";
        return s;
    }
    private String getSpjHTML(){
        String s="";
        if(spj){
            s+="<div class='row'>" +
                    "<center>" +
                        HTML.text("Special Judge","red") +
                    "</center>"+
                "</div>";
        }
        return s;
    }
    private String edit(String href,String s){
        return "<a class=\"btn btn-default btn-sm\" style=\"\n" +
                "    margin-top: -16px;\n" +
                "    margin-right: -16px;\n" +
                "    float: right;\n" +
                "    border-radius: 0px 0px 0px 6px;\n" +
                "\" href='"+href+"'>"+s+"</a>";
    }
    public void setAdmin(boolean a){
        admin=a;
    }
    private String problemMisStar(){
        if(user == null){
            return HTML.text(HTML.glyphicon("star-empty")+"登录后收藏","green");
        }
        UserStar userStar = UserService.userStarSQL.getBeanByKey(user.getUsername()).getStar(UserStarType.PROBLEM,pid);
        if(userStar != null){
            return HTML.a("cancelStarProblem.action?starID="+pid,"title='点击取消收藏'",HTML.text(HTML.glyphicon("star")+"已收藏(备注:"+userStar.getText()+")","green"));
        }else {
            FormHTML formHTML = new FormHTML();
            formHTML.addForm(new hidden("starID", pid + ""));
            formHTML.addForm(new text("text", "备注"));
            formHTML.setPartFrom();
            modal m = new modal("problemStar", "收藏题目", formHTML.toHTML(), HTML.glyphicon("star-empty") + "点击收藏");
            m.setAction("starProblem.action");
            return m.toHTMLA();
        }
    }
    private String problemMisSolved(){
        if(user != null){
            int z = Main.status.submitResult(pid,user.getUsername());
            //1->AC
            //0->submit but no AC
            //-1->no submit
            if(z == 1){
                return HTML.text(HTML.glyphicon("ok")+"已解决","green");
            }else if(z == 0){
                return HTML.text(HTML.glyphicon("remove")+"未解决","red");
            }else{
                return HTML.text(HTML.glyphicon("minus")+"未提交","black");
            }
        }else{
            return HTML.text(HTML.glyphicon("minus")+"未提交","black");
        }
    }
    private String getProblemMis(){
        if(!isInContest){
            return problemMisSolved()+" | "+problemMisStar();
        }else{
            return "";
        }
    }
    public String getHTML(){
        Problem p= Main.problems.getProblem(pid);
        String s="";
        s+=getTitleHTML();
        s+=getLimitHTML();
        s+=getInt64HTML();
        s+=getSpjHTML();

        String admin_s = "";
        if(admin){
            String adminstring="";
            if(p.getType()==Problem.OTHEROJ && p.getOjid()!=7){
                adminstring += "[" + HTML.a("delProblemDis.action?pid=" + pid, "重新获取") + "]";
                adminstring+="<br>"+"["+HTML.a("admin.jsp?page=AddProblem&pid="+pid,"编辑")+"]";
            }else{
                adminstring+="["+HTML.a("UploadSample.jsp?pid="+pid,"测试数据")+"]";
                adminstring+="<br>"+"["+HTML.a("admin.jsp?page=AddProblem&pid="+pid,"编辑")+"]";
            }
            modal mo=new modal("problem_admin","题目管理",adminstring,"admin");
            mo.setBtnCls("link btn-sm");
            admin_s = HTML.floatRight(mo.toHTMLA());
        }
        s+=HTML.div("row", HTML.col(12,0,"'style='padding-top:8px;padding-bottom:8px", getProblemMis() + admin_s));
        //s+="<br><br>";
        s+=HTML.panel("Problem Description", (admin?edit("editproblem.jsp?pid="+pid+"&edit=dis","编辑"):"")+Dis);
        s+=HTML.panel("Input", (admin?edit("editproblem.jsp?pid="+pid+"&edit=input","编辑"):"")+Input);
        s+=HTML.panel("Output", (admin?edit("editproblem.jsp?pid="+pid+"&edit=output","编辑"):"")+Output);
        if(SampleInput.size()>=2){
            String ss="";
            for(int i=0;i<SampleInput.size();i++){
                ss+=HTML.panel("SampleInput "+(i+1),
                        (admin?edit("editproblem.jsp?pid="+pid+"&edit=sampleinput&num="+i,"编辑"):"")
                         +SampleInput.get(i));
                ss+=HTML.panel("SampleOutput " + (i + 1),
                        (admin?edit("editproblem.jsp?pid="+pid+"&edit=sampleoutput&num="+i,"编辑"):"")
                         +SampleOutput.get(i));
            }
            s+=ss;
            //s+=HTML.panel("SampleInput And SampleOutput", ss);
        }else if(SampleInput.size()==1){
            s+=HTML.panel("SampleInput", (admin?edit("editproblem.jsp?pid="+pid+"&edit=sampleinput&num=0","编辑"):"")+SampleInput.get(0));
            s+=HTML.panel("SampleOutput", (admin?edit("editproblem.jsp?pid="+pid+"&edit=sampleoutput&num=0","编辑"):"")+SampleOutput.get(0));
        }else if(SampleInput.size()==0){
            s+=HTML.panel("SampleInput And SampleOutput", (admin?edit("module/addproblemsample.jsp?pid="+pid,"新增"):"")+"NULL");
        }
        return s;
    }
    public String getHTML2(){
        String s="";
        s+=getTitleHTML();
        s+=getLimitHTML();
        s+=getInt64HTML();
        s+=getSpjHTML();
        if(admin){
            s+=HTML.floatRight(HTML.a("admin.jsp?page=AddProblem&pid="+pid,"Edit"));
        }
        s+="<br>";
        s+="<h3>"+"Problem Description"+"</h3><hr>";
        s+=(admin?edit("editproblem.jsp?pid="+pid+"&edit=dis","编辑"):"")+Dis;
        s+="<h3>"+"Input"+"</h3><hr>";
        s+=(admin?edit("editproblem.jsp?pid="+pid+"&edit=input","编辑"):"")+Input;
        s+="<h3>"+"Output"+"</h3><hr>";
        s+=(admin?edit("editproblem.jsp?pid="+pid+"&edit=output","编辑"):"")+Output;

        if(SampleInput.size()>=2){
            String ss="";
            for(int i=0;i<SampleInput.size();i++){
                ss+="<h3>"+"SampleInput "+(i+1)+"</h3><hr>";
                ss+=(admin?edit("editproblem.jsp?pid="+pid+"&edit=sampleinput&num="+i,"编辑"):"")
                                +SampleInput.get(i);
                ss+="<h3>"+"SampleOutput "+(i+1)+"</h3><hr>";
                ss+=(admin?edit("editproblem.jsp?pid="+pid+"&edit=sampleoutput&num="+i,"编辑"):"")
                                +SampleOutput.get(i);
            }
            s+=ss;
            //s+=HTML.panel("SampleInput And SampleOutput", ss);
        }else if(SampleInput.size()==1){
            s+="<h3>"+"SampleInput"+"</h3><hr>";
            s+=(admin?edit("editproblem.jsp?pid="+pid+"&edit=sampleinput&num=0","编辑"):"")+SampleInput.get(0);
            s+="<h3>"+"SampleOutput"+"</h3><hr>";
            s+=(admin?edit("editproblem.jsp?pid="+pid+"&edit=sampleoutput&num=0","编辑"):"")+SampleOutput.get(0);
        }
        return HTML.panel("Problem "+pid,s);
    }

    public int getTime(){
        try {
            return Integer.parseInt(TimeLimit.substring(0, TimeLimit.length() - 2));
        }catch (Exception e){
            return 1000;
        }
    }
    public int getMemory(){
        try {
            return Integer.parseInt(MenoryLimit.substring(0,MenoryLimit.length()-2));
        }catch (Exception e){
            return 128;
        }
    }

    public void setInContest(boolean inContest) {
        isInContest = inContest;
    }
}
