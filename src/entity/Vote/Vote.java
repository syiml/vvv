package entity.Vote;

import dao.VoteDao;
import entity.IBeanResultSetCreate;
import entity.User;
import util.HTML.HTML;
import util.HTML.TableHTML;
import util.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QAQ on 2017/3/26.
 */
public class Vote implements IBeanResultSetCreate {
    private int did;
    private List<Vote_record> records = new ArrayList<>(5);
    @Override
    public void init(ResultSet rs) throws SQLException {
        do {
            Vote_record vote_record = new Vote_record();
            vote_record.init(rs);
            did = vote_record.did;
            records.add(vote_record);
        }while(rs.next());
    }

    public void addNumber(int id){
        for(Vote_record record:records){
            if(record.id == id){
                record.number++;
            }
        }
    }

    public boolean hasId(int id){
        for(Vote_record record:records){
            if(record.id == id){
                return true;
            }
        }
        return false;
    }

    public static int maxVoteEveryDay = 2;
    public String toHTML(){
        User u = Main.loginUser();
        boolean canVote = false;
        boolean isVoteToday = false;
        int voteNum = 0;
        if(u == null){
            canVote = false;
        }else{
            voteNum = VoteDao.getInstance().getVoteToday(did, u.getUsername()).size();
        }
        if(voteNum >= maxVoteEveryDay){
            canVote = false;
            isVoteToday = true;
        }else if (u != null){
            canVote = true;
        }
        StringBuilder sb = new StringBuilder();
        int maxNumber = 0;
        for(Vote_record record : records){
            maxNumber = Math.max(record.number,maxNumber);
        }
        if(isVoteToday) sb.append("今天的票已经投完了了，请明天再来吧！<br><br>");
        else{
            if(u!=null) sb.append("今天你还有").append(maxVoteEveryDay - voteNum).append("次投票机会<br><br>");
            else{
                sb.append("登录后可以投票<br><br>");
            }
        }
        for(Vote_record record : records) {
            if(record.isHide) continue;
            sb.append(record.des);
            if(canVote && !record.isDisable) sb.append(HTML.a("vote.action?did="+did+"&id="+record.id,"立即投票"));
            sb.append(HTML.progress(record.number*100.0 / maxNumber,record.number+"票"));
        }
        return sb.toString();
    }
}
