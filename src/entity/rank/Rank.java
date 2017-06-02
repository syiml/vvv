package entity.rank;

import entity.*;
import entity.rank.RankICPC.user;
import servise.ContestMain;
import util.HTML.HTML;
import util.HTML.TableHTML;
import util.Main;
import util.rating._rank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2015/5/26.
 */
public abstract class Rank<user extends RankBaseUser> {
    private static String css="<style>" +
            ".rank_1{" +
            "    background: gold;}"+
            ".rank_2{" +
            "    background:#c0c0c0;}" +
            ".rank_3{" +
            "    background: #b87333;}" +
            "</style>";
    protected List<user> list;
    protected int type_1,type_2,type_3;
    protected int m1,m2,m3;
    Contest contest;
    List<String> extraTableCol = null;
    TableHTML tableHTML;
    protected Rank(Contest c){
        this.contest = c;
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

    public abstract void add(Status  s,Contest c);//处理rejudge
    public _rank get_rank(){
        _rank r=new _rank();
        Collections.sort(list);
        int rank=0;
        user pu=null;
        for (user u : list) {
            //遍历所有 非星号 用户计算排名并返回
            if (u.valid == 1) {
                if (pu == null || u.compareTo(pu)!=0) {
                    rank++;
                }
                r.add(rank, u.username);
                pu = u;
            }
        }
        return r;
    }
    public String toHTML(){
        Collections.sort(list);
        tableHTML = new TableHTML();
        tableHTML.setClass("table table-bordered table-hover table-condensed");
        tableHTML.addColname("Rank"/*,"User"*/,"Nick");
        extraTableCol = getExtraTableCol();
        tableHTML.addColname(extraTableCol);
        int pnum= contest.getProblemNum();
        for(int j=0;j<pnum;j++){
            if(pnum>26){
                tableHTML.addColname(HTML.a("#P"+j,j+1+""));
            }else{
                tableHTML.addColname(HTML.a("#P"+j,(char)(j+'A')+""));
            }
        }
        String loginUserName = null;
        if(contest.getType() == Contest_Type.TEAM_OFFICIAL){
            Object trueLoginUser = Main.getSession().getAttribute(Contest.TRUE_USERNAME+contest.getCid());
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
            if (aList.valid == 1) {
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
        int size = list.size();
        int Rank = 1;
        int rowNumber = 0;
        for(int i=0;i<size;i++){
            List<String> row=new ArrayList<String>();
            user us=list.get(i);
            if(us.valid == -1) continue;
            rowNumber ++;
            if(us.valid == 1){
                row.add(Rank+"");
                String cl=color(Rank,number1,number2,number3);
                if(us.noRank()) cl = "";
                if(!cl.equals("")) tableHTML.addCl(rowNumber,0,cl);
                Rank++;
            }else{
                row.add("*");
            }
            if(loginUserName!=null&&us.username.equals(loginUserName)){
                tableHTML.addCl(rowNumber,1,"info");
            }
            //row.add(us.showUsername);
            row.add(us.showNick);
            for (String anExtraTableCol : extraTableCol) {
                row.add(getCellByHead(rowNumber,us, anExtraTableCol));
            }
            for(int j=0;j<pnum;j++){
                row.add(getCellByPid(rowNumber,us,j));
            }
            tableHTML.addRow(row);
        }
        return css+HTML.panelnobody(getTitle(),tableHTML.HTML());
    }
    protected abstract String getCellByPid(int row,user u, int pid);
    protected abstract List<String> getExtraTableCol();
    public abstract String getCellByHead(int row,user u,String colName);
    public abstract String getTitle();

    private String color(int r,int number1,int number2,int number3){
        if(r<=number1) return "rank_1";
        if(r<=number2) return "rank_2";
        if(r<=number3) return "rank_3";
        return "";
    }
    protected void addProblemCl(int row,int pid,String cla){
        tableHTML.addCl(row,pid+2+extraTableCol.size(),cla);
    }
    protected void addExColCl(int row,int col,String cla){
        tableHTML.addCl(row,3+col,cla);
    }
    public void add(RegisterUser u, Contest c,Class<user> cls){
        user _user = null;
        try {
            if(u.getStatu()==1||u.getStatu() == RegisterUser.STATUS_TEAM_AUTO_APPENDED){//1是正式，2是星号
                _user = cls.newInstance();
                _user.init(u.getUsername(),1,c.getProblemNum());
            }else if(u.getStatu()==2){
                _user = cls.newInstance();
                _user.init(u.getUsername(),0,c.getProblemNum());
            }else if(u.getStatu()==-1){
                _user = cls.newInstance();
                _user.init(u.getUsername(),-1,c.getProblemNum());
            }
            if(_user != null) {
                if(c.getType() == Contest_Type.TEAM_OFFICIAL){
                    _user.showUsername = u.getShowUserName();
                    _user.showNick = u.getShowNick();
                }else{
                    _user.showUsername = u.getShowUserName();
                    _user.showNick = u.getShowNick();
                }
                list.add(_user);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    protected List<String> getStringList(String... ss){
        List<String> list = new ArrayList<>();
        Collections.addAll(list, ss);
        return list;
    }
}
