package entity.rank.RankTraining;

import entity.rank.RankBaseUser;
import util.Main;
import entity.User;
import entity.Contest;
import entity.RegisterUser;
import entity.rank.Rank;
import entity.rank.RankICPC.Form_text_select_inline;
import util.rating._rank;
import entity.Result;
import entity.Status;
import util.HTML.FromHTML.FormHTML;
import util.HTML.HTML;
import util.HTML.TableHTML;
import util.HTML.modal.modal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Syiml on 2015/8/11 0011.
 */
//根据AC题目排序，然后按照错误次数排序。
public class RankTraining extends Rank<user> {
    int cid;
    int pnum;
    public RankTraining(Contest c){
        super(c);
        this.list=new ArrayList<user>();
        this.cid=c.getCid();
        this.pnum=c.getProblemNum();
//        List<statu> list=Main.status.getStauts(cid);
        for(int i=0;i<c.getUserNum();i++){
            add(c.getUser(i),c,user.class);
        }
    }

    public static FormHTML getFormHTML(Rank r){
        FormHTML f= new FormHTML();
        f.setPartFrom();

        Form_text_select_inline f2=new Form_text_select_inline("training_m1","金奖");
        if(r instanceof RankTraining) f2.setValue(((RankTraining)r).m1+"",((RankTraining) r).type_1+"");
        else f2.setValue("10","1");
        f.addForm(f2);

        Form_text_select_inline f3=new Form_text_select_inline("training_m2","银奖");
        if(r instanceof RankTraining) f3.setValue(((RankTraining)r).m2+"",((RankTraining) r).type_2+"");
        else f3.setValue("30","1");
        f.addForm(f3);

        Form_text_select_inline f4=new Form_text_select_inline("training_m3","铜奖");
        if(r instanceof RankTraining) f4.setValue(((RankTraining)r).m3+"",((RankTraining) r).type_3+"");
        else f4.setValue("60","1");
        f.addForm(f4);

        return f;
    }

    @Override
    protected List<String> getExtraTableCol() {
        return getStringList("S","W");
    }

    @Override
    public String getCellByHead(int row, user u, String colName) {
        if(colName.equals("S")){
            return u.getAcNum()+"";
        }else if(colName.equals("W")){
            return u.getWrongNum()+"";
        }
        return null;
    }

    @Override
    public String getTitle() {
        modal m=new modal("rankrule","练习排名规则",getRuleHTML(),"查看详细规则");
        m.setBtnCls("link btn-xs");
        m.setHavesubmit(false);
        return "练习赛排名"+HTML.floatRight(m.toHTML());
    }

    public  void add(Status s,Contest c){
        if(s.getResult()== Result.DANGER||
                s.getResult()==Result.PENDING ||
                s.getResult()==Result.JUDGING||
                s.getResult()==Result.ERROR)return ;
        //if(s.getCid()!=c.getCid()) return;
        int j;
        for(j=0;j<this.list.size();j++){
            if(this.list.get(j).username.equals(s.getUser())){
                this.list.get(j).addres(c.getcpid(s.getPid()),s.getResult(),s.getCodelen());
                break;
            }
        }
        if(j==this.list.size()){
            user u = new user();
            u.init(s.getUser(),1,pnum);
            User us = Main.users.getUser(s.getUser());
            u.showUsername = us.getUsernameHTML();
            u.showNick = us.getNick();
            this.list.add(u);
            this.list.get(j).addres(c.getcpid(s.getPid()),s.getResult(),s.getCodelen());
        }
    }//处理rejudge

    @Override
    protected String getCellByPid(int row, user u, int pid) {
        if(u.num.get(pid)>0){
            //row.add("✔"+(u.num.get(j)>1?"(-"+(u.num.get(j)-1)+")":""));
            addProblemCl(row, pid, "success");
            return ""+(u.num.get(pid)>1?"-"+(u.num.get(pid)-1)+"":"");
        }else if(u.num.get(pid)<0){
            //row.add("✘("+u.num.get(j)+")");
            addProblemCl(row, pid, "danger");
            return ""+u.num.get(pid)+"";
        }else{
            return "";
        }
    }

    public  String getRuleHTML(){
        return "先按题数降序排名，再按AC题目的错误次数总和升序";
    }
}
