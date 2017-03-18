package action.App;

import action.BaseAction;
import dao.ProblemTagSQL;
import util.JSON.AppJson;
import util.Main;

/**
 * Created by QAQ on 2016/9/23.
 */
public class ActionGetUserInfo extends BaseAction{
    public String username;
    public String getSelfInfo(){
        out.print(AppJson.getSelfUserInfo());
        return NONE;
    }
    public String getUserTag(){
        out.print(ProblemTagSQL.userTag(username));
        return NONE;
    }
}
