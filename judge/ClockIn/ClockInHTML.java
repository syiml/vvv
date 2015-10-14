package ClockIn;

import Main.Main;
import Main.User.Permission;
import Main.User.User;
import Tool.HTML.FromHTML.FormHTML;
import Tool.HTML.FromHTML.date.date;
import Tool.HTML.FromHTML.select.select;
import Tool.HTML.FromHTML.text.text;
import Tool.HTML.HTML;
import Tool.HTML.TableHTML.TableHTML;
import Tool.HTML.modal.modal;
import action.ClockIn;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syiml on 2015/7/27 0027.
 */
public class ClockInHTML {
    static int shownum=50;
    static String timeShow(long a){
        long h=(a/1000/60/60);
        long m=a%(1000*60*60)/1000/60;
        return (h<10?"0"+h:h)+":"+(m<10?"0"+m:m);
    }
    private static String message(){
        String ret="签到说明：<br>"+HTML.textb("签到即为暑假考勤，考勤结果较差的队员优先淘汰，所以请详细阅读以下签到时间并牢记","blue")+"<br>";
        ret+="<br>每日签到时间：<br>";
        int n=ClockInSQL.times.size();
        for(int i=0;i<n;i++){
            ret+="第"+(i+1)+"次签到时间段:"+timeShow(ClockInSQL.times.get(i).get(0))
                    + "~" + timeShow(ClockInSQL.times.get(i).get(2))+"  "
                    +timeShow(ClockInSQL.times.get(i).get(1))+"后为迟到<br>";
        }
        ret+="如果对签到结果有异议，请联系管理员。<br>";
        ret+="PS：正常签到时间比要求到机房时间略晚，是为了给时间开机，所以因为迟到而没赶上开机签到的，概不受理<br>";

        ret+="<br>训练时间表：<br>";
        ret+="早上：9：00~11:30 下午：14:00~17:30 晚上：18:30~22:00<br>如有调整，以通知为准";
        return ret;
    }
    private static String pageHTML(String user,long day,int times){
        String s="";
// return -1;//未登录
// return -3;//非机房IP
// return -4;//已经签到
// return -2;//不在签到时段
        int ret=ClockInSQL.mustClockIn();
        s+="<h3>";
        if(ret>=0){
            s+=HTML.a("clockin.action","立即签到");
        }else if(ret==-1){
            s+="要签到请先"+HTML.a("Login.jsp","登录");
        }else if(ret==-2){
            s+="当前不在签到时段";
        }else if(ret==-3){
            s+="到机房才能签到";
        }else if(ret==-4){
            s+="已经签到";
        }else{
            s+="错误";
        }
        s+="</h3>";
        String url="ClockIn.jsp?";
        if(user!=null) url+="user="+user+"&";
        String preurl;
        if(ClockInSQL.getDay(Main.now())<day||(ClockInSQL.getDay(Main.now())==day&&ClockInSQL.getTimeNum2(Main.now())<=times)){
            preurl=null;
        }else{
            if(times==2){
                preurl=url+"day="+(day+1)+"&times=0";
            }else{
                preurl=url+"day="+(day)+"&times="+(times+1);
            }
        }
        String nexturl;
        if(day<16644L||(day==16644L&&times==0)){
            nexturl=null;
        }else{
            if(times!=0){
                nexturl=url+"day="+day+"&times="+(times-1);
            }else{
                nexturl=url+"day="+(day-1)+"&times=2";
            }
        }
        String ss="<div class='btn-toolbar' role='toolbar'>"+HTML.btngroup(HTML.abtn("sm",url,"TOP",""));

        String nowpageString=new Timestamp((day * (1000 * 60 * 60 * 24)-(1000*60*60*8))).toString().substring(5,10)+
                (times==0?"上午":times==1?"下午":"晚上");
        ss+=HTML.btngroup(
                HTML.abtn("sm",preurl,"＜",preurl==null?"disabled":"")+
                HTML.abtn("sm",null,nowpageString,"")+
                HTML.abtn("sm",nexturl,"＞",nexturl==null?"disabled":"")
        );
        ss+="</div>";

        FormHTML f=new FormHTML();
        f.setType(1);
        text t=new text("user", "user");
        t.setValue(user);
        f.addForm(t);
        f.setAction("ClockIn.jsp");

        return message()+"<br>"+s+"<br>"+HTML.floatLeft(ss)+HTML.floatRight(f.toHTML());
    }
    public static  String adminForm(){
        FormHTML form=new FormHTML();

        text s=new text("username","用户");
        s.setId("username");
//        s.setType(1);
        form.addForm(s);

        date d=new date("ttime","日期");
        d.setValue(Main.now().toString().substring(0, 10));
        form.addForm(d);

        select  sel2 = new select("todytimes","时间段");
//        sel2.setType(1);
        sel2.add(0, "上午");
        sel2.add(1,"下午");
        sel2.add(2,"晚上");
        form.addForm(sel2);

        select sel=new select("sign","状态");
//        sel.setType(1);
        sel.add(0,"请假");
        sel.add(1,"正常");
        sel.add(2,"迟到");
        sel.setValue("0");
        form.addForm(sel);

        text info=new text("info","请假信息");
        info.setPlaceholder("仅对请假有效");
        form.addForm(info);
//        form.setType(1);
        form.setAction("adminclockin.action");
        form.setCol(2,10);
//        modal m=new modal("adminadd","添加签到记录",,"添加记录");
        return form.toHTML();
    }
    public static String HTML(String user,long day,int times){
        Permission per=Main.loginUserPermission();
        if(user!=null&&user.equals("")) user=null;
        List<ClockInRecord> list;
        if(user==null){
            list=ClockInSQL.get(day,times);
//            if(list.size()==0&&day>=16644L){
//                if(times!=0){
//                    return HTML(null,day,times-1);
//                }else{
//                    return HTML(null,day-1,2);
//                }
//            }
        }
        else  list=ClockInSQL.getWithUser(user);
        String p=pageHTML(user, day, times);
        TableHTML table=new TableHTML();
        table.setClass("table table-condensed table-hover");
        table.addColname("用户名", "昵称", "签到时间","签到IP", "状态");
        if(per.getClockIn()) table.addColname("管理");
        int rank=1;
        for(ClockInRecord cir:list){
            User u=Main.users.getUser(cir.username);
            List<String> row=new ArrayList<String>();
            row.add(u.getUsernameHTML());
            row.add(u.getNick());
            row.add(cir.time.toString().substring(0, 19));
            row.add(cir.ip);
            if(!cir.sign.substring(0,2).equals("请假")){
                row.add((cir.todytimes==0?"上午":cir.todytimes==1?"下午":"晚上") + "第"+rank+"名签到，状态：" + cir.sign.substring(0,2));
                rank++;
            }else{
                row.add((cir.todytimes==0?"上午":cir.todytimes==1?"下午":"晚上") + cir.sign.substring(0,2));
            }

            if(per.getClockIn()) row.add(HTML.a("delclockin.action?id="+cir.id,"删除"));
            table.addRow(row);
        }
        return HTML.panel("签到"+HTML.floatRight(HTML.a("dmc.jsp","点名册")),p+table.HTML())
                +(per.getClockIn()?HTML.panel("添加签到记录",adminForm()):"");
    }
    static List<String> showusers;
    static{//要统计的用户列表，可以存起来
        showusers=new ArrayList<String>();
//        showusers.add("admin");
        showusers.add("huzhenbo");
        showusers.add("hzz1346");
        showusers.add("723264836");
        showusers.add("3131906112");
        showusers.add("3131903219");
        showusers.add("luoling");
        showusers.add("qpyghb");
        showusers.add("WRQ520");
        showusers.add("boylh");
        showusers.add("shanks");
        //showusers.add("3141905203");
        showusers.add("sunset_sparkle");
        showusers.add("3141906223");
        //showusers.add("3141911104");
        showusers.add("FJUTACM1408");
        showusers.add("3141911209");
        showusers.add("377834606");
        showusers.add("FJUTACM1414");
        showusers.add("FJUTACM1415");
        showusers.add("FJUT1416");
        showusers.add("FJUTACM1417");
        showusers.add("3141906113");
        //showusers.add("3141906110");
        showusers.add("3141906116");
        showusers.add("lxr12345");
        showusers.add("3141911211");
        showusers.add("gagaga");
    }
    public static String HTMLtable(){
        TableHTML table=new TableHTML();
        table.setClass("table table-bordered table-condensed table-hover");
        table.addColname("#");
        table.addCl(0,0,"width100");
        for(int i=0;i<showusers.size();i++){
            table.addColname(HTML.a("UserInfo.jsp?user="+showusers.get(i),Main.users.getUser(showusers.get(i)).getNick()));
            table.addCl(0, i + 1, "width10");
        }

        List<Integer> l1=new ArrayList<Integer>();//正常
        List<Integer> l2=new ArrayList<Integer>();//迟到
        List<Integer> l3=new ArrayList<Integer>();//请假
//        List<Integer> l4=new ArrayList<Integer>();//旷课
        List<Integer> l5=new ArrayList<Integer>();//积分
        List<List<String>> tablerows=new ArrayList<List<String>>();
        Long beginday=16644L;
        Long day=(Main.now().getTime()+(1000*60*60*8))/(1000*60*60*24);
        for(int i=0;i<showusers.size();i++){
            l1.add(0);
            l2.add(0);
            l3.add(0);
//            l4.add(0);
            l5.add(0);
            String user=showusers.get(i);
            int nowz=2;
            List<ClockInRecord> list=ClockInSQL.getClockInStatus(user);
            long dday=day;
            int nnowz=nowz;
            for(ClockInRecord c:list){
                if(beginday * (1000*60*60*24)-(1000*60*60*8)<=c.time.getTime()){
                    int rownum=(int)(day-c.getDay())*3+(2-c.todytimes);
                    if(rownum<0) continue;
//                    System.out.println("rownum:"+rownum);
                    int x=tablerows.size();
                    while(x<=rownum){
                        tablerows.add(new ArrayList<String>());
                        tablerows.get(x).add(new Timestamp((dday * (1000 * 60 * 60 * 24)-(1000*60*60*8))).toString().substring(5,10)+
                                (nnowz==0?"上午":nnowz==1?"下午":"晚上"));
                        for(int k=1;k<=showusers.size(); k++){
                            tablerows.get(x).add("");
                        }
                        x++;
                        nnowz--;
                        if(i==0&&dday%7==3){
                            //System.out.println("dday:"+dday +"  x:"+x);
                            table.addCl(x+4,-1,"active");
                        }
                        if(nnowz<0){
                            nnowz=2;
                            dday-=1;
                        }
                    }
                    if(c.sign.equals("正常")){
                        l1.set(i,l1.get(i) + 1);
                        l5.set(i,l5.get(i)+3);
                        tablerows.get(rownum).set(i+1,"✔");
                        table.addCl(rownum + 5,i+1,"success");
                    }else if(c.sign.equals("迟到")){
                        l2.set(i, l2.get(i) + 1);
                        l5.set(i, l5.get(i) + 2);
                        tablerows.get(rownum).set(i + 1, "○");
                        table.addCl(rownum + 5, i + 1, "warning");
                    }else if(c.sign.substring(0,2).equals("请假")){
                        l3.set(i,l3.get(i)+1);
                        l5.set(i, l5.get(i)+ 1);
                        modal jia=new modal("jia_"+rownum+"_"+i,"请假信息",c.sign.substring(2),"假");
                        jia.setHavesubmit(false);
                        tablerows.get(rownum).set(i + 1,jia.toHTMLA());
                        table.addCl(rownum+5,i+1,"danger");
                    }
                }
            }
        }
        List<String> row1=new ArrayList<String>();
        row1.add("正常 +3");
        for(int i=0;i<l1.size();i++){
            row1.add(l1.get(i)+"");
        }
        List<String> row2=new ArrayList<String>();
        row2.add("迟到 +2");
        for(int i=0;i<l2.size();i++){
            row2.add(l2.get(i)+"");
        }
        List<String> row3=new ArrayList<String>();
        row3.add("请假 +1");
        for(int i=0;i<l3.size();i++){
            row3.add(l3.get(i)+"");
        }
//        List<String> row4=new ArrayList<String>();
//        row4.add("旷课");
//        for(int i=0;i<l4.size();i++){
//            row4.add(l4.get(i)+"");
//        }
        List<String> row5=new ArrayList<String>();
        row5.add("考勤得分");
        for(int i=0;i<l5.size();i++){
            row5.add(l5.get(i)+"");
        }
        table.addRow(row5);
        table.addRow(row1);
        table.addRow(row2);
        table.addRow(row3);
//        table.addRow(row4);
        for(int i=0;i<tablerows.size();i++){
            table.addRow(tablerows.get(i));
        }

        return table.HTML();
    }
}
