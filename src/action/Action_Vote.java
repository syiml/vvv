package action;

import dao.VoteDao;
import entity.SomeOptRecord.SomeOptRecord;
import entity.User;
import entity.Vote.Vote;
import util.Main;
import util.MainResult;

import java.util.List;

/**
 * Created by QAQ on 2017/3/26.
 */
public class Action_Vote extends BaseAction {
    private int did;
    private int id;

    private MainResult do_vote_service(){
        User u = Main.loginUser();
        if(u==null){
            return MainResult.NO_LOGIN;
        }
        Vote vote = VoteDao.getInstance().getBeanByKey(did);
        if( vote == null ||  !vote.hasId(id)){
            return MainResult.ARR_ERROR;
        }

        List<SomeOptRecord> list = VoteDao.getInstance().getVoteToday(did,u.getUsername());
        if(list.size() >= Vote.maxVoteEveryDay){
            return MainResult.VOTE_NUM_LIMIT;
        }
        for(SomeOptRecord record : list){
            if(record.getData().equals(id+"")){
                return MainResult.VOTE_REP;
            }
        }
        VoteDao.getInstance().addVoteRecord(u.getUsername(),did,id);
        return MainResult.SUCCESS;
    }
    public String do_vote(){
        MainResult mr = do_vote_service();
        this.setPrompt(mr.getPrompt());
        if(mr == MainResult.SUCCESS) return SUCCESS;
        return ERROR;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
