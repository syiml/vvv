package servise;

import action.DiscussReply;
import dao.Discuss.DiscussSQL;
import entity.Discuss.Discuss;
import entity.Discuss.ReplyReply;
import entity.Permission;
import entity.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.HTML.pageBean;
import util.JSON.JSON;
import util.Main;
import util.MainResult;

import java.util.List;

/**
 * Created by QAQ on 2016/10/10.
 */
public class DiscussMain {
    public static int replyReplyShowNum = 5;

    public static MainResult replyReply(DiscussReply adr){
        User u = Main.loginUser();
        if(u == null) return MainResult.NO_LOGIN;
        Discuss discuss = DiscussSQL.getDiscuss(adr.getId());
        if(discuss == null) return MainResult.ARR_ERROR;
        entity.Discuss.DiscussReply discussReply = DiscussSQL.getDiscussReply(adr.getId(),adr.getRid());
        if(discussReply == null) return MainResult.ARR_ERROR;
        if(adr.getRrid() != -1){
            ReplyReply replyReply = DiscussSQL.getReplyReply(adr.getId(),adr.getRid(),adr.getRrid());
            if(replyReply == null) return MainResult.ARR_ERROR;
        }
        DiscussSQL.addReplyReply(adr.getId(),adr.getRid(),adr.getRrid(),u,adr.getText());
        return MainResult.SUCCESS;
    }

    public static JSONObject getReplyReplyJSON(int did,int rid,int page){
        Permission p = Main.loginUserPermission();
        int totalNum = DiscussSQL.getReplyReplyNum(did,rid,p.getAddDiscuss(),Main.loginUser());
        int totalPage = pageBean.getTotalPageNum(totalNum, replyReplyShowNum);
        if(page == -1){
            page = totalPage;
        }
        List<ReplyReply> replyReplyList = DiscussSQL.getReplyReply(did,rid,(page-1)* replyReplyShowNum, replyReplyShowNum,p.getAddDiscuss(),Main.loginUser());
        JSONObject ret = new JSONObject();
        ret.put("page",page);
        ret.put("totalPage",totalPage);
        ret.put("totalNum",totalNum);
        ret.put("did",did);
        ret.put("rid",rid);
        JSONArray ja = new JSONArray();
        for (ReplyReply rr : replyReplyList) {
            User u = Main.users.getUser(rr.getUsername());
            ReplyReply replyrr = DiscussSQL.getReplyReply(did,rid,rr.getReplyRid());
            User replyUser = null;
            if(replyrr!=null)
                 replyUser = Main.users.getUser(replyrr.getUsername());
            ja.add(JSON.getJSONObject(
                    "username",rr.getUsername(),
                    "usernameHTML",u.getUsernameAndNickHTML(),
                    "text",rr.getText(),
                    "time",rr.getTime().toString().substring(0,16),
                    "did",rr.getDid()+"",
                    "rid",rr.getRid()+"",
                    "rrid",rr.getRrid()+"",
                    "reply",replyrr==null?"":" 回复 "+replyUser.getUsernameAndNickHTML()
            ));
        }
        ret.put("list",ja);
        return ret;
    }
}
