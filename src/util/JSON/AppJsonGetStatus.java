package util.JSON;

import entity.Status;
import entity.User;
import net.sf.json.JSONObject;
import util.Main;

import java.util.List;

/**
 * Created by QAQ on 2016/9/29.
 */
public class AppJsonGetStatus extends BaseJsonPageBean<Status> {

    public AppJsonGetStatus(int nowPage, int everyPageNum) {
        super(nowPage, everyPageNum);
    }

    @Override
    protected List<Status> getList(int from, int num) {
        return Main.status.getStatus(-1,from,num,-1,-1,-1,"",false);
    }

    @Override
    protected int getTotalNum() {
        return Main.status.getStatusNum(-1,-1,-1,-1,"",false);
    }

    @Override
    protected JSONObject processingData(JSONObject jo){
        String username = jo.getString("username");
        User user = Main.users.getUser(username);
        jo.put("nick",user.getNick());
        return jo;
    }
}
