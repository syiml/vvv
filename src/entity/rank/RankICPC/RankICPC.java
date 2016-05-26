package entity.rank.RankICPC;

import entity.*;
import servise.ContestMain;
import util.Main;
import entity.rank.Rank;
import util.Tool;
import util.rating._rank;
import util.HTML.FromHTML.FormHTML;
import util.HTML.FromHTML.text.text;
import util.HTML.HTML;
import util.HTML.TableHTML;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2015/5/26.
 */
public class RankICPC extends Rank {
    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }
    public void setType_1(int type_1) {
        this.type_1 = type_1;
    }
    public void setType_3(int type_3) {
        this.type_3 = type_3;
    }
    public void setType_2(int type_2) {
        this.type_2 = type_2;
    }
    public void setM1(int m1) {
        this.m1 = m1;
    }
    public void setM2(int m2) {
        this.m2 = m2;
    }
    public void setM3(int m3) {
        this.m3 = m3;
    }

    int penalty = 20;//罚时默认20分钟
    List<user> list;
    List<String> fb;
    int cid;
    Contest contest = null;
    int type_1,type_2,type_3;//0数量、1比率（百分比）（百分比按有提交的user数量计算？）
    int m1=1,m2=2,m3=3;
    private static String css="<style>" +
            ".rank_1{" +
            "    background: gold;}"+
            ".rank_2{" +
            "    background:#c0c0c0;}" +
            ".rank_3{" +
            "    background: #b87333;}" +
            "</style>";
    public RankICPC(){}
    public RankICPC(Contest c){
        cid=c.getCid();
        contest = c;
        list=new ArrayList<user>();
        fb=new ArrayList<String>();
        for(int i=0;i<c.getUserNum();i++){
            add(c.getUser(i),c);
        }
//        for(int i=0;i<c.getStatusNum();i++){
//            add(c.getStatus(i),c);
//        }
        //System.out.println("ICPC rank create done");
    }
    private String color(int r,int number1,int number2,int number3){
        if(r<=number1) return "rank_1";
        if(r<=number2) return "rank_2";
        if(r<=number3) return "rank_3";
        return "";
    }
    public void add(statu s,Contest c){
        //去掉无效结果
        if(s.getResult()==Result.DANGER||
           s.getResult()==Result.PENDING ||
           s.getResult()==Result.JUDGING||
           s.getResult()==Result.ERROR) return;
        if(s.getCid()!=cid) return;
        //System.out.println("->"+s.getRid());
        long time = s.getSbmitTime().getTime() - c.getBeginDate().getTime();
        String u=s.getUser();
        int size=list.size(),i;
        user us;
        for(i=0;i<size;i++){
            us=list.get(i);
            if(us!=null){
                if(us.username.equals(u)) break;
            }
        }
        if(i==size){
            user _user = new user(u, true,c.getProblemNum());
            User user = Main.users.getUser(_user.username);
            if(user!=null){
                _user.showUsername = (user.getUsernameHTML());
            }else{
                _user.showUsername = (_user.username);
            }
            if(user!=null) {
                _user.showNick =(user.getNick());
            }else{
                _user.showNick =(HTML.textb("未注册","red"));
            }
            list.add(_user);
        }
        us=list.get(i);
        us.add(s,time,penalty,c);
    }
    public String toHTML(){
        getfb();
        Collections.sort(list);
        int size=list.size();
        int Rank=1;
        TableHTML table=new TableHTML();
        table.setClass("table table-bordered table-hover table-condensed");
        tableHeadHTML(table);
        String loginUserName = null;
        if(contest.getType() == Contest_Type.TEAM_OFFICIAL){
            Object trueLoginUser = Main.getSession().getAttribute(Contest.TRUE_USERNAME+cid);
            if(trueLoginUser!=null){
                loginUserName = (String)trueLoginUser;
            }
        }else {
            User loginUser = Main.loginUser();
            if(loginUser != null){
                loginUserName = loginUser.getUsername();
            }
        }
        int trueSize = 0;
        for (user aList : list) {
            if (aList.valid) {
                trueSize++;
            }
        }
        int number1=0,number2=0,number3=0;
        if(type_1 == 0){
            number1 = m1;
        }else{
            number1 = (int)(trueSize*1.0*m1/100);
        }
        if(type_2 == 0){
            number2 = m2;
        }else{
            number2 = (int)(trueSize*1.0*m2/100);
        }
        if(type_3 == 0){
            number3 = m3;
        }else{
            number3 = (int)(trueSize*1.0*m3/100);
        }

        for(int i=0;i<size;i++){
            List<String> row=new ArrayList<String>();
            user us=list.get(i);
            if(us.valid){
                row.add(Rank+"");
                String cl=color(Rank,number1,number2,number3);
                if(us.submitnum==0) cl = "";
                if(!cl.equals(""))table.addCl(i+1,0,cl);
                Rank++;
            }else{
                row.add("*");
            }
            if(loginUserName!=null&&us.username.equals(loginUserName)){
                table.addCl(i+1,1,"info");
            }
            row.add(us.showUsername);
            row.add(us.showNick);
            row.add(HTML.a("#S"+us.username,us.submitnum+""));
            row.add(us.penalty/1000/60+"");
            int pnum= ContestMain.getContest(cid).getProblemNum();
            for(int j=0;j<pnum;j++){
                //s+=us.result(j);
                row.add(us.result(j));
                if(fb.get(j).equals(us.username)){
                    table.addCl(i+1,j+5,"fb");
                }else{
                    String cl=us.resultclass(j);
                    if(!"".equals(cl)){
                        table.addCl(i+1,j+5,cl);
                    }
                }
            }
            table.addRow(row);
        }
        return css+HTML.panelnobody("Icpc Rank", table.HTML());
    }
    public _rank get_rank(){
        _rank r=new _rank();
        Collections.sort(list);
        int rank=0;
        user pu=null;
        for (user u : list) {
            //遍历所有 非星号 用户计算排名并返回
            if (u.valid) {
                if (pu == null || !(u.getSubmitnum() == pu.getSubmitnum() && u.getPenalty() == pu.getPenalty())) {
                    rank++;
                }
                r.add(rank, u.username);
                pu = u;
            }
        }
        return r;
    }
    public String getRuleHTML(){
        return "";
    }
    public static FormHTML getFormHTML(Rank r){
        FormHTML f= new FormHTML();
        f.setPartFrom();

        text f1=new text("icpc_penalty","罚时");
        f1.setValue("20");
        f1.setPlaceholder("错误一次的罚时（分钟）");
        if(r instanceof RankICPC) f1.setValue(((RankICPC)r).penalty+"");
        f.addForm(f1);

        Form_text_select_inline f2=new Form_text_select_inline("icpc_m1","金奖");
        if(r instanceof RankICPC) f2.setValue(((RankICPC)r).m1+"",((RankICPC) r).type_1+"");
        else f2.setValue("10","1");
        f.addForm(f2);

        Form_text_select_inline f3=new Form_text_select_inline("icpc_m2","银奖");
        if(r instanceof RankICPC) f3.setValue(((RankICPC)r).m2+"",((RankICPC) r).type_2+"");
        else f3.setValue("30","1");
        f.addForm(f3);

        Form_text_select_inline f4=new Form_text_select_inline("icpc_m3","铜奖");
        if(r instanceof RankICPC) f4.setValue(((RankICPC)r).m3+"",((RankICPC) r).type_3+"");
        else f4.setValue("60","1");
        f.addForm(f4);

        return f;
    }
    public void getfb(){
        fb.clear();
        int pnum=ContestMain.getContest(cid).getProblemNum();
        for(int i=0;i<pnum;i++){
            Long mintime=null;
            String user="";
            for (user aList : list) {
                Long time = aList.getSubmittime(i);
                if (time != null && time > 0 && (mintime == null || time < mintime)) {
                    user = aList.username;
                    mintime = time;
                }
            }
            //System.out.println("fb"+i+":"+user);
            fb.add(user);
        }
    }
    public void add(RegisterUser u,Contest c){
        //System.out.println("in->"+u.getUsername());
        user _user = null;
        if(u.getStatu()==1){//1是正式，2是星号
            _user = new user(u.getUsername(),true,c.getProblemNum());
        }else if(u.getStatu()==2){
            _user = new user(u.getUsername(),false,c.getProblemNum());
        }
        if(_user != null) {
            if(c.getType() == Contest_Type.TEAM_OFFICIAL){
                //Tool.log("123321");
                _user.showUsername = u.getShowUserName();
                _user.showNick = u.getShowNick();
            }else{
                _user.showUsername = u.getShowUserName();
                _user.showNick = u.getShowNick();
            }
            list.add(_user);
        }
    }
    private void tableHeadHTML(TableHTML table){
        table.addColname("Rank");
        table.addColname("User");
        table.addColname("Nick");
        table.addColname("S");
        table.addColname("T");
        int pnum=ContestMain.getContest(cid).getProblemNum();
        for(int j=0;j<pnum;j++){
            if(pnum>26){
                table.addColname(HTML.a("#P"+j,j+1+""));
            }else{
                table.addColname(HTML.a("#P"+j,(char)(j+'A')+""));
            }
        }
    }
}
