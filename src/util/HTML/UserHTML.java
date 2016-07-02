package util.HTML;

import entity.TeamMemberAwardInfo;
import entity.TeamMemberAwardInfo_ContestLevel;
import util.Main;
import entity.Permission;
import entity.User;
import dao.ProblemTagSQL;
import util.HTML.FromHTML.FormHTML;
import util.HTML.FromHTML.select.select;
import util.HTML.FromHTML.text.text;
import util.HTML.modal.modal;

import java.util.List;
import java.util.Set;

/**
 * Created by Syiml on 2015/6/26 0026.
 */
public class UserHTML {
    User showuser;//要显示的user信息
    User user;//正在查看该信息的user
    Set<Integer> l1;
    public UserHTML(User showuser,User user){
        this.showuser=showuser;
        this.user=user;
    }
    public String UserInfo2(){
        String headimg;
        if(user==null||!showuser.getUsername().equals(user.getUsername())) headimg=HTML.headImg(showuser.getUsername(), 2);
        else{
            modal m=new modal("headimg","上传头像",HTML.uploadHead(),HTML.headImg(showuser.getUsername(), 2));
            m.setHavesubmit(true);
            m.setAction("uploadhead.action");
            m.setEnctype();
            headimg=m.toHTMLA();
        }
        String left=headimg + "<br>"+HTML.text(showuser.getNick(),10)+"<br>"+HTML.text(showuser.getMotto(), 4);
        String r="";
        r+=HTML.text(showuser.getNick(),7)+HTML.text("("+showuser.getUsername() + ")",5);
        r+="在"+HTML.text(showuser.getRegistertime().toString().substring(0,10),5)+"加入OJ，";
        if(showuser.getSchool().length()>0) r+="来自"+HTML.text(showuser.getSchool(),5)+"。";
        r+="<br>";
        if(showuser.getRatingnum()==0){
            r+="截止目前尚未参加过积分赛，";
        }else{
            r+="截止目前共参加了"+HTML.text(showuser.getRatingnum()+"",6) + "场积分赛，";
            if(showuser.getShowRating()>=1300){
                r+="当前rating达到" +HTML.text(User.ratingToHTML(showuser.getShowRating()),8) + "了，";
                if(showuser.getShowRating()<1500) r+="";
                else if(showuser.getShowRating()<1600) r+="棒棒哒，";
                else if(showuser.getShowRating()<1700) r+="非常厉害，";
                else if(showuser.getShowRating()<1900) r+="好牛逼的，";
                else if(showuser.getShowRating()<2000) r+="已经出神入化，";
                else if(showuser.getShowRating()<2300) r+="快来膜拜巨巨，";
                else if(showuser.getShowRating()<2600) r+="这已经超神了，";
                else r+="我从未见过有如此厉害之人，";
            }else{
                r+="rating是"+HTML.text(User.ratingToHTML(showuser.getShowRating()),8)+"，";
            }
        }
        r+="在所有人中排名第" + HTML.text(showuser.getRank()+"",8) + "。<br>";
        //int acnum = Main.status.getAcNum(showuser.getUsername());
        int acnum = showuser.getAcnum();
        if(acnum!=0){
            r+="已经在OJ上AC过"+HTML.text(acnum+"",8)+"道题目，";
            if(acnum<10) r+="";
            else if(acnum<20) r+=HTML.text("已经小有所成",2,"#40C040")+"，";
            else if(acnum<50) r+=HTML.text("正在疯狂A题中",2,"#40C040")+"，";
            else if(acnum<100) r+=HTML.text("简直是A题能手",3,"#00FF00")+"，";
            else if(acnum<200) r+=HTML.text("OJ已经不能阻止他AC了",4,"#00C0FF")+"，";
            else if(acnum<300) r+=HTML.text("正在走向成神之路",5,"#0000FF")+"，";
            else if(acnum<500) r+=HTML.text("正在进入神的殿堂",6,"#C000FF")+"，";
            else if(acnum<1000) r+=HTML.text("AC的题目比走过的路还多",7,"#FF00FF")+"，";
            else r+=HTML.text("神都不能阻止他AC了",8,"#FF0080")+",";
        }
        r+="一共提交过"+HTML.text(Main.status.getSubmitTime(showuser.getUsername())+"",8) + "次。<br>";
        int tagnum=ProblemTagSQL.getTagNum(showuser.getUsername());
        if(tagnum!=0)
            r+="一共给"+ HTML.text(tagnum+"",5)+"道题目贴过标签，";
        else r+="还没有给题目贴过标签，";
        r+="当前有"+HTML.text(showuser.getACB()+"",5)+"ACB。<br>";

        if(showuser.getInTeamLv()>0){
            r+=TeamMemberInfo();
        }
//                 r+= HTML.text("Rank　", 4, "gray") + HTML.text(showuser.getRank() + "", 10) + "<br>" +
//                        HTML.text("Rating", 4, "gray") + HTML.text(User.ratingToHTML(showuser.getShowRating()), 10);
//        String rr=HTML.text("AC　　",4,"gray")+HTML.text(Main.status.getAcNum(showuser.getUsername())+"",10)+"<br>"+
//                HTML.text("Submit",4,"gray")+HTML.text(Main.status.getSubmitTime(showuser.getUsername())+"",10);
        //if(showuser.getMotto().length()!=0) r+="他要说的一句话是："+HTML.text("“"+showuser.getMotto()+"”",6);
        String in=HTML.floatRight( Tag())+r+Permissions();
        return HTML.div("userindex",HTML.center(left)+in);
    }
    public String TeamMemberInfo(){
        List<TeamMemberAwardInfo> infoList = Main.users.getTeamMemberAwardInfoList(showuser.getUsername());
        if(infoList.size()==0) return "";
        StringBuilder sb = new StringBuilder(HTML.textb("正式队员经历：<br>",4,""));
        for(TeamMemberAwardInfo info : infoList){
            sb.append("　　").append(info.getTime()).append(": ");
            if(info.getContestLevel() == TeamMemberAwardInfo_ContestLevel.NONE){
                sb.append(info.getText());
            }else {
                sb.append("参加").append(info.getContestLevel()).append("获得").append(info.getAwardLevel());
                if (info.getText().length() > 0) sb.append("(").append(info.getText()).append(")");
            }
            sb.append("<br>");
        }
        return sb.toString();
    }
    public String Mark(){
        return "";
//        return HTML.panel("Mark","",null,"info");
    }
    public String Permissions(){//user==showuser || user is admin
        if(user!=null&&user.getUsername().equals(showuser.getUsername())){
            Permission p=Main.getPermission(showuser.getUsername());
            String title="拥有权限";
            if(p.getAddContest()||p.getReJudge()||p.getAddProblem()||p.getAddTag()){
                title=title+"["+HTML.a("admin.jsp","Admin")+"]";
            }
            title+=": ";
            String s=p.toHTML();
            if(s.length()>0)
                return "<br>"+title+s;
        }
        return "";
    }
    public String Rating(){
//        String rr=HTML.UserRating(showuser.getUsername());
        String script="<div id='rating'>"+HTML.loader("400px")+"</div>"+
                "<script>" +
                "$('#rating').load('module/ratingshow.jsp?user="+
                showuser.getUsername();
//        User loginUser=(User)Main.getSession().getAttribute("user");
//        if(loginUser!=null&&!loginUser.getUsername().equals(showuser.getUsername())){
//            script+="&user2="+loginUser.getUsername();
//        }
        script+="');</script>";
        return HTML.panelnobody("Rating", "primary",script);//+rr
        //return "";
    }
    public String SolvedProblems(){
        String s="";
        l1= Main.status.getAcProblems(showuser.getUsername());
        for(Integer i:l1){
            s+=HTML.a("Status.jsp?all=1&user="+showuser.getUsername()+"&pid="+i+"&result=1",i+"")+" ";
        }
        return HTML.panel("已经解决题目列表："+l1.size(),s,null,"success");
    }
    public String NotSolvedProblems(){
        String s="";
        Set<Integer> l1= Main.status.getNotAcProblems(showuser.getUsername());
        for(Integer i:l1){
            s+=HTML.a("Status.jsp?all=1&user="+showuser.getUsername()+"&pid="+i,i+"")+" ";
        }
        return HTML.panel("尝试过但是仍未解决的题目列表："+l1.size(),s,null,"danger");
    }
    public String AcNotTag(){//AC了但是没有获得看代码权限的题目
        String s="";
        //l1=Main.status.getAcProblems(showuser.getUsername());
        Set<Integer> l2=Main.users.canViewCode(showuser.getUsername());
        int k=0;
        for(Integer pid:l1){
            if(!l2.contains(pid)){
                s+=HTML.a("Problem.jsp?pid="+pid,pid+"")+" ";
                k++;
            }
        }
        return HTML.panel("待贴标签题目列表："+k,s,null,"info");
    }
    public String Tag(){
        String script="<div id='ptag'>"+HTML.loader("200px","230px")+"</div>"+
                "<script>" +
                "$('#ptag').load('module/usertag.jsp?user="+showuser.getUsername();
        script+="');</script>";
        //return HTML.panelnobody("AC题目的专题分布(仅统计有贴标签的题目)", "primary",script);//+rr
        return script;
    }
    public String SubmitForm(){
        FormHTML f=new FormHTML();
        text t=new text("num","最近");
        t.setId("SubmitCount-num");
        select s=new select("sec","统计区间");
        s.setType(1);
        s.setId("SubmitCount-sec");
        s.add(60, "1分钟");
        s.add(60*5, "5分钟");
        s.add(60*20, "20分钟");
        s.add(60*60,"1小时");
        s.add(60*60*3,"3小时");
        s.add(60*60*6,"6小时");
        s.add(60*60*8,"8小时");
        s.add(60*60*24,"1天");
        s.add(60*60*24*7,"1周");
        s.add(60*60*24*30,"1月");
        s.add(60*60*24*30*3,"1季度");
        s.add(60 * 60 * 24 * 365, "1年");
        t.setValue("30");
        s.setValue(60*60*24+"");
        f.addForm(t);
        f.addForm(s);
        f.setSubmitText("统计");
        f.setType(1);
        f.setScript("javascript:submitcount_formsubmit()");
        return HTML.div("","style='padding:5px'",HTML.floatRight(f.toHTML()));
    }
    public String SubmitCount(){
        String script=SubmitForm()+"<div id='submitcount'></div>"+
                "<script>" +
                "function submitcount_go(user,num,sec){" +
                "$('#submitcount').html(HTML.loader_h('400px')).load('module/submitcount.jsp?user='+user+'&num='+num+'&sec='+sec);"+
                "}" +
                "submitcount_go('"+showuser.getUsername()+"',"+30+","+60*60*24+");" +
                "function submitcount_formsubmit(){" +
                "submitcount_go('"+showuser.getUsername()+"',$('#SubmitCount-num').val(),$('#SubmitCount-sec').val());" +
                "}";
        script+="</script>";
        return HTML.panelnobody("提交统计", "primary",script);//+rr
    }
    public String HTML(){
//        String left=userInfo()+Permissions()+Mark();
//        String right=Rating()+SolvedProblems()+NotSolvedProblems();
        return UserInfo2()+Rating()+SubmitCount()+ SolvedProblems()+NotSolvedProblems()+AcNotTag();
        //return HTML.row(HTML.col(3,"md",left)+HTML.col(9,"md",right));
    }
}
