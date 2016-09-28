package action.App;

import action.BaseAction;
import org.apache.struts2.ServletActionContext;
import util.JSON.AppJson;
import util.Main;
import util.Tool;

/**
 * 没有参数的请求
 * Created by syimlzhu on 2016/9/18.
 */
public class ActionNoPara extends BaseAction{

    public String getSelfInfo(){
        Tool.log("getSelfInfo");
        out.print(AppJson.getSelfUserInfo());
        return NONE;
    }
}
