package action.App;

import action.BaseAction;
import util.JSON.AppJson;

/**
 * Created by QAQ on 2016/9/23.
 */
public class ActionGetUserInfo extends BaseAction{
    public String username;
    public String getSelfInfo(){
        out.print(AppJson.getSelfUserInfo());
        return NONE;
    }
}
