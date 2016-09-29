package action.App;

import action.BaseAction;
import org.apache.struts2.ServletActionContext;
import org.jsoup.nodes.Document;
import util.JSON.AppJson;
import util.JSON.JSON;
import util.Main;
import util.MyClient;
import util.Tool;

import java.sql.Timestamp;

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
    public String getOtherOjsContest(){
        out.print(JSON.getOtherOjsContest());
        return NONE;
    }
}
