package entity.rank.RankShortCode;

import entity.rank.RankBaseUser;
import util.Main;
import entity.User;
import entity.rank.Rank;
import entity.rank.RankICPC.Form_text_select_inline;
import entity.Contest;
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
public class RankShortCode extends Rank<user> {
    public int chengfa=0;
    public int type_1=1,type_2=1,type_3=1;//0数量、1比率（百分比）（百分比按有提交的user数量计算？）
    public int m1=10,m2=30,m3=60;
    int pnum;
    int cid;
    private List<Integer> minlens;

    public RankShortCode(Contest c){
        super(c);
        this.list=new ArrayList<user>();
        this.cid=c.getCid();
        this.chengfa=0;
        this.pnum=c.getProblemNum();
//        List<statu> list=Main.status.getStauts(cid);
        for(int i=0;i<c.getUserNum();i++){
            add(c.getUser(i),c,user.class);
        }
//        for (statu s : list) {
//            add(s, c);
//        }
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

    private List<Integer> getminlen(){
        List<Integer> lens=new ArrayList<Integer>();
        for(int i=0;i<pnum;i++){
            int minlen=10000000;
            for (user aList : list) {
                int len = aList.codelen.get(i);
                if (len != 0) {
                    minlen = Math.min(minlen, len);
                }
            }
            lens.add(minlen);
        }
        return lens;
    }

    public String toHTML(){
        minlens = getminlen();
        return super.toHTML();
    }

    @Override
    protected String getCellByPid(int row, user u, int pid) {
        if(u.codelen.get(pid)>0){
            if(u.codelen.get(pid).equals(minlens.get(pid))){
                addProblemCl(row,pid,"fb");
            }else addProblemCl(row,pid, "success");

            return u.codelen.get(pid)+(u.wrongtime.get(pid)==0?"":"(-"+u.wrongtime.get(pid)+")");
        }else if(u.wrongtime.get(pid)>0){
            addProblemCl(row,pid, "danger");
            return "-"+u.wrongtime.get(pid);
        }else{
            return "";
        }
    }

    @Override
    protected List<String> getExtraTableCol() {
        return getStringList("S","L");
    }

    @Override
    public String getCellByHead(int row, user u, String colName) {
        if(colName.equals("S")){
            return HTML.a("#S"+u.username,u.getAcNum()+"");
        }else if(colName.equals("L")){
            return u.getCodelen(chengfa)+"";
        }
        return null;
    }

    @Override
    public String getTitle() {
        modal m=new modal("rankrule","本场短码赛规则",getRuleHTML(),"查看详细规则");
        m.setBtnCls("link btn-xs");
        m.setHavesubmit(false);
        return "Shortest Code Rank"+HTML.floatRight(m.toHTML());
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
            user u = new user();
            u.init(s.getUser(),pnum,1,chengfa);
            User us = Main.users.getUser(s.getUser());
            u.showUsername = us.getUsernameHTML();
            u.showNick = us.getNick();
            this.list.add(u);
            this.list.get(j).addres(c.getcpid(s.getPid()),s.getResult(),s.getCodelen());
        }
    }//处理rejudge

    public String getRuleHTML(){
        return "短码赛规则：顾名思义就是使你的代码尽可能的短。<br>"+
               "两个人先比较AC的题目数量，AC题目数量多的排名较靠前<br>"+
               "AC题目相同，则计算两者AC代码的总长度，总长度是指代码中所有字符的数量，但是"+HTML.textb("不包括空格和回车以及制表符","red")+"</b>。<br>"+
               "多个AC提交，则取代码长度最短的计算总长度，每题只计算一个AC代码长度<br>"+
                ((chengfa!=0)?"另外每次非AC的提交都会惩罚"+chengfa+"的代码长度":"错误的代码不计算在成绩内");
    }
}
