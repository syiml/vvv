package action.App;

import action.BaseAction;
import util.JSON.AppJson;

/**
 * 没有参数的请求
 * Created by syimlzhu on 2016/9/18.
 */
public class ActionNoPara extends BaseAction{
    public String getSelfInfo(){
        out.print(AppJson.getSelfUserInfo());
        return SUCCESS;
    }
}
