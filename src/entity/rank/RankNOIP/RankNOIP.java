package entity.rank.RankNOIP;

import entity.*;
import entity.rank.Rank;
import entity.rank.RankBaseUser;
import util.HTML.HTML;
import util.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QAQ on 2016/9/27.
 */
public class RankNOIP extends Rank<user> {
    int cid;
    Contest contest;

    public RankNOIP(Contest c){
        super(c);
        cid=c.getCid();
        contest = c;
        list=new ArrayList<user>();
        for(int i=0;i<c.getUserNum();i++){
            add(c.getUser(i),c,user.class);
        }
    }

    @Override
    protected List<String> getExtraTableCol() {
        List<String> cols = new ArrayList<>();
        cols.add("总分");
        return cols;
    }

    @Override
    public String getCellByHead(int row, user u, String colName) {
        return u.totalScore+"";
    }

    @Override
    public String getTitle() {
        return "NOIP Rank";
    }

    @Override
    public void add(Status s, Contest c) {
        //去掉无效结果
        if(s.getResult()== Result.DANGER||
                s.getResult()==Result.PENDING ||
                s.getResult()==Result.JUDGING||
                s.getResult()==Result.ERROR) return;
        //if(s.getCid()!=cid) return;
        //System.out.println("->"+s.getRid());

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
                _user.showUsername = user.getUsernameHTML();
                _user.showNick = user.getNick();
            }else{
                _user.showUsername = (_user.username);
                _user.showNick =(HTML.textb("未注册","red"));
            }
            if(user==null) {
                return ;
            }
            list.add(_user);
        }
        us=list.get(i);
        us.add(s,c);
    }

    @Override
    protected String getCellByPid(int row, user us, int pid) {
        if(us.submitNum.get(pid) == 0) return "";
        int score = us.scores.get(pid);
        if(score == 100){
            addProblemCl(row,pid,"success");
        }else if(score >=60){
            addProblemCl(row,pid,"warning");
        }else{
            addProblemCl(row,pid,"danger");
        }
        return us.scores.get(pid)+"";
    }
}
