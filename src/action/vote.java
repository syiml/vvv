package action;

import dao.VoteDao;
import entity.User;
import entity.Vote.Vote;
import util.Main;

/**
 * Created by QAQ on 2017/3/26.
 */
public class vote extends BaseAction {
    private int did;
    private int id;

    public String do_vote(){
        User u = Main.loginUser();
        if(u==null) return ERROR;
        Vote vote = VoteDao.getInstance().getBeanByKey(did);
        if( vote == null) return ERROR;
        if( !vote.hasId(id)) return ERROR;

        if(VoteDao.getInstance().isVoteToday(did,u.getUsername())) return ERROR;
        VoteDao.getInstance().addVoteRecord(u.getUsername(),did,id);
        return SUCCESS;
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
