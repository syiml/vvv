package dao;

import entity.SomeOptRecord.ESomeOptRecordType;
import entity.SomeOptRecord.SomeOptRecord;
import entity.Vote.Vote;
import util.MyTime;
import util.SQL.SQL;
import util.Tool;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by QAQ on 2017/3/26.
 */
public class VoteDao extends BaseCacheLRU<Integer,Vote> {

    private static VoteDao voteDao;

    public static VoteDao getInstance(){
        if (voteDao == null) voteDao = new VoteDao(100);
        return voteDao;
    }
    public VoteDao(int maxSize) {
        super(maxSize);
    }

    @Override
    protected Vote getByKeyFromSQL(Integer key) {
        Vote vote = new SQL("SELECT * FROM t_vote WHERE did = ?",key).queryBean(Vote.class);
        List<SomeOptRecord> records = SomeOptRecordSQL.getInstance().getRecord(ESomeOptRecordType.Vote,key);
        for(SomeOptRecord record : records){
            try {
                vote.addNumber(Integer.parseInt(record.getData()));
            }catch (NumberFormatException ignored){}
        }
        return vote;
    }

    public void addVoteRecord(String user,int did,int id){
        SomeOptRecordSQL.getInstance().addRecord(ESomeOptRecordType.Vote,user, Tool.now(),did,id+"");
        getBeanByKey(did).addNumber(id);
    }

    public boolean isVoteToday(int did,String user){
        Timestamp now = Tool.now();
        return (SomeOptRecordSQL.getInstance().getRecord(ESomeOptRecordType.Vote,user, MyTime.getFistTimeOfDay(now),MyTime.getLastTimeOfDay(now)).size() != 0);
    }
}
