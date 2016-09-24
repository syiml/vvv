package util.HTML;

import util.Main;
import entity.User;
import entity.Problem;
import util.HTML.modal.modal;
import util.Submitter;

import java.util.List;
import java.util.Map;

/**
 * Created by Syiml on 2015/8/18 0018.
 */
public class ProblemInfo {
    int pid,page;
    public ProblemInfo(int pid,int page){
        this.pid=pid;
        this.page=page;
    }

    public static String getStatusJson(int pid){
        String ss[]={"AC","WA","CE","RE","TLE","MLE","OLE","PE"};
        Map<Integer,Integer> status=Main.status.getProblemStatus(pid);
        String s="[";
        for(int i=2;i<=8;i++){
            if(status.containsKey(i)){
                s+="['"+ss[i-1]+"',"+status.get(i)+"],";
            }else{
                s+="['"+ss[i-1]+"',0],";
            }
        }
        if(status.containsKey(1)){
            s+="['"+ss[0]+"',"+status.get(1)+"]";
        }else{
            s+="['"+ss[0]+"',0]";
        }
        s+="]";
        return s;
    }

    public String ShortCodeTop10(){
        List<String[]> list=Main.status.getProblemShortCodeTop10(pid);
        TableHTML table=new TableHTML();
        table.setClass("table");
        table.addColname("user","nick","codelen");
        for(String[] row:list){
            User u=Main.users.getUser(row[0]);
            table.addRow(u.getUsernameHTML(),u.getNick(),HTML.aNew("Status.jsp?all=1&user="+row[0]+"&pid="+pid+"&result=1",row[1]));
        }
        return HTML.panelnobody("最短代码TOP10", "info", table.HTML());
    }

    public String front(){
        Problem p= Main.problems.getProblem(pid);
        String from;
        if(p.getType()==1){
            from=HTML.aNew(Submitter.ojs[p.getOjid()].getProblemURL(p.getOjspid()), HTML.spannull("badge", "题目将评测在：" + Submitter.ojs[p.getOjid()].getName() + " " + p.getOjspid()));
        }else{
            from=HTML.spannull("badge", "题目将评测在：本OJ");
        }
        String s=HTML.row(HTML.col(12,HTML.text(pid + " - " + p.Title, 10)+HTML.floatRight(from)));
        String  l =HTML.text("通过人数： ",3)+HTML.text(p.totalAcUser+"",8,"GREEN")+"<br>";
                l+=HTML.text("提交人数：",3)+HTML.text(p.totalSubmitUser+"",8,"GREEN")+"<br>";
                l+=HTML.text("提交次数：",3)+HTML.text(p.totalSubmit+"",8,"GREEN")+"<br>";
        if(p.totalSubmit==0){
            l+=HTML.text("通过率：　",3)+HTML.text("还没有人提交这题哦",8)+"<br><br>";
        }else{
            l+=HTML.text("通过率：　",3)+HTML.text(String.format("%.2f",p.totalAcUser*100.0/p.totalSubmit)+"%",8)+"<br><br>";
        }
        if(Main.loginUser()!=null){
            if(Main.users.haveViewCode(Main.loginUser().getUsername(),pid)){
                l+=HTML.text("您已经可以查看该题目的所有代码了 "+HTML.a("Status.jsp?all=1&pid="+pid+"&result=1","点击查看"),4)+"<br>";
            }else{
                int acb=Main.loginUser().getAcb();
                modal m=new modal("buy","确认购买","您当前ACB为："+acb+"<br>"+(acb>=100?"购买将花费100ACB购买本题的代码查看权，是否确定？":"ACB不足100不能购买"),"花100ACB购买查看代码权限");
                if(acb>=100)m.setHavesubmit(true);
                else m.setHavesubmit(false);
                m.setAction("buyviewcode.action?pid="+pid);
                l+=HTML.text(m.toHTMLA(),4)+"<br>";
            }
        }
        String r="<div id='status_info'></div><script>$('#status_info').load('module/problemInfo.jsp?pid="+pid+"');</script>";
        return HTML.div("userindex",s+HTML.row(HTML.col(5,l)+HTML.col(7,r)));
    }
    public String HTML(){
        return front()+HTML.row(HTML.col(4,ShortCodeTop10())+HTML.col(8, ProblemTagHTML.problemTag(pid, page)));
    }
}
