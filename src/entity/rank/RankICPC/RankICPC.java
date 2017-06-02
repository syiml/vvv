package entity.rank.RankICPC;

import entity.*;
import entity.rank.RankBaseUser;
import servise.ContestMain;
import util.Main;
import entity.rank.Rank;
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
public class RankICPC extends Rank<user> {
    int penalty = 20;//罚时默认20分钟
    List<String> fb;
    int cid;
    Contest contest = null;
    public RankICPC(Contest c){
        super(c);
        cid=c.getCid();
        contest = c;
        list=new ArrayList<user>();
        fb=new ArrayList<String>();
        for(int i=0;i<c.getUserNum();i++){
            add(c.getUser(i),c,user.class);
        }
    }

    public static FormHTML getFormHTML(Rank r){
        FormHTML f= new FormHTML();
        f.setPartFrom();

        text f1=new text("icpc_penalty","罚时");
        f1.setId("icpc_penalty");
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

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public void add(Status s,Contest c){
        //去掉无效结果
        if(s.getResult()==Result.DANGER||
           s.getResult()==Result.PENDING ||
           s.getResult()==Result.JUDGING||
           s.getResult()==Result.ERROR) return;
        //if(s.getCid()!=cid) return;
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
            user _user = new user();
            _user.init(u, 1,c.getProblemNum());
            User user = Main.users.getUser(_user.username);
            if(user!=null){
                _user.showUsername = (user.getUsernameHTML());
            }else{
                _user.showUsername = (_user.username);
            }
            if(user!=null) {
                _user.showNick =(user.getTitleAndNick());
            }else{
                _user.showNick =(HTML.textb("未注册","red"));
            }
            list.add(_user);
        }
        us=list.get(i);
        us.add(s,time,penalty,c);
    }

    @Override
    protected List<String> getExtraTableCol() {
        List<String> cols = new ArrayList<>();
        cols.add("S");
        cols.add("T");
        return cols;
    }

    @Override
    public String getCellByHead(int row, user us, String colName) {
        if(colName.equals("S")){
            return HTML.a("#S"+us.username,us.submitnum+"");
        }else if(colName.equals("T")){
            return us.penalty/1000/60+"";
        }
        return null;
    }

    @Override
    public String getTitle() {
        return "ICPC Rank";
    }

    public String getRuleHTML(){
        return "";
    }

    @Override
    public String toHTML(){
        getfb();
        return super.toHTML();
    }

    @Override
    protected String getCellByPid(int row, user u, int pid) {
        if(fb.get(pid).equals(u.username)){
            addProblemCl(row,pid,"fb");
        }else{
            String cl=u.resultclass(pid);
            if(!"".equals(cl)){
                addProblemCl(row,pid,cl);
            }
        }
        return u.result(pid);
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
