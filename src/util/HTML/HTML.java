package util.HTML;

import entity.*;
import servise.ChallengeMain;
import dao.ChallengeSQL;
import ClockIn.ClockInHTML;
import ClockIn.ClockInSQL;
import servise.ContestMain;
import util.*;
import util.CodeCompare.cplusplus.ContestCodeCompare;
import entity.OJ.OTHOJ;
import util.HTML.FromHTML.hidden.hidden;
import util.SQL.SQL;
import util.Vjudge.VjSubmitter;
import entity.rank.RankICPC.RankICPC;
import entity.rank.RankShortCode.RankShortCode;
import entity.rank.RankTraining.RankTraining;
import util.rating.Computer;
import dao.ratingSQL;
import util.HTML.FromHTML.FormHTML;
import util.HTML.FromHTML.FormPart.FormPart;
import util.HTML.FromHTML.check.check;
import util.HTML.FromHTML.date.date;
import util.HTML.FromHTML.file.file;
import util.HTML.FromHTML.select.select;
import util.HTML.FromHTML.text.text;
import util.HTML.FromHTML.text_in.text_in;
import util.HTML.FromHTML.text_select.text_select;
import util.HTML.FromHTML.textarea.textarea;
import util.HTML.UserListHTML.UserListContest;
import util.HTML.UserListHTML.UserListHTML;
import util.HTML.problemListHTML.problemListFilterHTML.ProblemListFilter;
import util.HTML.problemListHTML.problemListHTML;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTML类 产生HTML代码供页面显示
 * Created by Administrator on 2015/6/8.
 */
public class HTML {
    public static String text(String s,String color){
        return "<font color='"+color+"'>"+s+"</font>";
    }
    public static String text(String s,int size){
        return "<font size='"+size+"'>"+s+"</font>";
    }
    public static String text(String s,int size,String color){
        return "<font size='"+size+"' color='"+color+"'>"+s+"</font>";
    }
    public static String textb(String s,int size,String color){
        return "<font size='"+size+"' color='"+color+"' style='font-weight: bold'>"+s+"</font>";
    }
    public static String textb(String s,String color){
        return "<b style='color:"+color+"'>"+s+"</b>";
    }
    public static String panel(String title,String in,String footer,String arge){
        return panel(title,in,footer,arge,true);
    }
    public static String panel(String title,String in,String footer,String arge,boolean havebody){
        //面板
        String s="";
        s+="<div class='panel panel-";
        if(arge==null){
            s+="default";
        }else{
            s+=arge;
        }
        s+="'>";
        if(title!=null){
            s+="<div class='panel-heading'>"+title+"</div>";
        }
        if(havebody) s+="<div class='panel-body'>";
        s+=in;
        if(havebody) s+="</div>";
        if(footer!=null){
            s+="<div class='panel-footer'>"+footer+"</div>";
        }
        s+="</div>";
        return s;
    }
    public static String panel(String title,String in,String footer,String arge,String css){
        //面板
        String s="";
        s+="<div class='panel panel-";
        if(arge==null){
            s+="default";
        }else{
            s+=arge;
        }
        s+="' "+css;
        s+=">";
        if(title!=null){
            s+="<div class='panel-heading'>"+title+"</div>";
        }
        s+="<div class='panel-body'>";
        s+=in;
        s+="</div>";
        if(footer!=null){
            s+="<div class='panel-footer'>"+footer+"</div>";
        }
        s+="</div>";
        return s;
    }
    public static String panel(String title,String in){return panel(title,in,null,null);}
    public static String panelnobody(String Title,String in){
        String s="";
        s+="<div class='panel panel-default'>";
        s+="<div class='panel-heading'>"+Title+"</div>";
        s+=in;
        s+="</div>";
        return s;
    }
    public static String panelnobody(String Title,String cl,String in){
        String s="";
        s+="<div class='panel panel-"+cl+"'>";
        s+="<div class='panel-heading'>"+Title+"</div>";
        s+=in;
        s+="</div>";
        return s;
    }
    public static String panelnobody(String Title,String cl,String in,String css){
        String s="";
        s+="<div class='panel panel-"+cl+"' "+css+">";
        s+="<div class='panel-heading'>"+Title+"</div>";
        s+=in;
        s+="</div>";
        return s;
    }
    public static String panelToggle(String Title,String cl,String in,String in2,boolean body1,boolean body2,String id,boolean show1){
        //显示2
        String s="";
        s+="<div class='panel panel-"+cl+"'>";
        s+="<div class='panel-heading'><a onclick=\"javascript:$('#"+id+"_1').slideToggle();$('#"+id+"_2').slideToggle();\">"+Title+"</a></div>";
        s+="<div id='"+id+"_1' "+(body1?"class='panel-body'":"")+(!show1?" style='display: none;":"")+"'>"+in+"</div>";
        s+="<div id='"+id+"_2' "+(body2?"class='panel-body'":"")+(show1?" style='display: none;":"")+" '>"+in2+"</div>";
        s+="</div>";
        return s;
    }
    public static String code(String code,boolean replace,int lang){
        String language="";
        if(lang==2) language="java";
        else if(lang==1||lang==0) language="cpp";
        if(replace){
            code=code.replaceAll("&","&amp;");
            code=code.replaceAll("\"","&quot;");
            code=code.replaceAll("<","&lt;");
            code=code.replaceAll(">","&gt;");
        }
        return "<pre style='padding:0px;border-style:none;background-color:transparent'>"+
                "<code class='viewcode language-"+language+"'>"+code+"</code>"+
                "</pre>";
    }
    public static String codeSimple(String rid1,String rid2){
        try{
            return "相似度："+String.format("%.2f",100*Main.codeCmp(Main.getCode(Integer.parseInt(rid1)), Main.getCode(Integer.parseInt(rid2))))+"%";
        }catch (NumberFormatException e){
            return "";
        }
    }
    public static String a(String href,String s){
        return "<a href="+href+">"+s+"</a>";
    }
    public static String aNew(String href,String s){
        return "<a href="+href+" target='view_window'>"+s+"</a>";
    }
    public static String floatRight(String s){return "<front style='float: right'>"+s+"</front>";}
    public static String floatLeft(String s){return "<front style='float: left'>"+s+"</front>";}
    public static String span(String cl,String s){
        return "<span class='label label-"+cl+"'>"+s+"</span>";
    }
    public static String spannull(String cl,String s){
        return "<span class='"+cl+"'>"+s+"</span>";
    }
    public static String glyphicon(String name){
        return "<span class='glyphicon glyphicon-"+name+"'></span>";

    }
    public static String abtn(String size,String href,String s,String arge){
        StringBuilder ret=new StringBuilder("<a role='button' class='btn");
        if(arge.contains("btn-primary")){
            ret.append(" btn-primary");
        }else{
            ret.append(" btn-default");
        }
        ret.append(" btn-").append(size).append("'");
        if(href!=null){
            ret.append(" href='").append(href).append("'");
        }
        if(arge.contains("disabled")){
            ret.append(" disabled='disabled'");
        }
        if(arge.contains("id=")){
            ret.append(arge.substring(arge.indexOf("id=")));
        }
        ret.append(">").append(s).append("</a>");
        return ret.toString();
    }
    public static String btngroup(String s){
        return "<div class='btn-group' role='group'>"+s+"</div>";
    }
    public static String pre(String s){return "<pre style='padding:0px;border-style:none;background-color:transparent'>"+s+"</pre>";}
    public static String center(String s){return "<div style='text-align:center'>"+s+"</div>";}

    public static String div(String cl,String s){
        return "<div class='"+cl+"'>"+s+"</div>";
    }
    public static String div(String cl,String css,String s){
        return "<div class='"+cl+"' " +css+
                " >"+s+"</div>";
    }
    public static String row(String s){
        return "<div class='row'>"+s+"</div>";
    }
    public static String col(int width,int offset,String otherclass,String s){
        return "<div class='col-xs-"+width+" col-xs-offset-"+offset+" "+otherclass+"'>"+s+"</div>";
    }
    public static String col(int width,int offset,String s){
        return "<div class='col-xs-"+width+" col-xs-offset-"+offset+"'>"+s+"</div>";
    }
    public static String col(int width,String s){
        return "<div class='col-xs-"+width+"'>"+s+"</div>";
    }
    public static String col(int width,String size,String s){
        return "<div class='col-"+size+"-"+width+"'>"+s+"</div>";
    }
    public static String script(String src){
        return "<script src='"+src+"'></script>";
    }
    public static String HTMLtoString(String s){
//        s=s.replaceAll("\"","&quot;");
        s=s.replaceAll("&","&amp;");
        s=s.replaceAll("<","&lt;");
        s=s.replaceAll(">","&gt;");
        //s=s.replaceAll(" ","&nbsp;");
        return s;
    }
    public static String timeHTML(String id,long date){
        String s="<text id='"+id+"'></text><script>\n" +
                "var time="+date+";"+
                "    function "+id+"_f() {\n" +
                "      time+=1000;" +
                "      $('#"+id+"').text(new Date(time).Format(\"yyyy-MM-dd hh:mm:ss\"));\n" +
                "    }\n" +
                "    "+id+"_f();\n" +
                "    setInterval( \""+id+"_f()\" , 1000 );\n" +
                "  </script>";
        return s;
    }
    public static String time_djs(long time,String id){//time(ms)
        String s="<text id='"+id+"'></text><script>\n" +
                "    var t="+time+";"+
                "    function "+id+"_f() {\n" +
                "    if(t>=0){"+
                "       var d=Math.floor(t/86400);"+
                "       var time=(t+16*3600);"+
                "      $('#"+id+"').text((d>0?d+'days ':'')+new Date(time*1000).Format('hh:mm:ss'));\n" +
                "      t--;" +
                "    }}\n" +
                "    "+id+"_f();\n" +
                "    setInterval( \""+id+"_f()\" , 1000 );\n" +
                "  </script>";
        return s;
    }
    /**
     * 产生一个进度条，显示在正在进行的比赛内
     * 参考bootstrap的进度条
     * @param now 现在进行到的时间 单位秒
     * @param alltime 总时间 单位秒
     * @param id 为了避免同一个页面有多个进度条，应该要指定不同的id
     * @param s 进度条内文本
     * @return 返回HTML代码
     */
    public static String progress(long now,long alltime,String id,String s){//单位(s)
        return "<div class='progress' style='margin-bottom: 0px;'>" +
                "  <div id='"+id+"' class='progress-bar progress-bar-striped active' role='progressbar' style='width: "+now*100/alltime+"%;'>"+
                s+
                "  </div>" +
                "</div>"+"<script>" +
                "var now="+now+";"+
                "var alltime="+alltime+";"+
                "var "+id+"_progress=function(){var s=(now*100/alltime);now+=5000;\n" +
                "$('#"+id+"').css('width',s+'%');};" +
                "setInterval( \""+id+"_progress()\" , 5000 );" +
                "</script>";
    }
    public static String loader(String height){
        //<img src='pic/loading.jpg'>
        return loader(height,"100%");
    }
    public static String loader(String height,String width){
        //<img src='pic/loading.jpg'>
        return "<div class='loader' style='height:"+height+";width:"+width+"'><div class='loader-inner ball-spin-fade-loader'>" +
                "<div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div></div>";
    }
    public static String headImg(String username,int size){
        return "<img src='pic/head/"+username+".jpg' class='head-"+size+"' onerror='this.src=\"pic/defaulthead.jpg\"' />";
    }
    public static String replaceAt(String s){
        Pattern p = Pattern.compile("(@[_0-9a-zA-Z]+)");
        Matcher m = p.matcher(s);
        while(m.find()){
            String group=m.group();
            String name=group.substring(1,group.length());
            User u=Main.users.getUser(name);
            if(u!=null){
                s=s.replaceAll(group,HTML.a("UserInfo.jsp?user="+u.getUsername(),"@"+u.getUsernameHTMLNoA())/*+"("+u.getName()+")"*/);
            }
        }
        return s;
    }

    public static String ceInfo(String rid,boolean havepanel){
        if(rid==null){
            return panel("Error","缺少参数",null,"danger");
        }else{
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession s=request.getSession();
            User user=(User)s.getAttribute("user");
            if(user==null) return panel("Error", "Please login first", null, "danger");
            int ridInt;
            try{
                ridInt=Integer.parseInt(rid);
            }catch (NumberFormatException e){
                return havepanel?panel("Error","无效的参数",null,"danger"):"无效的参数";
            }
            Permission p = user.getPermission();
            if(p.getViewCode()){
                return Main.status.getCEInfoHTML(Integer.parseInt(rid),havepanel);
            }else{
                Status st=Main.status.getStatu(ridInt);
                if(st.getUser().equals(user.getUsername())){
                    return Main.status.getCEInfoHTML(Integer.parseInt(rid),havepanel);
                }else{
                    return havepanel?panel("Error","没有权限",null,"danger"):"没有权限";
                }
            }
        }
    }
    public static String copyCode(){
        //String s="<script>var copycode=function(){ copyToClipboard($('.viewcode').text());alert('复制成功');}</script>";
        //s+="["+HTML.a("javascript:copycode()","复制")+"]";
        return "";
    }
    public static String viewCode(String rid,boolean havepanel){
        Status s;
        int ridInt;
        try{
            ridInt=Integer.parseInt(rid);
        }catch(NumberFormatException e){
            return havepanel?panel("Error","无效的参数",null,"danger"):"无效的参数";
        }
        s=Main.status.getStatu(ridInt);
        User u=Main.users.getUser(s.getUser());
        User user = Main.loginUser();
        if(user==null) return havepanel?panel("Error","请先登录",null,"danger"):"请先登录";
        Permission p=user.getPermission();
        if(Main.canViewCode(s, user)){
            String code=Main.getCode(ridInt);
            if(p.getReJudge()){
                return havepanel?
                        panel("View Code"+copyCode()+reJudgeForm(s.getRid())+HTML.floatRight(u.getUsernameHTML()+"("+u.getNick()+")"),HTML.pre("/**" +
                                "<br>rid: "+s.getRid()+
                                "<br>user: "+u.getUsername()+
                                "<br>time: "+s.getSbmitTime().toString().substring(0,19)+
                                "<br>result: "+s.getResultString()+
                                "<br>*/")+code(code, true, s.getLanguage()))
                        :copyCode()+reJudgeForm(s.getRid())+HTML.pre("/**" +
                        "<br>rid: "+s.getRid()+
                        "<br>user: "+u.getUsername()+
                        "<br>time: "+s.getSbmitTime().toString().substring(0,19)+
                        "<br>result: "+s.getResultString()+
                        "<br>*/")+code(code, true, s.getLanguage());
            }else{
                return havepanel?
                        panel("View Code"+copyCode()+HTML.floatRight(u.getUsernameHTML()+"("+u.getNick()+")"),HTML.pre("/**" +
                                "<br>rid: "+s.getRid()+
                                "<br>user: "+u.getUsername()+
                                "<br>time: "+s.getSbmitTime().toString().substring(0,19)+
                                "<br>result: "+s.getResultString()+
                                "<br>*/")+code(code,true,s.getLanguage()))
                        :copyCode()+HTML.pre("/**" +
                        "<br>rid: "+s.getRid()+
                        "<br>user: "+u.getUsername()+
                        "<br>time: "+s.getSbmitTime().toString().substring(0,19)+
                        "<br>result: "+s.getResultString()+
                        "<br>*/")+
                        code(code, true,s.getLanguage());
            }
        }else{
            return havepanel?panel("Error","没有权限",null,"danger"):"没有权限";
        }
    }
    private static String problemInfo(int pid,int cid){
        Problem p = Main.problems.getProblem(pid);
        int acNum=p.totalAcUser;
        int submitUserNum=p.totalSubmitUser;
        int submitNum=p.totalSubmit;
        TableHTML table=new TableHTML();
        table.setClass("table");
        table.addColname("");
        table.addColname("");
        table.addCl(0, -1, "hide");
        table.addRow("Solved", HTML.a("Status.jsp?all=1&pid=" + pid + "&result=1", acNum + ""));
        table.addRow("SubmitUser",HTML.a("Status.jsp?all=1&pid=" + pid ,submitUserNum+""));
        table.addRow("Submit",HTML.a("Status.jsp?all=1&pid="+pid,submitNum+""));

        table.addRow("Ratio",(submitUserNum==0?"0.00%":(String.format("%.2f",(1.0*acNum/submitNum*100))+"%")));
        return panelnobody("Information"+HTML.floatRight(HTML.a("ProblemInfo.jsp?pid="+pid+(cid==-1?"":"&cid="+cid),"详细")),"info",table.HTML());
    }
    private static String problemTagJavaScript(){
        return "";
    }
    private static String problemTag(int pid,boolean admin){
        User u=Main.loginUser();
        boolean canedit=false;
        if(u!=null&&Main.status.submitResult(pid, u.getUsername())==1){
            canedit=true;
        }
        String tagslist= ProblemTagHTML.ProblemTag(pid);
//        String tagslist="<script src=\"js/ECharts/echarts.source.js\"></script>";
//        tagslist+="<div id='problemtaglist'></div>";
//        tagslist+="<script>$('#problemtaglist').load('js/ECharts/zifuyun.jsp?pid="+pid+"')</script>";
        return panelToggle(
                "Tags_"+pid,
                "info",
                (canedit?"<div style='margin:5px'>您通过了该题，点击"+HTML.a("javascript:tagadmin("+pid+")","<span class='badge'>此处</span>")+"添加标签</div>":"")+tagslist+"<div id='tagadminmodal'></div>"+problemTagJavaScript(),
                "AC该题后可以添加标签<br>" +
                "贴完标签可以获得20ACB。<br>" +
                "并且可以获得本题所有提交代码查看权限。<br>" +
                "点击标题可以显示标签。<br>" +
                "如果你还没认真思考过这题，请不要查看标签<br>" +
                "如果您已经通过了该题，请务为该题贴上标签",
                false,
                true,
                "problemtag",canedit);
    }
    private static String problemContest(int pid){
        return HTML.panel("出处", ContestMain.problemContest(pid),null,"info");
    }
    private static String problemAuthor(String author){
        if(author==null||author.equals("")) return "";
        return panel("作者",replaceAt(author),null,"info");
    }
    public static String problemRight(Problem p,int pid,boolean admin,int cid){
        return problemInfo(pid,cid)+problemTag(pid,admin)+problemContest(pid)+problemAuthor(p.getAuthor());
    }
    public static String problem(Object user,String cid,String pid){
//        Main.debug("in problem");
        User _user=(User)user;
        boolean admin=false;
        if(cid==null) cid="-1";
        try{
            int cidInt=Integer.parseInt(cid);
            if(pid==null||pid.equals("")) pid="-1";
            int pidInt=Integer.parseInt(pid);

            int tpid;//获取真实pid
            if(cidInt!=-1){
                Contest c=ContestMain.getContest(cidInt);
                tpid= c.getGlobalPid(pidInt);
            }
            else tpid=pidInt;
            if(cidInt!=-1&&!ContestMain.canShowProblem(cidInt)){
                return "error";
            }

            Problem p=Main.problems.getProblem(tpid);
            if(p==null) return panel("EORROR","参数错误",null,"danger");//题目不存在

            Permission per=null;
            if(_user!=null){
                per=Main.getPermission(_user.getUsername());
                //per=_user.getPermission();
            }else{
                per=new Permission();
            }
            if(cidInt==-1&&p.visiable==0){//不可见
                if(_user==null) return  panel("EORROR","没有权限",null,"danger");
                else per=_user.getPermission();
                if(!per.getShowHideProblem()) return  panel("EORROR","没有权限",null,"danger");
            }
            if(p.getType()==1){
                admin=per.getAddProblem();
            }else{
                admin=per.getAddLocalProblem();
            }
            String ret;
            problemHTML ph=Main.problems.getProblemHTML(tpid);

            if(ph==null){
                OTHOJ oj= Submitter.ojs[p.getOjid()];
                ph=oj.getProblemHTML(p.getOjspid());
                //System.out.println("save:pid="+tpid);
                Main.problems.saveProblemHTML(tpid,ph);
            }
            if(ph==null) ret="ERROR";
            else {
                ph.setPid(tpid);
                ph.setAdmin(admin);
                ret=ph.getHTML();
            }
            if(cidInt==-1){
                ret+=center(abtn("", "Submit.jsp?pid=" + pid, "Submit", ""));
            }else {
//                ret += center(abtn("", "Contest.jsp?cid=" + cid + "#Submit_" + pid, "Submit", ""));
                ret += center(abtn("", "javascript:submit("+pid+");","Submit",""));//NEW
            }
            if(cidInt==-1){//
                return HTML.row(HTML.col(9,ret)+HTML.col(3,problemRight(p,pidInt,admin,-1)));
            }else{
                Contest c=ContestMain.getContest(cidInt);
                if(c.getKind()==0){//练习场
                    return HTML.row(HTML.col(9,ret)+HTML.col(3,problemRight(p,tpid,admin,cidInt)));
                }
            }
            return ret;
        }catch (NumberFormatException e){
            return panel("Error","无效的参数",null,"danger");
        }
    }
    public static String problemInfo(String pids,String pages,String cids){
        int pid,page,cid;
        try{
            pid=Integer.parseInt(pids);
        }catch(NumberFormatException e){
            return "参数错误";
        }
        try{
            page=Integer.parseInt(pages);
        }catch(NumberFormatException e){
            page=0;
        }
        try{
            cid=Integer.parseInt(cids);
        }catch(NumberFormatException e){
            cid=-1;
        }
        //权限控制
        Problem p=Main.problems.getProblem(pid);
        if(p.visiable==0){//题目不可见
            if(Main.loginUserPermission().getShowHideProblem()){//拥有查看隐藏题目的权限
                return new ProblemInfo(pid,page).HTML();
            }else{
                if(cid!=-1){
                    Contest c=ContestMain.getContest(cid);
                    if(c.getKind()==0&&c.getcpid(pid)!=-1&&c.isBegin()){//练习场 且 比赛内存在该题目 且 比赛开始了
                        if(ContestMain.canInContest(cid)){//可以进入比赛
                            return new ProblemInfo(pid,page).HTML();
                        }
                    }
                }
                return panel("Error","没有权限",null,"danger");
            }
        }else{
            return new ProblemInfo(pid,page).HTML();
        }
        //return ProblemTagHTML.problemTag(pid,page);
    }
    public static String StatusHTML(int cid,int page,
                            int pid,int Language,int result,String ssuser,boolean all){
//        if(Main.loginUser()==null){
//            return "会话超时，请重新登录";
//        }
        statuListHTML s=new statuListHTML(cid,Main.statuShowNum,page,
                                            pid,Language,result,ssuser,all);
        return s.HTML();
    }
    public static String contestList(String num,String page,String statu,String name,String type,String kind){
        if(num==null||num.equals("")){num=Main.contestShowNum+"";}
        if(page==null||page.equals("")){page="1";}
        if(statu==null||statu.equals("")){statu="-1";}
        if(name==null) name="";
        if(type==null||type.equals(""))type="-1";
        if(kind==null||kind.equals("")) kind="-1";
        try{
            int numInt=Integer.parseInt(num);
            int pageInt=Integer.parseInt(page);
            int statuInt=Integer.parseInt(statu);
            int typeInt=Integer.parseInt(type);
            int kindInt=Integer.parseInt(kind);
            contestListHTML h=new contestListHTML(numInt,pageInt);
            h.setName(name);
            h.setStatu(statuInt);
            h.setType(typeInt);
            h.setKind(kindInt);
            return h.HTML();
        }catch (NumberFormatException e){
            return panel("Error","无效的参数",null,"danger");
        }
    }
    public static String problemList(int num,int page,Object user){
        problemListHTML p=new problemListHTML(num,page,user);
        return p.HTML();
    }
    public static String problemList(String cid){
        try{
            problemListHTML p=new problemListHTML(Integer.parseInt(cid));
            return p.HTMLincontest();
        }catch (NumberFormatException e){
            return panel("error","参数错误",null,"danger");
        }
    }
    public static String problemListFilter(String name,String tag,String page){
        int pa,ta;
        try{
            pa=Integer.parseInt(page);
        }catch (NumberFormatException e){
            pa=1;
        }
        try{
            ta=Integer.parseInt(tag);
        }catch (NumberFormatException e){
            ta=-1;
        }
        ProblemListFilter ret=new ProblemListFilter(name,ta,pa);
        return ret.HTML();
    }
    public static String userList(String cid,String pa,String search,String order,boolean desc){
         if(search==null) search="";
         if(order==null) order="";
         try{
             int page=1;
             if(pa!=null&&!pa.equals("")){
                 page=Integer.parseInt(pa);
             }
             try{
                 int cidInt=Integer.parseInt(cid);
//                 return new UserListHTML(cidInt , page, search).toHTML();
                 return new UserListContest(cidInt,page).HTML();
             }catch(NumberFormatException e) {
                 return new UserListHTML(page, search,order,desc).HTML();
             }
         }catch (NumberFormatException ee){
             return panel("error","参数错误",null,"danger");
         }
    }
    public static String user(String showuser,Object user){
        if(showuser==null||showuser.equals("")){
            return panel("error","参数错误",null,"danger");
        }
        User su=Main.users.getUserHaveRank(showuser);
        User u=null;
        if(user!=null){
            u=(User)user;
        }
        return new UserHTML(su,u).HTML();
    }
    private static String RatingCaseList(List<RatingCase> list){
        TableHTML table=new TableHTML();
        table.setClass("table table-hover table-condensed");
        table.addColname("username");
        table.addColname("cid");
        table.addColname("time");
        table.addColname("rank");
        table.addColname("prating");
        table.addColname("Rating");
        table.addColname("change");
        table.addColname("#");
        for(RatingCase rc:list){
            List<String> row=new ArrayList<String>();
            //username
            row.add(Main.users.getUser(rc.getUsername()).getUsernameHTML());
            //cid
            row.add(rc.getCid()+"");
            //time
            row.add(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(rc.getTime()));
            //rank
            row.add(rc.getRank()+"");
            //prating
            int pr= User.getShowRating(rc.getRatingnum() - 1, rc.getPrating());
            if(rc.getRatingnum()==1) {
                row.add("-");
            }else{
                row.add(User.ratingToHTML(pr));
            }
            //rating
            int r= User.getShowRating(rc.getRatingnum(), rc.getRating());
            row.add(User.ratingToHTML(r));
            //change
            int bh=r-pr;
            if(rc.getRatingnum()==1){
                row.add("-");
            }else if(bh>=0){
                row.add(HTML.textb("+"+bh,"green"));
            }else{
                row.add(HTML.textb(""+bh,"red"));
            }
            //ratingnum
            row.add(rc.getRatingnum()+"");
            table.addRow(row);
        }
        return table.HTML();
    }
    public static String computeRating(String cid){
        int cidInt;
        try{
            cidInt=Integer.parseInt(cid);
            List<RatingCase> list= ratingSQL.getRating(Integer.parseInt(cid));
            //System.out.print(list.size());
            if(list.size()!=0){
                return HTML.panelnobody("rating", RatingCaseList(list));
            }else{
                Computer c=new Computer(ContestMain.getContest(cidInt));
                c.comp();
                return c.HTML();
            }
        }catch(NumberFormatException e){
            return "ERROR";
        }
    }
    public static String UserRating(String user){
        List<RatingCase> list=ratingSQL.getRating(user);
        return RatingCaseList(list);
    }
    public static String index(){
        return IndexHTML.HTML();
    }
    public static String recentlyContestTable(int num){
        contestListHTML c=new contestListHTML(num,0);
        c.setList(ContestMain.getRecentlyContests(num+1));
        c.addTableHead("#","名称","开始时间","结束时间","权限","状态","类型");
        return c.tableHTML();
    }
    public static String clockIn(String user,String day,String times){
        if(day==null) day=ClockInSQL.getDay(Tool.now())+"";
        long dint;
        int tint;
        try{
            dint=Long.parseLong(day);
            tint=Integer.parseInt(times);
        }catch(NumberFormatException e){
            dint=ClockInSQL.getDay(Tool.now());
            tint=ClockInSQL.getTimeNum2(Tool.now());
        }
        return ClockInHTML.HTML(user,dint,tint);
    }
    public static String contestCodeCompare(int cid,double f){
        ContestCodeCompare c=new ContestCodeCompare(cid);
//        System.gc();
        return c.HTML(f);
    }
    public static String fileSize(long l){
        if(l<1024) return l+"B";
        if(l<1024*1024) return l*100/1024/100.0+"KB";
        if(l<1024*1024*1024) return l*100/1024/1024/100.0+"MB";
        return l*100/1024/1024/1024/100.0+"GB";
    }
    public static String uploadSample(String pid){
        int pidInt;
        try{
            pidInt=Integer.parseInt(pid);
        }catch(NumberFormatException e){
            return "参数错误";
        }
        Problem p=Main.problems.getProblem(pidInt);
        List<File> files= FILE.getFiles(pidInt);
        TableHTML showFiles=new TableHTML();
        showFiles.setClass("table table-striped table-hover table-condensed");
        showFiles.addColname("文件名","大小","下载","删除");
        for(File f:files){
            showFiles.addRow(f.getName(),fileSize(f.length()),HTML.a("downloadFile.action?pid="+pidInt+"&filename="+f.getName(),"下载"),HTML.a("delFile.action?pid="+pidInt+"&filename="+f.getName(),"删除"));
        }

        FormHTML uploadForm=new FormHTML();
        uploadForm.setAction("uploadFile");
        uploadForm.setCol(2, 10);
        file inf=new file("samplein","输入");
        inf.setAccept(".in");
        uploadForm.addForm(inf);
        file outf=new file("sampleout", "输出");
        outf.setAccept(".out");
        uploadForm.addForm(outf);

        if(p.isSpj()){
            file spj=new file("spj","特判程序");
            inf.setAccept("spj.cpp");
            uploadForm.addForm(spj);
        }

        uploadForm.addForm(new hidden("pid",pid+""));
        uploadForm.setEnctype();
        return panelnobody("样例文件列表 - "+HTML.a("Problem.jsp?pid="+pid,pidInt+" - "+p.getTitle()),showFiles.HTML())+panel("上传",uploadForm.toHTML());
    }
///////////////////////form///////////////////////////////////////
    public static String registerForm(){
        FormHTML f=new FormHTML();
        f.setId("register");
        text f1=new text("username","用户名*");
        f1.setId("username");
        f.addForm(f1);

        text f2=new text("password","密码*");
        f2.setId("password");
        f2.setPass();
        f.addForm(f2);

        text f3=new text("rpass","密码重复*");
        f3.setId("rpass");
        f3.setPass();
        f.addForm(f3);

        text f4=new text("nick","昵称");
        f4.setId("nick");
        f.addForm(f4);

        text f5=new text("school","学校");
        f5.setId("school");
        f.addForm(f5);

        text f6=new text("email","邮箱");
        f6.setId("email");
        f.addForm(f6);

        text f7=new text("motto","签名");
        f7.setId("motto");
        f.addForm(f7);

        f.setCol(2, 10);
        f.setAction("reg.action");

        return f.toHTML();
    }
    public static FormHTML editInfoForm(User user){
        FormHTML f=new FormHTML();
        f.setId("edit");

        text f2=new text("pass","密码*");
        f2.setId("pass");
        f2.setPass();
        f2.setPlaceholder("原密码，必填");
        f.addForm(f2);

        text p1=new text("newpass","新密码");
        p1.setId("newpass");
        p1.setPlaceholder("需要修改密码则输入");
        p1.setPass();
        f.addForm(p1);

        text p2=new text("renewpass","重复新密码");
        p2.setId("renewpass");
        p2.setPass();
        f.addForm(p2);

        text f4=new text("nick","昵称");
        f4.setId("nick");
        f4.setValue(user.getNick());
        f.addForm(f4);

        text name=new text("name","姓名");
        name.setValue(user.getName());
        name.setPlaceholder("注册正式比赛时必填");
        f.addForm(name);

        select sex=new select("gender","性别");
        sex.add(0,"请选择");
        sex.add(1,"男");
        sex.add(2,"女");
        sex.setValue(user.getGender() + "");
        f.addForm(sex);

        text f5=new text("school","学校");
        f5.setId("school");
        f5.setValue(user.getSchool());
        f5.setPlaceholder("注册正式比赛时必填");
        f.addForm(f5);

        text_select f8=new text_select("faculty","学院");
        f8.add("信息科学与工程学院");
        f8.add("数理学院");
        f8.add("国脉信息学院");
        f8.setId("faculty");
        f8.setValue(user.getFaculty());
        f.addForm(f8);

        text_select f9=new text_select("major","专业");
        f9.add("计算机科学与技术");
        f9.add("软件工程");
        f9.add("信息管理与信息系统");
        f9.add("网络工程");
        f9.add("物联网工程");
        f9.add("通信工程");
        f9.add("电子信息工程");
        f9.add("电气工程及其自动化");
        f9.add("电子科学与技术");
        f9.add("建筑电气与智能化");

        f9.setId("major");
        f9.setValue(user.getMajor());
        f.addForm(f9);

        text cla=new text("cla","班级");
        cla.setValue(user.getCla());
        cla.setPlaceholder("注册正式比赛时必填");
        f.addForm(cla);

        text no=new text("no","学号");
        no.setValue(user.getNo());
        no.setPlaceholder("注册正式比赛时必填");
        f.addForm(no);

        text phone=new text("phone","电话");
        phone.setValue(user.getPhone());
        phone.setPlaceholder("注册正式比赛时必填");
        f.addForm(phone);

        text f6=new text("email","邮箱");
        f6.setId("email");
        f6.setValue(user.getEmail());
        f.addForm(f6);

        text f7=new text("motto","个性签名");
        f7.setId("motto");
        f7.setValue(user.getMotto());
        f7.setPlaceholder("说一句话吧...");
        f.addForm(f7);

        f.setCol(2, 10);
        f.setAction("edit.action");

        return f;
    }
    public static String loginForm(){
        FormHTML f=new FormHTML();
        f.setAction("login.action");
        f.setId("loginform");
        text f1=new text("user","用户名");
        f1.setId("username");
        f.addForm(f1);
        text f2=new text("pass","密码");
        f2.setPass();
        f2.setId("password");
        f.addForm(f2);
        f.setCol(2, 10);
        f.setSubmitText("登录");
        f.setSubmitId("loginsubmit");
        return f.toHTML();
    }
    public static String reJudgeForm(int rid){
        return "["+HTML.a("rejudge.action?rid="+rid,"Rejudge")+"]";
    }
    public static String editProblem(String pid,String edit,String num){
        try{
            int pidInt=Integer.parseInt(pid);
            FormHTML f=new FormHTML();
            int numInt;
            try{
                numInt=Integer.parseInt(num);
            }catch(NumberFormatException a){
                numInt=0;
            }
            textarea f1=new textarea("s","");
            if(edit.equals("dis")||edit.equals("input")||edit.equals("output")){
                f1.setUEditor(true);
                f1.setId("problemDis");
            }
            f1.setId("s");
            f1.setValue("");
            f1.setPlaceholder("Input HTML code");
            f1.setCols(100);
            f1.setRows(10);
            f1.setValue(Main.problems.getP(pidInt,edit,numInt));
            f.addForm(f1);

            f.setAction("editproblem.action?pid="+pidInt+"&edit="+edit+"&num="+numInt);
            f.setCol(0,12);
            return f.toHTML()/*+HTML.panel("预览","<div id='view' style='word-break:break-all'></div>")*/;
        }catch(NumberFormatException e){
            return HTML.panel("ERROR","参数错误",null,"danger");
        }
    }
    public static String uploadHead(){
        FormHTML f=new FormHTML();
        file f1=new file("upload","上传头像");
        f1.setId("uploadhead_upload");
        f1.setAccept("image/jpeg,image/png");
        f.addForm(f1);
        f.setCol(2,10);
        f.setPartFrom();
        return f.toHTML();
    }
///////////////////////admin//////////////////////////////////////
    public static String active(boolean bo){
        if(bo) return " class='active'";
        else return "";
    }
    public static String li(String name,String url,String nowpage){
        return "<li role=presentation"+active(url.equals(nowpage))+">"+a("admin.jsp?page="+url,name)+"</li>";
    }
    public static String adminNAV(Permission p,String nowpage){
        if(p==null) return "";
        if(nowpage==null) nowpage="";
        String s="<ul class='nav nav-pills nav-stacked'>";
        if(p.getAddProblem())
            s+=li("新增题目","AddProblem",nowpage);
        if(p.getAddLocalProblem())
            s+=li("本地题目","AddLocalProblem",nowpage);
        if(p.getReJudge())
            s+=li("重判","ReJudge",nowpage);
        if(p.getAddContest())
            s+=li("新增比赛","AddContest",nowpage);
        if(p.getAddDiscuss())
            s+=li("新增通知","AddDiscuss",nowpage);
        if(p.getAddTag())
            s+=li("题目标签","AddTag",nowpage);
        if(Main.loginUser().getUsername().equals("admin"))
            s+=li("评测机","SubmitterInfo",nowpage);
        if(p.getAwardACB())
            s+=li("奖励ACB","AwardACBAdmin",nowpage);
        if(p.getChallengeAdmin())
            s+=li("挑战模式","ChallengeAdmin",nowpage);
        if(p.getUserAdmin())
            s+=li("用户管理","UserAdmin",nowpage);
        if(p.getResetPassword())
            s+=li("密码重置","ResetPassword",nowpage);
        if(p.getPermissionAdmin())
            s+=li("权限管理","PermissionAdmin",nowpage);
        if(p.getViewLog())
            s+=li("查看Log","ViewLog",nowpage);
        if(p.getTeamMemberAdmin())
            s+=li("获奖管理","TeamAward",nowpage);
        s+="</ul>";
        return s;
    }
    public static String adminMAIN(Permission p,String nowpage){
        if(nowpage==null) nowpage="";
        if(p!=null&&nowpage.equals("AddProblem")){
            if(p.getAddProblem())
                return panel("新增/编辑题目", adminAddProblemForm());
            else return panel("ERROR","NO PERMISSION",null,"danger");
        }
        else if(p!=null&&nowpage.equals("ReJudge")){
            if(p.getAddProblem())
                return panel("重判", adminReJudgeForm());
            else return panel("ERROR","NO PERMISSION",null,"danger");
        }
        else if(p!=null&&nowpage.equals("AddContest")){
            if(p.getAddContest())
                return panel("新增/编辑比赛", adminAddContestForm());
            else return panel("ERROR","NO PERMISSION",null,"danger");
        }
        else if(p!=null&&nowpage.equals("AddDiscuss")){
            if(p.getAddDiscuss())
                return panel("新增/编辑通知", adminAddDiscuss());
            else return panel("ERROR","NO PERMISSION",null,"danger");
        }
        else if(p!=null&&nowpage.equals("AddTag")){
            if(p.getAddTag())
                return panel("题目标签管理", adminAddTag());
            else return panel("ERROR","NO PERMISSION",null,"danger");
        }else if(p!=null&&nowpage.equals("SubmitterInfo")){
            return panel("评测机",adminSubmitterInfo());
        }else if(p!=null&&nowpage.equals("PermissionAdmin")){
            return panel("权限管理",adminPerimission());
        }else if(p!=null&&nowpage.equals("AwardACBAdmin")){
            return panel("奖励ACB",adminAwardACB())+panel("比赛奖励",adminAwardContestACB());
        }else if(p!=null&&nowpage.equals("AddLocalProblem")){
            return panel("添加本地题目",adminAddLocalProblem());
        }else if(p!=null&&nowpage.equals("ChallengeAdmin")){
            return panel("挑战模式管理",adminChallengeAdmin());
        }else if(p!=null&&nowpage.equals("ResetPassword")){
            return panel("密码重置",adminResetPassword());
        }else if(p!=null&&nowpage.equals("UserAdmin")){
            return panel("用户管理",adminUser());
        }else if(p!=null&&nowpage.equals("ViewLog")){
            return viewLog();
        }else if(p!=null&&nowpage.equals("TeamAward")){
            return panel("集训队获奖管理", TeamAwardAdmin());
        }
        return panel("Index","管理员界面，点击左边链接进行后台管理");
    }
    public static String adminAddProblemForm(){
        int pid;
        Problem p=null;
        try{
            pid=Integer.parseInt(Main.getRequest().getParameter("pid"));
            p=Main.problems.getProblem(pid);
        }catch(NumberFormatException e){
            pid=-1;
        }
        FormHTML f=new FormHTML();
        f.setAction("addproblem1.action");
        text f1=new text("pid","pid");
        f1.setId("pid");
        f1.setPlaceholder("不填则自动编号");
        if(p!=null) {
            f1.setValue(pid+"");
            f1.setDisabled();
        }
        f1.setDisabled();
        f.addForm(f1);

        select f2=new select("ojid","oj");
        for(int i=0;i<Submitter.ojs.length;i++){
            f2.add(i,Submitter.ojs[i].getName());
        }
        f2.setId("ojid");
        if(p!=null) f2.setValue(p.getOjid()+"");
        f.addForm(f2);

        text f3=new text("ojspid","对应OJ的Pid");
        f3.setId("ojspid");
        if(p!=null) f3.setValue(p.getOjspid());
        f.addForm(f3);

        text f5=new text("author","作者");
        f5.setId("author");
        f5.setPlaceholder("可以为空");
        if(p!=null) f5.setValue(p.getAuthor());
        f.addForm(f5);

        text f4=new text("title",abtn("","#","Title","id='getProblemTitle'"));
        f4.setId("title");
        f4.setPlaceholder("点击前面的按钮自动获得OJ标题");
        if(p!=null) f4.setValue(p.getTitle());
        f.addForm(f4);

        f.addForm(new text_in("<div id='buff'></div>"));
        f.setCol(2,10);
        return f.toHTML()+script("js/addProblem.js");
    }
    public static String adminReJudgeForm(){
//        FormHTML f=new FormHTML();
//        f.setAction("rejudge.action");
//        text f1=new text("rid","RunID");
//        f1.setId("rid");
//        f.addForm(f1);
//        f.setCol(2,10);
        FormHTML f=new FormHTML();
        f.setAction("batchRejudge.action");
        text f1=new text("rid","最早rid");
        f1.setPlaceholder("重判将从这个rid开始启动");
        f1.setId("rid");
        text f2=new text("pid","题目id");
        f2.setPlaceholder("重判的题目");
        f2.setId("pid");
        select s1=new select("status","模式");
        s1.add(1,"只重判AC代码");
        s1.add(2,"除了编译错误全部重判");
        s1.add(3,"全部重判");
        s1.add(4,"重判所有padding和judging");
        f.addForm(f1);
        f.addForm(f2);
        f.addForm(s1);
        return f.toHTML();
    }
    public static String adminAddContestFromJavaScript(){
        return "<script src='js/adminAddContestForm.js'></script>";
    }
    public static String adminAddContestForm(){
        int cid;
        try{
            cid=Integer.parseInt(Main.getRequest().getParameter("cid"));
        }catch(NumberFormatException e){
            cid=-1;
        }
        Contest  c=null;
        if(cid!=-1) c=ContestMain.getContest(cid);
        FormHTML form=new FormHTML();
        if(c==null) form.setAction("addcontest.action");
        else form.setAction("editcontest.action?cid="+cid);
        form.setId("addcontest");
        text f1=new text("name","名称");
        f1.setId("name");
        if(c!=null) f1.setValue(c.getName());
        form.addForm(f1);

        date f2=new date("begintime","开始时间");
        if(c!=null) f2.setValue(c.getBeginDate());
        else f2.setValue(Tool.nowDate());
        form.addForm(f2);

        date f3=new date("endtime","结束时间");
        if(c!=null) f3.setValue(c.getEndTime());
        else f3.setValue(Tool.nowDate());
        form.addForm(f3);

        select f4=new select("type","类型");
        //0public 1password 2private 3register 4register2 5team
        for(int i=0;i<Contest.typenum;i++){
            f4.add(i, Contest.getTypeText(i),Contest.getTypeStyle(i));
        }
        f4.setId("type");
        if(c!=null) f4.setValue(c.getType().getCode()+"");
        else f4.setValue("0");
        form.addForm(f4);

        select f44=new select("Kind","种类");
        //练习、积分、趣味、正式
        f44.add(0,"练习");
        f44.add(1,"积分");
        f44.add(2,"趣味");
        f44.add(3,"正式");
        f44.add(4,"隐藏");
        f44.setId("kind");
        if(c!=null) f44.setValue(c.getKind()+"");
        else f44.setValue("0");
        form.addForm(f44);

        date f55=new date("registerstarttime","注册开始时间");
        if(c!=null)f55.setValue(c.getRegisterstarttime());
        form.addForm(f55);

        date f5=new date("registerendtime","注册截止时间");
        if(c!=null)f5.setValue(c.getRegisterendtime());
        form.addForm(f5);

        text f6=new text("pass","进入密码");
        f6.setAllid("password");
        if(c!=null)f6.setValue(c.getPassword());
        form.addForm(f6);

        text f7=new text("problems","题目列表");
        f7.setPlaceholder("输入题号列表，半角逗号分隔");
        if(c!=null) f7.setValue(c.getProblems());
        form.addForm(f7);

        textarea ta=new textarea("info","比赛信息");
        ta.setId("container");
        ta.setUEditor(true);
        ta.setName("info");
        ta.setPlaceholder("填入HTML代码，将显示在首页");
        if(c!=null) ta.setValue(c.getInfo());
        ta.setRows(10);
        ta.setCols(80);
        form.addForm(ta);

        check ch=new check("computerating","是否计算rating");
        if(c!=null&&c.isComputerating())ch.setChecked();
        form.addForm(ch);

        select f8=new select("rank","Rank模式");
        f8.setId("rank");
        f8.add(0, "ICPC");
        f8.add(1,"ShortCode");
        f8.add(2,"练习模式");
        if(c==null) f8.setValue("0");
        else f8.setValue(c.getRankType()+"");
        form.addForm(f8);

        FormPart f9;
        if(c!=null) f9=new FormPart(RankICPC.getFormHTML(c.getRank()));
        else f9=new FormPart(RankICPC.getFormHTML(null));
        f9.setId("icpcrank");
        form.addForm(f9);

        FormPart f10;
        if(c!=null) f10=new FormPart(RankShortCode.getFormHTML(c.getRank()));
        else f10=new FormPart(RankShortCode.getFormHTML(null));
        f10.setId("shortcode");
        form.addForm(f10);


        FormPart f11;
        if(c!=null) f11=new FormPart(RankTraining.getFormHTML(c.getRank()));
        else f11=new FormPart(RankTraining.getFormHTML(null));
        f11.setId("training");
        form.addForm(f11);


        form.setCol(2, 10);
        return form.toHTML()+ adminAddContestFromJavaScript();
        //return HTML.div("form-group","<input class='class='form-control' type=\"datetime-local\" name=\"user_date\" />");
    }
    public static String adminAddDiscuss(){
        return DiscussHTML.adminAddDiscussForm();
    }
    public static String adminAddTag(){
        return ProblemTagHTML.problemTag();
    }
    public static String adminSubmitterInfo(){
        TableHTML table=new TableHTML();
        table.setClass("table");
        //submitterID,ojid,status,username,info.rid,info.pid,ojsrid,show
        table.addColname("id","oj","s","username","rid","pid","ojsrid","show");
        for(VjSubmitter s:Main.submitter.m.getSubmitters()){
            table.addRow(s.row());
        }
        return table.HTML();
    }
    public static String adminPerimission(){
        TableHTML table=new TableHTML();
        table.setClass("table");
        table.addColname("user","per","admin");
        List<List<String>> ta=Main.users.getPermissionTable();
        for(List<String> row:ta){
            table.addRow(row);
        }
        FormHTML f=new FormHTML();
        text t=new text("user","user");
        f.addForm(t);
        select s=new select("perid","per");
        SQL sql=new SQL("select * from permission");
        ResultSet rs=sql.query();
        try {
            while(rs.next()){
                s.add(rs.getInt(1),rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql.close();
        f.addForm(s);
        f.setAction("addper.action");
        f.setType(1);
        return f.toHTML()+table.HTML();
    }
    public static String adminAwardACB(){
        FormHTML f=new FormHTML();
        f.setAction("awardacb.action");
        f.setSubmitText("确定");
        text t1=new text("user","user");
        f.addForm(t1);
        text t2=new text("acb","ACB");
        f.addForm(t2);
        text t3=new text("text","text");
        f.addForm(t3);
        f.setSubmitText("确定");
        f.setCol(2, 10);
        return f.toHTML();
    }
    public static String adminAwardContestACB(){
        FormHTML f=new FormHTML();
        f.setAction("ContestAward.action");
        text t1=new text("cid","cid");
        f.addForm(t1);

        for(int i=0;i<5;i++){
            text t2=new text("rank["+i+"]","rank");
            text t3=new text("acb["+i+"]","acb");
            f.addForm(t2);
            f.addForm(t3);
        }

        f.setSubmitText("确定");
        f.setCol(2,10);
        return f.toHTML();
    }
    public static String adminAddLocalProblem(){
        int pid=-1;
        Problem p=null;
        problemHTML ph=null;
        try{
            pid=Integer.parseInt(Main.getRequest().getParameter("pid"));
            p=Main.problems.getProblem(pid);
            ph=Main.problems.getProblemHTML(pid);
        }catch(NumberFormatException ignored){}
        FormHTML f=new FormHTML();
        f.setAction("addlocalproblem.action");
        f.setSubmitText("确定");
        text t0=new text("pid","pid");
        t0.setDisabled();
        t0.setValue(pid+"");
        f.addForm(t0);
        text t1=new text("title","标题");
        if(p!=null) t1.setValue(p.getTitle());
        text t2=new text("time","时限(MS)");
        if(ph!=null) t2.setValue(ph.getTime()+"");
        text t3=new text("memory","空间限制(MB)");
        if(ph!=null) t3.setValue(ph.getMemory()+"");

        text f5=new text("author","作者");
        f5.setId("author");
        f5.setPlaceholder("可以为空");
        if(p!=null) f5.setValue(p.getAuthor());

        check isSpj = new check("isSpj","特判 (特判必须在上传数据时上传spj.cpp文件)");
        if(p!=null)isSpj.setValue(p.isSpj()?"true":"false");

        f.addForm(t1,t2,t3,f5,isSpj);
        f.setCol(2,10);
        return f.toHTML();
    }
    public static String adminChallengeAdmin(){
        int id=-1;
        try{
            id=Integer.parseInt(Main.getRequest().getParameter("id"));
        }catch(NumberFormatException ignored){}
        if(id==-1){//模块列表
            //添加模块
            FormHTML f=new FormHTML();
            text t1=new text("id","id");
            f.addForm(t1);
            text t=new text("text","模块名称");
            f.addForm(t);
            f.setAction("addBlock.action");
            f.setType(1);

            //模块列表
            TableHTML table=new TableHTML();
            table.setClass("table");
            table.addColname("#","模块名","总积分","管理");
            for(Integer bid: ChallengeMain.blocks.keySet()){
                Block b=ChallengeMain.blocks.get(bid);
                table.addRow(bid+"",b.getIsEditing()==0?b.getName():HTML.text(b.getName(),"red"),b.getScore()+"",HTML.a("admin.jsp?page=ChallengeAdmin&id="+bid,"admin"));
            }
            return f.toHTML()+table.HTML();
        }else{//模块管理
            Block b=ChallengeMain.blocks.get(id);
            //setEditing
                String setEditing="编辑状态："+HTML.a("setEditing.action?id="+id+"&text="+(1-b.getIsEditing()),b.getIsEditing()==0?"不在编辑":HTML.textb("正在编辑","red"))+"(正在编辑的模块不会自动被开启)";
            //text
                FormHTML textForm=new FormHTML();
                textForm.setAction("editBlockText.action");
                text t=new text("id","id");
                t.setValue(id+"");
                t.setDisabled();
                textarea te=new textarea("text","模块说明");
                te.setPlaceholder("支持HTML代码");
                te.setId("container");
                te.setUEditor(true);
                te.setValue(b.getText());
                textForm.addForm(t);
                textForm.addForm(te);
            //condition list
                TableHTML conditionTable=new TableHTML();
                conditionTable.setClass("table table-bordered");
                conditionTable.addColname("开启条件","删除");
                for(Condition c:b.conditions){
                    conditionTable.addRow(c.toString(),HTML.a("delCondition.action?id="+c.getId(),"删除"));
                }
            //condition add form
                FormHTML addCondition=new FormHTML();
                addCondition.setType(1);
                addCondition.setAction("addCondition.action");
                text t1=new text("id","id");
                t1.setValue(id+"");
                t1.setDisabled();
                addCondition.addForm(t1);
                select type=new select("type","tpye");
                type.setType(1);
                type.add(1,"1");
                type.setValue("1");
                addCondition.addForm(type);
                select block=new select("block","block");
                block.setType(1);
                for(Integer bid: ChallengeMain.blocks.keySet()){
                    Block bb=ChallengeMain.blocks.get(bid);
                    block.add(bid,bid+"-"+bb.getName());
                }
                addCondition.addForm(block);
                text num=new text("num","num");
                addCondition.addForm(num);
                addCondition.setSubmitText("新增条件");
            //problem list
                TableHTML problemList=ChallengeSQL.getProblems(id);
            //addProblemForm
                FormHTML addProblemForm=new FormHTML();
                addProblemForm.setAction("addProblem.action");
                addProblemForm.setType(1);
                text pos=new text("pos","插入位置");
                text bl=new text("block","block");
                bl.setValue(id+"");
                bl.setDisabled();
                text pid=new text("pid","题目编号");
                text score=new text("score","积分");
                addProblemForm.addForm(pos);
                addProblemForm.addForm(bl);
                addProblemForm.addForm(pid);
                addProblemForm.addForm(score);
                addProblemForm.setSubmitText("添加题目");
            return HTML.text("模块【"+b.getName() + "】<br>", 11)+setEditing+textForm.toHTML()+addCondition.toHTML()+conditionTable.HTML()+addProblemForm.toHTML()+problemList.HTML();
        }
    }
    public static String adminResetPassword(){
        FormHTML f=new FormHTML();
        f.setAction("ResetPassword.action");
        f.setSubmitText("确定");
        text t1=new text("username","username");
        f.addForm(t1);
        f.setSubmitText("确定");
        f.setCol(2, 10);
        return f.toHTML();
    }
    public static String adminUser(){
        try{
            String user=Main.getRequest().getParameter("user");
            if(user==null||user.equals("")){
                FormHTML form=new FormHTML();
                text t=new text("user","用户名");
                text page=new text("page","page");
                page.setValue("UserAdmin");
                page.setDisabled();
                form.addForm(t,page);
                form.setAction("admin.jsp");
                form.setSubmitText("进入管理");
                form.setMethod("get");
                return form.toHTML();
            }else{
                User u=Main.users.getUser(user);
                if(u==null){
                    return "用户不存在";
                }else{
                    FormHTML editInfo=editInfoForm(u);
                    editInfo.setAction("AdminEditUser.action");

                    select inTeamStatus=new select("inTeamStatus","队员状态");
                    inTeamStatus.add(0,"非队员");
                    inTeamStatus.add(1,"现役队员");
                    inTeamStatus.add(2,"退役队员");
                    inTeamStatus.setValue(u.getInTeamStatus()+"");

                    text inTeamLv=new text("inTeamLv","队员等级");
                    inTeamLv.setPlaceholder("等级0~6，非队员无效");
                    inTeamLv.setValue(u.getInTeamLv()+"");
                    text username=new text("username","username");
                    username.setValue(user);
                    username.setDisabled();
                    editInfo.addForm(inTeamStatus,inTeamLv,username);
                    editInfo.delForm(2);
                    editInfo.delForm(1);
                    editInfo.delForm(0);
                    return "用户-"+u.getUsernameHTMLNoA()+" 管理"+editInfo.toHTML();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static String viewLog(){
        int id=-1;
        try{
            id=Integer.parseInt(Main.getRequest().getParameter("id"));
        }catch(NumberFormatException ignored){}
        if(id==-1){
            int page=1;
            try{
                page=Integer.parseInt(Main.getRequest().getParameter("pa"));
            }catch(NumberFormatException ignored){}
            return new LogHTML(20,page).HTML();
        }else{
            try{
                Log log=Main.logs.getLog(id);
                String text="time: "+log.getTime()+"\n";
                text+="user: "+log.getSessionUser()+"\n";
                text+="===============\n";
                text+=log.getText();
                return panel("Log id="+id,HTML.pre(text));
            }catch (NullPointerException e){
                return panel("参数错误","参数错误",null,"danger");
            }
        }
    }
    public static String TeamAwardAdmin(){
        int id;
        try{
            id=Integer.parseInt(Main.getRequest().getParameter("id"));
        }catch(NumberFormatException e){
            id=-1;
        }
        TeamMemberAwardInfo info = Main.users.getTeamMemberAwardInfo(id);

        FormHTML form = new FormHTML();
        form.setAction("TeamAward.action");

        form.addForm(new hidden("id",id+""));
        text t1 = new text("username1","用户名1");
        if(info!=null) t1.setValue(info.getUsername1());
        text t2 = new text("username2","用户名2");
        if(info!=null) t2.setValue(info.getUsername2());
        text t3 = new text("username3","用户名3");
        if(info!=null) t3.setValue(info.getUsername3());
        text n1 = new text("name1","姓名1");
        if(info!=null) n1.setValue(info.getName1());
        text n2 = new text("name2","姓名2");
        if(info!=null) n2.setValue(info.getName2());
        text n3 = new text("name3","姓名3");
        if(info!=null) n3.setValue(info.getName3());
        date time = new date("awardTime","日期");
        if(info!=null) time.setValue(info.getTime()+"");

        select s1 = new select("contestLevel","比赛级别");
        for(TeamMemberAwardInfo_ContestLevel it : TeamMemberAwardInfo_ContestLevel.values()){
            s1.add(it.getCode(),it.toString());
        }
        if(info!=null) s1.setValue(info.getContestLevel().getCode()+"");

        select s2 = new select("awardLevel","获得奖项");
        for(TeamMemberAwardInfo_AwardLevel it : TeamMemberAwardInfo_AwardLevel.values()){
            s2.add(it.getCode(),it.toString());
        }
        if(info!=null) s2.setValue(info.getAwardLevel().getCode()+"");
        text des = new text("text","备注");
        if(info!=null) des.setValue(info.getText()+"");

        form.addForm(time,t1,n1,t2,n2,t3,n3,s1,s2,des);

        return form.toHTML();
    }
}
