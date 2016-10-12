package servise;

import action.DiscussReply;
import dao.Discuss.DiscussSQL;
import entity.Discuss.Discuss;
import entity.Discuss.ReplyReply;
import entity.User;
import util.Main;
import util.MainResult;

/**
 * Created by QAQ on 2016/10/10.
 */
public class DiscussMain {
    public static MainResult replyReply(DiscussReply adr){
        User u = Main.loginUser();
        if(u == null) return MainResult.NO_LOGIN;
        Discuss discuss = DiscussSQL.getDiscuss(adr.getId());
        if(discuss == null) return MainResult.ARR_ERROR;
        entity.Discuss.DiscussReply discussReply = DiscussSQL.getDiscussReply(adr.getId(),adr.getRid());
        if(discussReply == null) return MainResult.ARR_ERROR;
        if(adr.getRrid() != -1){
            ReplyReply replyReply = DiscussSQL.getReplayReply(adr.getId(),adr.getRid(),adr.getRrid());
            if(replyReply == null) return MainResult.ARR_ERROR;
        }
        DiscussSQL.addReplyReply(adr.getId(),adr.getRid(),adr.getRrid(),u,adr.getText());
        return MainResult.SUCCESS;
    }
}
