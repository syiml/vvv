package entity.rank.RankShortCode;

import util.Main;
import entity.User;
import entity.rank.Rank;
import entity.rank.RankICPC.Form_text_select_inline;
import entity.Contest;
import entity.RegisterUser;
import util.rating._rank;
import entity.Result;
import entity.Status;
import util.HTML.FromHTML.FormHTML;
import util.HTML.FromHTML.text.text;
import util.HTML.HTML;
import util.HTML.TableHTML;
import util.HTML.modal.modal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Syiml on 2015/7/11 0011.
 */
public class RankShortCode extends Rank {
    int pnum;
    List<user> list;
    int cid;
    public int chengfa=0;

    public int type_1=1,type_2=1,type_3=1;//0数量、1比率（百分比）（百分比按有提交的user数量计算？）
    public int m1=10,m2=30,m3=60;

    private static String css="<style>" +
            ".rank_1{" +
            "    background: gold;}"+
            ".rank_2{" +
            "    background:#c0c0c0;}" +
            ".rank_3{" +
            "    background: #b87333;}" +
            "</style>";

    public RankShortCode(Contest c){
        this.list=new ArrayList<user>();
        this.cid=c.getCid();
        this.chengfa=0;
        this.pnum=c.getProblemNum();
//        List<statu> list=Main.status.getStauts(cid);
        for(int i=0;i<c.getUserNum();i++){
            add(c.getUser(i),c);
        }
//        for (statu s : list) {
//            add(s, c);
//        }
    }
    public void add(RegisterUser u,Contest c){
        //System.out.println("in->"+u.getUsername());
        if(u.getStatu()==1){//1是正式，2是星号
            list.add(new user(u.getUsername(),c.getProblemNum(),true,chengfa));
        }else if(u.getStatu()==2){
            list.add(new user(u.getUsername(),c.getProblemNum(),false,chengfa));
        }
    }

    public List<Integer> getminlen(){
        List<Integer> lens=new ArrayList<Integer>();
        for(int i=0;i<pnum;i++){
            int minlen=10000000;
            for(int j=0;j<list.size();j++){
                int len=list.get(j).codelen.get(i);
                if(len!=0){
                    minlen=Math.min(minlen,len);
                }
            }
            lens.add(minlen);
        }
        return lens;
    }
    public String toHTML(){
        List<Integer> minlens=getminlen();
        User loginuser=Main.loginUser();
        Collections.sort(list);
        TableHTML table=new TableHTML();
        table.setClass("table table-bordered table-hover table-condensed");
        table.addColname("Rank", "User", "Nick", "S", "L");
        for(int j=0;j<pnum;j++){
            table.addColname(HTML.a("#P" + j, (char) (j + 'A') + ""));
        }
        int rank=1,trank=1;
        int allusernum=list.size();
        for(int i=0;i<list.size();i++){
            user u=list.get(i);
            if(i!=0){
                if(u.compareTo(list.get(i-1))!=0){
                    trank=rank;
                }
            }else{
                rank=1;
                trank=1;
            }
            if(u.valid) rank++;
            String rankstring;
            if(u.valid) rankstring=trank+"";
            else rankstring="*";
            List<String> row=new ArrayList<String>();
            User us=Main.users.getUser(u.username);
            row.add(rankstring);
            if(u.valid&&u.getAcNum()>0){
                if((type_1==1&&1.0*trank/allusernum<1.0*m1/100)||(type_1==0&&trank<=m1)){
                    table.addCl(i+1,0,"rank_1");
                }else if((type_2==1&&1.0*trank/allusernum<1.0*m2/100)||(type_2==0&&trank<=m2)){
                    table.addCl(i+1,0,"rank_2");
                }else if((type_3==1&&1.0*trank/allusernum<1.0*m3/100)||(type_3==0&&trank<=m3)){
                    table.addCl(i+1,0,"rank_3");
                }
            }
            if(us!=null){
                row.add(us.getUsernameHTML());
            }else{
                row.add(u.username);
            }
            if(loginuser!=null&&u.username.equals(loginuser.getUsername())){
                table.addCl(i+1,1,"info");
            }
            if(us!=null) {
                row.add(us.getNick());
            }else{
                row.add(HTML.textb("未注册","red"));
            }
            row.add(HTML.a("#S"+u.username,u.getAcNum()+""));
            row.add(u.getCodelen(chengfa)+"");
            for(int j=0;j<pnum;j++){
                if(u.codelen.get(j)>0){
                    row.add(u.codelen.get(j)+(u.wrongtime.get(j)==0?"":"(-"+u.wrongtime.get(j)+")"));
                    if(u.codelen.get(j).equals(minlens.get(j))){
                        table.addCl(i+1,j+5,"fb");
                    }else table.addCl(i + 1, j + 5, "success");
                }else if(u.wrongtime.get(j)>0){
                    row.add("-"+u.wrongtime.get(j)+"");
                    table.addCl(i + 1, j + 5, "danger");
                }else{
                    row.add("");
                }
            }
            table.addRow(row);
        }
        //(String id,String title,String body,String btnlabel){
        modal m=new modal("rankrule","本场短码赛规则",getRuleHTML(),"查看详细规则");
        m.setBtnCls("link btn-xs");
        m.setHavesubmit(false);
        return css+HTML.panelnobody("Shortest Code Rank"+HTML.floatRight(m.toHTML()),table.HTML());
    }
    public void add(Status s,Contest c){
        if(s.getResult()== Result.DANGER||
                s.getResult()==Result.PENDING ||
                s.getResult()==Result.JUDGING||
                s.getResult()==Result.ERROR)return ;
        if(s.getCid()!=c.getCid()) return;
        int j;
        for(j=0;j<this.list.size();j++){
            if(this.list.get(j).username.equals(s.getUser())){
                this.list.get(j).addres(c.getcpid(s.getPid()),s.getResult(),s.getCodelen());
                break;
            }
        }
        if(j==this.list.size()){
            this.list.add(new user(s.getUser(),pnum,true,chengfa));
            this.list.get(j).addres(c.getcpid(s.getPid()),s.getResult(),s.getCodelen());
        }
    }//处理rejudge
    public _rank get_rank(){
        _rank r=new _rank();
        Collections.sort(list);
        int rank=1,trank=1;
        for(int i=0;i<list.size();i++){
            user u=list.get(i);
            if(i!=0){
                if(u.compareTo(list.get(i-1))!=0){
                    trank=rank;
                }
            }else{
                rank=1;
                trank=1;
            }
            if(u.valid) rank++;
            if(u.valid){
                r.add(trank,u.username);
            }
        }
        return r;
    }
    public String getRuleHTML(){
        return "短码赛规则：顾名思义就是使你的代码尽可能的短。<br>"+
               "两个人先比较AC的题目数量，AC题目数量多的排名较靠前<br>"+
               "AC题目相同，则计算两者AC代码的总长度，总长度是指代码中所有字符的数量，但是"+HTML.textb("不包括空格和回车以及制表符","red")+"</b>。<br>"+
               "多个AC提交，则取代码长度最短的计算总长度，每题只计算一个AC代码长度<br>"+
                ((chengfa!=0)?"另外每次非AC的提交都会惩罚"+chengfa+"的代码长度":"错误的代码不计算在成绩内");
    }

    public static FormHTML getFormHTML(Rank r){
        FormHTML f= new FormHTML();
        f.setPartFrom();

        text f1=new text("shortcode_chengfa","惩罚");
        if(r instanceof RankShortCode) f1.setValue(((RankShortCode)r).chengfa+"");
        else f1.setValue("0");
        f.addForm(f1);

        Form_text_select_inline f2=new Form_text_select_inline("shortcode_m1","金奖");
        if(r instanceof RankShortCode) f2.setValue(((RankShortCode)r).m1+"",((RankShortCode) r).type_1+"");
        else f2.setValue("10","1");
        f.addForm(f2);

        Form_text_select_inline f3=new Form_text_select_inline("shortcode_m2","银奖");
        if(r instanceof RankShortCode) f3.setValue(((RankShortCode)r).m2+"",((RankShortCode) r).type_2+"");
        else f3.setValue("30","1");
        f.addForm(f3);

        Form_text_select_inline f4=new Form_text_select_inline("shortcode_m3","铜奖");
        if(r instanceof RankShortCode) f4.setValue(((RankShortCode)r).m3+"",((RankShortCode) r).type_3+"");
        else f4.setValue("60","1");
        f.addForm(f4);

        return f;
    }
}
